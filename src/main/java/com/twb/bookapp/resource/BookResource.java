package com.twb.bookapp.resource;

import com.twb.bookapp.aop.logging.Logged;
import com.twb.bookapp.dto.BookDTO;
import com.twb.bookapp.dto.CreateBookDTO;
import com.twb.bookapp.service.BookService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/book")
public class BookResource {
    @Inject
    private BookService bookService;

    @POST
    @Logged
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createBook(CreateBookDTO dto) {
        BookDTO book = bookService.createBook(dto);
        return Response.status(Response.Status.CREATED)
                .entity(book).build();
    }

    @GET
    @Logged
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBookById(@PathParam("id") Long id) {
        BookDTO book = bookService.getBookById(id);
        return Response.status(Response.Status.OK)
                .entity(book).build();
    }

    @GET
    @Logged
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBooks() {
        List<BookDTO> books = bookService.getBooks();
        return Response.status(Response.Status.OK)
                .entity(books).build();
    }

    @DELETE
    @Logged
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        bookService.deleteBook(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}