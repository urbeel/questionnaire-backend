package by.urbel.questionnaireportal.controller;

import by.urbel.questionnaireportal.constants.Routes;
import by.urbel.questionnaireportal.dto.FieldDto;
import by.urbel.questionnaireportal.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Routes.FIELDS)
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
    public List<FieldDto> readAllByQuestionnaireId(@RequestParam UUID questionnaireId, Pageable pageable) {
        return fieldService.readAllByQuestionnaireId(questionnaireId, pageable);
    }

    @GetMapping(Routes.ACTIVE_FIELDS)
    public List<FieldDto> readAllActive(@RequestParam UUID questionnaireId) {
        return fieldService.readAllActive(questionnaireId);
    }

    @PutMapping(Routes.FIELD_ID)
    @PreAuthorize("hasAuthority('ROLE_USER') and #fieldDto.questionnaireId==principal.questionnaire.id")
    public void update(@PathVariable UUID id, @Validated(FieldDto.Update.class) @RequestBody FieldDto fieldDto) {
        fieldService.update(id, fieldDto);
    }

    @DeleteMapping(Routes.FIELD_ID)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void delete(@PathVariable UUID id, Authentication authentication) {
        fieldService.delete(id, authentication);
    }

    @GetMapping(Routes.FIELDS_SIZE)
    @PreAuthorize("hasAuthority('ROLE_USER') and #questionnaireId==principal.questionnaire.id")
    public long getFieldsSize(@RequestParam UUID questionnaireId) {
        return fieldService.getSize(questionnaireId);
    }
}
