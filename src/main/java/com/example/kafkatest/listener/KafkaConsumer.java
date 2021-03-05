package com.example.kafkatest.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "Kafka_Test", group = "group_id")
    public void consume(String message) {
        BufferedWriter writer = null;
        message = message.substring(1,message.length()-1).replaceAll(",", "\n");
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("src/test/file/result.txt", true), StandardCharsets.UTF_8));
            writer.write(message+"\n");
            writer.close();
        } catch (IOException ex) {
            // Report
        }
        System.out.println(message);
    }
}
