package com.crm.guard.filter;

import com.crm.guard.validator.base.Messages;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ValidationRequestResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter param) {
        Validation tableParamAnnotation = param.getParameterAnnotation(Validation.class);
        return tableParamAnnotation != null;
    }

    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        Validation tableParamAnnotation = param.getParameterAnnotation(Validation.class);
        if (tableParamAnnotation != null) {
            Messages messages = new Messages();
            return messages;
        }

        return WebArgumentResolver.UNRESOLVED;
    }
}
