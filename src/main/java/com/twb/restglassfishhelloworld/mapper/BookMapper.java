package com.twb.restglassfishhelloworld.mapper;

import com.twb.restglassfishhelloworld.dto.BookDTO;
import com.twb.restglassfishhelloworld.dto.CreateBookDTO;
import com.twb.restglassfishhelloworld.entity.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    Book createBookDtoToBook(CreateBookDTO createDto);

    BookDTO bookToBookDto(Book book);
}
