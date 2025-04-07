package com.gnr.pm.analytics_service.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

        @KafkaListener(topics = "patient", groupId = "analytics-service")
        public void consume(byte[] event) {
            try {
                PatientEvent patientEvent = PatientEvent.parseFrom(event);
                LOGGER.error("Received patient event {}", patientEvent);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("Error deserializing event {}", e.getMessage());
            }
        }
}
