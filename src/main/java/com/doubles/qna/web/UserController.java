package com.doubles.qna.web;

import com.doubles.qna.domain.User;
import com.doubles.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/users")
public class UserController {

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
    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    // 로그인 처리

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
