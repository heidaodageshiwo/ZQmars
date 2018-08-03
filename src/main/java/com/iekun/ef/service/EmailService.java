package com.iekun.ef.service;

import com.iekun.ef.dao.EmailTemplateMapper;
import com.iekun.ef.dao.OutboxMapper;
import com.iekun.ef.model.EmailTemplate;
import com.iekun.ef.model.Outbox;
import com.iekun.ef.util.TimeUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * Created by feilong.cai on 2017/1/2.
 */

@Service
public class EmailService {

    private static Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private EmailTemplateMapper emailTemplateMapper;

    @Autowired
    private OutboxMapper  outboxMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailForm;

    /**
     * 创建模板
     * @param emailTemplate , 邮件模板实体
     * @return  0-正常，1失败
     */
    public  int createTemplate(EmailTemplate emailTemplate) {

        try{
            emailTemplateMapper.insertEmailTemplate( emailTemplate );
        } catch ( Exception e ){
            e.printStackTrace();
            return  1;
        }
        return 0;
    }

    /**
     * 删除模板
     * @param id , 数据库主键ID
     * @return 0-正常，1失败
     */
    public int deleteTemplate( Long id ) {
        try {
            emailTemplateMapper.updateDeleteFlagById( id );
        }catch ( Exception e ){
            e.printStackTrace();
            return  1;
        }
        return 0;
    }

    /**
     * 更新模板
     * @param emailTemplate , 邮件模板实体
     * @return 0-正常，1失败
     */
    public int updateTemplate( EmailTemplate emailTemplate ){
        try {
            emailTemplateMapper.updateEmailTemplate( emailTemplate );
        }catch ( Exception e ){
            e.printStackTrace();
            return  1;
        }
        return 0;
    }


    public int updateTemplate( Long id, String  emailSubject, String  contentTp,
                               Integer  eventType, String  emailRemark){
        int result = 1;
        try {

            EmailTemplate emailTpl = emailTemplateMapper.getEmailTemplateById( id );
            if( null != emailTpl ){

                emailTpl.setSubject( emailSubject );
                emailTpl.setContentTpl( contentTp );
                emailTpl.setEventType( eventType );
                emailTpl.setRemark( emailRemark );
                String updateTime = TimeUtils.timeFormatterStr.format(new Date());
                emailTpl.setUpdateTime( updateTime );

                emailTemplateMapper.updateEmailTemplate( emailTpl );
                result = 0;
            }

        }catch ( Exception e) {
            e.printStackTrace();
        }

        return result;
    }



