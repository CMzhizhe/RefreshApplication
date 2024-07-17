package com.gxx.refreshapplication.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DataUtils {

    private static final String RAND_STRINGS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * @date 创建时间:2022/4/8 0008
     * @auther gaoxiaoxiong
     * @Descriptiion 创建数据
     **/
    public static List<String> createDatas(int totalCount,String name){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            list.add("i = " + i +"，" + newRandomStringId24() +"，"+ name);
        }
        return list;
    }

    /**
     * 产生24位严格大小写的数字+子母字符串
     *
     * @return
     * @author zai
     * 2020-04-09 12:09:16
     */
    public static String newRandomStringId24() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder(24);
        for (int i = 0; i < 24; i++) {
            sb.append(RAND_STRINGS.charAt(random.nextInt(RAND_STRINGS.length())));
        }
        return sb.toString();
    }
}
