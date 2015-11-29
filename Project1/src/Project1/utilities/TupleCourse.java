package Project1.utilities;

import Project1.Course;

public class TupleCourse
{
	private Course a;
	private Course b;
	public TupleCourse(Course a, Course b) {
		super();
		this.a = a;
		this.b = b;
	}
	public Course getA() {
		return a;
	}
	public void setA(Course a) {
		this.a = a;
	}
	public Course getB() {
		return b;
	}
	public void setB(Course b) {
		this.b = b;
	}
	
	
}
