package io.shakhov.refactoring;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductsTest {

    private final static HttpClient httpClient = HttpClient.newHttpClient();

    @BeforeClass
    public static void init() throws InterruptedException {
        Thread productsApp = new Thread(() -> {
            try {
                Main.main(new String[0]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        productsApp.start();
        // wait for app initialization
        Thread.sleep(3000);
    }

    @After
    public void tearDown() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
             Statement stmt = c.createStatement()) {
            stmt.executeUpdate("DELETE FROM product WHERE 1");
        }
    }

    @Test
    public void testGetProducts() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:8081/get-products")).GET().build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertHtmlContentType(response);
        assertEquals(
                "<html><body>\r\n" +
                        "</body></html>\r\n",
                response.body()
        );

        addProduct("iphone", 100);
        addProduct("iphone", 100);
        addProduct("samsung", 200);
        addProduct("iphone", 300);

        request = HttpRequest.newBuilder(URI.create("http://localhost:8081/get-products")).GET().build();
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertHtmlContentType(response);
        assertEquals(
                "<html><body>\r\n" +
                        "iphone\t100</br>\r\n" +
                        "iphone\t100</br>\r\n" +
                        "samsung\t200</br>\r\n" +
                        "iphone\t300</br>\r\n" +
                        "</body></html>\r\n",
                response.body()
        );
    }

    @Test
    public void testMinQuery() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:8081/query?command=min")).GET().build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertHtmlContentType(response);
        assertEquals(
                "<html><body>\r\n" +
                        "<h1>Product with min price: </h1>\r\n" +
                        "</body></html>\r\n",
                response.body()
        );

        addProduct("iphone", 100);
        addProduct("iphone", 100);
        addProduct("samsung", 200);

        request = HttpRequest.newBuilder(URI.create("http://localhost:8081/query?command=min")).GET().build();
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertHtmlContentType(response);
        assertEquals(
                "<html><body>\r\n" +
                        "<h1>Product with min price: </h1>\r\n" +
                        "iphone\t100</br>\r\n" +
                        "</body></html>\r\n",
                response.body()
        );
    }

    @Test
    public void testCountQuery() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:8081/query?command=count")).GET().build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertHtmlContentType(response);
        assertEquals(
                "<html><body>\r\n" +
                        "Number of products: \r\n" +
                        "0\r\n" +
                        "</body></html>\r\n",
                response.body()
        );

        addProduct("iphone", 100);
        addProduct("iphone", 100);
        addProduct("samsung", 200);
        addProduct("iphone", 300);

        request = HttpRequest.newBuilder(URI.create("http://localhost:8081/query?command=count")).GET().build();
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertHtmlContentType(response);
        assertEquals(
                "<html><body>\r\n" +
                        "Number of products: \r\n" +
                        "4\r\n" +
                        "</body></html>\r\n",
                response.body()
        );
    }

    @Test
    public void testMaxQuery() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:8081/query?command=max")).GET().build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertHtmlContentType(response);
        assertEquals(
                "<html><body>\r\n" +
                        "<h1>Product with max price: </h1>\r\n" +
                        "</body></html>\r\n",
                response.body()
        );

        addProduct("iphone", 100);
        addProduct("iphone", 100);
        addProduct("samsung", 200);

        request = HttpRequest.newBuilder(URI.create("http://localhost:8081/query?command=max")).GET().build();
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertHtmlContentType(response);
        assertEquals(
                "<html><body>\r\n" +
                        "<h1>Product with max price: </h1>\r\n" +
                        "samsung\t200</br>\r\n" +
                        "</body></html>\r\n",
                response.body()
        );
    }

    @Test
    public void testSumQuery() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:8081/query?command=sum")).GET().build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertHtmlContentType(response);
        assertEquals(
                "<html><body>\r\n" +
                        "Summary price: \r\n" +
                        "0\r\n" +
                        "</body></html>\r\n",
                response.body()
        );

        addProduct("iphone", 100);
        addProduct("iphone", 100);
        addProduct("samsung", 200);

        request = HttpRequest.newBuilder(URI.create("http://localhost:8081/query?command=sum")).GET().build();
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertHtmlContentType(response);
        assertEquals(
                "<html><body>\r\n" +
                        "Summary price: \r\n" +
                        "400\r\n" +
                        "</body></html>\r\n",
                response.body()
        );
    }

    @Test
    public void testUnknownQuery() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:8081/query?command=abc")).GET().build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertHtmlContentType(response);
        assertEquals("Unknown command: abc\r\n", response.body());
    }

    @Test
    public void testWrongEndpoint() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:8081/products")).GET().build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
    }

    private void addProduct(String name, int price) throws IOException, InterruptedException {
        URI uri = URI.create(String.format("http://localhost:8081/add-product?name=%s&price=%d", name, price));
        var request = HttpRequest.newBuilder(uri).GET().build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertHtmlContentType(response);
        assertEquals("OK\r\n", response.body());
    }

    private static void assertHtmlContentType(HttpResponse<String> response) {
        String contentType = response.headers()
                .firstValue("Content-Type")
                .get();
        assertTrue(contentType.contains("text/html"));
    }
}
