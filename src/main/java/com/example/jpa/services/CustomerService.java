package com.example.jpa.services;

import com.example.jpa.entities.Customer;
import com.example.jpa.services.common.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends BaseService<Customer, Long> {
}
