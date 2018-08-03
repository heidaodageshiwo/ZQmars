package com.iekun.ef.jms.vo.send;

import com.iekun.ef.util.ConvertTools;

public class SendRfPara {
	
	public SendRfPara(){}

	private String rfEnable;
	private String fastConfigEarfcn;
	private String eutraBand;
	private String dlEarfcn;
	private String ulEarfcn;
	private String frameStrucureType;
	private String subframeAssinment;
	private String specialSubframePatterns;
	private String dlBandWidth;
	private String ulBandWidth;
	private String rFchoice;
	private String tx1PowerAttenuation;
	private String tx2PowerAttenuation;
	public String getRfEnable() {
		return rfEnable;
	}
	public void setRfEnable(String rfEnable) {
		this.rfEnable = rfEnable;
	}
	public String getFastConfigEarfcn() {
		return fastConfigEarfcn;
	}
	public void setFastConfigEarfcn(String fastConfigEarfcn) {
		this.fastConfigEarfcn = fastConfigEarfcn;
	}
	public String getEutraBand() {
		return eutraBand;
	}
	public void setEutraBand(String eutraBand) {
		this.eutraBand = eutraBand;
	}
	public String getDlEarfcn() {
		return dlEarfcn;
	}
	public void setDlEarfcn(String dlEarfcn) {
		this.dlEarfcn = dlEarfcn;
	}
	public String getUlEarfcn() {
		return ulEarfcn;
	}
	public void setUlEarfcn(String ulEarfcn) {
		this.ulEarfcn = ulEarfcn;
	}
	public String getFrameStrucureType() {
		return frameStrucureType;
	}
	public void setFrameStrucureType(String frameStrucureType) {
		this.frameStrucureType = frameStrucureType;
	}
	public String getSubframeAssinment() {
		return subframeAssinment;
	}
	public void setSubframeAssinment(String subframeAssinment) {
		this.subframeAssinment = subframeAssinment;
	}
	public String getSpecialSubframePatterns() {
		return specialSubframePatterns;
	}
	public void setSpecialSubframePatterns(String specialSubframePatterns) {
		this.specialSubframePatterns = specialSubframePatterns;
	}
	public String getDlBandWidth() {
		return dlBandWidth;
	}
	public void setDlBandWidth(String dlBandWidth) {
		this.dlBandWidth = dlBandWidth;
	}
	public String getUlBandWidth() {
		return ulBandWidth;
	}
	public void setUlBandWidth(String ulBandWidth) {
		this.ulBandWidth = ulBandWidth;
	}
	public String getrFchoice() {
		return rFchoice;
	}
	public void setrFchoice(String rFchoice) {
		this.rFchoice = rFchoice;
	}
	public String getTx1PowerAttenuation() {
		return tx1PowerAttenuation;
	}
	public void setTx1PowerAttenuation(String tx1PowerAttenuation) {
		this.tx1PowerAttenuation = tx1PowerAttenuation;
	}
	public String getTx2PowerAttenuation() {
		return tx2PowerAttenuation;
	}
	public void setTx2PowerAttenuation(String tx2PowerAttenuation) {
		this.tx2PowerAttenuation = tx2PowerAttenuation;
	}
	
	/**
	 * ��ÿ����ת����HexString������ӿ�ʵ��Ҳ�ɰ���;
	 * �ӿ���1λ����2��HexString
	 * ���HexStringת��byte����
	 * @return
	 * @throws Exception
	 */
	public byte[] getBytes()throws Exception{
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("0000");
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.rfEnable)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.fastConfigEarfcn)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.eutraBand)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.dlEarfcn)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.ulEarfcn)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.frameStrucureType)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.subframeAssinment)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.specialSubframePatterns)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.dlBandWidth)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.ulBandWidth)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.rFchoice)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.tx1PowerAttenuation)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.tx2PowerAttenuation)), 4));
		
		return ConvertTools.hexStringToByte(sb.toString());
	}
	
	public String toString(){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("0000");
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.rfEnable)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.fastConfigEarfcn)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.eutraBand)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.dlEarfcn)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.ulEarfcn)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.frameStrucureType)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.subframeAssinment)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.specialSubframePatterns)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.dlBandWidth)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.ulBandWidth)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.rFchoice)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.tx1PowerAttenuation)), 4));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.tx2PowerAttenuation)), 4));
		
		return sb.toString();
	}	
}
