package com.tj.cloud.system.test;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/18
 * @Description:
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 *
 * 算法的时间复杂度应该为
 * @version:1.0
 */
public class LetCodeTest4 {


    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int middle = (nums1.length + nums2.length)/2+1;
        boolean flag = (nums1.length + nums2.length) %2==0?true:false;
        if(nums1.length==0){
            if(flag){
                return new Double(nums2[middle-2]+nums2[middle-1])/2;
            }else{
                return new Double(nums2[middle-1]);
            }
        }
        if(nums2.length==0){
            if(flag){
                return new Double(nums1[middle-2]+nums1[middle-1])/2;
            }else{
                return new Double(nums1[middle-1]);
            }
        }
        int cursor1 =0;
        int cursor2 =0;
        int[] result = new int[middle];
        int size = 0;
        do{
            if(cursor1>=nums1.length){
                result[size]=nums2[cursor2];
                cursor2++;
            }else{
                if(cursor2>=nums2.length){
                    result[size]=nums1[cursor1];
                    cursor1++;

                }else{
                    if(nums1[cursor1]>=nums2[cursor2]){
                        result[size]=nums2[cursor2];
                        cursor2++;
                    }else{
                        result[size]=nums1[cursor1];
                        cursor1++;

                    }
                }
            }
            size++;
        }while(size<middle);
        if(flag){
            return new Double(result[middle-2]+result[middle-1])/2;
        }else{
            return new Double(result[middle-1]);
        }
    }

    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[]{1,3},new int[]{2}));
    }
}
