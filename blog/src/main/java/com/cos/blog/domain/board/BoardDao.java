package com.cos.blog.domain.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cos.blog.config.DB;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.UpdateReqDto;
import com.cos.blog.domain.board.dto.WriteReqDto;
import com.cos.blog.domain.reply.Reply;

public class BoardDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public List<Board> seletList(int page) {
		
		try {
			String sql = "SELECT * FROM board ORDER BY id DESC LIMIT ?, 4";
			conn = DB.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page * 4);
			rs = pstmt.executeQuery();
			List<Board> boardLIst = new ArrayList<>();

			while (rs.next()) {
				Board board = Board.builder()
						.id(rs.getInt("id"))
						.title(rs.getString("title"))
						.content(rs.getString("content"))
						.readCount(rs.getInt("readCount"))
						.userId(rs.getInt("userId"))
						.createDate(rs.getTimestamp("createDate"))
						.build();
				boardLIst.add(board);
			}
			return boardLIst;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return null;
	}

	public int insert(WriteReqDto dto) {
		
		try {
			String sql = "INSERT INTO board(userId, title, content, createDate) VALUES (?,?,?,now())";
			conn = DB.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserId());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			int result = pstmt.executeUpdate();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}

		return -1;
	}

	public DetailRespDto findById(int id) {
		StringBuffer sb = new StringBuffer();
		sb.append("select b.id, b.title, b.content, b.readCount, b.userId, u.username\r\n"
				+ "from \r\n"
				+ "	board b \r\n"
				+ "inner join \r\n"
				+ "	user u \r\n"
				+ "on \r\n"
				+ "	b.userId = u.id\r\n"
				+ "where b.id = ?");
		
		try {
			String sql = sb.toString();
			conn = DB.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { 
				DetailRespDto dto = new DetailRespDto();
				dto.setId(rs.getInt("b.id"));
				dto.setTitle(rs.getString("b.title"));
				dto.setContent(rs.getString("b.content"));
				dto.setReadCount(rs.getInt("b.readCount"));
				dto.setUserId(rs.getInt("userId"));
				dto.setUsername(rs.getString("u.username"));
				return dto;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return null;
	}

	public int updateCount(int id) {
		try {
			String sql = "UPDATE board SET readCount = readCount + 1 WHERE id = ?";
			conn = DB.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		
		return -1;
	}

	public int deleteById(int id) {
		try {
			String sql = "DELETE FROM board WHERE id = ?";
			conn = DB.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		
		return -1;
	}

	public int updateById(UpdateReqDto dto) {
		try {
			String sql = "UPDATE board SET title = ?, content = ? WHERE id = ?";
			conn = DB.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getId());
			int result = pstmt.executeUpdate();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		
		return -1;
	}

	public List<Board> findByKeyword(String keyword, int page) {
		List<Board> boardList = new ArrayList<>();
		
		try {
			String sql = "SELECT * FROM board WHERE title like ? ORDER BY id DESC LIMIT ?, 4";
			conn = DB.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, page * 4);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Board board = Board.builder()
						.id(rs.getInt("id"))
						.title(rs.getString("title"))
						.content(rs.getString("content"))
						.readCount(rs.getInt("readCount"))
						.userId(rs.getInt("userId"))
						.createDate(rs.getTimestamp("createDate"))
						.build();
				boardList.add(board);
			}
			return boardList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return null;
	}

	public int count() {
		try {
			String sql = "SELECT count(*) FROM board";
			conn = DB.getConnetion();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		
		return -1;
	}
	
	public int count(String keyword) {
		try {
			String sql = "SELECT count(*) FROM board WHERE title like ?";
			conn = DB.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();

			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		
		return -1;
	}

	
}
