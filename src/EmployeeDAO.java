import java.sql.Statement;
import java.util.*;
import java.io.*;
import java.sql.*;

/**
 * This class is the Employee Database Access Object, the application will go through this to reach the 
 * database in order to manipulate employee data. (Features all CRUD methods).
 * 
 * @author Faisal Kabeer
 * @version 1.0
 */

public class EmployeeDAO {

	Connection connection = null;
	 Statement statement = null;
	 ResultSet resultSet = null;
	 PreparedStatement psmt=null;
	 private byte[] imageEmp;
	
	/**
	 * This is the {@link EmployeeDAO} constructor, it is empty
	 * 
	 */
	public EmployeeDAO()
		{
	

		}
/**
 * Produces a Connection to the Database and returns the connection
 * @return Connection 
 */
	public Connection getConnection() {
	
		try {
		Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
		System.out.println(e.getMessage());
		}

		try {
		String DataBaseURL = "jdbc:sqlite:empdb.sqlite";
		connection = DriverManager.getConnection(DataBaseURL);
		return connection;
		} catch (SQLException e) {
		System.out.println(e.getMessage());
		}
		return connection;
		}
	
/** 
 * This closes all the parameters if they were open to the database
 */
	public void closeConnection()
	{
			 try {

						if (resultSet != null) {
							resultSet.close();
						}
						if (statement != null) {
							statement.close();
						}
						if (psmt !=null)
						{
							psmt.close();
						}
						if (connection != null) {
						connection.close();
						}

			 	} catch (SQLException ex)
							{
							ex.printStackTrace();
							}
	}

/**
 * This method retrieves all employees currently within the Database
 * @return An ArrayList of employees
 * @throws SQLException 
 * 
 */
	public ArrayList<Employee> retrieve() throws SQLException {
	getConnection();
	ArrayList<Employee> allEmps = new  ArrayList<>();
	Employee temp = null;
	try {
	String query = "SELECT * FROM employees;";
	statement = connection.createStatement();
	 resultSet = statement.executeQuery(query);

	while (resultSet.next()) {
		String id = resultSet.getString("ID");
		String name = resultSet.getString("Name");
		String gender1 = resultSet.getString("Gender");
		char gender=gender1.charAt(0);
		String dob = resultSet.getString("DOB");
		String address = resultSet.getString("Address");
		String Postcode = resultSet.getString("Postcode");
		String natInscNo = resultSet.getString("NIN");
		String Title = resultSet.getString("JobTitle");
		String StartDate = resultSet.getString("StartDate");
		String Salary = resultSet.getString("Salary");
		String Email = resultSet.getString("Email");


		temp = new  Employee(id, name, gender, dob, address, Postcode, natInscNo, Salary, StartDate, Title, Email);
		allEmps.add(temp);
	}
	}finally {

    closeConnection();}
	return allEmps;
	}
	
/**
 * Selects an employee in accordance to it matching the provided String
 * @param employeeName 
 * @return Employee with the selected name
 * @throws SQLException
 */
	public Employee selectEmployeeByName(String employeeName) throws SQLException {
	getConnection();
	Employee temp = null;
	try {
	String query = "SELECT * FROM employees "+"WHERE Name LIKE'%"+employeeName+"%'";
	String DataBaseURL ="jdbc:sqlite:empdb.sqlite";
	connection = DriverManager.getConnection(DataBaseURL);
	statement = connection.createStatement();
	System.out.println(query);
	resultSet = statement.executeQuery(query);
	
	while (resultSet.next()) {

		String id = resultSet.getString("ID");
		String name = resultSet.getString("Name");
		String gender1 = resultSet.getString("Gender");
		char gender=gender1.charAt(0);
		String dob = resultSet.getString("DOB");
		String address = resultSet.getString("Address");
		String Postcode = resultSet.getString("Postcode");
		String natInscNo = resultSet.getString("NIN");
		String Title = resultSet.getString("JobTitle");
		String StartDate = resultSet.getString("StartDate");
		String Salary = resultSet.getString("Salary");
		String Email = resultSet.getString("Email");

		temp = new  Employee(id, name, gender, dob, address, Postcode, natInscNo, Salary, StartDate, Title, Email);
	}
	}finally {
    closeConnection();}
	if(temp!=null)
	{
	System.out.print(temp);
	return temp;
	}
	else{
		return null;
	}
	}

/**
 * Inserts an Employee into the database
 * @param insertEmp the employee to insert 
 * @return Boolean return true when the process is complete
 * @throws SQLException
 */
	public boolean insertEmployee(Employee insertEmp) throws SQLException {
		 getConnection();
		try {
			ArrayList<Employee> tempEmpList = retrieve();
			int x=tempEmpList.size();
		String query = "INSERT INTO employees (ID,Name,Gender,DOB,Address,Postcode,NIN,JobTitle,StartDate,Salary,Email) " +
			"VALUES ('"+Integer.toString(x)+"','"+insertEmp.getName()+"','"+insertEmp.getGender()+"','"+insertEmp.getDob()+"','"+
				insertEmp.getAddress()+"','"+insertEmp.getPostcode()+"','"+insertEmp.getNatInscNo()+"','"+insertEmp.getTitle()
				+"','"+insertEmp.getStartDate()+"','"+insertEmp.getSalary()+"','"+insertEmp.getEmail()+"')";
		String DataBaseURL ="jdbc:sqlite:empdb.sqlite";
		connection = DriverManager.getConnection(DataBaseURL);
		statement = connection.createStatement();
		System.out.println(query);
		statement.executeUpdate(query);
		}finally {
	    closeConnection();}
		return true;
	 }

/**
 * Inserts an employee at a specified Id number 
 * @param insertEmp the employee to insert 
 * @param IdNo , the ID number to insert into
 * @return Boolean to whether or not it is possible to insert into that id
 * @throws SQLException 
 */
public boolean insertEmployeeAtID(Employee insertEmp, String IdNo) throws SQLException {

	//everything has to be deleted and then reinserted into the db
	ArrayList<Employee> tempEmpList = retrieve();
	boolean retrieval =true ;


		for(int i=0; i<tempEmpList.size();i++)
		{
			Employee tempEmp= tempEmpList.get(i);
			retrieval = tempEmp.getId().contains(IdNo);
			if(retrieval==true)
			{
				break;
			}
		}
		if(retrieval==true)
		{
		System.out.println("Already exists can't enter it again");
		return false;
		}
		if(retrieval==false)
		{ getConnection();
		try {
		String query = "INSERT INTO employees (ID,Name,Gender,DOB,Address,Postcode,NIN,JobTitle,StartDate,Salary,Email)"
		+"VALUES ('"+insertEmp.getId()+"','"+insertEmp.getName()+"','"+insertEmp.getGender()+"','"+insertEmp.getDob()+"','"+
				insertEmp.getAddress()+"','"+insertEmp.getPostcode()+"','"+insertEmp.getNatInscNo()+"','"+insertEmp.getTitle()
				+"','"+insertEmp.getStartDate()+"','"+insertEmp.getSalary()+"','"+insertEmp.getEmail()+"')";
		String DataBaseURL ="jdbc:sqlite:empdb.sqlite";
		connection = DriverManager.getConnection(DataBaseURL);
		statement = connection.createStatement();
		System.out.println(query);
		statement.executeUpdate(query);

		}finally {
	    closeConnection();}
		return true;
		}
		else{
		 return false;
		}

	 }

/**
 * This method updates the employee without creating a new employee
 * @param tempEmp the employee to update
 * @throws SQLException
 */
public void updEmployee(Employee tempEmp) throws SQLException
{

	
	        String updateSQL = "UPDATE employees SET Name = ? , Gender  = ?, "
	        		+ " DOB  = ?,  Address  = ? ,  Postcode=?,  NIN  = ?"
	        		+ ",  JobTitle  = ?,  StartDate  = ?,  Salary  = ?"
	        		+ ",  Email  = ?"+ "WHERE ID=?";

	        try (PreparedStatement pstmt = getConnection().prepareStatement(updateSQL)) {

	            // setting parameters
	            pstmt.setString(1, tempEmp.getName());
	            pstmt.setString(2,String.valueOf( tempEmp.getGender()));
	            pstmt.setString(3, tempEmp.getDob());
	            pstmt.setString(4, tempEmp.getAddress());
	            pstmt.setString(5, tempEmp.getPostcode());
	            pstmt.setString(6, tempEmp.getNatInscNo());
	            pstmt.setString(7, tempEmp.getTitle());
	            pstmt.setString(8, tempEmp.getStartDate());
	            pstmt.setString(9, tempEmp.getSalary());
	            pstmt.setString(10, tempEmp.getEmail());
	            pstmt.setString(11, tempEmp.getId());
	            pstmt.executeUpdate();
	            System.out.println("updated the selected employee.");
	        }// closeConnection();
}

/**
 * Deletes the employee through the ID number 
 * @param IdNo the Id number to delete
 * @return Returns a boolean to whether the deletion is possible
 * @throws SQLException 
 */
public boolean deleteEmployeeById(String IdNo) throws SQLException {

	ArrayList<Employee> tempEmpList = retrieve();
	boolean retrieval =true ;


		for(int i=0; i<tempEmpList.size();i++)
		{
			Employee tempEmp= tempEmpList.get(i);
			retrieval = tempEmp.getId().contains(IdNo);
			if(retrieval==true)
			{
				break;
			}
		}
	if(retrieval == false)
	{

	 System.out.println("does not exist, can't delete something that is not there.");

	}
	if(retrieval == true)
	{
		getConnection();
	 try {

		 connection = DriverManager.getConnection("jdbc:sqlite:empdb.sqlite");
		 connection.setAutoCommit(false);
		 statement = connection.createStatement();
		 String sql = "DELETE from employees where ID="+IdNo+";";

		 statement.executeUpdate(sql);
		 connection.commit();
		 System.out.println(IdNo+" has been deleted succesfully ");




	}finally {

    closeConnection();}
	return true;
	}

	 return false;


	 }

/**
 * This converts an image file into a byte array output stream
 * @param file this is the the image file required to convert.
 * @return byte[] this makes it possible to insert into the SQLite table
 */
@SuppressWarnings("resource")
private byte[] readFile(String file) {
    ByteArrayOutputStream baos = null;
    try {
        File imgFile = new File(file);
        FileInputStream fis = new FileInputStream(imgFile);
        byte[] buffer = new byte[1024];
        baos = new ByteArrayOutputStream();
        for (int i; (i = fis.read(buffer)) != -1;) {
            baos.write(buffer, 0, i);
        }
    } catch (FileNotFoundException e2) {
        System.err.println(e2.getMessage());
    } catch (IOException e2) {
        System.err.println(e2.getMessage());
    }
	return baos != null ? baos.toByteArray() : null;
}

/**
 * This updates the image
 * @param empID, the id of the image needing updated
 * @param empFilePath,this is the the image file path going to be used.
 */

