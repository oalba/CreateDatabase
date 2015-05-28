package com.zubiri.CreateDatabase;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

/**
 * Servlet implementation class CreateDatabase
 */
public class CreateDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "zubiri";
	   
	Connection conn = null;
	Statement stmt = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateDatabase() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=iso-8859-1");
	    PrintWriter out = response.getWriter();
	    try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);

		      //STEP 4: Execute a query
		      System.out.println("Creating database...");
		      stmt = conn.createStatement();
		      
		      String sql = "CREATE DATABASE IF NOT EXISTS createdatabase";
		      stmt.executeUpdate(sql);
		      System.out.println("Database created successfully...");
		      System.out.println("Creando la tabla en la base de datos...");
		      stmt = conn.createStatement();
		      
		      String sql3 = "USE createdatabase";
		      stmt.executeUpdate(sql3);
		      
		      String sql2 = "CREATE TABLE IF NOT EXISTS USER (ID INTEGER  AUTO_INCREMENT,  USER VARCHAR(50), PASSWORD VARCHAR(50), PRIMARY KEY ( ID ))";

		      stmt.executeUpdate(sql2);
		      
		      
		      
		      stmt.executeUpdate("INSERT INTO USER (ID,USER,PASSWORD) VALUES ('" + 
		        request.getParameter("number") + "', '" + request.getParameter("nombre") + "','" + request.getParameter("password") + "')");

		      String sqlselect = "SELECT * FROM USER";
		      ResultSet rs = stmt.executeQuery(sqlselect);
		      out.print("<html>");
		      out.print("<head><title></title>");
		      out.print("</head>");
		      out.print("<body>");
		      out.println("<table align='center' width='40%' border='10' >  ");
		      out.println("<td><FONT COLOR='#00BFFF' SIZE='5'>	ID</FONT> </td>");
		      out.println("<td><FONT COLOR='#00BFFF' SIZE='5'>	USER</FONT> </td>");
		      out.println("<td><FONT COLOR='#00BFFF' SIZE='5'> PASSWORD</FONT> </td>");
		      out.println("</tr>");
		      while (rs.next()) {
		    	Integer idselect = rs.getInt("ID");
		    	String userselect = rs.getString("USER");
		        String passwordselect = rs.getString("PASSWORD");
		        

		        out.println("<tr>");
		        out.println("<td> <FONT COLOR='#58FA58' SIZE='3'>" + idselect + "</FONT></td>");
		        out.println("<td> <FONT COLOR='#58FA58' SIZE='3'>" + userselect + "</FONT></td>");
		        out.println("<td> <FONT COLOR='#58FA58' SIZE='3'>" + passwordselect + "</FONT></td>");
		        out.println("</tr>");
		      }
		      rs.close();
		      out.println("</table");
		      out.print("</body>");
		      out.print("</html>");
		    }
		    catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Goodbye!");
		}//end JDBCExample
}
