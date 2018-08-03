package com.iekun.ef.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by feilong.cai  on 2016/10/26.
 */

public class RealTimeDataWebSocketHandler extends TextWebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(RealTimeDataWebSocketHandler.class);

    private static final ArrayList<WebSocketSession> users = new ArrayList<>();


    @Override
    public void afterConnectionEstablished( WebSocketSession session) throws Exception {
        logger.info("webSocket connection established.");
        users.add(session);
        Long userId = (Long) session.getAttributes().get("WEBSOCKET_USERID");
        logger.info("realTimeData webSocket connection established, userId: " + userId);

        //// TODO: 2016/11/5
       //com.iekun.ef.test.ui.RealTimeDataTimer.startTimer();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        logger.info("realTimeData websocket connection closed.");
        users.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        logger.info("realTimeData webSocket connection closed.");
        users.remove(session);
        
        //// TODO: 2016/11/5
        //com.iekun.ef.test.ui.RealTimeDataTimer.stopTimer();
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers( TextMessage message) {
        for ( WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                   user.sendMessage(message);
                }
            } catch ( IOException e ) {
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
    public void sendMessageToUser( Long userId , TextMessage message) {
        for (WebSocketSession user : users) {
            if ( userId == ((Long)user.getAttributes().get( "WEBSOCKET_USERID")) ) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

}
