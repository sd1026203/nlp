package com.nlp.nlp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class NlpApplication {

    public static void main(String[] args) {
        System.out.println((int)(("华".toCharArray())[0]));
        DoubleArrayTrie doubleArrayTrie = new DoubleArrayTrie();
        doubleArrayTrie.build(Arrays.asList("中华", "中华人民", "中华人民共和国","清华"));
        System.out.println(doubleArrayTrie.exactMatchSearch("清华"));
        System.out.println();
    }

}

