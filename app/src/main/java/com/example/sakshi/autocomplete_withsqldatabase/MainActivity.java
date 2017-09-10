package com.example.sakshi.autocomplete_withsqldatabase;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DataHandler dataHandler;
    EditText editText;
    AutoCompleteTextView autoCompleteTextView;
    Button enter, save;
    ArrayList<String> mProductLit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataHandler = new DataHandler(MainActivity.this);
        editText = (EditText) findViewById(R.id.product);
        mProductLit = new ArrayList<>();
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto);
        enter = (Button) findViewById(R.id.enter);
        save= (Button) findViewById(R.id.save);
        //on click listener for save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inserting data in database
                Boolean status = dataHandler.insert_Data(editText.getText().toString());
                if(status){
                    Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                    //refreshing product list
                    refreshList();
                }else{
                    Toast.makeText(MainActivity.this, "can not insert data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshList();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mProductLit);
        //setting adapter
        autoCompleteTextView.setAdapter(arrayAdapter);
        //on click listener for enter button
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,autoCompleteTextView.getText()+" selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void refreshList(){
        Cursor cursor = dataHandler.showProduct();

        while (cursor.moveToNext()) {
            mProductLit.add(cursor.getString(cursor.getColumnIndex("product_name")));
        }
    }
}
