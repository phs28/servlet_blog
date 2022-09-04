package com.cos.blog.domain.reply.dto;

import lombok.Data;

@Data
public class ReplywriteDto {
	private int userId;
	private int boardId;
	private String content;
}

