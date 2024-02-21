package com.example.Demo1.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse {
    private Boolean error;
    private String msg;
    private List<Countries> data;
}
