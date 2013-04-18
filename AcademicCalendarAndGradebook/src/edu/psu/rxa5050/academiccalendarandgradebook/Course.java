package edu.psu.rxa5050.academiccalendarandgradebook;

import java.io.Serializable;
import java.util.Random;

@SuppressWarnings("serial")
public class Course implements Serializable {
	private int cid;
	private String name;
	private String sdate;
	private String edate;
	private String building;
	private String room;
	private Instructor instructor;
	private String class_time;
	private String notes;
	private Gradebook grades;
	
	//Constructors
	Course(){
		cid = 0;
		name = "";
		sdate = "";
		edate = "";
		building = "";
		room = "";
		instructor = null;
		class_time = "";
		notes = "";
		grades = new Gradebook();
	}
	
	//Setters
	public void setcid(){
		Random randomGenerator = new Random();
		cid = randomGenerator.nextInt(100000);
	}
	public void setcid(int c){
		cid = c;
	}
	public void setname(String n){
		name = n;
	}
	public void setsdate(String s){
		sdate = s;
	}
	public void setedate(String e){
		edate = e;
	}
	public void setbuilding(String b){
		building = b;
	}
	public void setroom(String r){
		room = r;
	}
	public void setinstructor(Instructor i){
		instructor = i;
	}
	public void setclass_time(String ct){
		class_time = ct;
	}
	public void setnotes(String n){
		notes = n;
	}
	public void setgrades(Gradebook g){
		grades = g;
	}
	
	//Getters
	public int getcid(){
		return cid;
	}
	public String getname(){
		return name;
	}
	public String getsdate(){
		return sdate;
	}
	public String getedate(){
		return edate;
	}
	public String getbuilding(){
		return building;
	}
	public String getroom(){
		return room;
	}
	public Instructor getinstructor(){
		return instructor;
	}
	public String getclass_time(){
		return class_time;
	}
	public String getnotes(){
		return notes;
	}
	public Gradebook getgrades(){
		return grades;
	}
	
	@Override
	public String toString(){
		return "Course cid = " + cid + 
				"\nCourse name = " + name +
				"\nCouse start date = " + sdate +
				"\nCourse end date = " + edate +
				"\nCourse building = " + building +
				"\nCourse room = " + room +
				"\nCourse instructor = " + instructor +
				"\nCourse class_time = " + class_time +
				"\nCourse notes = " + notes +
				"\nCourse Gradebook = " + grades;
	}
}

