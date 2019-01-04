package com.nlp.nlp.sense;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.common.util.concurrent.AtomicLongMap;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
                AtomicLongMap<String> letterCountMap =  docModel.getLetterCountMap();
                if(letterCountMap != null && letterCountMap.size() > 0) {
                    for (Map.Entry<String, Long> letterCountEntrySet : letterCountMap.asMap().entrySet()) {
                        String word = letterCountEntrySet.getKey();
                        Long wordCount = letterCountEntrySet.getValue();
                        Long totalCount = letterCountTable.get(word, category);
                        if (totalCount == null) {
                            letterCountTable.put(word, category, wordCount);
                        } else {
                            letterCountTable.put(word, category, wordCount + totalCount);
                        }
                    }
                }
            }
        }



        chi_square(preprocessedDataSet.getCategoryDocCountMap(), letterCountTable);
        System.out.println(letterCountTable);
        return bayesModel;
    }


    public void chi_square(AtomicLongMap<String> categoryCountMap, Table<String,String,Long> letterCountTable) {
        Map<String, Map<String, Long>> letterCountTableRowMap = letterCountTable.rowMap();
        String[] categoryArray = categoryCountMap.asMap().keySet().toArray(new String[categoryCountMap.size()]);
        Long totalDocCount = categoryCountMap.asMap().values().stream().reduce(0L, Long::sum);
        Integer cou = 0 ;
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
                    if(N11 == 44 && N10 == 38) {
                        double chisquareScore1 = (N1dot + N0dot) * Math.pow(N11 * N00 - N10 * N01, 2) / l;
                        System.out.println(letterCountTableRowMap.size() + "  " + word + "  " + category + "  " + chisquareScore + "  " + N1dot + " " + N0dot + " " + N11 + " " + N01 + " " + N00 + " " + N10);
                    }

                    if (chisquareScore > 10.83) {
                        System.out.println(word + "  " + category + "  " + chisquareScore);
                        cou++;
                    }
                }
            }
        }
        System.out.println("cou:" + cou);
    }

    public BayesModel getBayesModel() {
        return bayesModel;
    }

    public void setBayesModel(BayesModel bayesModel) {
        this.bayesModel = bayesModel;
    }
}
