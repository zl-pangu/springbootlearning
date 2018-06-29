/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 5.7.22 : Database - quick_scheduler_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `tf_sys_job` */

DROP TABLE IF EXISTS `tf_sys_job`;

CREATE TABLE `tf_sys_job` (
  `JOB_ID` varchar(32) NOT NULL COMMENT '任务ID',
  `JOB_NAME` varchar(50) NOT NULL COMMENT '任务名称',
  `JOB_TYPE` int(1) NOT NULL DEFAULT '1' COMMENT '任务类型：1-普通任务',
  `JOB_CLASS` varchar(100) NOT NULL COMMENT '任务执行类',
  `JOB_PARAMS` text COMMENT '任务参数：标准JSON格式',
  `ALARM_TYPE` int(1) DEFAULT NULL COMMENT '告警方式： 1-邮件',
  `ALARM_RECEIVER` varchar(200) DEFAULT NULL COMMENT '告警接受对象',
  `USE_TAG` int(1) NOT NULL DEFAULT '1' COMMENT '有效标识：0-禁用，1-启用',
  `EXEC_TIME` bigint(14) NOT NULL COMMENT '任务执行时间：yyyyMMddHHmmss',
  `REMARK` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '任务信息的创建时间',
  `CREATE_BY` varchar(50) DEFAULT NULL COMMENT '任务信息的创建人ID',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '任务信息的更新时间：注意不是执行结果的更新时间',
  `UPDATE_BY` varchar(50) DEFAULT NULL COMMENT '任务信息的更新人ID',
  `SERVER_NODE` varchar(30) NOT NULL COMMENT '所在服务节点',
  PRIMARY KEY (`JOB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='一次性任务表：执行完立即删除';

/*Data for the table `tf_sys_job` */

insert  into `tf_sys_job`(`JOB_ID`,`JOB_NAME`,`JOB_TYPE`,`JOB_CLASS`,`JOB_PARAMS`,`ALARM_TYPE`,`ALARM_RECEIVER`,`USE_TAG`,`EXEC_TIME`,`REMARK`,`CREATE_TIME`,`CREATE_BY`,`UPDATE_TIME`,`UPDATE_BY`,`SERVER_NODE`) values 
('20001','一个单次执行的任务',1,'DemoTask','DemoTask',1,'lijiezhong@sf-express.com',1,20170809064138,NULL,NULL,NULL,NULL,NULL,'quickstart-demo');

/*Table structure for table `tf_sys_timer` */

DROP TABLE IF EXISTS `tf_sys_timer`;

CREATE TABLE `tf_sys_timer` (
  `JOB_ID` varchar(32) NOT NULL COMMENT '任务ID',
  `JOB_NAME` varchar(50) NOT NULL COMMENT '任务名称',
  `JOB_TYPE` int(1) NOT NULL DEFAULT '1' COMMENT '任务类型：1-普通任务',
  `JOB_CLASS` varchar(100) NOT NULL COMMENT '任务执行类',
  `JOB_PARAMS` varchar(600) DEFAULT NULL COMMENT '任务参数：标准JSON格式',
  `CRON_EXPRESSION` varchar(50) NOT NULL COMMENT '时间表达式：cron 表达式',
  `TMP_VARIABLE` varchar(100) DEFAULT NULL COMMENT '临时变量：用于存储过程中的一些数据',
  `EXPIRED_TAG` int(1) DEFAULT '0' COMMENT '过期标识：0-未过期，1-过期。一般由机器人调度修改',
  `ALARM_TYPE` int(1) DEFAULT NULL COMMENT '预警方式： 0-短信，1-邮件',
  `ALARM_RECEIVER` varchar(100) DEFAULT NULL COMMENT '预警接受对象',
  `USE_TAG` int(1) NOT NULL DEFAULT '1' COMMENT '有效标识：0-禁用，1-启用',
  `START_TIME` bigint(14) NOT NULL COMMENT '有效期开始时间：yyyyMMddHHmmss',
  `END_TIME` bigint(14) NOT NULL COMMENT '有效期结束时间：yyyyMMddHHmmss',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT '备注',
  `LAST_EXEC_TIME` varchar(14) DEFAULT NULL COMMENT '上次执行时间',
  `LAST_FINISH_TIME` varchar(14) DEFAULT NULL COMMENT '上次执行结束时间',
  `NEXT_EXEC_TIME` varchar(14) DEFAULT NULL COMMENT '下次执行时间',
  `EXCEPTION_TAG` int(1) DEFAULT '0' COMMENT '异常标识',
  `EXCEPTION_INFO` text COMMENT '异常信息',
  `RESULT_CODE` varchar(50) DEFAULT NULL COMMENT '上次执行结果信息执行结果编码',
  `RESULT_INFO` varchar(100) DEFAULT NULL COMMENT '上次执行结果信息',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '任务信息的创建时间',
  `CREATE_BY` varchar(50) DEFAULT NULL COMMENT '任务信息的创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '任务信息的更新时间：注意不是执行结果的更新时间',
  `UPDATE_BY` varchar(50) DEFAULT NULL COMMENT '任务信息的更新人',
  `SERVER_NODE` varchar(30) DEFAULT NULL COMMENT '所在服务节点',
  `EXECUTING` int(1) DEFAULT NULL COMMENT '是否执行中0-未执行，1-执行中，2-执行完成',
  PRIMARY KEY (`JOB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务表';

/*Data for the table `tf_sys_timer` */

insert  into `tf_sys_timer`(`JOB_ID`,`JOB_NAME`,`JOB_TYPE`,`JOB_CLASS`,`JOB_PARAMS`,`CRON_EXPRESSION`,`TMP_VARIABLE`,`EXPIRED_TAG`,`ALARM_TYPE`,`ALARM_RECEIVER`,`USE_TAG`,`START_TIME`,`END_TIME`,`REMARK`,`LAST_EXEC_TIME`,`LAST_FINISH_TIME`,`NEXT_EXEC_TIME`,`EXCEPTION_TAG`,`EXCEPTION_INFO`,`RESULT_CODE`,`RESULT_INFO`,`CREATE_TIME`,`CREATE_BY`,`UPDATE_TIME`,`UPDATE_BY`,`SERVER_NODE`,`EXECUTING`) values 
('10001','一个周期性执行的任务',1,'DemoTask','{\"userName\":\"顺丰科技\",\"age\":\"20\"}','0 0/1 * * * ?',NULL,0,1,'lijiezhong@sf-express.com',1,20170809064138,20501231235959,NULL,'20180625165600','20180625165600','20180625165700',0,'','0','任务执行成功','2017-08-09 18:42:11','','2017-08-22 18:59:18','','quickstart-demo',2);

/*Table structure for table `tl_sys_job` */

DROP TABLE IF EXISTS `tl_sys_job`;

CREATE TABLE `tl_sys_job` (
  `JOB_ID` varchar(32) DEFAULT NULL COMMENT '任务ID',
  `JOB_NAME` varchar(50) DEFAULT NULL COMMENT '任务名称',
  `JOB_TYPE` int(1) DEFAULT '1' COMMENT '任务类型：1-普通任务',
  `JOB_CLASS` varchar(100) DEFAULT NULL COMMENT '任务执行类',
  `JOB_PARAMS` text COMMENT '任务参数：标准JSON格式',
  `ALARM_TYPE` int(1) DEFAULT NULL COMMENT '告警方式： 1-邮件',
  `ALARM_RECEIVER` varchar(200) DEFAULT NULL COMMENT '告警接受对象',
  `USE_TAG` int(1) DEFAULT '1' COMMENT '有效标识：0-禁用，1-启用',
  `EXEC_TIME` bigint(14) DEFAULT NULL COMMENT '任务执行时间：yyyyMMddHHmmss',
  `REMARK` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '任务信息的创建时间',
  `CREATE_BY` varchar(50) DEFAULT NULL COMMENT '任务信息的创建人ID',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '任务信息的更新时间：注意不是执行结果的更新时间',
  `UPDATE_BY` varchar(50) DEFAULT NULL COMMENT '任务信息的更新人ID',
  `SERVER_NODE` varchar(30) DEFAULT NULL COMMENT '所在服务节点',
  `LAST_EXEC_TIME` bigint(14) DEFAULT NULL COMMENT '上次执行时间',
  `LAST_FINISH_TIME` bigint(14) DEFAULT NULL COMMENT '上次执行结束时间',
  `EXCEPTION_TAG` int(1) DEFAULT '0' COMMENT '异常标识',
  `EXCEPTION_INFO` text COMMENT '异常信息',
  `RESULT_CODE` varchar(50) DEFAULT NULL COMMENT '上次执行结果信息执行结果编码',
  `RESULT_INFO` varchar(200) DEFAULT NULL COMMENT '上次执行结果信息',
  `T_CREATE_TIME` datetime DEFAULT NULL COMMENT '日志记录时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='一次性任务执行日志表';

/*Data for the table `tl_sys_job` */

/*Table structure for table `tl_sys_timer` */

DROP TABLE IF EXISTS `tl_sys_timer`;

CREATE TABLE `tl_sys_timer` (
  `JOB_ID` varchar(32) DEFAULT NULL COMMENT '任务ID',
  `JOB_NAME` varchar(50) DEFAULT NULL COMMENT '任务名称',
  `JOB_TYPE` int(1) DEFAULT '1' COMMENT '任务类型：1-普通任务',
  `JOB_CLASS` varchar(100) DEFAULT NULL COMMENT '任务执行类',
  `JOB_PARAMS` varchar(600) DEFAULT NULL COMMENT '任务参数：标准JSON格式',
  `CRON_EXPRESSION` varchar(50) DEFAULT NULL COMMENT '时间表达式：cron 表达式',
  `TMP_VARIABLE` varchar(100) DEFAULT NULL COMMENT '临时变量：用于存储过程中的一些数据',
  `EXPIRED_TAG` int(1) DEFAULT '0' COMMENT '过期标识：0-未过期，1-过期。一般由机器人调度修改',
  `ALARM_TYPE` int(1) DEFAULT NULL COMMENT '预警方式： 0-短信，1-邮件',
  `ALARM_RECEIVER` varchar(100) DEFAULT NULL COMMENT '预警接受对象',
  `USE_TAG` int(1) DEFAULT '1' COMMENT '有效标识：0-禁用，1-启用',
  `START_TIME` bigint(14) DEFAULT NULL COMMENT '有效期开始时间：yyyyMMddHHmmss',
  `END_TIME` bigint(14) DEFAULT NULL COMMENT '有效期结束时间：yyyyMMddHHmmss',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT '备注',
  `LAST_EXEC_TIME` varchar(14) DEFAULT NULL COMMENT '上次执行时间',
  `LAST_FINISH_TIME` varchar(14) DEFAULT NULL COMMENT '上次执行结束时间',
  `NEXT_EXEC_TIME` varchar(14) DEFAULT NULL COMMENT '下次执行时间',
  `EXCEPTION_TAG` int(1) DEFAULT '0' COMMENT '异常标识',
  `EXCEPTION_INFO` text COMMENT '异常信息',
  `RESULT_CODE` varchar(50) DEFAULT NULL COMMENT '上次执行结果信息执行结果编码',
  `RESULT_INFO` varchar(100) DEFAULT NULL COMMENT '上次执行结果信息',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '任务信息的创建时间',
  `CREATE_BY` varchar(50) DEFAULT NULL COMMENT '任务信息的创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '任务信息的更新时间：注意不是执行结果的更新时间',
  `UPDATE_BY` varchar(50) DEFAULT NULL COMMENT '任务信息的更新人',
  `SERVER_NODE` varchar(30) DEFAULT NULL COMMENT '所在服务节点',
  `EXECUTING` int(1) DEFAULT NULL COMMENT '是否执行中0-未执行，1-执行中，2-执行完成',
  `T_CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务执行历史表';

/*Data for the table `tl_sys_timer` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
