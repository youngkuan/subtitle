package com.yang.utils;

import java.util.List;

/**
 * 对字符的处理
 * @author yangkuan
 */
public class CharacterUtil {

    public static String replaceSpecialChracter(String s){
        /*String[] specials = {"\\","/",":","*","?","\"","<",">","|"};
        for(String special:specials){
            if(s.contains(special)){
                s.replaceAll(special,"_");
            }
        }*/
        s = s.split("/")[0];
        System.out.println("文件名："+s);
        return s;
    }
}
