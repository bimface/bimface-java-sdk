package com.bimface.sdk;

import com.bimface.api.bean.compatible.response.ShareLinkBean;
import com.bimface.api.bean.request.integrate.FileIntegrateRequest;
import com.bimface.api.bean.request.modelCompare.ModelCompareRequest;
import com.bimface.api.bean.request.translate.FileTranslateRequest;
import com.bimface.api.bean.request.translate.TranslateSource;
import com.bimface.api.bean.response.FileIntegrateBean;
import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.api.bean.response.ModelCompareBean;
import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.data.bean.*;
import com.bimface.exception.BimfaceException;
import com.bimface.file.bean.AppendFileBean;
import com.bimface.file.bean.FileBean;
import com.bimface.file.bean.SupportFileBean;
import com.bimface.file.bean.UploadPolicyBean;
import com.bimface.sdk.bean.request.FileUploadRequest;
import com.bimface.sdk.bean.request.OfflineDatabagRequest;
import com.bimface.sdk.bean.request.compare.CompareElementRequest;
import com.bimface.sdk.config.Config;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.config.authorization.AccessTokenStorage;
import com.bimface.sdk.config.authorization.Credential;
import com.bimface.sdk.config.authorization.DefaultAccessTokenStorage;
import com.bimface.sdk.service.*;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 访问bimface服务的入口
 *
 * @author bimface, 2016-06-01.
 */
public class BimfaceClient {

    private Credential credential;         // APP证书
    private Endpoint endpoint;           // API调用地址入口

    private AccessTokenService accessTokenService;
    private FileService fileService;
    private TranslateService translateService;
    private ViewTokenService viewTokenService;
    private ShareLinkService shareLinkService;
    private PropertyService propertyService;
    private DownloadService downloadService;
    private ElementService elementService;
    private CategoryTreeService categoryTreeService;
    private IntegrateService integrateService;
    private CompareService compareService;
    private OfflineDatabagService offlineDatabagService;
    private FloorService floorService;
    private SignatureService signatureService;
    private DataService dataService;
    private DatabagService databagService;

    /**
     * 构造BimfaceClient对象
     *
     * @param appKey    AppKey
     * @param appSecret AppSecret
     */
    public BimfaceClient(String appKey, String appSecret) {
        this(appKey, appSecret, null, null, null);
    }

    /**
     * 构造BimfaceClient对象
     *
     * @param appKey    AppKey
     * @param appSecret AppSecret
     * @param config    参数配置
     */
    public BimfaceClient(String appKey, String appSecret, Config config) {
        this(appKey, appSecret, null, config, null);
    }

    /**
     * 构造BimfaceClient对象
     *
     * @param appKey    AppKey
     * @param appSecret AppSecret
     * @param endpoint  参数配置
     */
    public BimfaceClient(String appKey, String appSecret, Endpoint endpoint) {
        this(appKey, appSecret, endpoint, null, null);
    }

    /**
     * 构造BimfaceClient对象
     *
     * @param appKey    AppKey
     * @param appSecret AppSecret
     * @param endpoint  API调用地址入口
     * @param config    参数配置
     */
    public BimfaceClient(String appKey, String appSecret, Endpoint endpoint, Config config) {
        this(appKey, appSecret, endpoint, config, null);
    }

    /**
     * 构造BimfaceClient对象
     *
     * @param appKey             AppKey
     * @param appSecret          AppSecret
     * @param endpoint           API调用地址入口
     * @param config             参数配置
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

        // 初始化缓存AccessToken的方式
        AccessTokenStorage usedAccessTokenStorage;
        if (accessTokenStorage == null) {
            usedAccessTokenStorage = new DefaultAccessTokenStorage();
        } else {
            usedAccessTokenStorage = accessTokenStorage;
        }

        // 初始化Service
        accessTokenService = new AccessTokenService(this.endpoint, credential, usedAccessTokenStorage);
        fileService = new FileService(this.endpoint, accessTokenService);
        translateService = new TranslateService(this.endpoint, accessTokenService);
        viewTokenService = new ViewTokenService(this.endpoint, accessTokenService);
        shareLinkService = new ShareLinkService(this.endpoint, accessTokenService);
        propertyService = new PropertyService(this.endpoint, accessTokenService);
        downloadService = new DownloadService(this.endpoint, accessTokenService);
        elementService = new ElementService(this.endpoint, accessTokenService);
        categoryTreeService = new CategoryTreeService(this.endpoint, accessTokenService);
        integrateService = new IntegrateService(this.endpoint, accessTokenService);
        compareService = new CompareService(this.endpoint, accessTokenService);
        offlineDatabagService = new OfflineDatabagService(this.endpoint, accessTokenService);
        floorService = new FloorService(this.endpoint, accessTokenService);
        signatureService = new SignatureService(credential);
        dataService = new DataService(this.endpoint, accessTokenService);
        databagService = new DatabagService(this.endpoint, accessTokenService);
    }

    /**
     * 获取支持的文件格式及文件大小
     *
     * @return 文件后缀名数组
     * @throws BimfaceException {@link BimfaceException}
     */
    public SupportFileBean getSupport() throws BimfaceException {
        return fileService.getSupportedFileTypes();
    }

