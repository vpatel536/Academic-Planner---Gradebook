package edu.psu.rxa5050.academiccalendarandgradebook;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class TodaysEvents extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todays_events);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_todays_events, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.calendar_action:
				//app icon in action bar clicked; go home
				Intent intent = new Intent(this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			case R.id.today_action:
				Intent today_intent = new Intent(this, TodaysEvents.class);
				startActivity(today_intent);
				return true;
			case R.id.assignment_action:
				Intent assignment_intent = new Intent(this, Assignments.class);
				startActivity(assignment_intent);
				return true;
			case R.id.planner_action:
				Intent planner_intent = new Intent(this, Instructors.class);
				startActivity(planner_intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);		
		}
	}

}
