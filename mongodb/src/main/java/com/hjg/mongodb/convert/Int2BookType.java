package com.hjg.mongodb.convert;

import com.hjg.mongodb.constant.BookType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class Int2BookType implements Converter<Integer, BookType> {
    @Override
    public BookType convert(Integer source) {
        return BookType.getBookType(source);
    }
}
