package com.szcgc.customer.service;

import com.szcgc.comm.IbcService;
import com.szcgc.customer.model.Person;

/**
 * 个人信息业务接口
 *
 * @Author chenjiaming
 * @create 2022-9-20 17:18:02
 */
public interface PersonService extends IbcService<Person, Integer> {

    Person findByCustId(Integer custId);
}
