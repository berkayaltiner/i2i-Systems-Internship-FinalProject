package connection.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

public class ConsumerConfiguration {
	
	 final String bootstrapServers = "34.141.15.250:9092";
	 final String consumerGroupId = "consumergroupUpdate";



	 public Properties getKafkaConsumerConfig(){
	        Properties properties = new Properties();
	        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
	        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
	        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "serializer.KryoDeserializer");
	        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"serializer.KryoDeserializer");
	        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	        return properties;
	    }

}
