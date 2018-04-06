package frontEnd;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import sharedData.Course;
import sharedData.Enrolment;
import sharedData.User;
import sharedData.Course;

import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	        	JOptionPane.showMessageDialog(null,
	        			courseName  + " is now " + activity);
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
	        	boolean val = (boolean)theView.getCourseTableElement(row, column);
	        	if(val == true) {
		        	for(int i = 0; i < numRows; i++) {
		        		if(i != row) {// set other rows to false
		        			theView.setCourseTableElement(false, i, column);
		        		}
		        	}
	        		
	        		JOptionPane.showMessageDialog(null,
	            		    "Now viewing " + courseName);
	        		
	        		currentCourseID = courseID;
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
			theView.addCourseTableRow(currentCourse.getID(), currentCourse.getName(), "Dr. "+ professor.getLastname());
		}
	}
	
	private void addNewestCourse()
	{
		Vector<Course> coursesInDB = getAllCourses();
		
		Course newCourse = coursesInDB.get(coursesInDB.size()-1);
		theView.addCourseTableRow(newCourse.getID(), newCourse.getName(), "Dr. " +professor.getLastname());
	}
	
	private Vector<Course> getAllCourses()
	{
		coms.write(new Course(true, 0, professor.getID(), null, false));
		return (Vector<Course>)coms.read();
	}
	
	private Vector<Enrolment> getEnrolledStudents()
	{
		coms.write(new Enrolment(true,0, 0, currentCourseID));
		return (Vector<Enrolment>)coms.read();
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
	        			coms.write(new Enrolment(false,0,studentID,currentCourseID));
	        		}
	        	}
	        	else
	        	{
	        		if(doesEnrolledExist(studentID))
	        		{
	        			coms.write(new Enrolment(false,-1,studentID,currentCourseID));
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
    

}