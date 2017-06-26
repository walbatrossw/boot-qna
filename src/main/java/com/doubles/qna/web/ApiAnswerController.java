package com.doubles.qna.web;

import com.doubles.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // 답변 하기
    @PostMapping
    public  Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        // 로그인되어 있지 않으면 로그인 페이지로
        if ( !HttpSessionUtils.isLoginUser(session) ) {
            return null;
        }
        // 로그인된 회원의 정보 가져오기
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findOne(questionId);
        Answer answer = new Answer(loginUser, question, contents);
        return answerRepository.save(answer);
    }
}
