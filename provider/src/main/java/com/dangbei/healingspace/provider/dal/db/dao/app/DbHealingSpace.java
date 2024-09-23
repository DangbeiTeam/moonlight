package com.dangbei.healingspace.provider.dal.db.dao.app;

import com.wangjie.rapidorm.api.annotations.Column;
import com.wangjie.rapidorm.api.annotations.Table;

import java.io.Serializable;

/**
 * 场景
 */
@Table
public class DbHealingSpace implements Serializable {

    /**
     * 定义id
     */
    @Column(primaryKey = true)
    int id;


    /**
     * 场景类型
     */
    @Column
    int sceneType;

    /**
     * 标题
     */
    @Column
    String name;

    /**
     * 副标题
     */
    @Column
    String subTitle;

    /**
     * 略说图
     */
    @Column
    String snapshotPic;

    /**
     * 默认背景
     */
    @Column
    String defaultPic;

    /**
     *
     */
    @Column
    String backendJson;


    public int getSceneType() {
        return sceneType;
    }

    public void setSceneType(int sceneType) {
        this.sceneType = sceneType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSnapshotPic() {
        return snapshotPic;
    }

    public void setSnapshotPic(String snapshotPic) {
        this.snapshotPic = snapshotPic;
    }

    public String getDefaultPic() {
        return defaultPic;
    }

    public void setDefaultPic(String defaultPic) {
        this.defaultPic = defaultPic;
    }

    public String getBackendJson() {
        return backendJson;
    }

    public void setBackendJson(String backendJson) {
        this.backendJson = backendJson;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DbHealingSpace{" +
                "sceneType=" + sceneType +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", snapshotPic='" + snapshotPic + '\'' +
                ", defaultPic='" + defaultPic + '\'' +
                ", backendJson='" + backendJson + '\'' +
                '}';
    }
}
