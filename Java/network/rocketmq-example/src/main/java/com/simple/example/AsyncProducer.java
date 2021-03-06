package com.simple.example;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

/**
 * asynchronous transmission is generally used
 * in response time sensitive business scenarios.
 *
 * @author http://rocketmq.apache.org/docs/simple-example/
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        // Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        // Launch the instance.
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        int maxMessgeNum = 100;
        for (int i = 0; i < maxMessgeNum; i++) {
            final int index = i;
            // Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest", "TagA", "OrderID188", "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exceptiopn %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        // Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }

}
