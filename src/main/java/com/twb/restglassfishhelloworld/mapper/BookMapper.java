package com.twb.restglassfishhelloworld.mapper;

import com.twb.restglassfishhelloworld.dto.BookDTO;
import com.twb.restglassfishhelloworld.dto.CreateBookDTO;
import com.twb.restglassfishhelloworld.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book createBookDtoToBook(CreateBookDTO createDto);
    BookDTO bookToBookDto(Book book);
}
