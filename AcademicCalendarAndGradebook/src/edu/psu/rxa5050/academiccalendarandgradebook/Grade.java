package edu.psu.rxa5050.academiccalendarandgradebook;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Grade implements Serializable{
	private int gid;
	private int aid;
	private String title;
	private String type;
	private String grade;
	
	//Constructor
	Grade(){
		gid = 0;
		aid= 0;
		title = "";
		type = "Homework";
		grade = "";
	}
	
	//Setters
	public void setgid(int g){
		gid = g;
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
	public void setgrade(String g){
		grade = g;
	}
	
	//Getters
	public int getgid(){
		return gid;
	}
	public int getaid(){
		return aid;
	}
	public String gettitle(){
		return title;
	}
	public String gettype(){
		return type;
	}
	public String getgrade(){
		return grade;
	}
	
	@Override
	public String toString(){
		return "Grade = " + grade;
	}
}
