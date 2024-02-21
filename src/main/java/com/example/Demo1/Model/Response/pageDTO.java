package com.example.Demo1.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class pageDTO {
    private Object Data;
    private Long totalElment;
    private Integer pageNumber;
    private Integer totalPages;
}
