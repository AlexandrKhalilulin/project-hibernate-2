package com.example;

import com.example.factories.SessionFactory;
import com.example.utils.ManagerDAO;

public class Runner {
    public static void main(String[] args) {
        ManagerDAO.getManagerDAO().createCustomer();
    }
}
