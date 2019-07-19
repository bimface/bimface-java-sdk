package com.bimface.sdk.service;


import com.bimface.exception.BimfaceException;
import com.bimface.sdk.client.FileClient;
import com.bimface.sdk.config.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 文件下载
 *
 * @author bimface, 2016-11-01.
 */
public class DownloadService  {
    private final static Logger logger = LoggerFactory.getLogger(DownloadService.class);
    private FileClient fileClient;
    private AccessTokenService accessTokenService;

    public DownloadService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.fileClient = FileClient.getFileClient(endpoint.getFileHost());
        this.accessTokenService = accessTokenService;
    }

    /**
     * 获取文件下载链接
     *
     * @param accessToken 登录后的授权凭证
     * @param fileId 文件Id
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getDownloadUrl(String accessToken, Long fileId) throws BimfaceException {
        return getDownloadUrl(fileId, null, accessToken);
    }

    public String getDownloadUrl(Long fileId) throws BimfaceException {
        return getDownloadUrl(accessTokenService.getAccessToken(), fileId);
    }

    /**
     * 获取文件下载链接
     *
     * @param fileId 文件Id
     * @param fileName 文件名
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getDownloadUrl(Long fileId, String fileName) throws BimfaceException {
        return getDownloadUrl(fileId, fileName, accessTokenService.getAccessToken());
    }

    public String getDownloadUrl(Long fileId, String fileName, String accessToken) throws BimfaceException {

        return fileClient.getFileDownloadUrl(fileId, fileName, accessToken);
    }

    /**
     * 获取文件流
     *
     * @param fileId 文件id
     * @param name 文件名称
     * @return 文件二进制输入流
     * @throws BimfaceException {@link BimfaceException}
     */
    public InputStream getFileContent(Long fileId, String name) throws BimfaceException {
        return getFileContent(fileId, name, accessTokenService.getAccessToken());
    }

    public InputStream getFileContent(Long fileId, String name, String accessToken) throws BimfaceException {
        String url;
        if (name == null) {
            url = getDownloadUrl(accessToken, fileId);
        } else {
            url = getDownloadUrl(fileId, name, accessToken);
        }
        logger.debug("download by url[{}]", url);
        try {
            return new URL(url).openStream();
        } catch (IOException e) {
            throw new BimfaceException("download by url error, url: " + url, e);
        }
    }
}
