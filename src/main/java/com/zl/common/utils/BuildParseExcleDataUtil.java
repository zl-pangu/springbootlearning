package com.zl.common.utils;

import com.zl.config.Constant;
import com.zl.domain.CustBchmakDO;
import com.zl.domain.CustGenerateFileDO;
import com.zl.domain.CustRuleDO;
import com.zl.domain.CustUploadingFilesDO;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: zhouliang
 * @Date: 2018/6/20 14:47
 */
public class BuildParseExcleDataUtil {
    public static void buildTargetData(CustRuleDO custRuleDO, ConcurrentHashMap<String,List<CustUploadingFilesDO>> map, ConcurrentHashMap<String,CustGenerateFileDO> tarGetDataMap, CustBchmakDO custBchmakDO, ThreadPoolExecutor executor) throws Exception{
        //目标列
        String targetColumn = custRuleDO.getTargetColumn();
        //第一个计算规则
        String calculation = custRuleDO.getCalculation();
        //第二个计算规则
        String calculations = custRuleDO.getCalculations();

        if (calculation.equals("5")){
            //操作表
            String tableA = custRuleDO.getTableA();

            //目标表替换 源文件表的值
            List<CustUploadingFilesDO> custUploadingFilesDOS = map.get(tableA);

            //用多线程读取list替换列
            multiThreadReplace(custRuleDO,custUploadingFilesDOS,tarGetDataMap,executor);
        }else if (StringUtils.isNotEmpty(calculation)&&StringUtils.isEmpty(calculations)&&calculation.equals("6")){
            //第一个有计算，第二个无计算且值为6；则目标列生成默认值
            String tableName = custBchmakDO.getTemplateTable();

            //查询到目标表的操作列
            List<CustUploadingFilesDO> list = null!=map.get(tableName)?map.get(tableName):null;

            //多线程读取list，设置对象的默认值
            multiThreaDefaultValue(custBchmakDO,custRuleDO,list,tarGetDataMap,executor);
        }else if (StringUtils.isNotEmpty(calculation)&&StringUtils.isEmpty(calculations)&&!calculation.equals("6")){
            //只有AB表进行加减乘除
            String tableB = custRuleDO.getTableB();
            String columnB = custRuleDO.getColumnB();
            String benchmarkB = custRuleDO.getBenchmarkB();
            ConcurrentHashMap<String,String> dataMapB=new ConcurrentHashMap<>();

            //把b要操作列的值放到map里面，拆分两个for循环,同时用多线程处理list
            buildDataMap(dataMapB,map,tableB,columnB,benchmarkB,executor);

            //多线程读取list，计算A，B表的值
            multiThreaCalculation(custRuleDO,map,executor,dataMapB,tarGetDataMap);
        }else if (StringUtils.isNotEmpty(calculation)&&StringUtils.isNotEmpty(calculations)){
            //abc三个表都有值，进行加减乘除

            //构建B table的值
            ConcurrentHashMap<String,String> dataMapB=new ConcurrentHashMap<>();
            String columnb= custRuleDO.getColumnB().toLowerCase();
            String benchmarkb = custRuleDO.getBenchmarkB().toLowerCase();
            String tableb = custRuleDO.getTableB();
            buildDataMap(dataMapB,map,tableb,columnb,benchmarkb,executor);

            //构建C table的值
            ConcurrentHashMap<String,String> dataMapC=new ConcurrentHashMap<>();
            String columnC = custRuleDO.getColumnC().toLowerCase();
            String benchmarkC = custRuleDO.getBenchmarkC().toLowerCase();
            String tableC = custRuleDO.getTableC();
            buildDataMap(dataMapC,map,tableC,columnC,benchmarkC,executor);

            //A tabie参与计算需要的数据
            String columnA = custRuleDO.getColumnA().toLowerCase();
            String benchmarkA = custRuleDO.getBenchmarkA().toLowerCase();

            //遍历A table的数据,三个for循环放到一个for里面处理
            List<CustUploadingFilesDO> list = map.get(custRuleDO.getTableA());
            // 每10000条数据开启一条线程
            int threadSize = 10000;
            // 总数据条数
            int dataSize = list.size();
            // 理论线程数，如果这个数大于机器能创建线程数，这个数就是list的批次数
            int threadNum = dataSize / threadSize + 1;
            // 定义标记,过滤threadNum为整数
            boolean special = dataSize % threadSize == 0;
            //栅栏数
            CountDownLatch latch=new CountDownLatch(threadNum-1);
            List<CustUploadingFilesDO> cutList = null;
            // 确定每条线程的数据
            for (int i = 0; i < threadNum; i++) {
                if (i == threadNum - 1) {
                    if (special) {
                        break;
                    }
                    cutList = list.subList(threadSize * i, dataSize);
                } else {
                    cutList = list.subList(threadSize * i, threadSize * (i + 1));
                }
                final List<CustUploadingFilesDO> listData = cutList;
                executor.execute(()->{
                    try{
                        for (CustUploadingFilesDO filesDO: listData) {
                            //a操作列的值
                            String colA = ReflectClass.reflect(filesDO, columnA);
                            //a基准列的值
                            String bkmarkA = ReflectClass.reflect(filesDO, benchmarkA);
                            //b操作列的值
                            String colB = null!=dataMapB.get(bkmarkA)?dataMapB.get(bkmarkA):"";
                            //c操作列的值
                            String colC =null!=dataMapC.get(bkmarkA)?dataMapC.get(bkmarkA):"";
                            //计算abc三表
                            String total = calculationCol(calculation, calculations, colA, colB, colC);
                            //取目标列
                            CustGenerateFileDO generateFile= tarGetDataMap.get(bkmarkA);
                            //把计算结果放到目标列
                            ReflectClass.setProperty(generateFile,targetColumn.toLowerCase(),total);
                            //放
                            tarGetDataMap.put(bkmarkA,generateFile);
                        }
                    }catch (Exception e){
                            e.printStackTrace();
                    }finally {
                        latch.countDown();
                    }
                });
            }
            //等待所有的线程完成
            latch.await();
        }else{
            //abc三个表都无值 目标列全部置空
        }

    }

