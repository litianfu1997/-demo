package com.dbmanager;

import android.content.Context;

import com.gwj.mygreendao.greendao.gen.DaoMaster;
import com.gwj.mygreendao.greendao.gen.DaoSession;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;


public class DaoManager {
    /***
     * 1.创建数据库
     * 2.创建数据库的表
     * 3.包含对数据库的CRUD
     * 4.对数据库的升级
     */

        private static final String TAG=DaoManager.class.getSimpleName();
        private static final String DB_NAME="mydb.sqlite";
        private volatile static  DaoManager manager;
        private static DaoMaster.DevOpenHelper helper;
        private static DaoMaster daoMaster;
        private static DaoSession daoSession;
        private Context context;


        public void init(Context context){

            this.context = context;
        }
        /***
         * 使用单例模式获得数据库操作对象
         * @return
         */
//        public static DaoManager getInstance(){
//            DaoManager instance = null;
//            if(manager==null){
//                synchronized (DaoManager.class){
//                    if (instance==null){
//                        instance=new DaoManager();
//                        manager=instance;
//                    }
//                }
//            }
//            return instance;
//        }
    public static DaoManager getInstance(){

        return new DaoManager();
    }

        /***
         * 判断是否存在数据库，如果没有则创建数据库
         * @return
         */
        public DaoMaster getDaoMaster(){
            if (daoMaster==null){
                DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(context,DB_NAME,null);
                daoMaster= new DaoMaster(helper.getWritableDatabase());
            }
            return daoMaster;
        }

        /***
         * 完成对数据库的增添改查的操作，仅仅只是一个接口；
         * @return
         */
        public  DaoSession getDaoSession(){
            if (daoSession==null){
                if (daoMaster==null){
                    daoMaster=getDaoMaster();
                }
                daoSession =daoMaster.newSession();
            }
            return daoSession;
        }

        /***
         * 打开输出日志的操作，默认是关闭的
         */
        public void setDebug(){
            QueryBuilder.LOG_SQL=true;
            QueryBuilder.LOG_VALUES=true;
        }
        /***
         * 关闭数据库
         */
        public void claoseConnection(){
            closeDaoSession();
            closeHelper();
        }
        public void closeHelper(){
            if (helper!=null){
                helper.close();
                helper=null;
            }
        }
        public void closeDaoSession(){
            if (daoSession!=null){
                daoSession.clear();
                daoSession=null;
            }
        }



}
