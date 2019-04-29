package com.nnxy.gjp.entity;

import java.util.List;

/**
 * @Classname User
 * @Date 2019/4/26 15:22
 * @author litianfu
 * @Email 1035869369@qq.com
 */
public class User {
    private Integer userId;
    private String userCode;
    private String userName;//用户昵称
    private String password;
    private String preLogin;
    private String userPhone;

    /**
     * 一对多引用账务表
     */
    private List<Account> accountList;

    public User() {
    }

    public User(Integer userId, String userCode, String userName, String password, String preLogin, String userPhone, List<Account> accountList) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.password = password;
        this.preLogin = preLogin;
        this.userPhone = userPhone;
        this.accountList = accountList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPreLogin() {
        return preLogin;
    }

    public void setPreLogin(String preLogin) {
        this.preLogin = preLogin;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", preLogin='" + preLogin + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}
