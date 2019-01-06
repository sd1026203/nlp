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



    public static Map<String, Double> classfy(BayesModel bayesModel, String content) {
        DocModel docModel = new DocModel(content);
        Map<String, Double> priorLogMap = bayesModel.getPriorLogMap();
        Map<String, Double> resultMap = new HashMap<>();
        for (Map.Entry<String, Double> entry : priorLogMap.entrySet())
        {
            String category = entry.getKey();
            Double priorLog = entry.getValue(); //用类目的对数似然初始化概率

            //对文档中的每个特征
            for (Map.Entry<String, Long> entry2 : docModel.getLetterCountMap().asMap().entrySet())
            {
                String feature = entry2.getKey();

                if (!bayesModel.getLogLikelihoods().rowMap().containsKey(feature))
                {
                    continue; //如果在模型中找不到就跳过了
                }

                Long occurrences = entry2.getValue(); //获取其在文档中的频次

                priorLog += occurrences * bayesModel.getLogLikelihoods().get(feature, category); //将对数似然乘上频次
            }
            resultMap.put(category, priorLog);
        }
        return normalizeExp(resultMap);
    }

    private static Map<String, Double> normalizeExp(Map<String, Double> resultMap)
    {
        double max = Double.NEGATIVE_INFINITY;
        for (double value : resultMap.values())
        {
            max = Math.max(max, value);
        }

        double sum = 0.0;
        //通过减去最大值防止浮点数溢出
        for (Map.Entry<String, Double> entry : resultMap.entrySet())
        {
            String key = entry.getKey();
            Double value = entry.getValue();
            value = Math.exp(value - max);
            resultMap.put(key, value);
            sum += value;
        }

        if (sum != 0.0)
        {
            for (Map.Entry<String, Double> entry : resultMap.entrySet())
            {
                String key = entry.getKey();
                Double value = entry.getValue();
                value /= sum;
                resultMap.put(key, value);
            }
        }
        return resultMap;
    }
}
