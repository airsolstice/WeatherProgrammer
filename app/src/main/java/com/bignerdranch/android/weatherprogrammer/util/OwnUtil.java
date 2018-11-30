package com.bignerdranch.android.weatherprogrammer.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @Package com.bignerdranch.android.weatherprogrammer.util
 * @Description 自定义工具类
 * @date 2018/11/30
 */
public class OwnUtil {

    private static String[] directions = {"N","NNE","NE","ENE","E","ESE","SE","SSE",
            "S","SSW","SW","WSW","W","WNW","NW","NNW"};

    /**
     * 将角度转换成方向
     */
    public static String changeAngleToDirection(String angle){
        if (StringUtils.isNotEmpty(angle)){
            return changeAngleToDirection(new BigDecimal(angle));
        }
        return null;
    }

    /**
     * 将角度转换成方向
     */
    public static String changeAngleToDirection(BigDecimal angle){
        boolean isNegative = (angle.compareTo(BigDecimal.ZERO) < 0 );
        BigDecimal circle = new BigDecimal("360");
        BigDecimal[] bigDecimals = angle.abs().divideAndRemainder(circle);
        BigDecimal angleR = bigDecimals[1];//余数为计算度数
        if (isNegative){
            //负数倒算
            angleR = circle.subtract(angleR);
        }
        int index = angleR.divide(new BigDecimal("22.5"), 0, BigDecimal.ROUND_DOWN).intValue();
        return directions[index];
    }

    private OwnUtil(){}

}
