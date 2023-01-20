package io.shakhov.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Supplier;
import io.shakhov.refactoring.dao.ProductDAO;
import io.shakhov.refactoring.model.Product;
import io.shakhov.refactoring.response.ResponseCreator;


public class QueryServlet extends HttpServlet {

    private final ProductDAO productDAO;

    public QueryServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            createProductResponse("<h1>Product with max price: </h1>", productDAO::getProductWithMaxPrice, response);
        } else if ("min".equals(command)) {
            createProductResponse("<h1>Product with min price: </h1>", productDAO::getProductWithMinPrice, response);
        } else if ("sum".equals(command)) {
            createIntResponse("Summary price: ", productDAO::getSummaryPrice, response);
        } else if ("count".equals(command)) {
            createIntResponse("Number of products: ", productDAO::getNumberOfProducts, response);
        } else {
            ResponseCreator.rawString("Unknown command: " + command, response);
        }
    }

    private static void createProductResponse(String header, Supplier<Product> productSupplier, HttpServletResponse response) {
        ResponseCreator.genericValue(header, productSupplier, product -> product.name() + "\t" + product.price() + "</br>", response);
    }

    private static void createIntResponse(String header, Supplier<Integer> intSupplier, HttpServletResponse response) {
        ResponseCreator.genericValue(header, intSupplier, String::valueOf, response);
    }
}
