package com.tj.cloud.system.test;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/22
 * @Description:
 *
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 *
 * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
 *
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 * @version:1.0
 */
public class LetCodeTest7 {

    public static int reverse(int x) {
        String[] s = new String(x + "").split("");
        if(s.length==1){
            return x;
        }
        if(s.length==2 && s[0].equals("-")){
            return x;
        }
        int rightCursor=0;
        int leftCursor=s.length-1;
        int count=0;
        double sum=0;
        do{
            if(x<0){
                if(rightCursor+1==leftCursor){
                    sum = sum+Integer.parseInt(s[rightCursor+1])*Math.pow(10,rightCursor);
                }else{
                    sum = sum+Integer.parseInt(s[leftCursor])*Math.pow(10,leftCursor-1)+Integer.parseInt(s[rightCursor+1])*Math.pow(10,rightCursor);
                }
            }else{

                if(leftCursor==rightCursor){
                    sum = sum+Integer.parseInt(s[leftCursor])*Math.pow(10,leftCursor);
                }else{
                    sum = sum+Integer.parseInt(s[leftCursor])*Math.pow(10,leftCursor)+Integer.parseInt(s[rightCursor])*Math.pow(10,rightCursor);
                }
            }
            rightCursor++;
            leftCursor--;
            count+=2;
        }while((x<0 && count<(s.length-1))|| (x>0 && count<(s.length)));
        if(x<0){
            sum=sum*-1;
        }
        if(sum<-1*Math.pow(2,31) || sum>Math.pow(2,31)-1){
            return 0;
        }
        return (int)sum;
    }




    public static void main(String[] args) {
        System.out.println(reverse(-123));
    }
}
