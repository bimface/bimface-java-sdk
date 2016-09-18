package com.bimface.sdk;

import java.io.InputStream;

import com.bimface.sdk.bean.request.FileTransferRequest;
import com.bimface.sdk.bean.request.FileUploadRequest;
import com.bimface.sdk.bean.response.FileBean;
import com.bimface.sdk.bean.response.PropertiesBean;
import com.bimface.sdk.bean.response.ShareLinkBean;
import com.bimface.sdk.bean.response.SupportFileBean;
import com.bimface.sdk.bean.response.TransferBean;
import com.bimface.sdk.config.Config;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.config.authorization.AccessTokenStorage;
import com.bimface.sdk.config.authorization.Credential;
import com.bimface.sdk.config.authorization.DefaultAccessTokenStorage;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.service.AccessTokenService;
import com.bimface.sdk.service.PropertiesService;
import com.bimface.sdk.service.ShareLinkService;
import com.bimface.sdk.service.SignatureService;
import com.bimface.sdk.service.SupportFileService;
import com.bimface.sdk.service.TransferService;
import com.bimface.sdk.service.UploadService;
import com.bimface.sdk.service.ViewTokenService;

/**
 * 访问bimface服务的入口
 * 
 * @author bimface, 2016-06-01.
 */
public class BimfaceClient {

    private Credential         credential;        // APP证书
    private Endpoint           endpoint;          // API调用地址入口

    private ServiceClient      serviceClient;     // API请求的Client

    private AccessTokenService accessTokenService;
    private SupportFileService supportFileService;
    private UploadService      uploadService;
    private TransferService    transferService;
    private ViewTokenService   viewTokenService;
    private ShareLinkService   shareLinkService;
    private PropertiesService  propertiesService;
    private SignatureService   signatureService;

    /**
     * 构造BimfaceClient对象
     * 
     * @param appKey AppKey
     * @param appSecret AppSecret
     */
    public BimfaceClient(String appKey, String appSecret) {
        this(appKey, appSecret, null, null, null);
    }

    /**
     * 构造BimfaceClient对象
     * 
     * @param appKey AppKey
     * @param appSecret AppSecret
     * @param config 参数配置
     */
    public BimfaceClient(String appKey, String appSecret, Config config) {
        this(appKey, appSecret, null, config, null);
    }

    /**
     * 构造BimfaceClient对象
     * 
     * @param appKey AppKey
     * @param appSecret AppSecret
     * @param endpoint API调用地址入口
     * @param config 参数配置
     */
    public BimfaceClient(String appKey, String appSecret, Endpoint endpoint, Config config) {
        this(appKey, appSecret, endpoint, config, null);
    }

    /**
     * 构造BimfaceClient对象
     * 
     * @param appKey AppKey
     * @param appSecret AppSecret
     * @param endpoint API调用地址入口
     * @param config 参数配置
     * @param accessTokenStorage AccessToken的缓存方式
     */
    public BimfaceClient(String appKey, String appSecret, Endpoint endpoint, Config config,
                         AccessTokenStorage accessTokenStorage) {

        // 初始化APP证书
        this.credential = new Credential(appKey, appSecret);

        // 初始化API调用地址入口
        if (endpoint == null) {
            this.endpoint = new Endpoint();
        } else {
            this.endpoint = endpoint;
        }

        // 初始化API请求的Client
        if (config == null) {
            this.serviceClient = new ServiceClient(new Config());
        } else {
            this.serviceClient = new ServiceClient(config);
        }

        // 初始化缓存AccessToken的方式
        AccessTokenStorage usedAccessTokenStorage = null;
        if (accessTokenStorage == null) {
            usedAccessTokenStorage = new DefaultAccessTokenStorage();
        } else {
            usedAccessTokenStorage = accessTokenStorage;
        }

        // 初始化Service
        accessTokenService = new AccessTokenService(serviceClient, this.endpoint, credential, usedAccessTokenStorage);
        supportFileService = new SupportFileService(serviceClient, this.endpoint, accessTokenService);
        uploadService = new UploadService(serviceClient, this.endpoint, accessTokenService);
        uploadService.setSupportFileService(supportFileService);
        transferService = new TransferService(serviceClient, this.endpoint, accessTokenService);
        viewTokenService = new ViewTokenService(serviceClient, this.endpoint, accessTokenService);
        shareLinkService = new ShareLinkService(serviceClient, this.endpoint, accessTokenService);
        propertiesService = new PropertiesService(serviceClient, this.endpoint, accessTokenService);
        signatureService = new SignatureService(credential);
    }

