package frontEnd;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import sharedData.Assignment;
import sharedData.Course;
import sharedData.Email;
import sharedData.Enrolment;
import sharedData.FileMessage;
import sharedData.User;
import sharedData.Course;

import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Controls the view and model of the program.  Sets action listeners for all buttons on the GUI and 
 * sets a list listener of the JList of clients 
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
public class MainMenuController {

 /**
 * The GUI of the program
 */
private MainMenuView theView;

private SocketCommunicator coms;

private User professor;

private int currentCourseID;


  /**
   * Constructs the controller and adds action listeners for all editable components of the view
   * 
 * @param v the GUI of the program
 * @param m the database of the program
 */
public MainMenuController (MainMenuView v, SocketCommunicator coms, User professor)
  {
	
    theView = v;
    this.coms = coms;
    this.professor = professor;
    
    fillCourseTable();
    
    // add listeners...
    theView.addCourseClearListener( new CourseClearListener());
    theView.addCourseAddListener( new CourseAddListener());
    theView.addClassTableListener(new CourseTableListener());

  }

  // add listener classes
  /**
   * This listener allows the user to choose the criteria for searching for clients
   * 
 * @author Ben Luft and Rob Dunn
 *
 */
//for assignments tab
	class AsgUploadListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent arg0)
		{
			try
			{
				String dueDate;
				int day = Integer.parseInt(theView.getDay());
				int month = Integer.parseInt(theView.getMonth());
				int year = Integer.parseInt(theView.getYear());
				
				if(day > 31 || day < 1 || month > 12 || month < 1
						|| year < 2018 || year > 2023) {
		        	JOptionPane.showMessageDialog(null,
		        		    "Invalid Due Date");
		        	theView.clearDueDateBoxes();
		        	return;
				}
				else {// due date is valid
					dueDate = month + "/" + day + "/" + year;
		        	JOptionPane.showMessageDialog(null,
		        		    "due date = " + dueDate);
		        	theView.clearDueDateBoxes();
		        	File selectedFile = theView.getAsgFile();
		        	
		        	long length = selectedFile.length(); 
		    		byte[] content = new byte[(int) length]; 
		    		// Converting Long to Int 
		    		try {  
		    			FileInputStream fis = new FileInputStream(selectedFile);  
		    			BufferedInputStream bos = new BufferedInputStream(fis);  
		    			bos.read(content, 0, (int)length); 
	    			} catch (FileNotFoundException e) {
	    				e.printStackTrace(); 
	    			} catch(IOException e){  
	    				e.printStackTrace(); 
	    			}
		        	
					FileMessage fileMessage = new FileMessage(false, 0, content);
					
					Assignment assign = new Assignment(false, -1, currentCourseID, selectedFile.getName(), 
							false, dueDate);
					
					coms.write(assign);
					coms.write(fileMessage);
				}

			
			}catch(NumberFormatException nf) {
	        	JOptionPane.showMessageDialog(null,
	        		    "Number format issue");
	        	theView.clearDueDateBoxes();
	        	return;
			}catch(Exception e){
			  System.out.println("issue with Search Type listener.");
			  return;
			}
			
			addNewestAssignment();
		}
	}
	
    class AsgTableListener implements TableModelListener {
    	
	    public void tableChanged(TableModelEvent e) {
	        int row = e.getFirstRow();
	        int column = e.getColumn();
	        String asgName = (String)theView.getAsgTableEl(row, 0);
	        String dueDate = (String)theView.getAsgTableEl(row, 1);
	        
	        if(column == 2) {// active or not active
	        	String activity = (String)theView.getAsgTableEl(row, column);
	        	JOptionPane.showMessageDialog(null,
	        			asgName  + " is now " + activity);
	        	if(activity.equals("Active"))
	        	{
	        		coms.write(new Assignment(false,0,currentCourseID,asgName,true,dueDate));
	        	}
	        	else
	        	{
	        		coms.write(new Assignment(false,0,currentCourseID,asgName,false,dueDate));
	        	}
	        }else if(column == 3) { // viewing column
	        	theView.changeAsgGrade(73);
	        	
	        }else if (column == 4) { // grades column
	        	
	        }
	    }
	}

