package com.example.nayan.tasklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class scheduleAddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add_task);
        final EditText editText = (EditText)findViewById(R.id.sccheduleEditText);
        Button button =  findViewById(R.id.schedulDialogDone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent get =  getIntent();
                String date =    get.getStringExtra("mainScheduleText");
                Intent intent =  new Intent(scheduleAddTask.this,scheduleMain.class);
                String data =  editText.getText().toString();
                intent.putExtra("dateAdd",schedule_activity.dateSchedule);
                if(!(data.equals(""))){
                intent.putExtra("mainScheduleText",data);
                }else {
                    Toast.makeText(scheduleAddTask.this,"you can not set nothing.",Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
                finish();
            }
        });
    }
}
