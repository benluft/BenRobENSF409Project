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
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
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
		//viewingColumn.setCellRenderer(new RadioButtonRenderer());    
		viewingColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));

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
	public void addCourseTableRow(int num, String name, String prof) {
		DefaultTableModel model = (DefaultTableModel) coursesTable.getModel();
		model.addRow(new Object[]{Integer.toString(num), name, prof, "Not Active", "false"});
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
	
	//assignment
	private void createAssignmentsPan() {
		jpnAsg = new JPanel();
		tabbedPane.addTab("Assignments", null, jpnAsg, null);
		jpnAsg.setLayout(null);
		
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
		contentPane.setLayout(gl_contentPane);
		
	}

}
