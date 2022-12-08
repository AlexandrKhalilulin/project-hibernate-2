package com.example.utils;

import com.example.dao.*;
import com.example.entities.*;
import com.example.factories.SessionFactory;
import org.hibernate.Session;

public class ManagerDAO {
    private static ManagerDAO instance;
    private final org.hibernate.SessionFactory sessionFactory;
    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    public ManagerDAO() {
        this.sessionFactory = SessionFactory.getSessionFactory();
        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        filmTextDAO = new FilmTextDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);
    }

    public static ManagerDAO getManagerDAO() {
        if (instance == null) {
            instance = new ManagerDAO();
        }
        return instance;
    }

    public Customer createCustomer(String firstName, String lastName, String currentAddress, String district, String phone, String currentCity){
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            Store store = storeDAO.getItems(0, 1).get(0);
            City city = cityDAO.getByName(currentCity);
            Address address = new Address();
            address.setAddress(currentAddress);
            address.setDistrict(district);
            address.setCity(city);
            address.setPhone(phone);
            addressDAO.save(address);
            Customer customer = new Customer();
            customer.setAddress(address);
            customer.setActive(true);
            customer.setStore(store);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customerDAO.save(customer);
            session.getTransaction().commit();
            return customer;
        }
    }

}
