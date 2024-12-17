package siit.dao;

import siit.config.DataBaseManager;
import siit.model.OrderProduct;
import siit.model.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderProductDao {
    public List<OrderProduct> getOrderProducts(int customerId, int orderId) {
        List<OrderProduct> orderProducts = new ArrayList<>();

        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT op.order_id, op.quantity, p.name, op.quantity * p.price AS VALUE, p.id AS product_id, p.weight, p.price "
                                + "FROM orders_products op "
                                + "JOIN orders o ON o.id = op.order_id "
                                + "JOIN products p ON p.id = op.product_id "
                                + "WHERE o.customer_id = ? AND op.order_id = ? "
                );

        ) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orderProducts.add(extractOrderProduct(rs));
            }

        } catch (SQLException e) {
            //oops
        }

        return orderProducts;
    }

    private OrderProduct extractOrderProduct(ResultSet rs) throws SQLException {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(rs.getInt("order_id"));
        orderProduct.setQuantity(rs.getBigDecimal("quantity"));
        orderProduct.setValue(rs.getBigDecimal("value"));

        Product product = new Product();
        product.setId(rs.getInt("product_id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setWeight(rs.getBigDecimal("weight"));

        orderProduct.setProduct(product);

        return orderProduct;
    }

    public OrderProduct findBy(int orderId, int productId) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT op.order_id, op.quantity, p.name, op.quantity * p.price AS VALUE, p.id AS product_id, p.weight, p.price "
                                + "FROM orders_products op "
                                + "JOIN products p ON p.id = op.product_id  "
                                + "WHERE op.order_id = ? and op.product_id = ? "
                );

        ) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return extractOrderProduct(rs);
        } catch (SQLException e) {
            //oops
        }

        return null;
    }

    public void addOrderProduct(int orderId, int productId, BigDecimal quantity) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement(
                        "INSERT INTO orders_products (order_id, product_id, quantity) VALUES (?, ?, ?)"
                );

        ) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setBigDecimal(3, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            //oops
        }
    }

    public void updateOrderProduct(int orderId, int productId, BigDecimal totalQuantity) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement(
                        "UPDATE orders_products SET quantity = ? WHERE order_id = ? AND product_id = ?"
                );

        ) {
            stmt.setBigDecimal(1, totalQuantity);
            stmt.setInt(2, orderId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            //oops
        }
    }

    public void deleteBy(int orderId, int productId) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement(
                        "DELETE FROM orders_products WHERE order_id = ? AND product_id = ?"
                );

        ) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
//            connection.commit();
        } catch (SQLException e) {
            //oops
        }
    }
}
