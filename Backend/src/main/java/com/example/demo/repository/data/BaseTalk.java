package com.example.demo.repository.data;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table("talk")
public class BaseTalk {

    @Id
    @Column ("id")
    private final long id;

    @Column ("user_id")
    private final long userId;

    @Column ("create_date")
    private final LocalDateTime createDate;

    @Column ("message")
    private final String message;

    @Column ("room_id")
    private final long roomId;
}
