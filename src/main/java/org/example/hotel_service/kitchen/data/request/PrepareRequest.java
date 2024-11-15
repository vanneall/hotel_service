package org.example.hotel_service.kitchen.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PrepareRequest(
    @JsonProperty("chefId")
    Long chefId
) {
}
