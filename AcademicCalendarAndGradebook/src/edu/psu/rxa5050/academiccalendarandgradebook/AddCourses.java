package edu.psu.rxa5050.academiccalendarandgradebook;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class AddCourses extends Activity {
	private static final int RESULT_OK = 1;
	private static final int RESULT_CANCEL = 0;

	EditText courseName;
	Button courseSDate;
	Button courseEDate;
	EditText courseBuilding;
	EditText courseRoom;
	Button courseInstructor;
	EditText courseTime;
	EditText courseNotes;
	Spinner courseGradeType;
	Course course;
	List<Spinner> gradebook_params = new ArrayList<Spinner>();
	List<EditText> gradebook_percentage = new ArrayList<EditText>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_courses);
		
		course = new Course();
		courseName = (EditText) findViewById(R.id.coursename);
		courseSDate = (Button) findViewById(R.id.course_sdate);
		courseEDate = (Button) findViewById(R.id.course_edate);
		courseBuilding = (EditText) findViewById(R.id.course_building);
		courseRoom = (EditText) findViewById(R.id.course_room);
		courseInstructor = (Button) findViewById(R.id.course_instructor);
		courseTime= (EditText) findViewById(R.id.course_time);
		courseNotes = (EditText) findViewById(R.id.course_notes);
		courseGradeType = (Spinner) findViewById(R.id.grade_type);
		
		ArrayAdapter<CharSequence> gradeTypeAdapter = ArrayAdapter.createFromResource(this, R.array.gradeType_list, android.R.layout.simple_spinner_item);
		gradeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		courseGradeType.setAdapter(gradeTypeAdapter);		
		courseGradeType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				course.getgrades().setgrade_type(parent.getItemAtPosition(pos).toString());
				Log.d("grade_type", course.getgrades().getgrade_type());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_courses, menu);
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
			case R.id.save_assignment:
				course.setname(courseName.getText().toString());
				course.setsdate(courseSDate.getText().toString());
				course.setedate(courseEDate.getText().toString());
				course.setbuilding(courseBuilding.getText().toString());
				course.setroom(courseRoom.getText().toString());
				course.setclass_time(courseTime.getText().toString());
				course.setnotes(courseNotes.getText().toString());
				for(int count = 0; count < gradebook_params.size(); count++){
					course.getgrades().setparameter(gradebook_params.get(count).getSelectedItem().toString(), Integer.parseInt(gradebook_percentage.get(count).getText().toString()));
					Log.d("gradebook_params " + Integer.toString(count) + ":", gradebook_params.get(count).getSelectedItem().toString());
					Log.d("gradebook_percentage" + Integer.toString(count) + ":", gradebook_percentage.get(count).getText().toString());
				}
				DatabaseHandler db = new DatabaseHandler(this);
				db.addCourse(course);
				Log.d("course:", course.toString());
				Intent intent = new Intent(this, Courses.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			case R.id.cancel_assignment:
				finish();
				//NavUtils.navigateUpFromSameTask(this);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	
	public void setSDate(View view){
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		String date = month + "/" + day + "/" + year;
		Log.d("Date", date);
		DatePickerDialog dialog = new DatePickerDialog(view.getContext(), new PickSDate(), year, month, day);
		dialog.show();

	}
	public void setEDate(View view){
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		String date = month + "/" + day + "/" + year;
		Log.d("Date", date);
		DatePickerDialog dialog = new DatePickerDialog(view.getContext(), new PickEDate(), year, month, day);
		dialog.show();

	}
	private class PickSDate implements DatePickerDialog.OnDateSetListener {
		
		@Override
		public void onDateSet(DatePicker view, int y,
				int m,  int d) {
			// TODO Auto-generated method stub
					int smonth = m + 1;
					String sdate = y + "/" + smonth + "/" + d; 
					courseSDate.setText("From: " + sdate);
		}
	}
	private class PickEDate implements DatePickerDialog.OnDateSetListener {
		
		@Override
		public void onDateSet(DatePicker view, int y,
				int m, int d) {
			// TODO Auto-generated method stub
					int emonth = m + 1;
					String edate = y + "/" + emonth + "/" + d; 
					courseEDate.setText("To: " + edate);
		}
	}

	public void selectInstructor(View view){
		Intent i = new Intent(this, Instructors.class);
		i.putExtra("course", true);
		startActivityForResult(i,1);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == 1){
			if(resultCode == RESULT_OK){
				course.setinstructor((Instructor) data.getSerializableExtra("instructor"));
				courseInstructor.setText(course.getinstructor().getname());
			}
			if(resultCode == RESULT_CANCEL){
				
			}
		}
	}
	public void addParameters(View view){
		LinearLayout ll = (LinearLayout) findViewById(R.id.gradebook_layout);
		LinearLayout hl = new LinearLayout(this);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 2f);
		LinearLayout.LayoutParams ep = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 3f);
		Spinner s = new Spinner(this);
		EditText e = new EditText(this);
		e.setText("0");
		e.setInputType(InputType.TYPE_CLASS_NUMBER);
		Log.d("parameters size", Integer.toString(course.getgrades().getparameters().size()));
		s.setId(gradebook_params.size()+1);
		hl.setLayoutParams(lp);
		hl.setOrientation(LinearLayout.HORIZONTAL);
		hl.addView(s, sp);
		hl.addView(e, ep);
		ll.addView(hl,lp);
		//gradebook_params.add(s);
		course.getgrades().setparameter(new Parameter());
		ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.type_list, android.R.layout.simple_spinner_item);
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(typeAdapter);
		gradebook_params.add(s);
		gradebook_percentage.add(e);
	}
}
