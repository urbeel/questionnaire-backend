package by.urbel.questionnaireportal.controller;

import by.urbel.questionnaireportal.dto.FieldDto;
import by.urbel.questionnaireportal.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldsController {
    private final FieldService fieldService;

    @PostMapping
    public void saveField(@RequestBody FieldDto field) {
        fieldService.create(field);
    }

    @GetMapping
    public List<FieldDto> readAll() {
        return fieldService.readAll();
    }
}
