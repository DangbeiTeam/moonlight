package com.dangbei.healingspace.provider.dal.db.dao;

import com.wangjie.rapidorm.core.dao.BaseDao;

import java.util.Collection;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/7/16.
 */
public interface XBaseDao<T> extends BaseDao<T> {
    boolean isExist(T model) throws Exception;

    void insertOrUpdate(T model) throws Exception;

    void insertOrUpdate(T... models) throws Exception;

    void insertOrUpdate(Collection<T> models) throws Exception;

    void insertOrUpdateInTx(T... models) throws Exception;

    void insertOrUpdateInTx(Collection<T> models) throws Exception;
}
