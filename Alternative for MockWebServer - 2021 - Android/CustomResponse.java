package com.company;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomResponse {

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private static String TAG = "CustomResponse";

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Response response;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PRIVATE)
    private Headers headers;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private OkHttpClient okHttpClient;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String url;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private RequestBody requestBody;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int responseCode;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String responseMessage;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Request.Builder builder;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Request request;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private static boolean testMode = false;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private static Map<String, Integer> responseCodesMap = new HashMap<>();

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private static Map<String, String> responseJsonMap = new HashMap<>();

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PRIVATE)
    private boolean successful = false;

    public CustomResponse(OkHttpClient client) {
        setBuilder(new Request.Builder());
        if (client != null) setOkHttpClient(client);
    }

    public void addHeader(String name, String value) {
        if (name != null && value != null && !name.equals("") && !value.equals(""))
            getBuilder().addHeader(name, value);
    }

    public String getHeader(String name, String defValue) {
        String value = getHeaders().get(name);
        if (value != null && !value.equals("")) return value;
        else return defValue;
    }

    public CustomResponse makeRequest(String url, RequestBody body) throws Exception {
        if (!isTestMode()) {
            setUrl(url);
            if (body != null) {
                setRequestBody(body);
                getBuilder().post(getRequestBody());
            }
            getBuilder().url(getUrl());
            setRequest(getBuilder().build());
            setResponse(getOkHttpClient().newCall(getRequest()).execute());
            setHeaders(getResponse().headers());
            setResponseCode(getResponse().code());
            setResponseMessage(getResponse().body().string());
            if (getResponseCode() >= 200 && getResponseCode() <= 299) setSuccessful(true);
            else setSuccessful(false);
            return this;
        } else {
            if (url != null && !url.equals("") && getResponseCodesMap().containsKey(url) && getResponseJsonMap().containsKey(url)) {
                setResponseCode(getResponseCodesMap().get(url));
                setResponseMessage(getResponseJsonMap().get(url));
                if (getResponseCode() >= 200 && getResponseCode() <= 299) setSuccessful(true);
                else setSuccessful(false);
                return this;
            } else {
                setResponseCode(0);
                setResponseMessage("");
                setSuccessful(false);
                return this;
            }
        }
    }
}
