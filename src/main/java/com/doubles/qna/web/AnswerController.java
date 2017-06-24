package com.doubles.qna.web;

import com.doubles.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // 답변 하기
    @PostMapping
    public  String create(@PathVariable Long questionId, String contents, HttpSession session) {
        // 로그인되어 있지 않으면 로그인 페이지로
        if ( !HttpSessionUtils.isLoginUser(session) ) {
            return "/users/loginForm";
        }
        // 로그인된 회원의 정보 가져오기
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findOne(questionId);
        Answer answer = new Answer(loginUser, question, contents);
        answerRepository.save(answer);
        return  String.format("redirect:/questions/%d", questionId);
    }

    // 답변 수정하기
//    @PutMapping
//    public  String update() {
//        return "";
//    }

    // 답변 삭제하기
//    @DeleteMapping
//    public String delete() {
//        return "";
//    }

    // 답변 갯수
//    @GetMapping
//    public String countAnswer() {
//        return "";
//    }
}
