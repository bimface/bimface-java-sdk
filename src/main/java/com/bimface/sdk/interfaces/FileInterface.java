package com.bimface.sdk.interfaces;

import com.bimface.file.bean.*;
import com.glodon.paas.foundation.restclient.RESTResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface FileInterface {
    @PUT("upload")
    Call<RESTResponse<FileBean>> uploadFileStream(@Query("name") String fileName, @Query("sourceId") String sourceId,
                                                  @Header("Content-Length") Long fileLength, @Body RequestBody file,
                                                  @Header("Authorization") String accessToken);

    @PUT("upload")
    Call<RESTResponse<FileBean>> uploadFileByUrl(@Query("name") String fileName, @Query("sourceId") String sourceId,
                                                 @Query("url") String url, @Query("etag") String etag,
                                                 @Header("Authorization") String accessToken);

    @PUT("upload")
    Call<RESTResponse<FileBean>> uploadFileFromOSS(@Query("name") String fileName, @Query("sourceId") String sourceId,
                                                   @Query("bucket") String ossBucket, @Query("objectKey") String ossObjectKey,
                                                   @Header("Authorization") String accessToken);

    @GET("upload/policy")
    Call<RESTResponse<UploadPolicyBean>> getUploadPolicy(@Query("name") String fileName, @Query("sourceId") String sourceId,
                                                         @Header("Authorization") String accessToken);

    @GET("support")
    Call<RESTResponse<SupportFileBean>> getSupportFileTypes(@Header("Authorization") String accessToken);

    @DELETE("file")
    Call<RESTResponse<Void>> deleteFile(@Query("fileId") Long fileId, @Header("Authorization") String accessToken);

    @POST("appendFiles")
    Call<RESTResponse<AppendFileBean>> createAppendFile(@Query("name") String fileName, @Query("sourceId") String sourceId,
                                                        @Query("length") Long fileLength, @Header("Authorization") String accessToken);

    @GET("appendFiles/{appendFileId}")
    Call<RESTResponse<AppendFileBean>> getAppendFile(@Path("appendFileId") Long appendFileId, @Header("Authorization") String accessToken);

    @POST("appendFiles/{appendFileId}/data")
    Call<RESTResponse<AppendFileBean>> appendUpload(@Path("appendFileId") Long appendFileId, @Query("position") Long position,
                                                    @Body RequestBody body, @Header("Authorization") String accessToken);

    @GET("metadata")
    Call<RESTResponse<FileBean>> getFileMetaData(@Query("fileId") Long fileId, @Header("Authorization") String accessToken);

    @GET("files/{fileId}")
    Call<RESTResponse<FileBean>> getFile(@Path("fileId") Long fileId, @Header("Authorization") String accessToken);

    @GET("files")
    Call<RESTResponse<List<FileBean>>> getFiles(@Query("suffix") String suffix,
                                                @Query("status") String status,
                                                @Query("startTime") String startTime,
                                                @Query("endTime") String endTime,
                                                @Query("offset") Long offset,
                                                @Query("rows") Long rows,
                                                @Header("Authorization") String accessToken);

    @GET("files/{fileId}/uploadStatus")
    Call<RESTResponse<FileUploadStatusBean>> getFileUploadStatus(@Path("fileId") Long fileId, @Header("Authorization") String accessToken);

    @GET("download/url")
    Call<RESTResponse<String>> getFileDownloadUrl(@Query("fileId") Long fileId, @Query("name") String fileName, @Header("Authorization") String accessToken);

    @POST
    Call<RESTResponse<FileBean>> uploadByPolicy(@Url String uploadUrl, @Body RequestBody requestBody);
}
