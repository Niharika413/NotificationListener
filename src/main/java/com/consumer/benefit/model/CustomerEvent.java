package com.consumer.benefit.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CustomerEvent {

    private Map<String, Object> allFields = new HashMap<>();

    @JsonAnySetter
    public void set(String key, Object value) {
        allFields.put(key, value);
    }
}
