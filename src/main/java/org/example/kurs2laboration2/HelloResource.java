package org.example.kurs2laboration2;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.util.List;

@Path("/")
public class HelloResource {
    @GET
    @Path("/hello")
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

    @GET
    @Path("/names")
    @Produces("Application/json")
    public List<String> names() {
        return List.of("Alice", "Bob", "Charlie");

    }
}