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
import sharedData.Submission;
import sharedData.User;
import sharedData.Course;

import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public class MainMenuController implements ClientFilePath
{

 /**
 * The GUI of the program
 */
/**
 * 
 */
private MainMenuView theView;

/**
 * 
 */
private SocketCommunicator coms;

/**
 * 
 */
private User currentUser;

/**
 * 
 */
private int currentCourseID;


  /**
   * Constructs the controller and adds action listeners for all editable components of the view
   * 
 * @param v the GUI of the program
 * @param m the database of the program
 * @param User is all of the data for the current user of the program
 */

public MainMenuController (MainMenuView v, SocketCommunicator coms, User currentUser) 
  {
	
    theView = v;
    this.coms = coms;
    this.currentUser = currentUser;
    
	fillCourseTable();
    
    // add listeners...
	if(currentUser.getType().equals("P")) {
	    theView.addCourseClearListener( new CourseClearListener());
	    theView.addCourseAddListener( new CourseAddListener());
	    theView.addSearchSudentListener(new StudentSearchListener());
	    theView.addClearSearchSudentListener(new StudentClearListener());
	    theView.addStudentTableListener(new StudentTableListener());
	    theView.addUploadAsgListener(new AsgUploadListener());
	    theView.addSubmissionDownloadListener(new DownloadSubmittedAssignmentListener());
	    theView.addSubmissionsTableListener(new SubmissionTableListener());
	}
	else
	{
		theView.addDownloadAsgListener(new AsgDownloadListener());
		theView.addSubmitAsgListener(new AsgSubmissionListener());
	}
    theView.addClassTableListener(new CourseTableListener());
    theView.addAssignmentsTableListener(new AsgTableListener());
	theView.addSendEmailListener(new SendEmailListener());
	theView.addClearEmailListener(new ClearEmailListener());

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
					
					System.out.println("Before assign send");
					coms.write(assign);
					System.out.println("Before file send");
					coms.write(fileMessage);
					System.out.println("After file send");
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
	
	/**
	 * Listens for push of the submission buttons in the student GUI.
	 * Allows the student to select a file which is submitted to the assignment
	 * 
	 * @author Ben Luft and Rob Dunn
	 *
	 */
	class AsgSubmissionListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent arg0)
		{
	        	
				int assignID = findAsgID();
				
				if(assignID == 0)
				{
					return;
				}
			
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
				
				Submission submission = new Submission(false, -1, assignID, currentUser.getID(), selectedFile.getName(), 0, null);
				
				System.out.println("Writing submission");
				coms.write(submission);
				System.out.println("Writing file");
				coms.write(fileMessage);
				System.out.println("Writing submission and file");
				
				
		}
	}
	
	/**
	 * Listens for the push of the download button by a student
	 * This will get the assignment for the selected assignment
	 * 
	 * @author Ben Luft and Rob Dunn
	 *
	 */
	class AsgDownloadListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent arg0)
		{
			String assignName = findAssignName();
			
			if(assignName == null)
			{
				return;
			}
			
			Assignment requestAssign = new Assignment(true, -1, 0, 
					assignName, true, null);
			
			coms.write(requestAssign);
			
			FileMessage filemessage = ((Vector<FileMessage>) coms.read()).get(0);
			
			File newFile = new File(assignmentPath + assignName);
			
			try{
				if(!newFile.exists())
				{
					newFile.createNewFile();
				}
				
				FileOutputStream writer = new FileOutputStream(newFile);
				BufferedOutputStream bos = new BufferedOutputStream(writer);
				
				bos.write(filemessage.getFileData());
				bos.close();
			} 
			catch(IOException e){
				e.printStackTrace();
			}
			
		}
	}
	
    /**
     * This listener listens for changes in the assignment table, and changes
     * things such as Assignment activity and which assignment is selected
     * 
     * @author Ben Luft and Rob Dunn
     *
     */
    class AsgTableListener implements TableModelListener {
    	
	    public void tableChanged(TableModelEvent e) {
	        int size = theView.getAsgTableNumRows();
	    	int row = e.getFirstRow();
	        int column = e.getColumn();
	        
	        if(size > 0)
	        {
	        	System.out.println("TABLE LISTENER ACTIVATED!!!!");
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
		        }
		        else if(column == 3) { // viewing column
		        	int numRows = theView.getAsgTableNumRows();
		        	String val = (String)theView.getAsgTableEl(row, column);
		        	if(!val.equals("False")) {
			        	for(int i = 0; i < numRows; i++) {
			        		if(i != row && theView.getAsgTableEl(i, column) == "True") {// set other rows to false
			        			theView.setAsgTableElement("False", i, column);
			        			//return;
			        		}
			        	}
		        		
			        	System.out.println("Finished removing falses");
			        	
		        		if(currentUser.getType().equals("P"))
		        		{
		        			theView.clearSubmissionsTable();
		        			fillSubmissionsTable(asgName);
		        		}
		        		
		        		
		        	}
		        }
		        else if (column == 4) 
		        { // grades column
		        	
		        }	
	        }
	    }
	}

