package com.bimface.sdk.config;

import com.bimface.sdk.constants.BimfaceConstants;

/**
 * API调用地址入口
 * 
 * @author bimface, 2016-06-01.
 */
public final class Endpoint {

    private String apiHost  = BimfaceConstants.API_HOST; // API地址
    private String fileHost = BimfaceConstants.FILE_HOST;// 文件API地址

    public Endpoint() {
    }

    public Endpoint(String apiHost, String fileHost) {
        check(apiHost, fileHost);
        this.apiHost = apiHost;
        this.fileHost = fileHost;
    }

    public String getApiHost() {
        return apiHost;
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

    public String getFileHost() {
        return fileHost;
    }

    public void setFileHost(String fileHost) {
        this.fileHost = fileHost;
    }

    private void check(String apiHost, String fileHost) {
        if (apiHost == null || apiHost.equals("")) {
            throw new IllegalArgumentException("apiHost should not be null or empty.");
        }
        if (fileHost == null || fileHost.equals("")) {
            throw new IllegalArgumentException("fileHost should not be null or empty.");
        }
    }
}
