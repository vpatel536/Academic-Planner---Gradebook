package edu.psu.rxa5050.academiccalendarandgradebook;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Button add_assignment;
	CalendarView calendar;
	TextView calendarDay;
	ListView calendarDayEvents;
	TextView calendarMonth;
	ListView calendarMonthEvents;
	int month;
	int day;
	int year;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		add_assignment = (Button) findViewById(R.id.add_assignment_main_button);
		calendar = (CalendarView) findViewById (R.id.calendar);
		calendarDay = (TextView) findViewById (R.id.calendarDay);
		calendarDayEvents = (ListView) findViewById (R.id.calendarDayEvents);
		calendarMonth = (TextView) findViewById (R.id.calendarMonth);
		calendarMonthEvents = (ListView) findViewById (R.id.calendarMonthEvents);
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		calendarDay.setText("Assignments Due " + Assignment.getmonthString(month)
				+ " " + day + ", " + year);
		calendarMonth.setText("Assignments Due " + Assignment.getmonthString(month) 
				+ ", " + year);
		//debugDatabase();
		setupAdapter(month, day, year);
		calendar.setOnDateChangeListener(new OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(CalendarView view, int y, int m, int d){
				year= y;
				day = d;
				month = m;
				calendarDay.setText("Assignments Due " + Assignment.getmonthString(month)
						+ " " + day + ", " + year);
				calendarMonth.setText("Assignments Due " + Assignment.getmonthString(month) 
						+ ", " + year);
				setupAdapter(month, day, year);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void add_assignment(View view) {
		Intent assignment = new Intent(this, AddAssignment.class);
		assignment.putExtra("month", month);
		assignment.putExtra("day", day);
		assignment.putExtra("year", year);
		startActivity(assignment);
	}
	
	public void setupAdapter(int month, int day, int year){
		SimpleAdapter de;
		SimpleAdapter me;
		DatabaseHandler db = new DatabaseHandler(this);
		final List<Assignment> todayAssignments = db.getTodaysAssignments(month, day, year);
		final List<Assignment> monthAssignments = db.getMonthsAssignments(month, year);
		ArrayList<HashMap<String,String>> todaylist = new ArrayList<HashMap<String,String>>();
		ArrayList<HashMap<String,String>> monthlist = new ArrayList<HashMap<String,String>>();
		
		if(todayAssignments == null){
			HashMap<String,String> item = new HashMap<String,String>();
			item.put("line1", "No Assignments Due Today");
			item.put("line2", "");
			todaylist.add(item);
			calendarDayEvents.setClickable(false);
		}
		else{
			for(int count = 0; count < todayAssignments.size(); count++){
				HashMap<String,String> item = new HashMap<String, String>();
				item.put("line1", todayAssignments.get(count).gettitle());
				item.put("line2", "Due Date: " + 
						Assignment.getmonthString(todayAssignments.get(count).getmonth())
						+ " " + todayAssignments.get(count).getday()
						+ ", " + todayAssignments.get(count).getyear());
				todaylist.add(item);
			}
		}
		if(monthAssignments == null){
			HashMap<String,String> item = new HashMap<String,String>();
			item.put("line1", "No Assignments Due This Month");
			item.put("line2", "");
			monthlist.add(item);
			calendarMonthEvents.setClickable(false);
		}
		else{
			for(int count = 0; count < monthAssignments.size(); count++){
				HashMap<String,String> item = new HashMap<String, String>();
				item.put("line1", monthAssignments.get(count).gettitle());
				item.put("line2", "Due Date: " + 
						Assignment.getmonthString(monthAssignments.get(count).getmonth())
						+ " " + monthAssignments.get(count).getday()
						+ ", " + monthAssignments.get(count).getyear());
				monthlist.add(item);
			}
		}
		de = new SimpleAdapter(this, todaylist, 
				android.R.layout.two_line_list_item, 
				new String [] {"line1", "line2"}, 
				new int[] {android.R.id.text1, android.R.id.text2});
		me = new SimpleAdapter(this, monthlist, 
				android.R.layout.two_line_list_item, 
				new String [] {"line1", "line2"}, 
				new int[] {android.R.id.text1, android.R.id.text2});
		calendarDayEvents.setAdapter(de);
		calendarMonthEvents.setAdapter(me);
		calendarDayEvents.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id){
				if(calendarDayEvents.isClickable())
					assignmentDetail(1, todayAssignments.get(position));
			}
		});
		calendarMonthEvents.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id){
				if(calendarMonthEvents.isClickable())
					assignmentDetail(2, monthAssignments.get(position));
			}
		});
	}
	public void debugDatabase(){
		Log.d("came into debugDatabase", "yes");
		DatabaseHandler db = new DatabaseHandler(this);
		Assignment assignment1 = new Assignment(1, "Assignment1" , "Homework", 2, 7, 2013, 2, new Course(), new Instructor(), new ArrayList<Partner>(), "Hello", 0, new Grade());
		Assignment assignment2 = new Assignment(1, "Assignment2" , "Homework", 2, 7, 2013, 1, new Course(), new Instructor(), new ArrayList<Partner>(), "Hello", 0, new Grade());
		Assignment assignment3 = new Assignment(1, "Assignment3" , "Homework", 2, 9, 2013, 2, new Course(), new Instructor(), new ArrayList<Partner>(), "Hello", 0, new Grade());
		db.addAssignment(assignment1);
		db.addAssignment(assignment2);
		db.addAssignment(assignment3);
	}
	
	public void assignmentDetail(int c, Assignment assignment){
		switch(c){
			case 1:
				Intent a_intent = new Intent(this, AssignmentDetailActivity.class);
				a_intent.putExtra("assignment", assignment);
				startActivity(a_intent);
				break;
			case 2:
				Intent b_intent = new Intent(this, AssignmentDetailActivity.class);
				b_intent.putExtra("assignment", assignment);
				startActivity(b_intent);
				break;
			default:
				break;
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.assignment_action:
				Intent assignment_intent = new Intent(this, Assignments.class);
				startActivity(assignment_intent);
				return true;
			case R.id.planner_action:
				Intent planner_intent = new Intent(this, Instructors.class);
				startActivity(planner_intent);
				return true;
			case R.id.course_action:
				Intent course_intent = new Intent(this, Courses.class);
				startActivity(course_intent);
				return true;
			case R.id.gradebook_action:
				Intent gradebook_intent = new Intent(this, Gradebooks.class);
				startActivity(gradebook_intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);		
		}
	}
}
