package com.nnxy.gjp.entity;

/**
 * @Classname Account
 * @Date 2019/4/26 15:22
 * @author litianfu
 * @Email 1035869369@qq.com
 */

public class Account {
    private Integer accId;
    private String accCreateDate;
    private Boolean accType;
    private String accStyle;
    private Double accMoney;
    private String accNote;
    private Boolean accIsDel;

    //用户操作的标志位
    /**
     * 0 代表无操作
     * 1 代表需要更新数据库中的此记录
     * 2 代表是一条新纪录,需要插入数据库
     */
    private Integer operateFlag;
    /**
     * 一对一引用用户表
     */
    private User user;

    public Account() {
    }

    public Account(Integer accId, String accCreateDate, Boolean accType, String accStyle, Double accMoney, String accNote, Boolean accIsDel, Integer operateFlag, User user) {
        this.accId = accId;
        this.accCreateDate = accCreateDate;
        this.accType = accType;
        this.accStyle = accStyle;
        this.accMoney = accMoney;
        this.accNote = accNote;
        this.accIsDel = accIsDel;
        this.operateFlag = operateFlag;
        this.user = user;
    }

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public String getAccCreateDate() {
        return accCreateDate;
    }

    public void setAccCreateDate(String accCreateDate) {
        this.accCreateDate = accCreateDate;
    }

    public Boolean getAccType() {
        return accType;
    }

    public void setAccType(Boolean accType) {
        this.accType = accType;
    }

    public String getAccStyle() {
        return accStyle;
    }

    public void setAccStyle(String accStyle) {
        this.accStyle = accStyle;
    }

    public Double getAccMoney() {
        return accMoney;
    }

    public void setAccMoney(Double accMoney) {
        this.accMoney = accMoney;
    }

    public String getAccNote() {
        return accNote;
    }

    public void setAccNote(String accNote) {
        this.accNote = accNote;
    }

    public Boolean getAccIsDel() {
        return accIsDel;
    }

    public void setAccIsDel(Boolean accIsDel) {
        this.accIsDel = accIsDel;
    }

    public Integer getOperateFlag() {
        return operateFlag;
    }

    public void setOperateFlag(Integer operateFlag) {
        this.operateFlag = operateFlag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accId=" + accId +
                ", accCreateDate='" + accCreateDate + '\'' +
                ", accType=" + accType +
                ", accStyle='" + accStyle + '\'' +
                ", accMoney=" + accMoney +
                ", accNote='" + accNote + '\'' +
                ", accIsDel=" + accIsDel +
                ", operateFlag=" + operateFlag +
                '}';
    }
}
