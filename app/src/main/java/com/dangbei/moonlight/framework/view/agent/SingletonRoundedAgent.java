package com.dangbei.moonlight.framework.view.agent;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.dangbei.moonlight.framework.view.IRoundedCapability;


/**
 * view切圓角的代理类
 */
public class SingletonRoundedAgent {


    public static final ViewOutlineProvider ROUND = new ViewOutlineProvider() {

        @Override
        public void getOutline(View view, Outline outline) {

            if (view instanceof IRoundedCapability && ((IRoundedCapability) view).getRoundRadius() > 0) {
                outline.setRoundRect(
                        0,
                        0,
                        view.getWidth(),
                        view.getHeight(),
                        ((IRoundedCapability) view).getRoundRadius()
                );
                outline.setAlpha(0.45f);
            }
        }
    };


    public static void roundCorner(View v, int px) {
        if (v == null || px <= 0) {
            return;
        }
        v.setClipToOutline(true);
        v.setOutlineProvider(ROUND);
    }
}
