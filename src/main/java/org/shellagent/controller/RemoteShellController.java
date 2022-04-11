package org.shellagent.controller;

import org.shellagent.dto.ShellDTO;
import org.shellagent.exception.MyException;
import org.shellagent.mapper.UserMapper;
import org.shellagent.services.FileService;
import org.shellagent.services.GatewayAuth;
import org.shellagent.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/remote")
@RestController
public class RemoteShellController {

    @Autowired
    GatewayAuth gatewayAuth;
    @Autowired
    UserMapper userMapper;
    @Autowired
    FileService fileService;

    @GetMapping("/cmdExecutor")
    public ResponseEntity cmdExecutor (ShellDTO shellDTO) throws MyException {
        RemoteShellRunner rs = new RemoteShellRunner(
                shellDTO.getIp(),shellDTO.getUsername(),"password");
        rs.login();
        rs.runCommand("cmd /c " + shellDTO.getCommandContext());
        return new ResponseBuilder().setData(rs.getResult()).getResponseEntity();
    }

    @GetMapping("/shellExecutor")
    public ResponseEntity shellExecutor (ShellDTO shellDTO) throws MyException {
        RemoteShellRunner rs = new RemoteShellRunner(
                shellDTO.getIp(),shellDTO.getUsername(),"password");
        rs.login();
        rs.runCommand("sh " + shellDTO.getCommandContext());
        return new ResponseBuilder().setData(rs.getResult()).getResponseEntity();
    }

    @GetMapping("/bashExecutor")
    public ResponseEntity bashExecutor (ShellDTO shellDTO) throws MyException {
        RemoteShellRunner rs = new RemoteShellRunner(
                shellDTO.getIp(),shellDTO.getUsername(),"password");
        rs.login();
        rs.runCommand(shellDTO.getCommandContext());
        return new ResponseBuilder().setData(rs.getResult()).getResponseEntity();
    }

    @PostMapping("/shellRunner")
    public ResponseEntity shellRunner (@RequestBody ShellDTO shellDTO) throws MyException {
        // TODO 从deploy_properties表获取SERVER_PASS
        RemoteShellRunner rs = new RemoteShellRunner(
                shellDTO.getIp(),shellDTO.getUsername(),"password");
        rs.login();
        fileService.uploadScript(rs, shellDTO.getName(), shellDTO.getLocalpath());
        rs.runCommand("sh " + shellDTO.getName());
        return new ResponseBuilder().setData(rs.getResult()).getResponseEntity();
    }
}
