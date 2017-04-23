package com.baseStationPackage;


import java.sql.*;
import java.util.Vector;

public class ViewPatientDataDAO extends SQLiteJDBC{


    public static Vector getColNamesFromDatabase(String select, String from) {
        String sql = buildSelectSQL(select, from);
        Vector<String> dropDownNames = new Vector<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData cols = rs.getMetaData();
            for(int i = 1; i <= cols.getColumnCount(); i++) {
                dropDownNames.add(cols.getColumnName(i));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dropDownNames;
    }


    public static Vector<Vector> getRowDataFromDatabase(String select, String from, String where, String is) {
        Vector row = getRowData(select,from,where,is);
        return row;
    }

    public static void setRowDataToDatabase(Vector data) {
        Vector colNames = getColNamesFromDatabase("*", "patient_data");
        String setValue = "";
        String setColumn = "";
        String whereColumn = "";
        String whereValue = "";
        String moreSet = "";
        whereValue = data.get(0).toString();//get patient num value @todo create key column
        whereColumn = colNames.get(0).toString();//gets num
        for(int i = 1; i<data.size(); i++) {//for loop skips 0 because its the name
            setColumn = colNames.get(i).toString();
            setValue = data.get(i).toString();
            moreSet = moreSet + moreSetSQL(setColumn,setValue);
            if(i!=(data.size()-1)) {
                moreSet = moreSet + ", ";
            }
        }
        String sql = buildUpdateSQL("patient_data",whereColumn,whereValue,moreSet);
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
