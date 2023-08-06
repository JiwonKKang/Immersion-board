package com.mygroup.sbb.answer;

import com.mygroup.sbb.question.QuestionDto;
import com.mygroup.sbb.user.SiteUserDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AnswerDto(
        String content,
        SiteUserDto author,
        Integer questionId,
        LocalDateTime createDate,
        LocalDateTime modifyDate
) {

    public static AnswerDto of(String content, SiteUserDto author, Integer questionId) {
        return new AnswerDto(
                content,
                author,
                questionId,
                null,
                null
        );
    }

    public static AnswerDto from(Answer answer) {
        return new AnswerDto(
                answer.getContent(),
                SiteUserDto.from(answer.getAuthor()),
                answer.getQuestion().getId(),
                answer.getCreateDate(),
                answer.getModifyDate()
        );
    }


}
