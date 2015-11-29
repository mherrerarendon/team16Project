package Project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Project1.utilities.TupleCourse;

public class Demo 
{
	public static void main(String[] args) 
	{
		
		//list of students
		//list of students is being acquired using a data folder for the purposes of the demo
		//list of courses
		Course c1 = new Course(1, "first course", 250);
		Course c2 = new Course(2, "second course", 250);
		Course c3 = new Course(3, "third course", 250);
		Course c4 = new Course(4, "fourth course", 250);
		Course c5 = new Course(5, "fifth course", 250);
		Course c6 = new Course(6, "sixth course", 250);
		Course c7 = new Course(7, "seventh course", 250);
		Course c8 = new Course(8, "eight course", 250);
		Course c9 = new Course(9, "ninth course", 250);
		Course c10 = new Course(10, "tenth course", 250);
		Course c11 = new Course(11, "eleventh course", 250);
		Course c12 = new Course(12, "twelveth course", 250);
		Course c13 = new Course(13, "thirteenth course", 250);
		Course c14 = new Course(14, "fourteenth course", 250);
		Course c15 = new Course(15, "fifteenth course", 250);
		Course c16 = new Course(16, "sixteenth course", 250);
		Course c17 = new Course(17, "seventeenth course", 250);
		Course c18 = new Course(18, "eighteenth course", 250);
		//Course List
		List<Course> courseList= new ArrayList<Course>();
		courseList.add(c1);
		courseList.add(c2);
		courseList.add(c3);
		courseList.add(c4);
		courseList.add(c5);
		courseList.add(c6);
		courseList.add(c7);
		courseList.add(c8);
		courseList.add(c9);
		courseList.add(c10);
		courseList.add(c11);
		courseList.add(c12);
		courseList.add(c13);
		courseList.add(c14);
		courseList.add(c15);
		courseList.add(c16);
		courseList.add(c17);
		courseList.add(c18);
		OptimizerAPI.setCourseList(courseList);
		//PreReqs
		List<TupleCourse> PreReqList= new ArrayList<TupleCourse>();
		PreReqList.add(new TupleCourse(c4, c16));
		PreReqList.add(new TupleCourse(c12, c1));
		PreReqList.add(new TupleCourse(c9, c13));
		PreReqList.add(new TupleCourse(c3, c7));
		OptimizerAPI.setPreRequisites(PreReqList);
		//Fall Courses
		List<Course> Fall = new ArrayList<Course>();
		Fall.add(c1);
		Fall.add(c7);
		Fall.add(c11);
		Fall.add(c15);
		Fall.add(c17);
		OptimizerAPI.setFallCourses(Fall);
		//Spring Courses
		List<Course> Spring = new ArrayList<Course>();
		
		Spring.add(c5);
		Spring.add(c10);
		Spring.add(c14);
		
		Spring.add(c16);
		
		Spring.add(c18);
		OptimizerAPI.setSpringCourses(Spring);
		List<Student> studentList = OptimizerAPI.optimize();
		
		System.out.println("recommendation for index3: "+studentList.get(3).getRecommendation().get(0).getCourseId());
		
		
	}
}
