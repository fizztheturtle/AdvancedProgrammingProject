import java.sql.SQLException;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The Main Form class Contains all the features for the Swing application form allowing the user to access
 * the database through a form.
 * @author Faisal Kabeer
 * @version 1.0
 */



@SuppressWarnings("serial")
public class MainForm extends JFrame {

	private EmployeeDAO dao=new EmployeeDAO();
	private ArrayList<Employee> emps ;
	//used to keep a record of Current employee on display
	
	private String sCurrEmp;
    private int returnValChosen;
	private int widthLen=350;
	private int heightLen=410;
	public int currEmp;
    //e=Enter, s=Select, d=Display
	
	private JLabel eName,sGender,sDob,eSalary,eAddress,
	ePostcode,eNatINo,eEmail,sStartDate,eJobTitle,sName,empPicLabel,dEmpID;
	
	
	private JTextField eNameField,eSalaryField,eNationalInsField,eAddressField,
	ePostcodeField,eEmailField,eJobTitleField;
	
	
	private JRadioButton MaleButton,FemaleButton;

	
	private ButtonGroup group;
	private JButton enterButton,clearButton,prevButton,forwButton;

	
	private JComboBox<String> dobDaysComboBox, dobMonthsComboBox;

	private JComboBox<Object> dobYearsComboBox;
	 
	private JComboBox<String> startDaysComboBox,startMonthsComboBox;
	private JComboBox<Object> startYearsComboBox;
	
	private boolean editEmpDB,addEmpDB;

	private JPanel West,Center,East,panelButtons,dobDateCB,startDateCB,prevForwDisp;
 
	private JMenuBar menuBar;
	private JMenu mRecord,mAddFile;
	private JMenuItem mAddEmpPic,mAddEmp,mEditEmp,mdeleteEmp,mdeleteEmpPic;
	private JFileChooser empPicChooser;
	private FileNameExtensionFilter imgOnly;
	
	private boolean editDB;

	
	private Image empJPG;
	private ImageIcon empIcon;
	private Image image;
	private Image newImg;
	
	private byte[] empPic;
	
	
	public ArrayList<Employee> getDBEmps() throws SQLException
	{
		emps =dao.retrieve();
		
		return emps;
	}//returns all employees in the database
	
	private void getFirstEmp()
	{
		sCurrEmp = emps.get(0).id;
		 currEmp=Integer.parseInt(sCurrEmp);
	}//returns first employee id number
	
	private int getLastEmp()
	{
		 int ilastEmp = emps.size()-1;
		return ilastEmp;
	}//returns last id number of database
	
	/**
	 * Sets the label on the JLabels.
	 */
	private void labelInfo()
	{
		dEmpID = new JLabel("Employee ID: "+currEmp);
		eName = new JLabel("Enter Name:");
		sGender = new JLabel("Select Gender:");
		sDob = new JLabel("Select Date Of Birth:");
		eSalary= new JLabel("Enter Salary:");
		eNatINo= new JLabel("Enter National Insurance Number:");
		eEmail = new JLabel("Enter Email:");
		sStartDate = new JLabel("Select Start Date:");
		eJobTitle = new JLabel("Enter Job Title:");
		eAddress=new JLabel("Enter Address:");
		ePostcode=new JLabel("Enter Postcode");		
		sName = new JLabel("Enter Employee Information");
		sName.setFont(new Font("Calibri", Font.PLAIN, 24));
		empPicLabel = new JLabel();//For use as container of employee picture 
		
	}
	
	/**
	 * Sets the size of textFields
	 */
	private void textField()
	{
		eNameField = new JTextField(18);
		eSalaryField = new JTextField(200);
		eNationalInsField = new JTextField(200);
		eEmailField = new JTextField(200);
		eJobTitleField = new JTextField(200);
		eAddressField = new JTextField(200);
		ePostcodeField = new JTextField(200);
	}
	
