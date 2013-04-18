package edu.psu.rxa5050.academiccalendarandgradebook;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class AddInstructor extends Activity {
	EditText name;
	EditText department;
	Button course;
	EditText email;
	EditText webpage;
	EditText phone;
	EditText officeHours;
	EditText notes;
	Instructor instructor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_instructor);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(false);
		
		name = (EditText) findViewById(R.id.instructor_name);
		department = (EditText) findViewById(R.id.instructor_dept);
		email = (EditText) findViewById(R.id.instructor_email);
		webpage = (EditText) findViewById(R.id.instructor_webpage);
		phone = (EditText) findViewById(R.id.instructor_phone);
		officeHours = (EditText) findViewById(R.id.instructor_office_hours);
		notes = (EditText) findViewById(R.id.instructor_notes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_instructor, menu);
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
			case R.id.cancel_instructor:
				return true;
			case R.id.save_instructor:
				Instructor instructor = new Instructor();
				DatabaseHandler db = new DatabaseHandler(this);
				instructor.setname(name.getText().toString());
				instructor.setdepartment(department.getText().toString());
				instructor.setemail(email.getText().toString());
				instructor.setwebpage(webpage.getText().toString());
				instructor.setphone(phone.getText().toString());
				instructor.setoffice_hours(officeHours.getText().toString());
				instructor.setnotes(notes.getText().toString());
				db.addInstructor(instructor);
				Intent intent = new Intent(this, Instructors.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
