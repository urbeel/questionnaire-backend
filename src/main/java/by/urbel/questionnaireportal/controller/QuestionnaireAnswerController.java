package by.urbel.questionnaireportal.controller;

import by.urbel.questionnaireportal.dto.QuestionnaireAnswerDto;
import by.urbel.questionnaireportal.service.QuestionnaireAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/responses")
@RequiredArgsConstructor
public class QuestionnaireAnswerController {
    private final QuestionnaireAnswerService questionnaireAnswerService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public void createAnswer(@RequestBody @Valid QuestionnaireAnswerDto dto) {
        questionnaireAnswerService.create(dto);
        messagingTemplate.convertAndSend("/topic/responses", "Update responses");
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_USER') and #questionnaireId==principal.questionnaire.id")
    public List<QuestionnaireAnswerDto> readAllByQuestionnaireId(@RequestParam Integer page,
                                                                 @RequestParam Integer size,
                                                                 @RequestParam Long questionnaireId) {
        return questionnaireAnswerService.readAllByQuestionnaireId(questionnaireId, page, size);
    }

    @GetMapping("/size")
    @PreAuthorize("hasAuthority('ROLE_USER') and #questionnaireId==principal.questionnaire.id")
    public long getAnswersSize(@RequestParam Long questionnaireId) {
        return questionnaireAnswerService.getSize(questionnaireId);
    }
}
