package com.bimface.sdk.service;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.squareup.okhttp.Response;

/**
 * 查询构件ID服务
 *
 * @author bimface, 2016-11-02.
 */
public class ElementService extends AbstractAccessTokenService {

    private final String GET_ELEMENT_URL = getApiHost() + "/data/element/query?transferId=%s";

    public ElementService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    /**
     * 通过条件查询构件ID组
     * 
     * @param transferId
     * @param categoryId
     * @param family
     * @param familyType
     * @param start
     * @param end
     * @return
     * @throws BimfaceException
     */
    public List<String> getElements(String transferId, String categoryId, String family, String familyType,
                                    Integer start, Integer end) throws BimfaceException {
        // 参数校验
        AssertUtils.assertStringNotNullOrEmpty(transferId, "transferId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());

        StringBuilder sb = new StringBuilder().append(String.format(GET_ELEMENT_URL, transferId));
        if (categoryId != null) {
            sb.append("&categoryId=").append(categoryId);
        }
        if (family != null) {
            sb.append("&family=").append(family);
        }
        if (familyType != null) {
            sb.append("&familyType=").append(familyType);
        }
        if (start != null) {
            sb.append("&start=").append(start);
        }
        if (end != null) {
            sb.append("&end=").append(end);
        }
        Response response = getServiceClient().get(sb.toString(), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<List<String>>>() {});
    }
}
