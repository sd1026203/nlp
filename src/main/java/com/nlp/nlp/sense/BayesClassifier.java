package com.nlp.nlp.sense;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.common.util.concurrent.AtomicLongMap;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/4 10:09
 */
public class BayesClassifier {
    private  BayesModel bayesModel;

    public BayesModel generateBayesModel(PreprocessedDataSet preprocessedDataSet) {
        BayesModel bayesModel = new BayesModel();
//        bayesModel.setCategorySet(preprocessedDataSet.getCategoryDocCountMap());
        List<DocModel> docModelList = preprocessedDataSet.getDocModels();
        Table<String,String,Long> letterCountTable = HashBasedTable.create();
        if (!CollectionUtils.isEmpty(docModelList)) {
            for (DocModel docModel : docModelList) {
                String category = docModel.getCategory();
                Set<String> letterSet =  docModel.getLetterSet();
                if(!CollectionUtils.isEmpty(letterSet)) {
                    for (String letter : letterSet) {
//                        String word = letterCountEntrySet.getKey();
//                        Long wordCount = letterCountEntrySet.getValue();
                        Long totalCount = letterCountTable.get(letter, category);
                        if (totalCount == null) {
                            letterCountTable.put(letter, category, 1L);
                        } else {
                            letterCountTable.put(letter, category, 1L + totalCount);
                        }
                    }
                }
            }
        }



        Map<String, Double> selectedFeatures = chi_square(preprocessedDataSet.getCategoryDocCountMap(), letterCountTable);

        Set<String> selectedWords = selectedFeatures.keySet();
        Table<String,String,Long> selectedLetterCountTable = HashBasedTable.create();
        Map<String, Map<String, Long>> letterCountTableRowMap = letterCountTable.rowMap();
        for (String selectedWord : selectedWords) {
            Map<String, Long> map = letterCountTableRowMap.get(selectedWord);
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                selectedLetterCountTable.put(selectedWord, entry.getKey(), entry.getValue());
            }
        }
        bayesModel.setSelectedLetterCountTable(selectedLetterCountTable);
        DoubleArrayTrie dic = new DoubleArrayTrie();
        dic.build(new ArrayList<>(selectedWords));
        bayesModel.setDic(dic);
        bayesModel.setFeatureCount(selectedFeatures.size());
        bayesModel.setCategoryCount(preprocessedDataSet.getCategoryDocCountMap().size());
        bayesModel.setCategoryDocCountMap(preprocessedDataSet.getCategoryDocCountMap());
        bayesModel.setDocCount(preprocessedDataSet.getCategoryDocCountMap().asMap().values().stream().mapToLong(value -> value).sum());
        Set<String> categorySet = preprocessedDataSet.getCategoryDocCountMap().asMap().keySet();
        Map<String, Double> priorLogMap = new HashMap<>();
        for (String category : categorySet) {
            Long value = bayesModel.getCategoryDocCountMap().get(category);
            priorLogMap.put(category, Math.log((double) value / bayesModel.getDocCount()));
        }
        bayesModel.setPriorLogMap(priorLogMap);
        //拉普拉斯平滑处理（又称加一平滑）时需要估计每个类目下的实例
        Map<String, Long> featureOccurrencesInCategory = new HashMap<>();
        Map<String, Map<String, Long>> map = selectedLetterCountTable.columnMap();
        for (Map.Entry<String, Map<String, Long>> entry : map.entrySet()) {
            featureOccurrencesInCategory.put(entry.getKey(), entry.getValue().values().stream().mapToLong(value -> value).sum());
        }

        Table<String,String,Double> logLikelihoods = HashBasedTable.create();
        for (String category : priorLogMap.keySet()) {
            Map<String, Map<String, Long>> rowMap = selectedLetterCountTable.rowMap();
            for (Map.Entry<String, Map<String, Long>> entry : rowMap.entrySet()) {
                String word = entry.getKey();
                Long count = entry.getValue().get(category) == null ? 0L : entry.getValue().get(category);
                double logLikelihood = Math.log((count + 1.0) / (featureOccurrencesInCategory.get(category) + bayesModel.getFeatureCount()));
                logLikelihoods.put(word, category, logLikelihood);
            }
        }
        bayesModel.setLogLikelihoods(logLikelihoods);
        return bayesModel;
    }


    public Map<String, Double> chi_square(AtomicLongMap<String> categoryCountMap, Table<String,String,Long> letterCountTable) {
        Map<String, Double> selectedFeatures = new HashMap<>();
        Map<String, Map<String, Long>> letterCountTableRowMap = letterCountTable.rowMap();
        Long totalDocCount = categoryCountMap.asMap().values().stream().reduce(0L, Long::sum);
        if (!CollectionUtils.isEmpty(letterCountTableRowMap)) {
            for (Map.Entry<String, Map<String, Long>> entry : letterCountTableRowMap.entrySet()) {
                String word = entry.getKey();
                Map<String, Long> map = entry.getValue();
                //计算 N1. (含有该特征的文档数量)
                Long N1dot = map.values().stream().reduce(0L, Long::sum);
                //还有 N0. (不含该特征的文档数量)
                Long N0dot = totalDocCount - N1dot;
                for (Map.Entry<String, Long> longEntry : map.entrySet()) {
                    String category = longEntry.getKey();
                    //N11 是含有该特征并属于该类目的文档数量
                    Long N11 = longEntry.getValue();
                    //N10 是含有该特征却不属于该类目的文档数量
                    Long N01 = categoryCountMap.get(category) - N11;
                    //N00 是不含该特征也不属于该类目的文档数量
                    Long N00 = N0dot - N01;
                    //N10 是含有该特征却不属于该类目的文档数量
                    Long N10 = N1dot - N11;
                    long l = (N11 + N01) * (N11 + N10) * (N10 + N00) * (N01 + N00);
                    double l1 = Math.pow(N11 * N00 - N10 * N01, 2);
                    long l3 = (N1dot + N0dot);
                    double chisquareScore = l3 * l1 / l;

                    if (chisquareScore > 10.83) {
                        selectedFeatures.put(word, chisquareScore);
                        break;
                    }
                }
            }
        }
        return selectedFeatures;
    }

    public BayesModel getBayesModel() {
        return bayesModel;
    }

    public void setBayesModel(BayesModel bayesModel) {
        this.bayesModel = bayesModel;
    }
}
