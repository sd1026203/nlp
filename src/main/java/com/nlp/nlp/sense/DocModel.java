package com.nlp.nlp.sense;

import com.google.common.collect.Multiset;
import com.google.common.util.concurrent.AtomicLongMap;

/**
 * 统计文档的类型以及词频
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/3 15:26
 */
public class DocModel {
    //所属类别
    private String category;

    //词频, 去重, value永远是1
    private AtomicLongMap<String> letterCountMap;
    //词频, 不去重, value大于等于1
    private AtomicLongMap<String> realLetterCountMap;
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

    public AtomicLongMap<String> getRealLetterCountMap() {
        return realLetterCountMap;
    }

    public void setRealLetterCountMap(AtomicLongMap<String> realLetterCountMap) {
        this.realLetterCountMap = realLetterCountMap;
    }
}