/**
 * Listens for the clear button on the assignments page of the Prof GUI.
 * Clears the due date
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
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
/**
 * Listens for the add course button of the professor GUI.  Adds 
 * a new assignment according to the file that the Professor selects
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
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
    		coms.write(new Course(false, 0, currentUser.getID(), newCourseName, false));
    		
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

	/**
	 * Listens for any changes in the course table, such as selecting a course or
	 * changing the activity of a course
	 * 
	 * @author Ben Luft and Rob Dunn
	 *
	 */
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
	        		coms.write(new Course(false,courseID,currentUser.getID(),courseName,true));
	        	}
	        	else
	        	{
	        		coms.write(new Course(false,courseID,currentUser.getID(),courseName,false));
	        	}
	        	
	        	
	        }
	        else if(column == 4) {// true = view and set others to false, false = stop viewing
	        	int numRows = theView.getCourseTableNumRows();
	        	String val = (String)theView.getCourseTableElement(row, column);
	        	if(val.equals("True")) {
		        	for(int i = 0; i < numRows; i++) {
		        		if(i != row && theView.getCourseTableElement(i, column) == "True") {// set other rows to false
		        			theView.setCourseTableElement("False", i, column);
		        			//return;
		        		}
		        	}
	        		
//	        		JOptionPane.showMessageDialog(null,
//	            		    "Now viewing " + courseName);
	        		
	        		currentCourseID = courseID;
	        		
	        		if(currentUser.getType().equals("P"))
	        		{
	        			theView.clearStudentsTable();
	        			fillStudentTable(Integer.parseInt((String)theView.getCourseTableElement(row, 0)));
	        		}
	        		
	        		theView.clearAssignmentsTable();
	        		
	        		fillAssignTable();
	   
	        	}
	        }
	        else {
	        	System.out.println("sooooo, there shouldnt be a table listener here...");
	        }
	    }
	}
	
	/**
	 * Fills the course table with all courses that a user can see
	 */
	private void fillCourseTable()
	{
		
		Vector<Course> courses = getAllCourses();
		
		String profName;
		
		for(int i = 0; i < courses.size(); i++)
		{
			Course currentCourse = courses.get(i);
			
			if(currentUser.getType() == "P")
			{
				profName = "Dr. " + currentUser.getLastname();
			}
			else
			{
				profName = "";
			}
			if(currentUser.getType().equals("S") && !currentCourse.isActive())
			{
				System.out.println("NOT ACTIVE");
			}
			else
			{
				theView.addCourseTableRow(currentCourse.getID(), currentCourse.getName(), profName,
						currentCourse.isActive());
			}
		}
	}
	
	/**
	 * Fills the submission table with all submissions for an assignment
	 * 
	 * @param assignName is the name of the assignment for which the submissions
	 * should be viewed
	 */
	private void fillSubmissionsTable(String assignName)
	{
		int assignID = findAsgID();
		
		System.out.println("AssignID is " + assignID);
		
		if(assignID == 0)
		{
			return;
		}
		
		Vector<Submission> submissions = getAllSubmissions(assignID);
		
		for(int i = 0; i < submissions.size(); i++)
		{
			Submission submission = submissions.get(i);
			System.out.println("Submission for student " + submission.getStudentID());
		
			
			theView.addSubmissionsTableRow(submission.getTitle(), submission.getStudentID(), "", submission.getGrade());
		}
		
	}
	
	/**
	 * Finds the name of the currently selected assignment
	 * 
	 * @return name of the assignment
	 */
	private String findAssignName()
	{
		int row = 0;
		String assignView;
		
		try
		{
			while(true)
			{
				assignView = (String)theView.getAsgTableEl(row, 3);
				if(assignView == "True")
				{
					return (String)theView.getAsgTableEl(row, 0);
				}
				row++;
				
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(theView, "Please select an assignment to view");
			return null;
		}
	}
	
	/**
	 * Fills the assignment table with all the assignments of the course selected
	 */
	private void fillAssignTable()
	{
		Vector<Assignment> assignmentsInDB = getAllAssignments();
		
		for(int i = 0; i < assignmentsInDB.size(); i++)
		{
		
			Assignment currentAssignment = assignmentsInDB.get(i);
			
			if(currentUser.getType().equals("P") || currentAssignment.isActive())
			{
				theView.addAsgTableRow(currentAssignment.getTitle(), currentAssignment.getDueDate(),
						currentAssignment.isActive());
			}
			
			int maxGrade = 0;
			
			if(currentUser.getType().equals("S") && currentAssignment.isActive())
			{
				System.out.println("Trying to get grade");
				Vector<Submission> submissionsForGrades = getAllSubmissions(assignmentsInDB.get(i).getID());
				for(int j = 0; j < submissionsForGrades.size(); j++)
				{
					System.out.println("Looping for grade");
					if(submissionsForGrades.get(j).getStudentID() == currentUser.getID())
					{
						if(submissionsForGrades.get(j).getGrade() > maxGrade)
						{
							maxGrade = submissionsForGrades.get(j).getGrade();
						}
					}
				}
				
				System.out.println("Grade is put in view");
				theView.setAsgTableElement((Object)maxGrade, i, 4);
			}
		}
	}
	
	/**
	 * Fills the student table with all the courses of the course ID
	 * 
	 * @param courseID the ID for the course 
	 */
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
	
	/**
	 * Find the ID of the currently selected assign
	 * 
	 * @return the assign ID
	 */
	private int findAsgID()
	{
		Vector<Assignment> assignments = getAllAssignments();
    	
    	String assignName = findAssignName();
    	
    	if(assignName == null)
    	{
    		return 0;
    	}
    	
    	
    	
    	for(int i = 0; i < assignments.size(); i++)
    	{
    		if(assignments.get(i).getTitle().equals(assignName))
    		{
    			return assignments.get(i).getID();
    		}
    	}
    	return 0;
	}
	
	
	/**
	 * Inserts newest course into the table
	 */
	private void addNewestCourse()
	{
		Vector<Course> coursesInDB = getAllCourses();
		
		Course newCourse = null;
		
		newCourse = coursesInDB.get(coursesInDB.size()-1);
		theView.addCourseTableRow(newCourse.getID(), newCourse.getName(), "Dr. " +currentUser.getLastname(),
				newCourse.isActive());
	}
	
	/**
	 * Insert newest assignment into the table
	 */
	private void addNewestAssignment()
	{
		Vector<Assignment> assignmentsInDB = getAllAssignments();
		
		Assignment newAssignment = null;
		
		newAssignment = assignmentsInDB.get(assignmentsInDB.size()-1);

		theView.addAsgTableRow(newAssignment.getTitle(), newAssignment.getDueDate(),
				newAssignment.isActive());
	}
	
	
	/**
	 * Gets all of the courses for the user
	 * 
	 * @return a Vector of the courses
	 */
	private Vector<Course> getAllCourses()
	{	
		if(currentUser.getType().equals("P"))
		{
			coms.write(new Course(true, 0, currentUser.getID(), null, false));
		}
		else
		{
			System.out.println("The controller user ID is " + currentUser.getID());
			coms.write(new Enrolment(true, 0, currentUser.getID(), 0));
		}
		return (Vector<Course>)coms.read();
	}
	
	/**
	 * Gets all of the assignments for the course
	 * 
	 * @return a Vector of the assignments
	 */
	private Vector<Assignment> getAllAssignments()
	{
		coms.write(new Assignment(true, 0,currentCourseID, null, false, null));
		return (Vector<Assignment>)coms.read();
	}
	
	/**
	 * Gets all of the enrolled students for the course
	 * 
	 * @return a Vector of the enrolled students
	 */
	private Vector<Enrolment> getEnrolledStudents()
	{
		coms.write(new Enrolment(true,0, 0, currentCourseID));
		return (Vector<Enrolment>)coms.read();
	}

	/**
	 * Gets all of the students for the course
	 * 
	 * @return a Vector of the students
	 */
	private Vector<User> getAllStudents()
	{
		coms.write(new User(true,0,null,null,null,null,"S"));
		return (Vector<User>)coms.read();
	}
	
	/**
	 * Gets all of the submissions for the assignment
	 * 
	 * @return a Vector of the submissions
	 */
	private Vector<Submission> getAllSubmissions(int assignID)
	{
		System.out.println("Getting submissions");
		coms.write(new Submission(true,0,assignID,0,null,0,null));
		return (Vector<Submission>)coms.read();
	}
	

	    //for students tab
 	/**
 	 * Clears the student search bar
 	 * 
 	 * @author Ben Luft and Rob Dunn
 	 *
 	 */
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
    /**
     * Searches for a student in the list of students
     * 
     * @author Ben Luft and Rob Dunn
     *
     */
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
    /**
     * Searches for student Last name
     * 
     * @param key name to search for
     */
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
    /**
     * Search for student by ID
     * 
     * @param key the Id to search for 
     */
    private void searchStudentsID(String key) {
    	String idFromTable;
    	for(int i = 0; i < theView.getStudentTableNumRows(); i++) {
    		idFromTable = (String)theView.getStudentTableElement(i, 2);
    		if(!idFromTable.equals(key)){
    			theView.removeStudentTableRow(i);
    		}
    	}
    }

    /**
     * Listens to the student table for enrolling students
     * 
     * @author Ben Luft and Rob Dunn
     *
     */
    class StudentTableListener implements TableModelListener {
    	
	    public void tableChanged(TableModelEvent e) {
	        int size = theView.getStudentTableNumRows();
	    	int row = e.getFirstRow();
	        int column = e.getColumn();
	        if(size > 0)
	        {
		        String firstName = (String)theView.getStudentTableElement(row, 0);
		        String lastName = (String)theView.getStudentTableElement(row, 1);
		        int studentID = Integer.parseInt((String)theView.getStudentTableElement(row, 2));
		        
		        if(column == 3) {// enrolled or not enrolled
		        	String enr = (String)theView.getStudentTableElement(row, column);
//		        	JOptionPane.showMessageDialog(null,
//		        			firstName + " " + lastName  + " is now " + enr);
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
    
    /**
     * Checks if the student is enrolled
     * 
     * @param studentID ID of the student to check
     * @return true if the student is enrolled
     */
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
    
    /**
     * Listens to the send email button.  If it is clicked send the
     * email according to whether the user is a student or prof
     * 
     * @author Ben Luft adn Rob Dunn
     *
     */
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
        	JOptionPane.showMessageDialog(null,
        		    "Email body = " + emailBody);
        	
        	coms.write(new Email(0,false,currentCourseID,emailBody,emailSubject));
 	    	
 	    }catch(Exception e){
 	      System.out.println("issue with Search Type listener.");
 	      e.printStackTrace();
 	    }
 	  }
 	}
    
    /**
     * Clears the email body and title when button pushed
     * 
     * @author Ben Luft and Rob Dunn
     *
     */
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

    
    // submissions

	/**
	 * Listens to the submission table to allow the prof to grade and
	 * select a submission
	 * 
	 * @author Ben Luft and Rob Dunn
	 *
	 */
	public class SubmissionTableListener implements TableModelListener {
	
	    public void tableChanged(TableModelEvent e) {
	        int row = e.getFirstRow();
	        int column = e.getColumn();
	        int size = theView.getSubmissionTableNumRows();
	        
	        if(size > 0)
	        {
		        String asgName = (String)theView.getSubmissionsTableEl(row, 0);
		        int studentID = Integer.parseInt((String)theView.getSubmissionsTableEl(row, 1));
		        String studentLastName = (String)theView.getSubmissionsTableEl(row, 2);
	
		        if(column == 3) {// grade changed
		        	int newGrade = Integer.parseInt((String)theView.getSubmissionsTableEl(row, column));
		        	JOptionPane.showMessageDialog(null,
		        			studentLastName + "'s grade on "
		        			+ asgName + " is: \n" + newGrade);
		        	System.out.println("Title is " + (String)theView.getSubmissionsTableEl(row, 0));
		        	coms.write(new Submission(false, 0, 0, 0, 
		        			(String)theView.getSubmissionsTableEl(row, 0), newGrade, null));
		        	
		        }
		        else if(column == 4) {// true == asg selected
		        	int numRows = theView.getSubmissionTableNumRows();
		        	String val = (String)theView.getSubmissionsTableEl(row, column);
		        	if(val.equals("True")) {
			        	for(int i = 0; i < numRows; i++) {
			        		if(i != row && theView.getSubmissionsTableEl(i, column) == "True") {// set other rows to false
			        			theView.setSubmissionsTableEl("False", i, column);
			        			//return;
			        		}
			        	}
		        		
		   
		        	}
		        }
		        else {
		        	System.out.println("sooooo, there shouldnt be a table listener here...");
		        }
	        }
	    }
	}
	
    /**
     * Button that allows the prof to view a submission
     * 
     * @author Ben Luft and Rob Dunn
     *
     */
    class DownloadSubmittedAssignmentListener implements ActionListener
 	{
 	  @Override
 	  public void actionPerformed (ActionEvent arg0)
 	  {
 		String asgToDownload = "";
 		String selected = "";
    	int numRows;
    	boolean found = false;
 	    try
 	    {
 	    	numRows = theView.getSubmissionTableNumRows();
 	    	for(int i = 0; i < numRows; i++) {
 	    		selected = (String)theView.getSubmissionsTableEl(i, 4);
 	    		if(selected.equals("True")) {
 	    			found = true;
 	    			asgToDownload = (String)theView.getSubmissionsTableEl(i, 0);
 	    			JOptionPane.showMessageDialog(null,
 	        			"You clicked the download submission button. \n"
 	        			+ "Downloading: " + asgToDownload +"\n");
 	    			
 	    			String assignName = findAssignName();
 	    			int assignID = findAsgID();
 	   			
 	   			if(assignName == null || assignID == 0)
 	   			{
 	   				return;
 	   			}
 	   			
 	   			Submission requestSub = new Submission(true, -1, assignID, 0, null, 0, null);
 	   			
 	   			coms.write(requestSub);
 	   			
 	   			FileMessage filemessage = ((Vector<FileMessage>) coms.read()).get(0);
 	   			
 	   			File newFile = new File(submissionPath + asgToDownload);
 	   			
 	   			try{
 	   				if(!newFile.exists())
 	   				{
 	   					newFile.createNewFile();
 	   				}
 	   				
 	   				FileOutputStream writer = new FileOutputStream(newFile);
 	   				BufferedOutputStream bos = new BufferedOutputStream(writer);
 	   				
 	   				bos.write(filemessage.getFileData());
 	   				bos.close();
 	   			} 
 	   			catch(IOException e){
 	   				e.printStackTrace();
 	   			}
 	    		}
 	    	}
 	    	if(!found) {
 	    		JOptionPane.showMessageDialog(null, 
 	    				"You must select a submission first");
 	    	}

 	    	
 	    }catch(Exception e){
 	      System.out.println("issue with Search Type listener.");
 	      e.printStackTrace();
 	    }
 	  }
 	}
    
}