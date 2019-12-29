package com.niek125.userconsumer.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KafkaHeader {
    private final Action action;
    private final String payload;
}
