package com.cos.blog.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class Script {
	
	public static void Back(HttpServletResponse response, String msg) {
		try {
			PrintWriter writer = response.getWriter();
			writer.println("<script>");
			writer.println("alert('"+ msg +"');");
			writer.println("history.go(-1);");
			writer.println("</script>");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
