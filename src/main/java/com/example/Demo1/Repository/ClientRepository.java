package com.example.Demo1.Repository;

import com.example.Demo1.Model.Client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from Demo1_Client where client_id=:clientId",nativeQuery = true)
    void deleteByClientId(Long clientId);

    @Query(value = "select * from Demo1_Client where Client_is_deleted=false ",nativeQuery = true)

    List<Client> getAllByDeleted();

    Client findByClientId(Long clientId);
    
    @Query(value = "select c from Client as c where (:clientName is null or lower(c.clientName) like %:clientName%) and (:clientEmail is null or lower(c.clientEmail) like %:clientEmail%) and (:clientMobileNo is null or lower(c.clientMobileNo) like %:clientMobileNo%) and c.clientIsDeleted=false")
    Page<Client> getAllByClientName(String clientName, String clientEmail, String clientMobileNo, Pageable pageable);

    Page<Client> findAllByClientIsDeleted(boolean b, Pageable pageable);

    @Query(value = "select * from demo1_Client where client_create_at between :startDateTime and :endDateTime and client_is_deleted=false order by client_create_at desc ",nativeQuery = true)
    Page<Client> getAllByDateFilter(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    boolean existsByClientEmail(String clientEmail);

    boolean existsByClientEmailAndClientIdNotIn(String clientEmail, List<Long> clientId);


    boolean existsByClientMobileNoAndClientIdNotIn(String clientMobileNo, List<Long> ids);

    boolean existsByClientMobileNo(String clientMobileNo);
}
