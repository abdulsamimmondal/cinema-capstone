import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowUsers
 */
@WebServlet("/ShowUsers")
public class ShowUsers extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;

    // Define constants to read database connection details from environment variables
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();   //PrintWriter variable, out initialize.
		out.print("<!DOCTYPE html><html><head><link href=\"//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css\" rel=\"stylesheet\" id=\"bootstrap-css\">\r\n" +
				"<script src=\"//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js\"></script>\r\n" +
				"<script src=\"//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\r\n" +
				"<meta charset=\"UTF-8\"></head><body><h3>ADMINS</h3>" +
		"<table border='1' style=\"border: solid 1px #DDEEEE;border-collapse: collapse;border-spacing: 0;font: normal 13px Arial, sans-serif\">"
		+ "<tr><th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">ADMIN ID</th>"
		+ "<th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">USERNAME</th>"
		+ "<th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">FULLNAME</th>"
		+ "<th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">CREATED BY ADMIN ID</th></tr>");
		//Availiable Movies' Table appears.

		try
		{  //A try statement.
			Class.forName("org.postgresql.Driver");

            // Construct the JDBC URL using the environment variables
            String jdbcUrl = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?ssl=true&sslmode=require&sslfactory=org.postgresql.ssl.NonValidatingFactory";

            // Establish the connection using environment variables and use try-with-resources
            try (Connection con = DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD)) {
                
                // --- Admins Table ---
                try (Statement stmt = con.createStatement(); // Creates Statement.
                     ResultSet rs = stmt.executeQuery("SELECT id,username,fullname,createdby_admin FROM admins")) { //Selects all the content from the database and the "movies" table.

                    while(rs.next())  //A while statement.
                     {
                        out.print("<tr><td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
                        out.println(rs.getInt(1));
                        out.print("</td>");
                        out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
                        out.println(rs.getString(2));
                        out.print("</td>");
                        out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
                        out.println(rs.getString(3));
                        out.print("</td>");
                        out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
                        out.println(rs.getInt(4));
                        out.print("</td></tr>");
                        //Prints the Availiable Movies' Table.
                    }
                } // rs and stmt for admins are closed here

                out.print("</table>"+ 
                "<h3>CONTENT ADMINS</h3>"+
                "<table border='1' style=\"border: solid 1px #DDEEEE;border-collapse: collapse;border-spacing: 0;font: normal 13px Arial, sans-serif\">"
                + "<tr><th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">CONTENT ADMIN ID</th>"
                + "<th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">USERNAME</th>"
                + "<th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">FULLNAME</th>"
                + "<th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">CREATED BY ADMIN ID</th></tr>");
                
                // --- Content Admins Table ---
                try (Statement stmt1 = con.createStatement(); //Creates Statement.
                     ResultSet rs1 = stmt1.executeQuery("SELECT id,username,fullname,createdby_admin FROM contentadmins")) { //Selects all the content from the database and the "movies" table.
                    while(rs1.next())  //A while statement.
                     {
                        out.print("<tr><td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
                        out.println(rs1.getInt(1));
                        out.print("</td>");
                        out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
                        out.println(rs1.getString(2));
                        out.print("</td>");
                        out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
                        out.println(rs1.getString(3));
                        out.print("</td>");
                        out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
                        out.println(rs1.getInt(4));
                        out.print("</td></tr>");
                        //Prints the Availiable Movies' Table.
                    }
                } // rs1 and stmt1 for contentadmins are closed here

                out.print("</table>"+ 
                        "<h3>CLIENTS</h3>"+
                        "<table border='1' style=\"border: solid 1px #DDEEEE;border-collapse: collapse;border-spacing: 0;font: normal 13px Arial, sans-serif\">"
                        + "<tr><th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">CLIENT ID</th>"
                        + "<th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">USERNAME</th>"
                        + "<th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">FULLNAME</th>"
                        + "<th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">CREATED BY ADMIN ID</th></tr>");
                
                // --- Clients Table ---
                try (Statement stmt2 = con.createStatement(); //Creates Statement.
                     ResultSet rs2 = stmt2.executeQuery("SELECT id,username,fullname FROM clients")) { //Selects all the content from the database and the "movies" table.
                    while(rs2.next())  //A while statement.
                     {
                        out.print("<tr><td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
                        out.println(rs2.getInt(1));
                        out.print("</td>");
                        out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
                        out.println(rs2.getString(2));
                        out.print("</td>");
                        out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
                        out.println(rs2.getString(3));
                        out.print("</td></tr>");
                        //Prints the Availiable Movies' Table.
                    }
                } // rs2 and stmt2 for clients are closed here

            } // con (Connection) is automatically closed here
		}
		catch(Exception e)
		{ //Catch Statement.
			System.out.println(e); //Exception.
		}
		out.print("</table></body></html>"); //Close the html components.
	}
}
