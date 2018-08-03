package com.iekun.ef.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.model.TargetRule;
import com.iekun.ef.util.TimeUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {  
    //总行数  
    private int totalRows = 0;    
    //总条数  
    private int totalCells = 0;   
    //错误信息接收器  
    private String errorMsg;  
    //构造方法  
    public ReadExcel(){}  
    //获取总行数  
    public int getTotalRows()  { return totalRows;}   
    //获取总列数  
    public int getTotalCells() {  return totalCells;}   
    //获取错误信息  
    public String getErrorInfo() { return errorMsg; }    
      
    //TargetAlarm aa;
  /** 
   * 读EXCEL文件，获取信息集合 
   * @param fielName 
   * @return 
   */  
    public List<TargetRule> getExcelInfo(MultipartFile mFile) {  
    	List<TargetRule> targetRuleList = null;
    	String fileName = mFile.getOriginalFilename();//mFile.getName();//获取文件名  
        try {  
            if (!validateExcel(fileName)) {// 验证文件名是否合格  
                return null;  
            }  
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本  
            if (isExcel2007(fileName)) {  
                isExcel2003 = false;  
            }
            
            //InputStream in = new FileInputStream(mFile);
            targetRuleList = createExcel(mFile.getInputStream(), isExcel2003);
           
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return targetRuleList;  
    }  
    
  /** 
   * 根据excel里面的内容读取客户信息 
   * @param is 输入流 
   * @param isExcel2003 excel是2003还是2007版本 
   * @return 
   * @throws IOException 
   */  
    public List<TargetRule> createExcel(InputStream is, boolean isExcel2003) {  
    	List<TargetRule> targetRuleList = null;
    	try{  
            Workbook wb = null;  
            if (isExcel2003) {// 当excel是2003时,创建excel2003  
                wb = new HSSFWorkbook(is);  
            } else {// 当excel是2007时,创建excel2007  
                wb = new XSSFWorkbook(is);  
            }  
            targetRuleList = readExcelValue(wb);// 读取Excel里面客户的信息  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return targetRuleList;  
    }  
    
  /** 
   * 读取Excel里面客户的信息 
   * @param wb 
   * @return 
   */  
    private List<TargetRule> readExcelValue(Workbook wb) {  
        // 得到第一个shell  
        Sheet sheet = wb.getSheetAt(0);  
        // 得到Excel的行数  
        this.totalRows = sheet.getPhysicalNumberOfRows();  
        // 得到Excel的列数(前提是有行数)  
        if (totalRows > 1 && sheet.getRow(0) != null) {  
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();  
        }  
        List<TargetRule> targetRuleList = new ArrayList<TargetRule>();  
        // 循环Excel行数  
        for (int r = 1; r < totalRows; r++) {  
            Row row = sheet.getRow(r);  
            if (row == null){  
                continue;  
            }  
            Cell firstCell = row.getCell(0);
            if (null == firstCell)
            {
            	continue;
            }
            TargetRule targetRule = new TargetRule();
            //targetRule.setCreatorId(SigletonBean.getUserId());
            Date date = new Date();
            targetRule.setCreateTime(TimeUtils.timeFormatterStr.format(date));
            date = null;
            targetRule.setDeleteFlag(0);
            // 循环Excel的列  
            DecimalFormat df = new DecimalFormat("0"); 
            for (int c = 0; c < this.totalCells; c++) {  
                Cell cell = row.getCell(c); 
                
                if (null != cell) {  
                    if (c == 0) {  
                        //如果是纯数字,比如你写的是25,cell.getNumericCellValue()获得是25.0,通过截取字符串去掉.0获得25  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String name = String.valueOf(cell.getNumericCellValue());  
                            targetRule.setName(name.substring(0, name.length()-2>0?name.length()-2:1));//名称  
                            
                        }else{  
                        	targetRule.setName(cell.getStringCellValue());//名称  
                        }  
                    } else if (c == 1) {  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                        	
                        	String imsi = df.format(cell.getNumericCellValue());  
                            //String imsi = cell.getStringCellValue();//String.valueOf(cell.getNumericCellValue());  
                            targetRule.setImsi(imsi);//imsi  
                        }else{  
                        	targetRule.setImsi(cell.getStringCellValue());//imsi
                        }  
                    } else if (c == 2){  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String imei = df.format(cell.getNumericCellValue());  
                            targetRule.setImei(imei);//imei  
                        }else{  
                        	targetRule.setImei(cell.getStringCellValue());//Imei  
                        }  
                    }else if (c == 3){  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String mac = df.format(cell.getNumericCellValue());  
                            
                            targetRule.setMac(mac);//mac  
                        }else{  
                        	targetRule.setMac(cell.getStringCellValue());//mac  
                        }  
                    }else if (c == 4){  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String targetType = String.valueOf(cell.getNumericCellValue());  
                            targetRule.setTargetType(Integer.parseInt(targetType.substring(0, targetType.length()-2>0?targetType.length()-2:1)));//targetType  
                           
                        }else{  
                        	targetRule.setTargetType(Integer.parseInt(cell.getStringCellValue()));//targetType  
                        }  
                    }else if (c == 5){  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String receiver = String.valueOf(cell.getNumericCellValue());  
                            targetRule.setReceiver(receiver);//receiver  
                        }else{  
                        	targetRule.setReceiver(cell.getStringCellValue());//receiver  
                        }  
                    }else if (c == 6){  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String remark = df.format(cell.getNumericCellValue());  
                            //targetRule.setRemark(remark.substring(0, remark.length()-2>0?remark.length()-2:1));//remark  
                            targetRule.setRemark(remark);
                        }else{  
                        	targetRule.setRemark(cell.getStringCellValue());//remark  
                        }  
                    }        
                    
                }  
            }  
            // 添加到list  
            targetRuleList.add(targetRule);  
        }  
        return targetRuleList;  
    }  
      
    /** 
     * 验证EXCEL文件 
     *  
     * @param filePath 
     * @return 
     */  
    public boolean validateExcel(String filePath) {  
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {  
            errorMsg = "文件名不是excel格式";  
            return false;  
        }  
        return true;  
    }  
      
    // @描述：是否是2003的excel，返回true是2003   
    public static boolean isExcel2003(String filePath)  {    
         return filePath.matches("^.+\\.(?i)(xls)$");    
     }    
     
    //@描述：是否是2007的excel，返回true是2007   
    public static boolean isExcel2007(String filePath)  {    
         return filePath.matches("^.+\\.(?i)(xlsx)$");    
     }    
}  
