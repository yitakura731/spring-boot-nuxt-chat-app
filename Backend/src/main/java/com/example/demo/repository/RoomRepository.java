package com.example.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.data.BaseRoom;

@Repository
public interface RoomRepository extends PagingAndSortingRepository<BaseRoom,Long>{


}
