package com.dangbei.healingspace.provider.bll.model;

import com.dangbei.healingspace.provider.dal.db.dao.app.DbHealingSpace;
import com.dangbei.healingspace.provider.dal.db.dao.contract.dao.DbHealingSpaceDao;
import com.dangbei.healingspace.provider.dal.db.dao.contract.impl.DbHealingSpaceDaoImpl;
import com.dangbei.healingspace.provider.dal.net.http.IHealingSpaceType;
import com.lerad.lerad_base_support.bridge.compat.RxCompat;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class HealingSpaceModel implements IHealingSpaceModel {

    /**
     * db:healingSpace
     */
    private DbHealingSpaceDao dbHealingSpaceDao;


    public HealingSpaceModel() {
        dbHealingSpaceDao = new DbHealingSpaceDaoImpl();
    }

    private List<DbHealingSpace> loadDefault() {
        List<DbHealingSpace> defaultList = new ArrayList<>();

        // 慢生活
        DbHealingSpace live = new DbHealingSpace();
        live.setId(100);
        live.setDefaultPic("");
        live.setName("慢生活");
        live.setSceneType(IHealingSpaceType.LIVE);
        live.setSnapshotPic("");
        defaultList.add(live);

        // 路飞
        DbHealingSpace lofi = new DbHealingSpace();
        lofi.setId(101);
        lofi.setDefaultPic("");
        lofi.setName("路飞");
        lofi.setSceneType(IHealingSpaceType.LO_FI);
        lofi.setSnapshotPic("");
        defaultList.add(lofi);

        // 音乐
        DbHealingSpace music = new DbHealingSpace();
        music.setId(102);
        music.setDefaultPic("");
        music.setName("音乐");
        music.setSceneType(IHealingSpaceType.MUSIC);
        music.setSnapshotPic("");
        defaultList.add(music);

        // 图片
        DbHealingSpace pic = new DbHealingSpace();
        pic.setId(103);
        pic.setDefaultPic("");
        pic.setName("艺术图片");
        pic.setSceneType(IHealingSpaceType.ART_PIC);
        pic.setSnapshotPic("");
        defaultList.add(pic);

        // 天气
        DbHealingSpace weather = new DbHealingSpace();
        weather.setId(104);
        weather.setDefaultPic("");
        weather.setName("信息-天气");
        weather.setSceneType(IHealingSpaceType.INFO_WEATHER);
        weather.setSnapshotPic("");
        defaultList.add(weather);

        return defaultList;
    }


    @Override
    public Observable<List<DbHealingSpace>> loadHealSpaceData() {

        return Observable
                .just("load")
                .compose(RxCompat.subscribeOnDb())
                .map(it -> {
                    if (!dbHealingSpaceDao.isEmpty()) {
                        return dbHealingSpaceDao.queryAll();
                    } else {
                        return loadDefault();
                    }
                });

    }


    @Override
    public Observable<List<DbHealingSpace>> loadHealSpaceBy(int healingSpaceType) {
        return null;
    }
}
