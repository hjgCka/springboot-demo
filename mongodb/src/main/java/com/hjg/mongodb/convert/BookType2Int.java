package com.hjg.mongodb.convert;

import com.hjg.mongodb.constant.BookType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class BookType2Int implements Converter<BookType, Integer> {
    @Override
    public Integer convert(BookType bookType) {
        return bookType.getType();
    }
}
