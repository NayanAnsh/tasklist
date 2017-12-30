package com.example.nayan.tasklist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class mainList extends AppCompatActivity {
    int CursorCount;

    @Override
    protected void onStart() {

        super.onStart();
        checkAndUpdateHistory history =  new checkAndUpdateHistory();
        history.start();

    }

    public class  checkSchedule extends Thread {
        @Override
        public void run() {
            super.run();
            sqliteDatabaseSchedule sqliteDatabaseSchedule =  new sqliteDatabaseSchedule(mainList.this);
            SQLiteDatabase db =  sqliteDatabaseSchedule.getReadableDatabase();
            DateFormat DateFormat =  new SimpleDateFormat("dd/MM/yyyy");
            Date dateRaw =  new Date();
            String date  = DateFormat.format(dateRaw);
            String[] projection  =  {KeyWordsdatabase.feedEntrySchedule.dateSchedule,KeyWordsdatabase.feedEntrySchedule.schedule};
            String selection  =  KeyWordsdatabase.feedEntrySchedule.dateSchedule + "=?";
            String [] selectionArgs = {date};
            Cursor cursor =  db.query(KeyWordsdatabase.feedEntrySchedule.TableNameSchedule,projection,selection,selectionArgs,null,null,null);
            int scheduleColumn  =  cursor.getColumnIndex(KeyWordsdatabase.feedEntrySchedule.schedule);
            if(cursor.getCount() !=0 && cursor.moveToFirst()){

                do{
                    addTarget(cursor.getString(scheduleColumn),0);
                }while(cursor.moveToNext());
                SwipeMenuListView listview =  (SwipeMenuListView)findViewById(R.id.list_item);
                readData(listview);
        }
    }

}
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sqliteDataBase sqliteDataBase = new sqliteDataBase(this);
        checkSchedule checkSchedule =  new checkSchedule();
        checkSchedule.run();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mainList.this, getdata.class);
                startActivity(intent);
            finish();

            }
        });
        sqliteDatabaseSchedule schedule =  new sqliteDatabaseSchedule(this);
        SQLiteDatabase dBitch =  schedule.getReadableDatabase();
        Intent intent = getIntent();
        if (intent.getStringExtra("target") != null) {
            addTarget(intent.getStringExtra("target"), 0);
            //  addTarget(0);
        }

        final SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.list_item);
        readData(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                checkBox.setChecked(!checkBox.isChecked());
                updateCheck(i + 1, checkBox.isChecked());
            }
        });
        SwipeMenuCreator swipeMenuListView =  new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
            SwipeMenuItem delete =  new SwipeMenuItem(getApplicationContext());
            /////delete button

            delete.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                    0xCE)));
            delete.setIcon(R.drawable.ic_delete_black_24dp);
            delete.setWidth(90);
            delete.setTitleSize(20);
            delete.setTitleColor(Color.WHITE);
                menu.addMenuItem(delete);

            }
        };
        listView.setMenuCreator(swipeMenuListView);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(mainList.this,"deleteItem just clicked" +"yo!",Toast.LENGTH_SHORT).show();
                        deleteRow(String.valueOf( position+1));
                        readData(listView);
                        break;
                }
                return false;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                checkBox.setVisibility(View.GONE);
                listView.setClickable(false);
                final TextView textView = view.findViewById(R.id.text1Custome);
                textView.setVisibility(View.GONE);
                final RelativeLayout relativeLayout = view.findViewById(R.id.updateRlayout);
                View child = getLayoutInflater().inflate(R.layout.row_layout, null);
                relativeLayout.addView(child);
                final EditText editText = child.findViewById(R.id.updateField);
                relativeLayout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                editText.setText(textView.getText().toString());
                ImageButton updateButton = (ImageButton) findViewById(R.id.updateButton);
                final int j = i + 1;
                listView.setLongClickable(false);
                final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutMAIN);
                 linearLayout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);


                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        update(j, editText.getText().toString());
                        relativeLayout.removeAllViews();
                        linearLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                        listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                        textView.setVisibility(View.VISIBLE);
                        readData(listView);
                        listView.setLongClickable(true);
                        listView.setClickable(true);

                    }
                });
                //*************I was using long click for delete buttonj now  I am using swipe menu listview for that purpose.************
            //    Button deleteButton = (Button) view.findViewById(R.id.deleteButton);
