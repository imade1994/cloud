package com.tj.cloud.datasource.readwrite;

import java.util.LinkedList;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/28 11:15
 * * @version v1.0.0
 * * @desc
 **/
public class ReadWriteSplitHolder {

    private static final ThreadLocal<LinkedList<Boolean>> RW_HOLDER = new ThreadLocal<>();


    public static void bindReadOnly(boolean readOnly) {
        LinkedList<Boolean> holder = RW_HOLDER.get();
        if (holder == null) {
            holder = new LinkedList<>();
            RW_HOLDER.set(holder);
        }
        holder.push(readOnly);
    }

    public static void restoreReadOnly() {
        LinkedList<Boolean> holder = RW_HOLDER.get();
        if (holder != null) {
            holder.pop();
            if (holder.isEmpty()) {
                RW_HOLDER.remove();
            }
        }
    }

    public static boolean isReadOnly() {
        LinkedList<Boolean> holder = RW_HOLDER.get();
        return holder != null && Boolean.TRUE.equals(holder.peek());
    }
}
