package com.bimface.sdk.service;

import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.http.ServiceClient;

/**
 * 业务处理的抽象类
 * 
 * @author bimface, 2016-06-01.
 */
public abstract class AbstractService {

    private ServiceClient serviceClient;
    private Endpoint      endpoint;

    public AbstractService() {
    }

    public AbstractService(ServiceClient serviceClient, Endpoint endpoint) {
        this.serviceClient = serviceClient;
        this.endpoint = endpoint;
    }

    public ServiceClient getServiceClient() {
        return serviceClient;
    }

    public String getApiHost() {
        return endpoint.getApiHost();
    }

    public String getFileHost() {
        return endpoint.getFileHost();
    }
}
