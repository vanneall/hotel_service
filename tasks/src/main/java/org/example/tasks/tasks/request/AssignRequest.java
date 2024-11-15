package org.example.tasks.tasks.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AssignRequest(
    @JsonProperty("executorId")
    String executorId
) {
}
