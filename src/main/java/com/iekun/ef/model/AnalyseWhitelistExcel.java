package com.iekun.ef.model;

import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.util.TimeUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AnalyseWhitelistExcel {

    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public AnalyseWhitelistExcel(){}
    //获取总行数
    public int getTotalRows()  { return totalRows;}
    //获取总列数
    public int getTotalCells() {  return totalCells;}
    //获取错误信息
    public String getErrorInfo() { return errorMsg; }

    /**
     * 读EXCEL文件，获取信息集合
     */
    public List<Whitelist> getExcelInfo(MultipartFile mFile) {
        List<Whitelist> targetRuleList = null;
        String fileName = mFile.getOriginalFilename();//mFile.getName();//获取文件名
        try {
            if (!validateExcel(fileName)) {// 验证文件名是否合格
                return null;
            }
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }
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
    public List<Whitelist> createExcel(InputStream is, boolean isExcel2003) {
        List<Whitelist> targetRuleList = null;
        try{
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            targetRuleList = AnalyseExcelValue(wb);// 读取Excel里面客户的信息
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
    private List<Whitelist> AnalyseExcelValue(Workbook wb) {

        List<Whitelist> WhitelistList = new ArrayList<Whitelist>();
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        //1.获取字段名来判断是不是要上传的Excel指定文件
        Row row0 = sheet.getRow(0);
        String cell0 = String.valueOf(row0.getCell(0));//获取name来判断
        String cell1 = String.valueOf(row0.getCell(1)); // 获取imsi来判断
        if ("IMSI".equals(cell0) && "手机号".equals(cell1)) {
            // 得到Excel的行数
            this.totalRows = sheet.getPhysicalNumberOfRows();
            // 得到Excel的列数(前提是有行数)
            if (totalRows > 1 && sheet.getRow(0) != null) {
                this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
            }
            // 循环Excel行数
            for (int r = 1; r < totalRows; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                Cell firstCell = row.getCell(0);
                if (null == firstCell) {
                    continue;
                }
                Whitelist whitelist = new Whitelist();
                //设置主键ID
                whitelist.setId(UUID.randomUUID().toString().trim().replace("-", ""));
                whitelist.setCreatorId(SigletonBean.getUserId());
                // 循环Excel的列
                DecimalFormat df = new DecimalFormat("0");
                for (int c = 0; c < this.totalCells; c++) {
                    Cell cell = row.getCell(c);
                    if (null != cell) {
                        if (c == 0) {
                            //如果是纯数字,比如你写的是25,cell.getNumericCellValue()获得是25.0,通过截取字符串去掉.0获得25
                            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                String imsi = df.format(cell.getNumericCellValue());
                                whitelist.setImsi(imsi);
                            } else {
                                whitelist.setImsi(cell.getStringCellValue());
                            }
                        } else if (c == 1) {
                            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                String phone = df.format(cell.getNumericCellValue());
                                whitelist.setPhone(phone);
                            } else {
                                whitelist.setPhone(cell.getStringCellValue());
                            }
                        } else if (c == 2) {
                            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                String name = df.format(cell.getNumericCellValue());
                                whitelist.setName(name);
                            } else {
                                whitelist.setName(cell.getStringCellValue());
                            }
                        } else if (c == 3) {
                            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                String operator = df.format(cell.getNumericCellValue());
                                whitelist.setOperator(operator);
                            } else {
                                whitelist.setOperator(cell.getStringCellValue());//mac
                            }
                        } else if (c == 4) {
                            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                String ownership = df.format(cell.getNumericCellValue());
                                whitelist.setOwnership(ownership);

                            } else {
                                whitelist.setOwnership(cell.getStringCellValue());
                            }
                        } else if (c == 5) {
                            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                String organization = df.format(cell.getNumericCellValue());
                                whitelist.setOrganization(organization);
                            } else {
                                whitelist.setOrganization(cell.getStringCellValue());
                            }

                        }
                    }
                }
                // 添加到list
                WhitelistList.add(whitelist);
            }
        }
        return WhitelistList;
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
