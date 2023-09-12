package com.example.demo.advice;

import com.example.demo.Enum.ResultCode;
import com.example.demo.annotation.NotControllerResponseAdvice;
import com.example.demo.exception.APIException;
import com.example.demo.response.ResponseResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = {"com.example.demo.controller"})
public class ControllerResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // response是ResponseResult类型，或者注释了NotControllerResponseAdvice都不进行包装
        return !(returnType.getParameterType().isAssignableFrom(ResponseResult.class) ||
                returnType.hasMethodAnnotation(NotControllerResponseAdvice.class));
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        // String类型不能直接包装
        if (String.class.equals(returnType.getGenericParameterType())) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在ResponseResult里后转换为json串进行返回
                return objectMapper.writeValueAsString(new ResponseResult(body));
            } catch (JsonProcessingException e) {
                throw new APIException(ResultCode.RESULT_ERROR, e.getMessage());
            }
        }
        // 否则直接包装成ResponseResult返回
        return new ResponseResult(body);
    }
}
