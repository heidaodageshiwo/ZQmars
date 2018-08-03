package com.iekun.ef.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.util.TimeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by feilong.cai on 2016/10/26.
 */

public class NoticeWebSocketHandler extends TextWebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(NoticeWebSocketHandler.class);

    private static List<WebSocketSession> users = new ArrayList<WebSocketSession>();
    
    public static List<WebSocketSession> getUsers() {
		return users;
	}

	public static void setUsers(List<WebSocketSession> users) {
		NoticeWebSocketHandler.users = users;
	}

	private Long closeUserId = new Long(0);
    
    @Override
    public synchronized void afterConnectionEstablished( WebSocketSession session) throws Exception {

        logger.info("webSocket connection established.");
        
       
        Long userId = (Long) session.getAttributes().get("WEBSOCKET_USERID");
       /* boolean findUserId = false;
        for ( WebSocketSession user : users)
        {
        	
        	if (userId == ((Long)user.getAttributes().get( "WEBSOCKET_USERID")))
        	{   
        		findUserId = true;
        		break;
        	}
        }*/
        //session.getAcceptedProtocol()
      /*  if (findUserId == false)
        {
        	 users.add(session);
			 logger.info("webSocket connection established, userId: " + userId);
			 String dateMsg = this.getServDateTime();
			 this.sendMessageToUser(userId, new TextMessage( dateMsg ));
        }*/
        
        users.add(session);
		logger.info("webSocket connection established, userId: " + userId);
		logger.info("webSocket connection established, users size: " + users.size());
		String dateMsg = this.getServDateTime();
		this.sendMessageToUser(userId, new TextMessage( dateMsg ));
       
         //// TODO: 2016/10/27
    }

    @Override
    public  void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        //// TODO: 2016/10/27  
    }

    @Override
    public  void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {
        if(session.isOpen()){
            session.close(CloseStatus.SERVER_ERROR);
        }
        logger.info("error webSocket connection closed.");
        users.remove(session);
    }

    @Override
    public  void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
    	logger.info("目前在线用户"+users.size() + "close reason " + status.getReason());
    	logger.info("afterConnectionClosed current thread " + Thread.currentThread().getName());
    	if(session.isOpen()){
    		//logger.info("==== afterConnectionClosed  userId :" + session.getAttributes().get("WEBSOCKET_USERID"));
            session.close(CloseStatus.NORMAL);
        }
    	logger.info(" before closed 剩余在线用户:" + users.size());
    	
    	Long userId = (Long) session.getAttributes().get("WEBSOCKET_USERID");
    	
    	Iterator<WebSocketSession> it = users.iterator();
    	while(it.hasNext()){
    		WebSocketSession user = it.next();
    	    if(userId == ((Long)user.getAttributes().get( "WEBSOCKET_USERID"))){
    	        it.remove();
    	    }
    	}
    	
        //this.closeUserId = (Long)session.getAttributes().get("WEBSOCKET_USERID");
        //logger.info("after webSocket connection closed.");
        logger.info(" after closed 剩余在线用户:" + users.size());
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public synchronized void sendMessageToUsers( TextMessage message) {
       
    	if (users.size() == 0)
    	{
    		//logger.info(" users size is 0!");
    		return;
    	}
    	/*else
    	{
    		logger.info("发送的用户个数: "+ users.size() + "  ===user id : " + users.get(0).getAttributes().get("WEBSOCKET_USERID"));
    	}*/
    	
        for ( WebSocketSession user : users) {
        	
            try {
                if (user.isOpen()) {
                	//logger.info("=====sendMessageToUsers current thread " + Thread.currentThread().getName());
                	//logger.info("====send user userId :" + user.getAttributes().get("WEBSOCKET_USERID"));
                    /*user.sendMessage(message);*/
                	this.sendMsgToUser(user, message);
                }
            } catch ( Exception e ) {
               	try {
            		logger.info("====close user userId :" + user.getAttributes().get("WEBSOCKET_USERID"));
					user.close();
					users.remove(user);
				} catch (Exception e1/*IOException e1*/) {
					// TODO Auto-generated catch block
					logger.info("web socket close fail !");
					e1.printStackTrace();
				}
            	e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userId
     * @param message
     */
    public synchronized void sendMessageToUser( Long userId , TextMessage message) {
    	if (users.size() == 0)
    	{
    		return;
    	}
        for (WebSocketSession user : users) {
        	        	
            if ( userId == ((Long)user.getAttributes().get( "WEBSOCKET_USERID")) ) {
                try {
                    if (user.isOpen()) {
                    	//logger.info("=====22sendMessageToUser current thread " + Thread.currentThread().getName());
                    	//logger.info("====22send user userId :" + user.getAttributes().get("WEBSOCKET_USERID"));
                        /*user.sendMessage(message);*/
                    	this.sendMsgToUser(user, message);
                    }
                } catch (Exception e) {
                	try {
                    	logger.info("====22close user userId :" + user.getAttributes().get("WEBSOCKET_USERID"));
						user.close();
						users.remove(user);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						logger.info("22web socket close fail !");
						e1.printStackTrace();
					}
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * 获取当前链接用户ID
     * @return
     */
    public List<Long> getWebSocketUserId() {
        List<Long> userIdList = new LinkedList<>();
        for (WebSocketSession user : users) {

            Long userId = ((Long)user.getAttributes().get( "WEBSOCKET_USERID"));
            userIdList.add( userId );
        }

        return userIdList;
    }
    
    
    public synchronized void sendMsgToUser(WebSocketSession user, TextMessage message)
    {
    	try {
			user.sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private String getServDateTime()
    {
    	JSONObject textMsgJsonObj = new JSONObject();
        textMsgJsonObj.put("type", "serverTimeNotify");
        textMsgJsonObj.put("data", TimeUtils.timeFormatter.format( new Date()));
        String textMsg = textMsgJsonObj.toJSONString();
        return textMsg;
    }
    

}