    /**
     * 上传文件
     *
     * @param fileUploadRequest {@link FileUploadRequest}
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean upload(FileUploadRequest fileUploadRequest) throws BimfaceException {
        return fileService.upload(fileUploadRequest);
    }

    /**
     * 上传文件,文件流方式
     *
     * @param name          文件名
     * @param contentLength 文件长度
     * @param inputStream   文件流
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean upload(String name, Long contentLength, InputStream inputStream) throws BimfaceException {
        return upload(name, null, contentLength, inputStream);
    }

    /**
     * 上传文件,文件流方式
     *
     * @param name          文件名
     * @param sourceId      上传源文件Id
     * @param contentLength 文件长度
     * @param inputStream   文件流
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean upload(String name, String sourceId, Long contentLength,
                           InputStream inputStream) throws BimfaceException {
        return fileService.upload(new FileUploadRequest(name, sourceId, contentLength, inputStream));
    }

    /**
     * 上传文件,URL方式
     *
     * @param name 文件名
     * @param url  文件下载地址
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean upload(String name, String url) throws BimfaceException {
        return upload(name, null, url);
    }

    /**
     * 上传文件,URL方式
     *
     * @param name     文件名
     * @param sourceId 上传源文件Id
     * @param url      文件下载地址
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean upload(String name, String sourceId, String url) throws BimfaceException {
        return fileService.upload(new FileUploadRequest(name, sourceId, url));
    }

    /**
     * 根据文件id获取文件元信息
     *
     * @param fileId 文件Id
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean getFileMetadata(Long fileId) throws BimfaceException {
        return fileService.getFileMetadata(fileId);
    }

    /**
     * 删除文件
     *
     * @param fileId 文件id
     * @throws BimfaceException {@link BimfaceException}
     */
    public void deleteFile(Long fileId) throws BimfaceException {
        fileService.deleteFile(fileId);
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
     * @param name     文件名
     * @param sourceId 上传源文件Id
     * @return {@link UploadPolicyBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public UploadPolicyBean getPolicy(String name, String sourceId) throws BimfaceException {
        return fileService.getPolicy(name, sourceId);
    }

    /**
     * 基于阿里云的policy机制，将文件直接上传阿里云 分两步：1. 调用bimface接口取得policy；2. 用policy将文件直接上传阿里云
     *
     * @param name          文件名
     * @param contentLength 文件内容长度
     * @param inputStream   文件流
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean uploadByPolicy(String name, Long contentLength, InputStream inputStream) throws BimfaceException {
        return uploadByPolicy(name, null, contentLength, inputStream);
    }

    /**
     * 基于阿里云的policy机制，将文件直接上传阿里云 分两步：1. 调用bimface接口取得policy；2. 用policy将文件直接上传阿里云
     *
     * @param name          文件名
     * @param sourceId      上传源文件Id
     * @param contentLength 文件内容长度
     * @param inputStream   文件流
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean uploadByPolicy(String name, String sourceId, Long contentLength,
                                   InputStream inputStream) throws BimfaceException {
        return fileService.uploadByPolicy(name, sourceId, contentLength, inputStream);
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
     * @param fileId   文件ID
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
     * @param fileId   文件id
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
     * @param fileId     文件ID
     * @param categoryId 构件类型ID
     * @param family     族名称
     * @param familyType 族类型
     * @return List&lt;{@link String}&gt; 构件id列表
     * @throws BimfaceException {@link BimfaceException}
     */
    @Deprecated
    public List<String> getElements(Long fileId, String categoryId, String family,
                                    String familyType) throws BimfaceException {
        return elementService.getElements(fileId, null, null, categoryId, family, familyType);
    }

    /**
     * 通过条件查询构件ID组
     *
     * @param fileId     文件ID
     * @param floor      楼层
     * @param specialty  专业
     * @param categoryId 构件类型ID
     * @param family     族名称
     * @param familyType 族类型
     * @return List&lt;{@link String}&gt; 构件id列表
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<String> getElements(Long fileId, String floor, String specialty, String categoryId, String family,
                                    String familyType) throws BimfaceException {
        return elementService.getElements(fileId, floor, specialty, categoryId, family, familyType);
    }

    /**
     * 获取集成模型的构件列表
     *
     * @param integrateId 集成id
     * @param floor       楼层
     * @param specialty   专业
     * @param categoryId  分类id
     * @param family      族
     * @param familyType  族类型
     * @return {@link ElementsWithBoundingBox}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ElementsWithBoundingBox getIntegrateElements(Long integrateId, String floor, String specialty, String categoryId,
                                                        String family, String familyType) throws BimfaceException {
        return elementService.getIntegrateElements(integrateId, floor, specialty, categoryId, family, familyType);
    }

    /**
     * 发起文件转换
     *
     * @param fileTranslateRequest {@link FileTranslateRequest}
     * @return {@link FileTranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileTranslateBean translate(FileTranslateRequest fileTranslateRequest) throws BimfaceException {
        return translateService.translate(fileTranslateRequest);
    }

    /**
     * 发起文件转换
     *
     * @param fileId 文件id
     * @return {@link FileTranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileTranslateBean translate(Long fileId) throws BimfaceException {
        FileTranslateRequest fileTranslateRequest = new FileTranslateRequest();
        TranslateSource source = new TranslateSource();
        source.setFileId(fileId);
        fileTranslateRequest.setSource(source);
        return translate(fileTranslateRequest);
    }

    /**
     * 发起文件转换
     *
     * @param fileId   文件id
     * @param callback 回调地址以http或者https开头
     * @return {@link FileTranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileTranslateBean translate(Long fileId, String callback) throws BimfaceException {
        FileTranslateRequest fileTranslateRequest = new FileTranslateRequest();
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
     * @return {@link FileTranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileTranslateBean getTranslate(Long fileId) throws BimfaceException {
        return translateService.getTranslate(fileId);
    }

    /**
     * 发起模型对比
     *
     * @param modelCompareRequest {@link ModelCompareBean}
     * @return {@link FileTranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ModelCompareBean compare(ModelCompareRequest modelCompareRequest) throws BimfaceException {
        return compareService.compare(modelCompareRequest);
    }

    /**
     * 获取模型对比状态
     *
     * @param compareId 模型对比id
     * @return {@link ModelCompareBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ModelCompareBean getCompareInfo(Long compareId) throws BimfaceException {
        return compareService.getCompareInfo(compareId);
    }

    /**
     * 获取模型对比结果
     *
     * @param compareId 模型对比id
     * @return {@link ModelCompareTree.SpecialtyNode}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<ModelCompareTree.SpecialtyNode> getCompareResult(Long compareId) throws BimfaceException {
        return compareService.getCompareResult(compareId);
    }

    /**
     * 获取模型对比构件差异
     *
     * @param compareElementRequest {@link CompareElementRequest}
     * @return {@link ModelCompareChange}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ModelCompareChange getCompareElementDiff(CompareElementRequest compareElementRequest) throws BimfaceException {
        return compareService.getCompareElementDiff(compareElementRequest);
    }

    /**
     * 获取集成模型viewToken, 用于模型预览的凭证
     *
     * @param integrateId 集成id
     * @return viewToken 预览凭证
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewTokenByIntegrateId(Long integrateId) throws BimfaceException {
        return viewTokenService.getViewTokenByIntegrateId(integrateId);
    }

    /**
     * 获取模型对比viewToken, 用于模型预览的凭证
     *
     * @param compareId 模型对比id
     * @return viewToken 预览凭证
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewTokenByCompareId(Long compareId) throws BimfaceException {
        return viewTokenService.getViewTokenByCompareId(compareId);
    }

    /**
     * 获取viewToken, 用于模型预览的凭证
     *
     * @param fileId 文件id
     * @return viewToken 预览凭证
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewTokenByFileId(Long fileId) throws BimfaceException {
        return viewTokenService.getViewTokenByFileId(fileId);
    }

    /**
     * 创建单个文件浏览分享链接
     *
     * @param fileId      文件id
     * @param activeHours 从当前算起，分享链接的有效时间，单位：小时; 如果为空，表示该分享链接永久有效
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createTranslateShare(Long fileId, Integer activeHours) throws BimfaceException {
        return shareLinkService.createShare(fileId, activeHours);
    }

    /**
     * 创建单个文件浏览分享链接,带分享密码
     * @param fileId
     * @param expireDate
     * @param needPassword
     * @return
     * @throws BimfaceException
     */
    public ShareLinkBean createTranslateShare(Long fileId, String expireDate, Boolean needPassword) throws BimfaceException {
        return shareLinkService.createShare(fileId, expireDate, needPassword);
    }

    /**
     * 创建单个文件浏览分享链接,永久有效
     *
     * @param fileId 文件id
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createTranslateShare(Long fileId) throws BimfaceException {
        return shareLinkService.createShare(fileId);
    }

    /**
     * 取消单个文件浏览分享链接
     *
     * @param fileId 文件id
     * @throws BimfaceException {@link BimfaceException}
     */
    public void deleteTranslateShare(Long fileId) throws BimfaceException {
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
    public ShareLinkBean createIntegrateShare(Long integrateId, Integer activeHours) throws BimfaceException {
        return shareLinkService.createIntegrateShare(integrateId, activeHours);
    }

    /**
     * 创建集成文件浏览分享链接,带分享密码
     * @param integrateId
     * @param expireDate
     * @param needPassword
     * @return
     * @throws BimfaceException
     */
    public ShareLinkBean createIntegrateShare(Long integrateId, String expireDate, Boolean needPassword) throws BimfaceException {
        return shareLinkService.createIntegrateShare(integrateId, expireDate, needPassword);
    }

    /**
     * 创建集成文件浏览分享链接,永久有效
     *
     * @param integrateId 集成id
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createIntegrateShare(Long integrateId) throws BimfaceException {
        return shareLinkService.createIntegrateShare(integrateId);
    }

    /**
     * 取消集成文件浏览分享链接
     *
     * @param integrateId 集成id
     * @throws BimfaceException {@link BimfaceException}
     */
    public void deleteIntegrateShare(Long integrateId) throws BimfaceException {
        shareLinkService.deleteIntegrateShare(integrateId);
    }

    /**
     * 批量删除分享链接
     * @param sourceIds
     * @throws BimfaceException
     */
    public void batchDeteleShare(List<Long> sourceIds) throws BimfaceException {
        shareLinkService.batchDeteleShare(sourceIds);
    }

    /**
     * 获取分享链接信息
     * @param token
     * @return
     * @throws BimfaceException
     */
    public ShareLinkBean getShareLink(String token) throws BimfaceException {
        return shareLinkService.getShareLink(token);
    }

    /**
     * 获取分享链接信息
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public ShareLinkBean getTranslateShare(Long fileId) throws BimfaceException {
        return shareLinkService.getTranslateShare(fileId);
    }

    /**
     * 获取分享链接信息
     * @param integrateId
     * @return
     * @throws BimfaceException
     */
    public ShareLinkBean getIntegrateShare(Long integrateId) throws BimfaceException {
        return shareLinkService.getIntegrateShare(integrateId);
    }

    /**
     * 获取分享列表
     * @return
     * @throws BimfaceException
     */
    public List<ShareLinkBean> shares() throws BimfaceException {
        return shareLinkService.shareList();
    }

    /**
     * 获取单文件模型对应构件下的属性
     *
     * @param fileId    文件id
     * @param elementId 构件id
     * @return PropertyBean {@link Property}
     * @throws BimfaceException {@link BimfaceException}
     */
    public Property getProperty(Long fileId, String elementId) throws BimfaceException {
        return propertyService.getElementProperty(fileId, elementId);
    }

    /**
     * 获取集成模型对应构件下的属性
     *
     * @param integrateId 集成id
     * @param fileId      文件id
     * @param elementId   构件id
     * @return {@link Property}
     * @throws BimfaceException {@link BimfaceException}
     */
    public Property getIntegrationProperty(Long integrateId, Long fileId,
                                           String elementId) throws BimfaceException {
        return propertyService.getIntegrationElementProperty(integrateId, fileId, elementId);
    }

    /**
     * 获取单文件构件分类树
     *
     * @param fileId 文件id
     * @return List&lt;{@link Category}&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<Category> getCategory(Long fileId) throws BimfaceException {
        return categoryTreeService.getCategoryTree(fileId);
    }

    /**
     * 获取单文件构件分类树V2
     *
     * @param fileId 文件id
     * @return List&lt;{@link Tree.TreeNode}&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<Tree.TreeNode> getCategoryV2(Long fileId) throws BimfaceException {
        return categoryTreeService.getCategoryTreeV2(fileId);
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
     * @param request {@link FileIntegrateRequest}
     * @return {@link FileIntegrateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileIntegrateBean integrate(FileIntegrateRequest request) throws BimfaceException {
        return integrateService.integrate(request);
    }

    /**
     * 获取文件集成状态
     *
     * @param integrateId 集成Id
     * @return {@link FileIntegrateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileIntegrateBean getIntegrate(Long integrateId) throws BimfaceException {
        return integrateService.getIntegrate(integrateId);
    }

    /**
     * 删除集成模型
     *
     * @param integrateId 集成模型id
     * @throws BimfaceException {@link BimfaceException}
     */
    public void deleteIntegrate(Long integrateId) throws BimfaceException {
        integrateService.deleteIntegrate(integrateId);
    }

    /**
     * 验证签名, 接收回调时使用
     *
     * @param signature 签名字符
     * @param id        可以是fileId或者integrateId
     * @param status    转换状态(success || failed)
     * @param nonce     随机数
     * @return true: 验证成功, false: 校验失败
     * @throws BimfaceException {@link BimfaceException}
     */
    public boolean validateSignature(String signature, String id, String status, String nonce) throws BimfaceException {
        return signatureService.validate(signature, id, status, nonce);
    }

    /**
     * 构建离线数据包
     *
     * @param offlineDatabagRequest {@link OfflineDatabagRequest}
     * @return {@link DatabagDerivativeBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public DatabagDerivativeBean generateOfflineDatabag(OfflineDatabagRequest offlineDatabagRequest) throws BimfaceException {
        return offlineDatabagService.generateOfflineDatabag(offlineDatabagRequest);
    }

    /**
     * 查询离线数据包
     *
     * @param offlineDatabagRequest {@link OfflineDatabagRequest}
     * @return {@link DatabagDerivativeBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<? extends DatabagDerivativeBean> queryOfflineDatabag(OfflineDatabagRequest offlineDatabagRequest) throws BimfaceException {
        return offlineDatabagService.queryOfflineDatabag(offlineDatabagRequest);
    }

    /**
     * 获取离线数据包信息
     *
     * @param offlineDatabagRequest {@link OfflineDatabagRequest}
     * @return 离线数据包下载地址
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getOfflineDatabagUrl(OfflineDatabagRequest offlineDatabagRequest) throws BimfaceException {
        return offlineDatabagService.getOfflineDatabagUrl(offlineDatabagRequest);
    }

    public Credential getCredential() {
        return credential;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }


    public AccessTokenService getAccessTokenService() {
        return accessTokenService;
    }

    public FileService getFileService() {
        return fileService;
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

    public OfflineDatabagService getOfflineDatabagService() {
        return offlineDatabagService;
    }

    /**
     * 创建追加文件
     *
     * @param name     文件的全名，使用URL编码（UTF-8），最多256个字符
     * @param sourceId 调用方的文件源ID，不能重复
     * @param length   上传文件长度
     * @return AppendFileBean {@link AppendFileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public AppendFileBean createAppendFile(String name, String sourceId, Long length) throws BimfaceException {
        return fileService.createAppendFile(name, sourceId, length);
    }

    /**
     * 查询追加文件信息
     *
     * @param appendFileId 追加文件ID
     * @return AppendFileBean {@link AppendFileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public AppendFileBean queryAppendFile(Long appendFileId) throws BimfaceException {
        return fileService.queryAppendFile(appendFileId);
    }

    /**
     * 追加上传
     *
     * @param inputStream  文件的整个完整流
     * @param appendFileId 追加文件ID
     * @return AppendFileBean {@link AppendFileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public AppendFileBean uploadAppendFile(InputStream inputStream, Long appendFileId) throws BimfaceException {
        return fileService.uploadAppendFile(inputStream, appendFileId);
    }

    /**
     * 获取楼层信息
     *
     * @param fileId 文件Id
     * @return 楼层列表
     * @throws BimfaceException {@link BimfaceClient}
     */
    public List<Floor> getFileFloors(Long fileId) throws BimfaceException {
        return floorService.getFileFloors(fileId);
    }

    /**
     * 获取集成模型楼层信息
     *
     * @param integrateId 模型集成ID
     * @return 楼层列表
     * @throws BimfaceException {@link BimfaceClient}
     */
    public List<Floor> getIntegrateFloors(Long integrateId) throws BimfaceException {
        return floorService.getIntegrateFloors(integrateId);
    }

    /**
     * 按查询条件查询构件ID 组，v2
     *
     * @param fileId 文件ID
     * @param specialty 专业
     * @param floor 楼层
     * @param categoryId    类别
     * @param family    族
     * @param familyType    族类型
     * @return 构件 ID 列表
     * @throws BimfaceException {@link BimfaceClient}
     */
    public List<String> getElementIdsV2(Long fileId, String specialty, String floor, String categoryId,
                                        String family, String familyType) throws BimfaceException {
        return elementService.getElementIdsV2(fileId, specialty, floor, categoryId, family, familyType);
    }

    /**
     * 获取单模型文件 id 与楼层的映射关系，v2
     *
     * @param fileIds   文件 IDs
     * @param includeArea   includeArea
     * @param includeRoom includeRoom
     * @return 楼层的映射关系
     * @throws BimfaceException {@link BimfaceClient}
     */
    public Map<String, Object> getSingleModelFileIdFloorsMapping(List<String> fileIds, Boolean includeArea, Boolean includeRoom) throws BimfaceException {
        return floorService.getSingleModelFileIdFloorsMapping(fileIds, includeArea, includeRoom);
    }

    /**
     * 获取单模型构件，v2
     *
     * @param fileId    文件ID
     * @param elementId 构件ID
     * @return  构件
     * @throws BimfaceException {@link BimfaceClient}
     */
    public Property getSingleModelElementV2(Long fileId, String elementId) throws BimfaceException {
        return elementService.getSingleModelElementV2(fileId, elementId);
    }

    /**
     * 根据构件 ID 批量获取单模型的单个构件，v2
     *
     * @param fileId    文件ID
     * @param elementIds    构件IDs
     * @return  构件
     * @throws BimfaceException {@link BimfaceClient}
     */
    public List<Property> getSingleModelElementsV2(Long fileId, List<String> elementIds) throws BimfaceException {
        return elementService.getSingleModelElementsV2(fileId, elementIds);
    }

    /**
     * 获取单文件的构件属性，v2
     *
     * @param fileId     文件id
     * @param elementIds 构件id
     * @return {@link Property}构件属性
     * @throws BimfaceException {@link BimfaceException}
     */
    public Property getElementPropertyV2(Long fileId, List<String> elementIds) throws BimfaceException {
        return propertyService.getSingleModelElementPropertyV2(fileId, elementIds);
    }

    /**
     * 获取单模型的构件材质，v2
     *
     * @param fileId    文件ID
     * @param elementId 构件ID
     * @return 构件材质 {@link MaterialInfo}
     * @throws BimfaceException {@link BimfaceClient}
     */
    public List<MaterialInfo> getSingleModelMaterials(Long fileId, String elementId) throws BimfaceException {
        return elementService.getSingleModelMaterials(fileId, elementId);
    }

    /**
     * 单模型获取钢筋工程量, v2
     *
     * @param fileId 文件ID
     * @param elementId 构件ID
     * @return 钢筋工程量 {@link Bar}
     * @throws BimfaceException {@link BimfaceClient}
     */
    public List<Bar> getSingleModelElementBars(Long fileId, String elementId) throws BimfaceException {
        return elementService.getSingleModelElementBars(fileId, elementId);
    }

    /**
     * 单模型获取构件工程量，v2
     *
     * @param fileId 文件ID
     * @param elementId 构件ID
     * @param type 类型
     * @return 构件工程量 {@link Quantity}
     * @throws BimfaceException
     */
    public List<Quantity> getSingleModelElementQuantities(Long fileId, String elementId, String type) throws BimfaceException {
        return elementService.getSingleModelElementQuantities(fileId, elementId, type);
    }

    /**
     * 获取多个构件的工程量汇总，v2
     *
     * @param fileId
     * @param elementIds
     * @return
     * @throws BimfaceException
     */
    public List<AggregatedQuantity> getSingleModelAggregatedElementQuantities(Long fileId, List<String> elementIds, String type) throws BimfaceException {
        return elementService.getSingleModelAggregatedElementQuantities(fileId, elementIds, type);
    }


    /**
     * 查询单模型三维视点信息，v2
     *
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public List<View> getSingleModelViews(Long fileId) throws BimfaceException {

        return dataService.getSingleModelViews(fileId);
    }

    /**
     * Rvt单模型查询楼层对应房间列表，v2
     *
     * @param fileId
     * @param floorId
     * @return
     * @throws BimfaceException
     */
    public List<Room> getSingleModelRooms(Long fileId, String floorId) throws BimfaceException {
        return dataService.getSingleModelRooms(fileId, floorId);
    }

    /**
     * Rvt单模型查询楼层对应房间, v2
     *
     * @param fileId
     * @param roomId
     * @return
     * @throws BimfaceException
     */
    public Room getSingleModelRoom(Long fileId, String roomId) throws BimfaceException {
        return dataService.getSingleModelRoom(fileId, roomId);
    }

    /**
     * Rvt单模型查询楼层对应Area列表, v2
     *
     * @param fileId
     * @param floorId
     * @return {@link Area}
     * @throws BimfaceException
     */
    public List<Area> getSingleModelAreas(Long fileId, String floorId) throws BimfaceException {
        return dataService.getSingleModelAreas(fileId, floorId);
    }

    /**
     * Rvt单模型查询 Area, v2
     *
     * @param fileId
     * @param areaId
     * @return
     * @throws BimfaceException
     */
    public Area getSingleModelArea(Long fileId, String areaId) throws BimfaceException {
        return dataService.getSingleModelArea(fileId, areaId);
    }

    /**
     * 查询单模型获取构件分类树，v2
     *
     * @param fileId
     * @param requestBody {@link FileTreeRequestBody}
     * @return
     * @throws BimfaceException
     */
    public List<Tree.TreeNode> getSingleModelTreeV2(Long fileId, FileTreeRequestBody requestBody) throws BimfaceException {
        return dataService.getSingleModelTreeV2(fileId, requestBody);
    }

    /**
     * 查询单模型自定义构件分类树
     *
     * @param fileId
     * @param requestBody
     * @return
     * @throws BimfaceException
     */
    public Tree getSingleModelCustomizedTree(Long fileId, FileTreeRequestBody requestBody) throws BimfaceException {
        return dataService.getSingleModelCustomizedTree(fileId, requestBody);
    }

    /**
     * 单模型查询链接, v2
     *
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public List<Link> getSingleModelLinks(Long fileId) throws BimfaceException {
        return dataService.getSingleModelLinks(fileId);
    }

    /**
     * 单模型查询drawingSheets信息, v2
     *
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public List<DrawingSheet> getSingleModelDrawingSheets(Long fileId) throws BimfaceException {
        return dataService.getSingleModelDrawingSheets(fileId);
    }

    /**
     * 获取模型数据包meta信息, v2
     *
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public Object getSingleModelModelInfo(Long fileId) throws BimfaceException {
        return dataService.getSingleModelModelInfo(fileId);
    }

    /**
     * 获取构件的子构件 id，v2
     *
     * @param fileId
     * @param elementId
     * @return
     * @throws BimfaceException
     */
    public List<ElementIdWithName> getSingleModelChildElementIds(Long fileId, String elementId) throws BimfaceException {
        return dataService.getSingleModelChildElementIds(fileId, elementId);
    }

    /**
     * 获取集成模型构件ID组, v2
     *
     * @param integrateId
     * @param specialty
     * @param roomId
     * @param floor
     * @param categoryId
     * @param family
     * @param familyType
     * @param systemType
     * @return
     * @throws BimfaceException
     */
    public ElementsWithBoundingBox getIntegrateModelElementIds(Long integrateId, String specialty, String roomId,
                                                               String floor, String categoryId, String family,
                                                               String familyType, String systemType) throws BimfaceException {
        return dataService.getIntegrateModelElementIds(integrateId, specialty, roomId, floor, categoryId, family, familyType, systemType);
    }

    /**
     * 获取集成模型构件, v2
     *
     * @param integrateId
     * @param fileId
     * @param elementId
     * @return
     * @throws BimfaceException
     */
    public Property getIntegrateModelElement(Long integrateId, Long fileId, String elementId) throws BimfaceException {
        return dataService.getIntegrateModelElement(integrateId, fileId, elementId);
    }

    /**
     * 获取集成模型构件, v2
     *
     * @param integrateId
     * @param elementId
     * @return
     * @throws BimfaceException
     */
    public Property getIntegrateModelElement(Long integrateId, String elementId) throws BimfaceException {
        return dataService.getIntegrateModelElement(integrateId, elementId);
    }

    /**
     * 获取集成模型构件属性, v2
     *
     * @param integrateId
     * @param fileIdHashWithElementIds {@link FileIdHashWithElementIds}
     * @return {@link Property}
     * @throws BimfaceException
     */
    public List<Property> getIntegrateModelElementProperties(Long integrateId, List<FileIdHashWithElementIds> fileIdHashWithElementIds) throws BimfaceException {
        return dataService.getIntegrateModelElementProperties(integrateId, fileIdHashWithElementIds);
    }

    /**
     * 获取集成模型构件材质, v2
     *
     * @param integrateId
     * @param fileIdHash
     * @param elementId
     * @return {@link Property}
     * @throws BimfaceException
     */
    public List<MaterialInfo> getIntegrateModelElementMaterials(Long integrateId, String fileIdHash, String elementId) throws BimfaceException {
        return dataService.getIntegrateModelElementMaterials(integrateId, fileIdHash, elementId);
    }

    /**
     * 获取集成模型分类树，v2
     *
     * @param integrateId
     * @param treeType
     * @param desiredHierarchy
     * @param requestBody
     * @return
     * @throws BimfaceException
     */
    public Tree getIntegrateModelTree(Long integrateId, String treeType, List<String> desiredHierarchy,
                                      IntegrationTreeOptionalRequestBody requestBody) throws BimfaceException {
        return dataService.getIntegrateModelTree(integrateId, treeType, desiredHierarchy, requestBody);
    }

    /**
     * 获取集成模型三维视点/二维视点, v2
     *
     * @param integrateId
     * @param viewType
     * @return
     * @throws BimfaceException
     */
    public List<FileViews> getIntegrateModelFileViews(Long integrateId, String viewType) throws BimfaceException {
        return dataService.getIntegrateModelFileViews(integrateId, viewType);
    }

    /**
     * 集成模型查询楼层对应房间列表, v2
     *
     * @param integrateId
     * @param floorId
     * @return
     * @throws BimfaceException
     */
    public List<Room> getIntegrateModelRooms(Long integrateId, String floorId) throws BimfaceException {
        return dataService.getIntegrateModelRooms(integrateId, floorId);
    }

    /**
     * 集成模型查询房间属性，v2
     *
     * @param integrateId
     * @param roomId
     * @return
     * @throws BimfaceException
     */
    public Room getIntegrateModelRoom(Long integrateId, String roomId) throws BimfaceException {
        return dataService.getIntegrateModelRoom(integrateId, roomId);
    }

    /**
     * 集成模型查询楼层对应Area列表, v2
     *
     * @param integrateId
     * @param floorId
     * @return
     * @throws BimfaceException
     */
    public List<Area> getIntegrateModelAreas(Long integrateId, String floorId) throws BimfaceException {
        return dataService.getIntegrateModelAreas(integrateId, floorId);
    }

    /**
     * 集成模型查询Area, v2
     *
     * @param integrateId
     * @param areaId
     * @return
     * @throws BimfaceException
     */
    public Area getIntegrateModelArea(Long integrateId, String areaId) throws BimfaceException {
        return dataService.getIntegrateModelArea(integrateId, areaId);
    }

    /**
     * 集成模型查询构件工程量, v2
     *
     * @param integrateId
     * @param fileId
     * @param elementId
     * @param type
     * @return
     * @throws BimfaceException
     */
    public List<Quantity> getIntegrateModelElementQuantities(Long integrateId, Long fileId, String elementId, String type) throws BimfaceException {
        return dataService.getIntegrateModelElementQuantities(integrateId, fileId, elementId, type);
    }

    /**
     * 集成模型查询构件工程量, v2
     *
     * @param integrateId
     * @param elementId
     * @param type
     * @return
     * @throws BimfaceException
     */
    public List<Quantity> getIntegrateModelElementQuantities(Long integrateId, String elementId, String type) throws BimfaceException {
        return dataService.getIntegrateModelElementQuantities(integrateId, elementId, type);
    }

    /**
     * 查询集成模型构件的汇总工程量, v2
     *
     * @param integrateId
     * @param fileIdHashWithElementIds
     * @param type
     * @return
     * @throws BimfaceException
     */
    public List<AggregatedQuantity> getIntegrateModelAccumulativeQuantities(Long integrateId, List<FileIdHashWithElementIds> fileIdHashWithElementIds, String type) throws BimfaceException {
        return dataService.getIntegrateModelAccumulativeQuantities(integrateId, fileIdHashWithElementIds, type);
    }

    /**
     * 查询指定的集成模型内参与集成的子文件信息, v2
     *
     * @param integrateId
     * @return
     * @throws BimfaceException
     */
    public List<IntegrateFile> getIntegrateFiles(Long integrateId) throws BimfaceException {
        return dataService.getIntegrateFiles(integrateId);
    }


    /**
     * 查询集成模型钢筋量，v2
     *
     * @param integrateId
     * @param fileId
     * @param elementId
     * @return
     * @throws BimfaceException
     */
    public List<Bar> getIntegrateModelElementBars(Long integrateId, Long fileId, String elementId) throws BimfaceException {
        return dataService.getIntegrateModelElementBars(integrateId, fileId, elementId);
    }

    /**
     * 查询集成模型钢筋量，v2
     *
     * @param integrateId
     * @param elementId
     * @return
     * @throws BimfaceException
     */
    public List<Bar> getIntegrateModelElementBars(Long integrateId, String elementId) throws BimfaceException {
        return dataService.getIntegrateModelElementBars(integrateId, elementId);
    }

    /**
     * 根据集成ID,文件ID获取viewToken，v2
     *
     * @param integrateId
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public String getIntegrateModelViewToken(Long integrateId, String fileId) throws BimfaceException {
        return dataService.getIntegrateModelViewToken(integrateId, fileId);
    }

    /**
     * 获取集成模型中所有的流水段分组，v2
     *
     * @param integrateId
     * @return
     * @throws BimfaceException
     */
    public List<SegmentGroupDto> getIntegrateModelSegmentGroups(Long integrateId) throws BimfaceException {
        return dataService.getIntegrateModelSegmentGroups(integrateId);
    }

    /**
     * 获取集成模型的流水段树, v2
     *
     * @param integrateId
     * @return
     * @throws BimfaceException
     */
    public List<SegmentGroupDto> getIntegrateModelSegmentTree(Long integrateId) throws BimfaceException {
        return dataService.getIntegrateModelSegmentTree(integrateId);
    }

    /**
     * 获取流水段分组中的所有流水段, v2
     *
     * @param integrateId
     * @param segmentGroupId
     * @return
     * @throws BimfaceException
     */
    public List<SegmentDto> getIntegrateModelSegments(Long integrateId, String segmentGroupId) throws BimfaceException {
        return dataService.getIntegrateModelSegments(integrateId, segmentGroupId);
    }

    /**
     * 获取流水段对应的所有构件id
     *
     * @param integrateId 集成ID
     * @param segmentId   流水段ID；支持批量查询，多个segment id用逗号隔开
     * @return
     * @throws BimfaceException
     */
    public List<String> getIntegrateModelSegmentElementIds(Long integrateId, String segmentId) throws BimfaceException {
        return dataService.getIntegrateModelSegmentElementIds(integrateId, segmentId);
    }

    /**
     * 集成模型获取构件属性
     *
     * @param integrateId
     * @param fileIdHashWithElementIds
     * @return
     * @throws BimfaceException
     */
    public Property getIntegrateModelCommonElementProperties(Long integrateId, List<FileIdHashWithElementIds> fileIdHashWithElementIds) throws BimfaceException {
        return dataService.getIntegrateModelCommonElementProperties(integrateId, fileIdHashWithElementIds);
    }

    /**
     * 新增双向业务挂接,可以在request body中指定新增项，也可以指定从其他集成拷贝
     *
     * @param integrateId
     * @param fromIntegrationId
     * @param fromBusinessType
     * @param fromBusinessFlag
     * @param request
     * @throws BimfaceException
     */
    public void addAssociations(String integrateId, String fromIntegrationId, String fromBusinessType, String fromBusinessFlag,
                                BusinessAssociationRequest request) throws BimfaceException {
        dataService.addAssociations(integrateId, fromIntegrationId, fromBusinessType, fromBusinessFlag, request);
    }

    /**
     * 根据构件ID查询对应业务挂接
     *
     * @param integrateId
     * @param elementId
     * @param businessType
     * @param businessFlag
     * @return
     * @throws BimfaceException
     */
    public List<ElementBusinessAssociation> getAssociationsByElementId(String integrateId,String elementId, String businessType,
                                                                       String businessFlag) throws BimfaceException {
        return dataService.getAssociationsByElementId(integrateId, elementId, businessType, businessFlag);
    }

    /**
     * 根据业务ID查询对应构件
     *
     * @param integrateId
     * @param businessType
     * @param businessId
     * @return
     * @throws BimfaceException
     */
    public List<BusinessElementAssociation> getAssociationsByBusinessId(String integrateId, String businessType, String businessId) throws BimfaceException {
        return dataService.getAssociationsByBusinessId(integrateId, businessType, businessId);
    }

    /**
     * 删除构件关联的业务挂接
     *
     * @param integrateId
     * @param elementId
     * @param businessType
     * @param businessFlag
     * @param businessIds
     * @throws BimfaceException
     */
    public void deleteAssociationsByElementId(String integrateId, String elementId, String businessType,
                                              String businessFlag, List<String> businessIds) throws BimfaceException {
        dataService.deleteAssociationsByElementId(integrateId, elementId, businessType, businessFlag, businessIds);
    }

    /**
     * 删除业务ID关联的业务挂接
     * @param integrateId
     * @param businessType
     * @param businessId
     * @throws BimfaceException
     */
    public void deleteAssociationsByBusinessId(String integrateId, String businessType, String businessId) throws BimfaceException {
        dataService.deleteAssociationsByBusinessId(integrateId, businessType, businessId);
    }

    /**
     * 删除业务挂接
     *
     * @param integrateId
     * @param businessType
     * @param businessFlag
     * @param elementIds
     * @throws BimfaceException
     */
    public void deleteAssociationsByElements(String integrateId, String businessType, String businessFlag,
                                             List<String> elementIds) throws BimfaceException {
        dataService.deleteAssociationsByElements(integrateId, businessType, businessFlag, elementIds);
    }

    /**
     * 删除业务挂接
     *
     * @param integrateId
     * @param businessType
     * @param businessFlag
     * @param businessIds
     * @throws BimfaceException
     */
    public void deleteAssociationsByBizIds(String integrateId, String businessType, String businessFlag,
                                           List<String> businessIds) throws BimfaceException {
        dataService.deleteAssociationsByBizIds(integrateId, businessType, businessFlag, businessIds);
    }

    /**
     * 获取模型构件对比差异, v2
     *
     * @param comparisonId
     * @param previousFileId
     * @param previousElementId
     * @param followingFileId
     * @param followingElementId
     * @return
     * @throws BimfaceException
     */
    public ModelCompareChange getModelCompareElementChange(Long comparisonId, Long previousFileId, String previousElementId, Long followingFileId, String followingElementId) throws BimfaceException {
        return dataService.getModelCompareElementChange(comparisonId, previousFileId, previousElementId, followingFileId, followingElementId);
    }

    /**
     * 获取模型对比构件分类树, v2
     *
     * @param comparisonId
     * @return
     * @throws BimfaceException
     */
    public Tree getModelCompareTree(Long comparisonId) throws BimfaceException {
        return dataService.getModelCompareTree(comparisonId);
    }

    /**
     * 分页获取模型对比结果, v2
     *
     * @param comparisonId
     * @param family
     * @param elementName
     * @param page
     * @param pageSize
     * @return
     * @throws BimfaceException
     */
    public Pagination<ModelCompareDiff> getModelCompareResult(Long comparisonId, String family, String elementName, Integer page,
                                                              Integer pageSize) throws BimfaceException {
        return dataService.getModelCompareResult(comparisonId, family, elementName, page, pageSize);
    }

    /**
     * 获取材质配置方案, v2
     *
     * @param id
     * @return
     * @throws BimfaceException
     */
    public MaterialOverrideSetVO getMaterialOverrideSet(Long id) throws BimfaceException {
        return dataService.getMaterialOverrideSet(id);
    }

    /**
     * 获取rfa文件族属性key列表, v2
     *
     * @param rfaFileId
     * @return
     * @throws BimfaceException
     */
    public List<String> getRfaFamilyPropertyNames(Long rfaFileId) throws BimfaceException {
        return dataService.getRfaFamilyPropertyNames(rfaFileId);
    }

    /**
     * rfa文件族类型列表, v2
     *
     * @param rfaFileId
     * @return
     * @throws BimfaceException
     */
    public List<RfaFamilyType> getRfaFamilyTypes(Long rfaFileId) throws BimfaceException {
        return dataService.getRfaFamilyTypes(rfaFileId);
    }

    /**
     * 获取rfa文件族类型属性列表, v2
     *
     * @param rfaFileId
     * @param familyTypeGuid
     * @return
     * @throws BimfaceException
     */
    public RfaFamilyTypeProperty getRfaFamilyTypeProperty(Long rfaFileId, String familyTypeGuid) throws BimfaceException {
        return dataService.getRfaFamilyTypeProperty(rfaFileId, familyTypeGuid);
    }

    /**
     * 查询elementId
     *
     * @param requestBody
     * @return
     * @throws BimfaceException
     */
    public List<SearchElementIdsResp> getElements(String requestBody) throws BimfaceException {

        return dataService.getElements(requestBody);
    }

    /**
     * 查询roomId
     *
     * @param requestBody
     * @return
     * @throws BimfaceException
     */
    public List<SearchRoomIdsResp> getRooms(String requestBody) throws BimfaceException {
        return dataService.getRooms(requestBody);
    }

    /**
     * 查询areaId
     *
     * @param requestBody
     * @return
     * @throws BimfaceException
     */
    public List<SearchAreaIdsResp> getAreas(String requestBody) throws BimfaceException {
        return dataService.getAreas(requestBody);
    }

    /**
     * 查询一些文件中某个property的所有可能值
     *
     * @param targetIds
     * @param targetType
     * @param properties
     * @return
     * @throws BimfaceException
     */
    public List<PropertyValuesResp> getPropertyValues(List<String> targetIds, String targetType,
                                                      List<String> properties) throws BimfaceException {
        return dataService.getPropertyValues(targetIds, targetType, properties);
    }

    /**
     * 获取缩略图url
     *
     * @param fileId
     * @param size
     * @return
     * @throws BimfaceException
     */
    public String getFileThumbnailUrl(Long fileId, Integer size) throws BimfaceException {
        return dataService.getFileThumbnailUrl(fileId, size);
    }

    /**
     * 获取DWG文件转换生成的pdf文件的url
     *
     * @param dwgFileId
     * @return
     * @throws BimfaceException
     */
    public String getDwgPdfUrl(Long dwgFileId) throws BimfaceException {
        return dataService.getDwgPdfUrl(dwgFileId);
    }

    /**
     * 获取DWG文件转换生成的预览图片的url
     *
     * @param dwgFileId
     * @param layoutName
     * @return
     * @throws BimfaceException
     */
    public String getDwgPreviewImageUrl(Long dwgFileId, String layoutName) throws BimfaceException {
        return dataService.getDwgPreviewImageUrl(dwgFileId, layoutName);
    }

    /**
     * 获取文件的数据包根URL
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public String getFileDataBagRootUrl(Long fileId) throws BimfaceException{
        return databagService.getFileDataBagRootUrl(fileId);
    }

    /**
     * 获取集成数据包的根Url
     * @param integrateId
     * @return
     * @throws BimfaceException
     */
    public String getIntegrateDatabagRootUrl(Long integrateId) throws BimfaceException{
        return databagService.getIntegrateDatabagRootUrl(integrateId);
    }

    /**
     * 获取数据包下载流
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public InputStream getFileDatabagContent(Long fileId) throws BimfaceException{
        return databagService.getFileDatabagContent(fileId);
    }

    /**
     * 获取数据包大小
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public Long getFileDatabagSize(Long fileId) throws BimfaceException {
        return databagService.getFileDatabagSize(fileId);
    }

}