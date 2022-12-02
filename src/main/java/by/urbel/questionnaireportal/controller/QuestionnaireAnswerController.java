package by.urbel.questionnaireportal.controller;

import by.urbel.questionnaireportal.constants.Routes;
import by.urbel.questionnaireportal.constants.WebSockets;
import by.urbel.questionnaireportal.dto.QuestionnaireAnswerDto;
import by.urbel.questionnaireportal.service.QuestionnaireAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Routes.Q_ANSWERS)
@RequiredArgsConstructor
public class QuestionnaireAnswerController {
    private final QuestionnaireAnswerService questionnaireAnswerService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public void createAnswer(@RequestBody @Valid QuestionnaireAnswerDto dto) {
        questionnaireAnswerService.create(dto);
        messagingTemplate.convertAndSend(
                WebSockets.RESPONSES_TOPIC,
                WebSockets.MESSAGE_UPDATE_RESPONSES
        );
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_USER') and #questionnaireId==principal.questionnaire.id")
    public List<QuestionnaireAnswerDto> readAllByQuestionnaireId(@RequestParam UUID questionnaireId,
                                                                 Pageable pageable) {
        return questionnaireAnswerService.readAllByQuestionnaireId(questionnaireId, pageable);
    }

    @GetMapping(Routes.Q_ANSWERS_SIZE)
    @PreAuthorize("hasAuthority('ROLE_USER') and #questionnaireId==principal.questionnaire.id")
    public long getAnswersSize(@RequestParam UUID questionnaireId) {
        return questionnaireAnswerService.getSize(questionnaireId);
    }
}
