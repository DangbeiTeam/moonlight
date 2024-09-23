package com.dangbei.healingspace.provider.dal.net.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wangjiegulu.dal.request.gson.configuration.AnnotationDeserializationExclusionStrategy;
import com.wangjiegulu.dal.request.gson.configuration.AnnotationSerializationExclusionStrategy;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 10/31/16.
 */
public final class GsonHelper {
    private GsonHelper() {
    }

    /**
     * 原始的Gson
     */
    private static final Gson ORIGINAL_GSON;


    static {
        ORIGINAL_GSON = generateOriginalGsonBuilder().disableHtmlEscaping().create();
    }



    public static Gson getOriginalGson() {
        return ORIGINAL_GSON;
    }

    private static GsonBuilder generateOriginalGsonBuilder() {
        return new GsonBuilder()
                .addSerializationExclusionStrategy(new AnnotationSerializationExclusionStrategy())
                .addDeserializationExclusionStrategy(new AnnotationDeserializationExclusionStrategy());
    }
}
