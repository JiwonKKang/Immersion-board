package com.mygroup.sbb.question;

import com.mygroup.sbb.answer.AnswerDto;
import com.mygroup.sbb.user.SiteUser;
import com.mygroup.sbb.user.SiteUserDto;

import java.time.LocalDateTime;
import java.util.List;

public record QuestionDto(
        String subject,
        String content,
        SiteUserDto author,
        LocalDateTime createDate,
        LocalDateTime modifyDate
) {

    public static QuestionDto of(String subject, String content, SiteUserDto author) {
        return of(
                subject,
                content,
                author,
                null,
                null
        );
    }

    public static QuestionDto of(String subject, String content, SiteUserDto author, LocalDateTime createDate, LocalDateTime modifyDate) {
        return new QuestionDto(
                subject, content, author, createDate, modifyDate);
    }

    public static QuestionDto from(Question entity) {
        return new QuestionDto(
                entity.getSubject(),
                entity.getContent(),
                SiteUserDto.from(entity.getAuthor()),
                entity.getCreateDate(),
                entity.getModifyDate()
        );
    }

    public Question toEntity(SiteUser siteUser) {
        return Question.of(
                subject,
                content,
                siteUser
        );
    }
}
