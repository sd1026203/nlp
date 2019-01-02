package com.nlp.nlp.sense;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/2 17:42
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
    @org.junit.Test
    public void testFenci() {
        List<Word> words = WordSegmenter.seg("杨尚川是APDPlat应用级产品开发平台的作者");
        LOGGER.info("1111111111111"+words);
        List<Word> words1 = WordSegmenter.seg("杨尚川是APDPlat应用级产品开发平台的作者");
        LOGGER.info("2222222222222"+words1);
        for (Word word : words) {
            LOGGER.info(word.getText());
        }
    }
}
