package com.example.userregistation.payload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessToken {
    private String grant_type;
    private String username;
    private String password;
    private String client_id;
    private String client_secret;
}
