package com.henrywarhurst.facerecog;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FacesLibrary extends ListActivity {
	private PeopleDataSource datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_faces_library);

		datasource = new PeopleDataSource(this);
		datasource.open();

		// create people list
		List<Person> data = datasource.getAllPeople();
		setListAdapter(new LibraryArrayAdapter(this, data));
	}

	// Put app in 'run' mode.
	public void runFd(View view) {
		Intent intent = new Intent(this, FdActivity.class);
		startActivity(intent);
	}

	// Add a new face.
	public void addFace(View view) {
		Intent intent = new Intent(this, FaceDetails.class);
		startActivity(intent);
	}
}