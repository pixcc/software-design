package io.shakhov.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import io.shakhov.refactoring.dao.ProductDAO;
import io.shakhov.refactoring.model.Product;
import io.shakhov.refactoring.net.HttpUtils;


public class AddProductServlet extends HttpServlet {

    private final ProductDAO productDAO;

    public AddProductServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        productDAO.addProduct(new Product(name, price));
        response.getWriter().println("OK");
        HttpUtils.htmlOk(response);
    }
}
