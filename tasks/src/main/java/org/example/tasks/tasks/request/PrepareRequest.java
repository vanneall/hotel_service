package org.example.tasks.tasks.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PrepareRequest(
    @JsonProperty("chefId")
    Long chefId
) {
}
