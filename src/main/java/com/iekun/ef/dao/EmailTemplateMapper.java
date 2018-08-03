package com.iekun.ef.dao;

import com.iekun.ef.model.EmailTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by feilong.cai on 2017/1/1.
 */
public interface EmailTemplateMapper {

    //获取模板个数
    Long  getEmailTemplateCount();
    //添加邮件模板
    int insertEmailTemplate( EmailTemplate emailTemplate );
    //更新邮件模板
    int updateEmailTemplate( EmailTemplate emailTemplate );
    //根据ID获取模板
    EmailTemplate getEmailTemplateById( @Param("id") Long id );
    //获取当前启用的触发事件类型邮件模板
    EmailTemplate getEmailTemplateByEventType( @Param("eventType") Integer eventType );
    //获取邮件模板列表
    List<EmailTemplate> getEmailTemplates( @Param("start") Integer start, @Param("length") Integer length );
    //获取邮件模板列表
    List<EmailTemplate> getEmailTemplateAll( );
    //标记软删除
    int updateDeleteFlagById(  @Param("id") Long id );
    //根据ID启用模板
    int updateEmailTemplateActiveById( @Param("id") Long id  );
    //根据ID停用模板
    int updateEmailTemplateInactiveById( @Param("id") Long id  );
    //停止当前ID模板相关的其他模板
    int updateEmailTemplateInActiveByIdAndType( @Param("id") Long id, @Param("eventType") Integer eventType );

}
