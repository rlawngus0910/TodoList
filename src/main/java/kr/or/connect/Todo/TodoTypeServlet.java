package kr.or.connect.Todo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.connect.TodoDao.TodoDao;
import kr.or.connect.TodoDto.TodoDto;


@WebServlet("/TodoTypeServlet")
public class TodoTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentType("text/html; charset=UTF-8");
		String type = request.getParameter("type");
		long id = Long.parseLong(request.getParameter("id"));
		
		TodoDto dto = new TodoDto(id, type);
		TodoDao dao = new TodoDao();
	    dao.updateTodo(dto);
	    
	    response.getWriter().write(getJSON(id));
		System.out.println("success");
	}
	
	public String getJSON(long id) {
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":[");
		
		TodoDao dao = new TodoDao();
		List<TodoDto> list = dao.getTodo();
		for(int i = 0;i<list.size();i++) {
			if(list.get(i).getId() == id) {
				String d = list.get(i).getRegDate();
				String year = d.substring(0,4);
			    String month = d.substring(5,7);
			    String day = d.substring(8,10);
			    String date = year + "." + month + "." + day;
			    
				result.append("[{\"value\": \"" + list.get(i).getTitle() + "\"},");
				result.append("{\"value\": \"" + list.get(i).getName() + "\"},");
				result.append("{\"value\": \"" + date + "\"},");
				result.append("{\"value\": \"" + list.get(i).getSequence() + "\"},");
				result.append("{\"value\": \"" + list.get(i).getId() + "\"}],");
				break;
			}
		}
		result.append("]}");
		return result.toString();
	}
}
