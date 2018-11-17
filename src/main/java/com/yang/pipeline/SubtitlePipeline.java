package com.yang.pipeline;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static us.codecraft.webmagic.utils.FilePersistentBase.PATH_SEPERATOR;

public class SubtitlePipeline implements Pipeline {

    private String basePath = "D:\\webmagic\\www.zimuku.cn\\captions";

    @Override
    public void process(ResultItems resultItems, Task task) {
        String path = basePath + PATH_SEPERATOR;
        System.out.print("task.getUUID():"+task.getUUID());
        String fileName = DigestUtils.md5Hex(resultItems.getRequest().getUrl());
        Map<String, Object> fields = resultItems.getAll();
        List<String> subtitleFileUrls = (List<String>)fields.get("subtitleFileUrls");
        for(String subtitleFileUrl:subtitleFileUrls){
            try {
                URL url = new URL(subtitleFileUrl);
                File file = new File(path+fileName+".rar");
                FileUtils.copyURLToFile(url,file);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
