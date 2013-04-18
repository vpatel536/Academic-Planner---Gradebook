package edu.psu.rxa5050.academiccalendarandgradebook;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	//All Static variables
	//Database Version
	private static final int DATABASE_VERSION = 1;
	
	//Database Name
	private static final String DATABASE_NAME = "assignmentManager";
	
	//Assignment table name
	private static final String TABLE_ASSIGNMENT = "Assignment";
	
	//Assignment table Column Names
	private static final String A_ID = "aid";
	private static final String TITLE = "title";
	private static final String TYPE = "type";
	private static final String MONTH = "month";
	private static final String DAY = "day";
	private static final String YEAR = "year";
	private static final String PRIORITY = "priority";
	private static final String C_ID = "cid";
	private static final String I_ID = "iid";
	private static final String NOTES = "notes";
	private static final String COMPLETED = "completed";

	//Instructor table name
	private static final String TABLE_INSTRUCTOR = "Instructor";
	
	//Instructor table Column Names
	//private static final String I_ID
	private static final String NAME = "name";
	private static final String DEPARTMENT = "department";
	//private static final String C_ID
	private static final String EMAIL = "email";
	private static final String WEBPAGE = "webpage";
	private static final String PHONE = "phone";
	private static final String OFFICEHOURS = "officehours";
	//private static final String NOTES
			
	//Course table name
	private static final String TABLE_COURSE = "Course";
	
	//Course table Column Names
	//private static final String C_ID
	//private static final String NAME
	private static final String SDATE = "sdate";
	private static final String EDATE = "edate";
	private static final String BUILDING = "building";
	private static final String ROOM = "room";
	//private static final String I_ID
	private static final String CLASS_TIME = "class_time";
	//private static final String NOTES
	
	//Grade table name
	private static final String TABLE_GRADE = "Grade";
	
	//Grade table Column Names
	//private static final String A_ID
	//private static final String TITLE
	//private static final String TYPE
	private static final String GRADE = "grade";
	
	//Partner table name
	private static final String TABLE_PARTNER = "Partner";
	
	//Partner table Column Names
	private static final String P_ID = "pid";
	//private static final String A_ID
	//private static final String NAME
	//private static final String EMAIL
	
	//Gradebook table name
	private static final String TABLE_GRADEBOOK = "Gradebook";
	
	//Gradebook table Column Names
	//private static final String C_ID
	private static final String GRADETYPE = "gradetype";
	
	//Parameter table name
	private static final String TABLE_PARAMETER = "Parameter";
	
	//Parameter table Column Names
	private static final String PR_ID = "prid";
	//private static final String C_ID
	//private static final String TYPE
	private static final String PERCENTAGE = "percentage";
	
	public DatabaseHandler(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	//Creating Tables
	public void onCreate(SQLiteDatabase db){
		Log.d("came into onCreate", "yes");
		String CREATE_ASSIGNMENT_TABLE = "CREATE TABLE Assignment" +
				"(aid INTEGER PRIMARY KEY, title TEXT NOT NULL, type TEXT NOT NULL," +
				" month INTEGER NOT NULL, day INTEGER NOT NULL, year INTEGER NOT NULL," +
				" priority INTEGER, cid INTEGER, iid INTEGER, notes TEXT," +
				" completed INTEGER)";
		String CREATE_INSRUCTOR_TABLE = "CREATE TABLE Instructor" +
				"(iid INTEGER PRIMARY KEY, name TEXT NOT NULL, department TEXT, " +
				"cid INTEGER, email TEXT, webpage TEXT, phone TEXT, " +
				"officehours TEXT, notes TEXT(25))";
		String CREATE_COURSE_TABLE = "CREATE TABLE Course" +
				"(cid INTEGER PRIMARY KEY, name TEXT NOT NULL, sdate TEXT, " +
				"edate TEXT, building TEXT, room TEXT, iid INTEGER, " +
				"class_time TEXT, notes TEXT)";
		String CREATE_GRADE_TABLE = "CREATE TABLE Grade " +
				"(gid INTEGER PRIMARY KEY, aid INTEGER NOT NULL, title TEXT NOT NULL, type TEXT NOT NULL," +
				"grade TEXT NOT NULL)";
		String CREATE_PARTNER_TABLE = "CREATE TABLE Partner " +
				"(pid INTEGER PRIMARY KEY, aid INTEGER NOT NULL, name TEXT NOT NULL," +
				"email TEXT)";
		String CREATE_GRADEBOOK_TABLE = "CREATE TABLE Gradebook" +
				"(grid INTEGER PRIMARY KEY, cid INTEGER NOT NULL, gradetype TEXT NOT NULL)";
		String CREATE_PARAMETER_TABLE = "CREATE TABLE Parameter" +
				"(prid INTEGER PRIMARY KEY, cid INTEGER NOT NULL, type TEXT NOT NULL, " +
				"percentage REAL NOT NULL)";
				
		db.execSQL(CREATE_ASSIGNMENT_TABLE);
		db.execSQL(CREATE_INSRUCTOR_TABLE);
		db.execSQL(CREATE_COURSE_TABLE);
		db.execSQL(CREATE_GRADE_TABLE);
		db.execSQL(CREATE_PARTNER_TABLE);
		db.execSQL(CREATE_GRADEBOOK_TABLE);
		db.execSQL(CREATE_PARAMETER_TABLE);
	}
	
	//Upgrading database
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		//Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSIGNMENT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTOR);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTNER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADEBOOK);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAMETER);
		
		//Create tables again
		onCreate(db);
	}
	//Setters
	//Adding new Assignment
	public void addAssignment(Assignment assignment){
		Log.d("cane into addAssignment", "yes");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TITLE, assignment.gettitle());
		values.put(TYPE, assignment.gettype());
		values.put(MONTH, assignment.getmonth());
		values.put(DAY, assignment.getday());
		values.put(YEAR, assignment.getyear());
		values.put(PRIORITY, assignment.getpriority());
		Log.d("set priority", "yes");
		if(assignment.getCourse() != null)
			values.put(C_ID, assignment.getCourse().getcid());
		else
			values.put(C_ID, 0);
		if(assignment.getinstructor() != null)
			values.put(I_ID, assignment.getinstructor().getiid());
		else
			values.put(I_ID, 0);
		values.put(NOTES, assignment.getnotes());
		values.put(COMPLETED, assignment.getcompleted());
		Log.d("ContentValues:", values.toString());
		Log.d("Am about to input values to database", "yes");
		long aid = db.insert(TABLE_ASSIGNMENT, null, values);
		assignment.setaid((int) aid);
		db.close(); //Closing database connection

		if(assignment.getpartners().size() > 0){
			for(int count = 0; count < assignment.getpartners().size(); count++){
				assignment.getpartners().get(count).setaid(assignment.getaid());
			}
			addPartner(assignment.getpartners());
		}
		if(assignment.getgrade() != null){
			assignment.getgrade().setaid(assignment.getaid());
			addGrade(assignment.getgrade());			
		}
	}
	//Adding new Instructor
	public void addInstructor(Instructor instructor){
		Log.d("entered addInstructor", "yes");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME, instructor.getname());
		values.put(DEPARTMENT, instructor.getdepartment());
		if(instructor.getcourse() != null)
			values.put(C_ID, instructor.getcourse().getcid());
		else
			values.put(C_ID, 0);
		values.put(EMAIL, instructor.getemail());
		values.put(WEBPAGE, instructor.getwebpage());
		values.put(PHONE, instructor.getphone());
		values.put(OFFICEHOURS, instructor.getoffice_hours());
		values.put(NOTES, instructor.getnotes());
		
		Long success = db.insert(TABLE_INSTRUCTOR, null, values);
		Log.d("added instructor successfully", success.toString());
		db.close();
	}
	//Adding new Course
	public void addCourse(Course course){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME, course.getname());
		values.put(SDATE, course.getsdate());
		values.put(EDATE, course.getedate());
		values.put(BUILDING, course.getbuilding());
		values.put(ROOM, course.getroom());
		if(course.getinstructor() != null)
			values.put(I_ID, course.getinstructor().getiid());
		else
			values.put(I_ID, 0);		
		values.put(CLASS_TIME, course.getclass_time());
		values.put(NOTES, course.getnotes());
				
		long success = db.insert(TABLE_COURSE, null, values);
		db.close();
		if(course.getgrades() != null && success > 0){
			course.setcid((int) success);
			course.getgrades().setcid(course.getcid());
			addGradebook(course.getgrades());		
		}
		db.close();
	}
	//Adding new Grade
	public void addGrade(Grade grade){
		Log.d("went into add grade", "yes");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(A_ID, grade.getaid());
		Log.d("aid", Integer.toString(grade.getaid()));
		values.put(TITLE, grade.gettitle());
		values.put(TYPE, grade.gettype());
		values.put(GRADE, grade.getgrade());
		
		db.insert(TABLE_GRADE, null, values);
		db.close();
	}
	//Adding a partner
	public void addPartner(Partner partner){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(A_ID, partner.getaid());
		values.put(NAME, partner.getname());
		values.put(EMAIL, partner.getemail());
		db.insert(TABLE_PARTNER, null, values);
		db.close();

	}
	//Adding list of Partners
	public void addPartner(List<Partner> partners){
		SQLiteDatabase db = this.getWritableDatabase();
		for(int count = 0; count <partners.size(); count++){
			ContentValues values = new ContentValues();
			values.put(A_ID, partners.get(count).getaid());
			values.put(NAME, partners.get(count).getname());
			values.put(EMAIL, partners.get(count).getemail());
			db.insert(TABLE_PARTNER, null, values);
		}
		db.close();
	}
	//Adding a Gradebook
	public void addGradebook(Gradebook gradebook){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(C_ID, gradebook.getcid());
		values.put(GRADETYPE, gradebook.getgrade_type());
		db.insert(TABLE_GRADEBOOK, null, values);
		db.close();
		addParameters(gradebook.getparameters());
	}	
	//Adding a list of Parameters
	public void addParameters(List<Parameter> parameters){
		SQLiteDatabase db = this.getWritableDatabase();
		for(int count = 0; count< parameters.size(); count++){
			ContentValues values = new ContentValues();
			values.put(C_ID, parameters.get(count).getcid());
			values.put(TYPE, parameters.get(count).gettype());
			values.put(PERCENTAGE, parameters.get(count).getpercentage());
			db.insert(TABLE_PARAMETER, null, values);
		}
		db.close();
	}
	
	//Getters
	//Getting specific assignment
	public Assignment getAssignment(int aid){
		Log.d("Went into getAssignment", "yes: " + Integer.toString(aid));
		String selectQuery = "SELECT * FROM Assignment " +
				" WHERE aid = " + aid;
		Log.d("selectQuery: ", selectQuery);
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		Log.d("getAssignment cursor size: " , Integer.toString(cursor.getCount()));
		Log.d("getAssignment cursor 1st entry: " , cursor.getString(0));
		Assignment assignment = new Assignment();
		assignment.setaid(Integer.parseInt(cursor.getString(0)));
		assignment.settitle(cursor.getString(1));
		assignment.settype(cursor.getString(2));
		assignment.setmonth(Integer.parseInt(cursor.getString(3)));
		assignment.setday(Integer.parseInt(cursor.getString(4)));
		assignment.setyear(Integer.parseInt(cursor.getString(5)));
		assignment.setpriority(Integer.parseInt(cursor.getString(6)));
		Log.d("Got priority", cursor.getString(6));
		Log.d("Course = ", cursor.getString(7));
		if(cursor.getInt(7) > 0)
			assignment.setCourse(getCourse(Integer.parseInt(cursor.getString(7))));
		else
			assignment.setCourse(null);
		if(cursor.getInt(8) > 0)
			assignment.setInstructor(getInstructor(Integer.parseInt(cursor.getString(8))));
		else
			assignment.setInstructor(null);
		assignment.setPartner(getPartners(assignment.getaid()));
		assignment.setnotes(cursor.getString(9));
		assignment.setCompleted(cursor.getInt(10));
		if(cursor.getInt(10) == 1){
			assignment.setGrade(getGrade(assignment.getaid()));
		}
		Log.d("Got everything successfully", "yes");
		return assignment;
	}
	//Getting specific course
	public Course getCourse(int cid){
		String selectQuery = "SELECT * FROM Course" +
				" WHERE cid = " + cid;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		Course course = new Course();
		course.setcid(cursor.getInt(0));
		course.setname(cursor.getString(1));
		course.setsdate(cursor.getString(2));
		course.setedate(cursor.getString(3));
		course.setbuilding(cursor.getString(4));
		course.setroom(cursor.getString(5));
		if(cursor.getInt(6) > 0)
			course.setinstructor(getInstructor(cursor.getInt(6)));
		else
			course.setinstructor(null);
		course.setclass_time(cursor.getString(7));
		course.setnotes(cursor.getString(8));
		return course;
	}
	//Getting specific instructor
	public Instructor getInstructor(int iid){
		Log.d("goes into getInstructor" , "yes");
		String selectQuery = "SELECT * FROM Instructor" +
				" WHERE iid = " + iid;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		Instructor instructor = new Instructor();
		instructor.setiid(cursor.getInt(0));
		Log.d("getting instructor name", cursor.getString(1));
		instructor.setname(cursor.getString(1));
		instructor.setdepartment(cursor.getString(2));
		if(cursor.getInt(3) > 0)
			instructor.setcourse(getCourse(cursor.getInt(3)));
		else
			instructor.setcourse(null);
		
		instructor.setemail(cursor.getString(4));
		instructor.setwebpage(cursor.getString(5));
		instructor.setphone(cursor.getString(6));
		instructor.setoffice_hours(cursor.getString(7));
		instructor.setnotes(cursor.getString(8));
		return instructor;
	}
	//Getting specific partners in assignment
	public List<Partner> getPartners(int aid){
		String selectQuery = "SELECT * FROM Partner" +
				" WHERE aid = " + aid;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		List<Partner> partners = new ArrayList<Partner>();
		if(cursor.moveToFirst()) {
			do{
				Partner partner = new Partner();
				partner.setpid(cursor.getInt(0));
				partner.setaid(cursor.getInt(1));
				partner.setname(cursor.getString(2));
				partner.setemail(cursor.getString(3));
				partners.add(partner);
			} while (cursor.moveToNext());
			return partners;
		}
		else
			return null;		
	}
	//Getting specific grade for a particular assignment
	public Grade getGrade(int aid){
		String selectQuery = "SELECT * FROM Grade" +
				" WHERE aid = " + aid;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		Grade grade = new Grade();
		grade.setgid(cursor.getInt(0));
		grade.setaid(cursor.getInt(1));
		grade.settitle(cursor.getString(2));
		grade.settype(cursor.getString(3));
		grade.setgrade(cursor.getString(4));
		return grade;
	}
	//Getting course's Gradebook
	public Gradebook getGradebok(int cid){
		String selectQuery = "SELECT * FROM Gradebook" +
				" WHERE cid = " + cid;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		Gradebook gradebook = new Gradebook();
		gradebook.setgrid(cursor.getInt(0));
		gradebook.setcid(cursor.getInt(1));
		gradebook.setgrade_type(cursor.getString(2));
		gradebook.setparameter(getParameters(gradebook.getcid()));
		gradebook.setgrade(getGrades(gradebook.getcid()));
		return gradebook;
				
	}
	//Getting course's Gradebook parameters
	public List<Parameter> getParameters(int cid){
		String selectQuery = "SELECT * FROM Paramter" +
			" WHERE cid = " + cid;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		List<Parameter> parameters = new ArrayList<Parameter>();
		if(cursor.moveToFirst()){
			do{
				Parameter parameter = new Parameter();
				parameter.setprid(cursor.getInt(0));
				parameter.setcid(cursor.getInt(1));
				parameter.settype(cursor.getString(2));
				parameter.setpercentage(Float.parseFloat(cursor.getString(3)));
				parameters.add(parameter);
			}while(cursor.moveToNext());
			return parameters;	
		}
		else
			return null;		
	}
	//Getting course's Gradebook grades
	public List<Grade> getGrades(int cid){
		String selectQuery = "SELECT Grade.aid, Grade.title, Grade.type, Grade.grade " +
				"FROM Grade, Assignment, Course " +
				"WHERE Grade.aid = Assignment.aid AND Assignment.cid = Course.cid AND" +
				" Assignment.completed = '1' AND Course.cid = " + cid;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		List<Grade> grades = new ArrayList<Grade>();
		if(cursor.moveToFirst()){
			do{
				Grade grade = new Grade();
				grade.setgid(cursor.getInt(0));
				grade.setaid(cursor.getInt(1));
				grade.settitle(cursor.getString(2));
				grade.settype(cursor.getString(3));
				grade.setgrade(cursor.getString(4));
				grades.add(grade);
			}while(cursor.moveToNext());
			return grades;
		}
		else
			return null;		
	}
	
	//Special Getters
	public List<Assignment> getTodaysAssignments(int month, int day, int year){
		Log.d("start:", "came into today assignments");
		String selectQuery = "SELECT * FROM Assignment" +
				" WHERE month = " + month +
				" AND day = " + day +
				" AND year = " + year +
				" ORDER BY priority DESC";
		Log.d("select query", selectQuery);
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		Log.d("Cursor size:", Integer.toString(cursor.getCount()));
		List<Assignment> assignments = new ArrayList<Assignment>();
		if(cursor.moveToFirst()){
			do{
				assignments.add(getAssignment(cursor.getInt(0)));
			}while(cursor.moveToNext());
			db.close();
			return assignments;
		}
		else{
			db.close();
			return null;
		}
			
	}
	public List<Assignment> getMonthsAssignments(int month, int year){
		String selectQuery = "SELECT * FROM Assignment" +
				" WHERE month = " + month +
				" AND year = " + year +
				" ORDER BY day, priority DESC";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		List<Assignment> assignments = new ArrayList<Assignment>();
		if(cursor.moveToFirst()){
			do{
				assignments.add(getAssignment(cursor.getInt(0)));
			}while(cursor.moveToNext());
			db.close();
			return assignments;
		}
		else{
			db.close();
			return null;
		}
	}
	public List<Assignment> getAllAssignments(){
		String selectQuery = "SELECT * FROM Assignment" + 
				" ORDER BY year, month, day, priority DESC";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		List <Assignment> assignments = new ArrayList<Assignment>();
		if(cursor.moveToFirst()){
			do{
				assignments.add(getAssignment(cursor.getInt(0)));
			}while(cursor.moveToNext());
			return assignments;
		}
		else
			return null;		
	}
	public List<Instructor> getAllInstructors(){
		String selectQuery = "SELECT * FROM Instructor";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		List <Instructor> instructors = new ArrayList <Instructor>();
		if(cursor.moveToFirst()){
			do{
				instructors.add(getInstructor(cursor.getInt(0)));
			}while(cursor.moveToNext());
			db.close();
			return instructors;
		}
		else
			return null;
	}
	public List<Course> getAllCourses(){
		String selectQuery = "SELECT * FROM Course";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		List<Course> courses = new ArrayList<Course>();
		if(cursor.moveToFirst()){
			do{
				courses.add(getCourse(cursor.getInt(0)));
			}while(cursor.moveToNext());
			return courses;
		}
		else
			return null;
	}
	
	//Update 
	public int updateAssignment(Assignment assignment){
		int update;
		Log.d("Went into updateAssignment" , "yes");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TITLE, assignment.gettitle());
		values.put(TYPE, assignment.gettype());
		values.put(MONTH, assignment.getmonth());
		values.put(DAY, assignment.getday());
		values.put(YEAR, assignment.getyear());
		values.put(PRIORITY, assignment.getpriority());
		if(assignment.getCourse() != null)
			values.put(C_ID, assignment.getCourse().getcid());
		if(assignment.getinstructor() != null)
			values.put(I_ID, assignment.getinstructor().getiid());
		values.put(NOTES, assignment.getnotes());
		values.put(COMPLETED, assignment.getcompleted());
		
		update = db.update(TABLE_ASSIGNMENT, values, A_ID + "= ?", 
				new String[] { String.valueOf(assignment.getaid())});
		if(assignment.getpartners() != null)
			updatePartner(assignment.getpartners());
		if(assignment.getgrade() != null)
			updateGrade(assignment.getgrade());			
		return update;
			
	}
	public int updateCourse(Course course){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME, course.getname());
		values.put(SDATE, course.getsdate());
		values.put(EDATE, course.getedate());
		values.put(BUILDING, course.getbuilding());
		values.put(ROOM, course.getroom());
		values.put(I_ID, course.getinstructor().getiid());
		values.put(CLASS_TIME, course.getclass_time());
		values.put(NOTES, course.getnotes());
		
		if(course.getgrades() != null){
			updateGradebook(course.getgrades());
		}
		return db.update(TABLE_COURSE, values, C_ID + "= ?", 
				new String [] { String.valueOf(course.getcid())});
	}
	public int updateInstructor(Instructor instructor){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME, instructor.getname());
		values.put(DEPARTMENT, instructor.getdepartment());
		values.put(C_ID, instructor.getcourse().getcid());
		values.put(EMAIL, instructor.getemail());
		values.put(WEBPAGE, instructor.getwebpage());
		values.put(PHONE, instructor.getphone());
		values.put(OFFICEHOURS, instructor.getoffice_hours());
		values.put(NOTES, instructor.getnotes());
		
		return db.update(TABLE_INSTRUCTOR, values, I_ID + "= ?", 
				new String [] { String.valueOf(instructor.getiid())});
	}
	public int updateGrade(Grade grade){
		int update;
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TITLE, grade.gettitle());
		values.put(TYPE, grade.gettype());
		values.put(GRADE, grade.getgrade());
		
		update = db.update(TABLE_GRADE, values, A_ID + "= ?", 
				new String [] {String.valueOf(grade.getaid())});
		if (update < 1){
			addGrade(grade);
		}
		return update;
	}
	public int updatePartner(List<Partner> partners){
		Log.d("Went into update partner", "yes");
		SQLiteDatabase db = this.getWritableDatabase();
		int updated = 0;
		for(int count = 0; count < partners.size(); count++){
			ContentValues values = new ContentValues();
			values.put(NAME, partners.get(count).getname());
			values.put(EMAIL, partners.get(count).getemail());
			updated += db.update(TABLE_PARTNER, values, P_ID + "= ?", 
					new String [] {String.valueOf(partners.get(count).getpid())});
		}
		Log.d("updated", Integer.toString(updated));
		return updated;
	}
	public int updateGradebook(Gradebook gradebook){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(GRADETYPE, gradebook.getgrade_type());
		if(gradebook.getparameters() != null)
			updateParameters(gradebook.getparameters());
		return db.update(TABLE_GRADEBOOK, values, C_ID + "= ?", 
				new String [] {String.valueOf(gradebook.getcid())});
	}
	public int updateParameters(List <Parameter> parameters){
		SQLiteDatabase db = this.getWritableDatabase();
		int updated = 0;
		for(int count = 0; count < parameters.size(); count++){
			ContentValues values = new ContentValues();
			values.put(TYPE, parameters.get(count).gettype());
			values.put(PERCENTAGE, parameters.get(count).getpercentage());
			updated += db.update(TABLE_PARAMETER, values, PR_ID + "= ?", 
					new String [] {String.valueOf(parameters.get(count).getprid())});	
		}
		return updated;
	}
	
	//Delete
	public void deleteAssignment(Assignment assignment){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ASSIGNMENT, A_ID + "=?", 
				new String [] {String.valueOf(assignment.getaid())});
		db.close();
		deletePartners(assignment.getpartners());
		deleteGrade(assignment.getgrade());
	}
	public void deleteCourse(Course course){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_COURSE, C_ID + "=?", 
				new String [] {String.valueOf(course.getcid())});
		db.close();
		deleteGradebook(course.getgrades());
	}
	public void deleteInstructor(Instructor instructor){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_INSTRUCTOR, I_ID + "=?", 
				new String [] {String.valueOf(instructor.getiid())});
	}
	public void deleteGrade(Grade grade){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_GRADE, A_ID + "=?", 
				new String [] {String.valueOf(grade.getaid())});
		db.close();
	}
	public void deletePartners(List<Partner> partners){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PARTNER, A_ID + "=?", 
				new String [] {String.valueOf(partners.get(0).getaid())});
		db.close();
	}
	public void deletePartner(Partner partner){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PARTNER, P_ID + "=?", 
				new String [] {String.valueOf(partner.getpid())});
		db.close();
	}
	public void deleteGradebook(Gradebook gradebook){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_GRADEBOOK, C_ID + "=?", 
				new String [] {String.valueOf(gradebook.getcid())});
		db.close();
		deleteParameters(gradebook.getparameters());
	}
	public void deleteParameters(List<Parameter> parameters){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PARAMETER, C_ID + "=?", 
				new String [] {String.valueOf(parameters.get(0).getcid())});
		db.close();
	}
	public void deleteParameter(Parameter parameter){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PARAMETER, C_ID + "=?", 
				new String [] {String.valueOf(parameter.getprid())});
		db.close();
	}
}	
		
