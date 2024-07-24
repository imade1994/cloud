package com.tj.cloud.system.test;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/23
 * @Description:
 * @version:1.0
 */
public class LetCodeTest12 {


    public String intToRoman(int num) {
        String[] s = (num+"").split("");
        int cursor=s.length-1;
        StringBuilder stringBuilder = new StringBuilder();
        int last =0;
        while(cursor>=0){
            int tmp = (int)(num-last/Math.pow(10,cursor));
            if(cursor==3){
                while(tmp>0){
                    stringBuilder.append("M");
                    tmp--;
                }
            }
            if(cursor==2){
                //if(tmp)
            }
            if(cursor==1){

            }
            if(cursor==0){

            }
            last=(int)(tmp*Math.pow(10,cursor));
        }

        return "";

    }
}
