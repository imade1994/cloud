package com.tj.cloud.system.test;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/18
 * @Description:
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 *
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 *
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * @version:1.0
 */
public class LetCodeTest2 {

  public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }



    public ListNode addTwoNumber(ListNode l1, ListNode l2) {
        int add =0;
        ListNode pNode = new ListNode(0);
        ListNode cursor = pNode;
        do{
            if(null != l1){
                add=l1.val+add;
                l1=l1.next;
            }
            if(null != l2){
                add=l2.val+add;
                l2=l2.next;
            }
            cursor.next = new ListNode(add%10);
            add=add/10;
            cursor=cursor.next;
        }while(add != 0 || (l1 != null || l2 != null));
        return pNode.next;
    }










    public int[] addTwoNumbers(int[] l1, int[] l2) {
        int size = Math.max(l1.length, l2.length);
        int cursor = 0;
        boolean flag = false;
        int[] result = new int[size];
        do{
            int count=l1[cursor]+l2[cursor];;
            if(flag){
                count+=1;
            }
            if(count>9){
                flag=true;
                result[cursor]=count%10;
            }
            cursor++;
        }while (cursor>=size);
        return result;
    }
}
