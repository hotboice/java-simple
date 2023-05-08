package org.example.system.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.system.common.ResultData;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@ControllerAdvice
public class BaseException {
    private static Map<Class<?>, Function<Exception, ResultData>> exceptionMap;

    public BaseException() {
        exceptionMap = new HashMap<>() {
            private static final long serialVersionUID = 5185621901635757318L;
            {
                put(BusinessException.class, (e) -> {
                    BusinessException businessException = (BusinessException) e;
                    return ResultData.optFail(businessException.getMessage());
                });
                put(UnauthorizedException.class, (e) -> {
                    UnauthorizedException unauthorizedException = (UnauthorizedException) e;
                    return ResultData.optFail(HttpStatus.FORBIDDEN.value(), unauthorizedException.getMessage());
                });
                put(HttpRequestMethodNotSupportedException.class, (e) -> {
                    HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException = (HttpRequestMethodNotSupportedException) e;
                    return ResultData.optFail(httpRequestMethodNotSupportedException.getMessage());
                });
                put(MethodArgumentNotValidException.class, (e) -> {
                    MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
                    BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
                    FieldError fieldError = bindingResult.getFieldErrors().get(0);
                    return ResultData.optFail(fieldError.getDefaultMessage());
                });
                put(MissingServletRequestParameterException.class, (e) -> {
                    MissingServletRequestParameterException missingServletRequestParameterException = (MissingServletRequestParameterException) e;
                    return ResultData.optFail(missingServletRequestParameterException.getMessage());
                });
                put(NoHandlerFoundException.class, (e) -> ResultData.optFail(HttpStatus.NOT_FOUND.getReasonPhrase()));
                put(HttpMediaTypeNotSupportedException.class, (e) -> {
                    HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException = (HttpMediaTypeNotSupportedException) e;
                    return ResultData.optFail(httpMediaTypeNotSupportedException.getMessage());
                });
                put(HttpMessageNotReadableException.class, (e) -> ResultData.optFail("Required request body is missing"));
            }
        };
    }

    @ResponseBody
    @ExceptionHandler
    public ResultData adviceException(Exception e) {
        return exceptionMap.entrySet().stream()
            .filter(t -> Optional.of(e).filter(t.getKey()::isInstance).map(t.getKey()::cast).isPresent()).findFirst()
            .orElseGet(() -> new AbstractMap.SimpleEntry<>(Exception.class, (ee) -> {
                log.error("BaseException: ", e);
                return ResultData.optFail("error");
            })).getValue().apply(e);
    }
}
