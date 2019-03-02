/**
 * This class{@link Person} is produce so that the base of the employee class contains the core details of a person.
 * 
 * 
 * @author Faisal Kabeer
 * @version 1.0
 */
public class Person {

	
		 public String name;
		 public char gender; 
		 public String natInscNo;
		 public String dob; 
		 public String address;
		 public String Postcode;
	
		 /**
		  * The constructor for the Person class.
		  * Takes in all core data of a person. 
		  */
		 public Person(String newName,char newGender,String newnatInscNo,String newDob, String newAddress, String newPostcode)
		 {
		 this.name = newName;
		 this.gender=newGender;
		 this.natInscNo=newnatInscNo;
		 this.dob=newDob;
		 this.address = newAddress;
		 this.Postcode = newPostcode;
		
		 }
		 
		 /**Sets the persons' name 
		  * @param newName
		  * 
		  */
			 public void setName(String newName){
			 this.name = newName;
			 }
		 /**gets the persons' name 
		  * @return persons' name
		  */
			 public String getName(){
			 return this.name;
			 }
			 
		 /**Sets the persons' gender 
		  * @param newGender
		  * 
		  */
			 public void setGender(char newGender){
			 this.gender = newGender;
			 }
		 /**gets the persons' gender 
		  * @return persons' gender
		  */ 
			 public char getGender(){
			 return this.gender;
			 }
		 /**Sets the persons' national insurance number 
		 * @param newNatInscNo
		 */
			 public void setNatInscNo(String newNatInscNo){
			 this.natInscNo = newNatInscNo;
			 }
		 /**gets the persons' national insurance number 
		 * @return persons' newNatInscNo
		 */
			 public String getNatInscNo(){
			 return this.natInscNo;
			 }
		 /**Sets the persons' Date of Birth 
		  * @param newDob
		  * 
		  */
		     public void setDob(String newDob){
			 this.dob = newDob;
			 }
		 /**gets the persons' Date Of Birth 
		  * @return persons' Date of Birth
		  */ 
			 public String getDob(){
			 return this.dob;
			 }
		/**Sets the persons' Address
		  * @param newAddress
		  * 
		  */ 
		 	 public void setAddress(String newAddress){
			 this.address = newAddress;
			 }
		 /**gets the persons' Address
		  * @return persons' Address
		  */ 
		 	 public String getAddress(){
			 return this.address;
			 }
			 /**Sets the persons' Postcode
		  * @param newPostcode
		  * 
		  */ 
		 	 public void setPostcode(String newPostcode){
			 this.Postcode = newPostcode;
			 } 
		 /**gets the persons' Postcode 
		  * @return persons' Postcode
		  */ 
		 	 public String getPostcode(){
			 return this.Postcode;
			 }
			 
	
		
			 /**
			  * @return The person is returned with all their details from this class.
			  */
		 	 public String toString(){
			 return "Name :"+getName()+" Gender: "+getGender() +
					 " Nationial Insurance Number:"+getNatInscNo()+" DOB:"+getDob()
					 +" Address"+getAddress()+ " Postcode"+getPostcode();
			 }
}
