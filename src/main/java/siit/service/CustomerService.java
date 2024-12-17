package siit.service;

import siit.dao.CustomerDao;
import siit.model.Customer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService() {
        this.customerDao = new CustomerDao();
    }

    public List<Customer> getCustomers() {
        return customerDao.getAllCustomers().stream()
                .sorted(Comparator.comparing(Customer::getId))
                .collect(Collectors.toList());
    }

    public Customer getCustomerById(int customerId) {
        if (customerId <= 0) {
            return null;
        }

        return customerDao.getCustomer(customerId);
    }

    public void updateCustomer(Customer updatedCustomer) {
        // daca numarul de telefon respecta formatul corespunzator
        // daca numele contine doar litere si caractere speciale ...
        // daca data de nastere este valida (1800 nu este o data de nastere valida)

        customerDao.update(updatedCustomer);
    }
}
