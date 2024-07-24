package com.tj.cloud.system.test;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/22
 * @Description:
 * @version:1.0
 */
public class LetCodeTest9 {


    public static boolean isPalindrome(int x) {
        int cursor=0;
        if(Math.abs(x)!=x){
            return false;
        }
        if(x==0){
            return true;
        }
        int length = (x + "").length();
        do{
            if(length%2!=0 && cursor==length/2){
                return true;
            }
            if(cursor==0){
                if((int)(x%Math.pow(10,cursor+1))%10!=(int)(x/Math.pow(10,length-1-cursor))){
                    return false;
                }
            }else{
                if((int)(x%Math.pow(10,cursor+1)/Math.pow(10,cursor))!=(int)(x/Math.pow(10,length-1-cursor)%10)){
                    return false;
                }
            }

            cursor++;
        }while(Math.pow(10,2*cursor)<x);
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(1));
    }
}
