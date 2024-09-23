package com.dangbei.healingspace.provider.dal.db.dao;

import com.dangbei.healingspace.provider.bll.utils.Constant;
import com.dangbei.xlog.XLog;
import com.wangjie.rapidorm.core.config.ColumnConfig;
import com.wangjie.rapidorm.core.connection.RapidORMManager;
import com.wangjie.rapidorm.core.dao.BaseDaoImpl;
import com.wangjie.rapidorm.core.delegate.database.RapidORMSQLiteDatabaseDelegate;
import com.wangjie.rapidorm.core.delegate.sqlitestatement.RapidORMSQLiteStatementDelegate;
import com.wangjie.rapidorm.core.generate.builder.Where;
import com.wangjie.rapidorm.core.generate.statement.util.SqlUtil;
import com.wangjie.rapidorm.util.func.RapidOrmFunc1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/4/16.
 */
public class XBaseDaoImpl<T> extends BaseDaoImpl<T> implements XBaseDao<T> {
    private static final String TAG = XBaseDaoImpl.class.getSimpleName();

    private RapidORMSQLiteStatementDelegate isExistSqlStatementDelegate;

    public XBaseDaoImpl(Class<T> clazz) {
        super(clazz, RapidORMManager.getInstance()
                .getDatabaseProcessor(Constant.DB.DB_SYMBOL));
        parseIsExistSql();
        isExistSqlStatementDelegate = parseIsExistSql();
    }

    private RapidORMSQLiteStatementDelegate parseIsExistSql() {
        try {
            List<ColumnConfig> pkColumnConfigs = tableConfig.getPkColumnConfigs();
            int len = 0;
            List<Where> wheres = new ArrayList<>();
            if (null != pkColumnConfigs && 0 < (len = pkColumnConfigs.size())) {
                StringBuilder builder = new StringBuilder("SELECT COUNT(");
                SqlUtil.formatName(builder, pkColumnConfigs.get(0).getColumnName());
                builder.append(") FROM ");
                SqlUtil.formatName(builder, tableConfig.getTableName());
                builder.append(" WHERE ");
                for (int i = 0; i < len; i++) {
                    wheres.add(Where.eq(pkColumnConfigs.get(i).getColumnName(), null));
                }
                Where finalWhere = Where.and(wheres);
                String sql = builder.append(finalWhere.getWhere()).toString();
                return getDatabase().compileStatement(sql);
            }
        } catch (Exception e) {
            XLog.e(TAG, e);
        }
        return null;
    }

    @Override
    public boolean isExist(T model) throws Exception {
        if (null == isExistSqlStatementDelegate || tableConfig.getPkColumnConfigs() == null || tableConfig.getPkColumnConfigs().isEmpty()) {
            throw new RuntimeException(tableConfig.getTableName() + " have no primary key, can not call this method. should override this method in dao class.");
        }
        final boolean[] result = new boolean[1];
        final RapidORMSQLiteDatabaseDelegate db = getDatabase();
        if (db.isDbLockedByCurrentThread()) {
            synchronized (tableConfig) {
                result[0] = isExitInternal(model);
            }
        } else {
            // Do TX to acquire a connection before locking the stmt to avoid deadlocks
            executeInTx(db, new RapidOrmFunc1() {
                @Override
                public void call() throws Exception {
                    synchronized (tableConfig) {
                        result[0] = isExitInternal(model);
                    }
                }
            });
        }
        return result[0];
    }

    private boolean isExitInternal(T model) {
        isExistSqlStatementDelegate.clearBindings();
        tableConfig.bindPkArgs(model, isExistSqlStatementDelegate, 0);
        return isExistSqlStatementDelegate.simpleQueryForLong() > 0;
    }

    @Override
    public void insertOrUpdate(T model) throws Exception {
        if (isExist(model)) {
            update(model);
        } else {
            insert(model);
        }
    }


    @Override
    public void insertOrUpdate(Collection<T> models) throws Exception {
        if (null != models) {
            for (T model : models) {
                insertOrUpdate(model);
            }
        }
    }


    @Override
    public void insertOrUpdateInTx(Collection<T> models) throws Exception {
        if (null != models) {
            executeInTx(new RapidOrmFunc1() {
                @Override
                public void call() throws Exception {
                    for (T model : models) {
                        insertOrUpdate(model);
                    }
                }
            });
        }
    }
}
