package AS_API.controller;

import AS_API.config.CustomUserDetails;
import AS_API.dto.UserRegisterDto;
import AS_API.exception.CustomException;
import AS_API.exception.ErrorCode;
import AS_API.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import AS_API.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    @PostMapping(value = "/api/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserRegisterDto userRegisterDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();

                switch (field) {
                    case "email":
                        throw new CustomException(ErrorCode.USER_EMAIL_INVALID);
                    case "password":
                        throw new CustomException(ErrorCode.USER_PASSWORD_INVALID);
                    case "nickName":
                        throw new CustomException(ErrorCode.USER_NICKNAME_INVALID);
                    default:
                        throw new CustomException(ErrorCode.USER_INVALID_INPUT);
                }
            }
        }


        try{
            User user = User.createUser(userRegisterDto, passwordEncoder);
            userService.registerUser(user);
        } catch (CustomException ce){
            throw ce;
        } catch (Exception e){
            throw new CustomException(ErrorCode.USER_REGIST_FAILED);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "회원가입이 완료되었습니다");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/api/users/{userId}")
    public @ResponseBody ResponseEntity<Object> deleteUser(@PathVariable("userId") Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long loginUserId = userDetails.getUser().getUserId();

        if (!userId.equals(loginUserId)) {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }

        userService.deleteUser(userId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "회원 탈퇴가 완료되었습니다");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
