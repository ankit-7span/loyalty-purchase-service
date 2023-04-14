package com.loyalty.service.purchase.client;

import com.loyalty.service.purchase.model.WalletEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class WalletProducer {


    private NewTopic topic;

    private KafkaTemplate<String, WalletEvent> kafkaTemplate;

    public WalletProducer(NewTopic topic, KafkaTemplate<String, WalletEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(WalletEvent event){
        log.info(String.format("Order event => %s", event.toString()));

        // create Message
        Message<WalletEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}