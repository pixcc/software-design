package io.shakhov.refactoring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

        }
    }

    public void addProduct(Product product) {
        try (Connection c = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + product.name() + "\"," + product.price() + ")";
            try (Statement stmt = c.createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