    /**
     * 获取支持的文件格式及文件大小
     * 
     * @return 文件后缀名数组
     * @throws BimfaceException {@link BimfaceException}
     */
    public SupportFileBean getSupport() throws BimfaceException {
        return supportFileService.getSupport();
    }

    /**
     * 上传文件
     * 
     * @param fileUploadRequest {@link FileUploadRequest}
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean upload(FileUploadRequest fileUploadRequest) throws BimfaceException {
        return uploadService.upload(fileUploadRequest);
    }

    /**
     * 上传文件,文件流方式
     * 
     * @param name 文件名
     * @param contentLength 文件长度
     * @param inputStream 文件流
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean upload(String name, Long contentLength, InputStream inputStream) throws BimfaceException {
        return uploadService.upload(new FileUploadRequest(name, contentLength, inputStream));
    }

    /**
     * 上传文件,URL方式
     * 
     * @param name 文件名
     * @param url 文件下载地址
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean upload(String name, String url) throws BimfaceException {
        return uploadService.upload(new FileUploadRequest(name, url));
    }

    /**
     * 发起文件转换
     * 
     * @param fileTransferRequest {@link FileTransferRequest}
     * @return {@link TransferBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public TransferBean transfer(FileTransferRequest fileTransferRequest) throws BimfaceException {
        return transferService.transfer(fileTransferRequest);
    }

    /**
     * 发起文件转换,不设置callback地址
     * 
     * @param fileId 文件ID
     * @return {@link TransferBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public TransferBean transfer(Long fileId) throws BimfaceException {
        return transferService.transfer(new FileTransferRequest(fileId));
    }

    /**
     * 发起文件转换,设置callback地址
     * 
     * @param fileId 文件ID
     * @param callback 回调地址
     * @return {@link TransferBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public TransferBean transfer(Long fileId, String callback) throws BimfaceException {
        return transferService.transfer(new FileTransferRequest(fileId, callback));
    }

    /**
     * 获取文件转换状态
     * 
     * @param transferId 转换ID
     * @return {@link TransferBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public TransferBean getTransfer(String transferId) throws BimfaceException {
        return transferService.getTransfer(transferId);
    }

    /**
     * 获取viewToken, 用于模型预览的凭证
     * 
     * @param transferId 转换ID
     * @return viewToken
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewToken(String transferId) throws BimfaceException {
        return viewTokenService.grantViewToken(transferId);
    }

    /**
     * 创建分享链接
     * 
     * @param transferId 转换ID
     * @param activeHours 从当前算起，分享链接的有效时间，单位：小时; 如果为空，表示该分享链接永久有效
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShareLink(String transferId, Integer activeHours) throws BimfaceException {
        return shareLinkService.create(transferId, activeHours);
    }

    /**
     * 创建分享链接,永久有效
     * 
     * @param transferId 转换ID
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShareLink(String transferId) throws BimfaceException {
        return shareLinkService.create(transferId);
    }

    /**
     * 取消分享链接
     * 
     * @param transferId 转换ID
     * @throws BimfaceException {@link BimfaceException}
     */
    public void deleteShareLink(String transferId) throws BimfaceException {
        shareLinkService.delete(transferId);
    }

    /**
     * 获取模型对应构件下的属性
     * 
     * @param transferId 转换ID
     * @param elementId 构件ID
     * @return PropertiesBean {@link PropertiesBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public PropertiesBean getProperties(String transferId, String elementId) throws BimfaceException {
        return propertiesService.getProperties(transferId, elementId);
    }

    /**
     * 验证签名, 接收转换回调时使用
     * 
     * @param signature 签名字符
     * @param transferId 转换ID
     * @param status 转换状态(success || failed)
     * @return true: 验证成功, false: 校验失败
     * @throws BimfaceException {@link BimfaceException}
     */
    public boolean validateSignature(String signature, String transferId, String status,
                                     String nonce) throws BimfaceException {
        return signatureService.validate(signature, transferId, status, nonce);
    }

    public Credential getCredential() {
        return credential;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public ServiceClient getServiceClient() {
        return serviceClient;
    }

    public AccessTokenService getAccessTokenService() {
        return accessTokenService;
    }

    public SupportFileService getSupportFileService() {
        return supportFileService;
    }

    public UploadService getUploadService() {
        return uploadService;
    }

    public TransferService getTransferService() {
        return transferService;
    }

    public ViewTokenService getViewTokenService() {
        return viewTokenService;
    }

    public ShareLinkService getShareLinkService() {
        return shareLinkService;
    }

    public PropertiesService getPropertiesService() {
        return propertiesService;
    }

    public SignatureService getSignatureService() {
        return signatureService;
    }

}
