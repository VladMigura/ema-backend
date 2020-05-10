package com.itechart.ema.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskTypeEntity {

    BUG("BUG"),
    FEATURE("FEATURE"),
    INVESTIGATION("INVESTIGATION"),
    DESIGN("DESIGN"),
    IMPLEMENTATION("IMPLEMENTATION"),
    FIX("FIX");

    private String value;

    TaskTypeEntity(final String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static TaskTypeEntity fromValue(final String text) {
        for (var b : TaskTypeEntity.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

}
