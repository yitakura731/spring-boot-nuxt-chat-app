package com.example.demo.api.request;

import java.util.List;

import lombok.Data;

@Data
public class CreateRoomRequest {

    private List<Long> userIds;
}
