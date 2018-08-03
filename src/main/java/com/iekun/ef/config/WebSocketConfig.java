package com.iekun.ef.config;

import com.iekun.ef.push.NoticeWebSocketHandler;
import com.iekun.ef.push.RealTimeDataWebSocketHandler;
import com.iekun.ef.push.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * Created by feilong.cai on 2016/10/26.
 */

@Configuration
@EnableWebSocket
class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler( realTimeDataWebSocketHandler(), "/realTimeDataWS").addInterceptors(new WebSocketHandshakeInterceptor()).withSockJS()
                             .setStreamBytesLimit( 8192 * 1024 )
                             .setHttpMessageCacheSize( 8192  )
                             .setDisconnectDelay( 2 * 1000 );
        registry.addHandler( noticeWebSocketHandler(), "/noticeWS").addInterceptors(new WebSocketHandshakeInterceptor()).withSockJS()
                            .setStreamBytesLimit( 8192 * 1024 )
                            .setHttpMessageCacheSize( 8192  )
                            .setDisconnectDelay( 2 * 1000 ).setClientLibraryUrl("/plugins/sockjs/sockjs.min.js");

    }

    @Bean
    public WebSocketHandler realTimeDataWebSocketHandler() {
        return new PerConnectionWebSocketHandler( RealTimeDataWebSocketHandler.class);
    }

    @Bean
    public WebSocketHandler noticeWebSocketHandler() {
       return new PerConnectionWebSocketHandler( NoticeWebSocketHandler.class);
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192 * 1024);
        container.setMaxBinaryMessageBufferSize(8192 * 1024);
        return container;
    }
}

