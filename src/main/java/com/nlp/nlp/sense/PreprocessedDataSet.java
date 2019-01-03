package com.nlp.nlp.sense;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/3 15:42
 */
public class PreprocessedDataSet {
    private final AtomicInteger categoryMapIDGenerator = new AtomicInteger(1);
    private BiMap<Integer,String> categoryMap;
    private List<DocModel> docModels;
    private DoubleArrayTrie dic;
//    private BiMap<Integer,String> categoryMap = HashBiMap.create();
//    private List<DocModel> docModels = new ArrayList<>();
//    private DoubleArrayTrie dic = new DoubleArrayTrie();


    public AtomicInteger getCategoryMapIDGenerator() {
        return categoryMapIDGenerator;
    }

    public BiMap<Integer, String> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(BiMap<Integer, String> categoryMap) {
        this.categoryMap = categoryMap;
    }

    public List<DocModel> getDocModels() {
        return docModels;
    }

    public void setDocModels(List<DocModel> docModels) {
        this.docModels = docModels;
    }

    public DoubleArrayTrie getDic() {
        return dic;
    }

    public void setDic(DoubleArrayTrie dic) {
        this.dic = dic;
    }
}
