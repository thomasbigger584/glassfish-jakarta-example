package com.twb.restglassfishhelloworld.resource;

import com.twb.restglassfishhelloworld.aop.logging.Logged;
import com.twb.restglassfishhelloworld.dto.HelloDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Logged
    @Produces({MediaType.APPLICATION_JSON})
    public HelloDTO hello() {
        return new HelloDTO("Hello, World!");
    }
}