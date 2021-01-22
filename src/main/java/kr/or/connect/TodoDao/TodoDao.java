package kr.or.connect.TodoDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.or.connect.TodoDto.TodoDto;

public class TodoDao {
	String url = "jdbc:mysql://localhost:3306/testdb?serverTimezone=Asia/Seoul&useSSL=false&useUnicode=true&characterEncoding=utf8";
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	public List<TodoDto> getTodo() {
		TodoDto todo = null;
		List<TodoDto> list = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, "", "");    // Input DBuser, DBpassword
			String sql = "SELECT * FROM todo ORDER BY regdate";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				long id = rs.getInt(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int sequence = rs.getInt(4);
				String type = rs.getString(5);
				String regdate = rs.getString(6);
				todo = new TodoDto(id, name, regdate,sequence,title,type);
				list.add(todo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
			    try {
			    	rs.close();
			    }catch(SQLException e) {
			    	e.printStackTrace();
			    }
			}
			if(ps != null) {
			    try {
			    	ps.close();
			    }catch(SQLException e) {
			    	e.printStackTrace();
			    }
			}
			if(conn != null) {
			    try {
			    	conn.close();
			    }catch(SQLException e) {
			    	e.printStackTrace();
			    }
			}
		}
		return list;
	}
	
	public int addTodo(TodoDto todo) {
		int insertCount = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, "", "");   // Input DBuser, DBpassword
			String sql = "INSERT INTO todo(title, name, sequence) VALUES(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, todo.getTitle());
			ps.setString(2, todo.getName());
			ps.setInt(3, todo.getSequence());
			insertCount = ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
			    try {
			    	rs.close();
			    }catch(SQLException e) {
			    	e.printStackTrace();
			    }
			}
			if(ps != null) {
			    try {
			    	ps.close();
			    }catch(SQLException e) {
			    	e.printStackTrace();
			    }
			}
			if(conn != null) {
			    try {
			    	conn.close();
			    }catch(SQLException e) {
			    	e.printStackTrace();
			    }
			}
		}
		return insertCount;
	}
	
	public int updateTodo(TodoDto todo) {
		int updateCount = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, "", "");    // Input DBuser, DBpassword
			String sql = "";
			if(todo.getType().equals("TODO")) {
				sql = "UPDATE todo SET type = 'DOING' WHERE id = ?";
			}
			else if(todo.getType().equals("DOING")) {
				sql = "UPDATE todo SET type = 'DONE' WHERE id = ?";
			}
			ps = conn.prepareStatement(sql);
			ps.setLong(1, todo.getId());
			updateCount = ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
			    try {
			    	rs.close();
			    }catch(SQLException e) {
			    	e.printStackTrace();
			    }
			}
			if(ps != null) {
			    try {
			    	ps.close();
			    }catch(SQLException e) {
			    	e.printStackTrace();
			    }
			}
			if(conn != null) {
			    try {
			    	conn.close();
			    }catch(SQLException e) {
			    	e.printStackTrace();
			    }
			}
		}
		return updateCount;
	}
	
	
	
}
