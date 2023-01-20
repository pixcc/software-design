package io.shakhov.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.shakhov.refactoring.dao.ProductDAO;
import io.shakhov.refactoring.response.ResponseCreator;


public class GetProductsServlet extends HttpServlet {

    private final ProductDAO productDAO;

    public GetProductsServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ResponseCreator.genericList("", productDAO::getProducts, product -> product.name() + "\t" + product.price() + "</br>", response);
    }
}
