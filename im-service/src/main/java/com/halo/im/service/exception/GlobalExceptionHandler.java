package com.halo.im.service.exception;

import com.halo.im.common.BaseErrorCode;
import com.halo.im.common.ResponseVO;
import com.halo.im.common.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 全局异常处理
 *
 * @author halo
 * @since 2023/4/30 07:53
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 未知异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseVO<Void> unknownException(Exception e) {
        log.error("server has unknown exception: [{}]", e.getMessage(), e);

        ResponseVO<Void> resultBean = new ResponseVO<>();
        resultBean.setCode(BaseErrorCode.SYSTEM_ERROR.getCode());
        resultBean.setMsg(BaseErrorCode.SYSTEM_ERROR.getError());

        // 未知异常的话，这里写逻辑，发邮件，发短信都可以、、

        return resultBean;
    }


    /**
     * Validator 参数校验异常处理
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ResponseVO<Void> handleMethodArgumentNotValidException(ConstraintViolationException ex) {

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        ResponseVO<Void> resultBean = new ResponseVO<>();
        resultBean.setCode(BaseErrorCode.PARAMETER_ERROR.getCode());
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();
            // 读取参数字段，constraintViolation.getMessage() 读取验证注解中的 message 值
            String paramName = pathImpl.getLeafNode().getName();
            String message = "参数{".concat(paramName).concat("}").concat(constraintViolation.getMessage());
            resultBean.setMsg(message);

            return resultBean;
        }
        resultBean.setMsg(BaseErrorCode.PARAMETER_ERROR.getError() + ex.getMessage());
        return resultBean;
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ResponseVO<Void> applicationExceptionHandler(ApplicationException e) {
        // 使用公共的结果类封装返回结果
        ResponseVO<Void> resultBean = new ResponseVO<>();
        resultBean.setCode(e.getCode());
        resultBean.setMsg(e.getError());
        return resultBean;
    }

    /**
     * Validator 参数校验异常处理
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResponseVO<Void> handleException2(BindException ex) {
        FieldError err = ex.getFieldError();
        ResponseVO<Void> resultBean = new ResponseVO<>();
        if (err != null && err.getDefaultMessage() != null) {
            String message = "参数{".concat(err.getField()).concat("}").concat(err.getDefaultMessage());
            resultBean.setCode(BaseErrorCode.PARAMETER_ERROR.getCode());
            resultBean.setMsg(message);
            return resultBean;
        }
        resultBean.setCode(BaseErrorCode.PARAMETER_ERROR.getCode());
        resultBean.setMsg(BaseErrorCode.PARAMETER_ERROR.getError());
        return resultBean;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVO<Void> handleException1(MethodArgumentNotValidException ex) {
        StringBuilder errorMsg = new StringBuilder();
        BindingResult re = ex.getBindingResult();
        for (ObjectError error : re.getAllErrors()) {
            errorMsg.append(error.getDefaultMessage()).append(",");
        }
        errorMsg.delete(errorMsg.length() - 1, errorMsg.length());

        ResponseVO<Void> resultBean = new ResponseVO<>();
        resultBean.setCode(BaseErrorCode.PARAMETER_ERROR.getCode());
        resultBean.setMsg(BaseErrorCode.PARAMETER_ERROR.getError() + " : " + errorMsg);
        return resultBean;
    }

}
