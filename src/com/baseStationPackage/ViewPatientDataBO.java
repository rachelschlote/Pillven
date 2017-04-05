package com.baseStationPackage;

import java.util.Vector;

public class ViewPatientDataBO extends ViewPatientDataDAO {
    private static final String[] DB_COL ={"PATIENT_NUMBER","FULL_NAME","FIRST_NAME","MIDDLE_NAME","LAST_NAME","AGE","ETHNICITY","ALLERGIES","CHRONIC_CONDITIONS","SHORT_CONDITIONS","DATE_ADMITTED","DATE_DISCHARGE","MED_TABLE"};

    public static Vector getLabelData() {
        Vector colDisp = new Vector();
        Vector colNameDB = getColNamesFromDatabase("*","PATIENT_DATA");

        for(int i = 0; i<=colNameDB.indexOf(colNameDB.lastElement()); i++) {//need to change this
            for(int j = 0; j<DB_COL.length;j++) {
                if(colNameDB.get(i).toString().equals(DB_COL[j])) {
                        colDisp.add(colNameDB.get(i).toString());
                }
            }
        }
        return colDisp;
    }

    public static Vector<Vector> getRowData(String searchBySelection, String searchByText) {

        return getRowDataFromDatabase("*","PATIENT_DATA", searchBySelection, searchByText);
    }

    public static void updateDatabase(Vector vector) {
        setRowDataToDatabase(vector);
    }



}
