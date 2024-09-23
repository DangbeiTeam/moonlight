package com.dangbei.healingspace.provider.dal.db.dao.contract.impl;

import com.dangbei.healingspace.provider.dal.db.dao.XBaseDaoImpl;
import com.dangbei.healingspace.provider.dal.db.dao.app.DbHealingSpace;
import com.dangbei.healingspace.provider.dal.db.dao.app.DbHealingSpace_RORM;
import com.dangbei.healingspace.provider.dal.db.dao.contract.dao.DbHealingSpaceDao;
import com.dangbei.xfunc.utils.collection.CollectionUtil;
import com.wangjie.rapidorm.core.generate.builder.Where;

import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/4/16.
 */
public class DbHealingSpaceDaoImpl extends XBaseDaoImpl<DbHealingSpace> implements DbHealingSpaceDao {

    public DbHealingSpaceDaoImpl() {
        super(DbHealingSpace.class);
    }


    @Override
    public boolean isEmpty() throws Exception {
        List<DbHealingSpace> list =  queryAll();
        return CollectionUtil.isEmpty(list);
    }


    @Override
    public DbHealingSpace queryScene(int id) throws Exception {
        return queryBuilder().setWhere(
                Where.eq(DbHealingSpace_RORM.ID, id)
        ).queryFirst();
    }


    @Override
    public List<DbHealingSpace> queryHealingSpaceList(int healingSpaceType) throws Exception {
        return queryBuilder().setWhere(
                Where.eq(DbHealingSpace_RORM.SCENETYPE, healingSpaceType)
        ).query();
    }

}
