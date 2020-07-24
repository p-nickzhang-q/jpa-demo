package com.example.jpa.repository;

import com.example.jpa.entities.LinkMan;
import com.example.jpa.repository.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkManDao extends BaseRepository<LinkMan, Long> {
}
