package com.doubles.qna.web;

import com.doubles.qna.domain.Question;
import com.doubles.qna.domain.QuestionRepository;
import com.doubles.qna.domain.Result;
import com.doubles.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // 권한체크 메서드 1 : Result 클래스를 작성하여 구현
    private Result valid(HttpSession session, Question question) {
        // 로그인 여부 체크
        if ( !HttpSessionUtils.isLoginUser(session) ) {
            return Result.fail("로그인이 필요합니다.");
        }
        // 본인여부 체크
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if ( !question.isSameWriter(loginUser) ) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }

    // 권한체크 메서드 2 : exception 으로 구현
    private boolean hasPermission(HttpSession session, Question question) {
        // 로그인 여부 체크
        if ( !HttpSessionUtils.isLoginUser(session) ) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        // 본인여부 체크
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if ( !question.isSameWriter(loginUser) ) {
            throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return true;
    }

    // 질문 수정 화면
    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        // 현재 질문 조회
        Question question = questionRepository.findOne(id);
        Result result = valid(session, question);
        if ( !result.isValid() ) {
            // 에러 메시지 저장
            model.addAttribute("errorMsg", result.getErrorMsg());
            // 로그인 페이지로 이동
            return "/user/login";
        }
        // 질문 수정화면으로 이동
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    // 질문 수정 처리
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
        // 현재 질문 조회
        Question question = questionRepository.findOne(id);
        Result result = valid(session, question);
        if ( !result.isValid() ) {
            // 에러 메시지 저장
            model.addAttribute("errorMsg", result.getErrorMsg());
            // 로그인 페이지로 이동
            return "/user/login";
        }
        // 업데이트 처리
        question.update(title, contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d", id);
    }

    // 질문 삭제 처리
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) {
        // 현재 질문 조회
        Question question = questionRepository.findOne(id);
        Result result = valid(session, question);
        if ( !result.isValid() ) {
            // 에러 메시지 저장
            model.addAttribute("errorMsg", result.getErrorMsg());
            // 로그인 페이지로 이동
            return "/user/login";
        }
        // 삭제 처리
        questionRepository.delete(id);
        return "redirect:/";
    }
}
