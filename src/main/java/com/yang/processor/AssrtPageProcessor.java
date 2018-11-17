package com.yang.processor;

import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;

import java.util.ArrayList;
import java.util.List;

/**
 * 射手字幕网，字幕下载
 * 官网：https://assrt.net
 * @author yangkuan
 */
public class AssrtPageProcessor implements PageProcessor {

    private String baseUrl = "http://assrt.net";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000).setCharset("UTF-8");

    @Override
    public void process(Page page) {
        //字幕网址链接
        List<String> subtitleUrls = page.getHtml().xpath("//div[@id='resultsdiv']/div[@class='subitem']//span/a/@href").all();
        for(int i=0;i< subtitleUrls.size();i++){
            subtitleUrls.set(i,baseUrl+subtitleUrls.get(i));
        }
        page.addTargetRequests(subtitleUrls);
        page.putField("subtitleUrls",subtitleUrls);
        //获取下一页网址个数
        List<String> nextPages = page.getHtml().xpath("//div[@class='pagelinkcard']/a/text()").all();
        page.putField("nextPages",nextPages);
        if(nextPages!=null&&nextPages.size()!=0){
            Integer pageSize = Integer.valueOf(nextPages.get(nextPages.size()-1).split("/")[1]);
            //下一页网址链接
            String currentUrl = page.getUrl().toString().split("&")[0];
            List<String> nextPageUrls = new ArrayList<>();
            for(int i=1;i<=pageSize;i++){
                nextPageUrls.add(currentUrl+"&page="+i);
            }
            page.addTargetRequests(nextPageUrls);
        }
        List<String> filmNames = page.getHtml().xpath("//h1/span[@class='name_org']/text()").all();
        page.putField("filmNames",filmNames);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void createSpider(String keyword) {
        String initUrl = "http://assrt.net/sub/?searchword=" + keyword;
        System.setProperty("selenuim_config","F:\\workspace\\webmagic-selenium\\config.ini");
                Spider.create(new AssrtPageProcessor())
                .setScheduler(new QueueScheduler()
                        .setDuplicateRemover(new HashSetDuplicateRemover()))
                .setDownloader(new SeleniumDownloader("F:\\workspace\\chromedriver.exe"))
                .addUrl(initUrl)
                .thread(5)
                .run();
    }

    public static void main(String[] args) {
        createSpider("蜘蛛侠");
    }
}
