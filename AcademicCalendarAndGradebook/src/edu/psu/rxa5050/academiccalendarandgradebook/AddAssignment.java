package edu.psu.rxa5050.academiccalendarandgradebook;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class AddAssignment extends Activity implements OnItemSelectedListener {
	
	private static final int RESULT_OK = 1;
	private static final int RESULT_CANCEL = 0;
	
	Assignment assignment;
	EditText name_of_assignment;
	Spinner type_of_assignment;
	TextView due_date;
	DatePickerDialog dialog;
	Spinner priority;
	Button course;
	Button instructor;
	Button add_friends;
	List<EditText> friend_names;
	List<EditText> friend_emails;
	EditText notes;
	CheckBox completed;
	EditText grade;
	int month;
	int day;
	int year;
	boolean edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_assignment);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(false);
		assignment = new Assignment();
		Intent intent = getIntent();
		month = intent.getIntExtra("month", 0);
		day = intent.getIntExtra("day", 0);
		year = intent.getIntExtra("year", 0);
		edit = false;
		
		name_of_assignment = (EditText) findViewById(R.id.name_of_assignment);
		type_of_assignment = (Spinner) findViewById(R.id.type_of_assignment);
		due_date = (TextView) findViewById(R.id.due_date);
		priority = (Spinner) findViewById(R.id.priority_spinner);
		course = (Button) findViewById(R.id.course_button);
		add_friends = (Button) findViewById(R.id.add_partner_button);
		friend_names = new ArrayList<EditText>();
		friend_emails = new ArrayList<EditText>();
		notes = (EditText) findViewById(R.id.notes);
		completed = (CheckBox) findViewById(R.id.completed);
		grade = (EditText) findViewById(R.id.grade);
		
		
		priority.setOnItemSelectedListener(this);
		type_of_assignment.setOnItemSelectedListener(this);
		
		if(intent.getSerializableExtra("assignment") != null){
			edit = true;
			assignment = (Assignment) intent.getSerializableExtra("assignment");
			month = assignment.getmonth();
			day = assignment.getday();
			year = assignment.getyear();
			editAssignment();
		}
		createSpinners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_assignment, menu);
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
				if(edit)
					updateAssignment();
				else{					
					assignment.settitle(name_of_assignment.getText().toString());
					assignment.setmonth(month);
					assignment.setday(day);
					assignment.setyear(year);
					if(friend_names.size() > 0 && 
							friend_names.size() == friend_emails.size()){
						for(int count = 0; count < friend_names.size(); count++){
							assignment.setPartner(friend_names.get(count).getText().toString(),
									friend_emails.get(count).getText().toString());
						}
					}
					assignment.setnotes(notes.getText().toString());
					if(assignment.getcompleted() == 1){
						Grade g = new Grade();
						g.settitle(assignment.gettitle());
						g.settype(assignment.gettype());
						g.setgrade(grade.getText().toString());						
						assignment.setGrade(g);
					}
					DatabaseHandler db = new DatabaseHandler(this);
					Log.d("assignment= " , assignment.toString());
					db.addAssignment(assignment);
					Intent intent = new Intent(this, MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					Toast toast = Toast.makeText(getApplicationContext(), "Assignment added successfully", Toast.LENGTH_SHORT);
					toast.show();
				}
				return true;
			case R.id.cancel_assignment:
				finish();
				//NavUtils.navigateUpFromSameTask(this);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	public void createSpinners(){
		ArrayAdapter<CharSequence> priority_adapter = ArrayAdapter.createFromResource
				(this, R.array.priority_list, android.R.layout.simple_spinner_item);
		if(edit){
			//priority_adapter.insert(assignment.getpriorityString(assignment.getpriority()), 0);
		}
		ArrayAdapter<CharSequence> type_adapter = ArrayAdapter.createFromResource
				(this, R.array.type_list, android.R.layout.simple_spinner_item);
		if(edit){
			Log.d("assignment type", assignment.gettype());
			//priority_adapter.insert(assignment.gettype(), 0);
		}		
		priority_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		priority.setAdapter(priority_adapter);
		type_of_assignment.setAdapter(type_adapter);
		
	}
	public void onItemSelected(AdapterView<?> parent, View view, 
			int pos, long id){
		Spinner spinner = (Spinner) parent;
		if(spinner.getId() == R.id.type_of_assignment)
			assignment.settype(parent.getItemAtPosition(pos).toString());
		else
			assignment.setpriority(parent.getItemAtPosition(pos).toString());
		
	}
	public void onNothingSelected(AdapterView<?> parent){

	}
	
	
	public void showDatePickerDialog(View view){
		dialog = new DatePickerDialog(view.getContext(), new PickDate(), year, month, day);
		dialog.show();
	}
	private class PickDate implements DatePickerDialog.OnDateSetListener {
				
				@Override
				public void onDateSet(DatePicker view, int y, int m,
						int d) {
					// TODO Auto-generated method stub
					year = y;
					month = m;
					day = d;
					due_date.setText(Assignment.getmonthString(m) + " " 
							+ day + ", " + year);
					dialog.hide();
				}
	}
	
	public void selectCourse(View v){
		Intent i = new Intent(this, Courses.class);
		i.putExtra("getcourse", true);
		startActivityForResult(i,2);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == 2){
			if(resultCode == RESULT_OK){
				assignment.setCourse((Course) data.getSerializableExtra("course"));
				course.setText(assignment.getCourse().getname());
			}
			if(resultCode == RESULT_CANCEL){
				
			}
		}
	}
	
	public void addPartner(View view){
		EditText partnerNameButton = new EditText(this);
		partnerNameButton.setHint("name");
		EditText partnerEmailButton = new EditText(this);
		partnerEmailButton.setHint("email");
		
		LinearLayout ll =(LinearLayout)findViewById(R.id.partner);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT);
		ll.addView(partnerNameButton, lp);
		ll.addView(partnerEmailButton, lp);
		
		friend_names.add(partnerNameButton);
		friend_emails.add(partnerEmailButton);
	}
	public void editPartner(String name, String email){
		EditText partnerNameButton = new EditText(this);
		partnerNameButton.setText(name);
		EditText partnerEmailButton = new EditText(this);
		partnerEmailButton.setText(email);
		
		LinearLayout ll =(LinearLayout)findViewById(R.id.partner);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT);
		ll.addView(partnerNameButton, lp);
		ll.addView(partnerEmailButton, lp);
		
		friend_names.add(partnerNameButton);
		friend_emails.add(partnerEmailButton);
	}

	public void isAssignmentCompleted(View view){
		boolean checked = ((CheckBox) view).isChecked();
		if(checked){
			assignment.setCompleted(1);
			grade.setVisibility(View.VISIBLE);
		}
		else{
			assignment.setCompleted(0);
			grade.setVisibility(View.INVISIBLE);
		}
	}
	public void editAssignment(){
		name_of_assignment.setText(assignment.gettitle());
		type_of_assignment.setSelection(getType(assignment.gettype()));
		due_date.setText(Assignment.getmonthString(assignment.getmonth()) + " " + assignment.getday() + ", " + assignment.getyear());
		priority.setSelection(assignment.getpriority());
		if(assignment.getCourse() != null)
			course.setText(assignment.getCourse().getname());
		if(assignment.getpartners() != null){
			for(int count=0; count<assignment.getpartners().size(); count++){
				editPartner(assignment.getpartners().get(count).getname(), 
						assignment.getpartners().get(count).getemail());
			}
		}
		notes.setText(assignment.getnotes());
		if(assignment.getcompleted() == 1){
			completed.setChecked(true);
			grade.setVisibility(View.VISIBLE);
			grade.setText((CharSequence) assignment.getgrade().getgrade());
		}
	}
	public void updateAssignment(){
		DatabaseHandler db = new DatabaseHandler(this);
		assignment.settitle(name_of_assignment.getText().toString());
		assignment.setmonth(month);
		assignment.setday(day);
		assignment.setyear(year);
		if(friend_names.size() > 0 && 
				friend_names.size() == friend_emails.size()){
			for(int count = 0; count < friend_names.size(); count++){
				if(count < assignment.getpartners().size()){
					assignment.getpartners().get(count).setname(friend_names.get(count).getText().toString());
					assignment.getpartners().get(count).setemail(friend_emails.get(count).getText().toString());
				}
				else{
					Partner p = new Partner();
					p.setaid(assignment.getaid());
					p.setname(friend_names.get(count).getText().toString());
					p.setemail(friend_emails.get(count).getText().toString());
					db.addPartner(p);
				}
				Log.d("Friend updated name: ", friend_names.get(count).getText().toString());
			}
		}
		assignment.setnotes(notes.getText().toString());
		if(assignment.getcompleted() == 1){
			Grade g = new Grade();
			g.setaid(assignment.getaid());
			g.settitle(assignment.gettitle());
			g.settype(assignment.gettype());
			g.setgrade(grade.getText().toString());						
			assignment.setGrade(g);
		}
		db.updateAssignment(assignment);
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	public int getType(String name){
        if(name=="Homework")
        	return 0;
        else if(name=="Test")
        	return 1;
        else if(name=="Study")
        	return 2;
        else if(name=="Read")
        	return 3;
        else if(name=="Paper")
        	return 4;
        else if(name=="Presentation")
        	return 5;
        else if(name=="Lab")
        	return 6;
        else if(name=="Final")
        	return 7;
        else if(name=="Midterm")
        	return 8;
        else if(name=="Quiz")
        	return 9;
        else if(name=="Project")
        	return 10;
        else if(name=="Workbook")
        	return 11;
        else if(name=="Lesson")
        	return 12;
        else if(name=="Other")
        	return 13;
        else
        	return 0;
	}
}