    static String calculationCol(String rule1,String rule2,String aStr,String bStr,String cStr){
        String total="";
        //如果三个值其中某个为空，则计算结果直接为空
        if (StringUtils.isNotEmpty(aStr)&&StringUtils.isNotEmpty(bStr)&&StringUtils.isNotEmpty(cStr)){
            /**
             * ++ -+ *+
             * +- -- *-
             * +* -* **
             * +/ -/  .....
             * 4*4 十六种计算情况
             */
            String getv = calculationCol(rule1, aStr, bStr);
            total = calculationCol(rule2, getv, cStr);
        }
        return total;
    }

     static synchronized String calculationCol(String rule, String aStr,String bStr){
            String result=null;
            if (StringUtils.isNotEmpty(aStr)&&StringUtils.isNotEmpty(bStr)){
                try{
                    BigDecimal a= new BigDecimal(aStr);
                    BigDecimal b=new BigDecimal(bStr);
                    switch (rule){
                        case Constant.CUST_ADD://加
                            result= a.add(b).toString();
                            break;
                        case Constant.CUST_SUB://减
                            result=a.subtract(b).toString();
                            break;
                        case Constant.CUST_MUL://乘
                            result=a.multiply(b).toString();
                            break;
                        case Constant.CUST_DIV://除
                            result=a.divide(b).toString();
                            break;
                    }
                }catch (Exception e){
                    //如果a，b不为数字则抛出异常。设置result为null
                    result=null;
                }
            }
            return result;
    }

