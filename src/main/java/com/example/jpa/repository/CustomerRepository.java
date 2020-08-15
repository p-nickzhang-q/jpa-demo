package com.example.jpa.repository;

import com.example.jpa.entities.Customer;
import com.example.jpa.repository.common.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, Long> {
    /**
     * @Query("from Customer where address = :address")
     */
    public Optional<Customer> findByAddress(String address);

    @Transactional
    @Modifying
    @Query("update Customer set address = :address where id = :id")
    public void updateAddress(Long id, String address);
}
