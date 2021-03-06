package com.henrywarhurst.facerecog;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PeopleDataSource {
	private SQLiteDatabase database;
	private SqlHelper dbHelper;
	private String[] allColumns = {SqlHelper.COLUMN_ID,
			SqlHelper.COLUMN_FIRST_NAME, SqlHelper.COLUMN_LAST_NAME};
	
	public PeopleDataSource(Context context) {
		dbHelper = new SqlHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Person createPerson(String firstname, String lastname) {
		ContentValues values = new ContentValues();
		values.put(SqlHelper.COLUMN_FIRST_NAME, firstname);
		values.put(SqlHelper.COLUMN_LAST_NAME, lastname);
		long insertId = database.insert(SqlHelper.TABLE_PEOPLE, null, values);
		Cursor cursor = database.query(SqlHelper.TABLE_PEOPLE, allColumns,
				SqlHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Person newPerson = cursorToPerson(cursor);
		cursor.close();
		return newPerson;
	}
	
	public void deletePerson(Person person) {
		long id = person.getId();
		System.out.println("Person deleted with id: " + id);
		database.delete(SqlHelper.TABLE_PEOPLE, SqlHelper.COLUMN_ID + " = " + id, null);
	}
	
	public List<Person> getAllPeople() {
		List<Person> people = new ArrayList<Person>();
		
		Cursor cursor = database.query(SqlHelper.TABLE_PEOPLE, 
				allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Person person = cursorToPerson(cursor);
			people.add(person);
			cursor.moveToNext();
		}
		// Close the cursor
		cursor.close();
		return people;
	}
	
	private Person cursorToPerson(Cursor cursor) {
		Person person = new Person();
		person.setId(cursor.getLong(0));
		person.setFirstname(cursor.getString(1));
		person.setLastname(cursor.getString(2));
		return person;
	}
}
