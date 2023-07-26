package com.example.miniproject_basic_yangyechan.controller;

import com.example.miniproject_basic_yangyechan.dto.ResponseDto;
import com.example.miniproject_basic_yangyechan.dto.UserDto;
import com.example.miniproject_basic_yangyechan.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller  // 로그인 페이지를 보여줄려고
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    // 1. login 페이지로 온다.
    // 2. login 페이지에 아이디 비밀번호를 입력한다.
    // 3. 성공하면 my-profile 로 이동한다.

    // 로그인 페이지를 위한 GetMapping
    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    // 로그인 성공 후 로그인 여부를 판단하기 위한 GetMapping
    @GetMapping("/my-profile")
    public String myProfile(
            Authentication authentication
    ) {
//        log.info(authentication.getName());
//        log.info(((User) authentication.getPrincipal()).getUsername());
        CustomUserDetails userDetails
                = (CustomUserDetails) authentication.getPrincipal();
        log.info(userDetails.getUsername());
        log.info(userDetails.getEmail());
//        log.info(SecurityContextHolder.getContext().getAuthentication().getName());
        return "my-profile";
    }

    // 1. 사용자가 register 페이지로 온다.
    // 2. 사용자가 register 페이지에 ID, 비밀번호, 비밀번호 확인을 입력
    // 3. register 페이지에서 /users/register 로 POST 요청
    // 4. UserDetailsManager 에 새로운 사용자 정보 추가

    @GetMapping("/register")
    public String registerForm() {
        return "register-form";
    }

    // 어떻게 사용자를 관리하는지는
    // interface 기반으로 의존성 주입
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;
/*
    @PostMapping("/register")
    public String registerPost(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("password-check") String passwordCheck
    ) {
        if (password.equals(passwordCheck)) {
            log.info("password match!");
            // username 중복도 확인해야 하지만,
            // 이 부분은 Service 에서 진행하는 것도 나쁘지 않아보임
//            manager.createUser(User.withUsername(username)
//                    .password(passwordEncoder.encode(password))
//                    .build());
            manager.createUser(CustomUserDetails.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .build());

            return "redirect:/users/login";
        }
        log.warn("password does not match...");
        return "redirect:/users/register?error";
    }

 */


    @PostMapping("/register")
    public ResponseDto registerPost(
            @RequestBody UserDto userDto
            ) {

        if (userDto.getPassword().equals(userDto.getPasswordCheck())) {
            log.info("password match!");
            // username 중복도 확인해야 하지만,
            // 이 부분은 Service 에서 진행하는 것도 나쁘지 않아보임
//            manager.createUser(User.withUsername(username)
//                    .password(passwordEncoder.encode(password))
//                    .build());
            manager.createUser(CustomUserDetails.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .email(userDto.getEmail())
                    .phone(userDto.getPhone())
                    .address(userDto.getAddress())
                    .build());

            ResponseDto response = new ResponseDto();
            response.setMessage("회원가입이 완료되었습니다.");

            return response;
        }else {
            log.warn("password does not match...");
            ResponseDto response = new ResponseDto();
            response.setMessage("비밀번호를 다시 확인하세요.");
            return response;
        }
    }

}
