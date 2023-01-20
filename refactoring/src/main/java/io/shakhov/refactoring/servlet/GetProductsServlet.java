package io.shakhov.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.shakhov.refactoring.dao.ProductDAO;
import io.shakhov.refactoring.model.Product;
import io.shakhov.refactoring.net.HttpUtils;


public class GetProductsServlet extends HttpServlet {

    private final ProductDAO productDAO;

    public GetProductsServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().println("<html><body>");
            for (Product product : productDAO.getProducts()) {
                response.getWriter().println(product.name() + "\t" + product.price() + "</br>");
            }
            response.getWriter().println("</body></html>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HttpUtils.htmlOk(response);
    }
}
