package com.github.bingoohuang.springbootbank.demo;

public interface MessageRepository {
    Iterable<Message> findAll();

    Message save(Message message);

    Message findMessage(Long id);
}
