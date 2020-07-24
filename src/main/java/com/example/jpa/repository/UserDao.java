package com.example.jpa.repository;

import com.example.jpa.entities.User;
import com.example.jpa.repository.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseRepository<User, Long> {
}
