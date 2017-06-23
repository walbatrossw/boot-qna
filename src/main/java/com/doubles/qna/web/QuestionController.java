package com.doubles.qna.web;

import com.doubles.qna.domain.Question;
import com.doubles.qna.domain.QuestionRepository;
import com.doubles.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    // 질문 작성 화면
    @GetMapping("/form")
    public String question(HttpSession session) {
        // 로그인되어 있지 않으면 로그인 페이지로
        if ( !HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        return "/qna/form";
    }

    // 질문 작성 처리
    @PostMapping
    public String create(String title, String contents, HttpSession session) {
        // 로그인되어 있지 않으면 로그인 페이지로
        if ( !HttpSessionUtils.isLoginUser(session) ) {
            return "redirect:/users/loginForm";
        }
        // 현재 로그인되어 있는 회원의 정보를 sessionUser 에 복사
        User sessionUser = HttpSessionUtils.getUserFromSession(session);

        Question newQuestion = new Question(sessionUser, title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    // 질문 상세 보기
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));
        return "/qna/show";
    }

    // 게시글 수정 화면

    // 게시글 수정 처리

    // 답변 작성 처리

    // 답변 목록
}
