package com.bimface.sdk.service;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.request.FileTransferRequest;
import com.bimface.sdk.bean.response.TransferBean;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.squareup.okhttp.Response;

/**
 * 文件转换
 * 
 * @author bimface, 2016-06-01.
 */
public class TransferService extends AbstractAccessTokenService {

    private final String TRANSFER_URL     = getApiHost() + "/transfer";
    private final String GET_TRANSFER_URL = getApiHost() + "/transfer?viewId=%s";

    public TransferService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    public TransferBean transfer(FileTransferRequest fileTransferRequest) throws BimfaceException {

        // 参数校验
        check(fileTransferRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().put(TRANSFER_URL, fileTransferRequest, headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<TransferBean>>() {});
    }

    public TransferBean getTransfer(String viewId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertStringNotNullOrEmpty(viewId, "viewId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(GET_TRANSFER_URL, viewId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<TransferBean>>() {});
    }

    private void check(FileTransferRequest fileTransferRequest) {
        AssertUtils.assertParameterNotNull(fileTransferRequest, "fileTransferRequest");
        if (fileTransferRequest.getFileId() == null && fileTransferRequest.getFileId() < 0) {
            throw new IllegalArgumentException("ParameterLongIsEmpty FileId");
        }
    }
}
