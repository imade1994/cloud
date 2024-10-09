package com.tj.cloud.system.test;

import java.util.*;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/26
 * @Description:
 * @version:1.0
 */
public class LetCodeTest13 {

    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> list=new ArrayList<>();
        if(strs.length==1){
            list.add(Arrays.asList(strs));
            return list;
        }
        int cursor=0;
        Map<String,List<String>> map=new HashMap<>();
        while(cursor<strs.length){
            String key = Arrays.toString(strs[cursor].chars().sorted().toArray());
            if(map.containsKey(key)){
               map.get(key).add(strs[cursor]);
                //map.get(length).add(strs[cursor]);
            }else{
                List<String> tmpL=new ArrayList<>();
                tmpL.add(strs[cursor]);
                map.put(key,tmpL);
            }
            cursor++;
        }
        for(String s:map.keySet()){
            list.add(map.get(s));
        }
        return list;
    }


    public static void main(String[] args) {
        System.out.println(groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));



    }
}
