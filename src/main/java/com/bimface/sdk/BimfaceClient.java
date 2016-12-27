package com.bimface.sdk;

import java.io.InputStream;
import java.util.List;

import com.bimface.sdk.bean.request.FileUploadRequest;
import com.bimface.sdk.bean.request.integrate.IntegrateRequest;
import com.bimface.sdk.bean.request.translate.TranslateRequest;
import com.bimface.sdk.bean.request.translate.TranslateSource;
import com.bimface.sdk.bean.response.CategoryBean;
import com.bimface.sdk.bean.response.ElementsBean;
import com.bimface.sdk.bean.response.FileBean;
import com.bimface.sdk.bean.response.FloorTree;
import com.bimface.sdk.bean.response.IntegrateBean;
import com.bimface.sdk.bean.response.PropertyBean;
import com.bimface.sdk.bean.response.ShareLinkBean;
import com.bimface.sdk.bean.response.SpecialtyTree;
import com.bimface.sdk.bean.response.SupportFileBean;
import com.bimface.sdk.bean.response.TranslateBean;
import com.bimface.sdk.bean.response.UploadPolicyBean;
import com.bimface.sdk.config.Config;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.config.authorization.AccessTokenStorage;
import com.bimface.sdk.config.authorization.Credential;
import com.bimface.sdk.config.authorization.DefaultAccessTokenStorage;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.service.AccessTokenService;
import com.bimface.sdk.service.CategoryTreeService;
import com.bimface.sdk.service.DownloadService;
import com.bimface.sdk.service.ElementService;
import com.bimface.sdk.service.IntegrateService;
import com.bimface.sdk.service.PropertyService;
import com.bimface.sdk.service.ShareLinkService;
import com.bimface.sdk.service.SignatureService;
import com.bimface.sdk.service.SupportFileService;
import com.bimface.sdk.service.TranslateService;
import com.bimface.sdk.service.UploadService;
import com.bimface.sdk.service.ViewTokenService;

/**
 * 访问bimface服务的入口
 *
 * @author bimface, 2016-06-01.
 */
public class BimfaceClient {

    private Credential          credential;         // APP证书
    private Endpoint            endpoint;           // API调用地址入口

    private ServiceClient       serviceClient;      // API请求的Client

