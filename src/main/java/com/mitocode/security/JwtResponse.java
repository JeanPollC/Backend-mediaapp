package com.mitocode.security;

import com.fasterxml.jackson.annotation.JsonProperty;

//CLase S3
public record JwtResponse(@JsonProperty(value = "access_token") String accessToken) {
}
