package com.example.demo.mvc;

import com.example.demo.dto.UserDto;
import com.example.demo.service.ResumeService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final ResumeService resumeService;

    @GetMapping("/{userId}")
    public String getMovie(@PathVariable int userId, Model model) {
        UserDto userDto=userService.mapToUserDto(userService.getUserById(userId).get());
        userDto.setResumes(resumeService.getResumesByUserId(userId));
        model.addAttribute("account", userDto);
        return "profile";
    }
}
