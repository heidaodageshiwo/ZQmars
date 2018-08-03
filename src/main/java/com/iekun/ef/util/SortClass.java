package com.iekun.ef.util;

import java.util.Comparator;

import com.iekun.ef.model.PushAlarmTimeInfo;

public class SortClass implements Comparator{  
    public int compare(Object arg0,Object arg1){  
    	PushAlarmTimeInfo pushAlarm0 = (PushAlarmTimeInfo)arg0;  
    	PushAlarmTimeInfo pushAlarm1 = (PushAlarmTimeInfo)arg1;  
        int flag = pushAlarm0.getAlarmTime().compareTo(pushAlarm1.getAlarmTime());  
        return flag;  
    }  

}
