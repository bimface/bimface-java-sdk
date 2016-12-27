package com.bimface.sdk.service;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.response.ElementsBean;
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

    private final String GET_ELEMENT_URL           = getApiHost() + "/data/element/id?fileId=%s";
    private final String GET_INTEGRATE_ELEMENT_URL = getApiHost() + "/data/integration/element?integrateId=%s";

    public ElementService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    /**
     * 按查询条件查询构件ID组
     * 
     * @param fileId 文件id
     * @param categoryId 分类id
     * @param family 族
     * @param familyType 族类型
     * @return List&lt;{@link String}&gt;
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<String> getElements(Long fileId, String categoryId, String family,
                                    String familyType) throws BimfaceException {
        // 参数校验
        AssertUtils.assertParameterNotNull(fileId, "fileId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());

        StringBuilder sb = new StringBuilder().append(String.format(GET_ELEMENT_URL, fileId));
        if (categoryId != null) {
            sb.append("&categoryId=").append(categoryId);
        }
        if (family != null) {
            sb.append("&family=").append(family);
        }
        if (familyType != null) {
            sb.append("&familyType=").append(familyType);
        }
        Response response = getServiceClient().get(sb.toString(), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<List<String>>>() {});
    }

    /**
     * 按查询条件获取集成模型的构件列表
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
        // 参数校验
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());

        StringBuilder sb = new StringBuilder().append(String.format(GET_INTEGRATE_ELEMENT_URL, integrateId));
        if (categoryId != null) {
            sb.append("&categoryId=").append(categoryId);
        }
        if (family != null) {
            sb.append("&family=").append(family);
        }
        if (familyType != null) {
            sb.append("&familyType=").append(familyType);
        }
        if (floor != null) {
            sb.append("&floor=").append(floor);
        }
        if (specialty != null) {
            sb.append("&specialty=").append(specialty);
        }
        Response response = getServiceClient().get(sb.toString(), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<ElementsBean>>() {});
    }

}
