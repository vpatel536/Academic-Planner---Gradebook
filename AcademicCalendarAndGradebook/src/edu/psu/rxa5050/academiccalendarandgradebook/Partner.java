package edu.psu.rxa5050.academiccalendarandgradebook;

import java.io.Serializable;
import java.util.Random;

@SuppressWarnings("serial")
public class Partner implements Serializable{
	private int pid;
	private int aid;
	private String name;
	private String email;
	
	//Constructor
	Partner(){
		pid = 0;
		aid = 0;
		name = "";
		email = "";
	}
	//Setters
	public void setpid(){
		Random randomGenerator = new Random();
		pid = randomGenerator.nextInt(100000);
	}
	public void setpid(int p){
		pid = p;
	}
	public void setaid(int a){
		aid= a;
	}
	public void setname(String n){
		name = n;
	}
	public void setemail(String e){
		email = e;
	}
	
	//Getters
	public int getpid(){
		return pid;
	}
	public int getaid(){
		return aid;
	}
	public String getname(){
		return name;
	}
	public String getemail(){
		return email;
	}
	 
	@Override
	public String toString(){
		return "Partner = " + name + " email = " + email + "\n";
	}
}

