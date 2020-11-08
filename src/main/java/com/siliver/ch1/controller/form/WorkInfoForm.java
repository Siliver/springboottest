package com.siliver.ch1.controller.form;

import com.siliver.ch1.controller.validate.WorkOverTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class WorkInfoForm {

    public interface Update{}

    public interface Add{}

    @Null(groups = {Update.class})
    @NotNull(groups = {Update.class})
    Long id;

    @Size(min = 3,max = 20)
    String name;

    @WorkOverTime(max = 5)
    Integer workTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }
}
