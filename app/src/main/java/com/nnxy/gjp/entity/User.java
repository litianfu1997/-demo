package com.nnxy.gjp.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.gwj.mygreendao.greendao.gen.DaoSession;
import com.gwj.mygreendao.greendao.gen.AccountDao;
import com.gwj.mygreendao.greendao.gen.UserDao;

/**
 * @Classname User
 * @Date 2019/4/26 15:22
 * @author litianfu
 * @Email 1035869369@qq.com
 */
@Entity
public class User {
    @Id
    private Integer userId;
    private String userCode;
    private String userName;//用户昵称
    private String password;
    private String preLogin;
    private String userPhone;

    /**
     * 一对多引用账务表
     */
    @ToMany(referencedJoinProperty ="accId")
    private List<Account> accountList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;

    @Generated(hash = 324588464)
    public User(Integer userId, String userCode, String userName, String password,
            String preLogin, String userPhone) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.password = password;
        this.preLogin = preLogin;
        this.userPhone = userPhone;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPreLogin() {
        return this.preLogin;
    }

    public void setPreLogin(String preLogin) {
        this.preLogin = preLogin;
    }

    public String getUserPhone() {
        return this.userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1588404143)
    public List<Account> getAccountList() {
        if (accountList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AccountDao targetDao = daoSession.getAccountDao();
            List<Account> accountListNew = targetDao._queryUser_AccountList(userId);
            synchronized (this) {
                if (accountList == null) {
                    accountList = accountListNew;
                }
            }
        }
        return accountList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1511731010)
    public synchronized void resetAccountList() {
        accountList = null;
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
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }


}
