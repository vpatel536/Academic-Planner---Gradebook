package edu.psu.rxa5050.academiccalendarandgradebook;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AssignmentDetailActivity extends Activity {
	private static final int RESULT_OK = 1;
	private static final int RESULT_CANCEL = 0;
	
	TextView assignmentTitle;
	TextView assignmentDetails;
	Assignment assignment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assignment_detail);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		assignmentTitle = (TextView) findViewById(R.id.assignmentDetailTitle);
		assignmentDetails = (TextView) findViewById(R.id.assignmentDetailInfo);
		Intent i = getIntent();
		assignment = (Assignment)i.getSerializableExtra("assignment");
		assignmentTitle.setText(assignment.gettitle());
		String aDetails = new String();
		aDetails = "Type: " + assignment.gettype() + "\n";
		aDetails += "Due Date: " + Assignment.getmonthString(assignment.getmonth());
		aDetails += " " + assignment.getday();
		aDetails += ", " + assignment.getyear() + "\n";
		aDetails += "Priority: " + assignment.getpriorityString(assignment.getpriority()) + "\n";
		if(assignment.getCourse() != null)
			aDetails += "Course: " + assignment.getCourse().getname() + "\n";
		else
			aDetails += "Course: " + "\n";
		aDetails += "Partners:\n";
		if(assignment.getpartners() != null){
			for(int count = 0; count < assignment.getpartners().size(); count ++){
				aDetails += "\t" + assignment.getpartners().get(count).getname();
				aDetails += "\t\t" + assignment.getpartners().get(count).getemail() + "\n";
			}
		}
		aDetails += "Notes: " + assignment.getnotes() + "\n";
		if(assignment.getcompleted() == 0)
			aDetails += "Completed: No";
		else
			aDetails += "Completed: Yes \t Grade: " + assignment.getgrade().getgrade();
		assignmentDetails.setText(aDetails);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assignment_detail, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			case R.id.edit_assignment:
				Intent intent = new Intent(this, AddAssignment.class);
				intent.putExtra("assignment", assignment);
				startActivityForResult(intent,1);
				return true;
			default:
				return super.onOptionsItemSelected(item);		

		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == 1){
			if(resultCode == RESULT_OK){
			}
			if(resultCode == RESULT_CANCEL){
				
			}
		}
	}

}
