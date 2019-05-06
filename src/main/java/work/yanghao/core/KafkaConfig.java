package work.yanghao.core;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import work.yanghao.comsumer.handler.ConsumerHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Config Class
 */
@Configuration
@EnableKafka
public class KafkaConfig {

    /**
     * 配置Kafka服务器连接地址和端口
     * @return
     */
    @Bean
    public KafkaAdmin admin(){
        Map<String,Object> configs = new HashMap();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.61.129:9092");
        return new KafkaAdmin(configs);
    }

    /**
     * 创建主题对象
     * @return
     */
    @Bean
    public NewTopic topic(){
        return new NewTopic("foo",10,(short) 2);
    }

    /**
     * 生产者配置
     * @return
     */
    @Bean
    public Map<String,Object> producerConfigs(){

        HashMap<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.61.129:9092");
        props.put("acks","all");
        props.put("retries",0);
        props.put("batch.size",1);
        props.put("linger.ms",1);
        props.put("buffer.memory",33554432);
        props.put("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    /**
     * 生产者工厂配置
     * @return
     */
    @Bean
    public ProducerFactory<Integer,String> producerFactory(){

        return new DefaultKafkaProducerFactory<Integer, String>(producerConfigs());
    }

    /**
     * 生产者模板对象
     * @return
     */
    @Bean
    public KafkaTemplate<Integer,String> kafkaTemplate(){
        return new KafkaTemplate<Integer,String>(producerFactory());
    }

    /**
     * 消息监听工厂
     * @return
     */
    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer,String> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    /**
     * 消费者创建工厂
     * @return
     */
    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /**
     * 消费者配置
     * @return
     */
    @Bean
    public Map<String,Object> consumerConfigs() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.61.129:9092");
        props.put("group.id","test");
        props.put("enable.auto.commit","true");
        props.put("auto.commit.interval.ms","1000");
        props.put("key.deserializer","org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    /**
     * 监听对象
     * @return
     */
    @Bean
    public ConsumerHandler ConsumerHandler(){
        return new ConsumerHandler();
    }


}
