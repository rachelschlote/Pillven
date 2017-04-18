package com.baseStationPackage;

import java.sql.*;
import java.util.Properties;
import java.util.Vector;


public class SQLiteJDBC{

    public static Connection connect() {
        Connection conn = null;
        try {
            Properties prop = new Properties();
            prop.setProperty("user","root");
            prop.setProperty("password","password");
            prop.setProperty("useSSL", "false");
            prop.setProperty("autoReconnect", "true");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pillvendatabase",prop);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public static Object select(String select, String from, String where, String is) {

        String sql = buildSelectSQL(select, from, where, is);
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            rs.next();
            return rs.getObject(select);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String buildSelectSQL(String select, String from, String where, String is) {
        return "SELECT " + select + " FROM " + from + " WHERE " + where + "=" + "'" + is + "'";
    }

    public static String buildSelectSQL(String select, String from) {
        return "SELECT " + select + " FROM " + from;
    }

    public static String buildUpdateSQL(String table, String whereColumn, Object whereValue, String moreSet) {
        return "UPDATE " + table +
                " SET " + moreSet +
                " WHERE " + whereColumn + "= '" + whereValue + "'";
    }

    //need to add commas in for loop in DAO
    public static String moreSetSQL(String setColumn, String setValue) {
        return setColumn + "= '" + setValue + "'";
    }

    public static Vector<Vector> getRowData(String select, String from, String where, String is) {
     String sql;
        if(where==null && is==null){
            sql = buildSelectSQL(select,from);
        }
        else{
             sql = buildSelectSQL(select, from, where, is);
        }

        Vector<Vector> rowData = new Vector<>();
        Vector row;
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            row = new Vector(colCount);
            while(rs.next()) {
                row = new Vector(colCount);
                for(int i = 1; i <= colCount; i++) {
                    row.add(rs.getString(i));

                }
                rowData.add(row);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowData;
    }

    public static void main(String[] args) {

    }

}