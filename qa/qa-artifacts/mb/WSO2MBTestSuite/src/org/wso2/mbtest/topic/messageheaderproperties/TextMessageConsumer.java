package org.wso2.mbtest.topic.messageheaderproperties;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;


public class TextMessageConsumer {
	
	public static final String QPID_ICF = "org.wso2.andes.jndi.PropertiesFileInitialContextFactory";
    private static final String CF_NAME_PREFIX = "connectionfactory.";
    private static final String CF_NAME = "qpidConnectionfactory";
    String userName = "admin";
    String password = "admin";
    private static String CARBON_CLIENT_ID = "carbon";
    private static String CARBON_VIRTUAL_HOST_NAME = "carbon";
    private static String CARBON_DEFAULT_HOSTNAME = "localhost";
    private static String CARBON_DEFAULT_PORT = "5672";
    String topicName = "SimpleStockQuoteService";
 
    public static void main(String[] args) throws NamingException, JMSException {
    	TextMessageConsumer queueReceiver = new TextMessageConsumer();
        queueReceiver.receiveMessages();
    }
    
    public void receiveMessages() throws NamingException, JMSException {
    	Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, QPID_ICF);
        properties.put(CF_NAME_PREFIX + CF_NAME, getTCPConnectionURL(userName, password));
        System.out.println("getTCPConnectionURL(userName,password) = " + getTCPConnectionURL(userName, password));
        InitialContext ctx = new InitialContext(properties);
        // Lookup connection factory
        TopicConnectionFactory connFactory = (TopicConnectionFactory) ctx.lookup(CF_NAME);
        TopicConnection topicConnection = connFactory.createTopicConnection();
        topicConnection.start();
        TopicSession topicSession =
                topicConnection.createTopicSession(false,QueueSession.AUTO_ACKNOWLEDGE);
        Topic topic = topicSession.createTopic(topicName);
       
        javax.jms.TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);
       
	    int count=1;

	    while(true){
	
	    	TextMessage message = (TextMessage) topicSubscriber.receive();
	        
	        System.out.println("::Message Count::"+count+":::::::::::::Recieved message with content::::::::::::" + message.getText());
	        
	        
	        String a=message.getJMSCorrelationID();
	        
	        byte[] b=message.getJMSCorrelationIDAsBytes();
	        
	        int c=message.getJMSDeliveryMode();
	       
			Queue d=(Queue) message.getJMSDestination();
			
			long e=message.getJMSExpiration();
			
			String f=message.getJMSMessageID();
			
			int g=message.getJMSPriority();
			
			boolean h=message.getJMSRedelivered();
			
			Queue i=(Queue) message.getJMSReplyTo();
			
			long j=message.getJMSTimestamp();
			
			String k=message.getJMSType();
			
			
	        
	        System.out.println(a+":::::::::::::::"+b.toString()+":::::::::::::::"+c+":::::::::::::::"+d.getQueueName()+":::::::::::::::"+e+":::::::::::::::"+f+":::::::::::::::"+g+":::::::::::::::"+h+":::::::::::::::"+i.getQueueName()+":::::::::::::::"+j+":::::::::::::::"+k+":::::::::::::::");
	        
		   	    
		    count++;
	}
        //queueReceiver.close();
       // queueSession.close();
       // queueConnection.stop();
        //queueConnection.close();
    }
    
    
    
    public String getTCPConnectionURL(String username, String password) {
        // amqp://{username}:{password}@carbon/carbon?brokerlist='tcp://{hostname}:{port}'
        return new StringBuffer()
                .append("amqp://").append(username).append(":").append(password)
                .append("@").append(CARBON_CLIENT_ID)
                .append("/").append(CARBON_VIRTUAL_HOST_NAME)
                .append("?brokerlist='tcp://").append(CARBON_DEFAULT_HOSTNAME).append(":").append(CARBON_DEFAULT_PORT).append("'")
                .toString();
    }
    

}
