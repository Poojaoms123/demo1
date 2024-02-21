package com.example.Demo1.Service;

import com.example.Demo1.Model.Client;
import com.example.Demo1.Model.SaveRequest.SaveClientRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ClientService {

    String saveOrUpdateClient(SaveClientRequest saveClientRequest) throws Exception;

    Client getById(Long clientId) throws Exception;

    List<Client> getAllByDeleted();

    Object deleteById(Long clientId) throws Exception;

    Object changeStatus(Long clientId) throws Exception;

    Object getByIdClient(Long clientId) throws Exception;

    Object getAllByDeleted(String clientName, String clientEmail, String clientMobileNo, Pageable pageable);

    Object getAllByDeleted(String clientName, String clientEmail, String clientMobileNo, String startdate, String enddate, Pageable pageable);

    Object getAllByDeleted(String startdate, String enddate, Pageable pageable);

    Object changepassword(Long clientId, String oldpassword, String newpassword) throws Exception;
}
