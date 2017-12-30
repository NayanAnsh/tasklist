package com.example.nayan.tasklist;

/**
 * Created by Nayan on 14-11-2017.
 */

public class KeyWordsdatabase  {


    public  class feedEntry {
        public static final String TableName =  "TargetList";
        public  static  final   String targetText = "target";
        public  static   final   String ID  =  "_id";
        public  static  final  String checkBox = "checking";
        public  static  final  String date  =  "date";

        public   static   final   String DatabaseRawCode = "CREATE TABLE "+ TableName + " ( "+ ID + " INTEGER PRIMARY KEY, "+ targetText + " TEXT , "+checkBox +" INTEGER )";

    }
    public  class feedEntryEXTRA {
        public  static  final   String TableNameExtra =  "TargetExtra";
        public  static  final  String date  =  "date";
        public  static  final  String idExtra = "_id";
        public  static final String historyData = "historyTarget";
        public  static  final  String databaseExtra = "CREATE TABLE " + TableNameExtra + "("+idExtra +" INTEGER PRIMARY KEY , "+ date + " TEXT , "+historyData+" TEXT )" ;
    }
    public class feedEntrySettings{
        public  static  final   String TableNameSettings =  "Setting";
        public  static  final  String Image  =  "image";
        public  static  final  String idSettings = "_id";

        public static final String Tableexe =
                "CREATE TABLE " + TableNameSettings + " (" +
                        idSettings + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + Image + " BLOB NOT NULL )";    }
    public   class feedEntrySchedule{
        public static  final  String TableNameSchedule = "scheduleTable";
        public static  final  String schedule  = "schedule";
        public  static final  String dateSchedule = "dateSchedule";
        public static  final  String idSchedule = "_id";
        public static  final  String TableexeSchedule =  "CREATE TABLE "+ TableNameSchedule + " ( "+idSchedule+"  INTEGER PRIMARY KEY AUTOINCREMENT,"
                + schedule +" TEXT , "+dateSchedule +" TEXT )";
    }
}
