package com.example.jpa.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "jpa_user")
@EqualsAndHashCode(exclude = {"roles"})
public class User {

    @Id
    @GeneratedValue
    private Long userId;
    private String username;
    private Integer age;
    /**
     * 中间表用JoinTable
     */
    @ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL)
    @JoinTable(name = "jpa_user_role",
            /*joinColumns是当前表对应中间表的外键*/
            joinColumns = {@JoinColumn(name = "jpaUserId", referencedColumnName = "userId")},
            /*inverseJoinColumns是对方表对应中间表的外键*/
            inverseJoinColumns = {@JoinColumn(name = "jpaRoleId", referencedColumnName = "roleId")})
    private Set<Role> roles = new HashSet<>();

    @CreatedBy
    private Long createBy;

    @CreatedDate
    private Long createTime;

    @LastModifiedBy
    private Long updateBy;

    @LastModifiedDate
    private Long updateTime;
}
