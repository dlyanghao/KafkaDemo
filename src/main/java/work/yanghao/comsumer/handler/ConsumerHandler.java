package work.yanghao.comsumer.handler;

import org.springframework.kafka.annotation.KafkaListener;

public class ConsumerHandler {

    @KafkaListener(id = "foo",topics = "topic-test")
    public void listen(String records){
        System.out.println("消息获取" + records);
    }
}
