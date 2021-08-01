package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.ReqisterRequestDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicitActionException;
import com.dynonuggets.refonteimplicaction.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody ReqisterRequestDto reqisterRequest) throws ImplicitActionException {
        authService.signupAndSendConfirmation(reqisterRequest);
        return new ResponseEntity<>("User registration successful", OK);
    }

    @GetMapping("accountVerification/{activationKey}")
    public ResponseEntity<String> verifyAccount(@PathVariable String activationKey) throws ImplicitActionException {
        authService.verifyAccount(activationKey);
        return new ResponseEntity<>("Account Activated Success", OK);
    }
}
