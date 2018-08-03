package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.SiteUser;
import com.iekun.ef.model.TreeNode;
import com.iekun.ef.service.DeviceService;
import com.iekun.ef.service.SiteGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by java1 on 2017/11/28 0028.
 */
@Controller
@RequestMapping("/sitegroupcontroller")
public class SiteGroupController {

    @Autowired
     SiteGroupService    siteGroupService;

    @Autowired
    private DeviceService  deviceService;
    /**
     * 获取zTree
     * */
    @RequestMapping("/queryZtree")
    @ResponseBody
    public List<TreeNode> queryZtree(
            @RequestParam(value = "id", required = true ) Long id

    ){

        //要返回的zTree
        List<TreeNode> list = new ArrayList<TreeNode>();
        //得到所有站点
        List<Site> siteList = siteGroupService.querySiteByUserId(id);
        List<Long>  province= new ArrayList<>();
        for(Site site: siteList){
            if(!province.contains(site.getProvince_id())){
                province.add(site.getProvince_id());
                list.add(new TreeNode(site.getProvince_id().toString(),"0",site.getProvince_name(),true,false,false));//省份
            }
        }
        List<Long> city = new ArrayList<>();
        for(Site site : siteList){
            if(!city.contains(site.getCity_id())){
                city.add(site.getCity_id());
                list.add(new TreeNode(site.getCity_id().toString(),site.getProvince_id().toString(),site.getCity_name(),true,false,false));//城市
            }
        }
        List<Long> towns = new ArrayList<>();
        for(Site site : siteList){
            if(!towns.contains(site.getTown_id())){
                towns.add(site.getTown_id());
                list.add(new TreeNode(site.getTown_id().toString(),site.getCity_id().toString(),site.getTown_name(),true,false,false));//区域
            }
        }
        List<Long> device=new ArrayList<>();
        for(Site site : siteList){
            if(!device.contains(site.getId())){
                device.add(site.getId());
                list.add(new TreeNode(site.getId().toString(),site.getTown_id().toString(),site.getName(),true,false,false));//站点
            }
        }
        /*List<SiteUser> siteUserList = siteGroupService.querysiteUserList(id);
        List<Long> device=new ArrayList<>();
        for(Site site : siteList){


                for(SiteUser siteuser:siteUserList) {
                    if(!device.contains(site.getId())){
                    device.add(site.getId());
                    if(siteuser.getSiteId().toString().equals(site.getId().toString())){
                        list.add(new TreeNode(site.getId().toString(), site.getTown_id().toString(), site.getName(), true, false, true));//站点
                    }else{
                        list.add(new TreeNode(site.getId().toString(), site.getTown_id().toString(), site.getName(), true, false, false));//站点
                    }
                }
            }
        }*/


       /* //通过userid查询出这个用户下的设备

        List<SiteUser> siteUserList = siteGroupService.querysiteUserList(id);

        for(SiteUser siteuser:siteUserList){
            for(TreeNode treenode:list){
                if(siteuser.getSiteId().toString().equals(treenode.getId().toString())){

                }
            }
        }*/
        List<TreeNode> list1 = new ArrayList<TreeNode>();
        List<SiteUser> siteUserList = siteGroupService.querysiteUserList(id);
       for(int i=0;i<list.size();i++){
           for(int j=0;j<siteUserList.size();j++){
               if(list.get(i).getId().toString().equals(siteUserList.get(j).getSiteId().toString())){
                    String id1=list.get(i).getId();
                      String pid=  list.get(i).getpId();
                       String name= list.get(i).getName();
                        list.remove(i);
                        i--;
                        list1.add(new TreeNode(id1,pid,name,true,false,true));
                   //list.add(new TreeNode(id1,pid,name,true,false,true));//站点
               }
           }
       }
    for(int o=0;o<list1.size();o++){
           list.add(list1.get(o));
    }
    //根据siteUserList查询出省
        List<Long> PRO=new ArrayList<>();
        List<TreeNode> list2 = new ArrayList<TreeNode>();

        for(int i=0;i<siteList.size();i++){
            for(int j=0;j<siteUserList.size();j++){
                if(siteList.get(i).getId().toString().equals(siteUserList.get(j).getSiteId().toString())){


                    if(!PRO.contains(siteList.get(i).getProvince_id())){
                    PRO.add(siteList.get(i).getProvince_id());
                    String id1=siteList.get(i).getProvince_id().toString();
                    String name=siteList.get(i).getProvince_name().toString();
                   //   list.remove(i);
                    list2.add(new TreeNode(id1,"0",name,true,false,true));
                    }

                }
            }
    }
        for(int i=0;i<list2.size();i++){
            for(int j=0;j<list.size();j++){
                if(list.get(j).getId().equals(list2.get(i).getId())){
                    list.remove(j);

                }
            }
        }
        for(int o=0;o<list2.size();o++){
            list.add(list2.get(o));
        }
//根据siteUserList查询出城市
        List<Long> CITY=new ArrayList<>();
        List<TreeNode> list3 = new ArrayList<TreeNode>();

        for(int i=0;i<siteList.size();i++){
            for(int j=0;j<siteUserList.size();j++){
                if(siteList.get(i).getId().toString().equals(siteUserList.get(j).getSiteId().toString())){


                    if(!CITY.contains(siteList.get(i).getCity_id())){
                        CITY.add(siteList.get(i).getCity_id());
                        String id1=siteList.get(i).getCity_id().toString();
                        String city1=siteList.get(i).getProvince_id().toString();
                        String name=siteList.get(i).getCity_name().toString();
                       // list.add(new TreeNode(id1,city1,name,true,false,false));//城市

                        //   list.remove(i);
                        list3.add(new TreeNode(id1,city1,name,true,false,true));
                    }

                }
            }
        }

        for(int i=0;i<list3.size();i++){
            for(int j=0;j<list.size();j++){
                if(list.get(j).getId().equals(list3.get(i).getId())){
                    list.remove(j);

                }
            }
        }
        for(int o=0;o<list3.size();o++){
            list.add(list3.get(o));
        }

//////////////////////////////////////
        //根据siteUserList查询出区域
        List<Long> TOWN=new ArrayList<>();
        List<TreeNode> list4 = new ArrayList<TreeNode>();

        for(int i=0;i<siteList.size();i++){
            for(int j=0;j<siteUserList.size();j++){
                if(siteList.get(i).getId().toString().equals(siteUserList.get(j).getSiteId().toString())){


                    if(!TOWN.contains(siteList.get(i).getTown_id())){
                        TOWN.add(siteList.get(i).getTown_id());
                        String id1=siteList.get(i).getTown_id().toString();
                        String city1=siteList.get(i).getCity_id().toString();
                        String name=siteList.get(i).getTown_name().toString();
                        // list.add(new TreeNode(id1,city1,name,true,false,false));//城市
                       // list.add(new TreeNode(site.getTown_id().toString(),site.getCity_id().toString(),site.getTown_name(),true,false,false));//区域

                        //   list.remove(i);
                        list4.add(new TreeNode(id1,city1,name,true,false,true));
                    }

                }
            }
        }

        for(int i=0;i<list4.size();i++){
            for(int j=0;j<list.size();j++){
                if(list.get(j).getId().equals(list4.get(i).getId())){
                    list.remove(j);

                }
            }
        }
        for(int o=0;o<list4.size();o++){
            list.add(list4.get(o));
        }
        return list;
    }

