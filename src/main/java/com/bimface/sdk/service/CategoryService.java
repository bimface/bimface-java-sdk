package com.bimface.sdk.service;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.response.CategoryBean;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.squareup.okhttp.Response;

/**
 * 构件分类树服务
 *
 * @author bimface, 2016-11-01.
 */
public class CategoryService extends AbstractAccessTokenService {

    private final String GET_CATEGORY_URL = getApiHost() + "/data/element/categoryTree?transferId=%s";

    public CategoryService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    /**
     * 获取构件分类树
     * 
     * @param transferId 文件转换id
     * @return {@link CategoryBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<CategoryBean> getCategory(String transferId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertStringNotNullOrEmpty(transferId, "transferId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(GET_CATEGORY_URL, transferId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<List<CategoryBean>>>() {});
    }

}
