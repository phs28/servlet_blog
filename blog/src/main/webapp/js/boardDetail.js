function addReply(date) {
	let replyItem = `<li id="reply-${date.id}" class="media">`
	replyItem += `<div class="media-body">`
	replyItem += `<strong class="text-primary">${date.userId}</strong>` 
	replyItem += `<p>${date.content}</p></div>` 
	replyItem += `<div class="m-2">`
	replyItem += `<i onClick="deleteReply(${date.id})" class="material-icons">delete</i></div></li>`
	
	$("#reply__list").prepend(replyItem)
}

function deleteReply(id) {
	$.ajax({
		type: "post",
		url: "/blog/reply?cmd=replyWriteDelete&id=" + id,
		dataType: "json"
	}).done(function(result) {
		if (result.statusCode == 1) {
			alert("삭제되었습니다.")
			$("#reply-" + id).remove()
		} else {
			alert("댓글삭제 실패")
		}
	})
}

function deleteById(boardId) {
	 let data = {
		boardId: boardId
	}
	
	$.ajax({
		type: 'post',
		url: '/blog/board?cmd=delete&id=' + boardId,
		dataType: 'json'
	}).done(function(result) {
		if (result.statusCode == 1) {
			location.href = "index.jsp"
		} else {
			alert("삭제 실패")
		}
	})
}

function replySave(userId, boardId) {
	let date = {
		userId: userId,
		boardId: boardId,
		content: $("#content").val() //글자수를 텍스트로
	}

	$.ajax({
		type: "post",
		url: "/blog/reply?cmd=replyWriteForm",
		data: JSON.stringify(date), // json화 시키는 문법
		contentType: "application/json; charset=utf-8",
		dataType: "json"
	}).done(function(result) {
		if (result.statusCode == 1) {
			console.log(result)
			addReply(result.date)
			$("content").val("")
			//location.reload();
		} else {
			alert("댓글쓰기 실패")
		}
	})
}