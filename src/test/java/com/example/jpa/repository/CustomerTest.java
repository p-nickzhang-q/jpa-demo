package com.example.jpa.repository;

import com.example.jpa.entities.Customer;
import com.example.jpa.entities.LinkMan;
import com.example.jpa.exception.ValidationException;
import com.example.jpa.services.CustomerService;
import com.example.jpa.services.LinkManService;
import com.example.jpa.utils.MapsUtill;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired()
    private CustomerService customerService;

    @Autowired
    private LinkManDao linkManDao;
    @Autowired
    private LinkManService linkManService;
//    @Test
//    public void testFindOne() {
//        Optional<Customer> byId = customerRepository.findById(3L);
//        System.out.println(byId);
//    }

//    @Test
//    public void testFindByAddress() {
//        Optional<Customer> test = customerRepository.findByAddress("test");
//        System.out.println(test);
//    }

//    @Test
//    public void testUpdate() {
//        customerRepository.updateAddress(1L, "test2");
//    }

    @Test
    public void testCustomerService() {
        List<LinkMan> linkManList = linkManService.filter(MapsUtill.newHashMap(
                "customer", MapsUtill.newHashMap(
                        "name", "张三"
                )
        ));
        System.out.println(linkManList);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testAdd() {
        Customer customer = Customer.builder().name("百度2").address("test").build();
        LinkMan linkMan = LinkMan.builder().name("小李2").customer(customer).build();
//        customer.setLinkMans(Sets.newHashSet(linkMan));
        customerRepository.save(customer);
        linkManDao.save(linkMan);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testAdd2() {
        Customer customer = Customer.builder().name("百度2").address("test").build();
        LinkMan linkMan = LinkMan.builder().name("小李2").customer(customer).build();
        /*通过改集合来添加外键，会额外通过update语句来增加外键，而通过子对象设值父对象，则不会，直接就是两个insert语句*/
        customer.setLinkMans(Sets.newHashSet(linkMan));
        customerRepository.save(customer);
        linkManDao.save(linkMan);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeAdd() {
        Customer customer = Customer.builder().name("百度cc").address("test").build();
        LinkMan linkMan = LinkMan.builder().name("小李cc").build();
        customer.setLinkMans(Sets.newHashSet(linkMan));
        customerRepository.save(customer);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeDel() {
        Customer customer = customerRepository.findById(1L).orElseThrow(ValidationException::new);
        customerRepository.delete(customer);
    }

    /**
     * 对象导航查询，默认会使用延迟加载，即用到的时候再去查询子对象，可以通过fetch设置延迟还是饿汉加载
     */
    @Test
    @Transactional
    public void testGet() {
//        Customer customer = customerRepository.findById(1L).orElseThrow(ValidationException::new);
//        customer.getLinkMans().forEach(System.out::println);
        LinkMan linkMan = linkManDao.findById(1L).orElseThrow(ValidationException::new);
        System.out.println(linkMan.getCustomer());
    }
}
