package com.order.example;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * Send message sample code
 *
 * @author http://rocketmq.apache.org/docs/order-example/
 */
public class OrderedProducer {
    @SuppressWarnings("Convert2Lambda")
    public static void main(String[] args) throws Exception {
        // Instantiate with a producer group name.
        MQProducer producer = new DefaultMQProducer("example_group_name");
        // Launch the instance.
        producer.start();
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        int maxMessageNum = 100;
        for (int i = 0; i < maxMessageNum; i++) {
            int orderId = i % 10;
            // Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest", tags[i % tags.length], "KEY" + i,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, orderId);
            System.out.printf("%s%n", sendResult);
        }
        // server shutdown
        producer.shutdown();
    }

}
