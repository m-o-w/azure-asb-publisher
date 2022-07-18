package com.eni.communication.teams.sendMessage.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.core.util.BinaryData;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusMessageBatch;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

@RestController
public class MyController {
	
	/*  To Implement
	String connectionString = System.getenv("AZURE_SERVICEBUS_NAMESPACE_CONNECTION_STRING");
    String queueName = System.getenv("AZURE_SERVICEBUS_SAMPLE_QUEUE_NAME");
	 */
	String connectionString = "Endpoint=sb://namespaceasb.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=95xYR3iL4hji/Lyr5AkYIucr4B4Uo9GueGGZ2sIvNVQ=";
    String queueName = "queue01";
	
    @GetMapping("/talkToMe")
    public String talking()
    {
        return("Hello from SendMessage");

    }
    @GetMapping("/sendMessage")
    public  List<String> sendMessage () throws InterruptedException
    {
        AtomicBoolean sampleSuccessful = new AtomicBoolean(false);
        CountDownLatch countdownLatch = new CountDownLatch(1);
        
        List<String> messageList=new ArrayList<String>();  
        
        String messageBody;
        
        ServiceBusSenderClient sender = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .queueName(queueName)
                .buildClient();
        
       /*
         final ServiceBusMessageBatch messageBatch = sender.createMessageBatch();
        
        IntStream.range(1, 3)
        .mapToObj(index -> new ServiceBusMessage(BinaryData.fromString("Message from MessageSender " + index + ":" + java.util.UUID.randomUUID())))
        .forEach(message -> 
        {
        	messageBatch.tryAddMessage(message);
        	messageList.add(message.getMessageId());
        }		
        		);
        */
        
        final ServiceBusMessageBatch messageBatch = sender.createMessageBatch();
        
        for (int i = 0; i < 5; i++) {
        messageBody="Message from MessageSender " + i + " " + java.util.UUID.randomUUID();
        messageBatch.tryAddMessage(new ServiceBusMessage(messageBody));
        messageList.add(messageBody);
        }
        
        sender.sendMessages(messageBatch);
        sender.close(); 
        
        return(messageList);
       
       
        
        //return("Message sent");
    }
}
