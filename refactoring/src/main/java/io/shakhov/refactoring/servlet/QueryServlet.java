package io.shakhov.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Function;
import java.util.function.Supplier;
import io.shakhov.refactoring.dao.ProductDAO;
import io.shakhov.refactoring.model.Product;
import io.shakhov.refactoring.net.HttpUtils;


public class QueryServlet extends HttpServlet {

    private final ProductDAO productDAO;

    public QueryServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            processProductQuery("<h1>Product with max price: </h1>", productDAO::getProductWithMaxPrice, response);
        } else if ("min".equals(command)) {
            processProductQuery("<h1>Product with min price: </h1>", productDAO::getProductWithMinPrice, response);
        } else if ("sum".equals(command)) {
            processIntQuery("Summary price: ", productDAO::getSummaryPrice, response);
        } else if ("count".equals(command)) {
            processIntQuery("Number of products: ", productDAO::getNumberOfProducts, response);
        } else {
            response.getWriter().println("Unknown command: " + command);
        }
        HttpUtils.htmlOk(response);
    }


    private static void processProductQuery(String header, Supplier<Product> productQuery, HttpServletResponse response) {
        processGenericQuery(header, productQuery, product -> product.name() + "\t" + product.price() + "</br>", response);
    }

    private static void processIntQuery(String header, Supplier<Integer> intQuery, HttpServletResponse response) {
        processGenericQuery(header, intQuery, String::valueOf, response);
    }

    private static <T> void processGenericQuery(String header,
                                                Supplier<T> genericQuery,
                                                Function<T, String> toRepresentation,
                                                HttpServletResponse response) {
        try {
            PrintWriter responseWriter = response.getWriter();
            responseWriter.println("<html><body>");
            responseWriter.println(header);
            T genericValue = genericQuery.get();
            if (genericValue != null) {
                responseWriter.println(toRepresentation.apply(genericValue));
            }
            responseWriter.println("</body></html>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
