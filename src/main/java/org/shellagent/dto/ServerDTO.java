package org.shellagent.dto;

import org.jetbrains.annotations.NotNull;
import org.shellagent.entity.Property;
import org.shellagent.exception.MyException;
import org.shellagent.services.PropertyService;
import org.shellagent.utils.Constant;

import java.util.List;

public class ServerDTO {
    @NotNull
    String ip;
    String username;
    String password;
    String type;
    String logPath;
    String deployPath;
    String runPath;
    String backupPath;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getDeployPath() {
        return deployPath;
    }

    public void setDeployPath(String deployPath) {
        this.deployPath = deployPath;
    }

    public String getRunPath() {
        return runPath;
    }

    public void setRunPath(String runPath) {
        this.runPath = runPath;
    }

    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }

    public ServerDTO(String ip, String type) {
        setIp(ip);
        setType(type);
    }
    public ServerDTO() {}

    public void fillData(PropertyService propertyService, List<Property> serverInfo) throws MyException {
        setUsername(propertyService.getValueByType(serverInfo, Constant.PropertyType.USERNAME));
        setBackupPath(propertyService.getValueByType(serverInfo, Constant.PropertyType.BACKUP_PATH));
        setDeployPath(propertyService.getValueByType(serverInfo, Constant.PropertyType.DEPLOY_PATH));
        if (getType().equals(Constant.PropertyKey.SERVICE)) {
            setRunPath(propertyService.getValueByType(serverInfo, Constant.PropertyType.RUN_PATH));
            setLogPath(propertyService.getValueByType(serverInfo, Constant.PropertyType.LOG_PATH));
        }
    }
}
