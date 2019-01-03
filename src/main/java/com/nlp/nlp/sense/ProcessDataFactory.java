package com.nlp.nlp.sense;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/3 17:53
 */
public class ProcessDataFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataFactory.class);
    public static PreprocessedDataSet loadPreprocessedDataSet(String folderPath) throws IOException {
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
            return null;
        }
        PreprocessedDataSet preprocessedDataSet = new PreprocessedDataSet();
        List<DocModel> docModels = new ArrayList<>();
        BiMap<Integer,String> categoryMap = HashBiMap.create();
        DoubleArrayTrie dic = new DoubleArrayTrie();
        AtomicInteger categoryMapIDGenerator = preprocessedDataSet.getCategoryMapIDGenerator();
        List<String> list = new ArrayList<>();
        for (File folder : folders) {
            if (folder.isFile()) continue;
            File[] files = folder.listFiles();
            if (files == null) {
                continue;
            }
            String categoryName = folder.getName();
            categoryMap.put(categoryMapIDGenerator.getAndIncrement(),categoryName);
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileContent = readWholeFile(file);
                DocModel docModel = new DocModel();
                docModel.setCategory(categoryName);
                List<Word> words = WordSegmenter.seg(fileContent);
                Multiset<String> letterCountNultiset = HashMultiset.create();
                if(!CollectionUtils.isEmpty(words)) {
                    for(Word word : words) {
                        String wordStr = word.getText();
                        letterCountNultiset.add(wordStr);
                        list.add(wordStr);
                    }
                }
                docModel.setLetterCountNultiset(letterCountNultiset);
                docModels.add(docModel);
            }
        }
        preprocessedDataSet.setCategoryMap(categoryMap);
        preprocessedDataSet.setDocModels(docModels);
        Collections.sort(list);
        dic.build(list);
        preprocessedDataSet.setDic(dic);
        return preprocessedDataSet;
    }

    public static String readWholeFile(File file) throws IOException {
        Long filelength = file.length();     //获取文件长度
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream in = new FileInputStream(file);
        in.read(filecontent);
        in.close();
        return new String(filecontent);//返回文件内容,默认编码
    }
}
