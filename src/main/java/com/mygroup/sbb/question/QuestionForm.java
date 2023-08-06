package com.mygroup.sbb.question;

import com.mygroup.sbb.user.SiteUser;
import com.mygroup.sbb.user.SiteUserDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

public record QuestionForm(
        @NotEmpty(message="제목은 필수항목입니다.")
        @Size(max=200)
        String subject,

        @NotEmpty(message="내용은 필수항목입니다.")
        String content
) {
    public static QuestionForm from(Question entity) {
        return new QuestionForm(
                entity.getSubject(),
                entity.getContent()
        );
    }

    public QuestionDto toDto(SiteUser author) {
        return QuestionDto.of(
                subject,
                content,
                SiteUserDto.from(author)
        );
    }
}
