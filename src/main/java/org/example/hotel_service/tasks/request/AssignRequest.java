package org.example.hotel_service.tasks.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AssignRequest(
    @JsonProperty("executorId")
    String executorId
) {
}
