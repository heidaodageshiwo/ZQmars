package com.iekun.ef.test.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import com.iekun.ef.push.RealTimeDataWebSocketHandler;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.TextMessage;

/**
 * Created by feilong.cai on 2016/11/5.
 */

public class RealTimeDataTimer {

    private static final long TICK_DELAY = 500;

    private static final Log log = LogFactory.getLog(RealTimeDataTimer.class);
    private static Timer gameTimer = null;
    private static AtomicInteger indication = new AtomicInteger(0);

    @Bean
    public static RealTimeDataWebSocketHandler realTimeDataWebSocketHandler() {
        return new RealTimeDataWebSocketHandler();
    }

    public static void tick() throws Exception {

        SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String msg = "";
        int val =  indication.getAndIncrement();
        if( val > 15) {
            indication.set(0);
            msg = "{'type': 'newData', 'data': [{ " +
                    "'imsi': '46000000001', " +
                    "'indication': 1, " +
                    "'operatorText': '中国移动', " +
                    "'cityName': '上海市', " +
                    "'numberSection': '139544**', " +
                    "'siteName': '闵行站点', " +
                    "'deviceName': '元江路设备', " +
                    "'captureTime': '" + dateformat.format( new Date()) + "' " +
                    "}] }";

        } else  {
            msg = "{'type': 'newData', 'data': [{ " +
                    "'imsi': '46000000001', " +
                    "'indication': 0, " +
                    "'operatorText': '中国移动', " +
                    "'cityName': '上海市', " +
                    "'numberSection': '139544**', " +
                    "'siteName': '闵行站点', " +
                    "'deviceName': '元江路设备', " +
                    "'captureTime': '" + dateformat.format( new Date()) + "' " +
                    "}] }";
        }

        log.info("real time data tick run!");

        //realTimeDataWebSocketHandler().sendMessageToUser( 1 , new TextMessage( msg ));
    }

    public static void startTimer() {
        gameTimer = new Timer(RealTimeDataTimer.class.getSimpleName() + " Timer");
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    tick();
                }
                catch (Throwable ex) {
                    log.error("Caught to prevent timer from shutting down", ex);
                }
            }
        }, TICK_DELAY, TICK_DELAY);
    }

    public  static void stopTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }

}
