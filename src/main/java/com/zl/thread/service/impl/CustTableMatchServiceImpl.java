package com.zl.thread.service.impl;

import com.zl.common.utils.BuildParseExcleDataUtil;
import com.zl.common.utils.ReflectClass;
import com.zl.thread.dao.*;
import com.zl.thread.domain.*;
import com.zl.thread.dto.CustTableMatchDto;
import com.zl.thread.service.CustTableMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.*;

/**
 * @Author: zhouliang
 * @Date: 2018/6/26 17:45
 */
@Service
public class CustTableMatchServiceImpl implements CustTableMatchService {

    @Autowired
    private CustRuleDao custRuleDao;

    @Autowired
    private CustUploadingFilesDao custUploadingFilesDao;

    @Autowired
    private CustUgDao custUgDao;

    @Autowired
    private CustBchmakDao custBchmakDao;

    @Autowired
    private CustGenerateFileDao custGenerateFileDao;

    //cpu核心数（获取到的cpu核心数不一定准确）
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void parseExcles(CustTableMatchDto matchDto) throws Exception {
        //创建线程池
        ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,new LinkedBlockingQueue<>(1000));

        //获取匹配参数
        String param = matchDto.getFlag();
        String userNo = matchDto.getUserNo();

        //查询源文件
        List<CustMatchTable> custMatchTables = custUgDao.queryMatchFile(param);

        //要查询多少源文件表我们就创建几个子线程
        int size = custMatchTables.size();
        CountDownLatch latch=new CountDownLatch(size);
        ConcurrentHashMap<String,List<CustUploadingFilesDO>> map=new ConcurrentHashMap<>();
        String targetFileName="";
        for (int i = 0; i <size; i++) {
            CustMatchTable custMatchTable = custMatchTables.get(i);
            String sourceFileName = custMatchTable.getSourceFileName();
            targetFileName = custMatchTable.getTargetFileName();

            Map<String,Object> uploadQueryMap=new HashMap<>();
            uploadQueryMap.put("fileName",sourceFileName);
            executor.execute(()->{
                List<CustUploadingFilesDO> list=new ArrayList<>();
                try{
                    list = custUploadingFilesDao.list(uploadQueryMap);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //任务结束，计数器减一
                    latch.countDown();
                    //把查询到的数据按照源文件的表名存到map里面去
                    map.put(sourceFileName,list);
                }
            });
        }
        //等待for循环里面的线程完成
        latch.await();

        //查询以哪个模块和哪个列为基准生成数据
        CustBchmakDO custBchmakDO = custBchmakDao.queryByFlag(param);

        //生成基准表的数据 先假设为A表为基准表C列为基准列
        ConcurrentHashMap<String,CustGenerateFileDO> tarGetDataMap=new ConcurrentHashMap<>();
        List<CustUploadingFilesDO> custUploadingFilesDOS = map.get(custBchmakDO.getTemplateTable());

        //多线程读取list，放在map里面
        multiThreadReadingList(custUploadingFilesDOS,tarGetDataMap,custBchmakDO,executor);

        //查询规则；
        Map<String,Object> custRuleQueryMap=new HashMap<>();
        custRuleQueryMap.put("flag",param);
        List<CustRuleDO> ruleList = custRuleDao.list(custRuleQueryMap);
        for (int i = 0; i < ruleList.size(); i++) {
            //规则对象
            CustRuleDO custRuleDO = ruleList.get(i);
            BuildParseExcleDataUtil.buildTargetData(custRuleDO,map,tarGetDataMap,custBchmakDO,executor);
        }

        //分批 批量入库
        int count=0;
        int mapSize = tarGetDataMap.size();
        int freq=mapSize/5000;
        boolean flag=true;

        List<CustGenerateFileDO> list=new ArrayList<>();
        Set<Map.Entry<String, CustGenerateFileDO>> entries = tarGetDataMap.entrySet();
        Iterator<Map.Entry<String, CustGenerateFileDO>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, CustGenerateFileDO> next = iterator.next();
            String key = next.getKey();
            CustGenerateFileDO value = next.getValue();//组装好的对象
            value.setCreationTime(new Date());
            value.setBenchmark(key);
            value.setFileName(targetFileName);
            value.setCreationUserNo(userNo);
            list.add(value);
            count++;

            if (mapSize>=5000&&freq!=0){
                if (count==5000){
                    custGenerateFileDao.batchInsert(list);
                    list.clear();
                    count=0;
                    freq--;
                }
            }else if (mapSize>=5000&&freq==0){
                flag=false;
                continue;
            }else{
                continue;
            }
        }

        //如果需要插入数据库的数据没满1000条，或则大于1000条但是不满整数 余下的list在外面insert
        if ((mapSize>0&&mapSize<5000)||!flag){
            custGenerateFileDao.batchInsert(list);
        }

        //任务完成关闭线程池
        executor.shutdown();
    }


    public  static  void multiThreadReadingList(List<CustUploadingFilesDO> list,ConcurrentHashMap<String,CustGenerateFileDO> map, CustBchmakDO custBchmakDO,ThreadPoolExecutor executor) throws Exception{
        //基准列
        String benchmarkClo = custBchmakDO.getBenchmarkClo().toLowerCase();
        //每一万条创建一个线程，
        int threadSize =10000 ;
        // 总数据条数
        int dataSize = list.size();
        // 理论线程数，如果这个数大于机器能创建线程数，
        // 则执行当前机器的线程数，其他子线程进入队列等待 队列长度为1000
        int threadNum = dataSize / threadSize + 1;
        // 定义标记,过滤threadNum为整数
        boolean special = dataSize % threadSize == 0;
        //定义总共的栅栏数，每个没打开一个表示该线程完成
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
            final List<CustUploadingFilesDO> listData= cutList;
            executor.execute(()->{
                try{
                    for (CustUploadingFilesDO filesDO: listData) {
                        CustGenerateFileDO fileDO=new CustGenerateFileDO();
                        String c = ReflectClass.reflect(filesDO,benchmarkClo);
                        map.put(c,fileDO);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    latch.countDown();
                }
            });
        }
        //等待所有的线程完成再往下面进行计算
        latch.await();
    }
}
