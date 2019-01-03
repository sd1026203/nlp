package com.nlp.nlp.sense;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * 统计文档的类型以及词频
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/3 15:26
 */
public class DocModel {
    private String category;

    public void setLetterCountNultiset(Multiset<String> letterCountNultiset) {
        this.letterCountNultiset = letterCountNultiset;
    }

    private Multiset<String> letterCountNultiset;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Multiset<String> getLetterCountNultiset() {
        return letterCountNultiset;
    }
}
