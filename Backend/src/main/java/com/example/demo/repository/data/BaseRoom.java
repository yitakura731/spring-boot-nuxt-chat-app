package com.example.demo.repository.data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table("room")
public class BaseRoom {

    @Id
    @Column ("id")
    private final Long id;

    @Column ("create_date")
    private final LocalDateTime createDate;

    @Column ("update_date")
    private final LocalDateTime updateDate;

    @MappedCollection(idColumn = "room_id", keyColumn = "member_index")
    private final List<BaseMember> members;

}
