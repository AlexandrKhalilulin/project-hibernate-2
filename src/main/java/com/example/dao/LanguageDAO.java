package com.example.dao;

import com.example.dao.GenericDAO;
import com.example.entities.Language;
import org.hibernate.SessionFactory;

public class LanguageDAO extends GenericDAO<Language> {
    public LanguageDAO(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }
}
