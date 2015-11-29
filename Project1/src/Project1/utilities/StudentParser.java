package Project1.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Project1.Student;

public class StudentParser 
{
	public List<Student> readFile(String dataFolder)
	{
		List<Student> studentList=new ArrayList<Student>();
	    int counter=0;	    
	        
	    File file=new File(dataFolder);
	    
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    BufferedReader bf=null;
	    String[] str=new String[12];
	    String line;
	 
	    try 
	    {
	    	fis = new FileInputStream(file);
	 
	    	
	    	bis = new BufferedInputStream(fis);
	    	
	    	bf=new BufferedReader(new InputStreamReader(bis));
	    	
	    	bf.readLine();
	    	bf.readLine();
	    	
	    	while ((line=bf.readLine()) != null) 
	    	{	  		
	    		
	    	
	    		
	    		str=line.split("\\.", -2);
	    		int[] prioritizedList=new int[str.length-1]; //str.length should be 12, not 13. Hence -1
	    		
	    		for (int i=0;i<prioritizedList.length;i++)
	    		{
	    			str[i]=str[i].trim();
	    		

	    			prioritizedList[i]=Integer.parseInt(str[i]);
	    		}
	    		studentList.add(new Student(null, counter, prioritizedList));
	    		counter++;
	    		
	    	}
	 
	      // dispose all the resources after using them.
	    	fis.close();
	    	bis.close();
	
	    } 
	    catch (FileNotFoundException e) 
	    {
	    	e.printStackTrace();
	    } 
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    }
	
	    return studentList;	
	}
}
