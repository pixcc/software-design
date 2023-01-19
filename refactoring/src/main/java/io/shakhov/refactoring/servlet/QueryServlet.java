package io.shakhov.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import io.shakhov.refactoring.dao.ProductDAO;
import io.shakhov.refactoring.model.Product;


public class QueryServlet extends HttpServlet {

    private final ProductDAO productDAO;

    public QueryServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with max price: </h1>");
                Product productWithMaxPrice = productDAO.getProductWithMaxPrice();
                if (productWithMaxPrice != null) {
                    response.getWriter().println(productWithMaxPrice.name() + "\t" + productWithMaxPrice.price() + "</br>");

                }
                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with min price: </h1>");
                Product productWithMinPrice = productDAO.getProductWithMinPrice();
                if (productWithMinPrice != null) {
                    response.getWriter().println(productWithMinPrice.name() + "\t" + productWithMinPrice.price() + "</br>");
                }
                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Summary price: ");
                Integer summaryPrice = productDAO.getSummaryPrice();
                if (summaryPrice != null) {
                    response.getWriter().println(summaryPrice);
                }
                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Number of products: ");
                Integer numberOfProducts = productDAO.getNumberOfProducts();
                if (numberOfProducts != null) {
                    response.getWriter().println(numberOfProducts);
                }
                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
