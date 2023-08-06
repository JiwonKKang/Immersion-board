package com.mygroup.sbb.question;

import java.time.LocalDateTime;
import java.util.List;

import com.mygroup.sbb.BaseTimeEntity;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Question extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;

    private Question(String subject, String content, SiteUser author) {
        this.subject = subject;
        this.content = content;
        this.author = author;
    }

    public static Question of(String subject, String content, SiteUser author) {
        return new Question(
                subject,
                content,
                author
        );
    }

    public void addAnswer(Answer answer) {
        this.getAnswerList().add(answer);
        answer.setQuestion(this);
    }

    public void updateQuestion(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}