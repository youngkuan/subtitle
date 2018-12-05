package com.yang.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 字幕保存
 */
@Entity
public class Subtitle {

    @GeneratedValue
    @Id
    Long subtitleId;

    String filmName;

    String url;

    public Long getSubtitleId() {
        return subtitleId;
    }

    public void setSubtitleId(Long subtitleId) {
        this.subtitleId = subtitleId;
    }

    public String getFilmName() {
        return filmName;
    }
    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Subtitle{" +
                "subtitleId=" + subtitleId +
                ", fileName='" + filmName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
