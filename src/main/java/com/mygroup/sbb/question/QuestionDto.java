package com.mygroup.sbb.question;

import com.mygroup.sbb.user.SiteUserDto;

public record QuestionDto(
        String subject,
        String content,
        SiteUserDto author
) {
}
