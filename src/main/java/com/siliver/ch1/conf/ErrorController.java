package com.siliver.ch1.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理错误请求的接口
 */
@Controller
public class ErrorController extends AbstractErrorController {
    private static final String ERROR_PATH="/error";
    private final Logger logger= LoggerFactory.getLogger(ErrorController.class);

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 重写默认的构造函数，继承父类
     */
    public ErrorController() {
        super(new DefaultErrorAttributes());
    }

    /**
     * 匹配错误地址返回
     * @param request 请求对象
     * @param response 响应对象
     * @return
     */
    @RequestMapping(ERROR_PATH)
    public ModelAndView getErrorPath(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> model= Collections.unmodifiableMap(getErrorAttributes(request, ErrorAttributeOptions.defaults()));
        Throwable cause=getCause(request);
        int status=(Integer) model.get("status");
        //错误信息
        String message=(String) model.get("message");
        //友好提示
        String errorMessage=getErrorMessage(cause);
        //进行请求地址的获取
        String requestPath=(String) model.get("path");

        //后台打印日志信息，方便查询
        logger.info(status+":"+message,cause);
        logger.info("requestPath"+requestPath);

        logger.info(message,cause);
        response.setStatus(status);

        //判断请求参数是否是JSON请求
        if (!isJsonRequest(request)){
            ModelAndView view=new ModelAndView("/error.btl");
            view.addAllObjects(model);
            view.addObject("status",status);
            view.addObject("errorMessage",errorMessage);
            view.addObject("cause",cause);
            return view;
        }else {
            Map error=new HashMap();
            error.put("success",false);
            error.put("errorMessage",getErrorMessage(cause));
            error.put("cause",message);
            writeJson(response,error);
            return null;
        }
    }

    /**
     *
     * @param errorAttributes
     * @param errorViewResolvers
     */
    public ErrorController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorViewResolvers);
    }

    @Override
    public String getErrorPath() {
        return null;
    }

    /**
     * 对josn请求进行 相应的请求头的格式设置
     * @param response 相应
     * @param error
     */
    protected void writeJson(HttpServletResponse response,Map error){
        //设置 相应格式为 json
        response.setContentType("application/json;charset=utf-8");
        try{
            response.getWriter().write(objectMapper.writeValueAsString(error));
        }catch (IOException e){

        }
    }

    /**
     * 判断请求类型是否是json请求
     * @param request 请求对象
     * @return
     */
    protected boolean isJsonRequest(HttpServletRequest request){
        String requesetUri=request.getRequestURI();
        if (requesetUri.endsWith(".json")){
            return true;
        }else {
            //根据请求头判断请求
            return (request.getHeader("accept").contains("application/json") || request.getHeader("X-Requested-With")!=null && request.getHeader("X-Requested-With").contains("XMLHttpRequest"));
        }
    }
    /**
     * 防止异常暴露前端
     * @param ex
     * @return
     */
    protected String getErrorMessage(Throwable ex){
        /* 不给前端显示详细错误 */
        return "服务费错误，请联系管理员";
    }

    /**
     * 获取错误原因
     * @param request 请求对象
     * @return
     */
    protected Throwable getCause(HttpServletRequest request){
        Throwable error=(Throwable) request.getAttribute("javax.servlet.error.exception");
        if (error!=null){
            while (error instanceof ServletException && error.getCause()!=null){
                error=((ServletException)error).getCause();
            }
        }
        return error;
    }
}
