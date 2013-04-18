package edu.psu.rxa5050.academiccalendarandgradebook;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Gradebook implements Serializable {
	int grid;
	int cid;
	private String grade_type;
	private List<Parameter> parameters;
	private List<Grade> grades;
	
	//Constructor
	Gradebook(){
		grid = 0;
		cid = 0;
		grade_type = "Points";
		parameters = new ArrayList<Parameter>();
		grades = new ArrayList<Grade>();
	}
	
	//Setters
	public void setgrid(int gr){
		grid = gr;
	}
	public void setcid(int c){
		cid = c;
	}
	public void setgrade_type(String g){
		grade_type = g;
	}
	public void setparameter(String t, float p){
		Parameter param = new Parameter();
		param.settype(t);
		param.setpercentage(p);
		parameters.add(param);
	}
	public void setparameter(Parameter p){
		parameters.add(p);
	}
	public void setparameter(List<Parameter> p){
		parameters = p;
	}
	public void setgrade(int aid, String title, String type, String grade){
		Grade g = new Grade();
		g.setaid(aid);
		g.settitle(title);
		g.settype(type);
		g.setgrade(grade);
		grades.add(g);
	}
	public void setgrade(Grade g){
		grades.add(g);
	}
	public void setgrade(List<Grade> g){
		grades = g;
	}
	//Getters
	public int getgrid(){
		return grid;
	}
	public int getcid(){
		return cid;
	}
	public String getgrade_type(){
		return grade_type;
	}
	public List<Parameter> getparameters(){
		return parameters;
	}
	public List<Grade> getgrades(){
		return grades;
	}
	
	@Override
	public String toString(){
		String p= "";
		String g= "";
		for(int count=0; count<parameters.size(); count++)
			p += parameters.get(count).toString() + "\n";	
		for(int count=0; count<grades.size(); count++)
			g += grades.get(count).toString() + "\n" ;
		return "Gradebook =" + "\nParameters: " + p + 
				"\nGrades: " + g;
	}
}
