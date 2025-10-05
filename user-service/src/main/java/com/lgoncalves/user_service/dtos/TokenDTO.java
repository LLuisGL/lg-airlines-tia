package com.lgoncalves.user_service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenDTO (
    @JsonProperty("access_token")
    String accessToken,
    @JsonProperty("refresh_token")
    String refresh_token
){ }
