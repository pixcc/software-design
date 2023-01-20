package io.shakhov.refactoring.response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ResponseCreator {

    private ResponseCreator() {
    }

    public static <T> void genericValue(String header,
                                        Supplier<T> genericValueSupplier,
                                        Function<T, String> toRepresentation,
                                        HttpServletResponse response) {
        T genericValue = genericValueSupplier.get();
        List<T> genericValueList;
        if (genericValue != null) {
            genericValueList = List.of(genericValue);
        } else {
            genericValueList = List.of();
        }
        genericList(header, () -> genericValueList, toRepresentation, response);
    }

    public static <T> void genericList(String header,
                                       Supplier<List<T>> genericListSupplier,
                                       Function<T, String> toRepresentation,
                                       HttpServletResponse response) {
        try {
            PrintWriter responseWriter = response.getWriter();
            responseWriter.println("<html><body>");
            if (!header.isEmpty()) {
                responseWriter.println(header);
            }
            for (T genericValue : genericListSupplier.get()) {
                responseWriter.println(toRepresentation.apply(genericValue));
            }
            responseWriter.println("</body></html>");
            htmlOk(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void rawString(String string, HttpServletResponse response) throws IOException {
        response.getWriter().println(string);
        htmlOk(response);
    }

    private static void htmlOk(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
