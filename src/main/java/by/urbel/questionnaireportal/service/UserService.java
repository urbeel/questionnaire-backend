package by.urbel.questionnaireportal.service;

import by.urbel.questionnaireportal.dto.UserDto;

public interface UserService {
    void update(Long id, UserDto userDto);
}
