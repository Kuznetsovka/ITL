package ru.itl.train.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itl.train.service.UserService;
import ru.itl.train.utils.Utils;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@AllArgsConstructor
public class MainController {

    private final UserService userService;

    @RequestMapping(value = "/login")
    public String login(Model model, HttpSession session, @RequestParam(required = false) Boolean error) {
        Object exception = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (exception != null) {
            model.addAttribute("error", true);
        }
        return "login";
    }

    @RequestMapping(value = "/auth", produces = {"text/plain"})
    public ResponseEntity<?> login() {
        return new ResponseEntity<>("Добро пожаловать в REST сервис", HttpStatus.OK);
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @ControllerAdvice
    static class GlobalDefaultExceptionHandler {
        @ExceptionHandler(Exception.class)
        public ResponseEntity<?> globalError(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(PSQLException.class)
        public ResponseEntity<?> sqlError(PSQLException e) {
            ServerErrorMessage sem = Utils.getExceptionErrorMessage(e);
            String msg = (sem != null) ? sem.getDetail() : e.getMessage();
            log.info(e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

    }
}
