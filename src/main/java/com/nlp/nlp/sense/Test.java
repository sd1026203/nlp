package com.nlp.nlp.sense;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.nlp.nlp.DemoSentimentAnalysis;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/2 17:42
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    @org.junit.Test
    public void testFenci() throws IOException {
        PreprocessedDataSet preprocessedDataSet = ProcessDataFactory.loadPreprocessedDataSet(DemoSentimentAnalysis.class.getClassLoader().getResource("ChnSentiCorp").getPath());
        BayesModel bayesModel = new BayesModel(preprocessedDataSet);
        Map<String, Double> map = BayesClassifier.classfy(bayesModel, "都是些什么东西啊， 真的很垃圾");
        System.out.println();
    }
}
