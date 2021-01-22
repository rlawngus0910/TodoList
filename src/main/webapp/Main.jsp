<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.or.connect.TodoDto.TodoDto" %>
<%@ page import="kr.or.connect.TodoDao.TodoDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TODO List</title>
<link rel="stylesheet" href="todomain.css?after">

</head>
<body>
	<header>
		<div class="maintitle">나의 해야할 일들</div>
		<li class="newtodo" onclick="location.href='http://localhost:8080/Todo/TodoformServlet';">새로운 TODO 등록</li>
	</header>
	<section>
	<ul id="TODO">
	   <li class="navtitle">TODO</li>
	   <%
	   TodoDao dao = new TodoDao();
	   List<TodoDto> todolist = (ArrayList <TodoDto>)request.getAttribute("todo");
	   
	   for(int i=0;i<todolist.size();i++){
		   TodoDto dto = todolist.get(i);
		   String d = dto.getRegDate();
		   String year = d.substring(0,4);
		   String month = d.substring(5,7);
		   String day = d.substring(8,10);
		   String date = year + "." + month + "." + day;
	   %>
	   <li id = "todo<%=dto.getId() %>" class="navcontent">
	   <h3><%=dto.getTitle() %></h3>
	   <p>등록날짜 : <%=date%>, <%=dto.getName() %>,우선순위 <%=dto.getSequence() %><button id="changetype<%=dto.getId() %>" value="<%=dto.getId()%>" name="<%=dto.getType()%>" style="float:right; margin-right:10px;">→</button></p>
	   </li>
	   <%
	   }
	   %>
	</ul>
	<ul id=doing>
	   <li class="navtitle">DOING</li>
	   <%
	   List<TodoDto> doinglist = (ArrayList <TodoDto>)request.getAttribute("doing");
	   
	   for(int i = 0;i < doinglist.size();i++){
		   TodoDto dto = doinglist.get(i);
		   String d = dto.getRegDate();
		   String year = d.substring(0,4);
		   String month = d.substring(5,7);
		   String day = d.substring(8,10);
		   String date = year + "." + month + "." + day;
	   %>
	   <li id = "todo<%=dto.getId() %>" class="navcontent">
	   <h3><%=dto.getTitle() %></h3>
	   <p>등록날짜 : <%=date%>, <%=dto.getName() %>,우선순위 <%=dto.getSequence() %><button id="changetype<%=dto.getId() %>" value="<%=dto.getId()%>" name="<%=dto.getType()%>" style="float:right; margin-right:10px;">→</button></p>
	   </li>
	   <%  
	   }
	   %>
	</ul>
	<ul id="done">
	   <li class="navtitle">DONE</li>
	   <%
	   List<TodoDto> donelist = (ArrayList <TodoDto>)request.getAttribute("done");
	   
	   for(int i = 0;i < donelist.size();i++){
		   TodoDto dto = donelist.get(i);
		   String d = dto.getRegDate();
		   String year = d.substring(0,4);
		   String month = d.substring(5,7);
	       String day = d.substring(8,10);
           String date = year + "." + month + "." + day;
	   %>
	   <li id = "todo<%=dto.getId() %>" class="navcontent">
	   <h3><%=dto.getTitle() %></h3>
	   <p>등록날짜 : <%=date%>, <%=dto.getName() %>,우선순위 <%=dto.getSequence() %></p>
	   </li>
	   <% 
	   }
	   %>
	</ul>
	</section>
	<script type="text/javascript">
	var oReq = new XMLHttpRequest();
	window.onload = ajaxFunction();
	function ajaxFunction(){
		var btns = document.querySelectorAll("button");
		for(var i = 0; i < btns.length ; i++){
			btns[i].addEventListener("click", function(evt){
				console.log(evt.currentTarget.id);
				oReq.open("Post","http://localhost:8080/Todo/TodoTypeServlet?type=" + document.getElementById(evt.currentTarget.id).name +"&id=" + document.getElementById(evt.currentTarget.id).value, true);
				if(document.getElementById(evt.currentTarget.id).name === "TODO")
					oReq.onreadystatechange = doingChange;
				else if(document.getElementById(evt.currentTarget.id).name === "DOING")
					oReq.onreadystatechange = doneChange;
				oReq.send(null);
			});
		}
	}
	
	function doingChange(){
		if(oReq.readyState === 4 && oReq.status === 200){
			console.log("success");
			var object = eval('(' + oReq.responseText + ')');
			var result = object.result;
			var elem = '';
			for(var i = 0; i < result.length; i++){
				var bid = "changetype" + result[i][4].value;
				var lid = "todo" + result[i][4].value;
				var deleteElem = document.getElementById(lid);
				deleteElem.parentNode.removeChild(deleteElem);
				elem = elem + '<li id="' + lid +'"class="navcontent"><h3>' + result[i][0].value + '</h3><p>등록날짜 : '+ result[i][2].value + ', ' + result[i][1].value + ',우선순위 ' + result[i][3].value + '<button id = "' + bid +'" value = "' + result[i][4].value +'" name="DOING" style="float:right; margin-right:10px;">→</button></p></li>';
			}
			document.getElementById("doing").innerHTML += elem;
			ajaxFunction();
		}
	}
	
	function doneChange(){
		if(oReq.readyState === 4 && oReq.status === 200){
			console.log("success");
			var object = eval('(' + oReq.responseText + ')');
			var result = object.result;
			var elem = '';
			for(var i = 0; i < result.length; i++){
				var bid = "changetype" + result[i][4].value;
				var lid = "todo" + result[i][4].value;
				var deleteElem = document.getElementById(lid);
				deleteElem.parentNode.removeChild(deleteElem);
				elem = elem + '<li id="' + lid +'"class="navcontent"><h3>' + result[i][0].value + '</h3><p>등록날짜 : '+ result[i][2].value + ', ' + result[i][1].value + ',우선순위 ' + result[i][3].value + '</p></li>';
			}
			document.getElementById("done").innerHTML += elem;
			ajaxFunction();
		}
	}
</script>
</body>
</html>