package com.szcgc.comm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Optional;

public interface IbcService<T, ID extends Serializable> {

    /**
     * @param id 读取
     * @return
     */
    Optional<T> find(ID id);

    /**
     * @param entity 更新
     * @return
     */
    T update(T entity);

    /**
     * @param entity 插入
     * @return
     */
    T insert(T entity);

    /**
     * @param id 删除
     */
    void delete(ID id);

    /**
     * 一般是后台用到
     *
     * @param pageNo   当前页数
     * @param pageSize 每页条数
     * @return
     */
    Page<T> findAll(int pageNo, int pageSize);

    Sort DFTSORT = Sort.by(Sort.Direction.DESC, "id");

    default Pageable dftPage(int pageNo, int pageSize) {
        //checkArgument(pageNo >= 0);
        //checkArgument(pageSize > 0);
        return PageRequest.of(pageNo, pageSize, DFTSORT);
    }

    /**
     * 批量插入
     *
     * @param entitys 批量插入
     * @return
     */
    void batchInsert(Iterable<T> entitys);

    /**
     * 批量修改
     *
     * @param entitys 批量修改
     * @return
     */
    void batchUpdate(Iterable<T> entitys);

//    default T copyProperties(T newT, T oldT) {
//        //将传过来的新实体赋值给从数据库取出来旧实体，并返回
//        BeanUtils.copyProperties(newT,oldT,"createDate","createBy");
//        return oldT;
//    }
}
