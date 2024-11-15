package org.example.tasks.tasks.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CancelRequest(
    @JsonProperty("reason")
    String reason
) {
}
