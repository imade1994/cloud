package com.tj.cloud.system.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/26
 * @Description:
 * @version:1.0
 */
public class LetCodeTest14 {



    public int longestConsecutive(int[] nums) {

        int cursor=0;
        int bigger=0;
        int[] newN = new int[nums.length];
        Set<Integer> set=new HashSet<>();
        Map<Integer,Integer> map=new HashMap<>();
        while (cursor<nums.length) {

            if(set.contains(nums[cursor])) {
                continue;
            }else{
                //查询有没有前后值
                int length=0;
                if(set.contains(nums[cursor]+1)) {
                    length=map.get(nums[cursor]+1);
                    map.put(nums[cursor]+1+length,length+1);
                }else if(set.contains(nums[cursor]-1)) {
                    length=map.get(nums[cursor]-1);
                    map.put(nums[cursor]-1-length,length+1);
                }else{
                    map.put(nums[cursor],length);
                }
                set.add(nums[cursor]);
            }
            cursor++;
        }
        return 1;
    }

    public static void main(String[] args) {
        Set<int[]> set = new HashSet<>();
        int[] nums = new int[]{1,3,4,7,9,10};
        set.add(nums);
        System.out.println(set);



    }
}
