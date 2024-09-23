package com.dangbei.healingspace.provider.bll.app.data;

import android.annotation.SuppressLint;

import com.dangbei.healingspace.provider.dal.db.dao.app.DbHealingSpace;
import com.dangbei.healingspace.provider.dal.db.dao.contract.dao.DbHealingSpaceDao;
import com.dangbei.healingspace.provider.dal.db.dao.contract.impl.DbHealingSpaceDaoImpl;

import java.util.List;

/**
 * 数据控制中心
 * 主要处理 缓存数据和网络数据的问题
 */
public class DataController {
    private DbHealingSpaceDao dbSceneDao;


    public void addOrUpdateScene(DbHealingSpace dbHealingSpace) {
        try {
            dbSceneDao.insertOrUpdate(dbHealingSpace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<DbHealingSpace> querySceneListByCategory(int categoryId) {
        try {
            return dbSceneDao.queryHealingSpaceList(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public DbHealingSpace queryScene(int sceneId) {
        try {
            return dbSceneDao.queryScene(sceneId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void clearAll() {
        try {
            dbSceneDao.deleteAll();
        } catch (Exception e) {
        }
    }

    public static class Holder {
        @SuppressLint("StaticFieldLeak")
        static DataController INSTANCE = new DataController();
    }

    public static DataController getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 需要在加载完数据后在进行调用
     */
    public void init() {
        dbSceneDao = new DbHealingSpaceDaoImpl();
    }

}
