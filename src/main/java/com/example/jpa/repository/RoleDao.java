package com.example.jpa.repository;

import com.example.jpa.entities.Role;
import com.example.jpa.repository.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends BaseRepository<Role, Long> {
}
