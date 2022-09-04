package com.cos.blog.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.authenticator.SavedRequest;

import com.cos.blog.domain.board.dto.CommonRespDto;
import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.reply.dto.ReplywriteDto;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.ReplyService;
import com.cos.blog.util.Script;
import com.google.gson.Gson;

@WebServlet("/reply")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReplyController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cmd = request.getParameter("cmd");
		ReplyService replyService = new ReplyService();
		
		if(cmd.equals("replyWriteForm")) {
			BufferedReader br = request.getReader();
			String reqData = br.readLine();
			Gson gson = new Gson();
			ReplywriteDto dto = gson.fromJson(reqData, ReplywriteDto.class);
			
			CommonRespDto<Reply> commonRespDto = new CommonRespDto<>();
			
			Reply reply = null;
			int result = replyService.replywrite(dto);
			if(result != -1) {
				reply = replyService.replyfind(result);
				commonRespDto.setStatusCode(1);
				commonRespDto.setDate(reply);
			} else {
				commonRespDto.setStatusCode(-1);
			}
			
			String responseData = gson.toJson(commonRespDto);
			System.out.println(responseData);
			PrintWriter writer = response.getWriter(); //json 출력
			writer.print(responseData);
			writer.close(); 
			
		} else if(cmd.equals("replyWriteDelete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			int result = replyService.writeDelete(id);
			
			CommonRespDto commonDto = new CommonRespDto();
			commonDto.setStatusCode(result);
			
			Gson gson = new Gson();
			String jsonData = gson.toJson(commonDto);
			PrintWriter writer = response.getWriter();
			writer.print(jsonData);
			writer.close();
		}
	}
}
