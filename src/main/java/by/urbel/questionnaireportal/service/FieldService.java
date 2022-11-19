package by.urbel.questionnaireportal.service;

import by.urbel.questionnaireportal.dto.FieldDto;

import java.util.List;

public interface FieldService {
    void create(FieldDto fieldDto);

    List<FieldDto> readAll();
}
