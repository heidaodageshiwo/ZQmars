package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iekun.ef.dao.DataTablePager;
import com.iekun.ef.model.Whitelist;
import com.iekun.ef.service.WhitelistService;
import com.iekun.ef.util.M16Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/whitelist")
public class WhitelistController {

    @Autowired
    WhitelistService whitelistService;


    /**
     * 查询所有白名单
     * */
    @RequestMapping("/queryAll")
    @ResponseBody
    public JSONObject queryAllGroup(HttpServletRequest request){

        //分页处理
        int iDisplayStart = M16Tools.stringToNumber(request.getParameter("iDisplayStart"));
        int iDisplayLength = M16Tools.stringToNumber(request.getParameter("iDisplayLength"));
        String sEcho = request.getParameter("sEcho");
        int startNum = iDisplayStart / iDisplayLength + 1;

        PageHelper.startPage(startNum, iDisplayLength);
        List<Whitelist> targetGroups =  whitelistService.queryAll();
        PageInfo pg = new PageInfo(targetGroups);
        DataTablePager page = new DataTablePager();
        page.setDataResult(targetGroups);
        page.setiTotalRecords(pg.getTotal());
        page.setiTotalDisplayRecords(pg.getTotal());
        page.setiDisplayLength(iDisplayLength);
        page.setsEcho(sEcho);

        return (JSONObject)JSONObject.toJSON(page);

    }

    /**
     * 添加白名单
     * */
    @RequestMapping("/addWhitelist")
    @ResponseBody
    public JSONObject addWhitelist(String imsi,
                                    String phone,
                                    String name,
                                    String operator,
                                    String ownership,
                                    String organization){

        JSONObject jsonObject = new JSONObject();
        Whitelist whitelist = new Whitelist();
        String uuid = UUID.randomUUID().toString().trim().replace("-", "");
        whitelist.setId(uuid);
        whitelist.setImsi(imsi);
        whitelist.setPhone(phone);
        whitelist.setName(name);
        whitelist.setOperator(operator);
        whitelist.setOwnership(ownership);
        whitelist.setOrganization(organization);
        whitelist.setCreatorId(SigletonBean.getUserId());
        jsonObject = whitelistService.addWhitelist(whitelist);

        return jsonObject;

    }

    /**
     * 修改白名单
     * */
    @RequestMapping("/updateWhitelist")
    @ResponseBody
    public JSONObject addWhitelist(String upid,
                                    String upimsi,
                                    String upphone,
                                    String upname,
                                    String upoperator,
                                    String upownership,
                                    String uporganization){

        JSONObject jsonObject = new JSONObject();
        Whitelist whitelist = new Whitelist();
        whitelist.setId(upid);
        whitelist.setImsi(upimsi);
        whitelist.setPhone(upphone);
        whitelist.setName(upname);
        whitelist.setOperator(upoperator);
        whitelist.setOwnership(upownership);
        whitelist.setOrganization(uporganization);
        jsonObject = whitelistService.updateWhitelist(whitelist);

        return jsonObject;

    }

    /**
     * 删除白名单
     * */
    @RequestMapping("/delWhitelist")
    @ResponseBody
    public JSONObject delWhitelist(String ids) {

        JSONObject jsonObject = new JSONObject();
        List<String> list = (List<String>)JSONObject.parse(ids);
        Map map = new HashMap();
        map.put("whitelistIds", list);
        jsonObject = whitelistService.delWhitelist(map);

        return jsonObject;

    }

    /**
     * 导入黑名单
     * */
    @RequestMapping(value = "/readExcel", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addExcelTarget(@RequestParam("uploadFile") MultipartFile uploadFile) {

        JSONObject jsonObject = new JSONObject();
        boolean isUploadSucc = false;
        try{
            jsonObject = whitelistService.readExcel(uploadFile);
        }catch(Exception e){
            e.printStackTrace();
        }

        return jsonObject;

    }

}
