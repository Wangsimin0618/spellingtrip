package com.spellingtrip.example.liaotian;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.spellingtrip.example.CustomApplication;

import java.sql.SQLException;

public class SqliteHelper extends OrmLiteSqliteOpenHelper {

    private final String LOG_TAG = getClass().getSimpleName();

    // 数据库名字
    private static final String DATABASE_NAME = "im_user_cache.db";

    // 版本号
    private static final int DATABASE_VERSION = 1;

    private static SqliteHelper mInstance;

    private Dao<UserCacheInfo, Integer> mUserInfoDao = null;

    public SqliteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public synchronized static SqliteHelper getInstance(){
        if (mInstance == null) {
            ///TODO:: 把这里的 DemoApplication.getInstance() 换成自己的Application类实例对象。例如：new MainApplication()
            mInstance = new SqliteHelper(CustomApplication.context);
        }

        return mInstance;
    }

    /**
     * 创建SQLite数据库
     */
    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserCacheInfo.class);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Unable to create datbases", e);
        }
    }

    /**
     * 更新SQLite数据库
     */
    @Override
    public void onUpgrade(
            SQLiteDatabase sqliteDatabase,
            ConnectionSource connectionSource,
            int oldVer,
            int newVer) {
        try {
            TableUtils.dropTable(connectionSource, UserCacheInfo.class, true);

            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(LOG_TAG,
                    "Unable to upgrade database from version " + oldVer + " to new "
                            + newVer, e);
        }
    }

    public Dao<UserCacheInfo,Integer> getUserDao() throws SQLException{
        if(mUserInfoDao == null){
            try {
                mUserInfoDao = getDao(UserCacheInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mUserInfoDao;
    }

}
