package com.example.androiddatabasetest;

public class DataEntry {

	int _id;
	int _field1;
	int _field2;

	public DataEntry() {

	}

	public DataEntry(int _id,int _field1, int _field2) {
		this._id = _id;
		this._field1 = _field1;
		this._field2 = _field2;	
		}
	
	public DataEntry(int _field1, int _field2) {
		this._field1 = _field1;
		this._field2 = _field2;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_field1() {
		return _field1;
	}

	public void set_field1(int _field1) {
		this._field1 = _field1;
	}

	public int get_field2() {
		return _field2;
	}

	public void set_field2(int _field2) {
		this._field2 = _field2;
	}
	
}
