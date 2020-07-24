package com.example.jpa.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"linkMans"})
@ToString(exclude = {"linkMans"})
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String address;
    private String name;

    /**
     * JoinColumn name是外键名称，referencedColumnName是主表的主键
     * JoinColumn 在主从表都配置的话，是双向，反之则是单向，
     */
//    @OneToMany(targetEntity = LinkMan.class)
//    @JoinColumn(name = "customerId", referencedColumnName = "id")
    /**
     * 放弃外键维护，添加mappedBy，指定子对象的customer属性来维护外键。
     * 如果主表不放弃外键维护，那么删除从表时，会将从表外键设为null，然后删除主表记录。
     * 但当放弃外键维护后，那么删除主表会报有外键引用的错误。
     */
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    private Set<LinkMan> linkMans;
}
