package com.example.demo.api.data;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Room {

    private final Long id;

    private final List<Member> members;

    private final LocalDateTime latestDate;

    private final String latestMessage;
}
