package com.dbmanager;

import android.content.Context;

import com.gwj.mygreendao.greendao.gen.AccountDao;
import com.gwj.mygreendao.greendao.gen.DaoMaster;
import com.gwj.mygreendao.greendao.gen.UserDao;
import com.nnxy.gjp.entity.Account;
import com.nnxy.gjp.entity.User;


import org.greenrobot.greendao.database.Database;
import org.jetbrains.annotations.TestOnly;

import java.util.List;


public class CommomUtils {
    private  DaoManager manager;
    private Database db;

    public CommomUtils(Context context){
        this.manager=DaoManager.getInstance();
        this.manager.init(context);
         db= new DaoMaster.DevOpenHelper(context, "mydb.sqlite").getWritableDb();
    }

    /***
     * 关闭数据库
     */
    public void close(){
        manager.claoseConnection();
    }

    /**
     * 增加用户
     * */
    public  boolean insertUser(User user){
        boolean flag = false;
       flag = manager.getDaoSession().insert(user) != -1 ? true : false;

        return flag;

    }

    /**
     * 增加账务
     * */
    public  boolean insertAccount(Account account){
        boolean flag = false;
        flag = manager.getDaoSession().insert(account) != -1 ? true : false;

        return flag;

    }

    public void updateAccount(Account account){
        manager.getDaoSession().update(account);
    }

    /**
     * 删除数据表
     *
     */

    public void deleteTable( ){

        db.execSQL("delete from ACCOUNT");
    }


    /***
     * 更新用户
     * @param user
     * @return
     */

    public void updateUser(User user){
        manager.getDaoSession().update(user);
    }

    /**
     * 删除用户
     * @param id
     */
    public void deleteUser(Long id){
        manager.getDaoSession().getUserDao().deleteByKey(id);
    }
    /***
     * 查询用户
     * @return
     */
    public List<User> queryAllUser(){//查询全部
        return manager.getDaoSession().loadAll(User.class);
    }

    public List<User> queryUser(Long id){
        return  manager.getDaoSession().queryBuilder(User.class).where(UserDao.Properties.UserId.eq(id)).list();
    }

    public String queryById4Password(Long key){
        return  manager.getDaoSession().load(User.class,key).getPassword();
    }
    public List<User> queryUser(String username){
        return  manager.getDaoSession().queryBuilder(User.class).where(UserDao.Properties.UserCode.eq(username)).list();
    }
    public List<User> queryUserId(String username){

        return  manager.getDaoSession().queryBuilder(User.class).where(UserDao.Properties.UserCode.eq(username)).list();
    }

    /**
     * 查询账务
     */
    public List<Account> queryAllAccountAndIsDel(Long id){
        return manager.getDaoSession().queryBuilder(Account.class).where(AccountDao.Properties.UserId.eq(id)
        ,AccountDao.Properties.OperateFlag.notEq(0)).list();
    }
    public List<Account> queryAllAccount(Long id){
        return manager.getDaoSession().queryBuilder(Account.class).where(AccountDao.Properties.UserId.eq(id)
        ,AccountDao.Properties.AccIsDel.eq(false)).list();
    }

    public List<Account> queryAllAccountByDate(Long id,String start,String end){
        return manager.getDaoSession().queryBuilder(Account.class).where(AccountDao.Properties.UserId.eq(id)
                ,AccountDao.Properties.AccIsDel.eq(false),AccountDao.Properties.AccCreateDate.between(start,end)).list();
    }

    public  List<Account> queryAccount(Long id,String startDate,String endDate,Double miniMoney,Double bigMoney,Boolean type){
        if (!startDate.trim().equals("")){

            endDate="2100-01-01";
           return manager.getDaoSession().queryBuilder(Account.class).where(AccountDao.Properties.AccCreateDate.between(startDate,endDate)
           ,AccountDao.Properties.UserId.eq(id)
           ,AccountDao.Properties.AccType.eq(type)
                   ,AccountDao.Properties.AccIsDel.eq(false)).list();

        }else if(!endDate.trim().equals("")){
            startDate = "1970-01-01";
           return manager.getDaoSession().queryBuilder(Account.class).where(AccountDao.Properties.AccCreateDate.between(startDate,endDate)
                   ,AccountDao.Properties.UserId.eq(id)
           ,AccountDao.Properties.AccType.eq(type)
                   ,AccountDao.Properties.AccIsDel.eq(false)).list();
        }else if(miniMoney!=0){
            bigMoney = 100000000000d;
            return manager.getDaoSession().queryBuilder(Account.class).where(AccountDao.Properties.AccMoney.between(miniMoney,bigMoney)
                    ,AccountDao.Properties.UserId.eq(id)
                    ,AccountDao.Properties.AccType.eq(type)
                    ,AccountDao.Properties.AccIsDel.eq(false)).list();
        }else if (bigMoney!=0){
            return manager.getDaoSession().queryBuilder(Account.class).where(AccountDao.Properties.AccMoney.between(miniMoney,bigMoney)
                    ,AccountDao.Properties.UserId.eq(id)
            ,AccountDao.Properties.AccType.eq(type)
                    ,AccountDao.Properties.AccIsDel.eq(false)).list();
        } else {
            endDate="2100-01-01";
            startDate = "1970-01-01";
            bigMoney = 1000000000000d;

            return manager.getDaoSession().queryBuilder(Account.class).where(AccountDao.Properties.AccCreateDate.between(startDate,endDate)
            ,AccountDao.Properties.AccMoney.between(miniMoney,bigMoney)
            ,AccountDao.Properties.AccType.eq(type),AccountDao.Properties.AccIsDel.eq(false)
            ,AccountDao.Properties.UserId.eq(id)).list();
        }



    }


    /**
     * 删除账务
     */
    public void deleteAccount(Long id){
        manager.getDaoSession().getAccountDao().deleteByKey(id);
    }





}
