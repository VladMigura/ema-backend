package com.itechart.ema.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatusEntity {

    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    QA_REVIEW("QA_REVIEW"),
    COMPLETED("COMPLETED"),
    ACCEPTED("ACCEPTED");

    private String value;

    TaskStatusEntity(final String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static TaskStatusEntity fromValue(final String text) {
        for (var b : TaskStatusEntity.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

}
