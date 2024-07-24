package com.tj.cloud.system.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/19
 * @Description:
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * @version:1.0
 */
public class LetCodeTest5 {

    public static String longestPalindrome(String s) {
        if(s.length()==1){
            return s;
        }
        int bigger=0;
        String longest="";
        List<String> listApprove=new ArrayList<String>();
        for(int i=0;i<s.length();i++){
            String tmp = s.substring(i,i+1);
            if(s.lastIndexOf(tmp)==i){
                continue;
            }else{
                if(listApprove.contains(tmp)){
                    continue;
                }else{
                    String tmpS1 = s.substring(i,s.lastIndexOf(tmp)+1);
                    int cursor=0;
                    List<Integer> matchList = new ArrayList<>();
                    do{
                        if(tmpS1.substring(cursor,cursor+1).equals(tmp)){
                            for (int j = 0; j < matchList.size(); j++) {
                                String inTmp =tmpS1.substring(matchList.get(j),cursor+1);
                                if(inTmp.length()>bigger){
                                    if(validate(inTmp)){
                                        longest=inTmp;
                                        bigger=inTmp.length();
                                    }
                                }
                            }
                            matchList.add(cursor);
                        }
                        cursor++;
                    }while (cursor<=tmpS1.length()-1);
                    listApprove.add(tmp);
                }
            }
        }
        if(bigger==0){
            return s.substring(0,1);
        }
        return longest;
    }

    public static boolean validate(String s){
        boolean flag = s.length() % 2 == 0;
        int leftCursor  = 0;
        int rightCursor = 0;
        if(flag){
            leftCursor=s.length()/2-1;
            rightCursor=s.length()/2;
            do{
                if(!s.substring(leftCursor,leftCursor+1).equals(s.substring(rightCursor,rightCursor+1))){
                    return false;
                }
                leftCursor--;
                rightCursor++;
            }while(leftCursor>=0||rightCursor<=s.length()-1);
            return true;
        }else{
            leftCursor=s.length()/2-1;
            rightCursor=s.length()/2+1;
            do{
                if(!s.substring(leftCursor,leftCursor+1).equals(s.substring(rightCursor,rightCursor+1))){
                    return false;
                }
                leftCursor--;
                rightCursor++;
            }while(leftCursor>=0||rightCursor<=s.length()-1);
            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheeart"));
    }
}
