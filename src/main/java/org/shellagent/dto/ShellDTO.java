package org.shellagent.dto;

import org.jetbrains.annotations.NotNull;
import org.shellagent.entity.Property;
import org.shellagent.exception.MyException;
import org.shellagent.services.PropertyService;
import org.shellagent.utils.Constant;

import java.util.List;

public class ShellDTO {
    @NotNull
    String ip;
    String username; // 服务器登录用户名
    String localpath; // 脚本在resource目录下的路径
    String remotepath; // 要上传到服务器的目标路径
    String name; // 脚本名称
    String commandContext; // 直接执行命令时，执行的内容

    public String getCommandContext() {
        return commandContext;
    }

    public void setCommandContext(String commandContext) {
        this.commandContext = commandContext;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalpath() {
        return localpath;
    }

    public void setLocalpath(String localpath) {
        this.localpath = localpath;
    }

    public String getRemotepath() {
        return remotepath;
    }

    public void setRemotepath(String remotepath) {
        this.remotepath = remotepath;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
