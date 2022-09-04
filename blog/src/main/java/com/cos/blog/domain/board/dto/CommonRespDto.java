package com.cos.blog.domain.board.dto;

import lombok.Data;

@Data
public class CommonRespDto<T> { //공통 json 객체 받는 Dto
	private int statusCode;
	private T date; // 제네릭 타입
}
