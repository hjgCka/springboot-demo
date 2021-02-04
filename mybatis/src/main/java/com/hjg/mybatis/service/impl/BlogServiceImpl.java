package com.hjg.mybatis.service.impl;

import com.hjg.mybatis.mapper.BlogMapper;
import com.hjg.mybatis.service.BlogService;
import com.hjg.mybatis.vo.Blog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogServiceImpl implements BlogService {

    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    private BlogMapper blogMapper;

    @Override
    @Transactional(readOnly = true)
    public Blog getBlogById(String id) {
        Blog blog = blogMapper.findById(id);
        logger.info("blog = {}", blog);
        return blog;
    }
}