//                deleteButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        deleteRow(String.valueOf(j));
//                        relativeLayout.removeAllViews();
//                        linearLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
//                        textView.setVisibility(View.VISIBLE);
//                        readData(listView);
//                        listView.setLongClickable(true);
//                        listView.setClickable(true);
//
//                    }
//                });
                return false;
            }
        });

        sqliteDatabseSettings sqliteDatabseSettings =  new sqliteDatabseSettings(this);
        SQLiteDatabase  db =  sqliteDatabseSettings.getReadableDatabase();
        String[] projection  =  {KeyWordsdatabase.feedEntrySettings.Image};
        Cursor cImage =  db.query(KeyWordsdatabase.feedEntrySettings.TableNameSettings,projection,null,null,null,null,null);
        ImageView imageView  =  (ImageView)findViewById(R.id.wallpaper);

        if(cImage.getCount() != 0){
        cImage.moveToLast();
        int columnBlob = cImage.getColumnIndex(KeyWordsdatabase.feedEntrySettings.Image);
        byte[] ImageByteCode =  cImage.getBlob(columnBlob);
        Bitmap bitmap  = BitmapFactory.decodeByteArray(ImageByteCode,0,ImageByteCode.length);
settings  settings =  new settings();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),bitmap);

        if (bitmap != null  ) {

            imageView.setBackgroundDrawable(bitmapDrawable);
        }else{
            imageView.setBackgroundResource(R.drawable.tasklist_default_background);
        }
            }else{
            imageView.setBackgroundResource(R.drawable.tasklist_default_background);

        }
