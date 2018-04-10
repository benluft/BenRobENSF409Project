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
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JLabel;
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


public class MainMenuView extends JFrame {

	private JPanel contentPane = new JPanel();;
	GroupLayout gl_contentPane = new GroupLayout(contentPane);
	private JPanel jpnStudents;
	private JPanel jpnGrades;
	private JPanel jpnDropBox;
	private JPanel jpnAsg;
	private JTabbedPane tabbedPane;
	private JPanel jpnEmail;
	
	//courses panel
	private JPanel jpnCourses;
	private JTable coursesTable;
	private JLabel lblCourses;
	//courses but only for prof
	private JLabel lblNewCourse;
	private JTextField textCourseName;
	private JLabel lblCourseName;
	private JButton btnAddCourse;
	private JButton btnCourseClear;
	private JScrollPane coursePane;
	//student
	private JTable studentTable;
	private JTextField searchStudenttextField;
	private JLabel lblStudents;
	private JLabel lblSearchStudents;
	private JLabel lblSearchStudentUsing;
	private JComboBox searchStudentComboBox;
	private JButton btnSearchStudent;
	private JButton btnStudentClear;
	//assignments
	private JLabel lblAssignnents;
	private JTable asgTable;
	private JButton btnUploadAssignment;
	private JLabel lblAddAssignment;
	private JLabel lblDueDate;
	private JTextField monthTxt;
	private JTextField dayTxt;
	private JTextField yearTxt;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public MainMenuView() {
		setTitle("Main Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 761);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		createMenuTabs();
		setVisible(true);
	}
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
		createDropBoxPan();
		createGradesPan();
		createStudentsPan();
		createEmailPan();
		//if prof only:
		createProfCoursesPan();
		//setProfEditability();

	}
	
