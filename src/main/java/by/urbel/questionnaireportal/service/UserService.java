package by.urbel.questionnaireportal.service;

import by.urbel.questionnaireportal.dto.UserDto;

import java.util.UUID;

public interface UserService {
    UserDto findById(UUID id);

    void update(UUID id, UserDto userDto);
}