    @RequestMapping("/update")
    @ResponseBody
    public JSONObject queryZtree1(
            @RequestParam(value = "id", required = false ) String id,
            @RequestParam(value = "name", required = false ) String name,
            @RequestParam(value = "pid", required = false ) String pid,
            @RequestParam(value = "checked", required = false ) String checked,
            @RequestParam(value = "userid", required = false ) String userid

    ) {
        JSONObject jb=new JSONObject();

    //通过id查询站点是否是有数据  如果为true:1删除关联数据 2重新插入false 删除关联数据

        if(checked.equals("true")){
            List<String> list = getList(id);
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("userid",userid);
            map.put("list",list);
            siteGroupService.deleteData(map);
            List<SiteUser> sitelist= getListModel(id,userid);
            siteGroupService.insertData(sitelist);
            setGroup(id,userid,checked);
        }else{
            List<String> list = getList(id);
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("userid",userid);
            map.put("list",list);
            siteGroupService.deleteData(map);
            setGroup(id,userid,checked);
        }
        /*List<String> list = getList(id);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("userid",userid);
        map.put("list",list);
        siteGroupService.deleteData(map);*/

        /*List<SiteUser> sitelist= getListModel(id,userid);
        siteGroupService.insertData(sitelist);*/

/*
        if(checked.equals("true")){

            List<String> list = getList(id);
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("userid",userid);
            map.put("list",list);
            siteGroupService.deleteData(map);
           // System.out.println(id);
            String c[]=id.split(",");
            for(int i=0;i<c.length;i++){
                siteGroupService.deleteData(c[i],userid);
                siteGroupService.insertData(c[i],userid);
            }
        }else{
            String c[]=id.split(",");
            for(int i=0;i<c.length;i++){
                System.out.println(c[i]+"-------"+userid);
                siteGroupService.deleteData(c[i],userid);
            }
        }*/
        jb.put("status", true);
        jb.put("message", "站点分配成功");

         return  jb;
    }

    public List<String> getList(String id) {
        List<String> list = new ArrayList<String>();
        String[] str = id.split(",");
        for (int i = 0; i < str.length; i++) {
            list.add(str[i]);
        }
        return list;
    }
    public List<SiteUser> getListModel(String id, String userid) {
        List<SiteUser> list = new ArrayList<SiteUser>();
        String[] str = id.split(",");
        for (int i = 0; i < str.length; i++) {
            SiteUser site=new SiteUser();
            site.setSiteId(Long.parseLong(str[i]));
            site.setUserId(Long.parseLong(userid));
//            site.setSn(str[i]);
//            site.setName(userid);
            list.add(site);
        }
        return list;
    }


    public void setGroup(String id, String userid,String checked){
        List<SiteUser> list = new ArrayList<SiteUser>();
        String[] str = id.split(",");
        for (int i = 0; i < str.length; i++) {
            SiteUser site=new SiteUser();
            site.setSiteId(Long.parseLong(str[i]));
            site.setUserId(Long.parseLong(userid));
            if(checked.equals("true")){
                site.setDeleteFlag(0);
            }else{
                site.setDeleteFlag(1);
            }

           deviceService.updateDeviceUseMaps(site);
//            site.setSn(str[i]);
//            site.setName(userid);
           // list.add(site);
        }
    }
}
