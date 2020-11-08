package com.siliver.ch1.controller.ch2;

import com.siliver.ch1.controller.form.WorkInfoForm;
import com.siliver.ch1.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 日志格式化 参考MvcConfigurer,配置全局
 */
@Controller
@RequestMapping("/databind")
public class DataBindingController {

    private static final Logger logger= LoggerFactory.getLogger(DataBindingController.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
        binder.addValidators(new Validator() {
            @Override
            public boolean supports(Class<?> aClass) {
                return aClass== WorkInfoForm.class;
            }

            @Override
            public void validate(Object o, Errors errors) {
                WorkInfoForm form=(WorkInfoForm) o;
            }
        });
    }

    @RequestMapping("/date.json")
    public @ResponseBody User printDate(Date d){
        logger.info(d.toString());
        return new User();
    }

    @ResponseBody
    @RequestMapping("/addworkinfo.html")
    public void addWorkInfo(@Validated({WorkInfoForm.Add.class}) WorkInfoForm workInfo, BindingResult result){
        if (result.hasErrors()){
            List<ObjectError> list=result.getAllErrors();
            FieldError error=(FieldError) list.get(0);
            logger.info(error.getObjectName()+","+error.getField()+","+error.getDefaultMessage());
            return;
        }
        return;
    }
}
