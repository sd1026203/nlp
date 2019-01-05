package com.nlp.nlp.sense;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.util.concurrent.AtomicLongMap;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.classification.tokenizers.HanLPTokenizer;
import com.hankcs.hanlp.seg.common.Term;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

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
//
        File[] folders = root.listFiles();
        if (folders == null) {
            LOGGER.info("空集");
            return null;
        }
        PreprocessedDataSet preprocessedDataSet = new PreprocessedDataSet();
        List<DocModel> docModels = new ArrayList<>();
        AtomicLongMap<String> categoryDocCountMap = AtomicLongMap.create();
        HanLPTokenizer hanLPTokenizer = new HanLPTokenizer();
        for (File folder : folders) {
            if (folder.isFile()) continue;
            File[] files = folder.listFiles();
            if (files == null) {
                continue;
            }
            String categoryName = folder.getName();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if(file.getName().equals("neg.1674.txt")){
                    System.out.println(readWholeFile(file));
                }
                String fileContent = readWholeFile(file);
                DocModel docModel = new DocModel();
                docModel.setCategory(categoryName);
//                AtomicLongMap<String> letterCountMap = AtomicLongMap.create();
                Set<String> letterSet = new HashSet<>();


//                List<Word> words = WordSegmenter.seg(fileContent);
//                if(!CollectionUtils.isEmpty(words)) {
//                    for(Word word : words) {
//                        String wordStr = word.getText();
//                        letterCountMap.put(wordStr, 1);
//                    }
//                }


                String[] words = hanLPTokenizer.segment(fileContent);
                if(words != null && words.length > 0) {
                    for(String word : words) {
//                        String wordStr = term.word;
                        letterSet.add(word);
                    }
                }



                docModel.setLetterSet(letterSet);
                docModels.add(docModel);
                categoryDocCountMap.incrementAndGet(categoryName);
            }
        }
        preprocessedDataSet.setCategoryDocCountMap(categoryDocCountMap);
        preprocessedDataSet.setDocModels(docModels);
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