	/**
	 * This displays all the data of the current Employee
	 * @param currEmpl is the current employee
	 * @throws SQLException
	 */
	public void dispDataField(int currEmpl) throws SQLException
	{
		if(emps.get(currEmpl)==null)
			{return;}
		dEmpID.setText("Employee ID: "+ currEmpl );
		Employee temp=emps.get(currEmpl);
		eNameField.setText(temp.name);
		eSalaryField.setText(temp.salary);
		eNationalInsField.setText(temp.natInscNo);
		eEmailField.setText(temp.email);
		eJobTitleField.setText(temp.title);
	    if(temp.gender=='F')
		{
		 FemaleButton.setSelected(true);
		}
		else if(temp.gender=='M')
		{
		 MaleButton.setSelected(true);
		} 
		eAddressField.setText(temp.address);
		ePostcodeField.setText(temp.Postcode);
		comboBoxUpdate();
		loadImgDB(currEmpl);
	}
	
	
	/**
	 * creates the combo box for Date of birth and start date
	 */
	private void comboBoxCreation()
	{
		 String[] days= {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", 
		          "12", "13", "14", "15", "16", "17", "18", "19", "20", 
		          "21", "22", "23", "24", "25", "26", "27", "28", "29", 
		          "30", "31"};
		 String[] months = {"01", "02", "03", "04", "05", "06",
		           "07", "08", "09", "10", "11",
		           "12"};
		
		 
		 
		 ArrayList<String> employableYears = new ArrayList<String>();
		 
		  
		 
		 int overEighteen= Calendar.getInstance().get(Calendar.YEAR)-18; //PREVENTS UNDER 18 FROM ENETRING DATA
		        
		 for(int years = overEighteen; years>=1955; years--)
		 {employableYears.add(years+"");}
		        
		 dobDaysComboBox = new JComboBox<String>(days);
		 dobMonthsComboBox = new JComboBox<String>(months);
		 dobYearsComboBox = new JComboBox<Object>(employableYears.toArray());
			    
				
		 ArrayList<String> employedYears = new ArrayList<String>();
		 for(int years = Calendar.getInstance().get(Calendar.YEAR); years>=1999; years--)
		 {employedYears.add(years+"");}
		 startDaysComboBox = new JComboBox<String>(days);
		 startMonthsComboBox = new JComboBox<String>(months);
		 startYearsComboBox = new JComboBox<Object>(employedYears.toArray());
		 
		
	}
	/**
	 * Using the current employee it updates the combo boxes 
	 * @throws SQLException
	 */
	private void comboBoxUpdate() throws SQLException
	{
		if(emps.get(currEmp)==null)
	{return;}
		else{
		Employee temp=emps.get(currEmp);
		String startDayStr=Character.toString(temp.startDate.charAt(0))+Character.toString(temp.startDate.charAt(1));
		String startMonStr=Character.toString(temp.startDate.charAt(3))+Character.toString(temp.startDate.charAt(4));
		String startYeaStr=Character.toString(temp.startDate.charAt(6))+Character.toString(temp.startDate.charAt(7))+
				Character.toString(temp.startDate.charAt(8))+Character.toString(temp.startDate.charAt(9));
		
		startDaysComboBox.getModel().setSelectedItem(startDayStr);
		startMonthsComboBox.getModel().setSelectedItem(startMonStr);
		startYearsComboBox.getModel().setSelectedItem(startYeaStr);

		String dobDaysStr=Character.toString(temp.dob.charAt(0))+Character.toString(temp.dob.charAt(1));
		String dobMonthdStr=Character.toString(temp.dob.charAt(3))+Character.toString(temp.dob.charAt(4));
		String dobYearStr=Character.toString(temp.dob.charAt(6))+Character.toString(temp.dob.charAt(7))+
				Character.toString(temp.dob.charAt(8))+Character.toString(temp.dob.charAt(9));
		
		dobDaysComboBox.getModel().setSelectedItem(dobDaysStr);
		dobMonthsComboBox.getModel().setSelectedItem(dobMonthdStr);
		dobYearsComboBox.getModel().setSelectedItem(dobYearStr);		
	
	
		}
	}
	

	
	 
	
	
	
	private void buttons() throws SQLException
	{
		 //Creates the radio buttons.
	    MaleButton = new JRadioButton("Male");
	    MaleButton.setMnemonic(KeyEvent.VK_B);
	    MaleButton.setActionCommand("M");
	    MaleButton.setSelected(true);

	    FemaleButton = new JRadioButton("Female");
	    FemaleButton.setMnemonic(KeyEvent.VK_C);
	    FemaleButton.setActionCommand("F");
		
	    group = new ButtonGroup();
	    group.add(MaleButton);
	    group.add(FemaleButton);
		
	    
	    
	    enterButton = new JButton("Enter");
//Depending on what was chosen before the enter button has several different actions, from adding to updating data. 
	 
	    enterButton.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	   try {
	    	testDataEntered();
	    	if(testDataEntered()==false)
	    	{
	    		clearData();
				dispDataField(currEmp);
	    	}
	    	else
	    	{
	    	
	    	 if(editEmpDB==true)
	    	{
	    	
	    	
	  
	    		String StartDate= String.valueOf(startDaysComboBox.getSelectedItem())+"/"
	    		    	+String.valueOf(startMonthsComboBox.getSelectedItem())
	    		    	+"/"+String.valueOf(startYearsComboBox.getSelectedItem());
	    		    	
	    		    	String dob= String.valueOf(dobDaysComboBox.getSelectedItem())+"/"
	    		    	+String.valueOf(dobMonthsComboBox.getSelectedItem())
	    		    	+"/"+String.valueOf(dobYearsComboBox.getSelectedItem());
	    		
					
				
	    		Employee tempEmpl=null;
		    	tempEmpl = new  Employee(Integer.toString(currEmp), eNameField.getText(), group.getSelection().getActionCommand().charAt(0), 
		    			dob, eAddressField.getText(), ePostcodeField.getText(), eNationalInsField.getText(),
		    			eSalaryField.getText(), StartDate, eJobTitleField.getText(),eEmailField.getText());
		    	dao.updEmployee(tempEmpl);
		    	System.out.println(tempEmpl.getName());

		    	emps=dao.retrieve();
		    	dispDataField(currEmp);
	    	}
	    	
	    	else if(addEmpDB==true)
	    	{
	    		
	    		currEmp=getLastEmp();
	    	currEmp++;
	    		String StartDate= String.valueOf(startDaysComboBox.getSelectedItem())+"/"
	    		    	+String.valueOf(startMonthsComboBox.getSelectedItem())
	    		    	+"/"+String.valueOf(startYearsComboBox.getSelectedItem());
	    		    	
	    		    	String dob= String.valueOf(dobDaysComboBox.getSelectedItem())+"/"
	    		    	+String.valueOf(dobMonthsComboBox.getSelectedItem())
	    		    	+"/"+String.valueOf(dobYearsComboBox.getSelectedItem());
	    		Employee temp=null;
		    	temp = new  Employee(Integer.toString(dao.retrieve().size()), eNameField.getText(), group.getSelection().getActionCommand().charAt(0), 
		    			dob, eAddressField.getText(), ePostcodeField.getText(), eNationalInsField.getText(),
		    			eSalaryField.getText(), StartDate, eJobTitleField.getText(),eEmailField.getText());
		    	
		    	dao.insertEmployee(temp);
		    	
		      	emps=dao.retrieve();
		      	dispDataField(getLastEmp());
	    	}
	    	 
	    	
	    	
	    	}
	    		    	
	    	
	    	editDB=false;
	    	editEmpDB=false;
	    	addEmpDB=false;
	    	
	    	prevForwButtonDisp();
	    	editSQLiteDB();
	  
	    	} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
	    
	    	}
	    	});
	    
	    
	    clearButton= new JButton("Clear");
	    clearButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	           clearData();
	           if(addEmpDB==true)
	           {
	        	   try {
					dispDataField(currEmp);
					editEmpDB=false;
					addEmpDB=false;
					prevForwButtonDisp();
				} catch (SQLException e1) {
				
					e1.printStackTrace();
				}
	        	   
	           }
	        }
	    });
	    forwButton= new JButton("Next");
	    prevButton= new JButton("Previous");
	  
	    prevForwButtonControl n=new prevForwButtonControl();
	    forwButton.addActionListener(n);
	    prevButton.addActionListener(n);
	    forwButton.setPreferredSize(new Dimension(0, 35));
	    prevButton.setPreferredSize(new Dimension(0, 35));
	    prevForwDisp=new JPanel(new GridLayout(0,2,10,10));
	    prevForwDisp.add(prevButton);
	    prevForwDisp.add(forwButton);
	    prevForwButtonDisp();
	    
	}
	
	private class prevForwButtonControl implements ActionListener
	{
	public void actionPerformed(ActionEvent e)
	{
	 	 
		if(e.getSource()==prevButton)
		{
			try {
	    		if(currEmp>0)
	    		{
	    		currEmp--;
		    	
	    		dispDataField(currEmp);
					loadImgDB(currEmp);
		    	forwButton.setEnabled(true);
	    		}
	    	    if(currEmp<1)
	        	{	    			
	    			prevButton.setEnabled(false);
	        	}
	    	    	
	    		} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
		}
		
		else if(e.getSource()==forwButton)
		{
			try {
				
				   if(currEmp<=(emps.size()-2)){
				   	
			 		currEmp++;
			 		dispDataField(currEmp);
				    	loadImgDB(currEmp);
				      	prevButton.setEnabled(true);
				    	
				    		}
				   
				   if(currEmp==(emps.size()-1))
				    		{
				    			forwButton.setEnabled(false);
				    		}
				    		
				    	} catch (SQLException e1) {
								
								e1.printStackTrace();
						}
		}
	
	}
	}
	
	/**
	 * Controls the buttons whether they should be on or off depending on what the current employee is.
	 * @throws SQLException
	 */
	private void prevForwButtonDisp() throws SQLException
	{
		if(currEmp==(emps.size()-1))
		{
	    	forwButton.setEnabled(false);
	    	prevButton.setEnabled(true);
		}
	 else if(currEmp>1)
	 	{	    			
				prevButton.setEnabled(true);
				forwButton.setEnabled(true);
	 	}
	 else if(currEmp<=0)
	 	{	    			
				prevButton.setEnabled(false);
				forwButton.setEnabled(true);
	 	}
		
		
	}
	
	/**
	 * This resets all data 
	 */
	private void clearData()
	{
		eNameField.setText(null);
		eSalaryField.setText(null);
		eNationalInsField.setText(null);
		eEmailField.setText(null);
		eJobTitleField.setText(null);
		eAddressField.setText(null);
		ePostcodeField.setText(null);	
		dobDaysComboBox.setSelectedIndex(0);
		dobMonthsComboBox.setSelectedIndex(0);
		dobYearsComboBox.setSelectedIndex(0);
		startDaysComboBox.setSelectedIndex(0);
		startMonthsComboBox.setSelectedIndex(0);
		startYearsComboBox.setSelectedIndex(0);
		group.clearSelection();
	}
	/**
	 * Tests all the data that is being inputed to make sure there are no incorrect data being entered into the database
	 * @return Boolean true if data is corect false if otherwise
	 */
	private boolean testDataEntered() throws SQLException
	{
		Pattern emailP = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
		Matcher matchEmail=emailP.matcher(eEmailField.getText());
		
		String dayDOB= (String) dobDaysComboBox.getModel().getSelectedItem();
		String monthDOB= (String) dobMonthsComboBox.getModel().getSelectedItem();
		String yearDOB= (String) dobYearsComboBox.getModel().getSelectedItem();
		
		String daySD= (String) startDaysComboBox.getModel().getSelectedItem();
		String monthSD= (String) startMonthsComboBox.getModel().getSelectedItem();
		String yearSD= (String) startYearsComboBox.getModel().getSelectedItem();
		
		if(eNameField.equals("")||Pattern.matches("[a-zA-Z]+", eNameField.getText()))
			//take the text matches and put it at the top
		{

			JOptionPane.showMessageDialog(this,
					"Can not enter a single word , or contain numerical values as a name.",
					"Name error",
					JOptionPane.ERROR_MESSAGE);
			
			return false;
		}
		else if(eSalaryField.getText().equals("")||Pattern.matches("[0-9]", eSalaryField.getText()))
		{

			JOptionPane.showMessageDialog(this,
					"Can not enter any alphabetical values in a salary. Must only contain numerical values.",
					"Salary error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		
		/*
		A-CEGHJ-NOPR-TW-Z followed by 6 digits, followed by an optional upper case letter (A through D)
		[ACEGHJNOPRTWZ]//first 2 letters
		[0-9]//next 6 digits
		[A-D]//last letter
		  */
		
		else if(eNationalInsField.getText().equals("")||!Pattern.matches("^[A-CEGHJ-PR-TW-Z]{1}[A-CEGHJ-NPR-TW-Z]{1}"
				+ "[0-9]{6}[A-DFM]{0,1}$", eNationalInsField.getText()))
		{
			JOptionPane.showMessageDialog(this,
					"Can not enter this as a National Insurance Number format. Must be a correct"
					+ " National Insurance Number",
					"National Insurance Number error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(eEmailField.getText().equals("")||!matchEmail.find())
		{
			JOptionPane.showMessageDialog(this,
					"Must contain an @ symbol as well be a correct format of an email.",
					"Email error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(eJobTitleField.getText().equals("")||Pattern.matches("[a-zA-Z]", eJobTitleField.getText()))
		{
			JOptionPane.showMessageDialog(this,
					"Can not enter any numerical values as the job title must be made up alpha characters.",
					"Job title error",
					JOptionPane.ERROR_MESSAGE);
			
			return false;
		}
		else if(eAddressField.getText().equals("")||Pattern.matches("[a-zA-Z]", eAddressField.getText()))
		{
			JOptionPane.showMessageDialog(this,
					"Can not enter an address with numerical values. Must only contain alpha characters.",
					"Address Field error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(ePostcodeField.getText().equals("")||Pattern.matches("[a-zA-Z_0-9]+", ePostcodeField.getText()))
		{
			JOptionPane.showMessageDialog(this,
					"Can not enter an incorrect format of a postcode, must contain 2 seperate parts"
					+ " and contain no speical symbols.",
					"PostCode error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(group.getSelection()==null)
		{
			JOptionPane.showMessageDialog(this,
					"Must select a gender.",
					"Gender error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
	else if(Integer.parseInt(monthDOB)==2 && Integer.parseInt(dayDOB)>28
			||(Integer.parseInt(monthSD)==2 && Integer.parseInt(dayDOB)>28))
	{
		JOptionPane.showMessageDialog(this,
				"Feburary only has 28 days",
				"Date error",
				JOptionPane.ERROR_MESSAGE);	
		return false;
		
	}
	else if(Integer.parseInt(monthDOB)==4 && Integer.parseInt(dayDOB)>30
			||(Integer.parseInt(monthSD)==4 && Integer.parseInt(dayDOB)>30))
	{
		JOptionPane.showMessageDialog(this,
				"April only has 30 days",
				"Date error",
				JOptionPane.ERROR_MESSAGE);	
		return false;
		
	}
	else if(Integer.parseInt(monthDOB)==6 && Integer.parseInt(dayDOB)>30
			||(Integer.parseInt(monthSD)==6 && Integer.parseInt(dayDOB)>30))
	{
		JOptionPane.showMessageDialog(this,
				"June only has 30 days",
				"Date error",
				JOptionPane.ERROR_MESSAGE);	
		return false;
		
	}
	else if(Integer.parseInt(monthDOB)==9 && Integer.parseInt(dayDOB)>30
			||(Integer.parseInt(monthSD)==9&&Integer.parseInt(dayDOB)>30))
	{
		JOptionPane.showMessageDialog(this,
				"September only has 30 days",
				"Date error",
				JOptionPane.ERROR_MESSAGE);	
		return false;
		
	}
	else if((Integer.parseInt(monthDOB)==11 && Integer.parseInt(dayDOB)>30)
			||(Integer.parseInt(monthSD)==11&&Integer.parseInt(dayDOB)>30))
	{
		JOptionPane.showMessageDialog(this,
				"November only has 30 days",
				"Date error",
				JOptionPane.ERROR_MESSAGE);	
		return false;
		
	}
				
		
		
		
	else if(Integer.parseInt(yearDOB)== Calendar.getInstance().get(Calendar.YEAR)-18)
		{

			if(Integer.parseInt(monthDOB)-1>=Calendar.getInstance().get(Calendar.MONTH)&&
					Integer.parseInt(dayDOB)>Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
		{
			JOptionPane.showMessageDialog(this,
					"Date Of Birth must be older than 18 years old.",
					"DOB error",
					JOptionPane.ERROR_MESSAGE);	
		return false;
		}
				return true;
		}
		
			else if(Integer.parseInt(yearSD)== Calendar.getInstance().get(Calendar.YEAR))
		{
			 
			  if(Integer.parseInt(monthSD)-1==Calendar.getInstance().get(Calendar.MONTH))
				{
					if(Integer.parseInt(daySD)>Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
					{
						JOptionPane.showMessageDialog(this,
								"Start Date must be before today.",
								"Start Date error",
								JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}	
				
			 else if(Integer.parseInt(monthSD)-1 > Calendar.getInstance().get(Calendar.MONTH))
			{
				JOptionPane.showMessageDialog(this,
				"Start Date must be before today.",
						"Start Date error",
						JOptionPane.ERROR_MESSAGE);
			 return false;
			}	
		
				return true;
		}
		
		else
		{
			return true;
		}	
	}
	
	private void editSQLiteDB()
	{
	
		 if(editDB==false)
			{
				eNameField.setEditable(false);
				eSalaryField.setEditable(false);
				eNameField.setEditable(false);
				eNationalInsField.setEditable(false);
				eEmailField.setEditable(false);
				eJobTitleField.setEditable(false);
				eAddressField.setEditable(false);
				ePostcodeField.setEditable(false);
				enterButton.setEnabled(false);
				clearButton.setEnabled(false);
				dobDaysComboBox.setEnabled(false);
				dobMonthsComboBox.setEnabled(false);
				dobYearsComboBox.setEnabled(false);
				startDaysComboBox.setEnabled(false);
				startMonthsComboBox.setEnabled(false);
				startYearsComboBox.setEnabled(false);
				MaleButton.setEnabled(false);
				FemaleButton.setEnabled(false);
			}
		 else if(editDB==true)
			{
				eNameField.setEditable(true);
				eSalaryField.setEditable(true);
				eNameField.setEditable(true);
				eNationalInsField.setEditable(true);
				eEmailField.setEditable(true);
				eJobTitleField.setEditable(true);
				eAddressField.setEditable(true);
				ePostcodeField.setEditable(true);
				enterButton.setEnabled(true);
				clearButton.setEnabled(true);
				dobDaysComboBox.setEnabled(true);
				dobMonthsComboBox.setEnabled(true);
				dobYearsComboBox.setEnabled(true);
				startDaysComboBox.setEnabled(true);
				startMonthsComboBox.setEnabled(true);
				startYearsComboBox.setEnabled(true);
				MaleButton.setEnabled(true);
				FemaleButton.setEnabled(true);
			}

	
		
	}
	

	/**
	 * This loads the image from the database for the current employee
	 * @param currEmpID, the current employee to find the image of
	 * @throws SQLException
	 */
	private void loadImgDB(int currEmpID) throws SQLException
	{
	empPic=null;
	empJPG=null;
	empIcon=null;
	image=null;
	newImg=null;
	empIcon=null;
	empPicLabel.setIcon(null);
	empPicLabel.setText("  ");
	
	empPic=dao.getPicture(Integer.toString(currEmpID));
		if(empPic==null)
		{
			empPicLabel.setText("You need to set an image through File(menu bar)");
			empPicLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		}	
		else{
	 empJPG=Toolkit.getDefaultToolkit().createImage(empPic);
	 empIcon =new ImageIcon(empJPG);
	 image = empIcon.getImage(); // transform the image
	 newImg = image.getScaledInstance(widthLen, heightLen,  java.awt.Image.SCALE_SMOOTH); // scale the image to a smooth effect over speed  
	 empIcon = new ImageIcon(newImg);  // transform it back to an icon
	 empPicLabel.revalidate();
	 empPicLabel.setIcon(empIcon);
		
		}
	}
	/**
	 * Left hand side of the application
	 */
	private void createPanelWest()
	{
		West = new JPanel(new GridLayout(11,1,4,4));
		West.add(eName);
		West.add(sGender);
		West.add(sDob);
		West.add(eAddress);
		West.add(ePostcode);
		West.add(eSalary);
		West.add(eNatINo);
		West.add(eEmail);
		West.add(sStartDate);
		West.add(eJobTitle);
		West.add(enterButton);
	}
	/**
	 * Middle of application
	 */
	private void createPanelCenter()
	{  Center = new JPanel(new GridLayout(11,1,4,4));
		startDateCB = new JPanel(new GridLayout(0,3));
		startDateCB.add(startDaysComboBox);
		startDateCB.add(startMonthsComboBox);
		startDateCB.add(startYearsComboBox);
		
		panelButtons = new JPanel(new FlowLayout(10));
		panelButtons.add(MaleButton);
		panelButtons.add(FemaleButton);
		
		dobDateCB = new JPanel(new GridLayout(0,3));
		dobDateCB.add(dobDaysComboBox);
		dobDateCB.add(dobMonthsComboBox);
		dobDateCB.add(dobYearsComboBox);
		
		Center.add(eNameField);
		Center.add(panelButtons);
		Center.add(dobDateCB);
		Center.add(eAddressField);
		Center.add(ePostcodeField);		
		Center.add(eSalaryField);
		Center.add(eNationalInsField);
		Center.add(eEmailField);
		Center.add(startDateCB);
		Center.add(eJobTitleField);
		Center.add(clearButton);
	}
	/**
	 * Right hand side of the application
	 * @throws SQLException
	 * @throws IOException
	 */
	private void createPanelEast() throws SQLException, IOException
	{setLayout(new GridBagLayout());
		East = new JPanel(new GridBagLayout());
	
		GridBagConstraints gbC = new GridBagConstraints();
		
		
				
		gbC.gridx=0;
		gbC.gridy=0;
		gbC.gridwidth=1;
		gbC.gridheight=1;
		gbC.insets = new Insets(0,0,20,0); 
				
		East.add(dEmpID,gbC);
	    Border border = BorderFactory.createLineBorder(Color.black);
	    border.paintBorder(empPicLabel, getGraphics(), 0, 0, widthLen, heightLen);
	    empPicLabel.setBorder(border);

		empPicLabel.setPreferredSize(new Dimension(widthLen,heightLen));
		empPicLabel.setMinimumSize(new Dimension(widthLen,heightLen));
		empPicLabel.setMaximumSize(new Dimension(widthLen,heightLen));
		gbC.gridx=0;
		gbC.gridy=1;
		gbC.gridwidth=5;
		gbC.gridheight=4;
		loadImgDB(currEmp);
        East.add(empPicLabel,gbC);
	
       
        gbC.gridx=0;
		gbC.gridy=5;
		gbC.gridwidth=10;
		gbC.gridheight=1;
		gbC.fill=GridBagConstraints.BOTH;

		East.add(prevForwDisp,gbC);
	
		

	}
	
	/**
	 * This opens dialog boxes allowing the user to search through the database by typing a name.
	 * @throws SQLException
	 */
	public void SearchDB() throws SQLException
	{
		String found = null;
		String name = JOptionPane.showInputDialog(this, "Type a name to search DataBase");
		if(name==null){
			JOptionPane.showMessageDialog(this,
					"No Name has been found with the letters you have entered",
					"No Name Found",
					JOptionPane.ERROR_MESSAGE);
		}
		else{String[] nameSearch=new String[emps.size()];
		int x=0;
		for(Employee i : emps) {
			if(i.getName().toLowerCase().contains(name.toLowerCase())) {
				nameSearch[x]=i.getName();
				x=x+1;
			}
			else if (x==0){
				
			}
		}
		
		String matchedEmps = (String) JOptionPane.showInputDialog(this, 
		        "These are the matching employees",
		        "Employee Search",
		        JOptionPane.QUESTION_MESSAGE, 
		        null, 
		        nameSearch, 
		        nameSearch[0]);
		
		if(matchedEmps==null)
		{
			JOptionPane.showMessageDialog(this,
					"You decided to cancel",
					"Cancel",
					JOptionPane.ERROR_MESSAGE);
		}
		else{
		for(Employee i : emps) 
		{
			if(i.getName().contains(matchedEmps))
			{
				found=i.getId();
			}
		}
		currEmp=Integer.parseInt(found);
		dispDataField(currEmp);
	
		}
		}
	}
	

	/**
	 * This creates the menuBar for the form application with all the seperate parts of the menu
	 * @param form1
	 */
		public void menuComponents(JFrame form1)
		{
			


		
		menuBar = new JMenuBar();
		mAddFile = new JMenu("File");
		mAddFile.setMnemonic(KeyEvent.VK_ALT);

		menuBar.add(mAddFile);
			
		mAddEmpPic = new JMenuItem("Select image for Employee");
		mAddFile.add(mAddEmpPic);	
					imgOnly = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg","gif");
		mAddEmpPic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	empPicChooser = new JFileChooser();
            	empPicChooser.setFileFilter(imgOnly);  
            	empPicChooser.setAcceptAllFileFilterUsed(false);
            	//empPicChooser.showOpenDialog(form1);
            	empPicChooser.setDialogTitle("Choose an image for selected Employee");
            	returnValChosen = empPicChooser.showOpenDialog(null);
            	
			    if (returnValChosen == JFileChooser.APPROVE_OPTION) {
			      File selectedFile = empPicChooser.getSelectedFile();
			      System.out.println("you have chosen: " + selectedFile);
			      String fileStr=selectedFile.toString();
			      dao.updatePictureFilePath(Integer.toString(currEmp),fileStr);
			      
			    }
			    try {
					loadImgDB(currEmp);
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
            }
            });
		
		
		
		
		//Building the first menu.
		mRecord = new JMenu("Record");
		mRecord.setMnemonic(KeyEvent.VK_ALT);
		
JMenuItem miSearch = mRecord.add(new JMenuItem("Search"));

		miSearch.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				try {
					SearchDB();
					prevForwButtonDisp();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}

			}

		});
		menuBar.add(mRecord);
		
		mAddEmp = new JMenuItem("Add Employee");
		
		mAddEmp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clearData();
				forwButton.setEnabled(false);
	    		prevButton.setEnabled(false);
				addEmpDB=true;
				editDB=true;
				
					try {
						loadImgDB(getLastEmp()+1);
					} catch (SQLException e1) {
				
						e1.printStackTrace();
					}
			
				editSQLiteDB();
				
		    	}
		    	});
		
		
		mRecord.add(mAddEmp);
		
		mEditEmp = new JMenuItem("Edit Employee");
		mEditEmp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				editEmpDB=true;
		    	editDB=true;
		    	editSQLiteDB();
		    	}
		    	});
		
		mRecord.add(mEditEmp);
		
		
			mdeleteEmp= new JMenuItem("Delete Current Employee");
			
			mdeleteEmp.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try {
						dao.deleteEmployeeById(Integer.toString(currEmp));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				
				}
			});


			mRecord.add(mdeleteEmp);
			
			mdeleteEmpPic= new JMenuItem("Delete Current Employee Picture");
		
			mdeleteEmpPic.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dao.removePicture(Integer.toString(currEmp));
				
				}
			});


			mRecord.add(mdeleteEmpPic);
		form1.setJMenuBar(menuBar);
		}
		
	/**
	 * 	The Constructor will be used in controller and features the layout of the application.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public MainForm() throws ClassNotFoundException, SQLException, IOException
	{
		super("Employee Record System Assignment");

		getDBEmps();
		getFirstEmp();
		labelInfo();
		textField();
		buttons();		
		comboBoxCreation();
		createPanelWest();
		createPanelCenter();
		createPanelEast();
		editSQLiteDB();

		menuComponents(this);
		
		setLayout(new GridBagLayout());
		GridBagConstraints mainGui =new GridBagConstraints();
		
		mainGui.gridx=0; 
		mainGui.gridy=0;
		mainGui.gridwidth=GridBagConstraints.CENTER; 
		mainGui.gridheight=1; 
		mainGui.weightx=3.0; 
		mainGui.weighty=1.0; 
		
		mainGui.insets= new Insets(0,1,0,0); 
		this.getContentPane().add(sName,mainGui);
		
		mainGui.anchor = GridBagConstraints.WEST;
		mainGui.gridx=0; 
		mainGui.gridy=1;
		mainGui.gridwidth=2; 
		mainGui.gridheight=1; 
		mainGui.weightx=3.0; 
		mainGui.weighty=1.0; 
		mainGui.fill=GridBagConstraints.BOTH; 
		mainGui.insets= new Insets(2,20 ,10,5); 
		this.getContentPane().add(West,mainGui); 
		
		mainGui.anchor = GridBagConstraints.CENTER;
		mainGui.gridx=2; 
		mainGui.gridy=1;
		mainGui.gridwidth=2; 
		mainGui.gridheight=1; 
		mainGui.weightx=3.0; 
		mainGui.weighty=1.0; 
		mainGui.fill=GridBagConstraints.BOTH; 
		mainGui.insets= new Insets(2,2,10,0);
		this.getContentPane().add(Center,mainGui);
		
		mainGui.anchor = GridBagConstraints.EAST;
		mainGui.gridx=4; 
		mainGui.gridy=1;
		mainGui.gridwidth=2; 
		mainGui.gridheight=1; 
		mainGui.weightx=2.0; 
		mainGui.weighty=1.0; 
		mainGui.fill=GridBagConstraints.BOTH; 
		mainGui.insets= new Insets(2,7,10,0); 
		this.getContentPane().add(East,mainGui);
		
		dispDataField(currEmp);		
		
		this.setSize(900,700);		
		 
		  }

	}
	    