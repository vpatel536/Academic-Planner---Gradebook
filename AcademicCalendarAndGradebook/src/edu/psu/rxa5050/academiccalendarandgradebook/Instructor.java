package edu.psu.rxa5050.academiccalendarandgradebook;

import java.io.Serializable;
import java.util.Random;

@SuppressWarnings("serial")
public class Instructor implements Serializable {
	int iid;
	private String name;
	private String department;
	private Course course;
	private String email;
	private String webpage;
	private String phone;
	private String office_hours;
	private String notes;
	
	//Constructor
	Instructor(){
		iid = 0;
		name = "";
		department = "";
		course = null;
		email = "";
		webpage = "";
		phone = "";
		office_hours = "";
	}
	
	//Setters
	public void setiid(){
		Random randomGenerator = new Random();
		iid = randomGenerator.nextInt(100000);
	}
	public void setiid(int i){
		iid = i;
	}
	public void setname(String n){
		name = n;
	}
	public void setdepartment(String d){
		department = d;
	}
	public void setcourse(Course c){
		course = c;
	}
	public void setemail(String e){
		email = e;
	}
	public void setwebpage(String w){
		webpage = w;
	}
	public void setphone(String p){
		phone = p;
	}
	public void setoffice_hours(String o){
		office_hours = o;
	}
	public void setnotes(String n){
		notes = n;
	}
	
	//Getters
	public int getiid(){
		return iid;
	}
	public String getname(){
		return name;
	}
	public String getdepartment(){
		return department;
	}
	public Course getcourse(){
		return course;
	}
	public String getemail(){
		return email;
	}
	public String getwebpage(){
		return webpage;
	}
	public String getphone(){
		return phone;
	}
	public String getoffice_hours(){
		return office_hours;
	}
	public String getnotes(){
		return notes;
	}
	
	@Override
	public String toString(){
		return "Instructor iid = " + iid +
				"\nInstructor name = " + name +
				"\nInstructor department = " + department +
				"\nInstructor course = " + course +
				"\nInstructor email = " + email +
				"\nInstructor webpage = " + webpage +
				"\nInstructor phone = " + phone +
				"\nInstructor office_hours = " + office_hours;
				
	}
}
