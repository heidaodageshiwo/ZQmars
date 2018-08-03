package com.iekun.ef.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.TargetGroupMapper;
import com.iekun.ef.model.*;
import com.iekun.ef.util.DateUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.formula.functions.T;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


@Service
public class TargetGroupService {

    @Autowired
    TargetGroupMapper targetGroupMapper;

    @Autowired
    private
    SqlSessionTemplate sqlSessionTemplate;


    private static Logger logger = LoggerFactory.getLogger(ImportTargetRuleService.class);

    //获得当前时间并处理格式
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 通过用户ID查询对应站点
     * */
    public List<Site> querySiteByUserId(Long userId){

        List<Site> siteList = targetGroupMapper.querySiteByUserId(userId);
        return siteList;
    }

    /**
     * 获取分组列表
     */
    public List<TargetGroup> queryAllGroup(long userId) {
        try{
            List<TargetGroup> targetGroup = targetGroupMapper.queryAllGroup(userId);
            for (TargetGroup group : targetGroup) {
                int count = targetGroupMapper.queryGroupCount(group.getId());
                group.setCount(count);
                group.setCreate_time(sdf1.format(DateUtils.parseDate(group.getCreate_time())));
                group.setOverdue_time(sdf1.format(DateUtils.parseDate(group.getOverdue_time())));
                group.setCreator_name(targetGroupMapper.queryCreatorName(group.getCreator_id()));
            }
            return targetGroup;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 获取某个分组对应的黑名单人员信息
     */
    public List<TargetRule> queryBlacklist(String groupId) {

        List<TargetRule> targetGroup = null;
        try{
             targetGroup = targetGroupMapper.queryBlacklist(groupId);
             for(TargetRule targetRule : targetGroup){
                 targetRule.setCreateTime(sdf1.format(DateUtils.parseDate(targetRule.getCreateTime())));
             }
        }catch(Exception e){
            e.printStackTrace();
        }
        return targetGroup;

    }

    /**
     * 删除分组中一个黑名单人员
     */
    public JSONObject delGroupTarget(long targetRuleId, String imsi) {

        boolean status = false;
        JSONObject jo = new JSONObject();

        try {
            TargetRule targetRule = new TargetRule();
            targetRule.setId(targetRuleId);
            targetRule.setImsi(imsi);
            int x = targetGroupMapper.delGroupTarget(targetRule);
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
     * 通过用户ID查询对应的站点信息
     */
    public JSONObject queryAllSiteByUserId() {

        boolean status = false;
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();

        try{
            //获取用户ID
            long creator_id = SigletonBean.getUserId();
            List<Site> allSite = targetGroupMapper.queryAllSiteByUserId(creator_id);
            for (Site site : allSite) {
                JSONObject siteMessage = new JSONObject();
                siteMessage.put("id", site.getId());
                siteMessage.put("name", site.getName());
                ja.add(siteMessage);
            }
            status = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        if (status == true) {
            jo.put("status", true);
            jo.put("message", "成功");
            jo.put("data", ja);
        } else {
            jo.put("status", false);
            jo.put("message", "失败");
            jo.put("data", ja);
        }
        return jo;

    }

    /**
     * 添加分组
     */
    public JSONObject addGroup(TargetGroup targetGroup,java.lang.Long[] site) {

        boolean status = false;
        JSONObject jo = new JSONObject();

        //判断要添加的分组名字是否已存在
        List<TargetGroup> list = targetGroupMapper.queryAllGroup(SigletonBean.getUserId());
        List<String> groupNames = new ArrayList<>();
        for(TargetGroup x : list){
            groupNames.add(x.getName());
        }
        //不存在则将新建分组加入数据库
        if(groupNames.size() > 0 && groupNames.contains(targetGroup.getName())){
            jo.put("status", false);
            jo.put("message", "此新建分组名称已存在，请更换名称");
        }else{
            try {
                String uuid = UUID.randomUUID().toString().trim().replace("-", "");
                targetGroup.setId(uuid);
                long groupId = targetGroupMapper.addGroup(targetGroup);

                //groupId新建分组Id
                //site站点Ids
                if (groupId != 0) {
                    for (long sit : site) {
                        HashMap map = new HashMap();
                        map.put("siteId",sit);
                        map.put("groupId",uuid);
                        targetGroupMapper.insertSiteAndGroup(map);
                    }
                    status = true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (status == true) {
            jo.put("status", true);
            jo.put("message", "成功");
        }
        return jo;

    }

    /**
     * 通过站点ID查询站点名字
     * */
    public String querySiteNameById(long siteId){

        String siteName = targetGroupMapper.querySiteBySiteId(siteId);

        return siteName;
    }

    /**
     * 获取创建人姓名
     */
    public String queryCreatorName(long creatorId) {

            String creatorName = targetGroupMapper.queryCreatorName(creatorId);
            return creatorName;

    }

    /**
     * 删除分组
     */
    public JSONObject delGrouplist(Map map) {

        boolean status = false;
        JSONObject jo = new JSONObject();

        try {
            //删除分组
            targetGroupMapper.delGrouplist(map);
            //删除分组中的人员信息
            targetGroupMapper.delGroupPerson(map);
            //删除分组和站点的映射关系
            targetGroupMapper.delGroupSite(map);
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
     * 补录黑名单信息
     * */
    public JSONObject addOneTarget( List<Map<String, String>> receiverList){

        boolean status = false;
        JSONObject jo = new JSONObject();

       try {
           List<String> bad = new ArrayList<>();
            //判断是否存在
            for(Map<String, String> item : receiverList) {
                List<TargetRule> results = targetGroupMapper.selectCountByGroupIdAndImsi(item);
                if(results.size() > 0) {
                    bad.add(item.get("imsi"));
                }
            }
            if(bad.size() > 0) {
                jo.put("status", false);
                jo.put("bad", JSONObject.toJSON(bad));
                return jo;
            }

            for(Map<String, String> item : receiverList) {
                TargetRule record = new TargetRule();
                record.setImsi(item.get("imsi"));
                record.setName(item.get("name"));
                record.setRemark(item.get("remark"));
                record.setCreatorId(item.get("creatorId"));
                String curTime = sdf.format(new Date());
                record.setCreateTime(curTime);
                record.setUpdateTime(curTime);
                record.setIdCard(item.get("idCard"));
                record.setPhone(item.get("phone"));
                targetGroupMapper.addOneTarget(record);
            }
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
     * 导入黑名单,解析Excel
     */
    @Transactional(propagation= Propagation.REQUIRED)
    public JSONObject analyseExcel(MultipartFile file,String groupId) throws Exception {

        boolean status = false;
        JSONObject jo = new JSONObject();
        //创建Excel的处理类
        AnalyseExcel analyseExcel = new AnalyseExcel();
        //解析excel，
        List<TargetRule> targetRules = analyseExcel.getExcelInfo(file,groupId);
        //list中重复的去掉
        List<TargetRule> targetRuleList = new ArrayList<>();
        List<String> imsiList = new ArrayList<>();
        int count = 0;
        if(targetRules != null){
            for(TargetRule targetRule : targetRules){
                if(!imsiList.contains(targetRule.getImsi())){
                    imsiList.add(targetRule.getImsi());
                    targetRuleList.add(targetRule);
                }else{
                    count++;
                }
            }
        }
        int count2 = targetRuleList.size();
        //判断Excel表中有没有非法数字
        String isUnlawful = isUnlawful(targetRuleList);
        //将分组中已存在的IMSI号码过滤
        List<String> imsis = targetGroupMapper.queryAllImsiByGroupId(groupId);
        int count1 = 0;
        if(imsis.size() > 0){
            for (Iterator<TargetRule> iter =  targetRuleList.iterator(); iter.hasNext();) {
                TargetRule targetRule = (TargetRule) iter.next();
                if(imsis.contains(targetRule.getImsi())){
                    iter.remove();
                    count1++;
                }
            }
        }
        //imsi号码有没有未填写的,未填写的返回页面提示
        String isOk = isNull(targetRuleList);
        if(targetRules != null){
            if(targetRules.size() != 0){
                if(isOk.equals("")){
                    if("".equals(isUnlawful)){
                        if(count2 != count1){
                            try{
                                if(targetRuleList != null && !targetRuleList.isEmpty()){
                                    targetGroupMapper.insertExcel(targetRuleList);
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
                        jo.put("message", "导入失败,请检查Excel表中"+ isUnlawful +"的IMSI号码、身份证号码、手机号码是否含有非法数字！");
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
    public String isNull(List<TargetRule> targetRuleList){
        String isOk = "";
        for(int x = 0;x < targetRuleList.size();x++){
            if(targetRuleList.get(x).getImsi() == null || targetRuleList.get(x).getImsi().equals("")){
                isOk += "第" + (x+1) + "条,";
            }
        }
        return isOk;
    }

    /**
     * 去除重复的
     * */
    public List<TargetRule> distinct(List<TargetRule> targetRules){
        List<TargetRule> targetRuleList = new ArrayList<>();
        List<String> imsiList = new ArrayList<>();
        int count = 0;
        for(TargetRule targetRule : targetRules){
            if(!imsiList.contains(targetRule.getImsi())){
                imsiList.add(targetRule.getImsi());
                targetRuleList.add(targetRule);
            }else{
                count++;
            }
        }
        return targetRuleList;
    }

    /**
     * 判断Excel有没有非法字符
     * */
    public String isUnlawful(List<TargetRule> targetRuleList){
        String isUnlawful = new String();
        Pattern pattern = Pattern.compile("[0-9]*");
        for(int x = 0;x<targetRuleList.size();x++){
            boolean isTrue = true;
            StringBuffer sm1 = new StringBuffer();
            if(targetRuleList.get(x).getImsi() != null && !targetRuleList.get(x).getImsi().equals("")){
                isTrue = pattern.matcher(targetRuleList.get(x).getImsi()).matches();
                sm1.append(isTrue);
            }
            if(targetRuleList.get(x).getIdCard() != null && !targetRuleList.get(x).getIdCard().equals("")){
                isTrue = pattern.matcher(targetRuleList.get(x).getIdCard()).matches();
                sm1.append(isTrue);
            }
            if(targetRuleList.get(x).getPhone() != null && !targetRuleList.get(x).getPhone().equals("")){
                isTrue = pattern.matcher(targetRuleList.get(x).getPhone()).matches();
                sm1.append(isTrue);
            }
            if(targetRuleList.get(x).getImsi() != null && !targetRuleList.get(x).getImsi().equals("")){
                if(targetRuleList.get(x).getImsi().length() != 15){
                    isTrue = false;
                    sm1.append(isTrue);
                }
            }
            if(targetRuleList.get(x).getIdCard() != null && !targetRuleList.get(x).getIdCard().equals("")){
                if(targetRuleList.get(x).getIdCard().length() != 18){
                    isTrue = false;
                    sm1.append(isTrue);
                }
            }
            if(targetRuleList.get(x).getPhone() != null && !targetRuleList.get(x).getPhone().equals("")){
                if(targetRuleList.get(x).getPhone().length() != 11){
                    isTrue = false;
                    sm1.append(isTrue);
                }
            }
            if(sm1.toString().contains("false")){
                isUnlawful += "第" + (x+1) + "条数据,";
            }
        }
        return isUnlawful;
    }

    /**
     * 导入黑名单,存入数据库
     */
    public Map addBlackToList(TargetRule targetRule,Integer i) {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            int x = targetGroupMapper.addExcelTargetPerson(targetRule);
            this.clearAllTargetSyncSymbol();
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("msg","添加第"+(i+1)+"条数据时，IMSI是:"+targetRule.getImsi()+"，执行失败，请核查！；");
        }
        return resultMap;

    }

    /**
     * 加锁
     */
    public void clearAllTargetSyncSymbol() {

        List<RuleSend> targetRuleSendList = SigletonBean.targetRuleSendList;
        RuleSend ruleSend;
        for (int i = 0; i < targetRuleSendList.size(); i++) {
            ruleSend = targetRuleSendList.get(i);
            ruleSend.setSyncIndexSymbol(0);
            if (ruleSend.getSegIdSyncSymbolMap() != null) {
                ruleSend.getSegIdSyncSymbolMap().clear();
            }

            if (ruleSend.getSessionIdSegIdMap() != null) {
                ruleSend.getSessionIdSegIdMap().clear();
            }
        }

    }

    /**
     * 通过站点名字查询站点ID
     * */
    public List<Site> querySiteIdBySiteName(String nameArray){

        List<Long> Idslist = new ArrayList<Long>();
        List<Site> siteList = new ArrayList<>();
        String[] str = nameArray.split(";");
        try {
            for(String siteName : str){
               Idslist.add(targetGroupMapper.querySiteIdBySiteName(siteName));
            }
            //通过站点ID查询所有的站点信息
            for(Long siteId : Idslist){
                siteList.addAll(targetGroupMapper.querySitesBySiteId(siteId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return siteList;

    }

    /**
     * 更新分组信息
     */
    public JSONObject updateGroup(TargetGroup targetGroup,long[] site,String groupIdUp) {

        boolean status = false;
        JSONObject jo = new JSONObject();

        //判断要添加的分组名字是否已存在
        List<TargetGroup> list = targetGroupMapper.queryAllGroup(SigletonBean.getUserId());
        List<String> groupNames = new ArrayList<>();
        for(TargetGroup x : list){
            if(x.getId().equals(targetGroup.getId())){
                continue;
            }else{
                groupNames.add(x.getName());
            }
        }
        //不存在则将新建分组加入数据库
        if(groupNames.size() > 0 && groupNames.contains(targetGroup.getName())){
            jo.put("status", false);
            jo.put("message", "要更新的分组名称已存在，请更换名称");
        }else{
            try {
                //更新分组信息
                 targetGroupMapper.updateGroup(targetGroup);
                //删除分组站点映射  通过分组Id删除映射关系
                targetGroupMapper.delGroupSiteMappingByGroupId(groupIdUp);
                //site站点Ids添加分组站点映射
                if (site != null && targetGroup.getId() != null) {
                    for (long sit : site) {
                        HashMap map = new HashMap();
                        map.put("siteId",sit);
                        map.put("groupId",targetGroup.getId());
                        targetGroupMapper.insertSiteAndGroup(map);
                    }
                }
                status = true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (status == true) {
            jo.put("status", true);
            jo.put("message", "成功");
        }
        return jo;

    }


    public JSONObject querySiteIdsByGroupId(String groupId){
        JSONObject jsonObject = new JSONObject();
        Boolean status = false;

        List<Long> array = new ArrayList<>();
        try{
            array = targetGroupMapper.querySiteIdsByGroupId(groupId);
            status = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        if (status == true) {
            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
            jsonObject.put("data", array);
        } else {
            jsonObject.put("status", false);
            jsonObject.put("message", "失败");
        }

        return jsonObject;
    }

}
