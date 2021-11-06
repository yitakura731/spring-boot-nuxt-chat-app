package com.example.demo.repository.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table("member")
public class BaseMember {

    @Id
    @Column ("id")
    private final Long id;

    @Column ("member_index")
    private final Long memberIndex;

    @Column ("user_id")
    private final Long userId;

    @Column ("room_id")
    private final Long roomId;
}
