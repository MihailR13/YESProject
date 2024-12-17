package siit.dao;

import siit.config.DataBaseManager;
import siit.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    public List<Product> getProductsBy(String term) {
        List<Product> products = new ArrayList<>();

        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM products WHERE LOWER(name) LIKE ? ")
        ) {
            stmt.setString(1, "%" + term.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(extractProduct(rs));
            }
        } catch (SQLException e) {
            //oops
        }

        return products;
    }

    private Product extractProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setWeight(rs.getBigDecimal("weight"));

        return product;
    }
}
