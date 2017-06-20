package com.doubles.qna.web;

import com.doubles.qna.domain.User;
import com.doubles.qna.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    // 회원가입 화면
    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    // 회원가입 처리
    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users/list";
    }

    // 회원 리스트
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    // 로그인 화면
    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);

        if ( user == null ) {
            return "redirect:/users/loginForm";
        }

        if ( !password.equals(user.getPassword()) ) {
            System.out.println("login failure");
            return "redirect:/users/loginForm";
        }

        session.setAttribute("user", user);
        System.out.println("login success");
        return "redirect:/";
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션에 담기 user 를 제거
        session.removeAttribute("user");
        System.out.println("logout success");
        return "redirect:/";
    }

    // 회원 정보수정 화면
    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        User user = userRepository.findOne(id);
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    // 회원 정보수정 처리
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser) {
        User user = userRepository.findOne(id); // 기존의 아이디 정보를 조회
        user.update(updatedUser);   // 아이디의 정보 변경
        userRepository.save(user);  // 변경된 정보를 저장
        return "redirect:/users/list";
    }
}