	//courses
	private void createCoursesPan() {
		jpnCourses = new JPanel();
		tabbedPane.addTab("Courses", null, jpnCourses, null);
		jpnCourses.setLayout(null);

		//TODO: call from prof view constructor in super style
		createProfCourseTable();

		
		TableColumn activityColumn = coursesTable.getColumnModel().getColumn(3);
		activityColumn.setCellEditor(new DefaultCellEditor(createActivityBox()));
		TableColumn viewingColumn = coursesTable.getColumnModel().getColumn(4);
		viewingColumn.setCellEditor(new DefaultCellEditor(createTrueFalseBox()));

		coursePane = new JScrollPane(coursesTable);
		coursePane.setBounds(10, 127, 790, 294);
		jpnCourses.add(coursePane);
		
		lblCourses = new JLabel("Courses");
		lblCourses.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourses.setFont(new Font("Tahoma", Font.PLAIN, 39));
		lblCourses.setBounds(10, 35, 790, 59);
		jpnCourses.add(lblCourses);
		
	}
	public void clearCoursesTable() {
		DefaultTableModel model = (DefaultTableModel) coursesTable.getModel();
		model.setRowCount(0);
	}
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
	private void createStudentCourseTable(Object[][] coursesTest, String[] courseColumns) {
		coursesTable = new JTable(coursesTest, courseColumns) {
	        @Override
	        public boolean isCellEditable(int row, int column)
	        {
	            return column == 4;
	        }
		};
	}
	public void createProfCoursesPan() {
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
	public void addCourseAddListener(ActionListener l) {
		btnAddCourse.addActionListener(l);
	}
	public void addCourseClearListener(ActionListener l) {
		btnCourseClear.addActionListener(l);
	}
	public void addClassTableListener(TableModelListener l) {
		coursesTable.getModel().addTableModelListener(l);
	}
	public void clearNewCourseField() {
		textCourseName.setText("");
	}
	public String getNewCourseName() {
		return textCourseName.getText();
	}
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
	public void setCourseTableElement(Object val, int r, int c) {
		coursesTable.setValueAt(val, r, c);
	}
	public Object getCourseTableElement(int r, int c) {
		return coursesTable.getValueAt(r, c); 
	}
	private JComboBox createActivityBox() {
		  String[] activeBoxStrings = {"Active", "Not Active"};
		  JComboBox activeBox = new JComboBox(activeBoxStrings);
		  return activeBox;
	}
	private JComboBox createTrueFalseBox() {
		  String[] boxStrings = {"False", "True"};
		  JComboBox box = new JComboBox(boxStrings);
		  return box;
	}
	public int getCourseTableNumRows() {
		return coursesTable.getRowCount();
	}
	
	//students
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
	public void addStudentTableRow(String first, String last, int id) {
		DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
		model.addRow(new Object[]{first, last, Integer.toString(id), "Not Enrolled"});
	}
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
	public void addSearchSudentListener(ActionListener l) {
		btnSearchStudent.addActionListener(l);
	}
	public void addClearSearchSudentListener(ActionListener l) {
		btnStudentClear.addActionListener(l);
	}
	public void addStudentTableListener(TableModelListener l) {
		studentTable.getModel().addTableModelListener(l);
	}
	public void clearStudentsTable() {
		DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
		model.setRowCount(0);
	}
	public void removeStudentTableRow(int row) {
		DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
		model.removeRow(row);
	}
	private JComboBox createEnrollmentBox() {
		  String[] boxStrings = {"Enrolled", "Not Enrolled"};
		  JComboBox box = new JComboBox(boxStrings);
		  return box;
	}
	public void clearStudentSearchBox() {
		searchStudenttextField.setText("");
	}
	public String getStudentSearchBox() {
		return searchStudenttextField.getText();
	}
	public String getStudentSearchType() {
		return (String)searchStudentComboBox.getSelectedItem();
	}
	public Object getStudentTableElement(int r, int c) {
		return studentTable.getValueAt(r, c); 
	}
	public void setStudentTableElement(Object val, int r, int c) {
		studentTable.setValueAt(val, r, c);
	}
	public void addFakeStudents() {
		addStudentTableRow("Ben", "Luft", 1);
		addStudentTableRow("Rick", "Luft", 2);
		addStudentTableRow("Randy", "Dunn", 123);
		addStudentTableRow("Rob", "Dunn", 3);
	}
	public int getStudentTableNumRows() {
		return studentTable.getRowCount();
	}

	//assignment
	private void createAssignmentsPan() {
		jpnAsg = new JPanel();
		tabbedPane.addTab("Assignments", null, jpnAsg, null);
		jpnAsg.setLayout(null);
		
		lblAssignnents = new JLabel("Assignments");
		lblAssignnents.setHorizontalAlignment(SwingConstants.CENTER);
		lblAssignnents.setFont(new Font("Tahoma", Font.PLAIN, 39));
		lblAssignnents.setBounds(10, 38, 790, 66);
		jpnAsg.add(lblAssignnents);
		
		createAsgTable();
		JScrollPane asgScrollPane = new JScrollPane(asgTable);
		asgScrollPane.setBounds(10, 131, 790, 260);
		jpnAsg.add(asgScrollPane);
		TableColumn activityColumn = asgTable.getColumnModel().getColumn(2);
		activityColumn.setCellEditor(new DefaultCellEditor(createActivityBox()));
		
		btnUploadAssignment = new JButton("Upload Assignment");
		btnUploadAssignment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnUploadAssignment.setBounds(315, 596, 201, 50);
		jpnAsg.add(btnUploadAssignment);
		
		lblAddAssignment = new JLabel("Add Assignment");
		lblAddAssignment.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddAssignment.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblAddAssignment.setBounds(10, 425, 790, 44);
		jpnAsg.add(lblAddAssignment);
		
		lblDueDate = new JLabel("Due Date (MM/DD/YYYY): ");
		lblDueDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDueDate.setBounds(153, 508, 201, 50);
		jpnAsg.add(lblDueDate);

		monthTxt = new JTextField(2);
		monthTxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		monthTxt.setBounds(345, 508, 55, 44);
		//monthTxt.setDocument(new TextFieldLimit(2));
		jpnAsg.add(monthTxt);
		monthTxt.setColumns(10);
		
		dayTxt = new JTextField(2);
		dayTxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		dayTxt.setColumns(10);
		dayTxt.setBounds(410, 508, 55, 44);
		//dayTxt.setDocument(new TextFieldLimit(2));
		jpnAsg.add(dayTxt);
		
		yearTxt = new JTextField(4);
		yearTxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		yearTxt.setColumns(10);
		yearTxt.setBounds(475, 508, 91, 44);
		//yearTxt.setDocument(new TextFieldLimit(4));
		jpnAsg.add(yearTxt);
		contentPane.setLayout(gl_contentPane);
		
	}
	public void createAsgTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("File Name");
        model.addColumn("Due Date");
        model.addColumn("Activity");
        asgTable = new JTable(model) {
	        @Override
	        public boolean isCellEditable(int row, int column)
	        {
	            return column == 2;
	        }
		};
		//addAsgTableRow("Flying 1", "Tomorrow");
	}
	public String getMonth() {
		return monthTxt.getText();
	}
	public String getDay() {
		return dayTxt.getText();
	}
	public String getYear() {
		return yearTxt.getText();
	}
	public void addUploadListener(ActionListener l) {
		btnUploadAssignment.addActionListener(l);
	}
	public void addAssignmentsTableListener(TableModelListener l) {
		asgTable.getModel().addTableModelListener(l);
	}
	public void clearAssignmentsTable() {
		DefaultTableModel model = (DefaultTableModel) asgTable.getModel();
		model.setRowCount(0);
	}
	public void clearDueDateBoxes() {
		yearTxt.setText("");
		dayTxt.setText("");
		monthTxt.setText("");
	}
	public Object getAsgTableEl(int r, int c) {
		return asgTable.getValueAt(r, c); 
	}
	public void addAsgTableRow(String name, String date, boolean activity) {
		DefaultTableModel model = (DefaultTableModel) asgTable.getModel();
		if(activity == true)
		{
			model.addRow(new Object[]{name, date, "Active"});
		}
		else
		{
			model.addRow(new Object[]{name, date, "Not Active"});
		}
	}
	
