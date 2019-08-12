package com.tao.utilslib.data;

import java.math.BigDecimal;

public class ArithUtil {

    // 除法运算默认精度
    private static final int DEF_DIV_SCALE = 10;

    private ArithUtil() {

    }

    /**
     * 精确加法
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 精确减法
     */
    public static double sub(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 精确乘法
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 精确除法 使用默认精度
     */
    public static double div(double value1, double value2) throws IllegalAccessException {
        return div(value1, value2, DEF_DIV_SCALE);
    }

    /**
     * 精确除法
     * @param scale 精度
     */
    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        if(scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        // return b1.divide(b2, scale).doubleValue();
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入
     * @param scale 小数点后保留几位
     */
    public static double round(double v, int scale) throws IllegalAccessException {
        return div(v, 1, scale);
    }

    /**
     * 比较大小
     */
    public static boolean equalTo(BigDecimal b1, BigDecimal b2) {
        if(b1 == null || b2 == null) {
            return false;
        }
        return 0 == b1.compareTo(b2);
    }


    public static void main(String[] args) throws IllegalAccessException {
        double value1=1.2345678912311;
        double value2=9.1234567890123;
        BigDecimal value3=new BigDecimal(Double.toString(value1));
        BigDecimal value4=new BigDecimal(Double.toString(value2));
        System.out.println("精确加法================="+ ArithUtil.add(value1, value2));
        System.out.println("精确减法================="+ ArithUtil.sub(value1, value2));
        System.out.println("精确乘法================="+ ArithUtil.mul(value1, value2));
        System.out.println("精确除法 使用默认精度 ================="+ ArithUtil.div(value1, value2));
        System.out.println("精确除法  设置精度================="+ ArithUtil.div(value1, value2,20));
        System.out.println("四舍五入   小数点后保留几位 ================="+ ArithUtil.round(value1, 10));
        System.out.println("比较大小 ================="+ ArithUtil.equalTo(value3, value4));
    }
}
