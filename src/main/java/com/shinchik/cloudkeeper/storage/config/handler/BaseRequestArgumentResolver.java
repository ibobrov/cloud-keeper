package com.shinchik.cloudkeeper.storage.config.handler;

import com.shinchik.cloudkeeper.security.SecurityUserDetails;
import com.shinchik.cloudkeeper.storage.model.dto.BaseReqDto;
import com.shinchik.cloudkeeper.storage.util.PathUtils;
import com.shinchik.cloudkeeper.user.model.User;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class BaseRequestArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(BaseRequest.class) != null;
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        BaseReqDto reqDto = new BaseReqDto(
                getUserId(),
                getPath(webRequest),
                getObjName(webRequest)
        );

        if (parameter.hasParameterAnnotation(Valid.class)) {
            addBindingResult(reqDto, parameter.getParameterName(), webRequest, binderFactory, mavContainer);
        }

        return reqDto;
    }


    protected static String getPath(NativeWebRequest webRequest) {
        return PathUtils.normalize(webRequest.getParameter("path"));
    }

    protected static String getObjName(NativeWebRequest webRequest) {
        return PathUtils.normalize(webRequest.getParameter("objName"));
    }

    protected static long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (authentication != null) {
            user = ((SecurityUserDetails) authentication.getPrincipal()).getUser();
        } else {
            throw new RuntimeException("Failed to recognize user from request");
        }

        return user.getId();
    }

    protected static void addBindingResult(Object target, String objectName, NativeWebRequest webRequest, WebDataBinderFactory binderFactory, ModelAndViewContainer mavContainer) throws Exception {
        WebDataBinder binder = binderFactory.createBinder(webRequest, target, objectName);

        binder.validate();
        BindingResult bindingResult = binder.getBindingResult();
        //add the binding result to the mavContainer.
        //the key has to be prefixed with BindingResult.MODEL_KEY_PREFIX
        mavContainer.addAttribute(String.format("%s%s", BindingResult.MODEL_KEY_PREFIX, bindingResult.getObjectName()), bindingResult);
    }

}
