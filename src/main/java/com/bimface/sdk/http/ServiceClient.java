package com.bimface.sdk.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.bimface.sdk.config.Config;
import com.bimface.sdk.constants.BimfaceConstants;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.utils.StringUtils;
import com.bimface.sdk.utils.VersionInfoUtils;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import okio.BufferedSink;

/**
 * API请求的Client
 * 
 * @author bimface, 2016-06-01.
 */
public class ServiceClient {

    private OkHttpClient okHttpClient;
    public RequestBody   emptyRequestBody = RequestBody.create(BimfaceConstants.MEDIA_TYPE_JSON, "");

    public ServiceClient() {
    }

    public ServiceClient(Config config) {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(config.getMaxRequests());
        dispatcher.setMaxRequestsPerHost(config.getMaxRequestsPerHost());
        ConnectionPool connectionPool = new ConnectionPool(config.getMaxIdleConnections(),
                                                           config.getKeepAliveDurationNs());
        okHttpClient = new OkHttpClient();
        okHttpClient.setDispatcher(dispatcher);
        okHttpClient.setConnectionPool(connectionPool);
        okHttpClient.networkInterceptors().add(new Interceptor() {

            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                com.squareup.okhttp.Response response = chain.proceed(request);
                return response;
            }
        });
        okHttpClient.setConnectTimeout(config.getConnectTimeout(), TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(config.getReadTimeout(), TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(config.getWriteTimeout(), TimeUnit.SECONDS);
    }

    public Response get(String url) throws BimfaceException {
        return get(url, new HttpHeaders());
    }

    public Response get(String url, HttpHeaders headers) throws BimfaceException {
        Request.Builder requestBuilder = new Request.Builder().get().url(url);
        return send(requestBuilder, Headers.of(headers.getHeaders()));
    }

    public Response post(String url, byte[] body, HttpHeaders headers) throws BimfaceException {
        return post(url, body, headers, BimfaceConstants.STREAM_MIME);
    }

    public Response post(String url, String body, HttpHeaders headers) throws BimfaceException {
        return post(url, StringUtils.utf8Bytes(body), headers, BimfaceConstants.STREAM_MIME);
    }

    public Response post(String url, HttpFormEncoding params, HttpHeaders headers) throws BimfaceException {
        final FormEncodingBuilder f = new FormEncodingBuilder();
        for (String key : params.getParams().keySet()) {
            f.add(key, params.getParams().get(key));
        }
        return post(url, f.build(), headers);
    }

    public Response post(String url, byte[] body, HttpHeaders headers, String contentType) throws BimfaceException {
        RequestBody rbody;
        if (body != null && body.length > 0) {
            MediaType t = MediaType.parse(contentType);
            rbody = RequestBody.create(t, body);
        } else {
            rbody = RequestBody.create(null, new byte[0]);
        }
        return post(url, rbody, headers);
    }

    public Response post(String url, byte[] body, int offset, int size, HttpHeaders headers,
                         String contentType) throws BimfaceException {
        RequestBody rbody;
        if (body != null && body.length > 0) {
            MediaType t = MediaType.parse(contentType);
            rbody = requestIO(t, body, offset, size);
        } else {
            rbody = RequestBody.create(null, new byte[0]);
        }
        return post(url, rbody, headers);
    }

    public Response post(String url, InputStream inputStream, Long contentLength,
                         HttpHeaders headers) throws BimfaceException {
        return post(url, requestIO(inputStream, contentLength), headers);
    }

    private Response post(String url, RequestBody body, HttpHeaders headers) throws BimfaceException {
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
        return send(requestBuilder, Headers.of(headers.getHeaders()));
    }

    public Response put(String url, HttpHeaders headers) throws BimfaceException {
        return put(url, emptyRequestBody, headers);
    }

    public Response put(String url, Object body, HttpHeaders headers) throws BimfaceException {
        return put(url, JSONObject.toJSONString(body), headers);
    }

    public Response put(String url, String body, HttpHeaders headers) throws BimfaceException {
        return put(url, RequestBody.create(BimfaceConstants.MEDIA_TYPE_JSON, body), headers);
    }

    public Response put(String url, InputStream inputStream, Long contentLength,
                        HttpHeaders headers) throws BimfaceException {
        return put(url, requestIO(inputStream, contentLength), headers);

    }

    private Response put(String url, RequestBody body, HttpHeaders headers) throws BimfaceException {
        Request.Builder requestBuilder = new Request.Builder().url(url).put(body);
        return send(requestBuilder, Headers.of(headers.getHeaders()));
    }

    public Response delete(String url) throws BimfaceException {
        return delete(url, new HttpHeaders());
    }

    public Response delete(String url, HttpHeaders headers) throws BimfaceException {
        Request.Builder requestBuilder = new Request.Builder().url(url).delete();
        return send(requestBuilder, Headers.of(headers.getHeaders()));
    }

    public Response send(final Request.Builder requestBuilder, Headers headers) throws BimfaceException {

        if (headers != null) {
            requestBuilder.headers(headers);
        }

        try {
            requestBuilder.header("User-Agent", VersionInfoUtils.getDefaultUserAgent());
            Response response = okHttpClient.newCall(requestBuilder.build()).execute();
            if (response.code() >= 300) {
                throw new BimfaceException(response.message(), response.code());
            }
            return response;
        } catch (IOException e) {
            throw new BimfaceException(e);
        }
    }

    private static RequestBody requestIO(final MediaType contentType, final byte[] content, final int offset,
                                         final int size) {
        if (content == null) throw new NullPointerException("content == null");

        return new RequestBody() {

            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return size;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(content, offset, size);
            }
        };
    }

    private static RequestBody requestIO(final InputStream inputStream, final Long contentLength) {
        if (inputStream == null) throw new NullPointerException("content == null");

        return new RequestBody() {

            @Override
            public MediaType contentType() {
                return MediaType.parse(BimfaceConstants.STREAM_MIME);
            }

            @Override
            public long contentLength() {
                return contentLength;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                try {
                    final byte[] buffer = new byte[BimfaceConstants.PUT_THRESHOLD];
                    int l;
                    if (contentLength < BimfaceConstants.PUT_THRESHOLD) {
                        // consume until EOF
                        while ((l = inputStream.read(buffer)) != -1) {
                            sink.outputStream().write(buffer, 0, l);
                        }
                    } else {
                        // consume no more than length
                        long remaining = contentLength;
                        while (remaining > 0) {
                            l = inputStream.read(buffer, 0, (int) Math.min(BimfaceConstants.PUT_THRESHOLD, remaining));
                            if (l == -1) {
                                break;
                            }
                            sink.outputStream().write(buffer, 0, l);
                            remaining -= l;
                        }
                    }
                } finally {
                    sink.close();
                }
            }
        };
    }
}
