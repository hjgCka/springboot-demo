package com.hjg.mybatis.service.impl;

import com.hjg.mybatis.mapper.BlogMapper;
import com.hjg.mybatis.service.BlogService;
import com.hjg.mybatis.vo.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    @Transactional
    public Blog selectBlog(String id) {
        return blogMapper.findById(id);
    }
}
