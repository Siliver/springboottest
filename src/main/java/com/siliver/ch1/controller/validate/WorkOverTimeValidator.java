package com.siliver.ch1.controller.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 进行自定义注解校验
 */
public class WorkOverTimeValidator implements ConstraintValidator<WorkOverTime,Integer> {
    WorkOverTime work;
    int max;

    @Override
    public void initialize(WorkOverTime constraintAnnotation) {
        this.work=constraintAnnotation;
        max=work.max();
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if (integer==null){
            return true;
        }
        return integer<max;
    }
}
