package com.szcgc.customer.service.impl;

import com.szcgc.comm.service.BaseService;
import com.szcgc.customer.model.Person;
import com.szcgc.customer.repository.PersonRepository;
import com.szcgc.customer.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonServiceImpl extends BaseService<PersonRepository, Person, Integer> implements PersonService {

    @Override
    public Person findByCustId(Integer custId) {
        return repository.findByCustomerId(custId);
    }
}
