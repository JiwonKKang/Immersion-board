package com.mygroup.sbb.question;


import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import com.mygroup.sbb.answer.AnswerForm;
import com.mygroup.sbb.user.SiteUser;
import com.mygroup.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model,
                       @PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Question> paging = questionService.getList(pageable);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        questionService.create(questionForm.toDto(userService.getUser(principal.getName())));
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(@PathVariable("id") Integer id, Principal principal, ModelMap map) {
        Question question = questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        QuestionForm questionForm = QuestionForm.from(question);
        map.addAttribute("questionForm", questionForm);
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer questionId, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        questionService.modify(questionId, questionForm.toDto(userService.getUser(principal.getName())));
        redirectAttributes.addAttribute("questionId", questionId);
        return "redirect:/question/detail/{questionId}";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer questionId) {
        this.questionService.delete(questionId, principal.getName());
        return "redirect:/";
    }
}
