/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.wms.dto;

import cn.zhuatech.wms.model.UserAccount;
import jakarta.validation.constraints.NotBlank;

public final class AuthDto {
    private AuthDto() {}
    public record LoginRequest(@NotBlank(message = "请输入账号") String username,
                               @NotBlank(message = "请输入密码") String password) {}
    public record LoginResponse(String token, UserView user) {}
    public record UserView(String username, String fullName, String role, String warehouse) {
        public static UserView from(UserAccount user) {
            return new UserView(user.getUsername(), user.getFullName(), user.getRole().name(), user.getWarehouse());
        }
    }
}
