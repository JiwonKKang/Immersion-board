package com.mygroup.sbb.question;

import java.time.LocalDateTime;
import java.util.List;

import com.mygroup.sbb.answer.Answer;
import com.mygroup.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    private Question(String subject, String content, LocalDateTime createDate, SiteUser author, LocalDateTime modifyDate) {
        this.subject = subject;
        this.content = content;
        this.createDate = createDate;
        this.author = author;
        this.modifyDate = modifyDate;
    }

    public static Question of(String subject, String content, LocalDateTime createDate, SiteUser author) {
        return of(
                subject,
                content,
                createDate,
                author,
                null
        );
    }

    public static Question of(String subject, String content, LocalDateTime createDate, SiteUser author, LocalDateTime modifyDate) {
        return new Question(
                subject,
                content,
                createDate,
                author,
                modifyDate
        );
    }

    public void addAnswer(Answer answer) {
        this.getAnswerList().add(answer);
        answer.setQuestion(this);
    }

    public void updateQuestion(String subject, String content, LocalDateTime modifyDate) {
        this.subject = subject;
        this.content = content;
        this.modifyDate = modifyDate;
    }
}