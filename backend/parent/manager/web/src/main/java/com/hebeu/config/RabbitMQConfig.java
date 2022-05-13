package com.hebeu.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: RabbitMQConfig
 * @Author: Smoadrareun
 * @Description: TODO
 */

@Configuration
public class RabbitMQConfig {

    public static final String directExchangeName = "directExchange1";

    private static final String queue1BindingKey1 = "big";
    private static final String queue1BindingKey2 = "small";
    private static final String queue2BindingKey =  "cat";
    private static final String queue3BindingKey =  "dog";

    // 声明直连交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchangeName);
    }

    // 声明消息队列
    @Bean
    public Queue messageQueue1() {
        return new Queue("queue1");
    }

    @Bean
    public Queue messageQueue2() {
        return new Queue("queue2");
    }

    @Bean
    public Queue messageQueue3() {
        return new Queue("queue3");
    }

    // 向直连交换机上绑定队列
    @Bean
    Binding bindingQueue1Exchange1(Queue messageQueue1, DirectExchange directExchange) {
        return BindingBuilder.bind(messageQueue1)
                .to(directExchange)
                .with(queue1BindingKey1);
    }

    @Bean
    Binding bindingQueue1Exchange2(Queue messageQueue1, DirectExchange directExchange) {
        return BindingBuilder.bind(messageQueue1)
                .to(directExchange)
                .with(queue1BindingKey2);
    }

    @Bean
    Binding bindingQueue2Exchange(Queue messageQueue2, DirectExchange directExchange) {
        return BindingBuilder.bind(messageQueue2)
                .to(directExchange)
                .with(queue2BindingKey);
    }

    @Bean
    Binding bindingQueue3Exchange(Queue messageQueue3, DirectExchange directExchange) {
        return BindingBuilder.bind(messageQueue3)
                .to(directExchange)
                .with(queue3BindingKey);
    }
}