import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class MakeReservation
 */
@WebServlet("/MakeReservation")
public class MakeReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;

    // Define constants to read database connection details from environment variables
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{  //A try statement.
			Class.forName("org.postgresql.Driver");

            // Construct the JDBC URL using the environment variables
            String jdbcUrl = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

            // Establish the connection using environment variables
            Connection con = DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD);
		
			int userid=0;
			PreparedStatement ps1=con.prepareStatement("select id from clients where username=?;");
			ps1.setString(1,request.getParameter("username"));
			ResultSet rs = ps1.executeQuery();
			while(rs.next())  //A while statement.
			{
				userid = rs.getInt(1);
			}
			rs.close();
			ps1.close();  //Close the Prepared Statement variable, ps.
			PreparedStatement ps=con.prepareStatement(
			"insert into reservations(provoli_id,client_id) values(?, ?)");  //Prepared Statement to insert the movie's values into the database.
			 ps.setInt(1, Integer.valueOf(request.getParameter("provoli_id")));
			 ps.setInt(2, userid);
			 ps.executeUpdate();  //After getting the values execute an update.
			 ps.close();  //Close the Prepared Statement variable, ps.
	         con.close();  //Close the connection with the database.
	         PrintWriter out = response.getWriter(); //PrintWriter variable, out initialize.
	         out.print("<div class=\"alert alert-success\">\r\n" +
						"  <strong>SUCCESS!</strong> Reservation added to the database.\r\n" +
						"</div>"); //Success Message appears into user's console.
	         RequestDispatcher rd=request.getRequestDispatcher("MakeReservation.jsp"); //RequestDispatcher variable, rd requests AddMovie.jsp .
	         rd.include(request,response); //RequestDispatcher variable, rd includes two objects (request,response).
	         out.close(); //PrintWriter variable, out close.
		}
		catch(Exception e)
		{  //Catch statement.
			System.out.println(e);
			PrintWriter out = response.getWriter(); //PrintWriter variable, out initialize.
			 out.print("<div class=\"alert alert-danger\">\r\n" +
						"  <strong>ERROR!</strong> Something went wrong.\r\n" +
						"</div>"); //Excption's Error Message appears.
				RequestDispatcher rd=request.getRequestDispatcher("MakeReservation.jsp"); //RequestDispatcher variable, rd requests AddMovie.jsp .
				rd.include(request,response); //RequestDispatcher variable, rd includes two objects (request,response).
				out.close(); //PrintWriter variable, out close.
		}
	}

}
