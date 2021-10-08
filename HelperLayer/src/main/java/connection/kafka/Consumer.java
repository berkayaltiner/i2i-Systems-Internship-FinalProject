package connection.kafka;

import model.UsageToSendKafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import service.BalanceService;

import java.sql.SQLException;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;


public class Consumer {
	
    private Properties consumerConfig = new ConsumerConfiguration().getKafkaConsumerConfig();
    private BalanceService balanceService=null;
    
    public Consumer(BalanceService balanceService)
    {
    	this.balanceService=balanceService;
    }

	
	public void startConsume() throws SQLException
	{
	  
	  final KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(consumerConfig);
	  consumer.subscribe(Collections.singletonList("updateTopic"));
	  
	  while(true){
          ConsumerRecords<String,Object> records = consumer.poll(Duration.ofMillis(1000));
          for(ConsumerRecord<String, Object> record : records){
              System.out.println("-------------------------------------------------------------");
              System.out.println(" value:"+ record.value() + "topic: " +record.topic() );
              UsageToSendKafka balance = new UsageToSendKafka();
              balance = (UsageToSendKafka) record.value();
              if (balance.getOperation().equals("update")) 
              {
                    System.out.println("Updated User Balance Id : "+balance.getBalKey() + "\nUsed Amount is : "
                            + balance.getUsedAmount()+1);
      				balanceService.updateBalanceOracle(balance);

      		  }
              
           }
		
	   }
		
     }
}
	
	

