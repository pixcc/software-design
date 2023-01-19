package io.shakhov.refactoring;

import io.shakhov.refactoring.dao.ProductDAO;
import io.shakhov.refactoring.servlet.AddProductServlet;
import io.shakhov.refactoring.servlet.GetProductsServlet;
import io.shakhov.refactoring.servlet.QueryServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class Main {
    public static void main(String[] args) throws Exception {
        ProductDAO productDAO = new ProductDAO();
        productDAO.init();
        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(productDAO)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(productDAO)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(productDAO)), "/query");

        server.start();
        server.join();
    }
}
