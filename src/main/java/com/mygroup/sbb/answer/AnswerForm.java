package com.mygroup.sbb.answer;

import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

public record AnswerForm(
        @NotEmpty(message = "내용은 필수항목입니다.")
        String content
) {
}
