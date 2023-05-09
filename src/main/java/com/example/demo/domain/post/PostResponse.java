package com.example.demo.domain.post;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class PostResponse {
	private Long id;						//PK
	private String title;					//제목
	private String content;					//내용
	private String writer;					//작성자
	private int viewCnt;					//조회수
	private Boolean deleteYn;				//삭제여부
	private LocalDateTime createdDate;		//생성일시
	private LocalDateTime modifiedDate;		//최종수정일시
}
