package com.hjg.mybatis;

import com.hjg.mybatis.service.BlogService;
import com.hjg.mybatis.vo.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MybatisAppTest {

    @Autowired
    private BlogService blogService;

    @Test
    public void findTest() {
        String id = "1";
        Blog blog = blogService.getBlogById(id);
        System.out.println("blog = " + blog);
    }
}
