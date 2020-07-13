package com.hjg.mybatis;

import com.hjg.mybatis.service.BlogService;
import com.hjg.mybatis.vo.Blog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisAppTest {

    @Autowired
    private BlogService blogService;

    @Test
    public void findTest() {
        String id = "1";
        Blog blog = blogService.selectBlog(id);
        System.out.println("blog = " + blog);
    }
}
