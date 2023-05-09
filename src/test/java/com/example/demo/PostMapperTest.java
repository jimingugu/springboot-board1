package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.post.PostMapper;

@SpringBootTest
public class PostMapperTest {
	
	@Autowired
	PostMapper postMapper;
	
	@Test
	void delete() {
		System.out.println("삭제 이전의 전체 게시글 개수 : " + postMapper.findAll().size());
		postMapper.deleteById(1L);
		System.out.println("삭제 이전의 전체 게시글 개수 : " + postMapper.findAll().size());
	}
}
