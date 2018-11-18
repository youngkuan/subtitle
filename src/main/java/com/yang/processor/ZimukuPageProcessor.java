package com.yang.processor;

import com.yang.pipeline.SubtitlePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;

import java.util.List;

/**
 * 字幕库网站字幕下载
 * 官网：https://www.zimuku.cn/
 * @author yangkuan
 */
public class ZimukuPageProcessor implements PageProcessor {

    private String baseUrl = "https://www.zimuku.cn";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000).setCharset("UTF-8");

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        //字幕网址链接
        List<String> subtitleUrls = page.getHtml().xpath("//div[@class='persub clearfix']/h1[@class='title']/a/@href").all();
        for (int i = 0; i < subtitleUrls.size(); i++) {
            subtitleUrls.set(i, baseUrl + subtitleUrls.get(i));
        }
        page.addTargetRequests(subtitleUrls);
        page.putField("subtitleUrls", subtitleUrls);
        //获取下一页网址个数
        List<String> nextPageUrls = page.getHtml().xpath("//a[@class='num']/@href").all();
        if(nextPageUrls!=null&&nextPageUrls.size()!=0){
            for(int i=0;i<nextPageUrls.size();i++){
                nextPageUrls.set(i, baseUrl + nextPageUrls.get(i));
            }
            page.putField("nextPageUrls",nextPageUrls);
            page.addTargetRequests(nextPageUrls);
        }
        String currentPageUrl = page.getUrl().toString();
        page.putField("currentPageUrl",currentPageUrl);
        List<String> filmNames = page.getHtml().xpath("//div[@class='md_tt prel']/h1/text()").all();
        page.putField("filmNames",filmNames);
        List<String> subtitleFileUrls = page.getHtml().xpath("//a[@id='down1']/@href").all();
        page.putField("subtitleFileUrls",subtitleFileUrls);
    }

    public static void createSpider(String keyword) {
        String initUrl = "https://www.zimuku.cn/search?q=" + keyword + "&t=onlyst";
        System.setProperty("selenuim_config", "F:\\workspace\\webmagic-selenium\\config.ini");
        Spider.create(new ZimukuPageProcessor())
                .setScheduler(new QueueScheduler()
                        .setDuplicateRemover(new HashSetDuplicateRemover()))
                .setDownloader(new SeleniumDownloader("F:\\workspace\\chromedriver.exe"))
                .addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
                .addPipeline(new ConsolePipeline())
                .addPipeline(new SubtitlePipeline("D:\\webmagic\\"))
                .addUrl(initUrl)
                .thread(5)
                .run();
    }

    public static void main(String[] args) {
        createSpider("蜘蛛侠");
    }
}
