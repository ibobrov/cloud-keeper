package com.shinchik.cloudkeeper.storage.config.handler;

import com.shinchik.cloudkeeper.storage.model.dto.RenameDto;
import com.shinchik.cloudkeeper.storage.util.PathUtils;
import jakarta.validation.Valid;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;


public class RenameRequestArgumentResolver extends BaseRequestArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(RenameRequest.class) != null;
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        RenameDto reqDto = new RenameDto(
                getUserId(),
                getPath(webRequest),
                getObjName(webRequest),
                getNewObjName(webRequest)
        );

        if (parameter.hasParameterAnnotation(Valid.class)) {
            addBindingResult(reqDto, parameter.getParameterName(), webRequest, binderFactory, mavContainer);
        }

        return reqDto;

    }

    private static String getNewObjName(NativeWebRequest webRequest){
        return PathUtils.normalize(webRequest.getParameter("newObjName"));
    }

}
