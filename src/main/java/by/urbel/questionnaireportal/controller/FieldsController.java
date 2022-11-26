package by.urbel.questionnaireportal.controller;

import by.urbel.questionnaireportal.dto.FieldDto;
import by.urbel.questionnaireportal.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldsController {
    private final FieldService fieldService;

    @PostMapping
    public void save(@Validated(FieldDto.New.class) @RequestBody FieldDto field) {
        fieldService.create(field);
    }

    @GetMapping()
    public List<FieldDto> readAllByQuestionnaireId(@RequestParam(required = false) Integer page,
                                                   @RequestParam(required = false) Integer size,
                                                   @RequestParam Long questionnaireId) {
        return fieldService.readAllByQuestionnaireId(questionnaireId, page, size);
    }

    @GetMapping("/active")
    public List<FieldDto> readAllActive(@RequestParam Long questionnaireId) {
        return fieldService.readAllActive(questionnaireId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void update(@PathVariable Long id, @Validated(FieldDto.Update.class) @RequestBody FieldDto fieldDto) {
        fieldService.update(id, fieldDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        fieldService.delete(id);
    }

    @GetMapping("/size")
    public long getFieldsSize(@RequestParam Long questionnaireId) {
        return fieldService.getSize(questionnaireId);
    }
}
