package com.bimface.sdk;

import java.util.List;

import com.bimface.sdk.bean.request.FileTransferRequest;
import com.bimface.sdk.bean.request.FileUploadRequest;
import com.bimface.sdk.bean.response.FileBean;
import com.bimface.sdk.bean.response.ShareLinkBean;
import com.bimface.sdk.bean.response.TransferBean;
import com.bimface.sdk.config.Config;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.config.authorization.AccessTokenStorage;
import com.bimface.sdk.config.authorization.Credential;
import com.bimface.sdk.config.authorization.DefaultAccessTokenStorage;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.service.AccessTokenService;
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

    private AccessTokenStorage accessTokenStorage;
    private AccessTokenService accessTokenService;
    private SupportFileService supportFileService;
    private UploadService      uploadService;
    private TransferService    transferService;
    private ViewTokenService   viewTokenService;
    private ShareLinkService   shareLinkService;
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
        if (accessTokenStorage == null) {
            this.accessTokenStorage = new DefaultAccessTokenStorage();
        } else {
            this.accessTokenStorage = accessTokenStorage;
        }

        // 初始化Service
        accessTokenService = new AccessTokenService(serviceClient, this.endpoint, credential, this.accessTokenStorage);
        supportFileService = new SupportFileService(serviceClient, this.endpoint, accessTokenService);
        uploadService = new UploadService(serviceClient, this.endpoint, accessTokenService);
        uploadService.setSupportFileService(supportFileService);
        transferService = new TransferService(serviceClient, this.endpoint, accessTokenService);
        viewTokenService = new ViewTokenService(serviceClient, this.endpoint, accessTokenService);
        shareLinkService = new ShareLinkService(serviceClient, this.endpoint, accessTokenService);
        signatureService = new SignatureService(credential);
    }

    /**
     * 获取支持的文件格式
     * 
     * @return 文件后缀名数组
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<String> getSupportFile() throws BimfaceException {
        return supportFileService.getSupportFile();
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
     * 获取文件转换状态
     * 
     * @param viewId 预览ID
     * @return {@link TransferBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public TransferBean getTransfer(String viewId) throws BimfaceException {
        return transferService.getTransfer(viewId);
    }

    /**
     * 获取viewToken, 用于模型预览的凭证
     * 
     * @param viewId 预览ID
     * @return viewToken
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewToken(String viewId) throws BimfaceException {
        return viewTokenService.grantViewToken(viewId);
    }

    /**
     * 创建分享链接
     * 
     * @param viewId 预览ID
     * @param activeHours 从当前算起，分享链接的有效时间，单位：小时; 如果为空，表示该分享链接永久有效
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShareLink(String viewId, Integer activeHours) throws BimfaceException {
        return shareLinkService.create(viewId, activeHours);
    }

    /**
     * 获得预览文件的分享链接
     * 
     * @param viewId 预览ID
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean getShareLink(String viewId) throws BimfaceException {
        return shareLinkService.get(viewId);
    }

    /**
     * 修改分享链接
     * 
     * @param viewId 预览ID
     * @param activeHours 从当前算起，分享链接的有效时间，单位：小时; 如果为空，表示该分享链接永久有效
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean updateShareLink(String viewId, Integer activeHours) throws BimfaceException {
        return shareLinkService.update(viewId, activeHours);
    }

    /**
     * 删除分享链接
     * 
     * @param viewId 预览ID
     * @throws BimfaceException {@link BimfaceException}
     */
    public void deleteShareLink(String viewId) throws BimfaceException {
        shareLinkService.delete(viewId);
    }

    /**
     * 验证签名, 接收转换回调时使用
     * 
     * @param signature 签名字符
     * @param viewId 预览ID
     * @param status 转换状态(success || failed)
     * @return true: 验证成功, false: 校验失败
     * @throws BimfaceException {@link BimfaceException}
     */
    public boolean validateSignature(String signature, String viewId, String status) throws BimfaceException {
        return signatureService.validate(signature, viewId, status);
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

    public AccessTokenStorage getAccessTokenStorage() {
        return accessTokenStorage;
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

    public SignatureService getSignatureService() {
        return signatureService;
    }
}
