package com.henrywarhurst.facerecog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FaceDetails extends Activity{
	private PeopleDataSource datasource;
	
	private static final String TAG = "FaceDetails";
	
	private static final String errorText = "Name fields are mandatory.";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_face_details);
		
		// Interface with the database
		datasource = new PeopleDataSource(this);
		datasource.open();
	}
	
	public void writeDetails(View view) {
		EditText firstname = (EditText) findViewById(R.id.firstname);
		EditText lastname = (EditText) findViewById(R.id.lastname);
		
		String fn_str = firstname.getText().toString();
		String ln_str = lastname.getText().toString();
		
		// Check if the user left out either of the fields
		if (fn_str.length() == 0 || ln_str.length() == 0) {
			Context context = getApplicationContext();
			int duration = Toast.LENGTH_SHORT;
			Toast errorMessage = Toast.makeText(context, errorText, duration);
			errorMessage.show();
		} else {
			// Add the new person to the database
			Person p = datasource.createPerson(fn_str, ln_str);
			Intent intent = new Intent(FaceDetails.this, SnapFace.class);
			intent.putExtra("firstname", p.getFirstname());
			intent.putExtra("lastname", p.getLastname());
			intent.putExtra("personid", Long.toString(p.getId()));
			startActivity(intent);
		}
	}
}
