package com.example.demo.api.data;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Talk {

    private long id;

    private AppUser user;

    private LocalDateTime date;

    private String message;

}
