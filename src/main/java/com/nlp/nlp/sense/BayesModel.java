package com.nlp.nlp.sense;

import com.google.common.util.concurrent.AtomicLongMap;

import java.util.Set;

/**
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/3 13:56
 */
public class BayesModel {
    private Set<String> categorySet;
    private AtomicLongMap<String> categoryCountMap;
    private DoubleArrayTrie dic;

    public Set<String> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<String> categorySet) {
        this.categorySet = categorySet;
    }

    public DoubleArrayTrie getDic() {
        return dic;
    }

    public void setDic(DoubleArrayTrie dic) {
        this.dic = dic;
    }

    public AtomicLongMap<String> getCategoryCountMap() {
        return categoryCountMap;
    }

    public void setCategoryCountMap(AtomicLongMap<String> categoryCountMap) {
        this.categoryCountMap = categoryCountMap;
    }
}
