package com.tj.cloud.system.test;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/19
 * @Description:
 * @version:1.0
 */
public class LetCodeTest6 {



    public static String convert(String s, int numRows) {
        int gap;
        StringBuilder stringBuilder = new StringBuilder();
        if(numRows>1 &&  s.length()>numRows){
            for (int i = 0; i < numRows; i++) {
                int cursor=i;
                if(i==0||i==numRows-1){
                    gap = 2*(numRows -1);
                }else{
                    gap = (numRows-i-1)*2;
                }
                int count=0;
                do{
                    stringBuilder.append(s.substring(cursor,cursor+1));
                    if(i!=0&&i!=numRows-1){
                        if(count==1){
                            count=0;
                            cursor=cursor+(2*i);
                        }else{
                            cursor=cursor+gap;
                            count++;
                        }
                    }else{
                        cursor=cursor+gap;
                    }
                }while (cursor<=s.length()-1);
            }
            return stringBuilder.toString();
        }else{
            return s;
        }
    }

    public static void main(String[] args) {
        System.out.println(convert("PAYPALISHIRING",3));
    }
}
