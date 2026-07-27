/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.wms.controller;

import cn.zhuatech.wms.common.ApiResponse;
import cn.zhuatech.wms.dto.AuthDto.*;
import cn.zhuatech.wms.repository.UserRepository;
import cn.zhuatech.wms.security.JwtService;
import cn.zhuatech.wms.service.CurrentUserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository users;
    private final CurrentUserService currentUser;
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
                          UserRepository users, CurrentUserService currentUser) {
        this.authenticationManager = authenticationManager; this.jwtService = jwtService;
        this.users = users; this.currentUser = currentUser;
    }
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        var user = users.findByUsername(request.username()).orElseThrow();
        return ApiResponse.ok("登录成功", new LoginResponse(jwtService.generate(user.getUsername()), UserView.from(user)));
    }
    @GetMapping("/me") public ApiResponse<UserView> me() { return ApiResponse.ok(UserView.from(currentUser.get())); }
}
