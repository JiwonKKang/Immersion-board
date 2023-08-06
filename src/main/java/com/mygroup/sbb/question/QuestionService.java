package com.mygroup.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mygroup.sbb.user.SiteUserDto;
import com.mygroup.sbb.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.mygroup.sbb.DataNotFoundException;
import com.mygroup.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public void create(QuestionDto dto) {
        SiteUser user = userRepository.getReferenceById(dto.author().id());
        Question question = dto.toEntity(user);
        questionRepository.save(question);
    }

    public Page<Question> getList(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Question getQuestion(Integer id) {
        return questionRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("해당하는 질문을 찾을 수 없습니다."));
    }

    public void modify(Integer questionId, QuestionDto dto) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new DataNotFoundException("수정하려는 질문을 찾을 수 없습습니다")
        );

        if (!question.getAuthor().getUsername().equals(dto.author().username())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        question.updateQuestion(dto.subject(), dto.content());
    }

    public void delete(Integer questionId, String username) {
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new DataNotFoundException("삭제하려는 질문을 찾을 수 없습니다"));

        if (!question.getAuthor().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        this.questionRepository.delete(question);
    }
}