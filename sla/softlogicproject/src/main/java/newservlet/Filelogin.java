package newservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Filelogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
String email,pass;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		email=request.getParameter("t1");
		pass=request.getParameter("t2");
		if(validate.checkuser(email, pass)) {
			response.sendRedirect("Index.html");
		}
		else {
			PrintWriter out=response.getWriter();
			out.print("User name or password wrong");
		}
	}
public class validate{
	public static boolean checkuser(String email,String password)
	{
		boolean st=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/filesystem","root","4455");
			PreparedStatement ps=con.prepareStatement("select * from filesign where email=? and password=?");
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			st=rs.next();
			
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return st;
	}
}
}