    /**
     * 获取模板个数
     * @return 个数
     */
    public Long getTemplateCount(){
        Long count = new Long(0);
        try {
            count = emailTemplateMapper.getEmailTemplateCount();
        }catch ( Exception e ){
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 获取模板列表
     * @param start
     * @param length
     * @return 模板列表
     */
    public List<EmailTemplate> getTemplates(Integer start, Integer length ) {
        List<EmailTemplate> templates = null;
        try{
            templates = emailTemplateMapper.getEmailTemplates( start, length );
        } catch ( Exception e ) {
            e.printStackTrace();
            templates = new LinkedList<>();
        }
        return templates;
    }

    /**
     * 获取全部模板
     * @return 模板列表
     */
    public List<EmailTemplate> getTemplates() {
        List<EmailTemplate> templates = null;
        try{
            templates = emailTemplateMapper.getEmailTemplateAll( );
        } catch ( Exception e ) {
            e.printStackTrace();
            templates = new LinkedList<>();
        }
        return templates;
    }

    /**
     * 启用指定模板
     * @param id  模板主键Id
     * @return 0-正常，1失败
     */
    public int activeTemplate( Long id ) {
        try{
            EmailTemplate template = emailTemplateMapper.getEmailTemplateById( id );
            emailTemplateMapper.updateEmailTemplateActiveById( id );
            emailTemplateMapper.updateEmailTemplateInActiveByIdAndType( id, template.getEventType() );
        } catch ( Exception e ) {
            e.printStackTrace();
            return  1;
        }
        return 0;
    }

    /**
     * 停用指定模板
     * @param id 模板主键Id
     * @return 0-正常，1失败
     */
    public int inactiveTemplate( Long id ) {
        try{
            emailTemplateMapper.updateEmailTemplateInactiveById( id );
        } catch ( Exception e ) {
            e.printStackTrace();
            return  1;
        }
        return 0;
    }

    /**
     * 历史邮件个数
     * @param startTime 开始时间
     * @param endTime  结束时间
     * @param email  邮件地址
     * @return 个数
     */
    public  long  queryHistoryCount( String startTime, String endTime,String email ){
        long historyCount = 0 ;
        try{
            HashMap<String, Object> params = new HashMap<>();
            if( !startTime.isEmpty() ){
                startTime = TimeUtils.convertDateSepToNoSep(startTime);
                params.put("startTime", startTime);
            }
            if( !endTime.isEmpty() ){
                endTime = TimeUtils.convertDateSepToNoSep(endTime);
                params.put("endTime", endTime);
            }
            if( !email.isEmpty() ){
                params.put("notifyEmail", email);
            }
            historyCount = outboxMapper.getEmailCount(params);
        } catch ( Exception e ){
            e.printStackTrace();
        }

        return historyCount;
    }

    /**
     * 历史邮件列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param email  邮件地址
     * @return 历史邮件列表
     */
    public List<Outbox> queryHistory( String startTime, String endTime,
                                      String email ){

        List<Outbox> emails = null;
        try{
            HashMap<String, Object> params = new HashMap<>();
            if( !startTime.isEmpty() ){
                startTime = TimeUtils.convertDateSepToNoSep(startTime);
                params.put("startTime", startTime);
            }
            if( !endTime.isEmpty() ){
                endTime = TimeUtils.convertDateSepToNoSep(endTime);
                params.put("endTime", endTime);
            }
            if( !email.isEmpty() ){
                params.put("notifyEmail", email);
            }
            emails = outboxMapper.queryEmailHistory(params);
            //emails = outboxMapper.queryHistory(params);
        } catch ( Exception e ) {
            e.printStackTrace();
            emails = new LinkedList<>();
        }

        return emails;
    }

    /**
     * 根据ID获取邮件详情
     * @param id
     * @return
     */
    public Outbox getHistoryEmailDetail( Long id ) {
        Outbox email = null;
        try {
            email = outboxMapper.getEmail(id);
        } catch ( Exception e ){
            e.printStackTrace();
        }
        return email;
    }

    /**
     * 创建邮件发送队列
     * @param username 接收人姓名
     * @param to  接收人邮件地址
     * @param params  模板替换参数
     * @param eventType  触发事件类型
     * @return true-成功，false-失败
     */
    public boolean createEmail( String username, String to, Map<String, Object> params, Integer eventType ) {

        try{

            EmailTemplate template = emailTemplateMapper.getEmailTemplateByEventType(eventType);
            if( null == template ){
                logger.info("have not a active email template.");
                return false;
            }
            String contentTpl = template.getContentTpl();
            String subject = template.getSubject();
            Configuration cfg = new Configuration();
            Template freeMarkTpl = new Template( subject, contentTpl, cfg );
            String htmlContent =  FreeMarkerTemplateUtils.processTemplateIntoString( freeMarkTpl, params);

            String createTime =  TimeUtils.timeFormatterStr.format(new Date());

            Outbox outbox = new Outbox( username, to, (byte)1, (byte)(eventType.intValue()), (byte)0, createTime, htmlContent, subject );
            outboxMapper.insert(outbox);

        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 从队列中发送邮寄
     * @return 0-成功，1-失败
     */
    public int sendEmail() {

        try{

            HashMap<String, Object> params = new HashMap<String, Object>();;
            params.put("status",  0 );
            List<Outbox> emails = outboxMapper.getEmails(params);

            for ( Outbox email: emails ) {

                try{
                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setFrom( this.emailForm );
                    helper.setTo( email.getNotifyEmail() );
                    helper.setSubject( email.getEmailSubject() );
                    helper.setText( email.getContent(), true);
                    mailSender.send(mimeMessage);

                    email.setLastSendTime(TimeUtils.timeFormatterStr.format(new Date()));
                    email.setStatus( (byte)1);

                } catch ( Exception e ){
                    e.printStackTrace();

                    email.setLastSendTime(TimeUtils.timeFormatterStr.format(new Date()));
                    email.setStatus((byte)2);
                }

                outboxMapper.updateEmail(email);

            }


        } catch ( Exception e ){
            e.printStackTrace();
            return 1;
        }
        return  0;
    }

    /**
     * 获取发送地址
     * @return 邮件地址
     */
    public String getEmailForm(){
        return this.emailForm;
    }

}
