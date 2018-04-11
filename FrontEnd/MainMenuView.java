package frontEnd;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.NumberFormatter;

import sharedData.User;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;


public class MainMenuView extends JFrame {

	/**
	 * The entire panel used for the gui
	 */
	private JPanel contentPane = new JPanel();;
	/**
	 * The layout of the main panel
	 */
	GroupLayout gl_contentPane = new GroupLayout(contentPane);
	/**
	 * the students panel
	 */
	private JPanel jpnStudents;
	/**
	 * the submissions panel
	 */
	private JPanel jpnSubmissions;
	/**
	 * the assignments panel
	 */
	private JPanel jpnAsg;
	/**
	 * the tabbedPanel to which all other panels will be applied
	 */
	private JTabbedPane tabbedPane;
	/**
	 * the email panel
	 */
	private JPanel jpnEmail;
	
	/**
	 * the information about the current user
	 */
	private User currentUser;
	
	//courses panel
	/**
	 * the courses panel
	 */
	private JPanel jpnCourses;
	/**
	 * the courses table
	 */
	private JTable coursesTable;
	/**
	 * label for the courses panel
	 */
	private JLabel lblCourses;
	//courses but only for prof
	/**
	 * label for 
	 */
	private JLabel lblNewCourse;
	/**
	 * 
	 */
	private JTextField textCourseName;
	/**
	 * 
	 */
	private JLabel lblCourseName;
	/**
	 * 
	 */
	private JButton btnAddCourse;
	/**
	 * 
	 */
	private JButton btnCourseClear;
	/**
	 * 
	 */
	private JScrollPane coursePane;
	//student
	/**
	 * 
	 */
	private JTable studentTable;
	/**
	 * 
	 */
	private JTextField searchStudenttextField;
	/**
	 * 
	 */
	private JLabel lblStudents;
	/**
	 * 
	 */
	private JLabel lblSearchStudents;
	/**
	 * 
	 */
	private JLabel lblSearchStudentUsing;
	/**
	 * 
	 */
	private JComboBox searchStudentComboBox;
	/**
	 * 
	 */
	private JButton btnSearchStudent;
	/**
	 * 
	 */
	private JButton btnStudentClear;
	//assignments
	/**
	 * 
	 */
	private JLabel lblAssignnents;
	/**
	 * 
	 */
	private JTable asgTable;
	/**
	 * 
	 */
	private JButton btnUploadAssignment;
	/**
	 * 
	 */
	private JLabel lblAddAssignment;
	/**
	 * 
	 */
	private JLabel lblDueDate;
	/**
	 * 
	 */
	private JTextField monthTxt;
	/**
	 * 
	 */
	private JTextField dayTxt;
	/**
	 * 
	 */
	private JTextField yearTxt;
	/**
	 * 
	 */
	private JButton btnClearEmail;
	/**
	 * 
	 */
	private JButton btnSendEmail;
	/**
	 * 
	 */
	private JTextField txtSubject;
	/**
	 * 
	 */
	private JTextArea txtEmailBody;
	/**
	 * 
	 */
	private JTable tblSubmissions;
	/**
	 * 
	 */
	private JButton btnAsgDownload;
	/**
	 * 
	 */
	private JButton btnAsgSubmit;
	/**
	 * 
	 */
	private JButton btnSubmissionDownload;

