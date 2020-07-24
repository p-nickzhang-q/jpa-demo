package com.example.jpa.services.common;

import com.example.jpa.exception.ValidationException;
import com.example.jpa.repository.common.BaseRepository;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public abstract class BaseService<T, ID> implements IBaseService<T, ID> {

    @Autowired
    protected BaseRepository<T, ID> repository;


    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<T> findAllById(Iterable<ID> iterable) {
        return repository.findAllById(iterable);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void deleteAll(Iterable<? extends T> iterable) {
        repository.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public <S extends T> S save(S s) {
        return repository.save(s);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> iterable) {
        return repository.saveAll(iterable);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public <S extends T> S saveAndFlush(S s) {
        return repository.saveAndFlush(s);
    }

    @Override
    public void deleteInBatch(Iterable<T> iterable) {
        repository.deleteInBatch(iterable);
    }

    @Override
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    @Override
    public T getOne(ID id) {
        return repository.getOne(id);
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return repository.findOne(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return repository.count(example);
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return repository.exists(example);
    }

    @Override
    public Optional<T> findOne(Specification<T> specification) {
        return repository.findOne(specification);
    }

    @Override
    public List<T> findAll(Specification<T> specification) {
        return repository.findAll(specification);
    }

    @Override
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public List<T> findAll(Specification<T> specification, Sort sort) {
        return repository.findAll(specification, sort);
    }

    @Override
    public long count(Specification<T> specification) {
        return repository.count(specification);
    }

    public Page<T> filter(Map<String, Object> filter, Pageable pageable) {
        Specification<T> specification = pareseFilter2Spec(filter);
        return beforeReturn(repository.findAll(specification, pageable));
    }

    public List<T> filter(Map<String, Object> filter) {
        Specification<T> specification = pareseFilter2Spec(filter);
        return beforeReturn(repository.findAll(specification));
    }

    public List<T> filter(Map<String, Object> filter, Sort sort) {
        Specification<T> specification = pareseFilter2Spec(filter);
        return beforeReturn(repository.findAll(specification, sort));
    }

    protected Specification<T> pareseFilter2Spec(Map<String, Object> filter) {
        return (Specification<T>) (root, criteriaQuery, cb) -> {
            List<Predicate> restrictions = new ArrayList<>();
            parseFilter(root, cb, filter, restrictions);
            return cb.and(restrictions.toArray(new Predicate[0]));
        };
    }

    private Object processEnumValueFilter(Path<Object> path, String v) {
        Object result = null;
//        if (ApplicantStatus.class.getName().equals(root.get(k).getJavaType().getName())) {
//            result = Enum.valueOf(ApplicantStatus.class, v.toString());
//        }
        return Optional.ofNullable(result).orElseThrow(() -> new ValidationException("枚举类型错误"));
    }

    protected void parseFilter(Path<T> root, CriteriaBuilder cb, Map<String, Object> filter, List<Predicate> predicates) {
        filter.forEach((k, v) -> {
            if (v instanceof List) {
                switch (k) {
                    /*or*/
                    case "$or":
                        List<Predicate> orPredicates = Lists.newArrayList();
                        ((List) v).forEach(cvItem -> {
                            List<Predicate> andPredicates = Lists.newArrayList();
                            if (cvItem instanceof Map) {
                                parseFilter(root, cb, (Map<String, Object>) cvItem, andPredicates);
                            }
                            orPredicates.add(cb.and(andPredicates.toArray(new Predicate[0])));
                        });
                        predicates.add(cb.or(orPredicates.toArray(new Predicate[0])));
                        break;
                    /*and*/
                    case "$and":
                        List<Predicate> andAllPredicates = Lists.newArrayList();
                        ((List) v).forEach(cvItem -> {
                            List<Predicate> andPredicates = Lists.newArrayList();
                            if (cvItem instanceof Map) {
                                parseFilter(root, cb, (Map<String, Object>) cvItem, andPredicates);
                            }
                            andAllPredicates.add(cb.and(andPredicates.toArray(new Predicate[0])));
                        });
                        predicates.add(cb.and(andAllPredicates.toArray(new Predicate[0])));
                        break;
                    /*in*/
                    default:
                        CriteriaBuilder.In<Object> in = cb.in(root.get(k));
                        ((List) v).forEach(cv -> {
                            if (root.get(k).getJavaType().isEnum()) {
                                cv = processEnumValueFilter(root.get(k), cv.toString());
                            }
                            in.value(cv);
                        });
                        predicates.add(in);
                        break;
                }

            } else if (v instanceof Map) {
                ((Map) v).forEach((ck, cv) -> {
                    switch (ck.toString()) {
                        case "$start":
                        case "$ge":
                            predicates.add(cb.ge(root.get(k), (Number) cv));
                            break;
                        case "$end":
                        case "$le":
                            predicates.add(cb.le(root.get(k), (Number) cv));
                            break;
                        case "$ne":
                            if (root.get(k).getJavaType().isEnum()) {
                                cv = processEnumValueFilter(root.get(k), cv.toString());
                            }
                            predicates.add(cb.notEqual(root.get(k), cv));
                            break;

                        default:
                            /*子对象属性查询*/
                            parseFilter(root.get(k), cb, (Map<String, Object>) v, predicates);
                            break;
                    }
                });
            } else {
                /*基础属性处理*/
                /*predicate == null时，不加入predicates*/
                getBaseDataPredicate(cb, root.get(k), v).ifPresent(predicates::add);
            }
        });
    }

    protected Optional<Predicate> getBaseDataPredicate(CriteriaBuilder cb, Path<Object> path, Object v) {
        Predicate predicate = null;
        if (v instanceof String) {
            if (StringUtils.isNotEmpty(v.toString())) {
                if ("$null".equals(v)) {
                    predicate = cb.isNull(path);
                } else if (path.getJavaType().isEnum()) {
                    v = processEnumValueFilter(path, v.toString());
                    predicate = cb.equal(path, v);
                } else {
                    /*字符串模糊查询*/
                    if (v.toString().contains("%")) {
                        predicate = cb.like(path.as(String.class), v.toString());
                    } else {
                        /*字符串精确查询*/
                        predicate = cb.equal(path.as(String.class), v.toString());
                    }
                }
            }
        } else if (v != null) {
            predicate = cb.equal(path, v);
        }
        return Optional.ofNullable(predicate);
    }

    protected List<T> beforeReturn(List<T> entities) {
        return entities;
    }

    protected Page<T> beforeReturn(Page<T> page) {
        return page;
    }

}
