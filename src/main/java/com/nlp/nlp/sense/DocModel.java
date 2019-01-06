package com.nlp.nlp.sense;

import com.google.common.collect.Multiset;
import com.google.common.util.concurrent.AtomicLongMap;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 统计文档的类型以及词频
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/3 15:26
 */
public class DocModel {
    public DocModel(){}

    public DocModel(String fileContent) {
        DocModel docModel = new DocModel(null, fileContent);
        this.category = docModel.category;
        this.letterSet = docModel.letterSet;
        this.letterCountMap = docModel.letterCountMap;
    }

    public DocModel(String categoryName, String fileContent) {
        Set<String> letterSet = new HashSet<>();
        AtomicLongMap<String> letterCountMap = AtomicLongMap.create();
//        List<Word> words = WordSegmenter.seg(fileContent);
//        if(!CollectionUtils.isEmpty(words)) {
//            for(Word word : words) {
//                letterSet.add(word.getText());
//                letterCountMap.incrementAndGet(word.getText());
//            }
//        }
        String[] words = ProcessDataFactory.hanLPTokenizer.segment(fileContent);
        if(words != null && words.length > 0) {
            for(String word : words) {
                letterSet.add(word);
                letterCountMap.incrementAndGet(word);

            }
        }
        this.category = categoryName;
        this.letterSet = letterSet;
        this.letterCountMap = letterCountMap;
    }

    //所属类别
    private String category;

    //词频, 去重, value永远是1
    private Set<String> letterSet;
    //词频, 不去重, value大于等于1
    private AtomicLongMap<String> letterCountMap;
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<String> getLetterSet() {
        return letterSet;
    }

    public void setLetterSet(Set<String> letterSet) {
        this.letterSet = letterSet;
    }

    public AtomicLongMap<String> getLetterCountMap() {
        return letterCountMap;
    }

    public void setLetterCountMap(AtomicLongMap<String> letterCountMap) {
        this.letterCountMap = letterCountMap;
    }
}
