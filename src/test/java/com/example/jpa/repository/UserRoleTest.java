package com.example.jpa.repository;

import com.example.jpa.entities.Role;
import com.example.jpa.entities.User;
import com.example.jpa.exception.ValidationException;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    /**
     * 多对多中，谁是被动的一方，谁放弃维护外键
     */

    @Test
    @Transactional
    @Rollback(false)
    public void testAdd() {
        User user = User.builder().username("小李").build();
        Role role = Role.builder().roleName("java").build();
        user.setRoles(Sets.newHashSet(role));
        role.setUsers(Sets.newHashSet(user));
        userDao.save(user);
        roleDao.save(role);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeAdd() {
        User user = User.builder().username("小李").build();
        Role role = Role.builder().roleName("java").build();
        user.setRoles(Sets.newHashSet(role));
        userDao.save(user);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeDel() {
        User user = userDao.findById(1L).orElseThrow(ValidationException::new);
        userDao.delete(user);
    }

}
