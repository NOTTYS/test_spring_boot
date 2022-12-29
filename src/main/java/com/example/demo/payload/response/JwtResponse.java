package com.example.demo.payload.response;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Integer id, String username, String email, List<String> roles) {
        
    }
}
