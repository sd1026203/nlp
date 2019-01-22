package com.nlp.nlp.sense;

import com.google.common.util.concurrent.AtomicLongMap;
import com.hankcs.hanlp.classification.tokenizers.HanLPTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 葛伟 gewei01@58ganji.com
 * @Date 2019/1/3 17:53
 */
public class ProcessDataFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataFactory.class);
    public static final HanLPTokenizer hanLPTokenizer = new HanLPTokenizer();
    public static PreprocessedDataSet loadPreprocessedDataSet(String folderPath) throws IOException {
        if (folderPath == null) throw new IllegalArgumentException("参数 folderPath == null");
        File root = new File(folderPath);
        if (!root.exists()) throw new IllegalArgumentException(String.format("目录 %s 不存在", root.getAbsolutePath()));
        if (!root.isDirectory()) throw new IllegalArgumentException(String.format("目录 %s 不是一个目录", root.getAbsolutePath()));
        File[] folders = root.listFiles();
        if (folders == null) return null;

        PreprocessedDataSet preprocessedDataSet = new PreprocessedDataSet();
        List<DocModel> docModels = new ArrayList<>();
        AtomicLongMap<String> categoryDocCountMap = AtomicLongMap.create();

        for (File folder : folders) {
            if (folder.isFile()) continue;
            File[] files = folder.listFiles();
            if (files == null) continue;
            String categoryName = folder.getName();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileContent = readWholeFile(file);
                DocModel docModel = new DocModel(categoryName, fileContent);
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
