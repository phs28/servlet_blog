package com.cos.blog.domain.user;

import java.lang.annotation.Retention;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cos.blog.config.DB;
import com.cos.blog.domain.user.dto.JoinReqDto;
import com.cos.blog.domain.user.dto.LoginReqDto;
import com.cos.blog.util.Script;

public class UserDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs;
	
	public User findByUsernameAndPassword(LoginReqDto dto) {
		
		try {
			String sql = "SELECT id, username, email, address  FROM user WHERE username = ? AND password = ?";
			conn = DB.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				User user = User.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.address(rs.getString("address"))
						.build();
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public int findByUsername(String username) {
		
		try {
			String sql = "SELECT * FROM user WHERE username = ?";
			conn = DB.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}

		return -1;
	}
	
	public int insert(JoinReqDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs;
		
		try {
			String sql = "INSERT INTO user(username,password,email,address,userRole,createDate) VALUES (?,?,?,?,'USER',now())";
			conn = DB.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getAddress());
			int result = pstmt.executeUpdate();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}

		return -1;
	}

}
