package com.tj.cloud.system.test;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/18
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长
 * 子串
 *  的长度。
 * @version:1.0
 */
public class LetCodeTest3 {




    public static int lengthOfLongestSubstring(String s) {
        int cursor =0;
        int bigger =0;
        StringBuilder stringBuilder = new StringBuilder();
        if(s.length()==0){
            return 0;
        }
        do{
            String tmp = s.substring(cursor,cursor+1);
            if(stringBuilder.indexOf(tmp)!=-1 && !tmp.equals("")){
                if(bigger<stringBuilder.length()){
                    bigger = stringBuilder.length();
                }
                stringBuilder.delete(0,stringBuilder.indexOf(tmp)+1);
                stringBuilder.append(tmp);
            }else{
                stringBuilder.append(tmp);
            }
            System.out.println(stringBuilder.toString());
            cursor++;
            if(cursor>=s.length() && bigger<stringBuilder.length()){
                bigger = stringBuilder.length();
            }
        }while (cursor<s.length());
        System.out.println(stringBuilder.toString());
        return bigger;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring(" "));
    }
}
