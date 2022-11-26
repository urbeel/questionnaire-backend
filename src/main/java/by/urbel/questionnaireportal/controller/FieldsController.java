package by.urbel.questionnaireportal.controller;

import by.urbel.questionnaireportal.dto.FieldDto;
import by.urbel.questionnaireportal.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldsController {
    private final FieldService fieldService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER') and #fieldDto.questionnaireId==principal.questionnaire.id")
    public void save(@Validated(FieldDto.New.class) @RequestBody FieldDto fieldDto) {
        fieldService.create(fieldDto);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_USER') and #questionnaireId==principal.questionnaire.id")
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
    @PreAuthorize("hasAuthority('ROLE_USER') and #fieldDto.questionnaireId==principal.questionnaire.id")
    public void update(@PathVariable Long id, @Validated(FieldDto.Update.class) @RequestBody FieldDto fieldDto) {
        fieldService.update(id, fieldDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void delete(@PathVariable Long id, Authentication authentication) {
        fieldService.delete(id, authentication);
    }

    @GetMapping("/size")
    @PreAuthorize("hasAuthority('ROLE_USER') and #questionnaireId==principal.questionnaire.id")
    public long getFieldsSize(@RequestParam Long questionnaireId) {
        return fieldService.getSize(questionnaireId);
    }
}
