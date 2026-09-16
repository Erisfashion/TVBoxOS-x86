package com.github.catvod.crawler;

import com.github.catvod.crawler.python.IPyLoader;

public class pyLoader implements IPyLoader {
    @Override
    public Spider getSpider(String key, String api, String ext) {
        return new SpiderNull();
    }
}
