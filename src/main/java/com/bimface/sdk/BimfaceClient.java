package com.bimface.sdk;

import com.bimface.api.bean.compatible.response.BatchDeleteResultBean;
import com.bimface.api.bean.compatible.response.ShareLinkBean;
import com.bimface.api.bean.request.integrate.FileIntegrateRequest;
import com.bimface.api.bean.request.integrate.IntegrateQueryRequest;
import com.bimface.api.bean.request.modelCompare.CompareRequest;
import com.bimface.api.bean.request.modelCompare.ModelCompareQueryRequest;
import com.bimface.api.bean.request.modelCompare.ModelCompareRequest;
import com.bimface.api.bean.request.translate.FileTranslateRequest;
import com.bimface.api.bean.request.translate.TranslateQueryRequest;
import com.bimface.api.bean.request.translate.TranslateSource;
import com.bimface.api.bean.response.*;
import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.IntegrateDatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.TranslateDatabagDerivativeBean;
import com.bimface.data.bean.*;
import com.bimface.data.enums.ToleranceType;
import com.bimface.exception.BimfaceException;
import com.bimface.file.bean.*;
import com.bimface.page.PagedList;
import com.bimface.sdk.bean.request.FileBatchQueryRequest;
import com.bimface.sdk.bean.request.FileUploadRequest;
import com.bimface.sdk.bean.request.OfflineDatabagRequest;
import com.bimface.sdk.bean.request.QueryElementIdsRequest;
import com.bimface.sdk.bean.request.compare.CompareElementRequest;
import com.bimface.sdk.config.Config;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.config.authorization.AccessTokenStorage;
import com.bimface.sdk.config.authorization.Credential;
import com.bimface.sdk.config.authorization.DefaultAccessTokenStorage;
import com.bimface.sdk.service.*;
import com.bimface.sdk.utils.AssertUtils;

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
    private ExportDatabagService exportDatabagService;
    private BakeService bakeService;
    private FloorService floorService;
    private SignatureService signatureService;
    private DataService dataService;
    private DatabagService databagService;
    private DrawingSplitService drawingSplitService;

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
        exportDatabagService = new ExportDatabagService(this.endpoint, accessTokenService);
        floorService = new FloorService(this.endpoint, accessTokenService);
        signatureService = new SignatureService(credential);
        dataService = new DataService(this.endpoint, accessTokenService);
        databagService = new DatabagService(this.endpoint, accessTokenService);
        bakeService = new BakeService(this.endpoint, accessTokenService);
        drawingSplitService = new DrawingSplitService(this.endpoint, accessTokenService);
    }

    /**
     * 获取支持的文件格式及文件大小
     *
     * @return 文件后缀名数组 {@link SupportFileBean}
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
     * 已过时，推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getFile(java.lang.Long) getFile(java.lang.Long)}
     *
     * @param fileId 文件Id
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public FileBean getFileMetadata(Long fileId) throws BimfaceException {
        return fileService.getFileMetadata(fileId);
    }

    /**
     * 根据文件id获取文件元信息
     *
     * @param fileId 文件Id
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean getFile(Long fileId) throws BimfaceException {
        return fileService.getFile(fileId);
    }

    /**
     * 批量查询文件元信息
     *
     * @param request {@link FileBatchQueryRequest}
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<FileBean> getFiles(FileBatchQueryRequest request) throws BimfaceException {
        return fileService.getFiles(request);
    }

    /**
     * 根据文件id获取文件上传状态信息
     *
     * @param fileId 文件Id
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileUploadStatusBean getFileUploadStatus(Long fileId) throws BimfaceException {
        return fileService.getFileUploadStatus(fileId);
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
     * @return {@link String} 文件下载链接
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
     * @return {@link String} 文件下载链接
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
     * @return {@link InputStream} 文件流
     * @throws BimfaceException {@link BimfaceException}
     */
    public InputStream download(Long fileId, String fileName) throws BimfaceException {
        return downloadService.getFileContent(fileId, fileName);
    }


    /**
     * 通过条件查询构件ID组
     * 已过时，推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getElementIdsV2(java.lang.Long, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     * getElementIdsV2(java.lang.Long, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)}
     *
     * @param fileId     文件ID
     * @param categoryId 构件类型ID
     * @param family     族名称
     * @param familyType 族类型
     * @return List&lt;{@link String}&gt; 文件列表
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public List<String> getElements(Long fileId, String categoryId, String family,
                                    String familyType) throws BimfaceException {
        return elementService.getElements(fileId, null, null, categoryId, family, familyType);
    }

    /**
     * 通过条件查询构件ID组
     * 已过时，推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getElementIdsV2(java.lang.Long, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     * getElementIdsV2(java.lang.Long, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)}
     *
     * @param fileId     文件ID
     * @param floor      楼层
     * @param specialty  专业
     * @param categoryId 构件类型ID
     * @param family     族名称
     * @param familyType 族类型
     * @return List&lt;{@link String}&gt;
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public List<String> getElements(Long fileId, String floor, String specialty, String categoryId, String family,
                                    String familyType) throws BimfaceException {
        return elementService.getElements(fileId, floor, specialty, categoryId, family, familyType);
    }

    /**
     * 获取集成模型的构件列表
     * 已过时，推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getIntegrateModelElementIds(java.lang.Long, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     * getIntegrateModelElementIds(java.lang.Long, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * @param integrateId 集成id
     * @param floor       楼层
     * @param specialty   专业
     * @param categoryId  分类id
     * @param family      族
     * @param familyType  族类型
     * @return {@link ElementsWithBoundingBox}
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
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
     * 批量获取文件转换状态
     *
     * @param translateQueryRequest {@link TranslateQueryRequest}
     * @return PagedList&lt;{@link FileTranslateDetailBean}&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public PagedList<FileTranslateDetailBean> getTranslates(TranslateQueryRequest translateQueryRequest) throws BimfaceException {
        return translateService.getTranslates(translateQueryRequest);
    }

    /**
     * 发起模型对比
     * 已过期，推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#compareV2(com.bimface.api.bean.request.modelCompare.CompareRequest)
     * compareV2(com.bimface.api.bean.request.modelCompare.CompareRequest)}
     * @param modelCompareRequest {@link ModelCompareBean}
     * @return {@link FileTranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public ModelCompareBean compare(ModelCompareRequest modelCompareRequest) throws BimfaceException {
        return compareService.compare(modelCompareRequest);
    }

    /**
     * 发起模型对比，V2
     *
     * @param compareRequest {@link ModelCompareBean}
     * @return {@link FileTranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ModelCompareBean compareV2(CompareRequest compareRequest) throws BimfaceException {
        return compareService.compareV2(compareRequest);
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
     * 批量获取模型对比状态
     *
     * @param modelCompareQueryRequest {@link ModelCompareQueryRequest}
     * @return PagedList &lt;{@link ModelCompareBean }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public PagedList<ModelCompareBean> getCompares(ModelCompareQueryRequest modelCompareQueryRequest) throws BimfaceException {
        return compareService.getCompares(modelCompareQueryRequest);
    }

    /**
     * 删除模型对比
     *
     * @param compareId 模型对比id
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    public void deleteCompare(Long compareId) throws BimfaceException {
        compareService.deleteCompare(compareId);
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
     * 已过时，推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getModelCompareElementChange(java.lang.Long, java.lang.Long, java.lang.String,
     * java.lang.Long, java.lang.String)
     * getModelCompareElementChange(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String)}
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
     * @return {@link String} viewToken 预览凭证
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewTokenByIntegrateId(Long integrateId) throws BimfaceException {
        return viewTokenService.getViewTokenByIntegrateId(integrateId);
    }

    /**
     * 获取模型对比viewToken, 用于模型预览的凭证
     *
     * @param compareId 模型对比id
     * @return {@link String} viewToken 预览凭证
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewTokenByCompareId(Long compareId) throws BimfaceException {
        return viewTokenService.getViewTokenByCompareId(compareId);
    }

    /**
     * 获取viewToken, 用于模型预览的凭证
     *
     * @param fileId 文件id
     * @return {@link String} viewToken 预览凭证
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
     *
     * @param fileId 文件id
     * @param expireDate 过期日期
     * @param needPassword 是否需要密码
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
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
     *
     * @param integrateId 集成id
     * @param expireDate 过期日期
     * @param needPassword 是否需要密码
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
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
     *
     * @param sourceIds 源id
     * @return {@link BatchDeleteResultBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public BatchDeleteResultBean<Long> batchDeteleShare(List<Long> sourceIds) throws BimfaceException {
        return shareLinkService.batchDeteleShare(sourceIds);
    }

    /**
     * 获取分享链接信息
     *
     * @param token token
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean getShareLink(String token) throws BimfaceException {
        return shareLinkService.getShareLink(token);
    }

    /**
     * 获取分享链接信息
     *
     * @param fileId 文件id
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean getTranslateShare(Long fileId) throws BimfaceException {
        return shareLinkService.getTranslateShare(fileId);
    }

    /**
     * 获取分享链接信息
     *
     * @param integrateId 集成id
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean getIntegrateShare(Long integrateId) throws BimfaceException {
        return shareLinkService.getIntegrateShare(integrateId);
    }

    /**
     * 获取分享列表
     *
     * @param pageNo 分页查询的页数
     * @param pageSize 分页查询的每页大小
     * @return PagedList &lt;{@link ShareLinkBean }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public PagedList<ShareLinkBean> shares(Integer pageNo, Integer pageSize) throws BimfaceException {
        return shareLinkService.shareList(pageNo, pageSize);
    }

    /**
     * 获取单文件模型对应构件下的属性
     * 已过时，推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getSingleModelElementV2(java.lang.Long, java.lang.String)
     * getSingleModelElementV2(java.lang.Long, java.lang.String)}
     *
     * @param fileId    文件id
     * @param elementId 构件id
     * @return PropertyBean {@link Property}
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public Property getProperty(Long fileId, String elementId) throws BimfaceException {
        return propertyService.getElementProperty(fileId, elementId);
    }

    /**
     * 获取集成模型对应构件下的属性
     * 已过时，推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getIntegrateModelElementMaterials(java.lang.Long, java.lang.String, java.lang.String)
     * getIntegrateModelElementMaterials(java.lang.Long, java.lang.String, java.lang.String)}
     * @param integrateId 集成id
     * @param fileId      文件id
     * @param elementId   构件id
     * @return {@link Property}
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public Property getIntegrationProperty(Long integrateId, Long fileId,
                                           String elementId) throws BimfaceException {
        return propertyService.getIntegrationElementProperty(integrateId, fileId, elementId);
    }

    /**
     * 获取单文件构件分类树
     * 已过时，推荐使用V2版本文件分类数
     * {@linkplain com.bimface.sdk.BimfaceClient#getSingleModelTreeV2(java.lang.Long, com.bimface.data.bean.FileTreeRequestBody)
     * getSingleModelTreeV2(java.lang.Long, com.bimface.data.bean.FileTreeRequestBody)}
     * @param fileId 文件id
     * @return List&lt;{@link Category}&gt;
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public List<Category> getCategory(Long fileId) throws BimfaceException {
        return categoryTreeService.getCategoryTree(fileId);
    }

    /**
     * 获取单文件构件分类树V2
     * 已过时，推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getSingleModelTreeV2(java.lang.Long, com.bimface.data.bean.FileTreeRequestBody)
     * getSingleModelTreeV2(java.lang.Long, com.bimface.data.bean.FileTreeRequestBody)}
     * @param fileId 文件id
     * @return List &lt;{@link Tree.TreeNode }&gt;
     * @throws BimfaceException {@link BimfaceException} {@link com.bimface.exception.BimfaceException}
     * @deprecated
     */
    @Deprecated
    public List<Tree.TreeNode> getCategoryV2(Long fileId) throws BimfaceException {
        return categoryTreeService.getCategoryTreeV2(fileId);
    }

    /**
     * 获取集成模型专业层次结构
     * 已过时，推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getIntegrateModelTree(java.lang.Long, java.lang.String,
     * java.util.List, com.bimface.data.bean.IntegrationTreeOptionalRequestBody)
     * getIntegrateModelTree(java.lang.Long, java.lang.String, java.util.List, com.bimface.data.bean.IntegrationTreeOptionalRequestBody)}
     *
     * @param integrateId 集成id
     * @return {@link SpecialtyTree}
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public SpecialtyTree getSpecialtyTree(Long integrateId) throws BimfaceException {
        return categoryTreeService.getSpecialtyTree(integrateId);
    }

    /**
     * 获取集成模型楼层层次结构
     * 已过时，推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getIntegrateModelTree(java.lang.Long, java.lang.String,
     * java.util.List, com.bimface.data.bean.IntegrationTreeOptionalRequestBody)
     * getIntegrateModelTree(java.lang.Long, java.lang.String, java.util.List, com.bimface.data.bean.IntegrationTreeOptionalRequestBody)}
     *
     * @param integrateId 集成id
     * @return {@link FloorTree}
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
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
     * 批量获取文件集成状态
     *
     * @param integrateQueryRequest 集成文件查询条件
     * @return PagedList &lt;{@link FileIntegrateDetailBean }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public PagedList<FileIntegrateDetailBean> getIntegrates(IntegrateQueryRequest integrateQueryRequest) throws BimfaceException {
        return integrateService.getIntegrates(integrateQueryRequest);
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
     * 已过时：推荐使用：
     * 查询转换的离线数据包
     * {@linkplain com.bimface.sdk.BimfaceClient#queryTranslateOfflineDatabag(java.lang.Long) queryTranslateOfflineDatabag(java.lang.Long)}
     * 查询集成的离线数据包
     * {@linkplain com.bimface.sdk.BimfaceClient#queryIntegrateOfflineDatabag(java.lang.Long) queryIntegrateOfflineDatabag(java.lang.Long)}
     * 查询比较的离线数据包
     * {@linkplain com.bimface.sdk.BimfaceClient#queryCompareOfflineDatabag(java.lang.Long) queryCompareOfflineDatabag(java.lang.Long)}
     *
     * @param offlineDatabagRequest {@link OfflineDatabagRequest}
     * @return List &lt;{@link DatabagDerivativeBean }&gt;
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public List<? extends DatabagDerivativeBean> queryOfflineDatabag(OfflineDatabagRequest offlineDatabagRequest) throws BimfaceException {
        return offlineDatabagService.queryOfflineDatabag(offlineDatabagRequest);
    }

    /**
     * 查询离线数据包
     *
     * @param fileId 文件id
     * @return List &lt;{@link DatabagDerivativeBean }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<? extends DatabagDerivativeBean> queryTranslateOfflineDatabag(Long fileId) throws BimfaceException {
        return offlineDatabagService.queryOfflineDatabag(fileId, null, null);
    }

    /**
     * 查询离线数据包
     *
     * @param integrateId 集成id
     * @return List &lt;{@link DatabagDerivativeBean }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<? extends DatabagDerivativeBean> queryIntegrateOfflineDatabag(Long integrateId) throws BimfaceException {
        return offlineDatabagService.queryOfflineDatabag(null, integrateId, null);
    }

    /**
     * 查询离线数据包
     *
     * @param compareId 比较id
     * @return List &lt;{@link DatabagDerivativeBean }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<? extends DatabagDerivativeBean> queryCompareOfflineDatabag(Long compareId) throws BimfaceException {
        return offlineDatabagService.queryOfflineDatabag(null, null, compareId);
    }

    /**
     * 通过文件ID导出gltf数据包
     *
     * @param fileId 文件id
     * @param callback 回调url地址，BIMFACE支持在创建离线数据包完成以后，通过Callback机制通知调用方
     * @return TranslateDatabagDerivativeBean {@link TranslateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public TranslateDatabagDerivativeBean exportTranslateGltfDatabag(Long fileId, String callback) throws BimfaceException {
        return exportDatabagService.exportTranslateGltfDatabag(fileId, callback);
    }

    /**
     * 查询gltf数据包信息
     *
     * @param fileId 文件id
     * @return List&lt;TranslateDatabagDerivativeBean&gt; {@link TranslateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<TranslateDatabagDerivativeBean> getTranslateGltfDatabag(Long fileId) throws BimfaceException {
        return exportDatabagService.getTranslateGltfDatabag(fileId);
    }

    /**
     * 通过集成模型ID创建gltf数据包
     *
     * @param integrateId 集成id
     * @param callback 回调url地址，BIMFACE支持在创建离线数据包完成以后，通过Callback机制通知调用方
     * @return IntegrateDatabagDerivativeBean {@link IntegrateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public IntegrateDatabagDerivativeBean exportIntegrateGltfDatabag(Long integrateId, String callback) throws BimfaceException {
        return exportDatabagService.exportIntegrateGltfDatabag(integrateId, callback);
    }

    /**
     * 查询集成模型gltf数据包
     *
     * @param integrateId 集成id
     * @return List&lt;IntegrateDatabagDerivativeBean&gt; {@link IntegrateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<IntegrateDatabagDerivativeBean> getIntegrateGltfDatabags(Long integrateId) throws BimfaceException {
        return exportDatabagService.getIntegrateGltfDatabags(integrateId);
    }

    /**
     * 指定文件ID，获取gltf数据包的下载链接
     * 如果只有一个数据包版本，则下载唯一的数据包，如果多个，则必须指定数据包版本
     * 在浏览器中输入gltf数据包的下载地址，即可下载得到对应模型的gltf数据包。
     *
     * @param fileId 文件id
     * @param databagVersion 数据包版本
     * @return gltf数据包的下载地址
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getTranslateGltfDatabagUrl(Long fileId, String databagVersion) throws BimfaceException {
        return exportDatabagService.getTranslateGltfDatabagUrl(fileId, databagVersion);
    }

    /**
     * 指定集成ID，获取gltf数据包的下载链接
     * 如果只有一个数据包版本，则下载唯一的数据包，如果多个，则必须指定数据包版本
     * 在浏览器中输入gltf数据包的下载地址，即可下载得到对应模型的gltf数据包。
     *
     * @param integrateId 集成id
     * @param databagVersion 数据包版本
     * @return gltf数据包的下载地址
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getIntegrateGltfDatabagUrl(Long integrateId, String databagVersion) throws BimfaceException {
        return exportDatabagService.getIntegrateGltfDatabagUrl(integrateId, databagVersion);
    }

    /**
     * 通过文件ID创建3DTiles数据包
     * 需要文件转换成功后调用
     *
     * @param fileId 文件id
     * @param callback 回调url，BIMFACE支持在创建离线数据包完成以后，通过Callback机制通知调用方
     * @param config 参数设置
     * @return TranslateDatabagDerivativeBean {@link TranslateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public TranslateDatabagDerivativeBean exportTranslate3DTilesDatabag(Long fileId, String callback, Map<String, String> config) throws BimfaceException {
        return exportDatabagService.exportTranslate3DTilesDatabag(fileId, callback, config);
    }

    /**
     * 查询文件的3DTiles数据包
     *
     * @param fileId 文件id
     * @return List&lt;TranslateDatabagDerivativeBean&gt; {@link TranslateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<TranslateDatabagDerivativeBean> getTranslate3DTilesDatabags(Long fileId) throws BimfaceException {
        return exportDatabagService.getTranslate3DTilesDatabags(fileId);
    }

    /**
     * 通过集成模型ID创建3DTiles数据包
     * 需要模型集成成功后调用
     *
     * @param integrateId 集成id
     * @param callback 回调url，BIMFACE支持在创建离线数据包完成以后，通过Callback机制通知调用方
     * @param config 参数设置
     * @return IntegrateDatabagDerivativeBean {@link IntegrateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public IntegrateDatabagDerivativeBean exportIntegrate3DTilesDatabag(Long integrateId, String callback, Map<String, String> config) throws BimfaceException {
        return exportDatabagService.exportIntegrate3DTilesDatabag(integrateId, callback, config);
    }

    /**
     * 查询集成模型3DTiles数据包
     *
     * @param integrateId 集成id
     * @return List&lt;IntegrateDatabagDerivativeBean&gt; {@link IntegrateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<IntegrateDatabagDerivativeBean> getIntegrate3DTilesDatabags(Long integrateId) throws BimfaceException {
        return exportDatabagService.getIntegrate3DTilesDatabags(integrateId);
    }

    /**
     * 指定文件ID，获取3DTiles数据包下载地址
     * 数据包版本；如果只有一个，则下载唯一的数据包，如果多个，则必须指定数据包版本
     * 在浏览器中输入3DTiles数据包的下载地址，即可下载得到对应模型的3DTiles数据包。
     *
     * @param fileId
     * @param databagVersion
     * @return 3DTiles数据包的下载地址
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getTranslate3dTilesDatabagUrl(Long fileId, String databagVersion) throws BimfaceException {
        return exportDatabagService.getTranslate3dTilesDatabagUrl(fileId, databagVersion);
    }

    /**
     * 指定集成ID，获取3DTiles数据包下载地址
     * 数据包版本；如果只有一个，则下载唯一的数据包，如果多个，则必须指定数据包版本
     * 在浏览器中输入3DTiles数据包的下载地址，即可下载得到对应模型的3DTiles数据包。
     *
     * @param integrateId
     * @param databagVersion
     * @return 3DTiles数据包的下载地址
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getIntegrate3dTilesDatabagUrl(Long integrateId, String databagVersion) throws BimfaceException {
        return exportDatabagService.getIntegrate3dTilesDatabagUrl(integrateId, databagVersion);
    }

    /**
     * 为文件创建bake数据包
     * 需要文件转换成功后调用
     *
     * @param fileId 文件id
     * @param callback 回调url，BIMFACE支持在创建离线数据包完成以后，通过Callback机制通知调用方
     * @param config 参数设置
     * @return TranslateDatabagDerivativeBean {@link TranslateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public TranslateDatabagDerivativeBean createTranslateBakeDatabag(Long fileId, String callback, Map<String, String> config) throws BimfaceException{
        return bakeService.createTranslateBakeDatabag(fileId, callback, config);
    }

    /**
     * 查询文件bake数据包
     * @param fileId 文件id
     * @return List&lt;TranslateDatabagDerivativeBean&gt; {@link TranslateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<TranslateDatabagDerivativeBean> getTranslateBakeDatabags(Long fileId) throws BimfaceException {
        return bakeService.getTranslateBakeDatabags(fileId);
    }

    /**
     * 为集成模型创建bake数据包
     * 需要模型集成成功后调用
     *
     * @param integrateId 集成id
     * @param callback 回调url，BIMFACE支持在创建离线数据包完成以后，通过Callback机制通知调用方
     * @param config 参数设置
     * @return IntegrateDatabagDerivativeBean {@link IntegrateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public IntegrateDatabagDerivativeBean createIntegrateBakeDatabag(Long integrateId, String callback, Map<String, String> config) throws BimfaceException{
        return bakeService.createIntegrateBakeDatabag(integrateId, callback, config);
    }

    /**
     * 查询集成模型bake数据包
     * @param integrateId 集成id
     * @return List&lt;IntegrateDatabagDerivativeBean&gt; {@link IntegrateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<IntegrateDatabagDerivativeBean> getIntegrateBakeDatabags(Long integrateId) throws BimfaceException {
        return bakeService.getIntegrateBakeDatabags(integrateId);
    }

    /**
     * 发起图纸拆分
     * @param fileId 文件id
     * @param callback 回调url，BIMFACE支持在创建离线数据包完成以后，通过Callback机制通知调用方
     * @param config 参数设置
     * @return TranslateDatabagDerivativeBean {@link TranslateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public TranslateDatabagDerivativeBean createDrawingSplit(Long fileId, String callback, Map<String, String> config) throws BimfaceException {
        return drawingSplitService.createDrawingSplit(fileId, callback, config);
    }

    /**
     * 获取拆图状态
     * 图纸发起拆分后，可以通过该接口查询拆图状态
     *
     * @param fileId 文件id
     * @return TranslateDatabagDerivativeBean {@link TranslateDatabagDerivativeBean} 数据包元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public TranslateDatabagDerivativeBean getDrawingSplit(Long fileId) throws BimfaceException {
        return drawingSplitService.getDrawingSplit(fileId);
    }

    /**
     * 获取图纸拆分结果
     * @param fileId 文件id
     * @return List&lt;DrawingSplitLayout&gt; {@link DrawingSplitLayout}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<DrawingSplitLayout> getDrawingFrames(Long fileId) throws BimfaceException {
        return drawingSplitService.getDrawingFrames(fileId);
    }

    /**
     * 分页获取图纸对比结果
     * @param comparisonId 图纸对比ID
     * @param layer 图层名称
     * @param page 页码
     * @param pageSize 每页记录数
     * @return Pagination &lt;{@link DrawingCompareDiff }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public Pagination<DrawingCompareDiff> pageGetDrawingCompareResult(Long comparisonId, String layer, Integer page, Integer pageSize) throws BimfaceException {
        return dataService.pageGetDrawingCompareResult(comparisonId, layer, page, pageSize);
    }

    /**
     * 获取MEP系统信息
     * @param fileId 需要获取系统信息的文件Id
     * @param systemCategory 希望获取的系统的systemCategory
     * @param systemType 希望获取的系统的systemType
     * @return List&lt;MEPSystem&gt; {@link MEPSystem}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<MEPSystem> getMEPSystem(Long fileId, String systemCategory, String systemType) throws BimfaceException {
        return dataService.getMEPSystem(fileId, systemCategory, systemType);
    }

    /**
     * 计算指定构件列表的包围盒列表
     * 获取每个构件对应的包围盒
     *
     * @param integrateId 模型集成ID
     * @param fileIdWithEleIdList 构件ID列表,由','分隔.每个构件ID由fileID和elementID组成,由'.'分隔;也可以由单个elementId组成
     * @return List&lt;ElementIdWithBoundingBox&gt; {@link ElementIdWithBoundingBox}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<ElementIdWithBoundingBox> getBoundingBoxesByEleIds(Long integrateId, List<String> fileIdWithEleIdList) throws BimfaceException {
        return dataService.getBoundingBoxes(integrateId, fileIdWithEleIdList);
    }

    /**
     * 获取离线数据包信息
     * 已过时：
     * 请使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getOfflineDatabagUrl(java.lang.Long, java.lang.Long, java.lang.Long,
     * java.lang.String) getOfflineDatabagUrl(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String)}
     *
     * @param offlineDatabagRequest {@link OfflineDatabagRequest}
     * @return 离线数据包下载地址
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public String getOfflineDatabagUrl(OfflineDatabagRequest offlineDatabagRequest) throws BimfaceException {
        return offlineDatabagService.getOfflineDatabagUrl(offlineDatabagRequest);
    }

    /**
     * 获取离线数据包下载链接
     *
     * @param fileId 文件id
     * @param integrateId 集成id
     * @param compareId 比较id
     * @param databagVersion 离线数据包版本
     * @return 离线数据包下载链接
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getOfflineDatabagUrl(Long fileId, Long integrateId, Long compareId, String databagVersion) throws BimfaceException {
        AssertUtils.assertTrue(fileId!=null || integrateId != null || compareId != null,
                "fileId, integrateId, compareId can not be null for all !");
        return offlineDatabagService.getOfflineDatabagUrl(fileId, integrateId, compareId, databagVersion);
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
     * @return AppendFileBean {@link AppendFileBean} 追加文件的元信息
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
     * @return AppendFileBean {@link AppendFileBean} 追加文件的元信息
     * @throws BimfaceException {@link BimfaceException}
     */
    public AppendFileBean uploadAppendFile(InputStream inputStream, Long appendFileId) throws BimfaceException {
        return fileService.uploadAppendFile(inputStream, appendFileId);
    }

    /**
     * 获取楼层信息
     * 已过时：推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getFileFloors(java.lang.Long, Boolean, Boolean)
     *  getFileFloors(java.lang.Long, Boolean, Boolean)
     * }
     *
     * @param fileId 文件Id
     * @return 楼层列表
     * @throws BimfaceException {@link BimfaceException} {@link BimfaceClient}
     * @deprecated
     */
    @Deprecated
    public List<Floor> getFileFloors(Long fileId) throws BimfaceException {
        return getFileFloors(fileId, false, false);
    }

    /**
     * 获取楼层信息
     *
     * @param fileId      fileId 文件Id
     * @param includeArea 是否将楼层中的面积分区ID、名称一起返回
     * @param includeRoom 是否将楼层中的房间ID、名称一起返回
     * @return 楼层列表
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<Floor> getFileFloors(Long fileId, Boolean includeArea, Boolean includeRoom) throws BimfaceException {
        return floorService.getFileFloors(fileId, includeArea, includeRoom);
    }

    /**
     * 获取集成模型楼层信息
     * 已过时：推荐使用
     * {@link com.bimface.sdk.BimfaceClient#getIntegrateFloors(java.lang.Long, java.lang.Boolean, java.lang.Boolean)
     * getIntegrateFloors(java.lang.Long, java.lang.Boolean, java.lang.Boolean)
     * }
     *
     * @param integrateId 模型集成ID
     * @return 楼层列表
     * @throws BimfaceException {@link BimfaceException} {@link BimfaceClient}
     * @deprecated
     */
    @Deprecated
    public List<Floor> getIntegrateFloors(Long integrateId) throws BimfaceException {
        return getIntegrateFloors(integrateId, false, false);
    }

    /**
     * 获取集成模型楼层信息
     *
     * @param integrateId 模型集成ID
     * @param includeArea 是否将楼层中的空间ID、名称一起返回
     * @param includeRoom 是否将楼层中的房间ID、名称一起返回
     * @return 楼层列表
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<Floor> getIntegrateFloors(Long integrateId, Boolean includeArea, Boolean includeRoom) throws BimfaceException {
        return floorService.getIntegrateFloors(integrateId, includeArea, includeRoom);
    }

    /**
     * 按查询条件查询单模型构件ID 组，v2
     * 已过时：推荐使用：
     * {@link com.bimface.sdk.BimfaceClient#getElementIdsV2(java.lang.Long,
     * com.bimface.sdk.bean.request.QueryElementIdsRequest)
     * getElementIdsV2(java.lang.Long, com.bimface.sdk.bean.request.QueryElementIdsRequest)
     * }
     *
     * @param fileId     文件ID
     * @param specialty  专业
     * @param floor      楼层
     * @param categoryId 类别
     * @param family     族
     * @param familyType 族类型
     * @return 构件 ID 列表
     * @throws BimfaceException {@link BimfaceException} {@link BimfaceClient}
     */
    @Deprecated
    public List<String> getElementIdsV2(Long fileId, String specialty, String floor, String categoryId,
                                        String family, String familyType) throws BimfaceException {
        QueryElementIdsRequest queryElementIdsRequest = new QueryElementIdsRequest(specialty, floor, categoryId, family,
                familyType);
        return getElementIdsV2(fileId, queryElementIdsRequest);
    }

    /**
     * 按查询条件查询单模型构件ID 组，v2
     *
     * @param fileId 文件id
     * @param queryElementIdsRequest 元素查询条件
     * @return 构建id
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<String> getElementIdsV2(Long fileId, QueryElementIdsRequest queryElementIdsRequest) throws BimfaceException {
        return elementService.getElementIdsV2(fileId, queryElementIdsRequest);
    }

    /**
     * 获取单模型文件 id 与楼层的映射关系，v2
     *
     * @param fileIds     文件 IDs
     * @param includeArea includeArea
     * @param includeRoom includeRoom
     * @return 楼层的映射关系
     * @throws BimfaceException {@link BimfaceException} {@link BimfaceClient}
     */
    public List<Map<String, Object>> getSingleModelFileIdFloorsMapping(List<String> fileIds, Boolean includeArea, Boolean includeRoom) throws BimfaceException {
        return floorService.getSingleModelFileIdFloorsMapping(fileIds, includeArea, includeRoom);
    }

    /**
     * 获取单模型构件，v2
     *
     * @param fileId    文件ID
     * @param elementId 构件ID
     * @return 构件
     * @throws BimfaceException {@link BimfaceException} {@link BimfaceClient}
     */
    public Property getSingleModelElementV2(Long fileId, String elementId) throws BimfaceException {
        return getSingleModelElementV2(fileId, elementId, false);
    }

    /**
     * 获取单模型构件，v2
     *
     * @param fileId           文件ID
     * @param elementId        构件ID
     * @param includeOverrides 是否查询修改的属性
     * @return 构件
     * @throws BimfaceException {@link BimfaceException} {@link BimfaceClient}
     */
    public Property getSingleModelElementV2(Long fileId, String elementId, boolean includeOverrides) throws BimfaceException {
        return elementService.getSingleModelElementV2(fileId, elementId, includeOverrides);
    }

    /**
     * 根据构件 ID 批量获取单模型的单个构件，v2
     * 即将过期，推荐使用：
     * {@link com.bimface.sdk.BimfaceClient#getSingleModelElementsV2(java.lang.Long, java.util.List, java.util.List, boolean)
     * getSingleModelElementsV2(java.lang.Long, java.util.List, java.util.List, boolean)
     * }
     *
     * @param fileId     文件ID
     * @param elementIds 构件IDs
     * @return List &lt;{@link Property }&gt; 构件
     * @throws BimfaceException {@link BimfaceException} {@link BimfaceClient}
     * @deprecated
     */
    @Deprecated
    public List<Property> getSingleModelElementsV2(Long fileId, List<String> elementIds) throws BimfaceException {
        return elementService.getSingleModelElementsV2(fileId, elementIds);
    }

    /**
     * 根据构件 ID 批量获取单模型的单个构件，v2
     * 支持查询模型属性重写后构件的属性，需要设置请求参数includeOverrides的值为true
     *
     * @param fileId           文件ID
     * @param elementIds       构件IDs
     * @param filter           查询筛选条件，可选
     * @param includeOverrides 是否查询修改的属性
     * @return List &lt;{@link Property }&gt; 构件
     * @throws BimfaceException {@link BimfaceException} {@link BimfaceClient}
     */
    public List<Property> getSingleModelElementsV2(Long fileId, List<String> elementIds,
                                                   List<ElementPropertyFilterRequest.GroupAndKeysPair> filter,
                                                   boolean includeOverrides) throws BimfaceException {
        return elementService.getSingleModelElementsV2(fileId, elementIds, filter, includeOverrides);
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
        return propertyService.getSingleModelElementPropertyV2(fileId, elementIds, false);
    }

    /**
     * 获取单文件的构件属性，v2
     * 支持查询模型属性重写后多个构件的共同属性，需要设置请求参数includeOverrides的值为true
     *
     * @param fileId     文件id
     * @param elementIds 构件id
     * @param includeOverrides 是否包括覆盖的属性
     * @return {@link Property}构件属性
     * @throws BimfaceException {@link BimfaceException}
     */
    public Property getElementPropertyV2(Long fileId, List<String> elementIds, Boolean includeOverrides) throws BimfaceException {
        return propertyService.getSingleModelElementPropertyV2(fileId, elementIds, includeOverrides);
    }

    /**
     * 获取单模型的构件材质，v2
     *
     * @param fileId    文件ID
     * @param elementId 构件ID
     * @return 构件材质 {@link MaterialInfo}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<MaterialInfo> getSingleModelMaterials(Long fileId, String elementId) throws BimfaceException {
        return elementService.getSingleModelMaterials(fileId, elementId);
    }

    /**
     * 查询单模型三维视点信息，v2
     *
     * @param fileId 文件ID
     * @return List &lt;{@link View }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<View> getSingleModelViews(Long fileId) throws BimfaceException {

        return dataService.getSingleModelViews(fileId);
    }

    /**
     * Rvt单模型查询楼层对应房间列表，v2
     * 已过时：推荐使用
     * {@linkplain com.bimface.sdk.BimfaceClient#getSingleModelRooms(java.lang.Long, java.lang.String, java.lang.String,
     * com.bimface.data.enums.ToleranceType, com.bimface.data.enums.ToleranceType)
     * getSingleModelRooms(java.lang.Long, java.lang.String, java.lang.String,
     * com.bimface.data.enums.ToleranceType, com.bimface.data.enums.ToleranceType)
     * }
     *
     * @param fileId 文件id
     * @param floorId 楼层id
     * @return List &lt;{@link Room }&gt;
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public List<Room> getSingleModelRooms(Long fileId, String floorId) throws BimfaceException {
        return getSingleModelRooms(fileId, floorId, null, null, null);
    }

    /**
     * Rvt单模型查询楼层对应房间列表，v2
     * 当前支持两种方式查询房间列表：1）使用楼层ID查询属于给定楼层的房间列表；2）使用构件ID在空间中计算查询包含该构件的房间列表
     * 这两种方式只能取其一，楼层ID优先。
     *
     * @param fileId 文件id
     * @param floorId 楼层id
     * @param elementId 元素id
     * @param roomToleranceZ 房间Z方向容忍类型
     * @param roomToleranceXY 房间Y方向容忍类型
     * @return List &lt;{@link Room }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<Room> getSingleModelRooms(Long fileId, String floorId, String elementId, ToleranceType roomToleranceZ,
                                          ToleranceType roomToleranceXY) throws BimfaceException {
        return dataService.getSingleModelRooms(fileId, floorId, elementId, roomToleranceZ, roomToleranceXY);
    }

    /**
     * Rvt单模型查询楼层对应房间, v2
     *
     * @param fileId 文件id
     * @param roomId 房间id
     * @return {@link Room}
     * @throws BimfaceException {@link BimfaceException}
     */
    public Room getSingleModelRoom(Long fileId, String roomId) throws BimfaceException {
        return dataService.getSingleModelRoom(fileId, roomId);
    }

    /**
     * Rvt单模型查询楼层对应Area列表, v2
     *
     * @param fileId 文件id
     * @param floorId 楼层id
     * @return {@link Area}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<Area> getSingleModelAreas(Long fileId, String floorId) throws BimfaceException {
        return dataService.getSingleModelAreas(fileId, floorId);
    }

    /**
     * Rvt单模型查询 Area, v2
     *
     * @param fileId 文件id
     * @param areaId 区域id
     * @return {@link Area}
     * @throws BimfaceException {@link BimfaceException}
     */
    public Area getSingleModelArea(Long fileId, String areaId) throws BimfaceException {
        return dataService.getSingleModelArea(fileId, areaId);
    }

    /**
     * 查询单模型获取构件分类树，v2
     *
     * @param fileId 文件id
     * @param requestBody {@link FileTreeRequestBody} 构件分类树查询请求体
     * @return List &lt;{@link Tree.TreeNode }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<Tree.TreeNode> getSingleModelTreeV2(Long fileId, FileTreeRequestBody requestBody) throws BimfaceException {
        return dataService.getSingleModelTreeV2(fileId, requestBody);
    }

    /**
     * 查询单模型自定义构件分类树
     *
     * @param fileId 文件id
     * @param requestBody {@link FileTreeRequestBody} 构件分类树查询请求体
     * @return {@link Tree}
     * @throws BimfaceException {@link BimfaceException}
     */
    public Tree getSingleModelCustomizedTree(Long fileId, FileTreeRequestBody requestBody) throws BimfaceException {
        return dataService.getSingleModelCustomizedTree(fileId, requestBody);
    }

    /**
     * 单模型查询链接, v2
     *
     * @param fileId 文件id
     * @return List &lt;{@link Link }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<Link> getSingleModelLinks(Long fileId) throws BimfaceException {
        return dataService.getSingleModelLinks(fileId);
    }

    /**
     * 单模型查询drawingSheets信息, v2
     * 已过时：推荐使用
     * {@linkplain com.bimface.sdk.BimfaceClient#getSingleModelDrawingSheets(java.lang.Long, java.lang.String)
     * getSingleModelDrawingSheets(java.lang.Long, java.lang.String)
     * }
     * @param fileId 文件ID
     * @return List &lt;{@link DrawingSheet }&gt;图纸列表
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public List<DrawingSheet> getSingleModelDrawingSheets(Long fileId) throws BimfaceException {
        return getSingleModelDrawingSheets(fileId, null);
    }

    /**
     * 单模型查询drawingSheets信息, v2
     *
     * @param fileId    文件ID
     * @param elementId 构件ID
     * @return 图纸列表
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<DrawingSheet> getSingleModelDrawingSheets(Long fileId, String elementId) throws BimfaceException {
        return dataService.getSingleModelDrawingSheets(fileId, elementId);
    }

    /**
     * 获取模型数据包meta信息, v2
     *
     * @param fileId
     * @return 数据包meta信息
     * @throws BimfaceException {@link BimfaceException}  {@link BimfaceException}
     */
    public Object getSingleModelModelInfo(Long fileId) throws BimfaceException {
        return dataService.getSingleModelModelInfo(fileId);
    }

    /**
     * 获取构件的子构件 id，v2
     *
     * @param fileId 文件id
     * @param elementId 元素id
     * @return List &lt;{@link ElementIdWithName }&gt;
     * @throws BimfaceException {@link BimfaceException}  {@link BimfaceException}
     */
    public List<ElementIdWithName> getSingleModelChildElementIds(Long fileId, String elementId) throws BimfaceException {
        return dataService.getSingleModelChildElementIds(fileId, elementId);
    }

    /**
     * 修改（包含添加和更新）构件基本属性组以外的属性, V2
     *
     * @param fileId 文件id
     * @param elementId 构件元素id
     * @param propertyGroups 修改的属性 {@link PropertyGroup}
     * @return 修改是否成功
     * @throws BimfaceException {@link BimfaceException}
     */
    public String updateSingleModelElementProperties(Long fileId, String elementId, List<PropertyGroup> propertyGroups) throws BimfaceException {
        return dataService.updateSingleModelElementProperties(fileId, elementId, propertyGroups);
    }

    /**
     * 删除构件基本属性组以外的属性, V2
     *
     * @param fileId 文件id
     * @param elementId 构件元素id
     * @param propertyGroups 删除的属性
     * @return 删除是否成功
     * @throws BimfaceException {@link BimfaceException}
     */
    public String deleteSingleModelElementProperties(Long fileId, String elementId, List<PropertyGroup> propertyGroups) throws BimfaceException {
        return dataService.deleteSingleModelElementProperties(fileId, elementId, propertyGroups);
    }

    /**
     * 获取集成模型构件ID组, v2
     * 已过时：推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getIntegrateModelElementIds(java.lang.Long,
     * com.bimface.sdk.bean.request.QueryElementIdsRequest)
     * getIntegrateModelElementIds(java.lang.Long, com.bimface.sdk.bean.request.QueryElementIdsRequest) }
     *
     * @param integrateId 集成id
     * @param specialty 筛选条件专业
     * @param roomId 房间id
     * @param floor 楼层id
     * @param categoryId 分类id
     * @param family 筛选条件族
     * @param familyType 筛选条件族类型
     * @param systemType 筛选条件系统类型
     * @return {@link ElementsWithBoundingBox}
     * @throws BimfaceException {@link BimfaceException}
     */
    @Deprecated
    public ElementsWithBoundingBox getIntegrateModelElementIds(Long integrateId, String specialty, String roomId,
                                                               String floor, String categoryId, String family,
                                                               String familyType, String systemType) throws BimfaceException {
        QueryElementIdsRequest queryElementIdsRequest = new QueryElementIdsRequest(specialty, floor, categoryId, family,
                familyType);
        queryElementIdsRequest.setRoomId(roomId);
        queryElementIdsRequest.setSystemType(systemType);
        return getIntegrateModelElementIds(integrateId, queryElementIdsRequest);
    }

    /**
     * 获取集成模型构件ID组, v2
     *
     * @param integrateId 集成id
     * @param queryElementIdsRequest 查询条件
     * @return {@link ElementsWithBoundingBox}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ElementsWithBoundingBox getIntegrateModelElementIds(Long integrateId, QueryElementIdsRequest queryElementIdsRequest) throws BimfaceException {
        return dataService.getIntegrateModelElementIds(integrateId, queryElementIdsRequest);
    }

    /**
     * 修改集成模型指定构件的属性, V2
     *
     * @param integrateId
     * @param fileIdHash
     * @param elementId
     * @param propertyGroups
     * @return 是否修改成功
     * @throws BimfaceException {@link BimfaceException}
     */
    public String updateIntegrateModelElementProperties(Long integrateId, String fileIdHash,
                                                        String elementId, List<PropertyGroup> propertyGroups) throws BimfaceException {
        return dataService.updateIntegrateModelElementProperties(integrateId, fileIdHash, elementId, propertyGroups);
    }

    /**
     * 删除集成模型指定构件的属性, V2
     *
     * @param integrateId
     * @param fileIdHash
     * @param elementId
     * @param propertyGroups
     * @return
     * @throws BimfaceException {@link BimfaceException}
     */
    public String deleteIntegrateModelElementProperties(Long integrateId, String fileIdHash,
                                                        String elementId, List<PropertyGroup> propertyGroups) throws BimfaceException {
        return dataService.deleteIntegrateModelElementProperties(integrateId, fileIdHash, elementId, propertyGroups);
    }

    /**
     * 获取集成模型构件, v2
     *
     * @param integrateId
     * @param fileIdHash
     * @param elementId
     * @return {@link Property}
     * @throws BimfaceException {@link BimfaceException}
     */
    public Property getIntegrateModelElement(Long integrateId, String fileIdHash, String elementId) throws BimfaceException {
        return dataService.getIntegrateModelElement(integrateId, fileIdHash, elementId, false);
    }

    /**
     * 获取集成模型构件, v2
     * 支持查询模型属性重写后多个构件的共同属性，需要设置请求参数includeOverrides的值为true
     *
     * @param integrateId 集成id
     * @param fileIdHash 文件id的hash值
     * @param elementId 元素id
     * @param includeOverrides 是否包含被覆盖的构件
     * @return {@link Property}
     * @throws BimfaceException {@link BimfaceException}
     */
    public Property getIntegrateModelElement(Long integrateId, String fileIdHash, String elementId, Boolean includeOverrides) throws BimfaceException {
        return dataService.getIntegrateModelElement(integrateId, fileIdHash, elementId, includeOverrides);
    }

    /**
     * 获取集成模型构件, v2
     *
     * @param integrateId
     * @param elementId
     * @return {@link Property}
     * @throws BimfaceException {@link BimfaceException}
     */
    public Property getIntegrateModelElement(Long integrateId, String elementId) throws BimfaceException {
        return dataService.getIntegrateModelElement(integrateId, elementId);
    }

    /**
     * 获取集成模型构件材质, v2
     *
     * @param integrateId
     * @param fileIdHash
     * @param elementId
     * @return List &lt;{@link MaterialInfo }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<MaterialInfo> getIntegrateModelElementMaterials(Long integrateId, String fileIdHash, String elementId) throws BimfaceException {
        return dataService.getIntegrateModelElementMaterials(integrateId, fileIdHash, elementId);
    }

    /**
     * 获取集成模型分类树，v2
     *
     * @param integrateId
     * @param treeType         可选为：floor, specialty和customized三种类型
     * @param desiredHierarchy
     * @param requestBody
     * @return {@link Tree}
     * @throws BimfaceException {@link BimfaceException}
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
     * @return List &lt;{@link FileViews }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<FileViews> getIntegrateModelFileViews(Long integrateId, String viewType) throws BimfaceException {
        return dataService.getIntegrateModelFileViews(integrateId, viewType);
    }

    /**
     * 集成模型查询楼层对应房间列表, v2
     * 已过时：推荐使用：
     * {@linkplain com.bimface.sdk.BimfaceClient#getIntegrateModelRooms(java.lang.Long, java.lang.String, java.lang.String,
     * java.lang.String, com.bimface.data.enums.ToleranceType, com.bimface.data.enums.ToleranceType)
     * getIntegrateModelRooms(java.lang.Long, java.lang.String,
     * java.lang.String, com.bimface.data.enums.ToleranceType, com.bimface.data.enums.ToleranceType)
     * }
     *
     * @param integrateId
     * @param floorId
     * @return
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public List<Room> getIntegrateModelRooms(Long integrateId, String floorId) throws BimfaceException {
        return getIntegrateModelRooms(integrateId, floorId, null, null, null, null);
    }

    /**
     * 集成模型查询楼层对应房间列表, v2
     * 当前支持两种方式查询房间列表：1）使用楼层ID查询属于给定楼层的房间列表；2）使用构件ID在空间中计算查询包含该构件的房间列表
     * 这两种方式只能取其一，楼层ID优先。
     *
     * @param integrateId 集成id
     * @param floorId 楼层id
     * @param elementId 元素id
     * @param roomToleranceZ Z坐标轴方向对构件的筛选容忍度
     * @param roomToleranceXY XY坐标轴方向对构件的筛选容忍度
     * @return List &lt;{@link Room }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<Room> getIntegrateModelRooms(Long integrateId, String floorId, String elementId, ToleranceType roomToleranceZ,
                                             ToleranceType roomToleranceXY) throws BimfaceException {
        return dataService.getIntegrateModelRooms(integrateId, floorId, null, elementId, roomToleranceZ, roomToleranceXY);
    }

    /**
     * 集成模型查询楼层对应房间列表, v2
     * 当前支持两种方式查询房间列表：1）使用楼层ID查询属于给定楼层的房间列表；2）使用构件ID在空间中计算查询包含该构件的房间列表
     * 这两种方式只能取其一，楼层ID优先。
     *
     * @param integrateId 集成id
     * @param floorId 楼层id
     * @param fildIdHash 子文件ID
     * @param elementId 元素id
     * @param roomToleranceZ Z坐标轴方向对构件的筛选容忍度
     * @param roomToleranceXY XY坐标轴方向对构件的筛选容忍度
     * @return List &lt;{@link Room }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<Room> getIntegrateModelRooms(Long integrateId, String floorId, String fildIdHash, String elementId, ToleranceType roomToleranceZ,
                                             ToleranceType roomToleranceXY) throws BimfaceException {
        return dataService.getIntegrateModelRooms(integrateId, floorId, fildIdHash, elementId, roomToleranceZ, roomToleranceXY);
    }

    /**
     * 集成模型查询房间属性，v2
     *
     * @param integrateId 集成id
     * @param roomId 房间id
     * @return {@link Room}
     * @throws BimfaceException {@link BimfaceException}
     */
    public Room getIntegrateModelRoom(Long integrateId, String roomId) throws BimfaceException {
        return dataService.getIntegrateModelRoom(integrateId, roomId);
    }

    /**
     * 集成模型查询楼层对应Area列表, v2
     *
     * @param integrateId 集成id
     * @param floorId 房间id
     * @return List &lt;{@link Area }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<Area> getIntegrateModelAreas(Long integrateId, String floorId) throws BimfaceException {
        return dataService.getIntegrateModelAreas(integrateId, floorId);
    }

    /**
     * 集成模型查询Area, v2
     *
     * @param integrateId
     * @param areaId
     * @return {@link Area}
     * @throws BimfaceException {@link BimfaceException}
     */
    public Area getIntegrateModelArea(Long integrateId, String areaId) throws BimfaceException {
        return dataService.getIntegrateModelArea(integrateId, areaId);
    }

    /**
     * 查询指定的集成模型内参与集成的子文件信息, v2
     *
     * @param integrateId
     * @return List &lt;{@link IntegrateFileData }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<IntegrateFileData> getIntegrateFiles(Long integrateId) throws BimfaceException {
        return dataService.getIntegrateFiles(integrateId, false);
    }

    /**
     * 查询指定的集成模型内参与集成的子文件信息, v2
     * 支持将文件下转换出的图纸数量一起返回，需要设置请求参数includeDrawingSheet的值为true
     *
     * @param integrateId 集成ID
     * @param includeDrawingSheet 是否将文件下转换出的图纸数量一起返回
     * @return List &lt;{@link IntegrateFileData }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<IntegrateFileData> getIntegrateFiles(Long integrateId, Boolean includeDrawingSheet) throws BimfaceException {
        return dataService.getIntegrateFiles(integrateId, includeDrawingSheet);
    }

    /**
     * 根据集成ID,文件ID获取viewToken，v2
     *
     * @param integrateId
     * @param fileId
     * @return
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getIntegrateModelViewToken(Long integrateId, String fileId) throws BimfaceException {
        return dataService.getIntegrateModelViewToken(integrateId, fileId);
    }

    /**
     * 获取集成模型中所有的流水段分组，v2
     *
     * @param integrateId
     * @return List &lt;{@link SegmentGroupDto }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<SegmentGroupDto> getIntegrateModelSegmentGroups(Long integrateId) throws BimfaceException {
        return dataService.getIntegrateModelSegmentGroups(integrateId);
    }

    /**
     * 获取集成模型的流水段树, v2
     *
     * @param integrateId
     * @return List &lt;{@link SegmentGroupDto }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<SegmentGroupDto> getIntegrateModelSegmentTree(Long integrateId) throws BimfaceException {
        return dataService.getIntegrateModelSegmentTree(integrateId);
    }

    /**
     * 获取流水段分组中的所有流水段, v2
     *
     * @param integrateId
     * @param segmentGroupId
     * @return List &lt;{@link SegmentDto }&gt;
     * @throws BimfaceException {@link BimfaceException}
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
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<String> getIntegrateModelSegmentElementIds(Long integrateId, String segmentId) throws BimfaceException {
        return dataService.getIntegrateModelSegmentElementIds(integrateId, segmentId);
    }

    /**
     * 集成模型获取构件属性
     *
     * @param integrateId
     * @param fileIdHashWithElementIds
     * @return  {@link Property}
     * @throws BimfaceException {@link BimfaceException}
     */
    public Property getIntegrateModelCommonElementProperties(Long integrateId, List<FileIdHashWithElementIds> fileIdHashWithElementIds) throws BimfaceException {
        return dataService.getIntegrateModelCommonElementProperties(integrateId, fileIdHashWithElementIds, false);
    }

    /**
     * 集成模型获取构件属性
     * 支持查询模型属性重写后多个构件的共同属性，需要设置请求参数includeOverrides的值为true
     *
     * @param integrateId
     * @param fileIdHashWithElementIds
     * @param includeOverrides
     * @return {@link Property}
     * @throws BimfaceException {@link BimfaceException}
     */
    public Property getIntegrateModelCommonElementProperties(Long integrateId, List<FileIdHashWithElementIds> fileIdHashWithElementIds, Boolean includeOverrides) throws BimfaceException {
        return dataService.getIntegrateModelCommonElementProperties(integrateId, fileIdHashWithElementIds, includeOverrides);
    }

    /**
     * 新增双向业务挂接,可以在request body中指定新增项，也可以指定从其他集成拷贝
     *
     * @param integrateId
     * @param fromIntegrationId
     * @param fromBusinessType
     * @param fromBusinessFlag
     * @param request
     * @throws BimfaceException {@link BimfaceException}
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
     * @return List &lt;{@link ElementBusinessAssociation }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<ElementBusinessAssociation> getAssociationsByElementId(String integrateId, String elementId, String businessType,
                                                                       String businessFlag) throws BimfaceException {
        return dataService.getAssociationsByElementId(integrateId, elementId, businessType, businessFlag);
    }

    /**
     * 根据业务ID查询对应构件
     *
     * @param integrateId
     * @param businessType
     * @param businessId
     * @return List &lt;{@link BusinessElementAssociation }&gt;
     * @throws BimfaceException {@link BimfaceException}
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
     * @throws BimfaceException {@link BimfaceException}
     */
    public void deleteAssociationsByElementId(String integrateId, String elementId, String businessType,
                                              String businessFlag, List<String> businessIds) throws BimfaceException {
        dataService.deleteAssociationsByElementId(integrateId, elementId, businessType, businessFlag, businessIds);
    }

    /**
     * 删除业务ID关联的业务挂接
     *
     * @param integrateId
     * @param businessType
     * @param businessId
     * @throws BimfaceException {@link BimfaceException}
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
     * @throws BimfaceException {@link BimfaceException}
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
     * @throws BimfaceException {@link BimfaceException}
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
     * @return {@link ModelCompareChange}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ModelCompareChange getModelCompareElementChange(Long comparisonId, Long previousFileId, String previousElementId, Long followingFileId, String followingElementId) throws BimfaceException {
        return dataService.getModelCompareElementChange(comparisonId, previousFileId, previousElementId, followingFileId, followingElementId);
    }

    /**
     * 获取模型对比构件分类树, v2
     *
     * @param comparisonId
     * @return {@link Tree}
     * @throws BimfaceException {@link BimfaceException}
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
     * @return Pagination &lt;{@link ModelCompareDiff }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public Pagination<ModelCompareDiff> getModelCompareResult(Long comparisonId, String family, String elementName, Integer page,
                                                              Integer pageSize) throws BimfaceException {
        return dataService.getModelCompareResult(comparisonId, family, elementName, page, pageSize);
    }

    /**
     * 获取材质配置方案, v2
     *
     * @param id
     * @return {@link MaterialOverrideSetVO}
     * @throws BimfaceException {@link BimfaceException}
     */
    public MaterialOverrideSetVO getMaterialOverrideSet(Long id) throws BimfaceException {
        return dataService.getMaterialOverrideSet(id);
    }

    /**
     * 获取rfa文件族属性key列表, v2
     *
     * @param rfaFileId
     * @return List &lt;{@link String }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<String> getRfaFamilyPropertyNames(Long rfaFileId) throws BimfaceException {
        return dataService.getRfaFamilyPropertyNames(rfaFileId);
    }

    /**
     * rfa文件族类型列表, v2
     *
     * @param rfaFileId
     * @return List &lt;{@link RfaFamilyType }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<RfaFamilyType> getRfaFamilyTypes(Long rfaFileId) throws BimfaceException {
        return dataService.getRfaFamilyTypes(rfaFileId);
    }

    /**
     * 获取rfa文件族类型属性列表, v2
     *
     * @param rfaFileId
     * @param familyTypeGuid
     * @return {@link RfaFamilyTypeProperty}
     * @throws BimfaceException {@link BimfaceException}
     */
    public RfaFamilyTypeProperty getRfaFamilyTypeProperty(Long rfaFileId, String familyTypeGuid) throws BimfaceException {
        return dataService.getRfaFamilyTypeProperty(rfaFileId, familyTypeGuid);
    }

    /**
     * 查询符合条件的构件ID列表
     *
     * @param requestBody
     * @return List &lt;{@link SearchElementIdsResp }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<SearchElementIdsResp> getElements(String requestBody) throws BimfaceException {

        return dataService.getElements(requestBody, false);
    }

    /**
     * 查询符合条件的构件ID列表
     * 支持查询模型属性重写后符合条件的构件ID列表，需要设置请求参数includeOverrides的值为true
     *
     * @param requestBody
     * @param includeOverrides 是否查询修改的属性
     * @return List &lt;{@link SearchElementIdsResp }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<SearchElementIdsResp> getElements(String requestBody, Boolean includeOverrides) throws BimfaceException {

        return dataService.getElements(requestBody, includeOverrides);
    }

    /**
     * 查询roomId
     *
     * @param requestBody
     * @return List &lt;{@link SearchRoomIdsResp }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<SearchRoomIdsResp> getRooms(String requestBody) throws BimfaceException {
        return dataService.getRooms(requestBody);
    }

    /**
     * 查询areaId
     *
     * @param requestBody
     * @return List &lt;{@link SearchAreaIdsResp }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<SearchAreaIdsResp> getAreas(String requestBody) throws BimfaceException {
        return dataService.getAreas(requestBody);
    }

    /**
     * 查询指定模型构件属性的所有可能值
     * 仅支持查询单模型，即targetType可填：file
     *
     * @param targetIds 目标ID
     * @param targetType 目标类型
     * @param properties 需要查询的属性列表
     * @return List &lt;{@link PropertyValuesResp }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<PropertyValuesResp> getPropertyValues(List<String> targetIds, String targetType,
                                                      List<String> properties) throws BimfaceException {
        return dataService.getPropertyValues(targetIds, targetType, properties, false);
    }

    /**
     * 查询指定模型构件属性的所有可能值
     * 仅支持查询单模型，即targetType可填：file
     * 支持查询模型属性重写后构件的属性的所有可能值，需要设置请求参数includeOverrides的值为true
     *
     * @param targetIds 目标ID
     * @param targetType 目标类型
     * @param properties 需要查询的属性列表
     * @param includeOverrides 是否查询修改的属性
     * @return List &lt;{@link PropertyValuesResp }&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<PropertyValuesResp> getPropertyValues(List<String> targetIds, String targetType,
                                                      List<String> properties, Boolean includeOverrides) throws BimfaceException {
        return dataService.getPropertyValues(targetIds, targetType, properties, includeOverrides);
    }

    /**
     * 生成分页查询的ContextId
     *
     * @return 分页查询的ContextId
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getPaginationContextId() throws BimfaceException {
        return dataService.getPaginationContextId();
    }

    /**
     * 获取缩略图url
     *
     * @param fileId
     * @param size
     * @return
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getFileThumbnailUrl(Long fileId, Integer size) throws BimfaceException {
        return dataService.getFileThumbnailUrl(fileId, size);
    }

    /**
     * 获取DWG文件转换生成的pdf文件的url
     *
     * @param dwgFileId
     * @return
     * @throws BimfaceException {@link BimfaceException}
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
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getDwgPreviewImageUrl(Long dwgFileId, String layoutName) throws BimfaceException {
        return dataService.getDwgPreviewImageUrl(dwgFileId, layoutName);
    }

    /**
     * 获取文件的数据包根URL
     *
     * @param fileId
     * @return
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getFileDataBagRootUrl(Long fileId) throws BimfaceException {
        return databagService.getFileDataBagRootUrl(fileId);
    }

    /**
     * 获取集成数据包的根Url
     *
     * @param integrateId
     * @return
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getIntegrateDatabagRootUrl(Long integrateId) throws BimfaceException {
        return databagService.getIntegrateDatabagRootUrl(integrateId);
    }

    /**
     * 获取数据包下载流
     *
     * @param fileId
     * @return
     * @throws BimfaceException {@link BimfaceException}
     */
    public InputStream getFileDatabagContent(Long fileId) throws BimfaceException {
        return databagService.getFileDatabagContent(fileId);
    }

    /**
     * 获取数据包大小
     *
     * @param fileId
     * @return
     * @throws BimfaceException {@link BimfaceException}
     */
    public Long getFileDatabagSize(Long fileId) throws BimfaceException {
        return databagService.getFileDatabagSize(fileId);
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

    public BakeService getBakeService() {
        return bakeService;
    }

    public DrawingSplitService getDrawingSplitService() {
        return drawingSplitService;
    }
}