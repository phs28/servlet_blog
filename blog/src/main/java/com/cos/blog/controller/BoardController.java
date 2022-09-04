package com.cos.blog.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.BoardDao;
import com.cos.blog.domain.board.dto.CommonRespDto;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.UpdateReqDto;
import com.cos.blog.domain.board.dto.WriteReqDto;
import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.ReplyService;
import com.cos.blog.util.Script;
import com.google.gson.Gson;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardController() {
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
		BoardService boardService = new BoardService();
		ReplyService replyService = new ReplyService();
		
		HttpSession session = request.getSession();
		if(cmd.equals("writeForm")) {
			User login = (User)session.getAttribute("login");
			if(login != null) {
				RequestDispatcher dis = request.getRequestDispatcher("board/writeForm.jsp");
				dis.forward(request, response);
			} else {
				RequestDispatcher dis = request.getRequestDispatcher("user/loginForm.jsp");
				dis.forward(request, response);
			}
			
		} else if(cmd.equals("write")) {
			int userId = Integer.parseInt(request.getParameter("userId"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			WriteReqDto dto = new WriteReqDto();
			dto.setUserId(userId);
			dto.setTitle(title);
			dto.setContent(content);
			
			int reuslt = boardService.write(dto);
			if(reuslt == 1) {
				RequestDispatcher dis = request.getRequestDispatcher("index.jsp");
				dis.forward(request, response);
			} else {
				Script.Back(response, "글쓰기 실패");
			}
		} else if(cmd.equals("list")) {
			int page = Integer.parseInt(request.getParameter("page")); //최초에는 0, Next: 1, Next: 2
			List<Board> boardlist = boardService.writeList(page);
			request.setAttribute("boardlist", boardlist);
			
			int listCount = boardService.writeCount();
			int lastPage = (listCount - 1 )/4;
			double currentPosition = (double)page/(lastPage)*100;
			
			request.setAttribute("lastPage", lastPage);
			request.setAttribute("currentPosition", currentPosition);
			
			RequestDispatcher dis = request.getRequestDispatcher("board/listForm.jsp");
			dis.forward(request, response);
			
		} else if (cmd.equals("detail")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
		    DetailRespDto dto = boardService.writeimpo(id); //board테이블 + user테이블 = 조인된 데이터
		    List<Reply> replys = replyService.watchWriteList(id);
		    
		    if(dto == null) {
		    	Script.Back(response, "상세보기 실패");
		    } else {
		    	request.setAttribute("dto", dto);
		    	request.setAttribute("replys", replys);
				RequestDispatcher dis = request.getRequestDispatcher("board/detailForm.jsp");
				dis.forward(request, response);
		    }
		    
		} else if(cmd.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			int result = boardService.deletebtn(id);
			
			CommonRespDto<String> commonRespDto = new CommonRespDto<>();
			commonRespDto.setStatusCode(result);
			commonRespDto.setDate("성공");
			
			Gson gson = new Gson();
			String respData = gson.toJson(commonRespDto);
			PrintWriter writer = response.getWriter();
			writer.print(respData);
			writer.close();
			
		} else if(cmd.equals("updateForm")) {
			int id = Integer.parseInt(request.getParameter("id"));
			DetailRespDto dto =  boardService.writeimpo(id);
			request.setAttribute("dto", dto);
			
			RequestDispatcher dis = request.getRequestDispatcher("board/updateForm.jsp");
			dis.forward(request, response);
			
		} else if(cmd.equals("update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			UpdateReqDto dto = new UpdateReqDto();
			dto.setId(id);
			dto.setTitle(title);
			dto.setContent(content);
			
			int result = boardService.writeupdate(dto);
			if(result == 1) {
				response.sendRedirect("/blog/board?cmd=detail&id=" + id); //재접으로 데이터 가져오기 requestDispatcher로 가게되면 데이터를 가져오지 못함
			} else {
				Script.Back(response, "글수정 실패");
			}
			
		} else if (cmd.equals("search")) {
			String keyword = request.getParameter("keyword");
			int page = Integer.parseInt(request.getParameter("page"));
			
			List<Board> boardlist = boardService.writeClick(keyword ,page);
			
			request.setAttribute("boardlist", boardlist);
			
			int listCount = boardService.writeCount(keyword);
			int lastPage = (listCount - 1 )/4;
			double currentPosition = (double)page/(lastPage)*100;
			
			
			request.setAttribute("lastPage", lastPage);
			request.setAttribute("currentPosition", currentPosition);
			
			RequestDispatcher dis = request.getRequestDispatcher("board/listForm.jsp");
			dis.forward(request, response);
		}
	}
}
