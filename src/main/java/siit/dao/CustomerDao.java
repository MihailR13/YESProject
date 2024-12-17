package siit.dao;

import siit.config.DataBaseManager;
import siit.model.Customer;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM customers")
        ) {
            while (rs.next()) {
                customers.add(extractCustomer(rs));
            }

        } catch (SQLException e) {
            //oops
        }

        return customers;
    }

    private Customer extractCustomer(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String phone = rs.getString("phone");
        String email = rs.getString("email");
        LocalDate birthDate = rs.getDate("birthday").toLocalDate();

        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setBirthDate(birthDate);

        return customer;
    }

    public Customer getCustomer(int customerId) {

        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM customers WHERE id = ?");
        ) {
            stmt.setInt(1, customerId);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            return extractCustomer(rs);


        } catch (SQLException e) {
            //oops
        }

        return null;
    }

    public void update(Customer updatedCustomer) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("UPDATE customers SET name = ?, phone = ?, birthday = ? WHERE id = ?");
        ) {
            stmt.setString(1, updatedCustomer.getName());
            stmt.setString(2, updatedCustomer.getPhone());
            stmt.setDate(3, Date.valueOf(updatedCustomer.getBirthDate()));
            stmt.setInt(4, updatedCustomer.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            //oops
        }

    }
}
