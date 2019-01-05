package com.nlp.nlp.sense;

import com.google.common.collect.Table;
import com.google.common.util.concurrent.AtomicLongMap;

import java.util.Map;
import java.util.Set;

/**
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/3 13:56
 */
public class BayesModel {
    //类目数量
    private Integer categoryCount;
    //每个类目下文档数量
    private AtomicLongMap<String> categoryDocCountMap;
    //文档总数量
    private Long docCount;
    //特征总数量
    private Integer featureCount;
    //特征字典
    private DoubleArrayTrie dic;
    //先验概率
    private Map<String, Double> priorLogMap;
    //卡方验证之后, 词频表
    private Table<String,String,Long> selectedLetterCountTable;
    //似然概率
    private Table<String,String,Double> logLikelihoods;

    public Long getDocCount() {
        return docCount;
    }

    public void setDocCount(Long docCount) {
        this.docCount = docCount;
    }

    public DoubleArrayTrie getDic() {
        return dic;
    }

    public void setDic(DoubleArrayTrie dic) {
        this.dic = dic;
    }

    public AtomicLongMap<String> getCategoryDocCountMap() {
        return categoryDocCountMap;
    }

    public void setCategoryDocCountMap(AtomicLongMap<String> categoryDocCountMap) {
        this.categoryDocCountMap = categoryDocCountMap;
    }

    public Integer getFeatureCount() {
        return featureCount;
    }

    public void setFeatureCount(Integer featureCount) {
        this.featureCount = featureCount;
    }

    public Integer getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(Integer categoryCount) {
        this.categoryCount = categoryCount;
    }

    public Map<String, Double> getPriorLogMap() {
        return priorLogMap;
    }

    public void setPriorLogMap(Map<String, Double> priorLogMap) {
        this.priorLogMap = priorLogMap;
    }

    public Table<String, String, Long> getSelectedLetterCountTable() {
        return selectedLetterCountTable;
    }

    public void setSelectedLetterCountTable(Table<String, String, Long> selectedLetterCountTable) {
        this.selectedLetterCountTable = selectedLetterCountTable;
    }

    public Table<String, String, Double> getLogLikelihoods() {
        return logLikelihoods;
    }

    public void setLogLikelihoods(Table<String, String, Double> logLikelihoods) {
        this.logLikelihoods = logLikelihoods;
    }
}
