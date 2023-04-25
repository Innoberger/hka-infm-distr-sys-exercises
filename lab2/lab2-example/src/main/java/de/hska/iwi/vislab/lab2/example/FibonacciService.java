package de.hska.iwi.vislab.lab2.example;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("fibonacci")
public class FibonacciService {

    public int getFibonacci(int n) {
        if (n < 0)
            throw new IllegalArgumentException(String.format("n must >= 0, got %s", n));

        if (n == 0)
            return 0;

        if (n < 3)
            return 1;

        return getFibonacci(n - 1) + getFibonacci(n - 2);
    }

    @GET
    @Path("/{n}")
    @Produces(MediaType.TEXT_PLAIN)
    public String fibonacci(@DefaultValue("0") @PathParam("n") int n) {
        return String.format("fibonacci(%s) = %s", n, getFibonacci(n));
    }

    @GET
    @Path("/next")
    @Produces(MediaType.TEXT_PLAIN)
    public Response nextFibonacci(@CookieParam("FibonacciIndex") Cookie cookie) {
        if (cookie == null) {
            NewCookie fibonacciIndex = new NewCookie("FibonacciIndex", "0");
            Response.ResponseBuilder rb = Response.ok(String.format("fibonacci(%s) = %s", 0, getFibonacci(0)));

            return rb.cookie(fibonacciIndex).build();
        }

        int nextIndex = Integer.parseInt(cookie.getValue()) + 1;

        NewCookie fibonacciIndex = new NewCookie("FibonacciIndex", String.valueOf(nextIndex));
        Response.ResponseBuilder rb = Response.ok(String.format("fibonacci(%s) = %s", nextIndex, getFibonacci(nextIndex)));

        return rb.cookie(fibonacciIndex).build();
    }

}