class CourseClearListener implements ActionListener
{
  @Override
  public void actionPerformed (ActionEvent arg0)
  {
    try
    {
    	theView.clearNewCourseField();
    
    }catch(Exception e){
      System.out.println("issue with Search Type listener.");
    }
  }
}
class CourseAddListener implements ActionListener
{
  @Override
  public void actionPerformed (ActionEvent arg0)
  {
    try
    {
    	String newCourseName = theView.getNewCourseName();
    	if(newCourseName.equals("")) {
        	JOptionPane.showMessageDialog(null,
        		    "Blank names are invalid");
    	}
    	else {// add course number and prof name here
    		coms.write(new Course(false, 0, professor.getID(), newCourseName, false));
    		
    		addNewestCourse();
    	}

    	theView.clearNewCourseField();
    
    }
    catch(Exception e)
    {
      System.out.println("issue with Search Type listener.");
    }
  }
}

	public class CourseTableListener implements TableModelListener {
	
	    public void tableChanged(TableModelEvent e) {
	        int row = e.getFirstRow();
	        int column = e.getColumn();
	        String courseName = (String)theView.getCourseTableElement(row, 1);
	        int courseID = Integer.parseInt((String)theView.getCourseTableElement(row, 0));
	        
	        if(column == 3) {// active or not active
	        	String activity = (String)theView.getCourseTableElement(row, column);
//	        	JOptionPane.showMessageDialog(null,
//	        			courseName  + " is now " + activity);
	        	if(activity.equals("Active"))
	        	{
	        		coms.write(new Course(false,courseID,professor.getID(),courseName,true));
	        	}
	        	else
	        	{
	        		coms.write(new Course(false,courseID,professor.getID(),courseName,false));
	        	}
	        	
	        	
	        }
	        else if(column == 4) {// true = view and set others to false, false = stop viewing
	        	int numRows = theView.getCourseTableNumRows();
	        	String val = (String)theView.getCourseTableElement(row, column);
	        	if(val.equals("True")) {
		        	for(int i = 0; i < numRows; i++) {
		        		if(i != row) {// set other rows to false
		        			theView.setCourseTableElement("False", i, column);
		        		}
		        	}
	        		
//	        		JOptionPane.showMessageDialog(null,
//	            		    "Now viewing " + courseName);
	        		
	        		currentCourseID = courseID;
	        		
	        		theView.clearStudentsTable();
	        		
	        		fillStudentTable(Integer.parseInt((String)theView.getCourseTableElement(row, 0)));
	        		
	        		fillAssignTable();
	        		
	        	    theView.addSearchSudentListener(new StudentSearchListener());
	        	    theView.addClearSearchSudentListener(new StudentClearListener());
	        	    theView.addStudentTableListener(new StudentTableListener());
	        	    theView.addUploadListener(new AsgUploadListener());
	        	    theView.addAssignmentsTableListener(new AsgTableListener());
	        		theView.addSendEmailListener(new SendEmailListener());
	        		theView.addClearEmailListener(new ClearEmailListener());
	        	}
	        }
	        else {
	        	System.out.println("sooooo, there shouldnt be a table listener here...");
	        }
	    }
	}
	
	private void fillCourseTable()
	{
		Vector<Course> coursesInDB = getAllCourses();
		
		for(int i = 0; i < coursesInDB.size(); i++)
		{
			Course currentCourse = coursesInDB.get(i);
			theView.addCourseTableRow(currentCourse.getID(), currentCourse.getName(), "Dr. "+ professor.getLastname(),
					currentCourse.isActive());
		}
	}
	
	private void fillAssignTable()
	{
		Vector<Assignment> assignmentsInDB = getAllAssignments();
		
		for(int i = 0; i < assignmentsInDB.size(); i++)
		{
			Assignment currentAssignment = assignmentsInDB.get(i);
			theView.addAsgTableRow(currentAssignment.getTitle(), currentAssignment.getDueDate(),
					currentAssignment.isActive());
		}
	}
	
	private void fillStudentTable(int courseID)
	{
		Vector<User> studentInDB = getAllStudents();
		Vector<Enrolment> enrolledInCourse = getEnrolledStudents();
		
		User currentStudent;
		Enrolment currentEnrolment;
		
		boolean isEnrolled;
		
		for(int i = 0; i < studentInDB.size(); i++)
		{
			currentStudent = studentInDB.get(i);
			
			theView.addStudentTableRow(currentStudent.getFirstname(), currentStudent.getLastname(), 
					currentStudent.getID());
			
			for(int j = 0; j < enrolledInCourse.size(); j++)
			{
				currentEnrolment = enrolledInCourse.get(j);
				if(currentEnrolment.getStudentID() == currentStudent.getID())
				{
					theView.setStudentTableElement("Enrolled", i, 3);
				    
					break;
				}
			}
		}
	}
	
	
	private void addNewestCourse()
	{
		Vector<Course> coursesInDB = getAllCourses();
		
		Course newCourse = null;
		
		newCourse = coursesInDB.get(coursesInDB.size()-1);
		theView.addCourseTableRow(newCourse.getID(), newCourse.getName(), "Dr. " +professor.getLastname(),
				newCourse.isActive());
	}
	
	private void addNewestAssignment()
	{
		Vector<Assignment> assignmentsInDB = getAllAssignments();
		
		Assignment newAssignment = null;
		
		newAssignment = assignmentsInDB.get(assignmentsInDB.size()-1);

		theView.addAsgTableRow(newAssignment.getTitle(), newAssignment.getDueDate(),
				newAssignment.isActive());
	}
	
	
	private Vector<Course> getAllCourses()
	{
		coms.write(new Course(true, 0, professor.getID(), null, false));
		return (Vector<Course>)coms.read();
	}
	
	private Vector<Assignment> getAllAssignments()
	{
		coms.write(new Assignment(true, 0,currentCourseID, null, false, null));
		return (Vector<Assignment>)coms.read();
	}
	
	private Vector<Enrolment> getEnrolledStudents()
	{
		coms.write(new Enrolment(true,0, 0, currentCourseID));
		return (Vector<Enrolment>)coms.read();
	}

	private Vector<User> getAllStudents()
	{
		coms.write(new User(true,0,null,null,null,null,"S"));
		return (Vector<User>)coms.read();
	}
	

	    //for students tab
 	class StudentClearListener implements ActionListener
	{
	  @Override
	  public void actionPerformed (ActionEvent arg0)
	  {
	    try
	    {
	    	theView.clearStudentSearchBox();
	    	
	    	theView.clearStudentsTable();
	    	
	    	fillStudentTable(currentCourseID);
	    
	    }catch(Exception e){
	      System.out.println("issue with clear search box listener.");
	    }
	  }
	}
    class StudentSearchListener implements ActionListener
	{
	  @Override
	  public void actionPerformed (ActionEvent arg0)
	  {
	    try
	    {
	    	String searchType = theView.getStudentSearchType();
	    	String searchKey = theView.getStudentSearchBox();
	        switch (searchType){
	          case "Last Name": 
	        	  searchStudentsLastName(searchKey.toLowerCase());
	              break;
	          case "I.D. Number": 
	        	  searchStudentsID(searchKey);
	              break;
	          default: System.out.println("Error selecting search type");
	        }
	    	
	    }catch(Exception e){
	      System.out.println("issue with Search Type listener.");
	      e.printStackTrace();
	    }
	  }
	}
    private void searchStudentsLastName(String key) {
    	String lastFromTable;
    	for(int i = theView.getStudentTableNumRows() - 1; i >= 0; i--) {
    		lastFromTable = (String)theView.getStudentTableElement(i, 1);
    		lastFromTable = lastFromTable.toLowerCase();
    		if(!(lastFromTable.equals(key))){
    			theView.removeStudentTableRow(i);
    		}
    	}
    }
    private void searchStudentsID(String key) {
    	String idFromTable;
    	for(int i = 0; i < theView.getStudentTableNumRows(); i++) {
    		idFromTable = (String)theView.getStudentTableElement(i, 2);
    		if(!idFromTable.equals(key)){
    			theView.removeStudentTableRow(i);
    		}
    	}
    }

    class StudentTableListener implements TableModelListener {
    	
	    public void tableChanged(TableModelEvent e) {
	        int row = e.getFirstRow();
	        int column = e.getColumn();
	        if(row >= 0)
	        {
		        String firstName = (String)theView.getStudentTableElement(row, 0);
		        String lastName = (String)theView.getStudentTableElement(row, 1);
		        int studentID = Integer.parseInt((String)theView.getStudentTableElement(row, 2));
		        
		        if(column == 3) {// enrolled or not enrolled
		        	String enr = (String)theView.getStudentTableElement(row, column);
		        	JOptionPane.showMessageDialog(null,
		        			firstName + " " + lastName  + " is now " + enr);
		        	if(enr.equals("Enrolled"))
		        	{
		        		if(!doesEnrolledExist(studentID))
		        		{
		        			System.out.println("Controller sending add enrollment");
		        			coms.write(new Enrolment(false,0,studentID,currentCourseID));
		        		}
		        	}
		        	else
		        	{
		        		if(doesEnrolledExist(studentID))
		        		{
		        			System.out.println("Controller sending delete enrollment");
		        			coms.write(new Enrolment(false,-1,studentID,currentCourseID));
		        		}
		        	}
		        }
	        }
	    }
	}
    
    private boolean doesEnrolledExist(int studentID)
    {
    	Vector<Enrolment> enrolled = getEnrolledStudents();
    	
    	for(int i = 0; i < enrolled.size(); i++)
    	{
    		if(enrolled.get(i).getStudentID() == studentID)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    
    // email controller
    
    class SendEmailListener implements ActionListener
 	{
 	  @Override
 	  public void actionPerformed (ActionEvent arg0)
 	  {
 	    try
 	    {
 	    	String emailBody = theView.getEmailBody();
 	    	String emailSubject = theView.getEmailSubject();
 	    	JOptionPane.showMessageDialog(null,
        		    "Subject = " + emailSubject);
        	theView.clearDueDateBoxes();
        	JOptionPane.showMessageDialog(null,
        		    "Email body = " + emailBody);
        	theView.clearDueDateBoxes();
        	
        	coms.write(new Email(0,false,currentCourseID,emailBody,emailSubject));
 	    	
 	    }catch(Exception e){
 	      System.out.println("issue with Search Type listener.");
 	      e.printStackTrace();
 	    }
 	  }
 	}
    
    class ClearEmailListener implements ActionListener
 	{
 	  @Override
 	  public void actionPerformed (ActionEvent arg0)
 	  {
 	    try
 	    {
 	    	theView.clearEmail();
 	    	
 	    }catch(Exception e){
 	      System.out.println("issue with Search Type listener.");
 	      e.printStackTrace();
 	    }
 	  }
 	}


}