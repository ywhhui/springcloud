package com.szcgc.customer.service;

import cn.hutool.core.util.RandomUtil;
import com.szcgc.customer.model.CustomerInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @Resource
    private ICustomerService customerService;

    @Test
    public void save() {
        for (int i = 0; i < 100; i++) {
            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setName("测试客户" + i);
            customerInfo.setCate(RandomUtil.randomInt(100) % 2 + 1);
            customerService.insert(customerInfo);
        }

    }

}
