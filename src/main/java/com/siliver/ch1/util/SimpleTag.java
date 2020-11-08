package com.siliver.ch1.util;

import org.beetl.core.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
public class SimpleTag extends Tag {
    private static final Logger logger= LoggerFactory.getLogger(SimpleTag.class);

    @Override
    public void render() {
        logger.info(this.toString());
        try {
            ctx.byteWriter.writeString("被删除了，付费可以看");
        } catch (IOException e) {
            //ingore
        }
    }
}
