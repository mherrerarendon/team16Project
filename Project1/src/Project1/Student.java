package Project1;

import java.util.ArrayList;
import java.util.List;

public class Student 
{
	private String studentName;
	private int studentID;
	private List<Course> prioritizedList;
	private int[] desiredCourses;
	private List<Course> recommendation=new ArrayList<Course>();
	

	public Student()
	{}
	
	
	public Student(String studentName, int studentID,
			List<Course> prioritizedList) {
		super();
		this.studentName = studentName;
		this.studentID = studentID;
		this.prioritizedList = prioritizedList;
	}


	public Student(String studentName, int studentID, int[] desiredCourses) {
		super();
		this.studentName = studentName;
		this.studentID = studentID;
		this.desiredCourses = desiredCourses;
	}
	
	public List<Course> getRecommendation() {
		return recommendation;
	}


	public void setRecommendation(List<Course> recommendation) {
		this.recommendation = recommendation;
	}


	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public int[] getDesiredCourses() {
		return desiredCourses;
	}
	public void setDesiredCourses(int[] desiredCourses) {
		this.desiredCourses = desiredCourses;
	}
	
}
