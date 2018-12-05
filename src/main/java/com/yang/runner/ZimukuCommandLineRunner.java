package com.yang.runner;

import com.yang.processor.ZimukuPageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author yangkuan
 */
@Component
public class ZimukuCommandLineRunner implements CommandLineRunner {

    @Autowired
    ZimukuPageProcessor zimukuPageProcessor;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... args) throws Exception {
        logger.error("蜘蛛侠");
        zimukuPageProcessor.createSpider("蜘蛛侠");
    }
}
