package frontEnd;

/**
 * Interface to select where a logged in user would save their files
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
interface ClientFilePath
{
	/**
	 * The path to save an assignment
	 */
	static final String assignmentPath = "C:\\Users\\Ben\\workspace\\Lab9\\AssignmentsStudentDownload\\";
	/**
	 * The path to save a submission
	 */
	static final String submissionPath = "C:\\Users\\Ben\\workspace\\Lab9\\SubmissionsProfDownload\\";
	
	/*
	static final String assignmentPath = "C:\\Users\\Rob Dunn\\Documents\\School\\Win_18\\ENSF 409\\Labs\\Final_Project_Submissions_assignments\\assignments\\";
	static final String submissionPath = "C:\\Users\\Rob Dunn\\Documents\\School\\Win_18\\ENSF 409\\Labs\\Final_Project_Submissions_assignments\\submissions\\";
	*/
}