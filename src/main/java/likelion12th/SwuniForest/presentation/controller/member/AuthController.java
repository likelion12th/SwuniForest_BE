package likelion12th.SwuniForest.presentation.controller.member;


import jakarta.validation.Valid;

import likelion12th.SwuniForest.service.member.AuthService;
import likelion12th.SwuniForest.service.member.domain.dto.LoginDto;
import likelion12th.SwuniForest.service.member.domain.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        TokenDto tokenDto = authService.login(loginDto);
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }
}
