package com.sf.common.utils;


import java.lang.reflect.Field;

/**
 * @Author: zhouliang
 * @Date: 2018/6/19 14:10
 */
public class ReflectClass {

    /**
     * 根据类和字段名字获取该字段对应的值
     * @param obj
     * @param fileName
     * @return
     */
    public static String reflect(Object obj,String fileName) {
        if (obj == null) {
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        String[] types1 = {"int", "java.lang.String", "boolean", "char", "float", "double", "long", "short", "byte"};
        String[] types2 = {"Integer", "java.lang.String", "java.lang.Boolean", "java.lang.Character",
                "java.lang.Float", "java.lang.Double", "java.lang.Long", "java.lang.Short", "java.lang.Byte"};
        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            if (fields[j].getName().equals(fileName)){
                for (int i = 0; i < types1.length; i++) {
                    if (fields[j].getType().getName().equalsIgnoreCase(types1[i]) || fields[j].getType().getName().equalsIgnoreCase(types2[i])) {
                        try {
                            Object o = fields[j].get(obj);
                            return null!=o?o.toString():"";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
        }
        return null;
    }


    /**
     *  设置值得时候，必须和对象的属性相对应
     *
     * @param obj 设置值的对象
     * @param PropertyName 字段名称
     * @param value 设置的值
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setProperty(Object obj, String PropertyName, Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // 获取obj类的字节文件对象
        Class c = obj.getClass();
        // 获取该类的成员变量
        Field f = c.getDeclaredField(PropertyName);
        // 取消语言访问检查
        f.setAccessible(true);
        // 给变量赋值
        f.set(obj, value);
        }

}
