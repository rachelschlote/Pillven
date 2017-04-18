package com.baseStationPackage;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
// TODO: 4/9/2017  make either specific table or specific col for where or table name to get data
public class SchedulingDAO extends SQLiteJDBC{


    Vector getNamesRaw() {
        Vector sched;
        sched = getRowData("MED1,MED2,MED3,MED4,MED5,MED6","med_sched",null,null);
        return sched;
   }
   Vector getMedTimesRaw(){
        Vector medTimes = getRowData("TIME1,TIME2,TIME3,TIME4,TIME5,TIME6","med_sched",null,null);
        return medTimes;
   }
   void setMedSchedChange(int patNumVal,int index, String newSched) {
       String sql = buildUpdateSQL("med_sched","PAT_NUM ",patNumVal,"TIME"+index+" = "+newSched);
       try {
           Connection conn = connect();
           Statement stmt = conn.createStatement();
           stmt.executeUpdate(sql);
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }

   }


}
