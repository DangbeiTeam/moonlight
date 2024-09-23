package com.dangbei.healingspace.provider.bll.model;

import com.dangbei.healingspace.provider.dal.db.dao.app.DbHealingSpace;

import java.util.List;

import io.reactivex.Observable;

public interface IHealingSpaceModel {

    /**
     * 主要的治愈空间类型
     *
     * @return
     */
    Observable<List<DbHealingSpace>> loadHealSpaceData();

    /**
     * 按类型获取治愈空间列表
     *
     * @param healingSpaceType
     * @return
     */
    Observable<List<DbHealingSpace>> loadHealSpaceBy(int healingSpaceType);

}
