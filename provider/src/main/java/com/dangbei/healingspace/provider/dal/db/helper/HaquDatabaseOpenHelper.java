package com.dangbei.healingspace.provider.dal.db.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.wangjie.rapidorm.core.connection.DatabaseProcessor;
import com.wangjie.rapidorm.core.connection.RapidORMConnection;
import com.wangjie.rapidorm.core.delegate.database.RapidORMDefaultSQLiteDatabaseDelegate;
import com.wangjie.rapidorm.core.delegate.database.RapidORMSQLiteDatabaseDelegate;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 6/25/15.
 */
public class HaquDatabaseOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION_V1_0_0 = 1;
    private static final int VERSION_CURRENT = VERSION_V1_0_0;
    private RapidORMConnection rapidORMConnection;
    private RapidORMSQLiteDatabaseDelegate rapidORMSQLiteDatabaseDelegate;


    public HaquDatabaseOpenHelper(Context context, String name, RapidORMConnection rapidORMConnection) {
        this(context, name, VERSION_CURRENT, rapidORMConnection);
        this.rapidORMConnection = rapidORMConnection;
    }

    public HaquDatabaseOpenHelper(Context context, String name, int version, RapidORMConnection rapidORMConnection) {
        this(context, name, null, version, rapidORMConnection);
        this.rapidORMConnection = rapidORMConnection;
    }

    public HaquDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, RapidORMConnection rapidORMConnection) {
        super(context, name, factory, version);
        this.rapidORMConnection = rapidORMConnection;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public HaquDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        rapidORMSQLiteDatabaseDelegate = new RapidORMDefaultSQLiteDatabaseDelegate(db);
        DatabaseProcessor databaseProcessor = rapidORMConnection.getDatabaseProcessor();
        databaseProcessor.initializeDatabase(rapidORMSQLiteDatabaseDelegate);
        databaseProcessor.createAllTable(rapidORMSQLiteDatabaseDelegate, true);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        rapidORMSQLiteDatabaseDelegate = new RapidORMDefaultSQLiteDatabaseDelegate(db);
        DatabaseProcessor databaseProcessor = rapidORMConnection.getDatabaseProcessor();
        databaseProcessor.initializeDatabase(rapidORMSQLiteDatabaseDelegate);
        // 把drop掉的表都构建起来
        // 主要用于建一些之前不含有表
        onCreate(db);
    }
}
