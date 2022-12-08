package com.example;

import com.example.entities.Customer;
import com.example.factories.SessionFactory;
import com.example.utils.ManagerDAO;

public class Runner {
    public static void main(String[] args) {
        // Customer customer = ManagerDAO.getManagerDAO().createCustomer("Ivana", "Ivanova", "Rush str, 35",
        //        "Gulder", "50-00-53", "Ivanovo");
        // ManagerDAO.getManagerDAO().customerReturnInventoryToStore();
        // ManagerDAO.getManagerDAO().customerRentInventory(customer);
        ManagerDAO.getManagerDAO().newFilmWasReleased();

    }
}
