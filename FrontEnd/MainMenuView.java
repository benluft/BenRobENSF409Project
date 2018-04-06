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

	private void createCoursesPan() {
		jpnCourses = new JPanel();
		tabbedPane.addTab("Courses", null, jpnCourses, null);
		jpnCourses.setLayout(null);
		
		String[] courseColumns = {"Course Number", "Course Name", "Proffesor", "Activity", "Viewing (click to change)"};
		Object[][] coursesTest = {
				{"400", "ENEL", "Dr. Dennis Onen", "Active", false},
				{"319", "CPSC", "Dr. Manzera", "Not Active", false},
		};
		//TODO: call from prof view constructor in super style
		createProfTable();

		
		TableColumn activityColumn = coursesTable.getColumnModel().getColumn(3);
		activityColumn.setCellEditor(new DefaultCellEditor(createActivityBox()));
		TableColumn viewingColumn = coursesTable.getColumnModel().getColumn(4);
		//viewingColumn.setCellRenderer(new RadioButtonRenderer());    
		viewingColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));

		coursePane = new JScrollPane(coursesTable);
		coursePane.setBounds(10, 157, 790, 186);
		jpnCourses.add(coursePane);
		
		lblCourses = new JLabel("Courses");
		lblCourses.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourses.setFont(new Font("Tahoma", Font.PLAIN, 39));
		lblCourses.setBounds(80, 52, 606, 59);
		jpnCourses.add(lblCourses);
		
	}
	public void createProfTable() {
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
	private void createStudentTable(Object[][] coursesTest, String[] courseColumns) {
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
		lblCourseName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCourseName.setBounds(129, 516, 157, 32);
		jpnCourses.add(lblCourseName);
		
		textCourseName = new JTextField();
		textCourseName.setColumns(10);
		textCourseName.setBounds(129, 550, 568, 20);
		jpnCourses.add(textCourseName);
		
		btnAddCourse = new JButton("Add Course");
		btnAddCourse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAddCourse.setBounds(225, 614, 122, 32);
		jpnCourses.add(btnAddCourse);
		
		btnCourseClear = new JButton("Clear");
		btnCourseClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
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
	private void createAssignmentsPan() {
		jpnAsg = new JPanel();
		tabbedPane.addTab("Assignments", null, jpnAsg, null);
		jpnAsg.setLayout(null);
		
	}
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
	private void createStudentsPan() {
		jpnStudents = new JPanel();
		tabbedPane.addTab("Students", null, jpnStudents, null);
		jpnStudents.setLayout(null);
		
	}
	private void createEmailPan() {
		jpnEmail = new JPanel();
		tabbedPane.addTab("Email", null, jpnEmail, null);
		jpnEmail.setLayout(null);
		contentPane.setLayout(gl_contentPane);
		
	}
	private JComboBox createActivityBox() {
		  String[] activeBoxStrings = {"Active", "Not Active"};
		  JComboBox activeBox = new JComboBox(activeBoxStrings);
		  return activeBox;
	}
}
