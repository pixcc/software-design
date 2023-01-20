package io.shakhov.refactoring.net;

import javax.servlet.http.HttpServletResponse;

public final class HttpUtils {

    private HttpUtils() {
    }

    public static void htmlOk(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
