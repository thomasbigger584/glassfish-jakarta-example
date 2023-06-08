package com.twb.restglassfishhelloworld.resource;

import com.twb.restglassfishhelloworld.aop.logging.Logged;
import com.twb.restglassfishhelloworld.dto.HelloDTO;
import com.twb.restglassfishhelloworld.service.HelloService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello-world")
public class HelloResource {

    @Inject
    @Named("aws")
    private HelloService awsHelloService;

    @Inject
    @Named("azure")
    private HelloService azureHelloService;

    @GET
    @Path("/aws")
    @Logged
    @Produces({MediaType.APPLICATION_JSON})
    public HelloDTO awsHello() {
        return awsHelloService.getHello();
    }

    @GET
    @Path("/azure")
    @Logged
    @Produces({MediaType.APPLICATION_JSON})
    public HelloDTO azureHello() {
        return azureHelloService.getHello();
    }
}