 public void updatePictureFilePath(String empID, String empFilePath) {
     getConnection();
	 
        String updateSQL = "UPDATE employees "+ "SET Picture = ? "+ "WHERE ID=?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(updateSQL)) {

            // set parameters
            pstmt.setBytes(1, readFile(empFilePath));
            pstmt.setString(2, empID);


            pstmt.executeUpdate();
            System.out.println("Stored the file in the BLOB column.");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
     }
 
 /**
  * This stores the image into the picture column of the SQLite database
  * @param empID
  * @param empImg
  */
 public void updatePictureByte(String empID, byte[] empImg) {
     getConnection();

        String updateSQL = "UPDATE employees "+ "SET Picture = ? "+ "WHERE ID=?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(updateSQL)) {

            pstmt.setBytes(1, empImg);
            pstmt.setString(2, empID);

            pstmt.executeUpdate();
            System.out.println("Stored the file in the BLOB column.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
   
    }


/**
 * Get the Byte array at the selected ID
 * @param empID Find the 
 * @return byte[] this return the byte array so that it can be converted into a image.
 * @throws SQLException
 */
	public byte[] getPicture(String empID ) throws SQLException  {
    

        String selectSQL = "SELECT Picture FROM employees WHERE ID=?";

        getConnection();
            psmt = connection.prepareStatement(selectSQL);
            psmt.setString(1, empID);
            resultSet = psmt.executeQuery();

         imageEmp=null;

            while (resultSet.next()) {

                imageEmp=resultSet.getBytes("Picture");
            }

            if(imageEmp==null)
            {
            	return null;
            }

            empID=null;
          
                 return imageEmp;

    }
/**
 * Remove the picture at the provided ID number
 * @param empID the image will be removed at this ID
 */
	 public void removePicture(String empID) {
	     getConnection();
		 
	        String updateSQL = "UPDATE employees "+ "SET Picture = ? "+ "WHERE ID=?";

	        try (PreparedStatement pstmt = getConnection().prepareStatement(updateSQL)) {
 
	            pstmt.setBytes(1, null);
	            pstmt.setString(2, empID);


	            pstmt.executeUpdate();
	            System.out.println("nulled the BLOB column of "+ empID);


	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    
	    }




}	

	