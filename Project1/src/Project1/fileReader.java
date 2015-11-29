package Project1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStreamReader;

public class fileReader 
{
	public Student[] readFile(String dataFolder)
	{
		Student[] studentList=new Student[600];
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
	 
	    	// Here BufferedInputStream is added for fast reading.
	    	bis = new BufferedInputStream(fis);
	    	
	    	bf=new BufferedReader(new InputStreamReader(bis));
	    	
	    	bf.readLine();
	    	bf.readLine();
	    	
	    	while ((line=bf.readLine()) != null) 
	    	{	  		
	    		
	    		studentList[counter]=new Student();
	    		str=line.split("\\.", -2);
	    		
	    		for (int i=0;i<12;i++)
	    		{
	    			str[i]=str[i].trim();
	    			
	    			studentList[counter].getDesiredCourses()[i]=Integer.parseInt(str[i]);
	    		}	    		
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
