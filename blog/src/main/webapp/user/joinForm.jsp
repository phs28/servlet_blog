<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<div class="container">
	<form action="/blog/user?cmd=join" method="post" onsubmit="return valid()">
		<div class="d-flex flex-row-reverse">
			<button type="button" class="btn btn-info"  onClick="usernameCheck()">중복체크</button>
		</div>
		<div class="form-group">
			이름: <input type="text"  id="username" class="form-control"  name="username" placeholder="Enter username"  required="required"/>
		</div>
		<div class="form-group">
			비밀번호: <input type="password" name="password" class="form-control" placeholder="Enter password"  required="required"/>
		</div>
		<div class="form-group">
			이메일: <input type="email"  name="email"   class="form-control" placeholder="Enter email"  required="required"/>
		</div>
		<div class="form-group">
			<button type="button" class="btn btn-info"  onClick="goPopup();">주소검색</button>
			<input type="text"  id="address" name="address" class="form-control" placeholder="Enter address"  required="required" readonly="readonly"/>
		</div>
		<button type="submit" class="btn btn-primary" class="d-flex flex-row-reverse bg-secondary">회원가입</button>
	</form>
</div>

<script>
	let isChecking = false;

	function valid() {
		if(isChecking == false) {
			alert("이름 중복체크를 해주세요.")
		}
		return isChecking;
	}

	function usernameCheck() {
		let username = $("#username").val()
		
		$.ajax ({
			type: 'POST',
			url: '/blog/user?cmd=usernameCheck',
			data: username,
			contentType: 'text/plain; charset=utf-8',
			dataType: 'text'
		}).done(function(data) {
			if(data === 'ok') {
				isChecking = false
				alert('이름이 중복되었습니다.')
			} else {
				isChecking = true
				alert('해당 이름을 사용가능합니다.')
			}
		})
	}

	function goPopup() {
		let pop = window.open("/blog/test/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
	}

	function jusoCallBack(roadFullAddr) {
		 let addressEl = document.querySelector("#address")
		 addressEl.value = roadFullAddr
	}
</script>
</body>
</html>