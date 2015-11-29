package Project1;

import java.util.List;

import Project1.utilities.MaxSeats;
import Project1.utilities.TupleCourse;

public class OptimizerAPI 
{
	public static List<Student> StudentList;
	public static List<Course> CourseList;
	public static List<TupleCourse> PreRequisites;
	public static List<Course> FallCourses;
	public static List<Course> SpringCourses;
	
	
	
	public static List<Student> optimize()
	{
		Project1Scheduler scheduler = new Project1Scheduler();
		scheduler.calculateSchedule("/home/ubuntu/workspace/Project1/student_schedule.txt");
		return scheduler.getRecommendations();
	}
	public Student getStudent(int id)
	{
		return null;
	}
	public static List<Student> getStudentList() {
		return StudentList;
	}
	public static void setStudentList(List<Student> studentList) {
		StudentList = studentList;
	}
	public static List<Course> getCourseList() {
		return CourseList;
	}
	public static void setCourseList(List<Course> courseList) {
		CourseList = courseList;
	}
	public static List<TupleCourse> getPreRequisites() {
		return PreRequisites;
	}
	public static void setPreRequisites(List<TupleCourse> preRequisites) {
		PreRequisites = preRequisites;
	}
	public static List<Course> getFallCourses() {
		return FallCourses;
	}
	public static void setFallCourses(List<Course> fallCourses) {
		FallCourses = fallCourses;
	}
	public static List<Course> getSpringCourses() {
		return SpringCourses;
	}
	public static void setSpringCourses(List<Course> springCourses) {
		SpringCourses = springCourses;
	}
	
	
}
