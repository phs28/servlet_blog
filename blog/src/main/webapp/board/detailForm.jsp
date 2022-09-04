<%@page import="com.cos.blog.domain.user.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<div class="container">
	<c:if test="${sessionScope.login.id == dto.userId }">
		<a href="/blog/board?cmd=updateForm&id=${dto.id }"
			class="btn btn-warning">수정</a>
		<button onClick="deleteById(${dto.id})" class="btn btn-danger">글삭제</button>
	</c:if>
	<br> <br>
	<h6 class="m-2">
		작성자: <i>${dto.username }</i> 조횟수: <i>${dto.readCount }</i>
	</h6>
	<br>
	<h3 class="m-2">
		<b>${dto.title }</b>
	</h3>
	<hr>
	<div class="form-group">
		<div class="m-2">${dto.content }</div>
	</div>

	<div class="row bootstrap snippets">
		<div class="col-md-12">
			<div class="comment-wrapper">
				<div class="panel panel-info">
					<div class="panel-heading m-2"></div>
					<div class="panel-body">
						<input type="hidden" name="userId" value="${sessionScope.login.id }" /> 
						<input type="hidden" name="boardId" value="${dto.id }" />
						<textarea id="content" name="content" class="form-control" placeholder="댓글 작성" class="form-control" rows="2"></textarea>
						<br>
						<button onClick="replySave(${sessionScope.login.id}, ${dto.id })" class="btn btn-primary pull-right">댓글쓰기</button>
						<!-- json에 던지기위한 매개에 id 값을 객체로 받기 -->
						<div class="clearfix"></div>
						<hr>

						<ul id="reply__list" class="media-list">
							<c:forEach items="${replys }" var="reply">
								<li id="reply-${reply.id }" class="media">
									<div class="media-body">
										<strong class="text-primary">${reply.userId }</strong>
										<p>${reply.content }</p>
									</div>
									<div class="m-2">
										<c:if test="${sessionScope.login.id == reply.userId }">
											<i onClick="deleteReply(${reply.id})" class="material-icons">delete</i>
										</c:if>
									</div>								
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="js/boardDetail.js"></script>
</body>
</html>
