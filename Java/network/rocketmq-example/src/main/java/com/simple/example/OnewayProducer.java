package com.simple.example;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

/**
 * One-way transmission is used for cases requiring moderate reliability, such as log collection.
 *
 * @author http://rocketmq.apache.org/docs/simple-example/
 */
@SuppressWarnings("SpellCheckingInspection")
public class OnewayProducer {

    public static void main(String[] args) throws Exception {
        // Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        // Launch the instance.
        producer.start();
        int maxMessageNum = 100;
        for (int i = 0; i < maxMessageNum; i++) {
            // Create a message instance, specifying topic, tag and message body.
            Message msg = new Message(
                     /* Topic */
                    "TopicTest",
                     /* Tag */
                    "TagA",
                    ("Hello RocketMQ " +
                            /* Message body */
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            // Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);
        }
        // Shut down the producer instance is not longer in use.
        producer.shutdown();
    }

}
