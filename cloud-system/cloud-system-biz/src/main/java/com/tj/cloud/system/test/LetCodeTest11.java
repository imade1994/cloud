package com.tj.cloud.system.test;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/23
 * @Description:
 * @version:1.0
 */
public class LetCodeTest11 {


    public static int maxArea(int[] height) {
        int lc=0;
        int rc=height.length-1;
        int max =0;
        while(lc<=rc){
            if((rc-lc)*Math.min(height[lc],height[rc])>max){
                max =(rc-lc)*Math.min(height[lc],height[rc]);
            }
            if(height[lc]>height[rc]){
                rc--;
            }else{
              lc++;
            }
        }

        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1,8,6,2,5,4,8,3,7}));
    }
}
