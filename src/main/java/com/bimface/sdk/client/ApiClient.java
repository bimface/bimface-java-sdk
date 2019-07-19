package com.bimface.sdk.client;

import com.bimface.api.bean.compatible.response.BatchDeleteResultBean;
import com.bimface.api.bean.compatible.response.ShareLinkBean;
import com.bimface.api.bean.request.integrate.FileIntegrateRequest;
import com.bimface.api.bean.request.integrate.IntegrateQueryRequest;
import com.bimface.api.bean.request.modelCompare.CompareRequest;
import com.bimface.api.bean.request.modelCompare.ModelCompareQueryRequest;
import com.bimface.api.bean.request.modelCompare.ModelCompareRequest;
import com.bimface.api.bean.request.translate.FileTranslateRequest;
import com.bimface.api.bean.request.translate.TranslateQueryRequest;
import com.bimface.api.bean.response.*;
import com.bimface.api.bean.response.databagDerivative.IntegrateDatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.ModelCompareDatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.TranslateDatabagDerivativeBean;
import com.bimface.exception.BimfaceException;
import com.bimface.http.BimfaceResponseChecker;
import com.bimface.page.PagedList;
import com.bimface.sdk.bean.response.AccessTokenBean;
import com.bimface.sdk.config.authorization.Credential;
import com.bimface.sdk.interfaces.ApiInterface;
import com.bimface.sdk.utils.AssertUtils;
import com.bimface.sdk.utils.StringUtils;
import com.glodon.paas.foundation.restclient.RESTClientBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class ApiClient extends AbstractClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);
    private ApiInterface apiClient;

    private static ApiClient instance;

    public static synchronized ApiClient getApiClient(String apiBaseUrl) {
        if (instance == null)
            instance = new ApiClient(apiBaseUrl);
        return instance;
    }

    private ApiClient(String apiBaseUrl) {
        RESTClientBuilder builder = new RESTClientBuilder()
                .registerJSONTypeAdapter(Date.class, new JsonSerializer<Date>() {
                    @Override
                    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive(date.getTime());
                    }
                })
                .serviceBaseUrl(apiBaseUrl);

        if (logger.isDebugEnabled()) {
            builder.enableHttpLoggingInterceptor();
        }

        this.apiClient = builder.responseChecker(new BimfaceResponseChecker())
                .build(ApiInterface.class);
    }

    public AccessTokenBean applyOAuthToken(@NotNull Credential credential) throws BimfaceException {
        String basicOAuth = generateBasicOAuth(credential);
        return executeCall(apiClient.applyOAuthToken(basicOAuth));
    }

    public String getViewToken(Long fileId, Long integrateId, Long compareId, @NotNull String accessToken) throws BimfaceException {
        if ((fileId != null && integrateId != null) ||
                (integrateId != null && compareId != null) ||
                (fileId != null && compareId != null) ||
                (fileId == null && integrateId == null && compareId == null)) {
            throw new IllegalArgumentException("one and only one argument can be not null in (fileId, integrateId, compareId)");
        }

        accessToken = validToken(accessToken);
        return executeCall(apiClient.getViewToken(fileId, integrateId, compareId, accessToken));
    }

    public FileTranslateBean translate(@NotNull FileTranslateRequest request, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.translate(request, accessToken));
    }

    public FileTranslateBean getTranslation(@NotNull Long fileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.getTranslation(fileId, accessToken));
    }

    public PagedList<FileTranslateDetailBean> getTranslations(@NotNull TranslateQueryRequest translateQueryRequest, @NotNull String accessToken) throws BimfaceException{
        accessToken = validToken(accessToken);
        return executeCall(apiClient.getTranslates(translateQueryRequest, accessToken));
    }
    public ShareLinkBean createShare(Long fileId, Long integrateId, Integer activeDurationInHours, String expireDate, Boolean needPassword, @NotNull String accessToken) throws BimfaceException {
        if ((fileId == null && integrateId == null) ||
                (fileId != null && integrateId != null)) {
            throw new IllegalArgumentException("one and only one argument can be not null in (fileId, integrateId)");
        }
        if (activeDurationInHours != null && activeDurationInHours < 0) {
            throw new IllegalArgumentException("activeDurationInHours can not be negative");
        }
        if(!StringUtils.isNullOrEmpty(expireDate) && !AssertUtils.isEffectiveDate(expireDate)){
            throw new IllegalArgumentException("expireDate is not effective data");
        }
        accessToken = validToken(accessToken);
        return executeCall(apiClient.createShare(fileId, integrateId, activeDurationInHours, expireDate, needPassword, accessToken));

    }

    public String deleteShare(Long fileId, Long integrateId, @NotNull String accessToken) throws BimfaceException {
        if ((fileId == null && integrateId == null) ||
                (fileId != null && integrateId != null)) {
            throw new IllegalArgumentException("one and only one argument can be not null in (fileId, integrateId)");
        }
        accessToken = validToken(accessToken);
        return executeCall(apiClient.deleteShare(fileId, integrateId, accessToken));
    }

    public BatchDeleteResultBean<Long> batchDeteleShare(List<Long> sourceIds, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.batchDeteleShare(sourceIds, accessToken));
    }

    public ShareLinkBean getShareLink(String token, Long fileId, Long integrateId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.getShareLink(token, fileId, integrateId, accessToken));
    }

    public PagedList<ShareLinkBean> shareList(@NotNull String accessToken, Integer pageNo, Integer pageSize) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.shareList(accessToken, pageNo, pageSize));
    }

    public TranslateDatabagDerivativeBean createTranslateOfflineDatabag(@NotNull Long fileId, String callback, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.createTranslateOfflineDatabag(fileId, callback, accessToken));
    }

    public IntegrateDatabagDerivativeBean createIntegateOfflineDatabag(@NotNull Long integrateId, String callback, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.createIntegateOfflineDatabag(integrateId, callback, accessToken));
    }

    public ModelCompareDatabagDerivativeBean createCompareOfflineDatabag(@NotNull Long compareId, String callback, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.createCompareOfflineDatabag(compareId, callback, accessToken));
    }

    public List<TranslateDatabagDerivativeBean> getTranslateOfflineDatabag(@NotNull Long fileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.getTranslateOfflineDatabag(fileId, accessToken));
    }

    public List<IntegrateDatabagDerivativeBean> getIntegateOfflineDatabag(@NotNull Long integrateId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.getIntegateOfflineDatabag(integrateId, accessToken));
    }

    public List<ModelCompareDatabagDerivativeBean> getCompareOfflineDatabag(@NotNull Long compareId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.getCompareOfflineDatabag(compareId, accessToken));
    }

    public FileIntegrateBean integrate(@NotNull FileIntegrateRequest request, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.integrate(request, accessToken));
    }

    public FileIntegrateBean getIntegrate(@NotNull Long integrateId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.getIntegrate(integrateId, accessToken));
    }

    public PagedList<FileIntegrateDetailBean> getIntegrates(@NotNull IntegrateQueryRequest integrateQueryRequest, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.getIntegrates(integrateQueryRequest, accessToken));
    }

    public void deleteIntegrate(@NotNull Long integrateId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        executeCall(apiClient.deleteIntegrate(integrateId, accessToken));
    }

    public ModelCompareBean invokeModelCompare(@NotNull ModelCompareRequest request, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.invokeModelCompare(request, accessToken));
    }

    public ModelCompareBean invokeModelComparev2(@NotNull CompareRequest request, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.invokeModelCompareV2(request, accessToken));
    }

    public ModelCompareBean getModelCompare(@NotNull Long compareId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.getModelCompareV2(compareId, accessToken));
    }

    public PagedList<ModelCompareBean> getModelCompares(@NotNull ModelCompareQueryRequest modelCompareQueryRequest, @NotNull  String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(apiClient.getModelCompares(modelCompareQueryRequest, accessToken));
    }

    public void deleteModelCompare(@NotNull Long compareId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        executeCall(apiClient.deleteModelCompareV2(compareId, accessToken));
    }

}
