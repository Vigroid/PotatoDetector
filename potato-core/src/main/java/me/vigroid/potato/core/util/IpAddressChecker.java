package me.vigroid.potato.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vigroid on 10/17/17.
 * Author:ITluochen
 * Cite from: http://blog.csdn.net/itluochen/article/details/53641895
 */

public class IpAddressChecker {

    public static boolean isLegalIp(String addr){
        final int ip_min_size = 7;
        final int ip_max_size = 15;

        if(addr.length() < ip_min_size || addr.length() > ip_max_size || "".equals(addr))
        {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean isLegal = mat.find();

        return isLegal;
    }
}
