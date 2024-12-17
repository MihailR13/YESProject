package siit.dao;

import siit.config.DataBaseManager;
import siit.model.Order;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    public List<Order> getOrdersBy(int customerId) {
        List<Order> orders = new ArrayList<>();

        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM orders WHERE customer_id = ?");
        ) {
            stmt.setInt(1, customerId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(extractOrder(rs));
            }

        } catch (SQLException e) {
            //oops
        }

        return orders;
    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String number = rs.getString("number");
        LocalDateTime placed = rs.getTimestamp("placed").toLocalDateTime();

        Order order = new Order();
        order.setId(id);
        order.setValue(0.0);
        order.setNumber(number);
        order.setPlaced(placed);

        return order;
    }

    public void insert(int customerId, Order updatedOrder) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO orders (number, placed, customer_id) VALUES (?,?,?)");
        ) {
            stmt.setString(1, updatedOrder.getNumber());
            stmt.setTimestamp(2, Timestamp.valueOf(updatedOrder.getPlaced().truncatedTo(ChronoUnit.SECONDS)));
            stmt.setInt(3, customerId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            //oops
        }
    }

    public void delete(int orderId) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM orders WHERE id = ?");
        ) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            //oops
        }
    }

    public Order getOrderBy(int orderId) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM orders WHERE id = ?");
        ) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return extractOrder(rs);
        } catch (SQLException e) {
            //oops
        }

        return null;
    }
}