	/**
	 * Create the frame.
	 */
	/**
	 * @param currentUser
	 */
	public MainMenuView(User currentUser) {
		this.currentUser = currentUser;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 761);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		createMenuTabs();
		setVisible(true);
	}
	/**
	 * 
	 */
	private void createMenuTabs() {
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 815, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 710, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		createCoursesPan();
		createAssignmentsPan();
		createEmailPan();
		
		//if prof only:
		if(currentUser.getType().equals("P")) {
			setTitle("Proffesor Main Menu");
			createProfCoursesPan();
			createProfAsgTable();
			createStudentsPan();
			createSubmissionsPan();		
			createProfAsgPan();
			
		}
		else {// if student only:
			setTitle("Student Main Menu");
			createStudentAsgTable();
			System.out.println("User is a student");
			createStudentCoursesPan();
			createStudentAsgPan();
		}
		

	}
	
	//courses
	/**
	 * 
	 */
	private void createCoursesPan() {
		jpnCourses = new JPanel();
		tabbedPane.addTab("Courses", null, jpnCourses, null);
		jpnCourses.setLayout(null);

		//TODO: call from prof view constructor in super style

		
		lblCourses = new JLabel("Courses");
		lblCourses.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourses.setFont(new Font("Tahoma", Font.PLAIN, 39));
		lblCourses.setBounds(10, 35, 790, 59);
		jpnCourses.add(lblCourses);
		
	}
	/**
	 * 
	 */
	public void clearCoursesTable() {
		DefaultTableModel model = (DefaultTableModel) coursesTable.getModel();
		model.setRowCount(0);
	}
	/**
	 * 
	 */
	public void createProfCourseTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Course Number");
        model.addColumn("Course Name");
        model.addColumn("Proffesor");
        model.addColumn("Activity");
        model.addColumn("Viewing (click to change)");
		coursesTable = new JTable(model) {
	        @Override
	        public boolean isCellEditable(int row, int column)
	        {
	            return column == 3 || column == 4;
	        }
		};
	}
	/**
	 * 
	 */
	private void createStudentCourseTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Course Number");
        model.addColumn("Course Name");
        model.addColumn("Proffesor");
        model.addColumn("Activity");
        model.addColumn("Viewing (click to change)");
		coursesTable = new JTable(model) {
	        @Override
	        public boolean isCellEditable(int row, int column)
	        {
	            return column == 4;
	        }
		};
	}
	/**
	 * 
	 */
	public void createProfCoursesPan() {
		
		createProfCourseTable();
		TableColumn activityColumn = coursesTable.getColumnModel().getColumn(3);
		activityColumn.setCellEditor(new DefaultCellEditor(createActivityBox()));
		TableColumn viewingColumn = coursesTable.getColumnModel().getColumn(4);
		viewingColumn.setCellEditor(new DefaultCellEditor(createTrueFalseBox()));
		coursePane = new JScrollPane(coursesTable);
		coursePane.setBounds(10, 127, 790, 294);
		jpnCourses.add(coursePane);
		
		lblNewCourse = new JLabel("Create New Course");
		lblNewCourse.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewCourse.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewCourse.setBounds(10, 452, 790, 42);
		jpnCourses.add(lblNewCourse);
		
		lblCourseName = new JLabel("Course Name");
		lblCourseName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCourseName.setBounds(129, 516, 157, 32);
		jpnCourses.add(lblCourseName);
		
		textCourseName = new JTextField();
		textCourseName.setColumns(10);
		textCourseName.setBounds(129, 550, 573, 32);
		jpnCourses.add(textCourseName);
		
		btnAddCourse = new JButton("Add Course");
		btnAddCourse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAddCourse.setBounds(225, 614, 122, 32);
		jpnCourses.add(btnAddCourse);
		
		btnCourseClear = new JButton("Clear");
		btnCourseClear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCourseClear.setBounds(416, 614, 122, 32);
		jpnCourses.add(btnCourseClear);
		
	}
	/**
	 * 
	 */
	private void createStudentCoursesPan() {
		createStudentCourseTable();
		TableColumn activityColumn = coursesTable.getColumnModel().getColumn(3);
		activityColumn.setCellEditor(new DefaultCellEditor(createActivityBox()));
		TableColumn viewingColumn = coursesTable.getColumnModel().getColumn(4);
		viewingColumn.setCellEditor(new DefaultCellEditor(createTrueFalseBox()));
		coursePane = new JScrollPane(coursesTable);
		coursePane.setBounds(10, 127, 790, 294);
		jpnCourses.add(coursePane);
	}
	/**
	 * @param l
	 */
	public void addCourseAddListener(ActionListener l) {
		btnAddCourse.addActionListener(l);
	}
	/**
	 * @param l
	 */
	public void addCourseClearListener(ActionListener l) {
		btnCourseClear.addActionListener(l);
	}
	/**
	 * @param l
	 */
	public void addClassTableListener(TableModelListener l) {
		coursesTable.getModel().addTableModelListener(l);
	}
	/**
	 * 
	 */
	public void clearNewCourseField() {
		textCourseName.setText("");
	}
	/**
	 * @return
	 */
	public String getNewCourseName() {
		return textCourseName.getText();
	}
	/**
	 * @param num
	 * @param name
	 * @param prof
	 * @param active
	 */
	public void addCourseTableRow(int num, String name, String prof, boolean active) {
		DefaultTableModel model = (DefaultTableModel) coursesTable.getModel();
		if(active)
		{
			model.addRow(new Object[]{Integer.toString(num), name, prof, "Active", "False"});
		}
		else
		{
			model.addRow(new Object[]{Integer.toString(num), name, prof, "Not Active", "False"});
		}	
	}
	/**
	 * @param val
	 * @param r
	 * @param c
	 */
	public void setCourseTableElement(Object val, int r, int c) {
		coursesTable.setValueAt(val, r, c);
	}
	/**
	 * @param r
	 * @param c
	 * @return
	 */
	public Object getCourseTableElement(int r, int c) {
		return coursesTable.getValueAt(r, c); 
	}
	/**
	 * @return
	 */
	private JComboBox createActivityBox() {
		  String[] activeBoxStrings = {"Active", "Not Active"};
		  JComboBox activeBox = new JComboBox(activeBoxStrings);
		  return activeBox;
	}
	/**
	 * @return
	 */
	private JComboBox createTrueFalseBox() {
		  String[] boxStrings = {"False", "True"};
		  JComboBox box = new JComboBox(boxStrings);
		  return box;
	}
	/**
	 * @return
	 */
	public int getCourseTableNumRows() {
		return coursesTable.getRowCount();
	}
	
	//students
	/**
	 * 
	 */
	private void createStudentsPan() {
		jpnStudents = new JPanel();
		tabbedPane.addTab("Students", null, jpnStudents, null);
		jpnStudents.setLayout(null);
		
		createStudentListTableForProf();
		
		JScrollPane studentPane = new JScrollPane(studentTable);
		studentPane.setBounds(10, 126, 790, 288);
		jpnStudents.add(studentPane);
		
		TableColumn enrollmentColumnt = studentTable.getColumnModel().getColumn(3);
		enrollmentColumnt.setCellEditor(new DefaultCellEditor(createEnrollmentBox()));
		
		lblStudents = new JLabel("Students");
		lblStudents.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudents.setFont(new Font("Tahoma", Font.PLAIN, 39));
		lblStudents.setBounds(10, 30, 790, 66);
		jpnStudents.add(lblStudents);
		
		lblSearchStudents = new JLabel("Search Students");
		lblSearchStudents.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchStudents.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblSearchStudents.setBounds(0, 452, 800, 37);
		jpnStudents.add(lblSearchStudents);
		
		searchStudentComboBox = new JComboBox();
		searchStudentComboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		searchStudentComboBox.setModel(new DefaultComboBoxModel(new String[] {"Last Name", "I.D. Number"}));
		searchStudentComboBox.setBounds(177, 525, 142, 37);
		jpnStudents.add(searchStudentComboBox);
		
		lblSearchStudentUsing = new JLabel("Search Using: ");
		lblSearchStudentUsing.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSearchStudentUsing.setBounds(64, 525, 103, 37);
		jpnStudents.add(lblSearchStudentUsing);
		
		searchStudenttextField = new JTextField();
		searchStudenttextField.setBounds(329, 525, 442, 37);
		jpnStudents.add(searchStudenttextField);
		searchStudenttextField.setColumns(10);
		
		btnSearchStudent = new JButton("Search");
		btnSearchStudent.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSearchStudent.setBounds(232, 594, 134, 37);
		jpnStudents.add(btnSearchStudent);
		
		btnStudentClear = new JButton("Clear");
		btnStudentClear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnStudentClear.setBounds(433, 594, 134, 37);
		jpnStudents.add(btnStudentClear);
		
	}
	/**
	 * @param first
	 * @param last
	 * @param id
	 */
	public void addStudentTableRow(String first, String last, int id) {
		DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
		model.addRow(new Object[]{first, last, Integer.toString(id), "Not Enrolled"});
	}
	/**
	 * 
	 */
	public void createStudentListTableForProf() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("I.D. Number");
        model.addColumn("Enrollment");
        studentTable = new JTable(model) {
	        @Override
	        public boolean isCellEditable(int row, int column)
	        {
	            return column == 3;
	        }
		};
		//addFakeStudents();//for testing
	}
	/**
	 * @param l
	 */
	public void addSearchSudentListener(ActionListener l) {
		btnSearchStudent.addActionListener(l);
	}
	/**
	 * @param l
	 */
	public void addClearSearchSudentListener(ActionListener l) {
		btnStudentClear.addActionListener(l);
	}
	/**
	 * @param l
	 */
	public void addStudentTableListener(TableModelListener l) {
		studentTable.getModel().addTableModelListener(l);
	}
	/**
	 * 
	 */
	public void clearStudentsTable() {
		DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
		model.setRowCount(0);
	}
	/**
	 * @param row
	 */
	public void removeStudentTableRow(int row) {
		DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
		model.removeRow(row);
	}
	/**
	 * @return
	 */
	private JComboBox createEnrollmentBox() {
		  String[] boxStrings = {"Enrolled", "Not Enrolled"};
		  JComboBox box = new JComboBox(boxStrings);
		  return box;
	}
	/**
	 * 
	 */
	public void clearStudentSearchBox() {
		searchStudenttextField.setText("");
	}
	/**
	 * @return
	 */
	public String getStudentSearchBox() {
		return searchStudenttextField.getText();
	}
	/**
	 * @return
	 */
	public String getStudentSearchType() {
		return (String)searchStudentComboBox.getSelectedItem();
	}
	/**
	 * @param r
	 * @param c
	 * @return
	 */
	public Object getStudentTableElement(int r, int c) {
		return studentTable.getValueAt(r, c); 
	}
	/**
	 * @param val
	 * @param r
	 * @param c
	 */
	public void setStudentTableElement(Object val, int r, int c) {
		studentTable.setValueAt(val, r, c);
	}
	/**
	 * 
	 */
	public void addFakeStudents() {
		addStudentTableRow("Ben", "Luft", 1);
		addStudentTableRow("Rick", "Luft", 2);
		addStudentTableRow("Randy", "Dunn", 123);
		addStudentTableRow("Rob", "Dunn", 3);
	}
	/**
	 * @return
	 */
	public int getStudentTableNumRows() {
		return studentTable.getRowCount();
	}

	//assignment
	/**
	 * 
	 */
	private void createAssignmentsPan() {
		jpnAsg = new JPanel();
		tabbedPane.addTab("Assignments", null, jpnAsg, null);
		jpnAsg.setLayout(null);
		
		lblAssignnents = new JLabel("Assignments");
		lblAssignnents.setHorizontalAlignment(SwingConstants.CENTER);
		lblAssignnents.setFont(new Font("Tahoma", Font.PLAIN, 39));
		lblAssignnents.setBounds(10, 38, 790, 66);
		jpnAsg.add(lblAssignnents);
		
		contentPane.setLayout(gl_contentPane);	
		
	}
	/**
	 * 
	 */
	private void createProfAsgPan() {
		
		btnUploadAssignment = new JButton("Upload Assignment");
		btnUploadAssignment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnUploadAssignment.setBounds(532, 578, 201, 50);
		jpnAsg.add(btnUploadAssignment);
		
		lblAddAssignment = new JLabel("Add Assignment");
		lblAddAssignment.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddAssignment.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblAddAssignment.setBounds(10, 518, 790, 44);
		jpnAsg.add(lblAddAssignment);
		
		lblDueDate = new JLabel("Due Date (MM/DD/YYYY): ");
		lblDueDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDueDate.setBounds(70, 578, 201, 50);
		jpnAsg.add(lblDueDate);

		monthTxt = new JTextField(2);
		monthTxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		monthTxt.setBounds(262, 578, 55, 44);
		monthTxt.setDocument(new TextFieldLimit(2));
		jpnAsg.add(monthTxt);
		monthTxt.setColumns(10);
		
		dayTxt = new JTextField(2);
		dayTxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		dayTxt.setColumns(10);
		dayTxt.setBounds(327, 578, 55, 44);
		dayTxt.setDocument(new TextFieldLimit(2));
		jpnAsg.add(dayTxt);
		
		yearTxt = new JTextField(4);
		yearTxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		yearTxt.setColumns(10);
		yearTxt.setBounds(392, 578, 91, 44);
		yearTxt.setDocument(new TextFieldLimit(4));
		jpnAsg.add(yearTxt);
	}
	/**
	 * 
	 */
	private void createStudentAsgPan() {
		btnAsgDownload = new JButton("Download Assignment");
		btnAsgDownload.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAsgDownload.setBounds(299, 431, 201, 50);
		jpnAsg.add(btnAsgDownload);
		
		btnAsgSubmit = new JButton("Submit Assignment");
		btnAsgSubmit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAsgSubmit.setBounds(299, 578, 201, 44);
		jpnAsg.add(btnAsgSubmit);
		
			
		
	}
	/**
	 * 
	 */
	public void createAsgTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("File Name");
        model.addColumn("Due Date");
        model.addColumn("Activity");
        model.addColumn("Viewing");
        model.addColumn("Grade");
        asgTable = new JTable(model) {
	        @Override
	        public boolean isCellEditable(int row, int column)
	        {
	            return column == 2 || column == 3 || column == 4;
	        }
		};
	}
	/**
	 * @return
	 */
	public String getMonth() {
		return monthTxt.getText();
	}
	/**
	 * @return
	 */
	public String getDay() {
		return dayTxt.getText();
	}
	/**
	 * @return
	 */
	public String getYear() {
		return yearTxt.getText();
	}
	/**
	 * @param l
	 */
	public void addUploadAsgListener(ActionListener l) {
		btnUploadAssignment.addActionListener(l);
	}
	/**
	 * @param l
	 */
	public void addDownloadAsgListener(ActionListener l) {
		btnAsgDownload.addActionListener(l);
	}
	/**
	 * @param l
	 */
	public void addAssignmentsTableListener(TableModelListener l) {
		asgTable.getModel().addTableModelListener(l);
	}
	/**
	 * @param l
	 */
	public void addSubmitAsgListener(ActionListener l)
	{
		btnAsgSubmit.addActionListener(l);
	}
	/**
	 * 
	 */
	public void clearAssignmentsTable() {
		DefaultTableModel model = (DefaultTableModel) asgTable.getModel();
		model.setRowCount(0);
	}
	/**
	 * 
	 */
	public void clearDueDateBoxes() {
		yearTxt.setText("");
		dayTxt.setText("");
		monthTxt.setText("");
	}
	/**
	 * @param r
	 * @param c
	 * @return
	 */
	public Object getAsgTableEl(int r, int c) {
		return asgTable.getValueAt(r, c); 
	}
	/**
	 * @return
	 */
	public int getAsgTableNumRows() {
		return asgTable.getRowCount();
	}
	/**
	 * @param val
	 * @param r
	 * @param c
	 */
	public void setAsgTableElement(Object val, int r, int c) {
		asgTable.setValueAt(val, r, c);
	}
	/**
	 * @param name
	 * @param date
	 * @param activity
	 */
	public void addAsgTableRow(String name, String date, boolean activity) {
		DefaultTableModel model = (DefaultTableModel) asgTable.getModel();
		if(activity == true)
		{
			model.addRow(new Object[]{name, date, "Active", "False"});
		}
		else
		{
			model.addRow(new Object[]{name, date, "Not Active", "False"});
		}
	}
	/**
	 * 
	 */
	public void createProfAsgTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("File Name");
        model.addColumn("Due Date");
        model.addColumn("Activity");
        model.addColumn("Selected");
        asgTable = new JTable(model) {
	        @Override
	        public boolean isCellEditable(int row, int column)
	        {
	            return column == 2 || column == 3;
	        }
		};
		JScrollPane asgScrollPane = new JScrollPane(asgTable);
		asgScrollPane.setBounds(10, 131, 790, 260);
		jpnAsg.add(asgScrollPane);
		TableColumn activityColumn = asgTable.getColumnModel().getColumn(2);
		activityColumn.setCellEditor(new DefaultCellEditor(createActivityBox()));
		TableColumn viewingColumn = asgTable.getColumnModel().getColumn(3);
		viewingColumn.setCellEditor(new DefaultCellEditor(createTrueFalseBox()));
		
	}
	/**
	 * 
	 */
	public void createStudentAsgTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("File Name");
        model.addColumn("Due Date");
        model.addColumn("Activity");
        model.addColumn("Selected");
        model.addColumn("Grade");
        asgTable = new JTable(model) {
	        @Override
	        public boolean isCellEditable(int row, int column)
	        {
	            return column == 3;
	        }
		};
		JScrollPane asgScrollPane = new JScrollPane(asgTable);
		asgScrollPane.setBounds(10, 131, 790, 260);
		jpnAsg.add(asgScrollPane);
		TableColumn activityColumn = asgTable.getColumnModel().getColumn(2);
		activityColumn.setCellEditor(new DefaultCellEditor(createActivityBox()));
		TableColumn viewingColumn = asgTable.getColumnModel().getColumn(3);
		viewingColumn.setCellEditor(new DefaultCellEditor(createTrueFalseBox()));
		
	}

	/**
	 * @param newGrade
	 */
	public void changeAsgGrade(int newGrade) {
		Object grade = (Object)newGrade;
		int rowNum = asgTable.getRowCount();
		for(int r = 0; r < rowNum; r++) {
			Object v = asgTable.getValueAt(r, 3);
			String viewing = (String)v;
			if(viewing.equals("True")) {
				asgTable.setValueAt(Integer.toString(newGrade), r, 4);
			}
		}
	}

	
	//BEN CHANGES
	/**
	 * @return
	 */
	public File getAsgFile() {
		JFileChooser fileBrowser = new JFileChooser();
		File selectedFile = null;
		if(fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileBrowser.getSelectedFile();  
		}
		return selectedFile;
	}
	
	//Submissions
	/**
	 * 
	 */
	private void createSubmissionsPan() {
		jpnSubmissions = new JPanel();
		tabbedPane.addTab("Submissions", null, jpnSubmissions, null);
		jpnSubmissions.setLayout(null);
		
		JLabel lblSubmissions = new JLabel("Submissions");
		lblSubmissions.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubmissions.setFont(new Font("Tahoma", Font.PLAIN, 39));
		lblSubmissions.setBounds(10, 33, 790, 66);
		jpnSubmissions.add(lblSubmissions);
		
		createSubmissionTable();
		tblSubmissions.setFont(new Font("Tahoma", Font.PLAIN, 11));
		JScrollPane submissionsPane = new JScrollPane(tblSubmissions);
		submissionsPane.setBounds(10, 133, 790, 323);
		jpnSubmissions.add(submissionsPane);
		TableColumn selectedColumn = tblSubmissions.getColumnModel().getColumn(4);
		selectedColumn.setCellEditor(new DefaultCellEditor(createTrueFalseBox()));
		//addSubmissionsTableRow("MyAsg", 69, "Maundy", 73);//testing
		
		btnSubmissionDownload = new JButton("Download Submission");
		btnSubmissionDownload.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSubmissionDownload.setBounds(306, 568, 201, 50);
		jpnSubmissions.add(btnSubmissionDownload);
		
		JLabel lblDownloadSelectedSubmission = new JLabel("Download Selected Submission");
		lblDownloadSelectedSubmission.setHorizontalAlignment(SwingConstants.CENTER);
		lblDownloadSelectedSubmission.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblDownloadSelectedSubmission.setBounds(10, 513, 790, 44);
		jpnSubmissions.add(lblDownloadSelectedSubmission);
		
	}
	/**
	 * 
	 */
	public void createSubmissionTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Submission");
        model.addColumn("Student I.D.");
        model.addColumn("Student Last");
        model.addColumn("Grade");
        model.addColumn("Selected");
        tblSubmissions = new JTable(model) {
	        @Override
	        public boolean isCellEditable(int row, int column)
	        {
	            return column == 3 || column == 4;
	        }
		};
	}
	/**
	 * @param l
	 */
	public void addSubmissionDownloadListener(ActionListener l) {
		btnSubmissionDownload.addActionListener(l);
	}
	/**
	 * @param l
	 */
	public void addSubmissionsTableListener(TableModelListener l) {
		tblSubmissions.getModel().addTableModelListener(l);
	}
	/**
	 * 
	 */
	public void clearSubmissionsTable() {
		DefaultTableModel model = (DefaultTableModel) tblSubmissions.getModel();
		model.setRowCount(0);
	}
	/**
	 * @param r
	 * @param c
	 * @return
	 */
	public Object getSubmissionsTableEl(int r, int c) {
		return tblSubmissions.getValueAt(r, c); 
	}
	/**
	 * @param o
	 * @param r
	 * @param c
	 */
	public void setSubmissionsTableEl(Object o, int r, int c) {
		tblSubmissions.setValueAt(o, r, c); 
	}
	/**
	 * @return
	 */
	public int getSubmissionTableNumRows() {
		return tblSubmissions.getRowCount();
	}
	/**
	 * @param asgName
	 * @param stuID
	 * @param stuLastN
	 * @param grade
	 */
	public void addSubmissionsTableRow(String asgName, int stuID, String stuLastN, int grade) {
		DefaultTableModel model = (DefaultTableModel) tblSubmissions.getModel();
		model.addRow(new Object[]{asgName, Integer.toString(stuID), stuLastN, Integer.toString(grade), "False"});

	}
	
	
	//email
	/**
	 * 
	 */
	private void createEmailPan() {
		jpnEmail = new JPanel();
		tabbedPane.addTab("Email", null, jpnEmail, null);
		jpnEmail.setLayout(null);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 39));
		lblEmail.setBounds(10, 27, 790, 66);
		jpnEmail.add(lblEmail);
		
		btnClearEmail = new JButton("Clear");
		btnClearEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClearEmail.setBounds(416, 615, 122, 32);
		jpnEmail.add(btnClearEmail);
		
		btnSendEmail = new JButton("Send");
		btnSendEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSendEmail.setBounds(225, 615, 122, 32);
		jpnEmail.add(btnSendEmail);
		
		JLabel lblSendAnEmail = new JLabel("Send an Email");
		lblSendAnEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblSendAnEmail.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblSendAnEmail.setBounds(10, 93, 790, 42);
		jpnEmail.add(lblSendAnEmail);
		
		txtSubject = new JTextField(50);
		txtSubject.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtSubject.setColumns(10);
		txtSubject.setBounds(80, 161, 589, 42);
		jpnEmail.add(txtSubject);
		
		JLabel lblSubject = new JLabel("Subject:");
		lblSubject.setHorizontalAlignment(SwingConstants.LEFT);
		lblSubject.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSubject.setBounds(10, 161, 65, 42);
		jpnEmail.add(lblSubject);
		
		txtEmailBody = new JTextArea();
		txtEmailBody.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtEmailBody.setLineWrap(true);
		JScrollPane eB = new JScrollPane(txtEmailBody);
		eB.setBounds(10, 224, 790, 376);
		jpnEmail.add(eB);
		
	}
	/**
	 * @return
	 */
	public String getEmailBody() {
		return txtEmailBody.getText();
	}
	/**
	 * @return
	 */
	public String getEmailSubject() {
		return txtSubject.getText();
	}
	/**
	 * @param l
	 */
	public void addSendEmailListener(ActionListener l) {
		btnSendEmail.addActionListener(l);
	}
	/**
	 * @param l
	 */
	public void addClearEmailListener(ActionListener l) {
		btnClearEmail.addActionListener(l);
	}
	/**
	 * 
	 */
	public void clearEmail() {
		txtEmailBody.setText("");
		txtSubject.setText("");
	}
	
}
