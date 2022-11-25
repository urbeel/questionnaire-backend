package by.urbel.questionnaireportal.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FieldType {
    SINGLE_LINE_TEXT("Single line text"),
    MULTILINE_TEXT("Multiline text"),
    RADIO_BUTTON("Radio button"),
    CHECKBOX("Checkbox"),
    COMBOBOX("Combobox"),
    DATE("Date");

    private final String htmlName;
}
