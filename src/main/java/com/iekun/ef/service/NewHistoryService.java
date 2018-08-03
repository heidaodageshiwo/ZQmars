package com.iekun.ef.service;

import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvWriter;
import com.iekun.ef.dao.NewHistoryMapper;
import com.iekun.ef.dao.OwnershipSiteMapping;
import com.iekun.ef.model.NewHistoryParamsEntity;
import com.iekun.ef.model.SelfAreaCode;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.UeInfo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NewHistoryService {
    @Autowired
    private NewHistoryMapper newHistoryMapper;

    @Autowired
    private OwnershipSiteMapping ownershipSiteMapping;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");

    @Value("${download.filePath}")
    private String filePath;

    @Value("${createFile.mixCount}")
    private int mixCount;

    @Value("${createFile.maxCount}")
    private int maxCount;

    @Value("${createFile.queryCount}")
    private int queryCount;

    @Value("${createFile.maxTxtCount}")
    private int maxTxtCount;

    public NewHistoryService() {
    }

    public int selectSCount(String tableName) {
        return newHistoryMapper.selectSCount(tableName);
    }

    /**
     * 查询时间插件开始时间
     * */
    public JSONObject getUeinfoToDaterangepicker(){
        JSONObject jsonObject = new JSONObject();
        Boolean status = false;
        String time = "";
        try{
            time = newHistoryMapper.getUeinfoToDaterangepicker();
            status = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        if(true == status){
            jsonObject.put("status",true);
            jsonObject.put("data",time);
        }else{
            jsonObject.put("status",false);
            jsonObject.put("message", "获取时间数据失败");
        }

        return jsonObject;
    }

    public void checkCountIsNeedRefresh(NewHistoryParamsEntity item) {
        int sCount = newHistoryMapper.selectSCount(item.getTableName());
        if(sCount != item.getsCount()) {
            item.setsCount(sCount);
            int x = newHistoryMapper.selectWCount(item);
            item.setwCount(x);
        }
    }

    public List<UeInfo> selectUeInfoByParams(List<NewHistoryParamsEntity> selectParamsList, int iDisplayStart, int iDisplayLength) {
        int passCount = 0;
        List<Map<String, Object>> selectBuf = new ArrayList<>();
        int subLength = iDisplayLength;//长度
        int subIDisplayStart = iDisplayStart;//开始位置
        int startIdx = -1;
        int limit = 0;
        //for(int j = 0; j < selectParamsList.size(); ++j) {
        for(int j = selectParamsList.size(); j > 0; j--) {
            NewHistoryParamsEntity item = selectParamsList.get(j-1);
            //根据所选条件查询的总数
            int wCount = item.getwCount();
            int nowCount = passCount + wCount;

            if(nowCount > subIDisplayStart) {
                int subCount = nowCount - subIDisplayStart;
                startIdx = wCount - subCount;
                if(subCount >= subLength) {
                    limit = subLength;
                } else {
                    limit = subCount;
                }
                subLength -= limit;
                subIDisplayStart += limit;
                Map<String, Object> buf = new HashMap<>();
                buf.put("startIdx", startIdx);
                buf.put("limit", limit);
                buf.put("length", iDisplayLength);
                buf.put("tableName", item.getTableName());
                buf.put("startTime", item.getStartTime());
                buf.put("endTime", item.getEndTime());
                buf.put("filterStart",item.getFilterStart());
                buf.put("filterEnd",item.getFilterEnd());
                buf.put("siteSn", item.getSiteSn());
                buf.put("imsi", item.getImsi());
                buf.put("imei", item.getImei());
                buf.put("operator", item.getOperator());
                buf.put("area", item.getArea());
                buf.put("devinceSn",item.getDeviceSn());
                selectBuf.add(buf);
                if(subLength <= 0)
                    break;
            }
            passCount += wCount;
        }

        //执行查询
        List<UeInfo> records = new ArrayList<>();
        for(Map<String, Object> params : selectBuf) {
            records.addAll(newHistoryMapper.selectHistoryRecords(params));
        }
        return records;
    }

    /**
     * 创建要下载的csv文件
     */
    public void writeCsvFile(String fileName,List<UeInfo> ueInfos,CsvWriter csvWriter) {
        String csvFilePath = filePath.trim() + "/" + fileName;
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFilePath,true),"GBK"),1024);
            csvWriter = new CsvWriter(out,',');
            for(UeInfo ueInfo : ueInfos){
                //"imsi号码", "imei号码", "运营商","归属地","站点编号","站点名称","设备编号","捕获时间"
                String[] csvContent = {"=\"" + ueInfo.getImsi() + "\"","=\"" + ueInfo.getImei() + "\"",ueInfo.getOperator(),ueInfo.getCityName(),ueInfo.getSiteSn(),ueInfo.getSiteName(),ueInfo.getDeviceSn(),"=\"" + ueInfo.getCaptureTime() + "\""};
                csvWriter.writeRecord(csvContent);
            }
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    /**
     * 创建要下载的csv文件
     * @param selectParamsList
     */
    public JSONObject createCsv(List<NewHistoryParamsEntity> selectParamsList) {
        JSONObject jsonObject = new JSONObject();
        boolean status = false;

        List<Integer> list4Selected = new ArrayList<>();
        int allCount = 0;
        for(NewHistoryParamsEntity item : selectParamsList) {
            int wCount = item.getwCount();
            allCount += wCount;
            if(allCount >= maxTxtCount) {
                list4Selected.add(wCount - (allCount - maxTxtCount));
                break;
            } else {
                list4Selected.add(wCount);
            }
        }
        //获取文件名称
        String uuid = UUID.randomUUID().toString().trim().replace("-", "");
        String fileName = uuid + ".csv";
        File file = new File(filePath.trim() + "/" +fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);
        CsvWriter csvWriter = new CsvWriter(filePath.trim() + "/" + fileName, ',', Charset.forName("GBK"));
        try {
            // 写表头
            String[] csvHeaders = { "imsi号码", "imei号码", "运营商","归属地","站点编号","站点名称","设备编号","捕获时间" };
            csvWriter.writeRecord(csvHeaders);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(allCount > 0){
            try {
                for(int j = 0; j < selectParamsList.size() && j < list4Selected.size(); ++j) {
                    Map map = new HashMap();
                    map.put("tableName",selectParamsList.get(j).getTableName());
                    map.put("startTime",selectParamsList.get(j).getStartTime());
                    map.put("endTime",selectParamsList.get(j).getEndTime());
                    map.put("filterStart",selectParamsList.get(j).getFilterStart());
                    map.put("filterEnd",selectParamsList.get(j).getFilterEnd());
                    map.put("imsi",selectParamsList.get(j).getImsi());
                    map.put("operator",selectParamsList.get(j).getOperator());
                    map.put("area",selectParamsList.get(j).getArea());
                    map.put("siteSn",selectParamsList.get(j).getSiteSn());
                    map.put("devinceSn",selectParamsList.get(j).getDeviceSn());
                    int item = list4Selected.get(j);
                    int limit = 0;
                    int startIdx = 0;
                    int selected = 0;
                    while(true) {
                        if(item - selected >= queryCount) {
                            limit = queryCount;
                            map.put("start",startIdx);
                            map.put("length",limit);
                            List<UeInfo> records = newHistoryMapper.queryUeinfo(map);
                            writeCsvFile(fileName, records,csvWriter);
                            startIdx += queryCount;
                            selected += queryCount;
                        } else {
                            limit = item - selected;
                            if(limit > 0){
                                map.put("start",startIdx);
                                map.put("length",limit);
                                List<UeInfo> records = newHistoryMapper.queryUeinfo(map);
                                writeCsvFile(fileName, records,csvWriter);
                            }
                            break;
                        }
                    }
                }
                status = true;
            } catch (Exception e) {
                jsonObject.put("status",false);
                jsonObject.put("message","创建Excel异常");
                e.printStackTrace();
            }
        }else{
            jsonObject.put("status", false);
            jsonObject.put("message", "您的查询条件没有数据,请重新选择!");
        }

        if(true == status){
            jsonObject.put("status", true);
            jsonObject.put("result",fileName);
            jsonObject.put("message", "创建csv文件完成");
        }
        return jsonObject;
    }

    /**
     * 创建要下载的Txt文件
     */
    public void writeTxtFile(String fileName,List<UeInfo> ueInfos){

        try {
            FileWriter writer = new FileWriter(fileName,true);
            for(UeInfo ueInfo : ueInfos) {
                writer.write( ueInfo.getImsi()+";"+ueInfo.getImei()+";"+ueInfo.getOperator()+";"+ueInfo.getCityName()+";"
                 +ueInfo.getSiteSn()+";"+ueInfo.getSiteName()+";"+ueInfo.getDeviceSn()+";"+ueInfo.getCaptureTime() + "\r\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建要下载的Txt文件
     * @param selectParamsList
     */
    public JSONObject createTxt(List<NewHistoryParamsEntity> selectParamsList) {
        JSONObject jsonObject = new JSONObject();
        boolean status = false;

        //获取文件名称
        String uuid = UUID.randomUUID().toString().trim().replace("-", "");
        String result = uuid + ".txt";
        String fileName = filePath.trim() + "/" + result ;
        File file = new File(fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        List<Integer> list4Selected = new ArrayList<>();
        int allCount = 0;
        for(NewHistoryParamsEntity item : selectParamsList) {
            int wCount = item.getwCount();
            allCount += wCount;
            if(allCount >= maxTxtCount) {
                list4Selected.add(wCount - (allCount - maxTxtCount));
                break;
            } else {
                list4Selected.add(wCount);
            }
        }
        if(allCount > 0){
            try {
                for(int j = 0; j < selectParamsList.size() && j < list4Selected.size(); ++j) {
                    Map map = new HashMap();
                    map.put("tableName",selectParamsList.get(j).getTableName());
                    map.put("startTime",selectParamsList.get(j).getStartTime());
                    map.put("endTime",selectParamsList.get(j).getEndTime());
                    map.put("filterStart",selectParamsList.get(j).getFilterStart());
                    map.put("filterEnd",selectParamsList.get(j).getFilterEnd());
                    map.put("imsi",selectParamsList.get(j).getImsi());
                    map.put("operator",selectParamsList.get(j).getOperator());
                    map.put("area",selectParamsList.get(j).getArea());
                    map.put("siteSn",selectParamsList.get(j).getSiteSn());
                    map.put("devinceSn",selectParamsList.get(j).getDeviceSn());
                    int item = list4Selected.get(j);
                    int limit = 0;
                    int startIdx = 0;
                    int selected = 0;
                    while(true) {
                        if(item - selected >= queryCount) {
                            limit = queryCount;
                            map.put("start",startIdx);
                            map.put("length",limit);
                            List<UeInfo> records = newHistoryMapper.queryUeinfo(map);
                            //write file
                            writeTxtFile(fileName, records);
                            startIdx += queryCount;
                            selected += queryCount;
                        } else {
                            limit = item - selected;
                            if(limit > 0){
                                map.put("start",startIdx);
                                map.put("length",limit);
                                List<UeInfo> records = newHistoryMapper.queryUeinfo(map);
                                //write file
                                writeTxtFile(fileName, records);
                            }
                            break;
                        }
                    }
                }
                status = true;
            } catch (Exception e) {
                jsonObject.put("status",false);
                jsonObject.put("message","创建txt文件异常");
                e.printStackTrace();
            }
        }else{
            jsonObject.put("status", false);
            jsonObject.put("message", "您的查询条件没有数据,请重新选择!");
        }

        if(true == status){
            jsonObject.put("status",true);
            jsonObject.put("result",result);
            jsonObject.put("message","创建txt文件成功");
        }
        return jsonObject;

    }

    /**
     * 创建要下载的Excel文件
     * @param selectParamsList
     */
    public JSONObject createExcel(List<NewHistoryParamsEntity> selectParamsList) {
        JSONObject jsonObject = new JSONObject();
        boolean status = false;

        List<Integer> list4Selected = new ArrayList<>();
        int allCount = 0;
        for(NewHistoryParamsEntity item : selectParamsList) {
            int wCount = item.getwCount();
            allCount += wCount;
            if(allCount >= maxCount) {
                list4Selected.add(wCount - (allCount - maxCount));
                break;
            } else {
                list4Selected.add(wCount);
            }
        }

        //获取文件名称
        String uuid = UUID.randomUUID().toString().trim().replace("-", "");
        String fileName = uuid + ".xls";
        if(allCount > 0){
            try {
                for(int j = 0; j < selectParamsList.size() && j < list4Selected.size(); ++j) {
                    Map map = new HashMap();
                    map.put("tableName",selectParamsList.get(j).getTableName());
                    map.put("startTime",selectParamsList.get(j).getStartTime());
                    map.put("endTime",selectParamsList.get(j).getEndTime());
                    map.put("filterStart",selectParamsList.get(j).getFilterStart());
                    map.put("filterEnd",selectParamsList.get(j).getFilterEnd());
                    map.put("imsi",selectParamsList.get(j).getImsi());
                    map.put("operator",selectParamsList.get(j).getOperator());
                    map.put("area",selectParamsList.get(j).getArea());
                    map.put("siteSn",selectParamsList.get(j).getSiteSn());
                    map.put("devinceSn",selectParamsList.get(j).getDeviceSn());
                    int item = list4Selected.get(j);
                    int limit = 0;
                    int startIdx = 0;
                    int selected = 0;
                    while(true) {
                        if(item - selected >= queryCount) {
                            limit = queryCount;
                            map.put("start",startIdx);
                            map.put("length",limit);
                            List<UeInfo> records = newHistoryMapper.queryUeinfo(map);
                            //write file
                            writeExcelFile(uuid, records);
                            startIdx += queryCount;
                            selected += queryCount;
                        } else {
                            limit = item - selected;
                            if(limit > 0){
                                map.put("start",startIdx);
                                map.put("length",limit);
                                List<UeInfo> records = newHistoryMapper.queryUeinfo(map);
                                //write file
                                writeExcelFile(uuid, records);
                            }
                            break;
                        }
                    }
                }
                status = true;
            } catch (Exception e) {
                jsonObject.put("status",false);
                jsonObject.put("message","创建Excel异常");
                e.printStackTrace();
            }
        }else{
            jsonObject.put("status", false);
            jsonObject.put("message", "您的查询条件没有数据,请重新选择!");
        }

        if(true == status){
            jsonObject.put("status", true);
            jsonObject.put("result",fileName);
            jsonObject.put("message", "创建Excel文件完成");
        }
        return jsonObject;
    }

    /**
     * 执行数据导出
     * @param response
     * @param formData
     */
    public void doExportHistoryInfos(HttpServletResponse response, String formData) {
        File file = new  File(filePath.trim() + "/" + formData);
        String suffix = formData.substring(formData.length() - 4);
        String targetName = sdf.format(new Date()) + suffix;
        try{
            FileInputStream inputStream = new FileInputStream(file);
            //写到前端
            response.reset();
            targetName = new String(targetName.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + targetName + "\"");
            response.addHeader("Content-lenth", "" + file.length());
            response.setContentType("application/octet-stream;charset=UTF-8");
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            int temp = 0 ;
            byte[] tempbytes = new byte[1024*1024*1024];
            while ((temp = inputStream.read(tempbytes)) != -1) { // 读取内容
                os.write(tempbytes,0,temp);
            }
            inputStream.close();
            os.flush();
            os.close();
            response.flushBuffer();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void writeExcelFile(String uuid, List<UeInfo> records) {
        String downloadPath = filePath.trim() + "/" + uuid + ".xls" ;
        File file = new File(downloadPath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        if(file.exists()) {
            try {
                POIFSFileSystem ps = new POIFSFileSystem(file);  //使用POI提供的方法得到excel的信息
                wb = new HSSFWorkbook(ps);
                sheet = getCurrentSheet(wb);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                wb = new HSSFWorkbook();
                sheet = getCurrentSheet(wb);
                createTitle(sheet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //写数据
        writeInfos(wb, sheet, records);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                wb.close();
                if(out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeInfos(HSSFWorkbook wb, HSSFSheet sheet, List<UeInfo> records) {
        //循环写入数据
        int rowIdx = sheet.getLastRowNum() + 1;
        for(int j = 0; j < records.size(); ++j) {
            HSSFRow row = sheet.createRow(rowIdx);
            UeInfo ueInfo = records.get(j);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(new HSSFRichTextString(ueInfo.getImsi()));
            cell = row.createCell(1);
            cell.setCellValue(new HSSFRichTextString(ueInfo.getImei()));
            cell = row.createCell(2);
            cell.setCellValue(new HSSFRichTextString(ueInfo.getOperator()));
            cell = row.createCell(3);
            cell.setCellValue(new HSSFRichTextString(ueInfo.getCityName()));
            cell = row.createCell(4);
            cell.setCellValue(new HSSFRichTextString(ueInfo.getSiteSn()));
            cell = row.createCell(5);
            cell.setCellValue(new HSSFRichTextString(ueInfo.getSiteName()));
            cell = row.createCell(6);
            cell.setCellValue(new HSSFRichTextString(ueInfo.getDeviceSn()));
            cell = row.createCell(7);
            cell.setCellValue(new HSSFRichTextString(ueInfo.getCaptureTime()));
            if(rowIdx == mixCount) {
                sheet = getCurrentSheet(wb);
                createTitle(sheet);
                rowIdx = 1;
            } else {
                rowIdx++;
            }
        }
    }

    public HSSFSheet getCurrentSheet(HSSFWorkbook wb) {
        int count = wb.getNumberOfSheets();
        if(count == 0) {
            return wb.createSheet();
        } else {
            int idx = -1;
            for(int j = 0; j < count; ++j) {
                HSSFSheet sheet = wb.getSheetAt(j);
                if(sheet.getLastRowNum() == mixCount) {
                    continue;
                } else {
                    idx = j;
                    break;
                }
            }
            if(idx == -1) {
                return wb.createSheet();
            } else {
                return wb.getSheetAt(idx);
            }
        }
    }

    public void createTitle(HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        //写入表头
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(new HSSFRichTextString("imsi号码"));
        cell = row.createCell(1);
        cell.setCellValue(new HSSFRichTextString("imei号码"));
        cell = row.createCell(2);
        cell.setCellValue(new HSSFRichTextString("运营商"));
        cell = row.createCell(3);
        cell.setCellValue(new HSSFRichTextString("归属地"));
        cell = row.createCell(4);
        cell.setCellValue(new HSSFRichTextString("站点编号"));
        cell = row.createCell(5);
        cell.setCellValue(new HSSFRichTextString("站点名称"));
        cell = row.createCell(6);
        cell.setCellValue(new HSSFRichTextString("设备编号"));
        cell = row.createCell(7);
        cell.setCellValue(new HSSFRichTextString("补获时间"));
    }

    /**
     * 通过用户ID查询对应站点
     * */
    public List<Site> querySiteByUserId(Long userId){

        List<Site> siteList = newHistoryMapper.querySiteByUserId(userId);
        return siteList;
    }
}
