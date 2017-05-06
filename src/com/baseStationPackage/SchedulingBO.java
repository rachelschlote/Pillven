package com.baseStationPackage;


import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SchedulingBO extends SchedulingDAO{
    Vector getMedNames() {
        return getNamesRaw();
    }
    Vector getMedTimes() {
        return getMedTimesRaw();
    }
    void setMedSched(int patNumVal, int index, String newSched) {
        setMedSchedChange(patNumVal,index, newSched);
    }

    String getMedQuantBO(int patNum, int quantIndex,int currentMed) {
        String quantString = "";
        String oldQuant = getMedQuant(patNum);
        String regex = "(([1-5]+))";
        Pattern pattern =  Pattern.compile(regex);
        Matcher matcher = pattern.matcher(select("QUANT","med_sched","PAT_NUM",patNum).toString());
        int i = 0;
        while(matcher.find()&& i <currentMed) {
            quantString += matcher.group() + "!";
            i++;
        }
        quantIndex += quantString.length();
        return Character.toString(oldQuant.charAt(quantIndex));
        }
    void setMedQuantBO(int patNumVal, int index, String newQuant) {

        String quantString = "";
        String regex = "(([1-5]+))";
        Pattern pattern =  Pattern.compile(regex);
        Matcher matcher = pattern.matcher(select("QUANT","med_sched","PAT_NUM",patNumVal).toString());
        int i = 0;
        while(matcher.find()&& i <index-1) {
                quantString += matcher.group() + "!";
                i++;
        }
        quantString+=newQuant +"!";
        while(matcher.find()) {
            quantString += matcher.group() + "!";
            i++;
        }
            setMedQuant(patNumVal,quantString);
        }

    }


