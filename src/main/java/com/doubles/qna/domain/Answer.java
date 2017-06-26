package com.doubles.qna.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;

    @Lob // 255자가 넘는 String 타입일 경우 애노테이션 추가
    @JsonProperty
    private String contents;

    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    @JsonProperty
    private Question question;

    // 기본 생성자
    public Answer() {

    }

    // 생성자
    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.contents = contents;
        this.question = question;
        this.createDate =  LocalDateTime.now();
    }

    // 시간 포맷변경 메서드
    public String getFormattedCreateDate() {
        if (createDate == null) {
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    // equals() 메서드 오버라이딩
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return id != null ? id.equals(answer.id) : answer.id == null;
    }

    // hashCode() 메서드 오버라이딩
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    // toString() 메서드
    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
