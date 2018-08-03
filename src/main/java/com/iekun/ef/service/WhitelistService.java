package com.iekun.ef.service;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.WhitelistMapper;
import com.iekun.ef.model.AnalyseWhitelistExcel;
import com.iekun.ef.model.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class WhitelistService {

    @Autowired
    WhitelistMapper whitelistMapper;

    /**
     * 查询所有白名单
     * */
    public List<Whitelist> queryAll() {

        try{
            long id = SigletonBean.getUserId();
            List<Whitelist> targetGroup = whitelistMapper.queryAll(SigletonBean.getUserId());
            return targetGroup;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 添加白名单
     * */
    public JSONObject addWhitelist(Whitelist whitelist){

        boolean status = false;
        JSONObject jo = new JSONObject();

        try {
            //判断要添加的IMSI号是否已经存在
            Whitelist isExist = whitelistMapper.queryWhitelistByImsi(whitelist.getImsi());
            if(isExist != null ){
                jo.put("status", false);
                jo.put("message", "要添加白名单的imsi号码已存在!");
            }else{
                whitelistMapper.addWhitelist(whitelist);
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (status == true) {
            jo.put("status", true);
            jo.put("message", "成功");
        }
        return jo;

    }

    /**
     * 修改白名单
     * */
    public JSONObject updateWhitelist(Whitelist whitelist){

        boolean status = false;
        JSONObject jo = new JSONObject();

        //判断要更新的白名单IMSI号码是否已存在
        try{
            List<Whitelist> list = whitelistMapper.queryAll(SigletonBean.getUserId());
            List<String> imsis = new ArrayList<>();
            for(Whitelist x : list){
                if(x.getImsi().equals(whitelist.getImsi())){
                    continue;
                }else{
                    imsis.add(x.getName());
                }
            }
            //不存在则将要更新的白名单人员信息加入数据库
            if(imsis.size() > 0 && imsis.contains(whitelist.getImsi())){
                jo.put("status", false);
                jo.put("message", "要更新的白名单imsi号码已存在，请更换名称");
            }else{
                whitelistMapper.updateWhitelist(whitelist);
                status = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        if (status == true) {
            jo.put("status", true);
            jo.put("message", "成功");
        }
        return jo;

    }

    /**
     * 删除白名单
     */
    public JSONObject delWhitelist(Map map) {

        boolean status = false;
        JSONObject jo = new JSONObject();

        try {
            //删除分组
            whitelistMapper.delWhitelist(map);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (status == true) {
            jo.put("status", true);
            jo.put("message", "成功");
        } else {
            jo.put("status", false);
            jo.put("message", "失败");
        }
        return jo;

    }

    /**
     * 导入白名单信息
     * */
    @Transactional(propagation= Propagation.REQUIRED)
    public JSONObject readExcel(MultipartFile file){

        boolean status = false;
        JSONObject jo = new JSONObject();

        //创建Excel的处理类
        AnalyseWhitelistExcel analyseExcel = new AnalyseWhitelistExcel();
        //解析excel，
        List<Whitelist> whitelists = analyseExcel.getExcelInfo(file);
        //list中重复的去掉
        List<Whitelist> whitelistList = new ArrayList<>();
        List<String> imsiList = new ArrayList<>();
        int count = 0;
        if(whitelists != null){
            for(Whitelist targetRule : whitelists){
                if(!imsiList.contains(targetRule.getImsi())){
                    imsiList.add(targetRule.getImsi());
                    whitelistList.add(targetRule);
                }else{
                    count++;
                }
            }
        }
        int count2 = whitelistList.size();
        //判断Excel表中IMSI号码、手机号码有没有非法数字
        String unlawful = "";
        if(whitelists != null){
            unlawful = isUnlawful(whitelists);
        }
        //将已存在的IMSI号码过滤
        List<String> imsis = whitelistMapper.queryAllImsiByUserId(SigletonBean.getUserId());
        int count1 = 0;
        if(imsis.size() > 0){
            for (Iterator<Whitelist> iter = whitelistList.iterator(); iter.hasNext();) {
                Whitelist whitelist = (Whitelist) iter.next();
                if(imsis.contains(whitelist.getImsi())){
                    iter.remove();
                    count1++;
                }
            }
        }
        //imsi号码有没有未填写的,未填写的返回页面提示
        String isOk = isNull(whitelistList);
        if(whitelists != null){
            if(whitelists.size() != 0){
                if(isOk.equals("")){
                    if("".equals(unlawful)){
                        if(count2 != count1){
                            try{
                                if(whitelists != null && !whitelists.isEmpty()){
                                    whitelistMapper.insertExcel(whitelistList);
                                    status = true;
                                }
                            }catch(Exception e){
                                status = false;
                                e.printStackTrace();
                            }
                            if(true == status){
                                if(count1 > 0 && count == 0){
                                    jo.put("status", true);
                                    jo.put("message", "有"+ count1 +"条数据的IMSI号码在此分组中已存在，已为您过滤，其余上传成功");
                                }else if(count1 > 0 && count > 0){
                                    jo.put("status", true);
                                    jo.put("message", "有"+ count +"条数据在Excel表中重复,有"+ count1 +"条数据的IMSI号码在此分组中已存在，已为您过滤，其余上传成功");
                                }else if(count1 == 0 && count == 0){
                                    jo.put("status", true);
                                    jo.put("message", "上传成功");
                                }else if(count1 == 0 && count > 0){
                                    jo.put("status", true);
                                    jo.put("message", "有"+ count +"条数据在Excel表中重复已为您过滤，其余上传成功");
                                }
                            }else{
                                jo.put("status", false);
                                jo.put("message", "上传失败,Excel表中含有异常数据！");
                            }
                        }else{
                            jo.put("status", false);
                            jo.put("message",  "上传失败，要添加的数据IMSI号码在此分组中都已经存在");
                        }
                    }else{
                        jo.put("status", false);
                        jo.put("message", "导入失败,请检查Excel表中"+ unlawful +"的IMSI号码、手机号码是否含有非法数字！");
                    }
                }else{
                    jo.put("status", false);
                    jo.put("message", "导入失败," + isOk + "数据IMSI号码未填写!" );
                }

            }else{
                jo.put("status", false);
                jo.put("message", "导入失败,请核查Excel中数据是否正确!" );
            }
        }else{
            jo.put("status", false);
            jo.put("message", "导入失败,请上传指定的Excel文件!" );
        }
        return jo;
    }

    /**
     * 判断imsi号码有没有未填写的
     * */
    public String isNull(List<Whitelist> whitelistList){
        String isOk = "";
        for(int x = 0;x < whitelistList.size();x++){
            if(whitelistList.get(x).getImsi() == null || whitelistList.get(x).getImsi().equals("")){
                isOk += "第" + (x+1) + "条,";
            }
        }
        return isOk;
    }

    /**
     *判断Excel表中IMSI号码、手机号码有没有非法数字
     * */
    public String isUnlawful(List<Whitelist> whitelists){

        String unlawful = new String();
        Pattern pattern = Pattern.compile("[0-9]*");
        for(int x = 0;x<whitelists.size();x++){
            boolean isTrue = true;
            StringBuffer sm1 = new StringBuffer();
            if(whitelists.get(x).getImsi() != null && !whitelists.get(x).getImsi().equals("")){
                isTrue = pattern.matcher(whitelists.get(x).getImsi()).matches();
                sm1.append(isTrue);
            }
            if(whitelists.get(x).getPhone() != null && !whitelists.get(x).getPhone().equals("")){
                isTrue = pattern.matcher(whitelists.get(x).getPhone()).matches();
                sm1.append(isTrue);
            }
            if(whitelists.get(x).getImsi() != null && !whitelists.get(x).getImsi().equals("")){
                if(whitelists.get(x).getImsi().length() != 15){
                    isTrue = false;
                    sm1.append(isTrue);
                }
            }
            if(whitelists.get(x).getPhone() != null && !whitelists.get(x).getPhone().equals("")){
                if(whitelists.get(x).getPhone().length() != 11){
                    isTrue = false;
                    sm1.append(isTrue);
                }
            }
            if(sm1.toString().contains("false")){
                unlawful += "第" + (x+1) + "条数据,";
            }
        }
        return unlawful;

    }


}
