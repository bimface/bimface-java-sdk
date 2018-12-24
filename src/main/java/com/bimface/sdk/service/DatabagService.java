package com.bimface.sdk.service;

import com.bimface.exception.BimfaceException;
import com.bimface.sdk.client.DataClient;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.utils.AssertUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DatabagService {
    private DataClient dataClient;
    private AccessTokenService accessTokenService;

    public DatabagService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.dataClient = DataClient.getDataClient(endpoint.getApiHost() + "/data/");
        this.accessTokenService = accessTokenService;
    }

    public InputStream getFileDatabagContent(Long fileId) throws BimfaceException {
        String dataBagUrl = getFileDatabagUrl(fileId);
        try {
            return new URL(dataBagUrl).openStream();
        } catch (IOException e) {
            throw new BimfaceException("download by url error, url: " + dataBagUrl, e);
        }
    }
    public String getFileDatabagUrl(Long fileId) throws BimfaceException{
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        String accessToken = accessTokenService.getAccessToken();
        return dataClient.getDatabagDownloadUrl(fileId, null, null, null, null, accessToken);
    }

    public Long getFileDatabagSize(Long fileId) throws BimfaceException{
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        return dataClient.getDatabagSize(fileId, accessTokenService.getAccessToken());
    }

    public String getFileDataBagRootUrl(Long fileId) throws BimfaceException {
        return dataClient.getFileDatabagRootUrl(fileId, accessTokenService.getAccessToken());
    }

    public String getIntegrateDatabagRootUrl(Long integrateId) throws BimfaceException {
        return dataClient.getIntegrateDatabagRootUrl(integrateId, accessTokenService.getAccessToken());
    }
}
