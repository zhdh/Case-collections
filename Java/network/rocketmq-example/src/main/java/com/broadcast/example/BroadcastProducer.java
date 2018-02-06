package com.broadcast.example;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

/**
 * Producer example
 * @author http://rocketmq.apache.org/docs/broadcast-example/
 */
public class BroadcastProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.start();
        int maxMessageNum = 100;
        for (int i = 0; i < maxMessageNum ; i++) {
            Message msg = new Message("TopicTest",
                    "TagA",
                    "OrderID123",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }

}
