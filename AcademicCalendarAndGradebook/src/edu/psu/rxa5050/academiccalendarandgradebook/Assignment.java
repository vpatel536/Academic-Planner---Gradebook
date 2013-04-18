package edu.psu.rxa5050.academiccalendarandgradebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("serial")
public class Assignment implements Serializable {
	private int aid;
	private String title;
	private String type;
	private int month;
	private int day;
	private int year;
	private int priority;
	private Course course;
	private Instructor instructor;
	private List<Partner> partners;
	private String notes;
	private int completed;
	private Grade grade;
	
	//Constructors
	Assignment(){
		aid= 0;
		title= "";
		type = "Homework";
		month= 0;
		day = 0;
		year = 0;
		priority = 0;
		course = null;
		instructor = null;
		partners = new ArrayList<Partner>();
		notes = "";
		completed = 0;
		grade = new Grade();	
	}
	
	//Debug constructor
	Assignment(int aid, String title, String type, int month, int day,
	int year, int priority, Course course, Instructor 
	instructor, List<Partner> partners, String notes,
	int completed,
	Grade grade){
		this.aid= aid;
		this.title= title;
		this.type = type;
		this.month= month;
		this.day = day;
		this.year = year;
		this.priority = priority;
		this.course = course;
		this.instructor = instructor;
		this.partners = partners;
		this.notes = notes;
		this.completed = completed;
		this.grade = grade;
	}
	//Setters
	public void setaid(){
		Random randomGenerator = new Random();
		aid = randomGenerator.nextInt(100000);
	} 
	public void setaid(int a){
		aid = a;
	}
	public void settitle(String t){
		title = t;
	}
	public void settype(String t){
		type = t;
	}
	public void setmonth(int m){
		month = m;
	}
	public void setday(int d){
		day = d;
	}
	public void setyear(int y){
		year = y;
	}
	public void setpriority(int p){
		priority = p;
	}
	public void setpriority(String p){		
		if(p == "Low")
			priority = 0;
		else if(p == "Medium")
			priority = 1;
		else if(p == "High")
			priority = 2;
		else
			priority = 0;
	}
	public void setCourse (Course c){
		course = c;
	}
	public void setInstructor (Instructor i){
		instructor = i;
	}
	public void setPartner(String name, String email){
		Partner p = new Partner();
		p.setname(name);
		p.setemail(email);
		partners.add(p);
	}
	public void setPartner(List<Partner> p){
		partners = p;
	}
	public void setnotes(String n){
		notes = n;
	}
	public void setCompleted(int c){
		completed = c;
	}
	public void setGrade(Grade g){
		grade = g;
	}
	
	//Getters
	public int getaid(){
		return aid;
	}
	public String gettitle(){
		return title;
	}
	public String gettype(){
		return type;
	}
	public int getmonth(){
		return month;
	}
	public static String getmonthString(int m){
		switch(m){
			case 0:
				return "January";
			case 1:
				return "February";
			case 2:
				return "March";
			case 3:
				return "April";
			case 4:
				return "May";
			case 5:
				return "June";
			case 6:
				return "July";
			case 7:
				return "August";
			case 8:
				return "September";
			case 9:
				return "October";
			case 10:
				return "November";
			case 11: 
				return "December";
			default:
				return "Error";			
		}
	}
	public int getday(){
		return day;
	}
	public int getyear(){
		return year;	
	}
	public int getpriority(){
		return priority;
	}
	public String getpriorityString(int p){
		switch(p){
			case 0:
				return "Low";
			case 1:
				return "Medium";
			case 2:
				return "High";
			default:
				return "Low";
		}
	}
	public Course getCourse(){
		return course;
	}
	public Instructor getinstructor(){
		return instructor;
	}
	public List<Partner> getpartners(){
		return partners;
	}
	public String getnotes(){
		return notes;
	}
	public int getcompleted(){
		return completed;
	}
	public Grade getgrade(){
		return grade;
	}
	
	//Other helpful functions
	public int isAssignmentCompleted(){
		return completed;
	}
	public void AssignmentCompleted(){
		completed = 1;
	}
	@Override
	public String toString(){ 
		return "aid = " + aid + 
				"\nAssignment Name = " + title + 
				"\nAssignment Type = " + type + 
				"\nAssignment Month = " + month + 
				"\nAssignment Day = " + day + 
				"\nAssignment Year = " + year + 
				"\nAssignment Priority = " + getpriorityString(priority) +
				"\nAssignment Course = " + course + 
				"\nAssignment Instructor = " + instructor + 
				"\nAssignment Partners = " + partners + 
				"\nAssignment notes = " + notes + 
				"\nAssignment Grade = " + grade;
	}
}
