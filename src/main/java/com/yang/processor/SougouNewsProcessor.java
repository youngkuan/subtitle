package com.yang.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 搜狗搜索，腾讯新闻
 */

@Component
public class SougouNewsProcessor implements PageProcessor {


    public static final String first_url = "https://news\\.sogou\\.com/news\\?query=site:qq\\.com [\\S]+";

    private Site site = Site.me().setRetryTimes(5).setSleepTime(6000).setCharset("UTF-8");

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        logger.debug("url: " + page.getUrl().toString());
        boolean flag = page.getUrl().regex(first_url).match();
        logger.error("String.valueOf(flag):"+String.valueOf(flag));

        if (page.getUrl().regex(first_url).match()) {
            logger.debug("firstUrl: " + page.getUrl().toString());
            List<String> urlList = page.getHtml().xpath("//*[@id='main']//*[@class='vrTitle']")
                    .links().all();
            logger.debug("URL list size : " + urlList.size());
            page.addTargetRequests(urlList);
            List<String> nextURL_a_List = page.getHtml().xpath("//*[@id='pagebar_container']/a/@id").all();
            if (nextURL_a_List.get(nextURL_a_List.size() - 1).equals("sogou_next")) {
                List<String> nextURLList = page.getHtml().xpath("//*[@id='pagebar_container']/a/@href").all();
                String nextURL = "https://news.sogou.com/news" + nextURLList.get(nextURLList.size() - 1);
                page.addTargetRequest(nextURL);
                logger.debug("next url: " + nextURL);
            }
        } else {
            logger.error("in else");
            String title = page.getHtml()
                    .xpath("//*[@id='Main-Article-QQ']/div/div[1]/div[1]/div[1]/h1/text()").toString();
            String time = page.getHtml()
                    .xpath("//*[@id='Main-Article-QQ']/div/div[1]/div[1]/div[1]/div/div[1]/span[3]/text()").toString();
            String content = page.getHtml()
                    .xpath("//*[@id='Cnt-Main-Article-QQ']/allText()").toString();
            if (title != null && content != null) {
                logger.debug("add a record");
                logger.debug(title);
                page.putField("title", title);
                page.putField("time", time);
                logger.debug("content: " + content);
                page.putField("content", content);
                page.putField("URL", page.getUrl().toString());
            }

        }


    }

    public void creatSpider() {
        System.setProperty("selenuim_config", "F:\\workspace\\webmagic-selenium\\config.ini");
        String keyword = "中美贸易战";
        /*try {
            keyword = URLEncoder.encode(keyword, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        String searchURL = "https://news.sogou.com/news?query=site:qq.com " + keyword;
        SeleniumDownloader seleniumDownloader = new SeleniumDownloader("F:\\workspace\\chromedriver.exe");
        seleniumDownloader.setSleepTime(10000);
        Spider.create(new SougouNewsProcessor())
                .setDownloader(seleniumDownloader)
                .addUrl(searchURL)
                .thread(5)
                .run();
    }

    public static void main(String[] args) {
        new SougouNewsProcessor().creatSpider();
    }
}
