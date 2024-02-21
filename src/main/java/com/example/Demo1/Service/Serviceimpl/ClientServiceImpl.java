package com.example.Demo1.Service.Serviceimpl;

import com.example.Demo1.Model.Client;
import com.example.Demo1.Model.Response.pageDTO;
import com.example.Demo1.Model.SaveRequest.SaveClientRequest;
import com.example.Demo1.Repository.ClientRepository;
import com.example.Demo1.Service.ClientService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    private LocalDateTime LocalDateTimeStartDateTime;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String saveOrUpdateClient(SaveClientRequest saveClientRequest) throws Exception {
        if (clientRepository.existsById(saveClientRequest.getClientId())) {
            List<Long> ids=new ArrayList<>();
            ids.add(saveClientRequest.getClientId());
            Client client = clientRepository.findById(saveClientRequest.getClientId()).get();
            client.setClientName(saveClientRequest.getClientName());
            if(clientRepository.existsByClientEmailAndClientIdNotIn(saveClientRequest.getClientEmail(),ids)){
                throw new Exception("Email already exists");
            }else {
                client.setClientEmail(saveClientRequest.getClientEmail());
            }
            if (clientRepository.existsByClientMobileNoAndClientIdNotIn(saveClientRequest.getClientMobileNo(),ids)){
                throw new Exception(("MobileNo already exists"));
            }else {
                client.setClientMobileNo(saveClientRequest.getClientMobileNo());
            }
            client.setClientUsername(saveClientRequest.getClientUserName());
            client.setClientPassword(this.bCryptPasswordEncoder.encode(client.getClientPassword()));
            client.setClientIsDeleted(false);
            client.setClientIsAcive(true);
            clientRepository.save(client);
            return "update sucessfully";
        } else {
            Client client = new Client();
            client.setClientName(saveClientRequest.getClientName());
            if(clientRepository.existsByClientEmail(saveClientRequest.getClientEmail())){
                throw new Exception("Email already exists");
            }else {
                client.setClientEmail(saveClientRequest.getClientEmail());
            }
            if (clientRepository.existsByClientMobileNo(saveClientRequest.getClientMobileNo())){
                throw  new Exception("Mobile No already exists");
            }else {
                client.setClientMobileNo(saveClientRequest.getClientMobileNo());
            }
            client.setClientUsername(saveClientRequest.getClientUserName());
            client.setClientPassword(this.bCryptPasswordEncoder.encode(saveClientRequest.getClientpassword()));
            client.setClientIsDeleted(false);
            client.setClientIsAcive(true);
            clientRepository.save(client);
            return "saved sucessfully";
        }
    }

    @Override
    public Client getById(Long clientId) throws Exception {
        if (clientRepository.existsById(clientId)) {
            Client client = clientRepository.findById(clientId).get();
            return client;
        } else {
            throw new Exception("client not found");
        }
    }

    @Override
    public List<Client> getAllByDeleted() {
        List<Client> clients = clientRepository.getAllByDeleted();
        return clients;
    }

    @Override
    public Object deleteById(Long clientId) throws Exception {
        if (clientRepository.existsById(clientId)) {
            Client client = clientRepository.findById(clientId).get();
            client.setClientIsDeleted(true);
            clientRepository.save(client);
            return "deleted sucessfully";

        } else {
            throw new Exception(" client not found");
        }

    }

    @Override
    public Object changeStatus(Long clientId) throws Exception {
        if (clientRepository.existsById(clientId)) {
            Client client = clientRepository.findByClientId(clientId);
            if (client.getClientIsAcive()) {
                client.setClientIsAcive(true);
                return "student active";
            } else {
                client.setClientIsAcive(false);
                return "student inactive";
            }
        } else {
            throw new Exception("student not exits");
        }
    }

    @Override
    public Object getByIdClient(Long clientId) throws Exception {
        if (clientRepository.existsById(clientId)) {

            clientRepository.deleteByClientId(clientId);
            return "Deleted sucessfully";
        } else {
            throw new Exception("client not found");
        }
    }

    @Override
    public Object getAllByDeleted(String clientName, String clientEmail, String clientMobileNo, Pageable pageable) {
        Page<Client> clients;
        if (StringUtils.isNotBlank(clientName) || StringUtils.isNotBlank(clientEmail) || StringUtils.isNotBlank(clientMobileNo)) {
            System.out.println("name " + clientName + " email " + clientEmail + " mob no " + clientMobileNo);
            System.out.println("search");
            if (StringUtils.isNotBlank(clientName)) {
                clientName = clientName.toLowerCase();
            } else {
                clientName = null;
            }
            if (StringUtils.isNotBlank(clientEmail)) {
                clientEmail = clientEmail.toLowerCase();
            } else {
                clientEmail = null;
            }
            if (StringUtils.isBlank(clientMobileNo)) {
                clientMobileNo = null;
            }
            clients = clientRepository.getAllByClientName(clientName, clientEmail, clientMobileNo, pageable);
        } else {
            System.out.println("all");
            clients = clientRepository.findAllByClientIsDeleted(false, pageable);
        }
        return new pageDTO(clients.getContent(), clients.getTotalElements(), clients.getNumber(), clients.getTotalPages());
    }

    @Override
    public Object getAllByDeleted(String clientName, String clientEmail, String clientMobileNo, String startdate, String enddate, Pageable pageable) {
        return null;
    }

    @Override
    public Object getAllByDeleted(String startdate, String enddate, Pageable pageable) {

       Page<Client> clients = null;

        if (StringUtils.isNotBlank(startdate) && StringUtils.isNotBlank(enddate)) {
            try {
                LocalDate startdate1 = LocalDate.parse(startdate);
                LocalDate enddate1 = LocalDate.parse(enddate);

                LocalDateTime startDateTime = LocalDateTime.of(startdate1, LocalTime.of(0, 0));
                LocalDateTime endDateTime = LocalDateTime.of(enddate1, LocalTime.of(23, 59));
                clients = clientRepository.getAllByDateFilter(startDateTime,endDateTime,pageable);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }else {
            clients = clientRepository.findAllByClientIsDeleted(false,pageable);
        }      
        return new pageDTO(clients.getContent(), clients.getTotalElements(), clients.getNumber(), clients.getTotalPages());
    }

    @Override
    public Object changepassword(Long clientId, String oldpassword, String newpassword) throws Exception {
        Client client=clientRepository.findById(clientId).get();

        String oldpasswordDatabase = client.getClientPassword();
        if (oldpassword.matches(oldpasswordDatabase)){
            if (newpassword.matches(oldpasswordDatabase)){
                throw new Exception("new password not mathch");
            }else {
                client.setClientPassword(newpassword);
                clientRepository.save(client);
            }

        }else {
            return "password incorrect";
        }
        return client;
    }
}




