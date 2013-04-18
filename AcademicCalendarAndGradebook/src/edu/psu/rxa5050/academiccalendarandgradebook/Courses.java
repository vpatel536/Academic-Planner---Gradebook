package edu.psu.rxa5050.academiccalendarandgradebook;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Courses extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_courses);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent i = getIntent();
		final boolean getcourse = i.getBooleanExtra("getcourse", false);
		DatabaseHandler db = new DatabaseHandler(this);
		final List<Course> courses = db.getAllCourses();
		String myCoursesArray [];
		if(courses != null){
			myCoursesArray = new String[courses.size()];
			for(int count = 0; count<courses.size(); count++){
				myCoursesArray[count] = courses.get(count).getname();
			}
		}
		else{ 
			myCoursesArray= new String[1];
			myCoursesArray[0] = "No courses added";
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myCoursesArray);
		ListView listView = (ListView) findViewById(R.id.course_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id){
				if(getcourse){
					Intent returnIntent = new Intent();
					returnIntent.putExtra("course", courses.get(position));
					setResult(1, returnIntent);
					finish();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.courses, menu);
		return true;
	}
	
	public void addCourse(View v){
		Intent i = new Intent(this, AddCourses.class);
		startActivity(i);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				//NavUtils.navigateUpFromSameTask(this);
				finish();
				return true;
			case R.id.calendar_action:
				//app icon in action bar clicked; go home
				Intent intent = new Intent(this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			case R.id.assignment_action:
				Intent assignment_intent = new Intent(this, Assignments.class);
				startActivity(assignment_intent);
				return true;
			case R.id.planner_action:
				Intent planner_intent = new Intent(this, Instructors.class);
				startActivity(planner_intent);
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
