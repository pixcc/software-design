package io.shakhov.refactoring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import io.shakhov.refactoring.model.Product;

public class ProductDAO {

    public static final String DB_URL = "jdbc:sqlite:test.db";

    public void init() {
        try (Connection c = DriverManager.getConnection(DB_URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS product" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " name           TEXT    NOT NULL, " +
                    " price          INT     NOT NULL)";
            try (Statement stmt = c.createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProduct(Product product) {
        try (Connection c = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + product.name() + "\"," + product.price() + ")";
            try (Statement stmt = c.createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(DB_URL)) {
            try (Statement stmt = c.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM product");
                while (rs.next()) {
                    String name = rs.getString("name");
                    long price = rs.getLong("price");
                    products.add(new Product(name, price));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
