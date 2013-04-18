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


public class Instructors extends Activity {
	
	private static final int RESULT_OK =1;
	private static final int RESULT_CANCEL = 0;
	boolean getInstructorForAssignment;
	boolean getInstructorForCourse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructors);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent i = getIntent();
		getInstructorForAssignment = i.getBooleanExtra("instructorresult", false);
		getInstructorForCourse = i.getBooleanExtra("course", false);
		DatabaseHandler db = new DatabaseHandler(this);
		final List <Instructor> instructors = db.getAllInstructors();
		String myInstructorsArray [];
		if(db.getAllInstructors() != null){
			myInstructorsArray = new String[instructors.size()];
			for(int count = 0; count<instructors.size(); count++){
				myInstructorsArray[count] = instructors.get(count).getname();
			}
		}
		else{ 
			myInstructorsArray= new String[1];
			myInstructorsArray[0] = "No instructors added";
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myInstructorsArray);
		ListView listView = (ListView) findViewById(R.id.instructor_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id){
				if(getInstructorForAssignment){
					Intent i = new Intent();
					i.putExtra("instructor", instructors.get(position));
					setResult(RESULT_OK, i);
					finish();
				}
				else if(getInstructorForCourse){
					Intent i = new Intent();
					i.putExtra("instructor", instructors.get(position));
					setResult(RESULT_OK, i);
					finish();
					
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_instructors, menu);
		return true;
	}
	
	public void add_instructor(View view) {
		Intent instructor = new Intent(this, AddInstructor.class);
		startActivity(instructor);		
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
				//NavUtils.navigateUpFromSameTask(this);
				Intent i = new Intent();
				setResult(RESULT_CANCEL, i);
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
