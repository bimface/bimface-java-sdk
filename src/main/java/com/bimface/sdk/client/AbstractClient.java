package com.bimface.sdk.client;


import com.bimface.exception.BimfaceException;
import com.bimface.exception.BimfaceRuntimeException;
import com.bimface.sdk.config.authorization.Credential;
import com.bimface.sdk.utils.Base64;
import com.glodon.paas.foundation.restclient.RESTCallHelper;
import com.glodon.paas.foundation.restclient.RESTException;
import com.glodon.paas.foundation.restclient.RESTResponse;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;

import java.io.IOException;

public class AbstractClient {
    protected String validToken(@NotNull String accessToken) {
        if (StringUtils.startsWithIgnoreCase(accessToken, "bearer ")) {
            return accessToken;
        } else {
            return "Bearer " + accessToken;
        }
    }

    protected String generateBasicOAuth(@NotNull Credential credential) {
        return "Basic " + Base64.encode((credential.getAppKey() + ":" + credential.getAppSecret()).getBytes());
    }

    protected static <T> T executeCall(Call<RESTResponse<T>> call) throws BimfaceException {
        try {
            return RESTCallHelper.executeCall(call);
        } catch (RESTException e) {
            throw new BimfaceException(e.getErrorMessage(), e);
        } catch (BimfaceRuntimeException bre) {
            throw new BimfaceException("error code:" + bre.getCode() + ", message:" + bre.getMessage(), bre);
        }

    }

    protected static ResponseBody execute(Call<ResponseBody> call) throws BimfaceException {
        try {
            return call.execute().body();
        } catch (IOException e) {
            throw new BimfaceException(e.getMessage(), e);
        }
    }
}
