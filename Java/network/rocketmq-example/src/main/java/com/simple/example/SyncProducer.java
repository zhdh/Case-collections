package com.simple.example;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;


/**
 * Reliable synchronous transmission is used in extensive scenes,
 * such as important notification messages, SMS notification, SMS marketing system,
 * etc..
 *
 * @author http://rocketmq.apache.org/docs/simple-example/
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        // Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("unique_group_name");
        // Launch the instance.
        producer.start();
        int maxMessageNum = 100;
        for (int i = 0; i < maxMessageNum; i++) {
            // Create a message instance, specifying topic, tag and message body.
            Message msg = new Message(
                    /* Topic */
                    "TopicTest",
                    "TagA"
                    /* Tag */,
                    ("Hello RocketMQ " +
                    /* Message body */
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        // Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }

}
