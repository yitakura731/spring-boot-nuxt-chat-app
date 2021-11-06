package com.example.demo.api.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Member {

    private long id;

    private String userId;

    private String name;
}
