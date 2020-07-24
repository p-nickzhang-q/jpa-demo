package com.example.jpa.services.common;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

interface IBaseService<T, ID> {


    List<T> findAll();

    List<T> findAll(Sort sort);


    Page<T> findAll(Pageable pageable);


    List<T> findAllById(Iterable<ID> iterable);


    long count();


    void deleteById(ID id);


    void delete(T t);


    void deleteAll(Iterable<? extends T> iterable);


    void deleteAll();


    <S extends T> S save(S s);


    <S extends T> List<S> saveAll(Iterable<S> iterable);


    Optional<T> findById(ID id);


    boolean existsById(ID id);


    void flush();


    <S extends T> S saveAndFlush(S s);


    void deleteInBatch(Iterable<T> iterable);


    void deleteAllInBatch();


    T getOne(ID id);


    <S extends T> Optional<S> findOne(Example<S> example);


    <S extends T> List<S> findAll(Example<S> example);


    <S extends T> List<S> findAll(Example<S> example, Sort sort);


    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);


    <S extends T> long count(Example<S> example);


    <S extends T> boolean exists(Example<S> example);

    Optional<T> findOne(Specification<T> specification);


    List<T> findAll(Specification<T> specification);

    Page<T> findAll(Specification<T> specification, Pageable pageable);


    List<T> findAll(Specification<T> specification, Sort sort);

    long count(Specification<T> specification);

}
