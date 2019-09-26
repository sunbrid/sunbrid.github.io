package com.dc.base.util;

import java.util.regex.Pattern;

/**
 * @author Enzo
 * @Description 正则表达式工具类
 * @date 2018-11-22 13:41
 */
public class RegularExpressionUtil {
    //验证特殊字符
    public static String SPECIAL_CODE = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    /**
     * @return boolean
     * @title:<h3> 正则表达式验证 <h3>
     * @author: Enzo
     * @date: 2018-11-22 13:46
     * @params [regular, str]
     **/
    public static boolean check(String regular, String str) throws Exception {
        return Pattern.matches(regular, str);
    }
}
