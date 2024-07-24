package com.tj.cloud.system.test;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/22
 * @Description:
 * @version:1.0
 */
public class LetCodeTest10 {

    public static boolean isMatch(String s, String p) {
        if(s.equals(p)){
            return true;
        }
        int pCursor=0;
        int sCursor=0;
        int cursor = 0;
        int firstIndex=0;
        String[] sSp = s.split("");
        String[] pSp = p.split("");

        String[] tmP = p.split("\\*");
        boolean flag = false;
        String lastVal = "";
        StringBuilder stringBuilder = new StringBuilder();
        if(p.contains(".*")){
            return true;
        }
        int s1 =0;
        int s2 =0;
        int index = 0;
        int pi = 0;
        while(s1<s.length()){
            if(s2<p.length()){
                if(pSp[s2].equals(".") || pSp.equals("*")){
                    s2++;
                }else{
                    String tmp = pSp[s2];
                    index = s.indexOf(tmp);
                    pi = s2;
                    int i1 = index-1;
                    int i2 = index+1;
                    int i3 = s2-1;
                    int i4 = s2+1;
                    String ls ="";
                    boolean f = false;
                    while (i1>=0){
                        //if(sSp[i1].equals(pSp[]))
                    }
                }
            }else{
                if(p.length()>=s.length()){
                    flag=true;
                }else{
                    if(p.contains("*")){
                        flag=true;
                    }
                }
                break;
            }
        }





        while(pCursor<p.length()){
            if(sCursor<s.length()){
                if(lastVal.isEmpty()){
                    if(sSp[sCursor].equals(pSp[pCursor])){
                        lastVal=sSp[sCursor];
                        sCursor++;
                        pCursor++;
                    }else{
                        if(pSp[pCursor].equals(".")){
                            sCursor++;
                            pCursor++;
                            lastVal=".";
                        }else if (pSp[pCursor].equals("*")){
                            flag=false;
                            break;
                        }else{
                            pCursor++;
                        }
                    }
                }else{
                    if(sSp[sCursor].equals(pSp[pCursor])){
                        lastVal=sSp[sCursor];
                        sCursor++;
                        pCursor++;
                    }else{
                        if(pSp[pCursor].equals("*")){
                            if(lastVal.equals(".")){
                                sCursor++;
                            }else if(lastVal.equals(sSp[sCursor])){
                                sCursor++;
                            }else{
                                flag=false;
                                break;
                            }
                        }else if(pSp[pCursor].equals(".")){
                            sCursor++;
                            pCursor++;
                            lastVal=".";
                        }else{
                            flag=false;
                            break;
                        }
                    }
                }
            }else{
                break;
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        //String str = ".*..*ab*";
        //System.out.println(Arrays.toString(str.split("\\.\\*")));
        System.out.println("abbcdefg".indexOf("bcd"));
        //System.out.println(isMatch("aab","c*a*b"));
    }
}
