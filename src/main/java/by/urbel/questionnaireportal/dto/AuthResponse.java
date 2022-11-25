package by.urbel.questionnaireportal.dto;

import lombok.*;

@Builder
@Getter
public class AuthResponse {
    private String jwtToken;
    private String fullName;
    private Long userId;
    private Long questionnaireId;
}
