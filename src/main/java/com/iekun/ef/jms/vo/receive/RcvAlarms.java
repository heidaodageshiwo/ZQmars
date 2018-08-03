package com.iekun.ef.jms.vo.receive;

import java.util.ArrayList;


public class RcvAlarms {

	private int count;
	
	private ArrayList<RcvAlarm> rcvAlarmList;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<RcvAlarm> getRcvAlarmList() {
		return rcvAlarmList;
	}

	public void setRcvAlarmList(ArrayList<RcvAlarm> rcvAlarmList) {
		this.rcvAlarmList = rcvAlarmList;
	}

	
}
