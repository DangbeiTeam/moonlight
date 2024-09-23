package com.dangbei.healingspace.provider.dal.net.http.response;



import com.lerad.lerad_base_support.bridge.compat.subscriber.RxCompatException;

import java.io.Serializable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/4/16.
 */
public class BaseHttpResponse implements Serializable {

    public static final int CODE_SUCCESS = 200;

    protected Integer code;
    protected String message;

    public Integer getCode() {
        return code;
    }

    public int getCode(int defaultValue) {
        return null == code ? defaultValue : code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RxCompatException toCompatException() {
        return new RxCompatException(getCode(RxCompatException.CODE_DEFAULT), message);
    }

    /**
     * 业务是否成功
     */
    public boolean isBizSucceed(boolean defaultValue) {
        return null == code ? defaultValue : CODE_SUCCESS == code;
    }

    @Override
    public String toString() {
        return "BaseHttpResponse{" +
            ", code=" + code +
            ", message='" + message + '\'' +
            '}';
    }
}
