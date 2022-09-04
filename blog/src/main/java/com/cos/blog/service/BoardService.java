package com.cos.blog.service;

import java.util.List;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.BoardDao;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.UpdateReqDto;
import com.cos.blog.domain.board.dto.WriteReqDto;

public class BoardService {
	
	private BoardDao boardDao;
	
	public BoardService() {
		boardDao = new BoardDao();
	}

	public int write(WriteReqDto dto) {
		int result = boardDao.insert(dto);
		return result;
	}

	public List<Board> writeList(int page) {
		return boardDao.seletList(page);
	}

	public  int writeCount() {
		return boardDao.count();
	}
	
	public int writeCount(String keyword) {
		return boardDao.count(keyword);
	}
	
	//하나의 서비스안에 여러가지 DB관련 로직 구현
	public DetailRespDto writeimpo(int id) {
		int result = boardDao.updateCount(id);
		
		if(result == 1) {
			return boardDao.findById(id);
		} else { 
			return null;
		}
	}

	public int deletebtn(int id) {
		return boardDao.deleteById(id);
	}

	public int writeupdate(UpdateReqDto dto) {
		return boardDao.updateById(dto);
	}

	public List<Board> writeClick(String keyword, int page) {
		return boardDao.findByKeyword(keyword, page);
	}

}
