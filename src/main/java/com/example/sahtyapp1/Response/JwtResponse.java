package com.example.sahtyapp1.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JwtResponse {
    @JsonProperty("token")
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    // getters
}
