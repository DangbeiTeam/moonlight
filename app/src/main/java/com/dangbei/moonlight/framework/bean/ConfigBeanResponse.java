package com.dangbei.moonlight.framework.bean;

import com.dangbei.healingspace.provider.dal.net.http.response.BaseHttpResponse;
import java.io.Serializable;

/**
 * author : zhengxihong  e-mail : tomatozheng0212@gmail.com   time  : 2024/08/21
 */
public class ConfigBeanResponse extends BaseHttpResponse {

    /**
     * protocolVerCode : 1
     * title : 用户协议及隐私政策概要
     * content : 依据相关法律法规和政策，本机电视桌面由未来电视有限公司作为互联网电视集成平台方负责播控管理当贝网络科技有限公司提供技术支撑,用户协议文案用户协议文案。
     * protocol : http://sonyyktestapi.v5tv.com/moon/h5/protocol.html
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private int protocolVerCode;
        private String title;
        private String content;
        private String protocol;
        private String advance;

        public int getProtocolVerCode() {
            return protocolVerCode;
        }

        public void setProtocolVerCode(int protocolVerCode) {
            this.protocolVerCode = protocolVerCode;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getAdvance() {
            return advance;
        }

        public void setAdvance(String advance) {
            this.advance = advance;
        }

        @Override public String toString() {
            return "DataBean{" +
                    "protocolVerCode=" + protocolVerCode +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", protocol='" + protocol + '\'' +
                    '}';
        }
    }

    @Override public String toString() {
        return "ConfigBeanResponse{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
