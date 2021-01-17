package rest.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import rest.helpers.Log;

import java.lang.reflect.Type;

@ControllerAdvice
public class RequestAdvice extends RequestBodyAdviceAdapter {
    @Autowired
    Log log;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        log.writeLog("Request: ", new StringBuilder().append(body).toString(), true);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
