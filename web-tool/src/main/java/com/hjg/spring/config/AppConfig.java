package com.hjg.spring.config;

import com.hjg.spring.model.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/12
 */
@Configuration
public class AppConfig {

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Book book() {
        Book book = new Book();
        return book;
    }


}
