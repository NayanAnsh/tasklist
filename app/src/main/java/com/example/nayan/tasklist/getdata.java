package com.example.nayan.tasklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class getdata extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getdata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText =  (EditText)findViewById(R.id.taskEdit);

                Intent intent =  new Intent(getdata.this,mainList.class);
                String data  =  editText.getText().toString();
                if(data.equals("")){
                    Toast.makeText(getApplicationContext(),"are you sure? you want nothing to be added",Toast.LENGTH_SHORT).show();
                }else {


                    intent.putExtra("target", data);
                }

                startActivity(intent);
                finish();
            }
        });
    }

}
