package work.yanghao.producer.handler;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import work.yanghao.core.KafkaConfig;

public class ProducerHandler {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(KafkaConfig.class);
        KafkaTemplate<Integer,String> kafkaTemplate = (KafkaTemplate<Integer,String>)ctx.getBean("kafkaTemplate");
        ListenableFuture<SendResult<Integer, String>> send = kafkaTemplate.send("topic-test", "haoareyou");
        send.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(SendResult<Integer, String> integerStringSendResult) {

            }
        });

    }
}
