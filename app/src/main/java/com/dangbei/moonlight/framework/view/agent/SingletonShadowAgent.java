package com.dangbei.moonlight.framework.view.agent;

import android.view.View;

/**
 * 本示例采用了系统的 {@View setElevation}
 */
public class SingletonShadowAgent {

    public static void shadow(View v, float shadow) {
        if (v == null || shadow <= 0) {
            return;
        }
        v.setElevation(shadow);
    }
}
