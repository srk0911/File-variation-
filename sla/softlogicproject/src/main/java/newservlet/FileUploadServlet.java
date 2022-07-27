package newservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

/**
 * Servlet implementation class servletcompare
 */

@WebServlet("/uploadServlet")
@MultipartConfig(maxFileSize = 16177215)
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String id;

	/*
	 * public void init() throws ServletException {
	 * System.out.println("-----------------------------------------");
	 * System.out.println(" Init method is called in " + this.getClass().getName());
	 * System.out.println("--------------------------------------"); }
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

//		String id=request.getParameter("id");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/filesystem";
			String userName = "root";
			String password = "4455";
			Connection con = DriverManager.getConnection(url, userName, password);
			System.out.println("connection successful" + con);

			Part filePart1 = request.getPart("file1");
			Part filePart2 = request.getPart("file2");

//			System.out.println(filePart1.getName());
//			System.out.println(filePart1.getSize());
//			System.out.println(filePart1.getContentType());
//
//			System.out.println(filePart2.getName());
//			System.out.println(filePart2.getSize());
//			System.out.println(filePart2.getContentType());

			InputStream inputStream1 = null;
			InputStream inputStream2 = null;
			inputStream1 = filePart1.getInputStream();
			inputStream2 = filePart2.getInputStream();

			Statement st = con.createStatement();

			PreparedStatement ps = con.prepareStatement("insert into files (file1,file2) values(?,?)");

			ps.setBlob(1, inputStream1);
			ps.setBlob(2, inputStream2);
//			ps.setString(3, id);

			ps.executeUpdate();

//				String st1="truncate table files";
//		        st.execute("truncate table files");

			ResultSet rs = st.executeQuery("select * from files order by id desc limit 1;");
			while (rs.next()) {
				String firstCol = rs.getString("file1");
				String secondCol = rs.getString("file2");

				System.out.println(firstCol + " " + secondCol);

//				firstCol.compareTo(secondCol);

				String s1, s2;
				StringTokenizer str1 = new StringTokenizer(firstCol);
				StringTokenizer str2 = new StringTokenizer(secondCol);
				s1 = str1.nextToken();
				s2 = str2.nextToken();
				while (str1.hasMoreTokens() && str2.hasMoreTokens()) {
					if (s1.equalsIgnoreCase(s2))

						out.print("the different words:\n" + str1.nextToken() + str2.nextToken());

				}

				String filename = "Filesystem.docx";
				String filepath = "Documents";
				response.setContentType("APPLICATION/OCTET-STREAM");
				  response.setHeader("Content-Disposition", "attachment; filename=\"" +
				  filename + "\"");
			
				FileInputStream fileInputStream = new FileInputStream(filepath + filename);

				int i;
				while ((i = fileInputStream.read()) != -1) {
					out.write(i);
				}
				fileInputStream.close();
				out.close();
			}

		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * public void destroy() {
	 * System.out.println("-----------------------------------------");
	 * System.out.println(" destroy method is called in " +
	 * this.getClass().getName());
	 * System.out.println("-----------------------------------------"); }
	 */

	public static void main(String[] args) {
		new FileUploadServlet();
	}
}