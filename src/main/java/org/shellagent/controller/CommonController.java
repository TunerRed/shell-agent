package org.shellagent.controller;

import org.shellagent.entity.User;
import org.shellagent.exception.LoginException;
import org.shellagent.mapper.UserMapper;
import org.shellagent.services.GatewayAuth;
import org.shellagent.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/common")
@RestController
public class CommonController {

    @Autowired
    GatewayAuth gatewayAuth;
    @Autowired
    UserMapper userMapper;

    @GetMapping("/login")
    public ResponseEntity login (String username, String password) throws LoginException {
        String token;
        if (username == null || password == null) {
            throw new LoginException("参数为空，请重新登录");
        }
        else {
            User user = userMapper.selectByPrimaryKey(username);
            if (user == null)
                throw new LoginException(Constant.ResultCode.NOT_FOUND, "未注册的用户");
            String enc = EncUtil.encode(EncUtil.decodeUserPass(password.trim()));
            if (!enc.equals(user.getPassword()))
                throw new LoginException(Constant.ResultCode.NOT_FOUND, "密码错误");
            token = gatewayAuth.createToken(username);
        }
        return new ResponseBuilder().putItem("token", token).putItem("expiration", gatewayAuth.getExpiration().getTime()).getResponseEntity();
    }
}
