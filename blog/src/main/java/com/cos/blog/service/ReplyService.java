package com.cos.blog.service;

import java.util.List;

import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.reply.ReplyDao;
import com.cos.blog.domain.reply.dto.ReplywriteDto;

public class ReplyService {
	
	private ReplyDao replyDao;

	public ReplyService() {
		replyDao = new ReplyDao();
	}

	public int replywrite(ReplywriteDto dto) {
		return ReplyDao.replyInsert(dto);
	}

	public Reply replyfind(int id) {
		return replyDao.findById(id);
	}

	public List<Reply> watchWriteList(int boardId) {
		return replyDao.findAll(boardId);
	}

	public int writeDelete(int id) {
		return replyDao.deleteById(id);
	}
	
}
