package com.example.dao;

import com.example.dao.GenericDAO;
import com.example.entities.Rental;
import org.hibernate.SessionFactory;

public class RentalDAO extends GenericDAO<Rental> {
    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }
}
