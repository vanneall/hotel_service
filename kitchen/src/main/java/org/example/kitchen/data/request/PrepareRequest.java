package org.example.kitchen.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PrepareRequest(
    @JsonProperty("chefId")
    Long chefId
) {
}
