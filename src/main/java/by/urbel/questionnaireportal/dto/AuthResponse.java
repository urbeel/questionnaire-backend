package by.urbel.questionnaireportal.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
public class AuthResponse {
    private String jwtToken;
    private String fullName;
    private UUID userId;
    private UUID questionnaireId;
}
