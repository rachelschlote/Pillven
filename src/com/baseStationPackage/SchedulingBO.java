package com.baseStationPackage;


import java.util.Vector;

public class SchedulingBO extends SchedulingDAO{
    Vector getMedNames() {
        return getNamesRaw();
    }
    Vector getMedTimes() {
        return getMedTimesRaw();
    }
    void set(int patNumVal,int index, String newSched) {
        setMedSchedChange(patNumVal,index, newSched);
    }
}
