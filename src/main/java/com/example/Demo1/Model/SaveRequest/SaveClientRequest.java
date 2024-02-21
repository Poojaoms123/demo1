package com.example.Demo1.Model.SaveRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveClientRequest {
    private Long clientId;
    private String clientName;
    private String clientEmail;
    private String clientMobileNo;
    private String clientUserName;
    private String clientpassword;
}
