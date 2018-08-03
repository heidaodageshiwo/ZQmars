package com.iekun.ef.service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.model.AnalysisData;
import com.iekun.ef.model.DeviceDetailsModel;
import com.iekun.ef.model.TargetAlarm;
import com.iekun.ef.util.PropertyUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class PoiWriteExcel {

        public static void PoiWriteExcel1(String title,List<TargetAlarm> psgInfos,String name){
        try{
            int sheetNum=1;
            int bodyRowCount=1;
            int currentRowCount=1;
            int perPageNum = 65530;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title+sheetNum);
            setSheetColumn(sheet);
            HSSFRow row = null;
            HSSFCell cell = null;
            HSSFCellStyle titleCellStyle = createTitleCellStyle(workbook);
            writeTitleContent(sheet,titleCellStyle);
            HSSFCellStyle bodyCellStyle = createBodyCellStyle(workbook);
            HSSFCellStyle dateBobyCellStyle = createDateBodyCellStyle(workbook);
            for(int i=0;i<psgInfos.size();i++){
                row = sheet.createRow(bodyRowCount);
                cell = row.createCell((short)0);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(currentRowCount);
                cell = row.createCell((short)1);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getImsi() );
                cell = row.createCell((short)2);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getOperator()==null?"":psgInfos.get(i).getOperator());
                cell = row.createCell((short)3);
                cell.setCellStyle(dateBobyCellStyle);
                cell.setCellValue(psgInfos.get(i).getCityName()==null?"":psgInfos.get(i).getCityName());
                cell = row.createCell((short)4);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getSiteSn()==null?"":psgInfos.get(i).getSiteSn() );
                cell = row.createCell((short)5);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getSiteName()==null?"":psgInfos.get(i).getSiteName());
                cell = row.createCell((short)6);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getDeviceSn()==null?"":psgInfos.get(i).getDeviceSn());
                cell = row.createCell((short)7);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getDeviceName()==null?"":psgInfos.get(i).getDeviceName());
                cell = row.createCell((short)8);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getCaptureTime()==null?"":psgInfos.get(i).getCaptureTime());
	        if(currentRowCount % perPageNum == 0){
                    sheet=null;
                    sheetNum++;
                    sheet = workbook.createSheet(title+sheetNum);
                    setSheetColumn(sheet);
                    bodyRowCount = 0;
                    writeTitleContent(sheet,titleCellStyle);
                }
                bodyRowCount++;
                currentRowCount++;
            }
           String wjcflj=PropertyUtil.getProperty("file");
          String filePar=wjcflj.substring(0,wjcflj.lastIndexOf("/"))+"/"+wjcflj.substring(wjcflj.lastIndexOf("/")+1,wjcflj.length());
            File myPath = new File( filePar );
            if ( !myPath.exists()){
                myPath.mkdir();
            }
            OutputStream	os = new FileOutputStream(wjcflj+"/"+name);
            workbook.write(os);
            os.flush();
            os.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static void PoiWriteTxt(List<TargetAlarm> psgInfos,String name){

        String wjcflj=PropertyUtil.getProperty("file");
        String filePar=wjcflj.substring(0,wjcflj.lastIndexOf("/"))+"/"+wjcflj.substring(wjcflj.lastIndexOf("/")+1,wjcflj.length());
        File myPath = new File( filePar );
        if ( !myPath.exists()){
            myPath.mkdir();
        }
File file =new File(wjcflj+"/"+name);
        try {
            BufferedWriter bw=new BufferedWriter(new FileWriter(file, true));
            String cc="";
            int cout=0;
            for(int i=0;i<psgInfos.size();i++){
                if("".equals(cc)){
                    bw.write("序号"+"    "+"IMSI"+"  "+"运营商"+"    "+"归属地"+"  "+"站点编号"+"   "+"站点名称"+"  "+"设备编号"+"   "+"设备名称"+"  "+"捕获时间"+"\r");
                    bw.newLine();
                    cc="a";
                }
                bw.write(++cout+",");
                bw.write(psgInfos.get(i).getImsi()==null?""+",":psgInfos.get(i).getImsi()+","   );
                bw.write(psgInfos.get(i).getOperator()==null?""+",":psgInfos.get(i).getOperator()+"," );
                bw.write(psgInfos.get(i).getCityName()==null?""+",":psgInfos.get(i).getCityName()+","  );
                bw.write(psgInfos.get(i).getSiteSn()==null?""+",":psgInfos.get(i).getSiteSn()+","  );
                bw.write(psgInfos.get(i).getSiteName()==null?""+",":psgInfos.get(i).getSiteName()+",");
                bw.write(psgInfos.get(i).getDeviceSn()==null?""+",":psgInfos.get(i).getDeviceSn()+","   );
                bw.write(psgInfos.get(i).getDeviceName()==null?""+",":psgInfos.get(i).getDeviceName()+","   );
                bw.write(psgInfos.get(i).getCaptureTime()==null?""+",":psgInfos.get(i).getCaptureTime()+" "  );
                bw.write("\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static HSSFCellStyle createBodyCellStyle(HSSFWorkbook workbook){
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName(HSSFFont.FONT_ARIAL);
        cellStyle.setFont(font);
        cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        return cellStyle;
    }
    public static HSSFCellStyle createDateBodyCellStyle(HSSFWorkbook workbook){
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName(HSSFFont.FONT_ARIAL);
        cellStyle.setFont(font);
        cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        HSSFDataFormat format= workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("yyyy-mm-dd"));
        return cellStyle;
    }

    public static HSSFCellStyle createTitleCellStyle(HSSFWorkbook workbook){
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 8);
        font.setFontName(HSSFFont.FONT_ARIAL);
        cellStyle.setFont(font);
        cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        return cellStyle;
    }
    public static void writeTitleContent (HSSFSheet sheet,HSSFCellStyle cellStyle){
            HSSFRow row = null;
            HSSFCell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell((short)0);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("序号");
            cell = row.createCell((short)1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("IMSI");
            cell = row.createCell((short)2);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("运营商");//
            cell = row.createCell((short)3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("归属地");
            cell = row.createCell((short)4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("站点编号");
            cell = row.createCell((short)5);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("站点名称");
            cell = row.createCell((short)6);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("设备编号");
            cell = row.createCell((short)7);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("设备名称");
            cell = row.createCell((short)8);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("捕获时间");
            cell = row.createCell((short)9);
    }
    public static void setSheetColumn(HSSFSheet sheet){
        sheet.setColumnWidth((short) 0, (short) 4200);
        sheet.setColumnWidth((short) 1, (short) 4200);
        sheet.setColumnWidth((short) 2, (short) 4600);
        sheet.setColumnWidth((short) 3, (short) 4600);
        sheet.setColumnWidth((short) 4, (short) 4200);
        sheet.setColumnWidth((short) 5, (short) 4200);
        sheet.setColumnWidth((short) 6, (short) 4600);
        sheet.setColumnWidth((short) 7, (short) 5100);
        sheet.setColumnWidth((short) 8, (short) 4600);
        sheet.setColumnWidth((short) 9, (short) 4600);
   }


    //导出设备详情
    public static void PoiWriteExcel2(String title, List<DeviceDetailsModel> psgInfos, String name){
        try{
            int sheetNum=1;
            int bodyRowCount=1;
            int currentRowCount=1;
            int perPageNum = 65530;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title+sheetNum);
            setSheetColumn(sheet);
            HSSFRow row = null;
            HSSFCell cell = null;
            HSSFCellStyle titleCellStyle = createTitleCellStyle(workbook);
            writeTitleContent1(sheet,titleCellStyle);
            HSSFCellStyle bodyCellStyle = createBodyCellStyle(workbook);
            HSSFCellStyle dateBobyCellStyle = createDateBodyCellStyle(workbook);
            for(int i=0;i<psgInfos.size();i++){
                row = sheet.createRow(bodyRowCount);

                cell = row.createCell((short)0);
                cell.setCellStyle(bodyCellStyle);
                //cell.setCellValue(currentRowCount);
                cell.setCellValue(psgInfos.get(i).getSitesn()==null?"":psgInfos.get(i).getSitesn());

                cell = row.createCell((short)1);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getSitename()==null?"":psgInfos.get(i).getSitename());

                cell = row.createCell((short)2);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getLongitude()==null?"":psgInfos.get(i).getLongitude());

                cell = row.createCell((short)3);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getLatitude()==null?"":psgInfos.get(i).getLatitude());

                cell = row.createCell((short)4);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getCreatetime()==null?"":psgInfos.get(i).getCreatetime());

                cell = row.createCell((short)5);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getDevicesn()==null?"":psgInfos.get(i).getDevicesn());

                cell = row.createCell((short)6);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getDevicename()==null?"":psgInfos.get(i).getDevicename());

                cell = row.createCell((short)7);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getZt()==null?"":psgInfos.get(i).getZt());

                cell = row.createCell((short)8);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getYd()==null?"":psgInfos.get(i).getYd());

                cell = row.createCell((short)9);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getLt()==null?"":psgInfos.get(i).getLt());

                cell = row.createCell((short)10);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getDx()==null?"":psgInfos.get(i).getDx());

                cell = row.createCell((short)11);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getWz()==null?"":psgInfos.get(i).getWz());
                if(currentRowCount % perPageNum == 0){
                    sheet=null;
                    sheetNum++;
                    sheet = workbook.createSheet(title+sheetNum);
                    setSheetColumn(sheet);
                    bodyRowCount = 0;
                    writeTitleContent(sheet,titleCellStyle);
                }
                bodyRowCount++;
                currentRowCount++;
                 }
            String wjcflj=PropertyUtil.getProperty("file");
            String filePar=wjcflj.substring(0,wjcflj.lastIndexOf("/"))+"/"+wjcflj.substring(wjcflj.lastIndexOf("/")+1,wjcflj.length());
            File myPath = new File( filePar );
            if ( !myPath.exists()){
                myPath.mkdir();
            }
            OutputStream	os = new FileOutputStream(wjcflj+"/"+name);
            workbook.write(os);
            os.flush();
            os.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    //导出设备详情
    public static void writeTitleContent1 (HSSFSheet sheet,HSSFCellStyle cellStyle){
        HSSFRow row = null;
        HSSFCell cell = null;
        row = sheet.createRow(0);

        cell = row.createCell((short)0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("站点编号");

        cell = row.createCell((short)1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("站点名称");


        cell = row.createCell((short)2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("经度");

        cell = row.createCell((short)3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("纬度");

        cell = row.createCell((short)4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("创建日期");

        cell = row.createCell((short)5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("设备编号");

        cell = row.createCell((short)6);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("设备名称");

        cell = row.createCell((short)7);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("状态");

        cell = row.createCell((short)8);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("移动上号数");

        cell = row.createCell((short)9);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("联通上号数");

        cell = row.createCell((short)10);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("电信上号数");

        cell = row.createCell((short)11);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("未知运营商上号数");

       /* cell = row.createCell((short)2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("运营商");//
        cell = row.createCell((short)3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("归属地");
        cell = row.createCell((short)4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("站点编号");
        cell = row.createCell((short)5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("站点名称");
        cell = row.createCell((short)6);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("设备编号");
        cell = row.createCell((short)7);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("设备名称");
        cell = row.createCell((short)8);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("捕获时间");
        cell = row.createCell((short)9);*/
    }















    //导出可疑人员分析
    public static void PoiWriteExcel5(String title, List<AnalysisData> psgInfos, String name){
        try{
            int sheetNum=1;
            int bodyRowCount=1;
            int currentRowCount=1;
            int perPageNum = 65530;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title+sheetNum);
            setSheetColumn(sheet);
            HSSFRow row = null;
            HSSFCell cell = null;
            HSSFCellStyle titleCellStyle = createTitleCellStyle(workbook);
            writeTitleContent5(sheet,titleCellStyle);
            HSSFCellStyle bodyCellStyle = createBodyCellStyle(workbook);
            HSSFCellStyle dateBobyCellStyle = createDateBodyCellStyle(workbook);
            for(int i=0;i<psgInfos.size();i++){
                row = sheet.createRow(bodyRowCount);

                cell = row.createCell((short)0);
                cell.setCellStyle(bodyCellStyle);
                //cell.setCellValue(currentRowCount);
                cell.setCellValue(psgInfos.get(i).getId()==null?"":psgInfos.get(i).getId());

                cell = row.createCell((short)1);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getImsi()==null?"":psgInfos.get(i).getImsi());

                cell = row.createCell((short)2);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getOperator()==null?"":psgInfos.get(i).getOperator());

                cell = row.createCell((short)3);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getCityname()==null?"":psgInfos.get(i).getCityname());

                cell = row.createCell((short)4);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getOne()==null?"":psgInfos.get(i).getOne());

                cell = row.createCell((short)5);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getTwo()==null?"":psgInfos.get(i).getTwo());

                cell = row.createCell((short)6);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getDaycount()==null?"":psgInfos.get(i).getDaycount());

                cell = row.createCell((short)7);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getDatacount()==null?"":psgInfos.get(i).getDatacount());

               /* cell = row.createCell((short)8);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getYd()==null?"":psgInfos.get(i).getYd());

                cell = row.createCell((short)9);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getLt()==null?"":psgInfos.get(i).getLt());

                cell = row.createCell((short)10);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getDx()==null?"":psgInfos.get(i).getDx());

                cell = row.createCell((short)11);
                cell.setCellStyle(bodyCellStyle);
                cell.setCellValue(psgInfos.get(i).getWz()==null?"":psgInfos.get(i).getWz());*/
                if(currentRowCount % perPageNum == 0){
                    sheet=null;
                    sheetNum++;
                    sheet = workbook.createSheet(title+sheetNum);
                    setSheetColumn(sheet);
                    bodyRowCount = 0;
                    writeTitleContent(sheet,titleCellStyle);
                }
                bodyRowCount++;
                currentRowCount++;
            }
            String wjcflj=PropertyUtil.getProperty("file");
            String filePar=wjcflj.substring(0,wjcflj.lastIndexOf("/"))+"/"+wjcflj.substring(wjcflj.lastIndexOf("/")+1,wjcflj.length());
            File myPath = new File( filePar );
            if ( !myPath.exists()){
                myPath.mkdir();
            }
            OutputStream	os = new FileOutputStream(wjcflj+"/"+name);
            workbook.write(os);
            os.flush();
            os.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    //导出可疑人员分析
    public static void writeTitleContent5 (HSSFSheet sheet,HSSFCellStyle cellStyle){
        HSSFRow row = null;
        HSSFCell cell = null;
        row = sheet.createRow(0);

        cell = row.createCell((short)0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("编号");

        cell = row.createCell((short)1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("IMSI");


        cell = row.createCell((short)2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("运营商");

        cell = row.createCell((short)3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("归属地");

        cell = row.createCell((short)4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("第1可能位置");

        cell = row.createCell((short)5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("第2可能位置");

        cell = row.createCell((short)6);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("上号天数");

        cell = row.createCell((short)7);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("上号总数");
/*
        cell = row.createCell((short)8);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("移动上号数");

        cell = row.createCell((short)9);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("联通上号数");

        cell = row.createCell((short)10);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("电信上号数");

        cell = row.createCell((short)11);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("未知运营商上号数");*/

       /* cell = row.createCell((short)2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("运营商");//
        cell = row.createCell((short)3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("归属地");
        cell = row.createCell((short)4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("站点编号");
        cell = row.createCell((short)5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("站点名称");
        cell = row.createCell((short)6);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("设备编号");
        cell = row.createCell((short)7);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("设备名称");
        cell = row.createCell((short)8);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("捕获时间");
        cell = row.createCell((short)9);*/
    }
}
