package com.nlp.nlp.sense;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.nlp.nlp.DemoSentimentAnalysis;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.dictionary.DictionaryFactory;
import org.apdplat.word.segmentation.Word;
import org.apdplat.word.util.WordConfTools;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.hankcs.hanlp.classification.utilities.io.ConsoleLogger.logger;

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
    public void testFenci() {
        WordConfTools.set("dic.path", "classpath:dic.txt");
        DictionaryFactory.reload();//更改词典路径之后，重新加载词典
        List<Word> words = WordSegmenter.seg("小编真的是个垃圾");
        BiMap<Integer,String> weekNameMap = HashBiMap.create();
        weekNameMap.put(count.getAndIncrement(),"一");
        LOGGER.info(weekNameMap.get(1));
        LOGGER.info(""+weekNameMap.inverse().get("一"));


        if (folderPath == null) throw new IllegalArgumentException("参数 folderPath == null");
        File root = new File(folderPath);
        if (!root.exists()) throw new IllegalArgumentException(String.format("目录 %s 不存在", root.getAbsolutePath()));
        if (!root.isDirectory())
            throw new IllegalArgumentException(String.format("目录 %s 不是一个目录", root.getAbsolutePath()));
//        if (percentage > 1.0 || percentage < -1.0) throw new IllegalArgumentException("percentage 的绝对值必须介于[0, 1]之间");
//
        File[] folders = root.listFiles();
        if (folders == null) {
            LOGGER.info("空集");
            return;
        }
//        for()
        LOGGER.info(""+folders.length);
//        logger.start("模式:%s\n文本编码:%s\n根目录:%s\n加载中...\n", testingDataSet ? "测试集" : "训练集", charsetName, folderPath);


    }
}
