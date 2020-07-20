package com.hjg.mongodb.constant;

import lombok.Getter;

public enum BookType {
    TECH(1), FINANCE(2), NOVEL(3), ENGLISH(4);

    @Getter
    private int type;

    BookType(int type) {
        this.type = type;
    }

    public static BookType getBookType(int source) {
        for(BookType bookType : BookType.values()) {
            if(bookType.getType() == source) {
                return bookType;
            }
        }
        return null;
    }
}
