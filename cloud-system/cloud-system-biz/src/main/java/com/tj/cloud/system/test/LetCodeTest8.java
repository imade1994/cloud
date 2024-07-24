package com.tj.cloud.system.test;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/22
 * @Description:
 * @version:1.0
 */
public class LetCodeTest8 {

    public static int myAtoi(String s) {
        String[] tmp = s.split("");
        if(s.equals("")){
            return 0;
        }
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        boolean flag1=false;
        int cursor=0;
        do{
            int tmpI = (int)tmp[cursor].charAt(0);
            if(flag1){
                if(tmpI>=48 && tmpI<=57){
                    sb.append(tmp[cursor]);
                }else{
                    if(sb.length()==0||sb.toString().equals("-")){
                        return 0;
                    }else{
                        break;
                    }
                }
            }else{
                if(tmpI==45){
                    sb.append("-");
                    flag1=true;
                }else if(tmpI==43){
                    flag1=true;
                }else{
                    if(tmpI>=48 && tmpI<=57){
                        flag1=true;
                        sb.append(tmp[cursor]);
                    }
                    if(tmpI==32){
                        cursor++;
                        continue;
                    }
                    if(tmpI<48 || tmpI>57){
                        return 0;
                    }
                }

            }
            cursor++;
        }while (cursor< tmp.length);
        if(sb.toString().isEmpty()||sb.toString().equals("-")){
            return 0;
        }else{
            if(Double.parseDouble(sb.toString())<-1*Math.pow(2,31)){
                return (int)Math.pow(2,31)*-1-1;
            }else if(Double.parseDouble(sb.toString())>Math.pow(2,31)-1){
                return (int)Math.pow(2,31);
            }else{
                return Integer.parseInt(sb.toString());
            }
        }



    }

    public static void main(String[] args) {
        System.out.println(myAtoi("      -11919730356x"));
    }
}
