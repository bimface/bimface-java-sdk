package com.bimface.sdk.interfaces;

import com.bimface.api.bean.compatible.response.ShareLinkBean;
import com.bimface.api.bean.request.integrate.FileIntegrateRequest;
import com.bimface.api.bean.request.modelCompare.ModelCompareRequest;
import com.bimface.api.bean.request.translate.FileTranslateRequest;
import com.bimface.api.bean.response.FileIntegrateBean;
import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.api.bean.response.ModelCompareBean;
import com.bimface.api.bean.response.databagDerivative.IntegrateDatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.ModelCompareDatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.TranslateDatabagDerivativeBean;
import com.bimface.sdk.bean.response.AccessTokenBean;
import com.glodon.paas.foundation.restclient.RESTResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiInterface {
    @POST("oauth2/token")
    Call<RESTResponse<AccessTokenBean>> applyOAuthToken(@Header("Authorization") String credential);

    @GET("view/token")
    Call<RESTResponse<String>> getViewToken(@Query("fileId") Long fileId, @Query("integrateId") Long integrateId, @Query("compareId") Long compareId, @Header("Authorization") String accessToken);

    @PUT("translate")
    Call<RESTResponse<FileTranslateBean>> translate(@Body FileTranslateRequest request, @Header("Authorization") String accessToken);

    @GET("translate")
    Call<RESTResponse<FileTranslateBean>> getTranslation(@Query("fileId") Long fileId, @Header("Authorization") String accessToken);

    @POST("share")
    Call<RESTResponse<ShareLinkBean>> createShare(@Query("fileId") Long fileId, @Query("integrateId") Long integrateId,
                                                  @Query("activeHours") Integer activeDurationInHours, @Header("Authorization") String accessToken);

    @DELETE("share")
    Call<RESTResponse<String>> deleteShare(@Query("fileId") Long fileId, @Query("integrateId") Long integrateId, @Header("Authorization") String accessToken);

    @PUT("files/{fileId}/offlineDatabag")
    Call<RESTResponse<TranslateDatabagDerivativeBean>> createTranslateOfflineDatabag(@Path("fileId") Long fileId, @Query("callback") String callback,
                                                                                     @Header("Authorization") String accessToken);

    @PUT("integrations/{integrateId}/offlineDatabag")
    Call<RESTResponse<IntegrateDatabagDerivativeBean>> createIntegateOfflineDatabag(@Path("integrateId") Long integrateId, @Query("callback") String callback,
                                                                                    @Header("Authorization") String accessToken);

    @PUT("comparisions/{compareId}/offlineDatabag")
    Call<RESTResponse<ModelCompareDatabagDerivativeBean>> createCompareOfflineDatabag(@Path("compareId") Long compareId, @Query("callback") String callback,
                                                                                      @Header("Authorization") String accessToken);

    @GET("files/{fileId}/offlineDatabag")
    Call<RESTResponse<List<TranslateDatabagDerivativeBean>>> getTranslateOfflineDatabag(@Path("fileId") Long fileId, @Header("Authorization") String accessToken);

    @GET("integrations/{integrateId}/offlineDatabag")
    Call<RESTResponse<List<IntegrateDatabagDerivativeBean>>> getIntegateOfflineDatabag(@Path("integrateId") Long integrateId, @Header("Authorization") String accessToken);

    @GET("comparisions/{compareId}/offlineDatabag")
    Call<RESTResponse<List<ModelCompareDatabagDerivativeBean>>> getCompareOfflineDatabag(@Path("compareId") Long compareId, @Header("Authorization") String accessToken);

    @PUT("integrate")
    Call<RESTResponse<FileIntegrateBean>> integrate(@Body FileIntegrateRequest request, @Header("Authorization") String accessToken);

    @GET("integrate")
    Call<RESTResponse<FileIntegrateBean>> getIntegrate(@Query("integrateId") Long integrateId, @Header("Authorization") String accessToken);

    @DELETE("integrate")
    Call<RESTResponse<Void>> deleteIntegrate(@Query("integrateId") Long integrateId, @Header("Authorization") String accessToken);

    @POST("compare")
    Call<RESTResponse<ModelCompareBean>> invokeModelCompare(@Body ModelCompareRequest request, @Header("Authorization") String accessToken);

    @GET("compare")
    Call<RESTResponse<ModelCompareBean>> getModelCompare(@Query("compareId") Long compareId, @Header("Authorization") String accessToken);
}
