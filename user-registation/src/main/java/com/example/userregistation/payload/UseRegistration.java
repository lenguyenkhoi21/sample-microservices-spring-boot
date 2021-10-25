package com.example.userregistation.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UseRegistration {
    private String username;
    private String email;
    @Builder.Default
    private boolean emailVerified = false;
    @Builder.Default
    private boolean enabled = true;
    @Builder.Default
    private List<String> requiredActions = new ArrayList<>();
    @Builder.Default
    private List<String> disableableCredentialTypes = new ArrayList<>();
}
