package com.nlp.nlp.sense;

import com.google.common.collect.Multiset;
import com.google.common.util.concurrent.AtomicLongMap;

/**
 * 统计文档的类型以及词频
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/3 15:26
 */
public class DocModel {
    private String category;
    private AtomicLongMap<String> letterCountMap;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public AtomicLongMap<String> getLetterCountMap() {
        return letterCountMap;
    }

    public void setLetterCountMap(AtomicLongMap<String> letterCountMap) {
        this.letterCountMap = letterCountMap;
    }
}
