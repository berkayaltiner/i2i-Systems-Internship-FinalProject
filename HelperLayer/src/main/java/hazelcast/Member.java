package hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.io.FileNotFoundException;

public class Member {



    public void putHazelcast(int partitionKey , String msisdn) throws FileNotFoundException {
        final String hazelCastAdress = "172.104.149.9:5701";
        final String topicName = "Balance";
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName("dev");
        clientConfig.getNetworkConfig().addAddress(hazelCastAdress);

        HazelcastInstance instance = HazelcastClient.newHazelcastClient(clientConfig);

        IMap<String,Integer> map = instance.getMap(topicName);

            map.put(msisdn,partitionKey);

    }
}
