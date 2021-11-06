package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.data.BaseTalk;

@Repository
public interface TalkRepository extends CrudRepository<BaseTalk,Long> {

    public List<BaseTalk> findByRoomIdOrderByCreateDateDesc(long roomId) throws Exception;

    public List<BaseTalk> findByRoomIdOrderByCreateDateAsc(long roomId) throws Exception;

}
