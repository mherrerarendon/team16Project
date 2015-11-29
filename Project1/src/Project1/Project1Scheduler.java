package Project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Project1.utilities.StudentParser;
import Project1.utilities.TupleCourse;

import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class Project1Scheduler implements Scheduler 
{
	private static final int numStudents=600;   //replace 600 with OptimizerAPI.getStudentList().size();  
    private static final int numSemesters=12;    
    private static final int numCourses=OptimizerAPI.getCourseList().size();
    
    private static List<Student> studentList= new ArrayList<Student>();
    
	private GRBVar[][][] yijk=new GRBVar[numStudents][numCourses][numSemesters];
	private GRBVar X;
    
	public void calculateSchedule(String dataFolder)//will not need arguments once studentList is set through API
	{
		
		
		StudentParser studentParser=new StudentParser();
		studentList=studentParser.readFile(dataFolder);
		//uncomment following statement when studentList is set through API. Comment out the 2 previous statements
		//studentList=OptimizerAPI.getStudentList();
		
		
	
		int[] fallCourses=new int[OptimizerAPI.getFallCourses().size()];
		for (int i=0; i<fallCourses.length; i++)
		{
			fallCourses[i]=OptimizerAPI.getFallCourses().get(i).getCourseId();
		}
		int[] springCourses=new int[OptimizerAPI.getSpringCourses().size()];
		for (int i=0; i<springCourses.length; i++)
		{
			springCourses[i]=OptimizerAPI.getSpringCourses().get(i).getCourseId();
		}
		
		
		try 
		{			
			GRBEnv env;
			env = new GRBEnv("mip1.log");
			GRBModel model = new GRBModel(env);
						
			X=model.addVar(0, 600, 0.0, GRB.INTEGER, "Max Capacity");
			
			for (int i=0;i<numStudents;i++)//students
			{				
				for(int j=0;j<numCourses;j++)//courses
				{					
					for(int k=0;k<numSemesters;k++)//semester
					{
						String s="y"+i+j+k;
						yijk[i][j][k]=model.addVar(0, 1, 0.0, GRB.BINARY, s);																
					}					
				}
			}
			model.update();
			
			//CONSTRAINT: DESIRED COURSES
			
			for (int i=0;i<numStudents;i++)
			{
				for (int j=0;j<numCourses;j++)
				{
					
					if (containsInt(studentList.get(i).getDesiredCourses(), j+1)==false)
					{
						GRBLinExpr expr = new GRBLinExpr();
						for (int k=0;k<numSemesters;k++)
						{
							expr.addTerm(1, yijk[i][j][k]);
						}
						String s="course"+j+"student"+i;
						model.addConstr(expr, GRB.EQUAL, 0, s );
					}
					else
					{
						GRBLinExpr expr = new GRBLinExpr();
						for (int k=0;k<numSemesters;k++)
						{
							expr.addTerm(1, yijk[i][j][k]);
						}
						String s="course"+j+"student"+i;
						model.addConstr(expr, GRB.EQUAL, 1, s );
					}
				}
			}
			
			//CONSTRAINT: ONLY TWO COURSES PER SEMESTER
			
			for (int i=0; i<numStudents;i++)
			{
				for(int k=0; k<numSemesters;k++)
				{
					GRBLinExpr expr = new GRBLinExpr();
					for (int j=0; j<numCourses;j++)
					{
						expr.addTerm(1, yijk[i][j][k]);
					}
					String s="student"+i+"semester"+k;
					model.addConstr(expr, GRB.LESS_EQUAL, 2, s);
				}
			}
			
			//CONSTRAINT: AVAILABILITY OF COURSES (FALL)
			for (int i=0;i<numStudents;i++)
			{
				for (int j:fallCourses)
				{
					GRBLinExpr expr = new GRBLinExpr();
					for (int k=1;k<numSemesters;k=k+3)//make course j unavailable for spring semesters
					{
						expr.addTerm(1, yijk[i][j-1][k]);
					}
					
					for (int k=2;k<numSemesters;k=k+3)//make course j unavailable for summer semesters
					{
						expr.addTerm(1, yijk[i][j-1][k]);
					}
					String s="student"+i+"course"+j;
					model.addConstr(expr, GRB.EQUAL, 0, s);
				}				
			}
			
			//CONSTRAINT: AVAILABILITY OF COURSES (SPRING)
			for (int i=0;i<numStudents;i++)
			{
				for (int j:springCourses)
				{
					GRBLinExpr expr = new GRBLinExpr();
					for (int k=0;k<numSemesters;k=k+3)//make course j unavailable for spring semesters
					{
						expr.addTerm(1, yijk[i][j-1][k]);
					}
					
					for (int k=2;k<numSemesters;k=k+3)//make course j unavailable for summer semesters
					{
						expr.addTerm(1, yijk[i][j-1][k]);
					}
					String s="student"+i+"course"+j;
					model.addConstr(expr, GRB.EQUAL, 0, s);
				}				
			}
			
			for (TupleCourse preReq: OptimizerAPI.getPreRequisites())
			{
				for (int i=0;i<numStudents;i++)
				{
					for (int k1=1;k1<numSemesters-1;k1++)
					{
						if (containsInt(studentList.get(i).getDesiredCourses(), preReq.getB().getCourseId()))
						{
							GRBLinExpr expr = new GRBLinExpr();
							GRBLinExpr expr2 = new GRBLinExpr();
							for (int k=0;k<k1;k++)
							{
								expr.addTerm(1, yijk[i][preReq.getA().getCourseId()-1][k]);
								expr2.addTerm(1, yijk[i][preReq.getB().getCourseId()-1][k+1]);
							}						
							String s="student"+i+"course 4 and 16 prerequisite";
							model.addConstr(expr2, GRB.LESS_EQUAL, expr, s);
						}
					}			
				}
			}
			

			//CONSTRAINT: CLASS SIZE<X
			for (int j=0;j<numCourses;j++)
			{
				for (int k=0;k<numSemesters;k++)
				{
					GRBLinExpr expr = new GRBLinExpr();
					for (int i=0;i<numStudents;i++)
					{
						expr.addTerm(1, yijk[i][j][k]);
					}
					String s="course"+j+"semester"+k;
					model.addConstr(expr, GRB.LESS_EQUAL, X, s);
				}
			}
			//CONSTRAINT: MAX SEATS
			for (int j=0;j<numCourses;j++)
			{
				for (int k=0;k<numSemesters;k++)
				{
					GRBLinExpr expr = new GRBLinExpr();
					for (int i=0;i<numStudents;i++)
					{
						expr.addTerm(1, yijk[i][j][k]);
					}
					String s="course"+j+"semester"+k;
					model.addConstr(expr, GRB.LESS_EQUAL, OptimizerAPI.CourseList.get(j).getMaxSeat(), s);
				}
			}
			
			//SET OBJECTIVE
			GRBLinExpr expr = new GRBLinExpr();
			expr.addTerm(1, X);
			
			model.update();
			model.setObjective(expr, GRB.MINIMIZE);
			model.optimize();
			
			double objectiveValue = model.get(GRB.DoubleAttr.ObjVal);            
            System.out.printf( "Ojective value = %f\n", objectiveValue );
			
		} 
		catch (GRBException e) 
		{
			e.printStackTrace();
		}

		
	}

	public double getObjectiveValue() 
	{
		// TODO: You will need to implement this
		double objectiveValue=0;
		try 
		{
			objectiveValue=X.get(GRB.DoubleAttr.X);
		} 
		catch (GRBException e) 
		{
			
			e.printStackTrace();
		}
		return objectiveValue;
	}
	public List<Student> getRecommendations()
	{
		int k=0;
		for (int i=0; i<studentList.size(); i++)
		{
			for (int j=0;j<numCourses;j++)
			{
				try {
					if (yijk[i][j][k].get(GRB.DoubleAttr.X)==1)
					{
						
						studentList.get(i).getRecommendation().add(OptimizerAPI.CourseList.get(j));
					}
				} catch (GRBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return studentList;
		
	}
	public Vector<String> getCoursesForStudentSemester(String student, String semester) 
	{
		
		// TODO: You will need to implement this
		String[] courseNames={"CS 6210 Advanced Operating Systems", "CS 6250 Computer Networks", " CS 6300 Software Development Process", "CS 7641 Machine Learning", " CS 6290 High Performance Computer Architecture", "CS 6310 Software Architecture and Design", " CS 6440 Intro to Health Informatics", " CS 6505 Computability, Complexity and Algorithms", " CS 7637 Knowledge-Based Artificial Intelligence, Cognitive Systems", "CS 4495 Computer Vision", "CS 6475 Computational Photography", " CS 8803-002 Introduction to Operating Systems", "CS 8803-001 Artificial Intelligence for Robotics", "CS 6035 Introduction to Information Security", " CSE 6220 High-Performance Computing", "CS 7646 MachineLearning for Trading", "CS 8803 Special Topics: Reinforcement Learning", "CSE 8803 Special Topics: Big Data"};
		Vector<String> courses=new Vector<String>(0);
		try
		{
			int i=Integer.parseInt(student);
			int k=Integer.parseInt(semester);
			
			for (int j=0;j<numCourses;j++)
			{
				if (yijk[i][j][k].get(GRB.DoubleAttr.X)==1)
				{
					courses.addElement(courseNames[j+1]);
				}
			}			
		}
		
		catch (GRBException e) 
		{
			e.printStackTrace();
		}
		return courses;
	}

	public Vector<String> getStudentsForCourseSemester(String course, String semester) {
		// TODO: You will need to implement this
		Vector<String> students=new Vector<String>(0);
		try
		{
			int j=Integer.parseInt(course);
			int k=Integer.parseInt(semester);
			
			for (int i=0;i<numStudents;i++)
			{
				if (yijk[i][j][k].get(GRB.DoubleAttr.X)==1)
				{					
					students.addElement(Integer.toString(i));
				}
			}			
		}
		
		catch (GRBException e) 
		{
			e.printStackTrace();
		}
		return students;
		
	}
	
	private boolean containsInt(int[] a, int key)
	{
		for (int i=0; i<numSemesters; i++)
		{
			if (a[i]==key)
				return true;
		}
		return false;
	}
	
	
}
