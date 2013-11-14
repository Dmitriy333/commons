package org.wso2.mbtest.queue.messagetype;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;


public class MapMessageConsumer {
	
	public static final String QPID_ICF = "org.wso2.andes.jndi.PropertiesFileInitialContextFactory";
    private static final String CF_NAME_PREFIX = "connectionfactory.";
    private static final String CF_NAME = "qpidConnectionfactory";
    String userName = "admin";
    String password = "admin";
    private static String CARBON_CLIENT_ID = "carbon";
    private static String CARBON_VIRTUAL_HOST_NAME = "carbon";
    private static String CARBON_DEFAULT_HOSTNAME = "localhost";
    private static String CARBON_DEFAULT_PORT = "5672";
    String queueName = "asanka";
 
    public static void main(String[] args) throws NamingException, JMSException {
    	MapMessageConsumer queueReceiver = new MapMessageConsumer();
        queueReceiver.receiveMessages();
    }
    
    public void receiveMessages() throws NamingException, JMSException {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, QPID_ICF);
        properties.put(CF_NAME_PREFIX + CF_NAME, getTCPConnectionURL(userName, password));
        properties.put("queue."+ queueName,queueName);
        System.out.println("getTCPConnectionURL(userName,password) = " + getTCPConnectionURL(userName, password));
        InitialContext ctx = new InitialContext(properties);
        
        // Lookup connection factory
        QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup(CF_NAME);
        QueueConnection queueConnection = connFactory.createQueueConnection();
        queueConnection.start();
        
        QueueSession queueSession =queueConnection.createQueueSession(false,QueueSession.AUTO_ACKNOWLEDGE );
         
        Queue queue =  (Queue) ctx.lookup(queueName);
        MessageConsumer queueReceiver =  queueSession.createConsumer(queue);
	    int count=1;

	    while(true){
	
        MapMessage message = (MapMessage) queueReceiver.receive();
       
        System.out.println("::Message Count::"+count+":::::::::::::Recieved message with JMSMessageID::::::::::::" + message.getJMSMessageID());
	   
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
