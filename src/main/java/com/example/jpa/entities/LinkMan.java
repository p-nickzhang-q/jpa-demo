package com.example.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LinkMan {

    @GeneratedValue
    @Id
    private Long id;

    private String name;
    /**
     * JoinColumn name是外键名称，referencedColumnName是主表的主键
     */
    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    private Customer customer;
}
