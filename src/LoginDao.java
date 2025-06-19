import java.sql.*;

public class LoginDao
{
    // Define constants to read database connection details from environment variables
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

	public static boolean validate(String username,String password,String role)
	{  //Validate boolean with two string type object initialize.

		boolean status=false;  //Initially boolean type variable, status is not enabled.

		try
		{  //A try statement.

		Class.forName("org.postgresql.Driver");

        // Construct the JDBC URL using the environment variables
        String jdbcUrl = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?sslmode=require";

        // Establish the connection using environment variables
		Connection con=DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD);  //Connection with the postgresql database with following credentials.

		if(role.equals("contentadmins")) {
			
			String salt=null;
			PreparedStatement ps1=con.prepareStatement("select salt from contentadmins where username=?;");
			ps1.setString(1,username);
			ResultSet rs1 = ps1.executeQuery();
			while(rs1.next())  //A while statement.
			{
				salt = rs1.getString(1);
			}
			rs1.close();
			ps1.close();  //Close the Prepared Statement variable, ps.
			
			String hashedpassword = Encryption.getHashMD5(password,salt);
			
			PreparedStatement ps=con.prepareStatement(
					"select * from contentadmins where username=? and hashedpassword=?");
					//Prepared Statement variable, ps selects all the content from the database, the "contentadmins" table and columns "username" and "password".
					ps.setString(1,username);  //Prepared Statement variable, ps sets the first string.
					ps.setString(2,hashedpassword);	 //Prepared Statement variable, ps sets the second string.
					ResultSet rs=ps.executeQuery();  //ResultSet valiable, rs gets the result from the execution of the "ps" query.
					status=rs.next(); //Boolean type variable, status checks if rs variable has any content.
		}
		else if(role.equals("admins")) {
			String salt=null;
			PreparedStatement ps1=con.prepareStatement("select salt from admins where username=?;");
			ps1.setString(1,username);
			ResultSet rs1 = ps1.executeQuery();
			while(rs1.next())  //A while statement.
			{
				salt = rs1.getString(1);
			}
			rs1.close();
			ps1.close();  //Close the Prepared Statement variable, ps.
			
			String hashedpassword = Encryption.getHashMD5(password,salt);
			PreparedStatement ps=con.prepareStatement(
					"select * from admins where username=? and hashedpassword=?");
					//Prepared Statement variable, ps selects all the content from the database, the "admins" table and columns "username" and "password".
					ps.setString(1,username);  //Prepared Statement variable, ps sets the first string.
					ps.setString(2,hashedpassword);	 //Prepared Statement variable, ps sets the second string.

					ResultSet rs=ps.executeQuery();  //ResultSet valiable, rs gets the result from the execution of the "ps" query.
					status=rs.next(); //Boolean type variable, status checks if rs variable has any content.
		}
		else { // Handles clients
			String salt=null;
			PreparedStatement ps1=con.prepareStatement("select salt from clients where username=?;");
			ps1.setString(1,username);
			ResultSet rs1 = ps1.executeQuery();
			while(rs1.next())  //A while statement.
			{
				salt = rs1.getString(1);
			}
			rs1.close();
			ps1.close();  //Close the Prepared Statement variable, ps.
			
			String hashedpassword = Encryption.getHashMD5(password,salt);
			PreparedStatement ps=con.prepareStatement(
					"select * from clients where username=? and hashedpassword=?");
					//Prepared Statement variable, ps selects all the content from the database, the "clients" table and columns "username" and "password".
					ps.setString(1,username);  //Prepared Statement variable, ps sets the first string.
					ps.setString(2,hashedpassword);	 //Prepared Statement variable, ps sets the second string.

					ResultSet rs=ps.executeQuery();  //ResultSet valiable, rs gets the result from the execution of the "ps" query.
					status=rs.next(); //Boolean type variable, status checks if rs variable has any content.
		}
        // It's good practice to close the connection here if it's opened successfully
        // However, in a DAO method, sometimes connections are managed by a pool,
        // but for direct DriverManager, explicit close is good.
        // Make sure to close the connection whether status is true or false
        if (con != null) {
            con.close();
        }
		}
		catch(Exception e)
		{ //Catch statement.
			System.out.println(e); //Exception.
		}

		return status; //Returns boolean type variable, status.
	}
}
