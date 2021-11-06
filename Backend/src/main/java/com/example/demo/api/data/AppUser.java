package com.example.demo.api.data;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppUser {

    private Long id;

    private String userId;

    private String name;

    private String email;
}
