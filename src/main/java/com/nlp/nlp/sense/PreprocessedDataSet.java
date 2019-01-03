package com.nlp.nlp.sense;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/3 15:42
 */
public class PreprocessedDataSet {
    private BiMap<Integer,String> categoryMap = HashBiMap.create();
    private List<DocModel> docModels = new ArrayList<>();
    private DoubleArrayTrie dic = new DoubleArrayTrie();
}
