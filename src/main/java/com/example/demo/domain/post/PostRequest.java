package com.example.demo.domain.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
	private Long id;			//PK
	private String title;		//제목
	private String content;		//내용
	private String writer;		//작성자
}
