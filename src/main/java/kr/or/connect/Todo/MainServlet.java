package kr.or.connect.Todo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.connect.TodoDao.TodoDao;
import kr.or.connect.TodoDto.TodoDto;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(StandardCharsets.UTF_8.name());
		TodoDao dao = new TodoDao();
		List <TodoDto> list = dao.getTodo();
		List <TodoDto> todolist = new ArrayList<>();
		List <TodoDto> doinglist = new ArrayList<>();
		List <TodoDto> donelist = new ArrayList<>();
		
		for(int i=0;i<list.size();i++) {
			TodoDto dto = list.get(i);
			if(dto.getType().equals("TODO")) {
				todolist.add(dto);
			}
			else if(dto.getType().equals("DOING")) {
				doinglist.add(dto);
			}
			else if(dto.getType().equals("DONE")) {
				donelist.add(dto);
			}
		}
		
		request.setAttribute("todo", todolist);
		request.setAttribute("doing", doinglist);
		request.setAttribute("done", donelist);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Main.jsp");
		requestDispatcher.forward(request, response);
	}
}
