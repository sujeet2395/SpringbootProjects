package com.learncode.authservice.service.impl;

import com.learncode.authservice.request.Message;
import com.learncode.authservice.service.interfaces.KafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService {

    private static final Logger log = LoggerFactory.getLogger(KafkaServiceImpl.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(Message message) {
        kafkaTemplate.send(message.getTopic(), message.getContent());
    }

    @Override
    @KafkaListener(topics = "testtopic", groupId = "test")
    public void read(String message) {
        log.info("Consumer Group: Test, Topic: testtopic, Message: {}",message);
    }
}