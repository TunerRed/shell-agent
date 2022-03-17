package org.shellagent.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class NetUtil {

    /**
     * 获取真实ip
     * 如果通过NGINX做了转发，NGINX中要配置'X-Real_IP'等字段
     * */
    public static String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Real_IP");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip))
            return ip;
        ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1)
                return ip.substring(0,index);
            return ip;
        }
        String[] checkChain = new String[]{"Proxy-Client-Ip","WL-Proxy-Client-Ip","HTTP_CLIENT_IP","HTTP_X_FORWARDED_FOR"};
        for (int i = 0; i < checkChain.length; i++) {
            if (isNotValid(ip))
                ip = request.getHeader(checkChain[i]);
        }
        if (isNotValid(ip))
            ip = request.getRemoteAddr();
        return isNotValid(ip)?"":ip;
    }
    private static boolean isNotValid(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }
}
