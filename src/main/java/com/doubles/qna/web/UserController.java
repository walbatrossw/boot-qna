package com.doubles.qna.web;

import com.doubles.qna.domain.User;
import com.doubles.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 회원가입 화면
    @GetMapping("/form")
    public String form() {
        return "user/form";
    }
    // 회원가입 처리
    @PostMapping("/create")
    public String create(User user) {
        System.out.println("User : " + user);
        userRepository.save(user);
        return "redirect:/user/list";
    }

    // 회원 리스트
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "list";
    }
}