	//BEN CHANGES
	public File getAsgFile() {
		JFileChooser fileBrowser = new JFileChooser();
		File selectedFile = null;
		if(fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileBrowser.getSelectedFile();  
		}
		return selectedFile;
	}
	
	//dropBox
	private void createDropBoxPan() {
		jpnDropBox = new JPanel();
		tabbedPane.addTab("Drop Box", null, jpnDropBox, null);
		jpnDropBox.setLayout(null);
		
	}
	private void createGradesPan() {
		jpnGrades = new JPanel();
		tabbedPane.addTab("Grades", null, jpnGrades, null);
		jpnGrades.setLayout(null);
		
	}
	private void createEmailPan() {
		jpnEmail = new JPanel();
		tabbedPane.addTab("Email", null, jpnEmail, null);
		jpnEmail.setLayout(null);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 39));
		lblEmail.setBounds(10, 37, 790, 66);
		jpnEmail.add(lblEmail);
		
		JButton button = new JButton("Clear");
		button.setFont(new Font("Tahoma", Font.PLAIN, 14));
		button.setBounds(416, 573, 122, 32);
		jpnEmail.add(button);
		
		JButton button_1 = new JButton("Add Course");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		button_1.setBounds(225, 573, 122, 32);
		jpnEmail.add(button_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(10, 172, 790, 372);
		jpnEmail.add(textField);
		
		JLabel lblSendAnEmail = new JLabel("Send an Email to the Professor");
		lblSendAnEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblSendAnEmail.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblSendAnEmail.setBounds(10, 130, 790, 42);
		jpnEmail.add(lblSendAnEmail);
		
	}
}
