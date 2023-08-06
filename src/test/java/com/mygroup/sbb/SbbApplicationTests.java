package com.mygroup.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mygroup.sbb.question.QuestionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mygroup.sbb.answer.Answer;
import com.mygroup.sbb.answer.AnswerRepository;
import com.mygroup.sbb.question.Question;
import com.mygroup.sbb.question.QuestionRepository;



@SpringBootTest
class SbbApplicationTests {

}