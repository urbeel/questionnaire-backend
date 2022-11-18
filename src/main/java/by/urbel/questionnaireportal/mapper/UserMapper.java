package by.urbel.questionnaireportal.mapper;

import by.urbel.questionnaireportal.dto.SignUpRequest;
import by.urbel.questionnaireportal.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User signUpRequestToUser(SignUpRequest signUpRequest);
}
