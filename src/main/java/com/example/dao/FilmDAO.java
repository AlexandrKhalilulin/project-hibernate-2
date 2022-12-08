package com.example.dao;

import com.example.entities.Film;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class FilmDAO extends GenericDAO<Film>{
    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public Film getFirstAvailableFilmForRent() {
        Query<Film> query = getCurrentSession().createQuery("select f from Film f where f.film_id", Film.class);

    }
}
