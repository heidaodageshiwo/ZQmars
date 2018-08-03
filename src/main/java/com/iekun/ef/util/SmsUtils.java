package com.iekun.ef.util;

import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("deprecation")
@Component("smsUtils")
@Scope("singleton")
public class SmsUtils {

	private static Logger logger = LoggerFactory.getLogger(SmsUtils.class);
	
	@Value("${httpSmsSend.ip}")  
    private String httpIp;
	
	@Value("${httpSmsSend.port}")   
    private String httpPort;
	
	@Value("${httpSmsSend.gateway}")
    private String httpGateway;
	
	@Value("${httpSmsSend.password}")
    private String httpPassword;
	
	@Value("${httpSmsSend.encoding}")
    private String httpEncoding;
	
	@SuppressWarnings({ "resource" })
	public synchronized boolean httpSendMessage(String msg, String phoneNumber)throws Exception{
		
		try{
		
			String returnUrl;
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
	        requestFactory.setConnectTimeout(1000);
	        requestFactory.setReadTimeout(1000);

	        RestTemplate restTemplate = new RestTemplate(requestFactory);
	        returnUrl = this.getUrl(msg, phoneNumber);
	        restTemplate.put(returnUrl ,null); 
	        logger.info("短信发送：" + returnUrl);
	        
	        //logger.info("send phoneNumber: "+ phoneNumber + "return status:" +  clientResp.getStatusLine().getStatusCode());
		}catch(Exception e){
			logger.info("短信发送失败：" + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	@SuppressWarnings({ "resource" })
	public synchronized boolean httpSendMessageClient(String msg, String phoneNumber)throws Exception{
		
		try{
			HttpClient httpclient = new DefaultHttpClient();  
	        HttpGet httpgets = new HttpGet(this.getUrl(msg, phoneNumber));    
	        //httpclient.execute(httpgets); 
	        HttpResponse clientResp = httpclient.execute(httpgets); 
	        //clientResp.getStatusLine().getStatusCode();
	        logger.info("send phoneNumber: "+ phoneNumber + "return status:" +  clientResp.getStatusLine().getStatusCode());
		}catch(Exception e){
			logger.info("短信发送失败：" + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * 拼接http请求URL
	 * @param msg
	 * @param phoneNumber
	 * @return
	 * @throws Exception
	 */
	private String getUrl(String msg, String phoneNumber)throws Exception{
		String returnUrl =  "http://" + httpIp + ":" + httpPort + "/send?gateway=" + httpGateway + "&password=" + httpPassword + "&encoding=" + httpEncoding + "&recipient=" + phoneNumber + "&text=" + URLEncoder.encode(msg, "UTF-8");
	    return returnUrl;
	}
}
