package com.siliver.ch1.conf;

import com.siliver.ch1.util.SimpleFunction;
import com.siliver.ch1.util.SimpleTag;
import org.beetl.core.GroupTemplate;
import org.beetl.core.tag.Tag;
import org.beetl.core.tag.TagFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeetlExtConfig {
    private static final Logger logger= LoggerFactory.getLogger(BeetlExtConfig.class);

    @Autowired
    GroupTemplate groupTemplate;

    @Autowired
    ApplicationContext applicationContext;

    @PostConstruct
    public void config(){
        Map<String, Object> shared = new HashMap<String, Object>();
        shared.put("jsVersion", System.currentTimeMillis());
        groupTemplate.registerFunction("hi",applicationContext.getBean(SimpleFunction.class));
        //注册上下文标签
        groupTemplate.registerTagFactory("myTag", new TagFactory(){

            public Tag createTag() {
                return  applicationContext.getBean(SimpleTag.class);
            }

        });
        URL url = BeetlExtConfig.class.getResource("/templates/functions");
        logger.info("=================="+url);
    }

    /**
     * 另外一种配置方法，实现beetlTemplateCustomize
     * @return
     */
    /*@Bean
    public BeetlTemplateCustomize beetlTemplateCustomize(){
        return new BeetlTemplateCustomize() {
            @Override
            public void customize(GroupTemplate groupTemplate) {

            }
        };
    }*/
}
