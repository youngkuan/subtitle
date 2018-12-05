package com.yang.pipeline;

import com.yang.dao.SubtitleRepository;
import com.yang.domain.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

public class MysqlPipeline implements Pipeline {


    SubtitleRepository subtitleRepository;

    public MysqlPipeline(SubtitleRepository subtitleRepository) {
        this.subtitleRepository = subtitleRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> fields = resultItems.getAll();
        List<String> subtitleFileUrls = (List<String>) fields.get("subtitleFileUrls");
        List<String> filmNames = (List<String>) fields.get("filmNames");
        if (subtitleFileUrls.size() != 0) {
            Subtitle subtitle = new Subtitle();
            subtitle.setUrl(subtitleFileUrls.get(0));
//            String filmName = filmNames.get(0).split("/")[0];
            subtitle.setFilmName(filmNames.get(0));
            subtitleRepository.save(subtitle);
        }
    }
}
