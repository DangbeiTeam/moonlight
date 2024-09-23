package com.dangbei.healingspace.provider.dal.db.helper;

import android.content.Context;

import androidx.annotation.NonNull;

import com.dangbei.healingspace.provider.bll.utils.Constant;
import com.dangbei.healingspace.provider.dal.db.dao.app.DbHealingSpace;
import com.dangbei.healingspace.provider.dal.db.dao.app.DbHealingSpace_RORM;
import com.wangjie.rapidorm.core.config.TableConfig;
import com.wangjie.rapidorm.core.connection.DatabaseProcessor;
import com.wangjie.rapidorm.core.connection.RapidORMConnection;
import com.wangjie.rapidorm.core.connection.RapidORMManager;
import com.wangjie.rapidorm.core.delegate.openhelper.RapidORMDefaultSQLiteOpenHelperDelegate;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 6/25/15.
 */
public class DatabaseFactory extends RapidORMConnection<RapidORMDefaultSQLiteOpenHelperDelegate> {

    private String databaseName;
    private Context context;


    private DatabaseFactory(Context context, String symbol) {
        super(symbol);
        this.context = context.getApplicationContext();
        if (this.context == null) {
            this.context = context;
        }
    }

    public synchronized static DatabaseFactory getInstance(Context context) {
        if (Holder.instance == null) {
            Holder.instance = new DatabaseFactory(context, Constant.DB.DB_SYMBOL);
        }
        return Holder.instance;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public synchronized boolean resetDatabase(@NonNull String databaseName){
        this.databaseName = databaseName;
        DatabaseProcessor databaseProcessor = RapidORMManager.getInstance()
                .getDatabaseProcessor(Constant.DB.DB_SYMBOL);
        databaseProcessor.resetRapidORMDatabaseOpenHelper(
                this.getRapidORMDatabaseOpenHelper(databaseName));
        databaseProcessor.getDb();
        return true;
    }

    @Override
    public synchronized boolean resetDatabaseIfCrash() {
        resetDatabase(databaseName);
        return true;
    }

    @Override
    protected RapidORMDefaultSQLiteOpenHelperDelegate getRapidORMDatabaseOpenHelper(@NonNull String databaseName) {
        return new RapidORMDefaultSQLiteOpenHelperDelegate(new HaquDatabaseOpenHelper(context, databaseName, this));
    }


    private static class Holder {
        private static DatabaseFactory instance;
    }

    @Override
    protected void registerTableConfigMapper(ConcurrentHashMap<Class, TableConfig> tableConfigMapper) {
        tableConfigMapper.put(DbHealingSpace.class, new DbHealingSpace_RORM());
        // register all table config here...
    }

}
