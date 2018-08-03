package com.iekun.ef.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.iekun.ef.model.Site;

/*import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.iekun.efence.entity.Account;
import com.iekun.efence.entity.ImsiInfo;
import com.iekun.efence.entity.Purview;
import com.iekun.efence.service.MainService;*/

public class Utils {
	private static String[] cmNum = { "134", "135", "136", "137", "138", "139", "150", "151", "152", "157", "158",
			"159", "182", "183", "187", "188" };
	private static String[] cuNum = { "130", "131", "132", "155", "156", "185", "186" };

	public static byte[] intToByte(int i) {
		byte[] abyte0 = new byte[4];
		abyte0[0] = ((byte) (0xFF & i));
		abyte0[1] = ((byte) ((0xFF00 & i) >> 8));
		abyte0[2] = ((byte) ((0xFF0000 & i) >> 16));
		abyte0[3] = ((byte) ((0xFF000000 & i) >> 24));
		return abyte0;
	}

	public static int bytes2int2(byte[] b) {
		StringBuffer sb = new StringBuffer();
		int value = 0;
		for (int i = 0; i < 4; i++) {
			sb.append(b[i] + " ");

			value += ((b[i] & 0xFF) << 8 * i);
		}
		return value;
	}

	public static boolean hasChinese(String[] strs) {
		for (String str : strs) {
			if (str.length() != str.getBytes().length) {
				return true;
			}
		}
		return false;
	}

	public static int getMaxStringLength(String[] strs) {
		int maxLength = 0;
		for (String str : strs) {
			if (str.length() > maxLength) {
				maxLength = str.length();
			}
		}
		return maxLength;
	}

	public static String getString(byte[] data) {
		StringBuffer sb = new StringBuffer();

		int realLength = -1;
		for (int i = 0; i < data.length; i++) {
			sb.append(data[i] + " ");
			if (data[i] == 0) {
				realLength = i;
				break;
			}
		}
		if (realLength < 0) {
			realLength = data.length;
		}
		byte[] realData = new byte[realLength];
		System.arraycopy(data, 0, realData, 0, realLength);

		return new String(realData);
	}

	public static byte[] getBytes(short data) {
		byte[] bytes = new byte[2];
		bytes[0] = ((byte) (data & 0xFF));
		bytes[1] = ((byte) ((data & 0xFF00) >> 8));
		return bytes;
	}

	public static byte[] getBytes(char data) {
		byte[] bytes = new byte[2];
		bytes[0] = ((byte) data);
		bytes[1] = ((byte) (data >> '\b'));
		return bytes;
	}

	public static byte[] getBytes(int data) {
		byte[] bytes = new byte[4];
		bytes[0] = ((byte) (data & 0xFF));
		bytes[1] = ((byte) ((data & 0xFF00) >> 8));
		bytes[2] = ((byte) ((data & 0xFF0000) >> 16));
		bytes[3] = ((byte) ((data & 0xFF000000) >> 24));
		return bytes;
	}

	public static byte[] getBytes(long data) {
		byte[] bytes = new byte[8];
		bytes[0] = ((byte) (int) (data & 0xFF));
		bytes[1] = ((byte) (int) (data >> 8 & 0xFF));
		bytes[2] = ((byte) (int) (data >> 16 & 0xFF));
		bytes[3] = ((byte) (int) (data >> 24 & 0xFF));
		bytes[4] = ((byte) (int) (data >> 32 & 0xFF));
		bytes[5] = ((byte) (int) (data >> 40 & 0xFF));
		bytes[6] = ((byte) (int) (data >> 48 & 0xFF));
		bytes[7] = ((byte) (int) (data >> 56 & 0xFF));
		return bytes;
	}

	public static byte[] getBytes(float data) {
		int intBits = Float.floatToIntBits(data);
		return getBytes(intBits);
	}

	public static byte[] getBytes(double data) {
		long intBits = Double.doubleToLongBits(data);
		return getBytes(intBits);
	}

	public static short getShort(byte[] bytes) {
		return (short) (0xFF & bytes[0] | 0xFF00 & bytes[1] << 8);
	}

	public static char getChar(byte[] bytes) {
		return (char) (0xFF & bytes[0] | 0xFF00 & bytes[1] << 8);
	}

	public static int getInt(byte[] bytes) {
		return 0xFF & bytes[0] | 0xFF00 & bytes[1] << 8 | 0xFF0000 & bytes[2] << 16 | 0xFF000000 & bytes[3] << 24;
	}

	public static long getLong(byte[] bytes) {
		return 0xFF & bytes[0] | 0xFF00 & bytes[1] << 8 | 0xFF0000 & bytes[2] << 16 | 0xFF000000 & bytes[3] << 24
				| 0x0 & bytes[4] << 32 | 0x0 & bytes[5] << 40 | 0x0 & bytes[6] << 48 | 0x0 & bytes[7] << 56;
	}

