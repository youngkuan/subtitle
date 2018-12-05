package com.yang.runner;

import com.yang.processor.AssrtPageProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;

@Component
public class AssrtCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        /*String keyword = "蜘蛛侠";
        String initUrl = "http://assrt.net/sub/?searchword=" + keyword;
        Spider.create(new AssrtPageProcessor())
                .setScheduler(new QueueScheduler()
                        .setDuplicateRemover(new HashSetDuplicateRemover()))
                .addUrl(initUrl)
                .thread(5)
                .run();*/
    }
}
