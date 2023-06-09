package com.twb.restglassfishhelloworld.resource;

import com.twb.restglassfishhelloworld.aop.logging.Logged;
import com.twb.restglassfishhelloworld.dto.BookDTO;
import com.twb.restglassfishhelloworld.dto.CreateBookDTO;
import com.twb.restglassfishhelloworld.service.BookService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/book")
public class BookResource {
    @Inject
    private BookService bookService;

    @POST
    @Logged
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createBook(CreateBookDTO createDto) {
        BookDTO book = bookService.createBook(createDto);
        return Response.status(Response.Status.CREATED)
                .entity(book).build();
    }
}