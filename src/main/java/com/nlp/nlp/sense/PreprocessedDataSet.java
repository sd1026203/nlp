package com.nlp.nlp.sense;

import com.google.common.util.concurrent.AtomicLongMap;

import java.util.List;

/**
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/3 15:42
 */
public class PreprocessedDataSet {
    private AtomicLongMap<String> categoryDocCountMap;
    private List<DocModel> docModels;

    public AtomicLongMap<String> getCategoryDocCountMap() {
        return categoryDocCountMap;
    }

    public void setCategoryDocCountMap(AtomicLongMap<String> categoryDocCountMap) {
        this.categoryDocCountMap = categoryDocCountMap;
    }

    public List<DocModel> getDocModels() {
        return docModels;
    }

    public void setDocModels(List<DocModel> docModels) {
        this.docModels = docModels;
    }
}
