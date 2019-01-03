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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/2 17:42
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
    private static AtomicInteger count = new AtomicInteger(1);
    public static final String folderPath = DemoSentimentAnalysis.class.getClassLoader().getResource("ChnSentiCorp").getPath();

    @org.junit.Test
    public void testFenci() throws IOException {
//        WordConfTools.set("dic.path", "classpath:dic.txt");
//        DictionaryFactory.reload();//更改词典路径之后，重新加载词典
//        List<Word> words = WordSegmenter.seg("小编真的是个垃圾");
//        BiMap<Integer,String> weekNameMap = HashBiMap.create();
//        weekNameMap.put(count.getAndIncrement(),"一");
//        LOGGER.info(weekNameMap.get(1));
//        LOGGER.info(""+weekNameMap.inverse().get("一"));
//        logger.start("模式:%s\n文本编码:%s\n根目录:%s\n加载中...\n", testingDataSet ? "测试集" : "训练集", charsetName, folderPath);
        PreprocessedDataSet preprocessedDataSet = ProcessDataFactory.loadPreprocessedDataSet(DemoSentimentAnalysis.class.getClassLoader().getResource("ChnSentiCorp").getPath());
        Multiset<String> letterCountNultiset = HashMultiset.create();
        letterCountNultiset.add("中国");
        letterCountNultiset.add("中国");
        letterCountNultiset.add("中国");
        System.out.println(letterCountNultiset.elementSet().size());
    }
}
