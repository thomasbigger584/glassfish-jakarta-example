package com.twb.bookapp.mapper;

import com.twb.bookapp.dto.BookDTO;
import com.twb.bookapp.dto.CreateBookDTO;
import com.twb.bookapp.entity.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    Book createBookDtoToBook(CreateBookDTO createDto);

    BookDTO bookToBookDto(Book book);
}
