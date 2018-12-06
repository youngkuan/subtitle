# subtitle
subtitle downloader using webmagic 

## 实现爬取网站
1. 射手字幕网：https://assrt.net
2. 字幕库字幕网：https://www.zimuku.cn

## 根据关键词，爬取相关电影字幕文件

## 数据保存
1. MysqlPipeline保存电影名、字幕链接地址

column | note
---|---
film_name | 电影名
url | 字幕链接地址
2. SubtitlePipeline保存字幕文件到本地