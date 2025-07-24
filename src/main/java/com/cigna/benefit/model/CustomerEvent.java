package com.cigna.benefit.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CustomerEvent {

    private Map<String, Object> customerNotification = new HashMap<>();

    @JsonAnySetter
    public void set(String key, Object value) {
        customerNotification.put(key, value);
    }
}
