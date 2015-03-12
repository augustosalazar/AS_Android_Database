package com.example.androiddatabasetest;

/**
 * Created by Augusto on 3/11/2015.
 */
public class DataEntry2 {
    int _id;
    int _field3;


    public DataEntry2() {

    }

    public DataEntry2(int _id,int _field3) {
        this._id = _id;
        this._field3 = _field3;
    }

    public DataEntry2(int _field3) {
        this._field3 = _field3;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_field3() {
        return _field3;
    }

    public void set_field3(int _field3) {
        this._field3 = _field3;
    }


}

