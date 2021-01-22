<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="todoform.css?after">
</head>
<body>
<div class="title">할일 등록</div>
<form class="inputtext" action="http://localhost:8080/Todo/TodoAddServlet" method="post" autocomplete="off">
   <label>어떤일인가요?</br><input type="text" id="doing" name="title" maxlength="24" size="106" placeholder="swift공부하기(24자까지)" style="padding:10px; margin-top:10px;" required></label></br></br>
   <label>누가 할일인가요?</br><input type="text" id="who" name="name" size="106" placeholder="홍길동" style="padding:10px; margin-top:10px;" required></label></br></br>
   
   <label>우선순위를 선택하세요.</br>
     <input type="radio" name="sequence" value="1" required>1순위
     <input type="radio" name="sequence" value="2" required>2순위
     <input type="radio" name="sequence" value="3" required>3순위
   </label>
     
     <div class="buttonset">
     	<button class="erase" onclick="location.href = 'todoForm.jsp'">내용지우기</button>
     	<button type="submit" class="sumb">제출</button>
     	<button class="prev" onclick="location.href = 'http://localhost:8080/Todo/MainServlet'"> < 이전 </button>
     </div>  
</form>

</body>
</html>