    private AccessTokenService  accessTokenService;
    private SupportFileService  supportFileService;
    private UploadService       uploadService;
    private TranslateService    translateService;
    private ViewTokenService    viewTokenService;
    private ShareLinkService    shareLinkService;
    private PropertyService     propertyService;
    private SignatureService    signatureService;
    private DownloadService     downloadService;
    private ElementService      elementService;
    private CategoryTreeService categoryTreeService;
    private IntegrateService    integrateService;

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
        translateService = new TranslateService(serviceClient, this.endpoint, accessTokenService);
        viewTokenService = new ViewTokenService(serviceClient, this.endpoint, accessTokenService);
        shareLinkService = new ShareLinkService(serviceClient, this.endpoint, accessTokenService);
        propertyService = new PropertyService(serviceClient, this.endpoint, accessTokenService);
        signatureService = new SignatureService(credential);
        downloadService = new DownloadService(serviceClient, this.endpoint, accessTokenService);
        elementService = new ElementService(serviceClient, this.endpoint, accessTokenService);
        categoryTreeService = new CategoryTreeService(serviceClient, this.endpoint, accessTokenService);
        integrateService = new IntegrateService(serviceClient, this.endpoint, accessTokenService);
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
        return upload(name, null, contentLength, inputStream);
    }

    /**
     * 上传文件,文件流方式
     * 
     * @param name 文件名
     * @param sourceId 上传源文件Id
     * @param contentLength 文件长度
     * @param inputStream 文件流
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean upload(String name, String sourceId, Long contentLength,
                           InputStream inputStream) throws BimfaceException {
        return uploadService.upload(new FileUploadRequest(name, sourceId, contentLength, inputStream));
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
        return upload(name, null, url);
    }

    /**
     * 上传文件,URL方式
     * 
     * @param name 文件名
     * @param sourceId 上传源文件Id
     * @param url 文件下载地址
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean upload(String name, String sourceId, String url) throws BimfaceException {
        return uploadService.upload(new FileUploadRequest(name, sourceId, url));
    }

    /**
     * 获取上传凭证，用于直传到OSS
     *
     * @param name 文件名
     * @return {@link UploadPolicyBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public UploadPolicyBean getPolicy(String name) throws BimfaceException {
        return getPolicy(name, null);
    }

    /**
     * 获取上传凭证，用于直传到OSS
     *
     * @param name 文件名
     * @param sourceId 上传源文件Id
     * @return {@link UploadPolicyBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public UploadPolicyBean getPolicy(String name, String sourceId) throws BimfaceException {
        return uploadService.getPolicy(name, sourceId);
    }

    /**
     * 基于阿里云的policy机制，将文件直接上传阿里云 分两步：1. 调用bimface接口取得policy；2. 用policy将文件直接上传阿里云
     *
     * @param name 文件名
     * @param contentLength 文件内容长度
     * @param inputStream 文件流
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean uploadByPolicy(String name, Long contentLength, InputStream inputStream) throws BimfaceException {
        return uploadByPolicy(name, null, contentLength, inputStream);
    }

    /**
     * 基于阿里云的policy机制，将文件直接上传阿里云 分两步：1. 调用bimface接口取得policy；2. 用policy将文件直接上传阿里云
     * 
     * @param name 文件名
     * @param sourceId 上传源文件Id
     * @param contentLength 文件内容长度
     * @param inputStream 文件流
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean uploadByPolicy(String name, String sourceId, Long contentLength,
                                   InputStream inputStream) throws BimfaceException {
        return uploadService.uploadByPolicy(name, sourceId, contentLength, inputStream);
    }

    /**
     * 获取文件下载链接，直接从OSS下载
     *
     * @param fileId 文件ID
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getDownloadUrl(Long fileId) throws BimfaceException {
        return downloadService.getDownloadUrl(fileId);
    }

    /**
     * 获取文件下载链接，直接从OSS下载
     *
     * @param fileId 文件ID
     * @param fileName 文件名
     * @return String 下载链接
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getDownloadUrl(Long fileId, String fileName) throws BimfaceException {
        return downloadService.getDownloadUrl(fileId, fileName);
    }

    /**
     * 获取文件流
     * 
     * @param fileId 文件id
     * @param fileName 文件名
     * @return InputStream 文件流
     * @throws BimfaceException {@link BimfaceException}
     */
    public InputStream download(Long fileId, String fileName) throws BimfaceException {
        return downloadService.getFileContent(fileId, fileName);
    }

    /**
     * 通过条件查询构件ID组
     *
     * @param fileId 文件ID
     * @param categoryId 构件类型ID
     * @param family 族名称
     * @param familyType 族类型
     * @return List&lt;{@link String}&gt; 构件id列表
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<String> getElements(Long fileId, String categoryId, String family,
                                    String familyType) throws BimfaceException {
        return elementService.getElements(fileId, categoryId, family, familyType);
    }

    /**
     * 获取集成模型的构件列表
     * 
     * @param integrateId 集成id
     * @param floor 楼层
     * @param specialty 专业
     * @param categoryId 分类id
     * @param family 族
     * @param familyType 族类型
     * @return {@link ElementsBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ElementsBean getIntegrationElements(Long integrateId, String floor, String specialty, String categoryId,
                                               String family, String familyType) throws BimfaceException {
        return elementService.getIntegrationElements(integrateId, floor, specialty, categoryId, family, familyType);
    }

    /**
     * 发起文件转换
     * 
     * @param fileTranslateRequest {@link TranslateRequest}
     * @return {@link TranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public TranslateBean translate(TranslateRequest fileTranslateRequest) throws BimfaceException {
        return translateService.translate(fileTranslateRequest);
    }

    /**
     * 发起文件转换
     * 
     * @param fileId 文件id
     * @return {@link TranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public TranslateBean translate(Long fileId) throws BimfaceException {
        TranslateRequest fileTranslateRequest = new TranslateRequest();
        TranslateSource source = new TranslateSource();
        source.setFileId(fileId);
        fileTranslateRequest.setSource(source);
        return translateService.translate(fileTranslateRequest);
    }

    /**
     * 发起文件转换
     * 
     * @param fileId 文件id
     * @param callback 回调地址以http或者https开头
     * @return {@link TranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public TranslateBean translate(Long fileId, String callback) throws BimfaceException {
        TranslateRequest fileTranslateRequest = new TranslateRequest();
        fileTranslateRequest.setCallback(callback);
        TranslateSource source = new TranslateSource();
        source.setFileId(fileId);
        fileTranslateRequest.setSource(source);
        return translateService.translate(fileTranslateRequest);
    }

    /**
     * 获取文件转换状态
     * 
     * @param fileId 文件id
     * @return {@link TranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public TranslateBean getTranslate(Long fileId) throws BimfaceException {
        return translateService.getTranslate(fileId);
    }

    /**
     * 获取单文件模型viewToken, 用于模型预览的凭证
     * 
     * @param transferId 转换id
     * @return viewToken 预览凭证
     * @throws BimfaceException {@link BimfaceException}
     */
    @Deprecated
    public String getViewTokenByTransferId(String transferId) throws BimfaceException {
        return viewTokenService.grantViewTokenByTransferId(transferId);
    }

    /**
     * 获取集成模型viewToken, 用于模型预览的凭证
     * 
     * @param integrateId 集成id
     * @return viewToken 预览凭证
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewTokenByIntegrateId(Long integrateId) throws BimfaceException {
        return viewTokenService.grantViewTokenByIntegrateId(integrateId);
    }

    /**
     * 获取viewToken, 用于模型预览的凭证
     * 
     * @param fileId 文件id
     * @return viewToken 预览凭证
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewTokenByFileId(Long fileId) throws BimfaceException {
        return viewTokenService.grantViewTokenByFileId(fileId);
    }

    /**
     * 创建单个文件浏览分享链接
     * 
     * @param fileId 文件id
     * @param activeHours 从当前算起，分享链接的有效时间，单位：小时; 如果为空，表示该分享链接永久有效
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShare(Long fileId, Integer activeHours) throws BimfaceException {
        return shareLinkService.createShare(fileId, activeHours);
    }

    /**
     * 创建单个文件浏览分享链接,永久有效
     * 
     * @param fileId 文件id
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShare(Long fileId) throws BimfaceException {
        return shareLinkService.createShare(fileId);
    }

    /**
     * 取消单个文件浏览分享链接
     * 
     * @param fileId 文件id
     * @throws BimfaceException {@link BimfaceException}
     */
    public void deleteShare(Long fileId) throws BimfaceException {
        shareLinkService.deleteShare(fileId);
    }

    /**
     * 创建集成文件浏览分享链接
     * 
     * @param integrateId 集成id
     * @param activeHours 从当前算起，分享链接的有效时间，单位：小时; 如果为空，表示该分享链接永久有效
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShareIntegration(Long integrateId, Integer activeHours) throws BimfaceException {
        return shareLinkService.createShareIntegration(integrateId, activeHours);
    }

    /**
     * 创建集成文件浏览分享链接,永久有效
     * 
     * @param integrateId 集成id
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShareIntegration(Long integrateId) throws BimfaceException {
        return shareLinkService.createShareIntegration(integrateId);
    }

    /**
     * 取消集成文件浏览分享链接
     * 
     * @param integrateId 集成id
     * @throws BimfaceException {@link BimfaceException}
     */
    public void deleteShareIntegration(Long integrateId) throws BimfaceException {
        shareLinkService.deleteShareIntegration(integrateId);
    }

    /**
     * 获取单文件模型对应构件下的属性
     * 
     * @param fileId 文件id
     * @param elementId 构件id
     * @return PropertyBean {@link PropertyBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public PropertyBean getProperty(Long fileId, String elementId) throws BimfaceException {
        return propertyService.getElementProperty(fileId, elementId);
    }

    /**
     * 获取集成模型对应构件下的属性
     * 
     * @param integrateId 集成id
     * @param fileId 文件id
     * @param elementId 构件id
     * @return {@link PropertyBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public PropertyBean getIntegrationProperty(Long integrateId, Long fileId,
                                               String elementId) throws BimfaceException {
        return propertyService.getIntegrationElementProperty(integrateId, fileId, elementId);
    }

    /**
     * 获取单文件构件分类树
     *
     * @param fileId 文件id
     * @return List&lt;{@link CategoryBean}&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<CategoryBean> getCategory(Long fileId) throws BimfaceException {
        return categoryTreeService.getCategoryTree(fileId);
    }

    /**
     * 获取集成模型专业层次结构
     * 
     * @param integrateId 集成id
     * @return {@link SpecialtyTree}
     * @throws BimfaceException {@link BimfaceException}
     */
    public SpecialtyTree getSpecialtyTree(Long integrateId) throws BimfaceException {
        return categoryTreeService.getSpecialtyTree(integrateId);
    }

    /**
     * 获取集成模型楼层层次结构
     * 
     * @param integrateId 集成id
     * @return {@link FloorTree}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FloorTree getFloorTree(Long integrateId) throws BimfaceException {
        return categoryTreeService.getFloorTree(integrateId);
    }

    /**
     * 发起文件集成
     * 
     * @param request {@link IntegrateRequest}
     * @return {@link IntegrateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public IntegrateBean integrate(IntegrateRequest request) throws BimfaceException {
        return integrateService.integrate(request);
    }

    /**
     * 获取文件集成状态
     * 
     * @param integrateId 集成Id
     * @return {@link IntegrateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public IntegrateBean getIntegrate(Long integrateId) throws BimfaceException {
        return integrateService.getIntegrate(integrateId);
    }

    /**
     * 验证签名, 接收回调时使用
     * 
     * @param signature 签名字符
     * @param id 可以是fileId或者integrateId
     * @param status 转换状态(success || failed)
     * @return true: 验证成功, false: 校验失败
     * @throws BimfaceException {@link BimfaceException}
     */
    public boolean validateSignature(String signature, String id, String status, String nonce) throws BimfaceException {
        return signatureService.validate(signature, id, status, nonce);
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

    public TranslateService getTranslateService() {
        return translateService;
    }

    public ViewTokenService getViewTokenService() {
        return viewTokenService;
    }

    public ShareLinkService getShareLinkService() {
        return shareLinkService;
    }

    public PropertyService getPropertyService() {
        return propertyService;
    }

    public SignatureService getSignatureService() {
        return signatureService;
    }

    public DownloadService getDownloadService() {
        return downloadService;
    }

    public ElementService getElementService() {
        return elementService;
    }

    public CategoryTreeService getCategoryService() {
        return categoryTreeService;
    }
}
