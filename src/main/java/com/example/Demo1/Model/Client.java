package com.example.Demo1.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(name = "Demo1_Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_email")
    private String clientEmail;

    @Column(name = "client_mobile_no")
    private String clientMobileNo;

    @Column(name = "client_username")
    private String clientUsername;

    @Column(name = "client_password")
    private String clientPassword;

    @Column(name = "client_is_deleted")
    private Boolean clientIsDeleted;

    @Column(name = "client_is_active")
    private Boolean clientIsAcive;

    @CreationTimestamp
    @Column(name = "client_create_at",updatable = false)
    private LocalDateTime clientCreatetedAt;

    @UpdateTimestamp
    @Column(name = "client_updated_at")
    private LocalDateTime clientUpdatedAt;

}
