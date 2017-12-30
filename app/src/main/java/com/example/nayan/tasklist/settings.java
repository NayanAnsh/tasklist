package com.example.nayan.tasklist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class settings extends AppCompatActivity {
int RESULT_LOAD_IMAGE = 100 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button buttonBackground  =  (Button)findViewById(R.id.backgroundAddButton);
        buttonBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);


            }
        });
        Button buttonSchedule = (Button)findViewById(R.id.buttonS);
        buttonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent =  new Intent(settings.this,schedule_activity.class);
            startActivity(
                    intent
            );
            }
        });

    }
    Uri selectedImage =  null ;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==  RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null ){
            Log.v("settings","got code set proceeidnging to extract image");
            selectedImage =  data.getData();
            if(selectedImage !=  null) {
                try {
                    //we can not directly convert URI into byte hence we will first convert it into Stream reader
                    InputStream iStream = getContentResolver().openInputStream(selectedImage);
                    //we can convert stream reader into byte code  .
                    byte[] map = getBytes(iStream);
                    sqliteDatabseSettings sqliteDatabseSettings = new sqliteDatabseSettings(this);

                    ContentValues values = new ContentValues();
                    SQLiteDatabase dbRead  =  sqliteDatabseSettings.getReadableDatabase();
                    String[] projection  = {KeyWordsdatabase.feedEntrySettings.Image};
                    Cursor  c  =  dbRead.query(KeyWordsdatabase.feedEntrySettings.TableNameSettings,projection,null,null,null,null,null);

                    values.put(KeyWordsdatabase.feedEntrySettings.Image, map);
                    values.put(KeyWordsdatabase.feedEntrySettings.idSettings,c.getCount()+1);

                    SQLiteDatabase db = sqliteDatabseSettings.getWritableDatabase();
                    db.insert(KeyWordsdatabase.feedEntrySettings.TableNameSettings, null, values);
                    Toast.makeText(this, "inserted image " + values , Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(settings.this, mainList.class);
                intent.putExtra("ImageUri", selectedImage);

                startActivity(intent);
                // ImageView imageView  =  (ImageView)findViewById(R.id.wallpaper);

                // imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                // imageView.setImageURI(selectedImage);
                Log.v("settings", "got code set proceeidnging to extract image");
            }else{
            Toast.makeText(this,"Oops!no image selected  "
            ,Toast.LENGTH_SHORT).show();
            }
        }else{

            Log.v("settings","oops some data might me not matched check them out - "+" request code - "+ requestCode + " result code -  "+resultCode+
            "data - "+data);

        }

    }
    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
