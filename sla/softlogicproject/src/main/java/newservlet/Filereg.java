package newservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Filereg extends HttpServlet {
	private static final long serialVersionUID = 1L;
      Connection con;
      
    public Filereg()  {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/filesystem";
		String userName = "root";
		String password = "4455";
		try {
			con = DriverManager.getConnection(url, userName, password);
			System.out.println("connection successfully;" + con);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
    }
 public static void main(String[] args) {
	new Filereg();
}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		

		
		

			String fname=req.getParameter("fname");
			String email=req.getParameter("email");
			String dob=req.getParameter("dob");
			String mobile=req.getParameter("mobile");
			String pass=req.getParameter("password");
			String retypepass=req.getParameter("Retypepassword");
			try {
			PreparedStatement ps=con.prepareStatement("insert into filesign values(?,?,?,?,?,?)");
			    ps.setString(1, fname);
			    ps.setString(2, email);
			    ps.setString(3, dob);
			    ps.setString(4, mobile);
			    ps.setString(5, pass);
			    ps.setString(6, retypepass);
				int i=ps.executeUpdate();
				
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		}


}
