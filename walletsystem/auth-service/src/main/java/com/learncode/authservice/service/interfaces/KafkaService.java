package com.learncode.authservice.service.interfaces;

import com.learncode.authservice.request.Message;

public interface KafkaService {
    void publish(Message message);

    void read(String message);
}
