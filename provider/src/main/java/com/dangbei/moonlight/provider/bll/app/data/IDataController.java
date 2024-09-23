//package com.dangbei.healingspace.provider.bll.app.data;
//
//import com.dangbei.healingspace.provider.dal.db.dao.app.DBCategory;
//import com.dangbei.healingspace.provider.dal.db.dao.app.DBCurrentCover;
//import com.dangbei.healingspace.provider.dal.db.dao.app.DBCurrentScene;
//import com.dangbei.healingspace.provider.dal.db.dao.app.DbHealingSpace;
//import com.dangbei.healingspace.provider.dal.db.dao.app.DBSource;
//import com.dangbei.healingspace.provider.dal.db.dao.app.DBSourceRankInScene;
//
//import java.util.List;
//
///**
// * 数据控制中心的接口类
// */
//public interface IDataController {
//
//    /**
//     * 添加或者更新本地某个itemScene
//     * 这个更新是全部属性更新 用 dbScene的值
//     *
//     * @param dbHealingSpace 数据库场景信息
//     */
//    void addOrUpdateScene(DbHealingSpace dbHealingSpace);
//
//    /**
//     * 添加或者更新本地某个dbSource
//     * 这个更新是全部属性更新 用 dbSource的值
//     *
//     * @param dbSource
//     */
//    void addOrUpdateSource(DBSource dbSource);
//
//    /**
//     * 添加或者更新某个category的值
//     * 这个更新是全部属性更新 用 dbCategory 的值
//     *
//     * @param dbCategory
//     */
//    void addOrUpdateCategory(DBCategory dbCategory);
//
//    /**
//     * 删除某个category
//     *
//     * @param dbCategory
//     */
//    void deleteCategory(DBCategory dbCategory);
//
//    /**
//     * 添加或者更新某个当前选中的场景
//     * 这个更新是全部属性更新 用 dbCurrentScene 的值
//     *
//     * @param dbCurrentScene
//     */
//    void addOrUpdateCurrentSelectScene(DBCurrentScene dbCurrentScene);
//
//    /**
//     * 添加或者更新某个当前选中的场景
//     * 这个更新是全部属性更新 用 dbCurrentCover 的值
//     *
//     * @param dbCurrentCover
//     */
//    void addOrUpdateCurrentSelectCover(DBCurrentCover dbCurrentCover);
//
//    /**
//     * 添加或者更新某个场景的排序
//     *
//     * @param dbSourceRankInScene
//     */
//    void addOrUpdateSourceRankInScene(DBSourceRankInScene dbSourceRankInScene);
//
//    /**
//     * 查询当前选中的场景信息
//     *
//     * @return
//     */
//    DBCurrentScene queryCurrentScene();
//
//    /**
//     * 查询当前选中的场景信息
//     *
//     * @return
//     */
//    DBCurrentCover queryCurrentCover();
//
//    /**
//     * 查询当前选中的资源列表
//     *
//     * @return 当前选中的资源
//     */
//    List<DBSource> queryCurrentSceneSourceList();
//
//    /**
//     * 根据场景id 进行媒资的信息查询
//     *
//     * @param sceneId 场景id
//     * @return 媒资
//     */
//    List<DBSource> querySourceList(int sceneId);
//
//    /**
//     * 根据场景id 获取第一个媒资信息
//     *
//     * @param sceneId
//     * @return
//     */
//    DBSource queryFirstSource(int sceneId);
//
//    /**
//     * 查询一个source的实体类
//     *
//     * @param sourceId source的id
//     * @return
//     */
//    DBSource querySource(int sourceId);
//
//    /**
//     * 根据分类id 查询分类信息
//     *
//     * @param categoryId
//     * @return
//     */
//    DBCategory queryCategory(int categoryId);
//    /**
//     * 根据分类id 查询场景列表
//     *
//     * @param categoryId
//     * @return
//     */
//    List<DbHealingSpace> querySceneListByCategory(int categoryId);
//
//    /**
//     * @return 所有场景信息
//     * @throws Exception e
//     */
//    List<DBCategory> queryCategoryAll() throws Exception;
//
//    /**
//     * 请求某种类型的分类数据
//     *
//     * @param categoryType
//     * @return
//     * @throws Exception
//     */
//    public List<DBCategory> queryCategoryList(int categoryType) throws Exception;
//
//    /**
//     * 根据场景id 查询场景信息
//     *
//     * @param sceneId
//     * @return
//     */
//    DbHealingSpace queryScene(int sceneId);
//
//    /**
//     * 删除媒资id
//     *
//     * @param sourceId
//     */
//    void deleteSource(int sourceId);
//
//    /**
//     * 根据场景id获取 该场景下的source排序
//     *
//     * @param sceneId
//     * @return
//     */
//    DBSourceRankInScene queryDBSourceRankInScene(int sceneId);
//
//    /**
//     * 清空全部
//     */
//    void clearAll();
//
//
//}
