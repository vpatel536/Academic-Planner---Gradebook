package edu.psu.rxa5050.academiccalendarandgradebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.support.v4.app.NavUtils;

public class Assignments extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assignments);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		SimpleAdapter de;
		ListView assignments_list = (ListView) findViewById(R.id.assignments_list);
		DatabaseHandler db = new DatabaseHandler(this);
		List<Assignment> AllAssignments = db.getAllAssignments();
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		
		if(AllAssignments == null){
			HashMap<String,String> item = new HashMap<String,String>();
			item.put("line1", "No Assignments");
			item.put("line2", "");
			list.add(item);
		}
		else{
			for(int count = 0; count < AllAssignments.size(); count++){
				HashMap<String,String> item = new HashMap<String, String>();
				item.put("line1", AllAssignments.get(count).gettitle());
				item.put("line2", "Due Date: " + 
						Assignment.getmonthString(AllAssignments.get(count).getmonth())
						+ " " + AllAssignments.get(count).getday()
						+ ", " + AllAssignments.get(count).getyear());
				list.add(item);
			}
		}
		de = new SimpleAdapter(this, list, 
				android.R.layout.two_line_list_item, 
				new String [] {"line1", "line2"}, 
				new int[] {android.R.id.text1, android.R.id.text2});
		assignments_list.setAdapter(de);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_assignments, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.calendar_action:
			//app icon in action bar clicked; go home
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		case R.id.planner_action:
			Intent planner_intent = new Intent(this, Instructors.class);
			startActivity(planner_intent);
			return true;
		case R.id.course_action:
			Intent course_intent = new Intent(this, Courses.class);
			startActivity(course_intent);
		case R.id.gradebook_action:
			Intent gradebook_intent = new Intent(this, Gradebooks.class);
			startActivity(gradebook_intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);		
		}
	}

}
