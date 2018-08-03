package com.iekun.ef.model;

import java.util.ArrayList;
import java.util.Map;

public class RuleSend {

	  private int id;
	  private int syncIndexSymbol; /* 整个都同步成功了，则该标志为置位 */
	  private int sessionId;
	  private String serialNum;
	  //private ArrayList<Integer> segIndList; 
	  private  Map<Integer, Object> sessionIdSegIdMap; 
	  private  Map<Integer, Object> segIdSyncSymbolMap;
	  
	  public int getId()
	  {
	    return this.id;
	  }
	  
	  public void setId(int id)
	  {
	    this.id = id;
	  }
	  
	  public int getSyncIndexSymbol()
	  {
	    return this.syncIndexSymbol;
	  }
	  
	  public void setSyncIndexSymbol(int syncIndexSymbol)
	  {
	    this.syncIndexSymbol = syncIndexSymbol;
	  }
	  
	  public int getSessionId()
	  {
	    return this.sessionId;
	  }
	  
	  public void setSessionId(int sessionId)
	  {
	    this.sessionId = sessionId;
	  }
	  
	  public String getSerialNum()
	  {
	    return this.serialNum;
	  }
	  
	  public void setSerialNum(String serialNum)
	  {
	    this.serialNum = serialNum;
	  }

	public Map<Integer, Object> getSessionIdSegIdMap() {
		return sessionIdSegIdMap;
	}

	public void setSessionIdSegIdMap(Map<Integer, Object> sessionIdSegIdMap) {
		this.sessionIdSegIdMap = sessionIdSegIdMap;
	}

	public Map<Integer, Object> getSegIdSyncSymbolMap() {
		return segIdSyncSymbolMap;
	}

	public void setSegIdSyncSymbolMap(Map<Integer, Object> segIdSyncSymbolMap) {
		this.segIdSyncSymbolMap = segIdSyncSymbolMap;
	}


}
