package com.example.utils;

import com.example.dao.*;
import com.example.entities.*;
import com.example.enums.Feature;
import com.example.enums.Rating;
import com.example.factories.SessionFactory;
import org.hibernate.Session;
import org.hibernate.mapping.Set;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;

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

    public void customerReturnInventoryToStore(){
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            Rental rental = rentalDAO.getAnyUnreturnedRental();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(rental);
            session.getTransaction().commit();
        }
    }

    public void customerRentInventory(Customer customer){
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            Film film = filmDAO.getFirstAvailableFilmForRent();
            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            Store store = storeDAO.getItems(0, 1).get(0);
            inventory.setStore(store);
            inventoryDAO.save(inventory);
            Staff staff = store.getStaff();
            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setRentalDate(LocalDateTime.now());
            rental.setStaff(staff);
            rentalDAO.save(rental);
            Payment payment = new Payment();
            payment.setRental(rental);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setCustomer(customer);
            payment.setAmount(BigDecimal.valueOf(22.4));
            payment.setStaff(staff);
            paymentDAO.save(payment);
            session.getTransaction().commit();
        }
    }

    public void newFilmWasReleased(){
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            Language language = languageDAO.getItems(0, 2).get(1);

            List<Category> categories = categoryDAO.getItems(0, 3);

            List<Actor> actors = actorDAO.getItems(0, 15);
            HashSet<Actor> actorHashSet = new HashSet<>();
            actorHashSet.addAll(actors);

            Film film = new Film();
            film.setActors(actorHashSet);
            film.setRating(Rating.PG13);

            HashSet<Feature> features = new HashSet<>();
            features.add(Feature.BEHIND_THE_SCENES);
            features.add(Feature.TRAILERS);

            film.setSpecialFeatures(features);
            film.setLength((short)34);
            film.setRentalRate(BigDecimal.ZERO);
            film.setReplacementCost(BigDecimal.TEN);
            film.setLanguage(language);
            film.setDescription("Adventure");
            film.setTitle("Indiana Jones: Lost Ark");
            film.setRentalDuration((byte)19);
            film.setOriginalLanguage(language);
            film.setCategories(new HashSet<>(categories));
            film.setReleaseYear(Year.now());
            filmDAO.save(film);

            FilmText filmText = new FilmText();
            filmText.setFilm(film);
            filmText.setDescription("Adventure");
            filmText.setTitle("Indiana Jones: Lost Ark");
            filmText.setId(film.getId());
            filmTextDAO.save(filmText);

            session.getTransaction().commit();
        }
    }

}
