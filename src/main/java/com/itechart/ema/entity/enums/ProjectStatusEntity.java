package com.itechart.ema.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProjectStatusEntity {

    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    SUPPORT("SUPPORT"),
    COMPLETED("COMPLETED");

    private String value;

    ProjectStatusEntity(final String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ProjectStatusEntity fromValue(final String text) {
        for (var b : ProjectStatusEntity.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

}
