package com.baseStationPackage;


public class SignInDAO extends SQLiteJDBC{

    public static String getPassword(String userName) {

        String select ="PASSWORD";
        String from = "sign_in";
        String where = "USER";
        Object password = null;
        password = select(select, from, where, userName);

        return password.toString();
    }

}
