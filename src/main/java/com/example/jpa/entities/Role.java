package com.example.jpa.entities;

import lombok.*;
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
@Table(name = "jpa_role")
@EqualsAndHashCode(exclude = {"users"})
public class Role {

    @Id
    @GeneratedValue
    private Long roleId;
    private String roleName;

    /**
     * @ManyToMany(targetEntity = User.class)
     * @JoinTable(name = "jpa_user_role",
     * joinColumns = {@JoinColumn(name = "jpaRoleId", referencedColumnName = "roleId")},
     * inverseJoinColumns = {@JoinColumn(name = "jpaUserId", referencedColumnName = "userId")})
     */
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

}
