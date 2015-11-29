package Project1;

public class Course 
{
	private int CourseId;
	private String CourseName;
	private int MaxSeat;
	
	public Course(int courseId, String courseName) {
		super();
		CourseId = courseId;
		CourseName = courseName;
	}
	
	public Course(int courseId, String courseName, int maxSeat) {
		super();
		CourseId = courseId;
		CourseName = courseName;
		MaxSeat = maxSeat;
	}
	

	public Course(int courseId) {
		super();
		CourseId = courseId;
	}

	public int getCourseId() {
		return CourseId;
	}
	public void setCourseId(int courseId) {
		CourseId = courseId;
	}
	public String getCourseName() {
		return CourseName;
	}
	public void setCourseName(String courseName) {
		CourseName = courseName;
	}
	public int getMaxSeat() {
		return MaxSeat;
	}
	public void setMaxSeat(int maxSeat) {
		MaxSeat = maxSeat;
	}
	
	
	
}