	public static float getFloat(byte[] bytes) {
		return Float.intBitsToFloat(getInt(bytes));
	}

	public static double getDouble(byte[] bytes) {
		long l = getLong(bytes);
		return Double.longBitsToDouble(l);
	}

	public static boolean StringIsNotNull(String str) {
		if ((null != str) && (!str.equals("null")) && (str.trim().length() > 0)) {
			return true;
		}
		return false;
	}

	public static String joinArray(byte[] data) {
		StringBuffer sb = new StringBuffer();
		for (byte b : data) {
			if (sb.length() == 0) {
				sb.append(b);
			} else {
				sb.append(" " + b);
			}
		}
		return sb.toString();
	}

	/*public static void export(List<ImsiInfo> dataList, String[] headTitle, String fileName, String folderPath,
			HttpServletResponse response) {
		try {
			int total = dataList.size();
			int MAX_PER_SHEET = 100000;
			int num_sheet = total / MAX_PER_SHEET;
			if (total % MAX_PER_SHEET > 0) {
				num_sheet++;
			}
			HSSFWorkbook wb = new HSSFWorkbook();

			HSSFCellStyle hlink_style = wb.createCellStyle();
			HSSFFont hlink_font = wb.createFont();
			hlink_font.setUnderline((byte) 1);
			hlink_font.setColor((short) 12);
			hlink_style.setFont(hlink_font);
			for (int k = 0; k < num_sheet; k++) {
				HSSFSheet sheet = wb.createSheet("表格" + (k + 1));
				sheet.setDefaultColumnWidth(19);

				HSSFRow row = sheet.createRow(0);

				HSSFFont font = wb.createFont();
				font.setColor((short) 8);
				font.setFontHeightInPoints((short) 12);
				font.setBoldweight((short) 700);
				for (short i = 0; i < headTitle.length; i = (short) (i + 1)) {
					HSSFCell cell = row.createCell(i);

					cell.setCellValue(new HSSFRichTextString(headTitle[i]));

					HSSFCellStyle style = wb.createCellStyle();
					style.setBorderBottom((short) 1);
					style.setBorderLeft((short) 1);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					style.setAlignment((short) 2);

					style.setFont(font);
					cell.setCellStyle(style);
				}
				sheet.createFreezePane(0, 1, 0, 1);

				List<ImsiInfo> list = new ArrayList<ImsiInfo>();
				if (k == num_sheet - 1) {
					list = dataList.subList(k * MAX_PER_SHEET, dataList.size());
				} else {
					list = dataList.subList(k * MAX_PER_SHEET, (k + 1) * MAX_PER_SHEET);
				}
				HSSFFont cellfont = wb.createFont();
				cellfont.setColor((short) 8);
				cellfont.setFontHeightInPoints((short) 11);
				for (int m = 0; m < list.size(); m++) {
					ImsiInfo imsiInfo = (ImsiInfo) list.get(m);
					HSSFRow rowobj = sheet.createRow(m + 1);

					HSSFCell imsiCell = rowobj.createCell(0);

					imsiCell.setCellValue(imsiInfo.getImsi());

					HSSFCell imeiCell = rowobj.createCell(1);
					imeiCell.setCellValue(imsiInfo.getImei());

					HSSFCell netCell = rowobj.createCell(2);
					netCell.setCellValue(imsiInfo.getNetcodeStr());

					HSSFCell timeCell = rowobj.createCell(3);
					timeCell.setCellValue(imsiInfo.getDate());

					HSSFCell vestCell = rowobj.createCell(4);
					vestCell.setCellValue(imsiInfo.getVest());

					HSSFCell devCodeCell = rowobj.createCell(5);
					devCodeCell.setCellValue(imsiInfo.getDevcode());
				}
			}
			if (null != response) {
				response.reset();
				response.setContentType("unknown");

				fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1"); // FOR
																				// WINDOWS
																				// DEFAULT

				// fileName = new String(fileName.getBytes("UTF8"), "UTF8"); //
				// BY JEFFMA， for LINUX

				response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xls\"");

				response.addHeader("Pragma", "no-cache");
				response.addHeader("Expires", "0");

				ServletOutputStream ouputStream = response.getOutputStream();
				wb.write(ouputStream);
				ouputStream.flush();
				ouputStream.close();
			} else {
				File file = new File(folderPath);
				if (!file.exists()) {
					file.mkdir();
				}
				String filepath = folderPath + "/" + fileName + ".xls";
				FileOutputStream fos = new FileOutputStream(filepath);
				wb.write(fos);
				fos.flush();
				fos.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
*/
	public static String getMsin(String imsiStr) {
		String msin = "";
		if ((null == imsiStr) || (imsiStr.trim().length() != 15)) {
			return "";
		}
		int s0 = Integer.parseInt(imsiStr.charAt(8) + "");

		int h0 = Integer.parseInt(imsiStr.charAt(9) + "");
		if (imsiStr.startsWith("46000")) {
			if (h0 == 0) {
				if ((s0 >= 5) && (s0 <= 9)) {
					StringBuffer sb = new StringBuffer();
					sb.append("13");
					sb.append(s0);
					sb.append("0");
					String str = imsiStr.substring(5, 8);
					sb.append(str);

					msin = sb.toString();
				}
			} else if ((s0 >= 0) && (s0 <= 4)) {
				StringBuffer sb = new StringBuffer();
				sb.append("13");
				sb.append(s0 + 5);
				sb.append(h0);
				String str = imsiStr.substring(5, 8);
				sb.append(str);

				msin = sb.toString();
			}
		} else if (imsiStr.startsWith("460020")) {
			StringBuffer sb = new StringBuffer();
			sb.append("134");
			String str = imsiStr.substring(6, 10);
			sb.append(str);

			msin = sb.toString();
		} else if (imsiStr.startsWith("460021")) {
			StringBuffer sb = new StringBuffer();
			sb.append("151");
			String str = imsiStr.substring(6, 10);
			sb.append(str);

			msin = sb.toString();
		} else if (imsiStr.startsWith("460022")) {
			StringBuffer sb = new StringBuffer();
			sb.append("152");
			String str = imsiStr.substring(6, 10);
			sb.append(str);

			msin = sb.toString();
		} else if (imsiStr.startsWith("460023")) {
			StringBuffer sb = new StringBuffer();
			sb.append("150");
			String str = imsiStr.substring(6, 10);
			sb.append(str);

			msin = sb.toString();
		} else if (imsiStr.startsWith("460025")) {
			StringBuffer sb = new StringBuffer();
			sb.append("183");
			String str = imsiStr.substring(6, 10);
			sb.append(str);

			msin = sb.toString();
		} else if (imsiStr.startsWith("460026")) {
			StringBuffer sb = new StringBuffer();
			sb.append("182");
			String str = imsiStr.substring(6, 10);
			sb.append(str);

			msin = sb.toString();
		} else if (imsiStr.startsWith("460027")) {
			StringBuffer sb = new StringBuffer();
			sb.append("187");
			String str = imsiStr.substring(6, 10);
			sb.append(str);

			msin = sb.toString();
		} else if (imsiStr.startsWith("460028")) {
			StringBuffer sb = new StringBuffer();
			sb.append("158");
			String str = imsiStr.substring(6, 10);
			sb.append(str);

			msin = sb.toString();
		} else if (imsiStr.startsWith("460029")) {
			StringBuffer sb = new StringBuffer();
			sb.append("159");
			String str = imsiStr.substring(6, 10);
			sb.append(str);

			msin = sb.toString();
		} else if (imsiStr.startsWith("460077")) {
			StringBuffer sb = new StringBuffer();
			sb.append("157");
			String str = imsiStr.substring(6, 10);
			sb.append(str);

			msin = sb.toString();
		} else if (imsiStr.startsWith("460078")) {
			StringBuffer sb = new StringBuffer();
			sb.append("188");
			String str = imsiStr.substring(6, 10);
			sb.append(str);

			msin = sb.toString();
		} else if (imsiStr.startsWith("460079")) {
			StringBuffer sb = new StringBuffer();
			sb.append("147");
			String str = imsiStr.substring(6, 10);
			sb.append(str);

			msin = sb.toString();
		} else if (imsiStr.startsWith("46001")) {
			StringBuffer sb = new StringBuffer();
			if ((h0 == 0) || (h0 == 1)) {
				sb.append("130");
			} else if (h0 == 9) {
				sb.append("131");
			} else if (h0 == 2) {
				sb.append("132");
			} else if (h0 == 3) {
				sb.append("156");
			} else if (h0 == 4) {
				sb.append("155");
			} else if (h0 == 6) {
				sb.append("186");
			}
			if (sb.length() > 0) {
				sb.append(s0);
				sb.append(imsiStr.substring(5, 8));

				msin = sb.toString();
			}
		}
		return msin;
	}

	public static String getRecordName(int addDays) {
		StringBuffer strBuffer = new StringBuffer("");

		Calendar now = Calendar.getInstance();
		if (addDays != 0) {
			now.add(5, addDays);
		}
		Date nowDate = now.getTime();

		DateFormat format = new SimpleDateFormat("yyyy_MM_dd");

		strBuffer.append("t_record_");
		strBuffer.append(format.format(nowDate));

		System.out.println(strBuffer.toString());

		return strBuffer.toString();
	}
	
	public static String getSitelistStr(List<Site> sites)
	{
		String listSiteStr = "(";
		int listSize = sites.size();
	    for(int i =0; i < listSize -1; i ++)
	    {
	    	listSiteStr = listSiteStr + "'" +sites.get(i).getSn() + "'" + ",";
	    }
	    
	    listSiteStr = listSiteStr + "'" + sites.get(listSize-1).getSn() + "'" + ")";
	    //params.put("sites", sites);
	    return listSiteStr;
	    
	}

}
