package com.bimface.sdk.client;

import com.bimface.exception.BimfaceException;
import com.bimface.file.bean.*;
import com.bimface.http.BimfaceResponseChecker;
import com.bimface.sdk.interfaces.FileInterface;
import com.glodon.paas.foundation.restclient.RESTClientBuilder;
import com.glodon.paas.foundation.restclient.RESTStreamRequestBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

public class FileClient extends AbstractClient {
    private final static Logger logger = LoggerFactory.getLogger(FileClient.class);

    private FileInterface fileClient;

    private static FileClient instance;

    public static synchronized FileClient getFileClient(String dataBaseUrl) {
        if (instance == null)
            instance = new FileClient(dataBaseUrl);
        return instance;
    }

    private FileClient(String fileBaseUrl) {
        RESTClientBuilder builder = new RESTClientBuilder()
                .serviceBaseUrl(fileBaseUrl)
                .responseChecker(new BimfaceResponseChecker());
        if (logger.isDebugEnabled()) {
            builder.enableHttpLoggingInterceptor();
        }

        this.fileClient = builder.build(FileInterface.class);
    }

    public FileBean uploadFileStream(@NotNull String fileName, String sourceId, @NotNull Long fileLength, InputStream inputStream, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        RequestBody requestBody = RESTStreamRequestBody.create(MediaType.parse("application/octet-stream"), fileLength, inputStream);
        return executeCall(fileClient.uploadFileStream(fileName, sourceId, fileLength, requestBody, accessToken));
    }

    public FileBean uploadFileByUrl(@NotNull String fileName, String sourceId, @NotNull String url, String etag,
                                    @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(fileClient.uploadFileByUrl(fileName, sourceId, url, etag, accessToken));
    }

    public FileBean uploadFileFromOSS(@NotNull String fileName, String sourceId, @NotNull String ossBucket, @NotNull String ossObjectKey,
                                      @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(fileClient.uploadFileFromOSS(fileName, sourceId, ossBucket, ossObjectKey, accessToken));
    }

    public UploadPolicyBean getUploadPolicy(@NotNull String fileName, String sourceId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(fileClient.getUploadPolicy(fileName, sourceId, accessToken));
    }

    public SupportFileBean getSupportFileTypes(@NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(fileClient.getSupportFileTypes(accessToken));
    }

    public void deleteFile(@NotNull Long fileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        executeCall(fileClient.deleteFile(fileId, accessToken));
    }

    public AppendFileBean createAppendFile(@NotNull String fileName, String sourceId, @NotNull Long fileLength, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(fileClient.createAppendFile(fileName, sourceId, fileLength, accessToken));
    }

    public AppendFileBean getAppendFile(@NotNull Long appendFileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(fileClient.getAppendFile(appendFileId, accessToken));
    }

    public AppendFileBean appendUpload(@NotNull Long appendFileId, Long position, RequestBody requestBody, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(fileClient.appendUpload(appendFileId, position, requestBody, accessToken));
    }

    public FileBean getFileMetaData(@NotNull Long fileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(fileClient.getFileMetaData(fileId, accessToken));
    }

    public FileBean getFile(@NotNull Long fileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(fileClient.getFile(fileId, accessToken));
    }

    public List<FileBean> getFiles(String suffix, String status,String startTime, String endTime, Long offset, Long rows, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(fileClient.getFiles(suffix, status, startTime, endTime, offset, rows, accessToken));
    }

    public FileUploadStatusBean getFileUploadStatus(@NotNull Long fileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(fileClient.getFileUploadStatus(fileId, accessToken));
    }

    public String getFileDownloadUrl(@NotNull Long fileId, String fileName, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(fileClient.getFileDownloadUrl(fileId, fileName, accessToken));
    }

    public FileBean uploadByPolicy(@NotNull String url, @NotNull RequestBody requestBody) throws BimfaceException {
        return executeCall(fileClient.uploadByPolicy(url, requestBody));
    }
}
