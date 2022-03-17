package org.shellagent.controller;

import org.shellagent.entity.User;
import org.shellagent.exception.LoginException;
import org.shellagent.exception.MyException;
import org.shellagent.mapper.UserMapper;
import org.shellagent.services.GatewayAuth;
import org.shellagent.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    GatewayAuth gatewayAuth;
    @Autowired
    UserMapper userMapper;

    @GetMapping("/cmdExecutor")
    public ResponseEntity cmdExecutor (String cmd) throws MyException {
        LocalShellRunner.runCommand("cmd /c "+ cmd);
        return new ResponseBuilder().setData(LocalShellRunner.getResult().toString()).getResponseEntity();
    }

    @GetMapping("/shellExecutor")
    public ResponseEntity shellExecutor (String cmd) throws MyException {
        LocalShellRunner.runCommand("sh " + cmd);
        return new ResponseBuilder().setData(LocalShellRunner.getResult().toString()).getResponseEntity();
    }

    @GetMapping("/bashExecutor")
    public ResponseEntity bashExecutor (String cmd) throws MyException {
        LocalShellRunner.runCommand("bash " + cmd);
        return new ResponseBuilder().setData(LocalShellRunner.getResult().toString()).getResponseEntity();
    }

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
