package com.szcgc.comm.service;

import com.szcgc.comm.IbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.Optional;

/**
 * @Author liaohong
 * @create 2022/9/17 16:05
 */
public abstract class BaseService<R extends PagingAndSortingRepository<T, ID>, T, ID extends Serializable> implements IbcService<T, ID> {

    protected R repository;

    public R getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(R repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> find(ID id) {
        return repository.findById(id);
    }

    @Override
    public T update(T entity) {
        return repository.save(entity);
    }

    @Override
    public T insert(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Override
    public Page<T> findAll(int pageNo, int pageSize) {
        return repository.findAll(dftPage(pageNo, pageSize));
    }

    @Override
    public void batchInsert(Iterable<T> entitys) {
        repository.saveAll(entitys);
    }

    @Override
    public void batchUpdate(Iterable<T> entitys) {
        repository.saveAll(entitys);
    }
}
