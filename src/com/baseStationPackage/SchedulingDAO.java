package com.baseStationPackage;


import java.util.Vector;
// TODO: 4/9/2017  make either specific table or specific col for where or table name to get data
public class SchedulingDAO extends SQLiteJDBC{


    public Vector getNamesRaw() {
        Vector sched;
        sched = getRowData("MED1,MED2,MED3,MED4,MED5,MED6","med_sched",null,null);
        return sched;
   }
   public Vector getMedTimesRaw(){
        Vector medTimes = getRowData("TIME1,TIME2,TIME3,TIME4,TIME5,TIME6","med_sched",null,null);
        return medTimes;
   }


    public static void main(String[] args)
    {
        new SchedulingDAO();
    }
}
