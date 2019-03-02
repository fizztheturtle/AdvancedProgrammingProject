
/**
 * This {@link Employee} class is being used as a way of entering employee details before going into SQLite database.
 * 
 * @author Faisal Kabeer
 * @version 1.0
 */
public class Employee extends Person{
 public String id;
 public String salary;
 public String startDate;
 public String title;
 public String email;

	/**
	 * Constructor setting all the variables and calling the super for the person class.
	 * @param newId
	 * @param name
	 * @param gender
	 * @param dob
	 * @param address
	 * @param Postcode
	 * @param natInscNo
	 * @param newSalary
	 * @param newStartDate
	 * @param newTitle
	 * @param newEmail
	 */
	 public Employee(String newId, String name, char gender, String dob, String address ,
			 String Postcode,String natInscNo, String newSalary, String newStartDate,String newTitle, String newEmail)
	{
	
	 super(name, gender, natInscNo, dob, address, Postcode);
	 this.id=newId;
	 this.salary=newSalary;
	 this.startDate=newStartDate;
	 this.title=newTitle;
	 this.email=newEmail;
	 
	 }
	 /**
	  * Sets the employees Email Address
	  * @param newEmail
	  */
  	 public void setEmail(String newEmail){
	 this.email = newEmail;
	 }
  	 /**
	  * Gets the employees Email Address
	  * @return This returns the employees email
	  */
  	 public String getEmail(){
	 return this.email;
	 }
  	 /**
	  * Sets the employees Job title
	  * @param newtitle
	  */
	 public void setTitle(String newtitle){
	 this.title = newtitle;
	 }
	 /**
	  * Gets the employees Job title
	  * @return This returns the Job title
	  */
	 public String getTitle(){
	 return this.title;
	 }
	 /**
	  * Sets the employees Id number
	  * @param newId
	  */
	 public void setId(String newId){
	 this.id = newId;
	 }
	 /**
	  * Gets the employees ID number
	  * @return This returns the Id Number
	  */
	 public String getId(){ 
	 return this.id;
	 }
	 /**
	  * Sets the employees salary
	  * @param salary
	  */	 
	 public void setSalary(String salary){
	 this.salary = salary;
	 }
	 /**
	  * Gets the employees Salary
	  * @return This returns the Salary
	  */
	 public String getSalary(){
	 return this.salary;
	 }
	 /**
	  * Sets the employees Start Date
	  * @param newStartDate
	  */
	 public void setStartDate(String newStartDate){
	 this.startDate =newStartDate;
	 }
	 /**
	  * Gets the employees Start Date
	  * @return This returns the employees Start Date
	  */
	 public String getStartDate(){
	 return this.startDate;
	 }
	
	 
	 /**
	  * 
	  * 
	  * The employee is returned with all other variables required for this class.
	  */
	 public String toString(){
	 return "ID: "+ getId()+ " Name: " + super.getName()+" Gender: "+getGender() +
			" DOB:"+super.getDob()
	 +" Address: "+super.getAddress()+ " Postcode: "+super.getPostcode()+ 
	 " Nationial Insurance Number: "+super.getNatInscNo()+" JobTitle: "+ getTitle()+
	 " Start Date: " + getStartDate()+ 
	 " Salary: " + this.getSalary() + " Email: "+getEmail()+".";
	 } 
	}
	