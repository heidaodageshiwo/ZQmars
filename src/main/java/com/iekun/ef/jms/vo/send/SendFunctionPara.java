package com.iekun.ef.jms.vo.send;

import com.iekun.ef.util.ConvertTools;

/*
 * Typdef struct 					
{
	uint8	paraMcc[3];
	uint8		debug2;
	uint8		paraMnc[2];
	uint8 	ControlRange;  1黑名单，2全部
	uint8		INandOUTtype;
	uint16	BoolStart;
	uint16	paraPciNo;
	uint32	paraPeri;
	uint16	ARFCN;
	Uint8	padding[2];
	uint32 reserve[4];/*预留;
}RecvFuncPara;
*/
public class SendFunctionPara {
	
	public SendFunctionPara(){}
	
	private String paraMcc;
	private String debug;
	private String paraMnc;
	private String controlRange;
	private String inandOutType;
	private String boolStart;
	private String paraPcino;
	private String paraPeri;
	private String arfcn;
	private String reserve;
	private String arfcn1;
	private String arfcn2;
	private String arfcn3;



	public byte[] getBytes()throws Exception{
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("00");
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.paraMcc, 16)), 6));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.paraMnc, 16)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.boolStart, 16)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.paraPcino, 16)), 4));
		sb.append("0000");
		sb.append("00000000");
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.paraPeri, 16)), 8));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.controlRange, 16)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.inandOutType, 16)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.debug, 16)), 2));
		sb.append("00");
		/*sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.arfcn, 16)), 4));
		sb.append("0000");
		sb.append("00000000");*/
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.arfcn, 16)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.arfcn1, 16)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.arfcn2, 16)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.arfcn3, 16)), 4));
		return ConvertTools.hexStringToByte(sb.toString());
	}
	
	public String toString(){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("00");
		sb.append(this.getChar(ConvertTools.fillZero(this.paraMcc, 3)));
		sb.append(this.getChar(ConvertTools.fillZero(this.paraMnc, 2)));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.boolStart)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.paraPcino)), 4));
		sb.append("0000");
		sb.append("00000000");
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.paraPeri)), 8));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.controlRange)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.inandOutType)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.debug)), 2));
		sb.append("00");
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.arfcn)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.arfcn1)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.arfcn2)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.arfcn3)), 4));
		/*sb.append("0000");
		sb.append("00000000000000000000000000000000");
		*/
		sb.append("000000000000000000000000");
		
		return sb.toString();
	}
	
	private String getChar(String charString){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < charString.length(); i++){
			char x = charString.charAt(i);
			sb.append(ConvertTools.fillZero(String.valueOf(x), 2));
		}
		return sb.toString();
	}
	
	public String getParaMcc() {
		return paraMcc;
	}
	public void setParaMcc(String paraMcc) {
		this.paraMcc = paraMcc;
	}
	public String getDebug() {
		return debug;
	}
	public void setDebug(String debug) {
		this.debug = debug;
	}
	public String getControlRange() {
		return controlRange;
	}
	public void setControlRange(String controlRange) {
		this.controlRange = controlRange;
	}
	public String getInandOutType() {
		return inandOutType;
	}
	public void setInandOutType(String inandOutType) {
		this.inandOutType = inandOutType;
	}
	public String getBoolStart() {
		return boolStart;
	}
	public void setBoolStart(String boolStart) {
		this.boolStart = boolStart;
	}
	public String getParaPcino() {
		return paraPcino;
	}
	public void setParaPcino(String paraPcino) {
		this.paraPcino = paraPcino;
	}
	public String getParaPeri() {
		return paraPeri;
	}
	public void setParaPeri(String paraPeri) {
		this.paraPeri = paraPeri;
	}
	public String getArfcn() {
		return arfcn;
	}
	public void setArfcn(String arfcn) {
		this.arfcn = arfcn;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getParaMnc() {
		return paraMnc;
	}

	public void setParaMnc(String paraMnc) {
		this.paraMnc = paraMnc;
	}

	public void setArfcn1(String arfcn1) {
		this.arfcn1 = arfcn1;
	}

	public void setArfcn2(String arfcn2) {
		this.arfcn2 = arfcn2;
	}

	public void setArfcn3(String arfcn3) {
		this.arfcn3 = arfcn3;
	}

	public String getArfcn1() {

		return arfcn1;
	}

	public String getArfcn2() {
		return arfcn2;
	}

	public String getArfcn3() {
		return arfcn3;
	}
}
