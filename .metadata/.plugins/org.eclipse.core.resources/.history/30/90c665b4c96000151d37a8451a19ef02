package com.henrywarhurst.facerecog;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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