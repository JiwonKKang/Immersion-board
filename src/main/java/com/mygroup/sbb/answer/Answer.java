package com.mygroup.sbb.answer;

import java.time.LocalDateTime;

import com.mygroup.sbb.question.Question;
import com.mygroup.sbb.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    private Answer(String content, LocalDateTime createDate, SiteUser author) {
        this.content = content;
        this.createDate = createDate;
        this.author = author;
    }

    public static Answer of(String content, LocalDateTime createDate, SiteUser author) {
        return new Answer(
                content,
                createDate,
                author
        );
    }



}