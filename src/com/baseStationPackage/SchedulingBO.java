package com.baseStationPackage;


import java.util.Vector;

public class SchedulingBO extends SchedulingDAO{

    public Vector getMedNames() {
        return getNamesRaw();
    }
    public Vector getMedTimes() {
        return getMedTimesRaw();
    }
}
