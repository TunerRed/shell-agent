package org.shellagent.entity;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class User implements Serializable {
    @NotNull
    private String username;

    private String password;

    private String grantServerSeq;

    private String phone;

    private String root;

    private String label1;

    private String label2;

    private static final long serialVersionUID = 1L;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getGrantServerSeq() {
        return grantServerSeq;
    }

    public void setGrantServerSeq(String grantServerSeq) {
        this.grantServerSeq = grantServerSeq == null ? null : grantServerSeq.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root == null ? null : root.trim();
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1 == null ? null : label1.trim();
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2 == null ? null : label2.trim();
    }
}
