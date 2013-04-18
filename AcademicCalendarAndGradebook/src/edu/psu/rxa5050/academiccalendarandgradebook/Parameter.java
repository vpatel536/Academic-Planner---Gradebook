package edu.psu.rxa5050.academiccalendarandgradebook;

import java.io.Serializable;
import java.util.Random;

@SuppressWarnings("serial")
public class Parameter implements Serializable {
	int prid;
	int cid;
	private String type;
	private float percentage;
	
	//Constructor
	Parameter(){
		prid = 0;
		cid = 0;
		type = "Homework";
		percentage = 0;
	}
	
	//Setters
	public void setprid(){
		Random randomGenerator = new Random();
		prid = randomGenerator.nextInt(100000);
	} 
	public void setprid(int pr){
		prid = pr;
	}
	public void setcid(int c){
		cid = c;
	}
	public void settype(String t){
		type = t;
	}
	public void setpercentage(Float p){
		percentage = p;
	}
	
	//Getters
	public int getprid(){
		return prid;
	}
	public int getcid(){
		return cid;
	}
	public String gettype(){
		return type;
	}
	public float getpercentage(){
		return percentage;
	}
	
	@Override
	public String toString(){
		return "Parameter = " + type + ": " + percentage;
	}
}
