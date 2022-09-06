package org.statsserver.controllers;

import lombok.Data;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@ControllerAdvice(basePackages = "org")
public class JSONResponseWrapper implements ResponseBodyAdvice {

    // The addition of this class has been recommended to me by stumbling upon this stackoverflow post: https://stackoverflow.com/questions/40323299/how-to-wrap-json-response-from-spring-rest-repository
    // It is a security measure to prevent JSON hijacking; https://haacked.com/archive/2009/06/25/json-hijacking.aspx/

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof List) {
            return new Wrapper<>((List<Object>) body);
        }
        return body;
    }

    @Data // just the lombok annotation which provides getter and setter
    private class Wrapper<T> {
        private final List<T> response;

        public Wrapper(List<T> list) {
            this.response = list;
        }
    }
}