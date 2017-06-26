package com.doubles.qna.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;
    // private String writer;

    @JsonProperty
    private String title;

    @Lob
    @JsonProperty
    private String contents;

    // 날짜와 시간을 나타내기 위해 java8 부터 추가된 타입
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC")  // 내림차순 정렬
    private List<Answer> answers;

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        // 현재시간 생성 할당 : 현재 시간이 생성되었지만 알수 없는 글자들로 표현되기 때문에 formatting 을 해줘야한다.
        this.createDate =  LocalDateTime.now();
    }

    // 시간 포맷변경 메서드
    public String getFormattedCreateDate() {
        if (createDate == null) {
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    // 로그인유저와 글작성자 비교
    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    // update 메서드
    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer=" + writer +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
