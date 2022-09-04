<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../layout/header.jsp" %>

<div class="container">
	<form action="/blog/user?cmd=login" method="post"  enctype="application/x-www-form-urlencoded">
		<div class="form-group">
			이름: <input type="text"  id="username" class="form-control"  name="username" placeholder="Enter username"  required="required"/>
		</div>
		<div class="form-group">
			비밀번호: <input type="password" name="password" class="form-control" placeholder="Enter password"  required="required"/>
		</div>
		<button type="submit" class="btn btn-primary" class="d-flex flex-row-reverse bg-secondary">로그인</button>
	</form>
</div>
<script type="text/javascript">
	
</script>
</body>
</html>