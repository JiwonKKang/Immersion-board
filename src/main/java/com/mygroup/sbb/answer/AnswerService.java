package com.mygroup.sbb.answer;

import com.mygroup.sbb.question.Question;
import com.mygroup.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;


    public void create(Question question, String content, SiteUser author) {
        Answer answer = Answer.of(content, LocalDateTime.now(), author);
        question.addAnswer(answer);
        this.answerRepository.save(answer);
    }
}
