package com.github.bingoohuang.springbootbank;

import com.github.bingoohuang.springbootbank.demo.InMemoryMessageRespository;
import com.github.bingoohuang.springbootbank.demo.Message;
import com.github.bingoohuang.springbootbank.demo.MessageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

@SpringBootApplication
public class Application {
    @Bean
    public MessageRepository messageRepository() {
        return new InMemoryMessageRespository();
    }

    @Bean
    public Converter<String, Message> messageConverter() {
        return new Converter<String, Message>() {
            @Override
            public Message convert(String id) {
                return messageRepository().findMessage(Long.valueOf(id));
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}


