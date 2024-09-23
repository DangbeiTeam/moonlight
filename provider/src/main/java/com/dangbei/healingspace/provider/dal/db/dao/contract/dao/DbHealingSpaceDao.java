package com.dangbei.healingspace.provider.dal.db.dao.contract.dao;

import com.dangbei.healingspace.provider.dal.db.dao.XBaseDao;
import com.dangbei.healingspace.provider.dal.db.dao.app.DbHealingSpace;

import java.util.List;

public interface DbHealingSpaceDao extends XBaseDao<DbHealingSpace> {
    /**
     * 根据id 查询治愈空间
     *
     * @param id
     * @return
     * @throws Exception
     */
    DbHealingSpace queryScene(int id) throws Exception;

    /**
     * 根据【治愈空间类型】查询所有的【同类型治愈空间】列表
     *
     * @param healingSpaceType
     * @return
     */
    List<DbHealingSpace> queryHealingSpaceList(int healingSpaceType) throws Exception;


    /**
     * 是否为空
     *
     * @return
     * @throws Exception
     */
    boolean isEmpty() throws Exception;

}
