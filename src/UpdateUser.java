import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateUser
 */
@WebServlet("/UpdateUser")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    // Define constants to read database connection details from environment variables
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String r = request.getParameter("role");
		
		SecureRandom random = new SecureRandom();
		byte bytes[]= new byte[20];
		random.nextBytes(bytes);
		String salt = random.toString();
		String hashedpassword = Encryption.getHashMD5(request.getParameter("old_password"),salt); // Note: Hashing old_password here, but typically you'd verify old password first, then hash the *new* password if changing it. This code hashes the old password for storage. If you intend to *change* the password, you'd hash the *new* password here.

        // Construct the JDBC URL using the environment variables
        String jdbcUrl = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

		if(r.equals("contentadmins")) {
			try
			{  //A try statement.
				Class.forName("org.postgresql.Driver");
                // Using try-with-resources to ensure connection is closed automatically
				try (Connection con = DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD)) {
				
                    int creator=0;
                    try (PreparedStatement ps2=con.prepareStatement("select id from admins where username=?;")) {
                        ps2.setString(1,request.getParameter("creator"));
                        try (ResultSet rs1 = ps2.executeQuery()) {
                            while(rs1.next())  //A while statement.
                            {
                                creator = rs1.getInt(1);
                            }
                        }
                    } // ps2 and rs1 closed here
					
					try (PreparedStatement ps=con.prepareStatement(
							"update contentadmins set username=? , hashedpassword=?, salt=?, fullname=?, createdby_admin=? where username=? ")) {
							 ps.setString(1,request.getParameter("new_username"));
							 ps.setString(2,hashedpassword);
							 ps.setString(3,salt);
							 ps.setString(4,request.getParameter("fullname"));
							 ps.setInt(5,creator);
							 ps.setString(6,request.getParameter("old_username"));
							 ps.executeUpdate();  //After getting the values execute an update.
					} // ps closed here
				         PrintWriter out = response.getWriter(); //PrintWriter variable, out initialize.
				         out.print("<div class=\"alert alert-success\">\r\n" +
									"  <strong>SUCCESS!</strong> Content Admin updated to the database.\r\n" +
									"</div>"); //Success Message appears into user's console.
				         RequestDispatcher rd=request.getRequestDispatcher("UpdateUser.jsp"); //RequestDispatcher variable, rd requests AddMovie.jsp .
				         rd.include(request,response); //RequestDispatcher variable, rd includes two objects (request,response).
				         out.close(); //PrintWriter variable, out close.
                } // con closed here
			}
				catch(Exception e)
				{  //Catch statement.
					System.out.println(e);
					PrintWriter out = response.getWriter(); //PrintWriter variable, out initialize.
					 out.print("<div class=\"alert alert-danger\">\r\n" +
								"  <strong>ERROR!</strong> Something went wrong.\r\n" +
								"</div>"); //Excption's Error Message appears.
						RequestDispatcher rd=request.getRequestDispatcher("UpdateUser.jsp"); //RequestDispatcher variable, rd requests DeleteMovie.jsp .
						rd.include(request,response); //RequestDispatcher variable, rd includes two objects (request,response).
						out.close(); //PrintWriter variable, out close.
				}
		}
		else if(r.equals("admins")) {
			try
			{  //A try statement.
				Class.forName("org.postgresql.Driver");
                // Using try-with-resources to ensure connection is closed automatically
				try (Connection con = DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD)) {
				
                    int creator=0;
                    try (PreparedStatement ps2=con.prepareStatement("select id from admins where username=?;")) {
                        ps2.setString(1,request.getParameter("creator"));
                        try (ResultSet rs1 = ps2.executeQuery()) {
                            while(rs1.next())  //A while statement.
                            {
                                creator = rs1.getInt(1);
                            }
                        }
                    } // ps2 and rs1 closed here
					
					try (PreparedStatement ps=con.prepareStatement(
							"update admins set username=? , hashedpassword=?,salt=?, fullname=?, createdby_admin=? where username=? ")) {
							 ps.setString(1,request.getParameter("new_username"));
							 ps.setString(2,hashedpassword);
							 ps.setString(3,salt);
							 ps.setString(4,request.getParameter("fullname"));
							 ps.setInt(5,creator);
							 ps.setString(6,request.getParameter("old_username"));
							 ps.executeUpdate();  //After getting the values execute an update.
					} // ps closed here
				         con.close();  //Close the connection with the database. // This con.close() is redundant because of try-with-resources
				         PrintWriter out = response.getWriter(); //PrintWriter variable, out initialize.
				         out.print("<div class=\"alert alert-success\">\r\n" +
									"  <strong>SUCCESS!</strong> Admin updated to the database.\r\n" +
									"</div>"); //Success Message appears into user's console.
				         RequestDispatcher rd=request.getRequestDispatcher("UpdateUser.jsp"); //RequestDispatcher variable, rd requests AddMovie.jsp .
				         rd.include(request,response); //RequestDispatcher variable, rd includes two objects (request,response).
				         out.close(); //PrintWriter variable, out close.
                } // con closed here
			}
				catch(Exception e)
				{  //Catch statement.
					System.out.println(e);
					PrintWriter out = response.getWriter(); //PrintWriter variable, out initialize.
					 out.print("<div class=\"alert alert-danger\">\r\n" +
								"  <strong>ERROR!</strong> Something went wrong.\r\n" +
								"</div>"); //Excption's Error Message appears.
						RequestDispatcher rd=request.getRequestDispatcher("UpdateUser.jsp"); //RequestDispatcher variable, rd requests DeleteMovie.jsp .
						rd.include(request,response); //RequestDispatcher variable, rd includes two objects (request,response).
						out.close(); //PrintWriter variable, out close.
				}
		}
		else { // Handles 'clients' role
			try
			{  
				Class.forName("org.postgresql.Driver");
                // Using try-with-resources to ensure connection is closed automatically
				try (Connection con = DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD)) {
				
                    try (PreparedStatement ps=con.prepareStatement(
                            "update clients set username=? , hashedpassword=?,salt=?, fullname=? where username=? ")) {
                             ps.setString(1,request.getParameter("new_username"));
                             ps.setString(2,hashedpassword);
                             ps.setString(3,salt);
                             ps.setString(4,request.getParameter("fullname"));
                             ps.setString(5,request.getParameter("old_username"));
                             ps.executeUpdate();  //After getting the values execute an update.
                    } // ps closed here
				         PrintWriter out = response.getWriter(); //PrintWriter variable, out initialize.
				         out.print("<div class=\"alert alert-success\">\r\n" +
									"  <strong>SUCCESS!</strong> Client updated to the database.\r\n" +
									"</div>"); //Success Message appears into user's console.
				         RequestDispatcher rd=request.getRequestDispatcher("UpdateUser.jsp"); //RequestDispatcher variable, rd requests AddMovie.jsp .
				         rd.include(request,response); //RequestDispatcher variable, rd includes two objects (request,response).
				         out.close(); //PrintWriter variable, out close.
                } // con closed here
		    }
			catch(Exception e)
			{  //Catch statement.
				System.out.println(e);
				PrintWriter out = response.getWriter(); //PrintWriter variable, out initialize.
				 out.print("<div class=\"alert alert-danger\">\r\n" +
							"  <strong>ERROR!</strong> Something went wrong.\r\n" +
							"</div>"); //Excption's Error Message appears.
					RequestDispatcher rd=request.getRequestDispatcher("UpdateUser.jsp"); //RequestDispatcher variable, rd requests DeleteMovie.jsp .
					rd.include(request,response); //RequestDispatcher variable, rd includes two objects (request,response).
					out.close(); //PrintWriter variable, out close.
			}
	
		}
	}
}