    public static void buildDataMap(ConcurrentHashMap<String,String> dataMap, ConcurrentHashMap<String,List<CustUploadingFilesDO>> map, String tableName,String column,String benchmark,ThreadPoolExecutor executor) throws InterruptedException {
        if (StringUtils.isNotEmpty(tableName)){
            List<CustUploadingFilesDO> list = map.get(tableName);
            // 每10000条数据开启一条线程
            int threadSize = 10000;
            // 总数据条数
            int dataSize = list.size();
            // 理论线程数，如果这个数大于机器能创建线程数，这个数就是list的批次数
            int threadNum = dataSize / threadSize + 1;
            // 定义标记,过滤threadNum为整数
            boolean special = dataSize % threadSize == 0;
            //栅栏数
            CountDownLatch latch=new CountDownLatch(threadNum-1);
            List<CustUploadingFilesDO> cutList = null;
            // 确定每条线程的数据
            for (int i = 0; i < threadNum; i++) {
                if (i == threadNum - 1) {
                    if (special) {
                        break;
                    }
                    cutList = list.subList(threadSize * i, dataSize);
                } else {
                    cutList = list.subList(threadSize * i, threadSize * (i + 1));
                }
                final List<CustUploadingFilesDO> listData = cutList;
                executor.execute(()->{
                    try{
                        for (CustUploadingFilesDO custUploadingFilesDO:listData) {
                            //通过反射取到列的值
                            String columnValue = ReflectClass.reflect(custUploadingFilesDO, column.toLowerCase());
                            String benchmarkValue = ReflectClass.reflect(custUploadingFilesDO, benchmark.toLowerCase());
                            if (StringUtils.isNotEmpty(benchmarkValue)){
                                dataMap.put(benchmarkValue,columnValue);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        latch.countDown();
                    }
                });
            }
            latch.await();
        }
    }

    private static void multiThreaCalculation(CustRuleDO custRuleDO, ConcurrentHashMap<String,List<CustUploadingFilesDO>> map,ThreadPoolExecutor executor, ConcurrentHashMap<String,String> dataMapB,ConcurrentHashMap<String,CustGenerateFileDO> tarGetDataMap) throws InterruptedException {
        String tableA = custRuleDO.getTableA();
        String columnA = custRuleDO.getColumnA().toLowerCase();
        String benchmarkA = custRuleDO.getBenchmarkA().toLowerCase();
        String calculation = custRuleDO.getCalculation();
        String targetColumn = custRuleDO.getTargetColumn().toLowerCase();
        List<CustUploadingFilesDO> list = map.get(tableA);
        // 每10000条数据开启一条线程
        int threadSize = 10000;
        // 总数据条数
        int dataSize = list.size();
        // 理论线程数，如果这个数大于机器能创建线程数，这个数就是list的批次数
        int threadNum = dataSize / threadSize + 1;
        // 定义标记,过滤threadNum为整数
        boolean special = dataSize % threadSize == 0;
        //栅栏数
        CountDownLatch latch=new CountDownLatch(threadNum-1);
        List<CustUploadingFilesDO> cutList = null;
        // 确定每条线程的数据
        for (int i = 0; i < threadNum; i++) {
            if (i == threadNum - 1) {
                if (special) {
                    break;
                }
                cutList = list.subList(threadSize * i, dataSize);
            } else {
                cutList = list.subList(threadSize * i, threadSize * (i + 1));
            }
            final List<CustUploadingFilesDO> listData = cutList;
            executor.execute(()->{
                try{
                    for (CustUploadingFilesDO a :listData) {
                        //a操作列的值
                        String cola = ReflectClass.reflect(a, columnA);
                        String bcma = ReflectClass.reflect(a, benchmarkA);
                        //b操作列的值
                        String colb = dataMapB.get(bcma);
                        //计算啊，b的值
                        String total = calculationCol(calculation, cola, colb);
                        //取目标列
                        CustGenerateFileDO generateFile= tarGetDataMap.get(bcma);
                        //把计算结果放到目标列
                        ReflectClass.setProperty(generateFile,targetColumn,total);
                        //把设置好的值放回map
                        tarGetDataMap.put(bcma,generateFile);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
    }

    private static void multiThreaDefaultValue(CustBchmakDO custBchmakDO, CustRuleDO custRuleDO,List<CustUploadingFilesDO> list, ConcurrentHashMap<String,CustGenerateFileDO> tarGetDataMap, ThreadPoolExecutor executor) throws InterruptedException {
        String benchmarkClo = custBchmakDO.getBenchmarkClo().toLowerCase();
        String defaultValue = custRuleDO.getDefaultValue();
        String targetColumn = custRuleDO.getTargetColumn().toLowerCase();
        // 每10000条数据开启一条线程
        int threadSize = 10000;
        // 总数据条数
        int dataSize = list.size();
        // 理论线程数，如果这个数大于机器能创建线程数，这个数就是list的批次数
        int threadNum = dataSize / threadSize + 1;
        // 定义标记,过滤threadNum为整数
        boolean special = dataSize % threadSize == 0;
        //栅栏数
        CountDownLatch latch=new CountDownLatch(threadNum-1);
        List<CustUploadingFilesDO> cutList = null;
        // 确定每条线程的数据
        for (int i = 0; i < threadNum; i++) {
            if (i == threadNum - 1) {
                if (special) {
                    break;
                }
                cutList = list.subList(threadSize * i, dataSize);
            } else {
                cutList = list.subList(threadSize * i, threadSize * (i + 1));
            }
            final List<CustUploadingFilesDO> listData = cutList;
            executor.execute(()->{
                try{
                    for (CustUploadingFilesDO bchmak: listData) {
                        //基准列的值
                        String bchmakValue = ReflectClass.reflect(bchmak,benchmarkClo);
                        //取
                        CustGenerateFileDO generateFile= tarGetDataMap.get(bchmakValue);
                        //设置默认值
                        ReflectClass.setProperty(generateFile,targetColumn,defaultValue);
                        //放
                        tarGetDataMap.put(bchmakValue,generateFile);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    latch.countDown();
                }
            });
        }
        //等待所有的线程完成
        latch.await();
    }

    private static void multiThreadReplace(CustRuleDO custRuleDO,List<CustUploadingFilesDO> list, ConcurrentHashMap<String,CustGenerateFileDO> map, ThreadPoolExecutor executor) throws Exception{
        String columnA = custRuleDO.getColumnA().toLowerCase();
        String benchmarkA = custRuleDO.getBenchmarkA().toLowerCase();
        String targetColumn = custRuleDO.getTargetColumn().toLowerCase();
        // 每10000条数据开启一条线程
        int threadSize = 10000;
        // 总数据条数
        int dataSize = list.size();
        // 理论线程数，如果这个数大于机器能创建线程数，这个数就是list的批次数
        int threadNum = dataSize / threadSize + 1;
        // 定义标记,过滤threadNum为整数
        boolean special = dataSize % threadSize == 0;
        //栅栏数
        CountDownLatch latch=new CountDownLatch(threadNum-1);
        List<CustUploadingFilesDO> cutList = null;
        // 确定每条线程的数据
        for (int i = 0; i < threadNum; i++) {
            if (i == threadNum - 1) {
                if (special) {
                    break;
                }
                cutList = list.subList(threadSize * i, dataSize);
            } else {
                cutList = list.subList(threadSize * i, threadSize * (i + 1));
            }
            final List<CustUploadingFilesDO> listData = cutList;
            executor.execute(()->{
                try{
                    for (CustUploadingFilesDO uploadingFilesDO:listData) {
                        String  column = ReflectClass.reflect(uploadingFilesDO, columnA);
                        String benchmark = ReflectClass.reflect(uploadingFilesDO, benchmarkA);
                        //先从map里面根据基准列的值取出这个对象，然后给对应的列设置值。
                        CustGenerateFileDO generateFileDO = map.get(benchmark);
                        ReflectClass.setProperty(generateFileDO,targetColumn,column);
                        //设置完值再把这个对象放到map里面
                        map.put(benchmark,generateFileDO);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    latch.countDown();
                }
            });
        }
        //等待各子线程匹配的完成
        latch.await();
    }
}
