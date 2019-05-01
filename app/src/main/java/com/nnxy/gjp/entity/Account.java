package com.nnxy.gjp.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import com.gwj.mygreendao.greendao.gen.DaoSession;
import com.gwj.mygreendao.greendao.gen.UserDao;
import com.gwj.mygreendao.greendao.gen.AccountDao;

/**
 * @Classname Account
 * @Date 2019/4/26 15:22
 * @author litianfu
 * @Email 1035869369@qq.com
 */
@Entity
public class Account {
    @Id
    private Long accId;
    private String accCreateDate;
    private Boolean accType;
    private String accStyle;
    private Double accMoney;//金额
    private String accNote;//备注
    private Boolean accIsDel;//是否被删除

    //用户操作的标志位
    /**
     * 0 代表无操作
     * 1 代表需要更新数据库中的此记录
     * 2 代表是一条新纪录,需要插入数据库
     */
    private Long operateFlag;
    /**
     * 一对一引用用户表
     */
    private Long userId;
    @ToOne(joinProperty = "userId")
    private User user;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 335469827)
    private transient AccountDao myDao;
    @Generated(hash = 1839263782)
    public Account(Long accId, String accCreateDate, Boolean accType,
            String accStyle, Double accMoney, String accNote, Boolean accIsDel,
            Long operateFlag, Long userId) {
        this.accId = accId;
        this.accCreateDate = accCreateDate;
        this.accType = accType;
        this.accStyle = accStyle;
        this.accMoney = accMoney;
        this.accNote = accNote;
        this.accIsDel = accIsDel;
        this.operateFlag = operateFlag;
        this.userId = userId;
    }
    @Generated(hash = 882125521)
    public Account() {
    }
    public Long getAccId() {
        return this.accId;
    }
    public void setAccId(Long accId) {
        this.accId = accId;
    }
    public String getAccCreateDate() {
        return this.accCreateDate;
    }
    public void setAccCreateDate(String accCreateDate) {
        this.accCreateDate = accCreateDate;
    }
    public Boolean getAccType() {
        return this.accType;
    }
    public void setAccType(Boolean accType) {
        this.accType = accType;
    }
    public String getAccStyle() {
        return this.accStyle;
    }
    public void setAccStyle(String accStyle) {
        this.accStyle = accStyle;
    }
    public Double getAccMoney() {
        return this.accMoney;
    }
    public void setAccMoney(Double accMoney) {
        this.accMoney = accMoney;
    }
    public String getAccNote() {
        return this.accNote;
    }
    public void setAccNote(String accNote) {
        this.accNote = accNote;
    }
    public Boolean getAccIsDel() {
        return this.accIsDel;
    }
    public void setAccIsDel(Boolean accIsDel) {
        this.accIsDel = accIsDel;
    }
    public Long getOperateFlag() {
        return this.operateFlag;
    }
    public void setOperateFlag(Long operateFlag) {
        this.operateFlag = operateFlag;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 859885876)
    public User getUser() {
        Long __key = this.userId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 496399742)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            userId = user == null ? null : user.getUserId();
            user__resolvedKey = userId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1812283172)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAccountDao() : null;
    }


}
