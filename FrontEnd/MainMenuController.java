import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

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



  /**
   * Constructs the controller and adds action listeners for all editable components of the view
   * 
 * @param v the GUI of the program
 * @param m the database of the program
 */
public MainMenuController (MainMenuView v)
  {
    theView = v;

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
    		theView.addCourseTableRow(101, newCourseName, "Dr. Dab");
    	}

    	theView.clearNewCourseField();
    
    }catch(Exception e){
      System.out.println("issue with Search Type listener.");
    }
  }
}
class SearchTypeListener implements ActionListener
  {
    @Override
    public void actionPerformed (ActionEvent arg0)
    {
      try
      {
        JComboBox cb = (JComboBox)arg0.getSource();
        String searchType = (String)cb.getSelectedItem();
        switch (searchType){
          case "Client Type": System.out.println("Client Type Selected");
              break;
          case "I.D. Number": System.out.println("I.D. Number Selected");
              break;
          case "Last Name": System.out.println("Last name selected");
              break;
          default: System.out.println("Error selecting search type");
        }
      }catch(Exception e){
        System.out.println("issue with Search Type listener.");
      }
    }
  }

	public class CourseTableListener implements TableModelListener {
	
	    public void tableChanged(TableModelEvent e) {
	        int row = e.getFirstRow();
	        int column = e.getColumn();
	        String courseName = (String)theView.getCourseTableElement(row, 1);
	        
	        if(column == 3) {// active or not active
	        	String activity = (String)theView.getCourseTableElement(row, column);
	        	JOptionPane.showMessageDialog(null,
	        			courseName  + " is now " + activity);
	        }
	        else if(column == 4) {// true = view and set others to false, false = stop viewing
	        	int numRows = 2;
	        	boolean val = (boolean)theView.getCourseTableElement(row, column);
	        	if(val == true) {
		        	for(int i = 0; i < numRows; i++) {
		        		if(i != row) {// set other rows to false
		        			theView.setCourseTableElement(false, i, column);
		        		}
		        	}
	        		
	        		JOptionPane.showMessageDialog(null,
	            		    "Now viewing " + courseName);
	        	}
	        	else {
	        		JOptionPane.showMessageDialog(null,
	            		    "Not viewing " + courseName);
	        	}
	        }
	        else {
	        	System.out.println("sooooo, there shouldnt be a table listener here...");
	        }
	    }
	}
}