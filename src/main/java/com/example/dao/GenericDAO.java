package com.example.dao;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class GenericDAO<T> {
    private final Class<T> entityClass;
    private SessionFactory sessionFactory;

    public GenericDAO(Class<T> entityToSet, SessionFactory sessionFactory) {
        this.entityClass = entityToSet;
        this.sessionFactory = sessionFactory;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public T getById(final int id) {
        return (T) getCurrentSession().get(entityClass, id);
    }

    public List<T> getItems(int offset, int count) {
        Query query = getCurrentSession().createQuery("from " + entityClass.getName(), entityClass);
        query.setFirstResult(offset);
        query.setMaxResults(count);
        return query.getResultList();
    }

    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + entityClass.getName(), entityClass).list();
    }

    public T save(final T entity) {
        getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    public T update(final T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    public void delete(final T entity) {
        getCurrentSession().delete(entity);
    }

    public void deleteById(final int entityId) {
        final T entity = getById(entityId);
        delete(entity);
    }
}
