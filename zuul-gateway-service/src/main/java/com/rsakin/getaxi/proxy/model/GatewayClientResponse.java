package com.rsakin.getaxi.proxy.model;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;

public class GatewayClientResponse implements ClientHttpResponse {

    private HttpStatus status;
    private String message;

    public GatewayClientResponse(HttpStatus gatewayTimeout, String defaultMessage) {
        this.message = defaultMessage;
        this.status = gatewayTimeout;
    }

    @Override
    public HttpStatus getStatusCode() throws IOException {
        return status;
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return status.value();
    }

    @Override
    public String getStatusText() throws IOException {
        return message;
    }

    @Override
    public void close() {
    }

    @Override
    public InputStream getBody() throws IOException {
        return null;
    }

    @Override
    public HttpHeaders getHeaders() {
        return null;
    }
}