checkAndUpdateHistory history =  new checkAndUpdateHistory();
        history.start();


    }
    public class checkAndUpdateHistory  extends  Thread {
        @Override
        public void run(){
            super.run();
            ListView listView = (ListView) findViewById(R.id.list_item);
            sqliteDataBase sqliteDataBase1 = new sqliteDataBase(mainList.this);
            SQLiteDatabase dbReadable = sqliteDataBase1.getReadableDatabase();
            String[] projection = {KeyWordsdatabase.feedEntryEXTRA.date};
            Cursor cursorReadableAll = dbReadable.query(KeyWordsdatabase.feedEntryEXTRA.TableNameExtra, projection, null, null, null, null, null);
            String selection = KeyWordsdatabase.feedEntryEXTRA.idExtra + "=?";
            String[] selectionArgs = {String.valueOf(cursorReadableAll.getCount())};

            Log.v("history ", "count - " + cursorReadableAll.getCount());
            Cursor cursorReadable1 = dbReadable.query(KeyWordsdatabase.feedEntryEXTRA.TableNameExtra, projection, selection, selectionArgs, null, null, null);
            int dateColumnCount = cursorReadable1.getColumnIndex(KeyWordsdatabase.feedEntryEXTRA.date);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dateRaw = new Date();
            String Date = dateFormat.format(dateRaw);
            SQLiteDatabase dbWritable = sqliteDataBase1.getWritableDatabase();
            if (cursorReadableAll.getCount() != 0) {
                Log.v("history ", "count - " + cursorReadable1.getCount());


                Log.v("history ", "count - " + cursorReadable1.getCount() + "column count - " + dateColumnCount);
                cursorReadableAll.moveToLast();
                if (!(cursorReadableAll.getString(dateColumnCount).equals(Date))) {
                    ContentValues values = new ContentValues();
                    ContentValues valuesUpdate = new ContentValues();
                    values.put(KeyWordsdatabase.feedEntryEXTRA.date, Date);
                    String[] projectionMAin = {KeyWordsdatabase.feedEntry.targetText, KeyWordsdatabase.feedEntry.ID, KeyWordsdatabase.feedEntry.checkBox};
                    Cursor c = dbReadable.query(KeyWordsdatabase.feedEntry.TableName, projectionMAin, null, null, null, null, null);
                    c.moveToFirst();
                    String historyToday = "";
                    int targetTextColmun = c.getColumnIndex(KeyWordsdatabase.feedEntry.targetText);
                    int columnCheckBox = c.getColumnIndex(KeyWordsdatabase.feedEntry.checkBox);
                    if (c.getCount() != 0) {

                        do {
                            historyToday = historyToday + "\n" + c.getString(targetTextColmun);
                            if (c.getInt(columnCheckBox) == 1) {
                                historyToday = historyToday + "[completed]";
                            } else if (c.getInt(columnCheckBox) == 0) {
                                historyToday = historyToday + "[not completed]";
                            }

                        } while (c.moveToNext());
                        Log.v("HISTORY ", " FOUND MAIN DATABASE ADDING STUFF TO HISTORY " + historyToday + " count - " + cursorReadableAll.getCount());
                        valuesUpdate.put(KeyWordsdatabase.feedEntryEXTRA.historyData, historyToday);
                        values.put(KeyWordsdatabase.feedEntryEXTRA.idExtra, (cursorReadableAll.getCount() + 1));
                    } else {

                        Log.v("HISTORY ", "NOTHING FOUND MAIN DATABASE ADDING MESSAGE SAYING NOTHING ADDED ");
                        valuesUpdate.put(KeyWordsdatabase.feedEntryEXTRA.historyData, "its look like you had a holiday");
                        values.put(KeyWordsdatabase.feedEntryEXTRA.idExtra, (cursorReadableAll.getCount() + 1));


                    }
                    String selectionUpdate = KeyWordsdatabase.feedEntryEXTRA.idExtra + "=?";
                    String[] selectionArgsUpdate = {String.valueOf(cursorReadableAll.getCount())};
                    dbWritable.update(KeyWordsdatabase.feedEntryEXTRA.TableNameExtra, valuesUpdate, selectionUpdate, selectionArgsUpdate);
                    dbWritable.insert(KeyWordsdatabase.feedEntryEXTRA.TableNameExtra, null, values);


                    deleteTable();
                    readData(listView);
                    c.close();
                }
                cursorReadable1.close();
                cursorReadableAll.close();
                Log.v("HISTORY ", "NOTHING NEEDED TO UPDATE IN HISTORY DATABASE");

            } else {

                //TODOne: if table is empty then add today date to database
                ContentValues valuess = new ContentValues();
                valuess.put(KeyWordsdatabase.feedEntryEXTRA.idExtra, (cursorReadableAll.getCount() + 1));
                valuess.put(KeyWordsdatabase.feedEntryEXTRA.date, Date);
                long status = dbWritable.insert(KeyWordsdatabase.feedEntryEXTRA.TableNameExtra, null, valuess);
                Log.v("HISTORY PREPARATION ", "nothing found in extra database so adding todays date - " + Date + " count - " + cursorReadable1.getCount() + "status - " + status);
                cursorReadable1.close();
                cursorReadableAll.close();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_history) {
            Intent intent = new Intent(mainList.this, history.class);
            startActivity(intent);
            return true;
        }
        if(id ==  R.id.action_setting){
            Intent  intent  =  new Intent(mainList.this,settings.class);
            startActivity(intent);
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addTarget(String target, int i) {


        Log.v("addTarget", "adding " + target);
        sqliteDataBase sqliteDataBase = new sqliteDataBase(this);
        SQLiteDatabase db = sqliteDataBase.getWritableDatabase();

        String[] projection = {KeyWordsdatabase.feedEntry.targetText, KeyWordsdatabase.feedEntry.ID, KeyWordsdatabase.feedEntry.checkBox};
        Cursor c = db.query(KeyWordsdatabase.feedEntry.TableName, projection, null, null, null, null, null);

        ContentValues values = new ContentValues();
        values.put(KeyWordsdatabase.feedEntry.targetText, target);
        values.put(KeyWordsdatabase.feedEntry.checkBox, i);

        int nextCusrorNum = c.getCount() + 1;
        values.put(KeyWordsdatabase.feedEntry.ID, nextCusrorNum);
        db.insert(KeyWordsdatabase.feedEntry.TableName, null, values);

        Log.v("addTarget", "added " + target);


    }

    //// TODOno: 19-11-2017 find a way to update checkbox.  [DONE]
    public void readData(ListView listView) {
        Log.v("readData", "reading data ");
        sqliteDataBase sqliteDataBase = new sqliteDataBase(this);
        SQLiteDatabase db = sqliteDataBase.getReadableDatabase();
        String[] projection = {KeyWordsdatabase.feedEntry.targetText, KeyWordsdatabase.feedEntry.ID, KeyWordsdatabase.feedEntry.checkBox};

        Cursor c = db.query(KeyWordsdatabase.feedEntry.TableName, projection, null, null, null, null, null);
        CursorCount = c.getCount();
        // int ColumnID =  c.getColumnIndex(KeyWordsdatabase.feedEntry.ID);

        //int ColumnText  =  c.getColumnIndex(KeyWordsdatabase.feedEntry.targetText);

        if (c.moveToFirst()) {
            CursorAdapterClass cursorAdapter = new CursorAdapterClass(this, c);
            listView.setAdapter(cursorAdapter);
        }
        if (c.getCount() == 0) {
            CursorAdapterClass cursorAdapter = new CursorAdapterClass(this, c);
            listView.setAdapter(cursorAdapter);
        }

    }

    public void update(int id, String text) {
        sqliteDataBase sqliteDataBase = new sqliteDataBase(this);
        SQLiteDatabase db = sqliteDataBase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KeyWordsdatabase.feedEntry.targetText, text);
        String whereClause = KeyWordsdatabase.feedEntry.ID + "=?";
        String[] wherArgs = {String.valueOf(id)};
        db.update(KeyWordsdatabase.feedEntry.TableName, values, whereClause, wherArgs);

    }

    public void updateCheck(int id, boolean b) {
        sqliteDataBase sqliteDataBase = new sqliteDataBase(this);
        SQLiteDatabase db = sqliteDataBase.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (b) {

            Log.v("updateCheck", "1 is added to checkbox(true)");
            values.put(KeyWordsdatabase.feedEntry.checkBox, 1);

        } else {

            Log.v("updateCheck", "0 is added to checkbox(false)");

            values.put(KeyWordsdatabase.feedEntry.checkBox, 0);

        }
        String whereClause = KeyWordsdatabase.feedEntry.ID + "=?";
        String[] wherArgs = {String.valueOf(id)};
        db.update(KeyWordsdatabase.feedEntry.TableName, values, whereClause, wherArgs);
    }

    public void deleteRow(String id) {
        sqliteDataBase sqliteDataBase = new sqliteDataBase(this);
        SQLiteDatabase db = sqliteDataBase.getWritableDatabase();
        String whereClaus = KeyWordsdatabase.feedEntry.ID + " =? ";
        String[] whereArgs = {id};
        db.delete(KeyWordsdatabase.feedEntry.TableName, whereClaus, whereArgs);
        updateID(Integer.parseInt(id));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void deleteTable() {
        Log.v("DELETE ALL TABLE (MAIN)", "deleting all data of table(main)");
        sqliteDataBase sqliteDataBase = new sqliteDataBase(this);
        SQLiteDatabase db = sqliteDataBase.getWritableDatabase();

        int j = db.delete(KeyWordsdatabase.feedEntry.TableName, null, null);
        Log.v("DELETE ALL TABLE (MAIN)", "deleting all data of table(main)(done code - " + j + ")");


    }

    public void updateID(int j) {
        sqliteDataBase sqliteDataBase = new sqliteDataBase(this);
        SQLiteDatabase db = sqliteDataBase.getWritableDatabase();


        String[] projection = {KeyWordsdatabase.feedEntry.targetText, KeyWordsdatabase.feedEntry.ID, KeyWordsdatabase.feedEntry.checkBox};
        Cursor c = db.query(KeyWordsdatabase.feedEntry.TableName, projection, null, null, null, null, null);
        int m = j;
        ContentValues values = new ContentValues();
        Log.v("updzateID ", "updating - " + m);
        String whereClaus = KeyWordsdatabase.feedEntry.ID + " =? ";
        int o = (c.getCount() + 1);
        for (int i = j + 1; i <= o; i++) {
            Log.v("update", "count - " + c.getCount());
            Log.v("updzateID ", "updating - " + i + "to" + m);
            values = new ContentValues();


            String[] whereArgs = {String.valueOf(i)};

            values.put(KeyWordsdatabase.feedEntry.ID, m);
            db.update(KeyWordsdatabase.feedEntry.TableName, values, whereClaus, whereArgs);
            m++;
        }

    }
   ItemTouchHelper.SimpleCallback itemTouchHelper =  new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){

       @Override
       public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
        target) {
           return false;
       }

       @Override
       public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

       }
   };






}
