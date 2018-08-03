package com.iekun.ef.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Created by feilong.cai on 2017/3/15.
 */

@Configuration
@EnableJms
public class ActivemqConfig {

    @Bean
    public JmsListenerContainerFactory<?> jmsContainerQueue(ConnectionFactory connectionFactory ) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setSessionTransacted(false);
        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        factory.setAutoStartup(true);
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerHeartBeat(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(connectionFactory);
        bean.setSessionTransacted(false);
        bean.setAutoStartup(true);
        bean.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        return bean;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerUeInfo(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(connectionFactory);
        bean.setSessionTransacted(false);
        bean.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        bean.setAutoStartup(true);
        //bean.setBackOff(backOff);
        return bean;
    }
    
   /* @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) throws JMSException {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        jmsTemplate.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        return jmsTemplate;
    }*/
      
    
    

}
