package com.compass.api;


/**
 * @author daodao（dao。dev@qq.com）
 *
 */
public class Urls {

	//测试环境
//	public static final String HOST = "http://192.168.0.172:8080";
	//实际环境
	public static final String HOST = "http://jhauto.gicp.net";
	
	public static final String BASIC = HOST+"/accountclient/app";

	/**
	 * 登录
	 * 
	 * http://jhauto.gicp.net/accountclient/api/login.action?openid=4011034326&passwd=123&userna=sxthydzsgcyxgs&token=123
	 * 
	 *  http://jhauto.gicp.net/accountclient/api/login.action?c=00140011055&USERNA=pdxczj&PASSWD=123&TOKEN=123
	 */
	public static final String USER_LOGIN =BASIC 
			+"/login.action?"
			+ "&TOKEN=123"
			+ "&USERNA=%s"
			+ "&OPENID=%s"
			+ "&PASSWD=%s"
			;
	/**
	 * 获取待办信息
	 */
	public static final String GET_BACKLOG_INFORS_URL = BASIC 
			+ "/task.action?"
			+ "SIGNATURE=%s"
			;
	/**
	 * 获取对应查询类型下的账号集合
	 * 
	 * 四，请求某种类型账号列表
请求方式：post
WebService/app/requestAccLi.action? signature= Signature &type=hqsize
//
http://jhauto.gicp.net/accountclient/app/requestAccLi.action?signature=123&type=hqsize
--------
{"data":[{"ACCOUNT":"401103010300000054328"}],"errcode":0}
	 */
	public static final String GET_ACCOUNTS_URL = BASIC 
			+ "/app/requestAccLi.action?"
			+ "SIGNATURE=%s"
			+ "&TYPE=%s"
			 ;
	/**
	 * 对账操作（相符、不相符）
	 * 六，对一个账号对账操作
请求方式：post
webService/app/accUpdate.action?signature=123&account=401103010300000054328&accs=null&dates=2014-10-31&clientcheck=1&type=0
http://jhauto.gicp.net/accountclient/app/accUpdate.action?signature=123&account=401103010300000054328&accs=null&dates=2014-10-31&clientcheck=1&CHECKE=1&type=hqsize
----------------------
{"errcode":0}
	 * 
	 */
	public static final String OPERATE_URL = BASIC 
			+ "/accUpdate.action?"
			+ "SIGNATURE=%s"
			+ "&ACCOUNT=%s"
			+ "&ACCS=null"
			+ "&DATES=%s"
			+ "&CLIENTCHECK=%s"
			+ "&CHECKE=%s"
			+ "&TYPE=0"
			 ;
	/**
	 三，请求待办任务列表

请求方式：post
WebService/app/requestTask.action?signature=signature&type=hqsize
Type有3种可选bzjsize，hqsize，dqsize
//
http://jhauto.gicp.net/accountclient/app/requestTask.action?Signature=123&type=hqsize
----------
{"data":[{"PASSED":"0","ACCOUNT":"401103010300000054328","CLINAME":"山西太行永达装饰工程有限公司","DATES":"2014-06-30","LASTTIME":"2014-06-

21T00:00:00","BALANCE":148351.88,"EXITEMNO":"2001","CLIENTCHECK":"0","TYPE":"1","CHECKE":null,"ATTR":null,"ACCS":null},

{"PASSED":"0","ACCOUNT":"401103010300000054328","CLINAME":"山西太行永达装饰工程有限公司","DATES":"2014-04-30","LASTTIME":"2014-04-

30T00:00:00","BALANCE":29045377.82,"EXITEMNO":"2001","CLIENTCHECK":"0","TYPE":"1","CHECKE":null,"ATTR":null,"ACCS":null},

{"PASSED":"0","ACCOUNT":"401103010300000054328","CLINAME":"山西太行永达装饰工程有限公司","DATES":"2014-07-31","LASTTIME":"2014-07-

31T00:00:00","BALANCE":201352.09,"EXITEMNO":"2001","CLIENTCHECK":"0","TYPE":"1","CHECKE":null,"ATTR":null,"ACCS":null},

{"PASSED":"0","ACCOUNT":"401103010300000054328","CLINAME":"山西太行永达装饰工程有限公司","DATES":"2014-03-31","LASTTIME":"2014-03-

25T00:00:00","BALANCE":53048.3,"EXITEMNO":"2001","CLIENTCHECK":"0","TYPE":"1","CHECKE":null,"ATTR":null,"ACCS":null},

{"PASSED":"0","ACCOUNT":"401103010300000054328","CLINAME":"山西太行永达装饰工程有限公司","DATES":"2014-05-31","LASTTIME":"2014-05-

21T00:00:00","BALANCE":1597897.63,"EXITEMNO":"2001","CLIENTCHECK":"0","TYPE":"1","CHECKE":null,"ATTR":null,"ACCS":null}],"errcode":0
	 * 
	 */
	public static final String REQUEST_TASK_URL = BASIC 
			+ "/requestTask.action?"
			+ "SIGNATURE=%s"
			+ "&TYPE=%s"
			;
	/**
	 *四，请求某种类型账号列表
请求方式：post

http://jhauto.gicp.net/accountclient/app/accDetail.action?signature=123&account=401103010300000054328&accs=&sdate=2014-09-01&edate=2014-09-30&type=0
-----
{"data":[{"PASSED":"0","ACCOUNT":"401103010300000054328","CLINAME":"山西太行永达装饰工程有限公司","DATES":"2014-09-30","LASTTIME":"2014-09-

29T00:00:00","BALANCE":141026.45,"EXITEMNO":"2001","CLIENTCHECK":"1","TYPE":"1","CHECKE":null,"ATTR":null,"ACCS":null}],"errcode":0}


ISCHECKED //1.已对账 0，未对账 
	 */
	public static final String ACC_DETAIL_URL = BASIC 
			+ "/accDetail.action?"
			+ "SIGNATURE=%s"
			+ "&ACCOUNT=%s"
			+ "&ACCS="
			+ "&SDATE=%s"
			+ "&EDATE=%s"
			+ "&ISCHECKED=%s"
			+ "&TYPE=1"
			;
	/**
	 *7，发送验证短信
请求方式
http://jhauto.gicp.net/accountclient/app/remindcheckagin.action?SIGNATURE=123
正常返回
{"errcode":0}
失败返回
{"errmsg":"send failed ","errcode":"1"}

	 */
	public static final String GET_SMS_AUTH_CODE = BASIC 
			+ "/remindcheckagin.action?"
			+ "SIGNATURE=%s"
			;
	/**
	 *8，验证短信
请求方式
 http://jhauto.gicp.net/accountclient/app/remindcheck.action?SIGNATURE=123&MSG=123
参数说明
参数	是否必须	说明
SIGNATURE	是	系统签名
MSG	是	6位验证码
正常返回
{"errcode":0}
失败返回
{"errmsg":"send failed ","errcode":"1"}

	 */
	public static final String SUBMIT_AUTH_CODE = BASIC 
			+ "/remindcheck.action?"
			+ "SIGNATURE=%s"
			+ "&MSG=%s"
			;
	/**
	9，查询一个账号的流水
请求方式：post
webService/app/getAccbookAccount.action?SIGNATURE=SIGNATURE&account=account&startDate=startDate&endDate=endDate&type=type
参数说明
参数	是否必须	说明
SIGNATURE	是	系统签名
ACCOUNT	是	账号
Accs	可为空	分账号
StartDate	是	开始日期
endDate	是	结束日期
Type	可为空	类型
正常返回
{"data":[{name:value,name:value,...},{}，{}..]},"errcode":0}
参数说明
参数	说明
Errcode	状态码
D31TXDT	记账日期
D31TXRMKS	内容摘要
D31VOUN	凭证号
D31ACCS	借方发生额
D31AMT	贷方发生额
D31ATUNTT	交易机构
D31TELID	操作员号
D31BALE	余额
D31CHKTEL	
ACCS	


{"DATA":[{"D31ACCS":15000,"D31AMT":0,"D31BALE":-15000,"D31VOUN":"20141004947302","D31TXDT":"2014-09-05","D31ATUNTT":"401103","D31TXRMKS":"函校办公费","D31TELID":"420362","D31CHKTEL":null,"ACCS":null},{"D31ACCS":0,"D31AMT":15000,"D31BALE":0,"D31VOUN":null,"D31TXDT":"2014-09-05","D31ATUNTT":"401103","D31TXRMKS":"贷清算资金","D31TELID":"420495","D31CHKTEL":null,"ACCS":null},{"D31ACCS":10000,"D31AMT":0,"D31BALE":-10000,"D31VOUN":"20141004947303","D31TXDT":"2014-09-16","D31ATUNTT":"401103","D31TXRMKS":"办公费","D31TELID":"420362","D31CHKTEL":null,"ACCS":null},{"D31ACCS":0,"D31AMT":10000,"D31BALE":0,"D31VOUN":null,"D31TXDT":"2014-09-16","D31ATUNTT":"401103","D31TXRMKS":"贷清算资金","D31TELID":"420362","D31CHKTEL":null,"ACCS":null}],"ERRCODE":0}

	 */
	
	public static final String Detailed_Reconciliation = BASIC 
			+ "/getAccbookAccount.action?"
			+ "SIGNATURE=%s"
			+ "&ACCOUNT=%s"
			+ "&STARTDATE=%s"
			+ "&ENDDATE=%s"
			+ "&TYPE=0"
			;
	/**
	12，查询一个账号的未达账务列表
此方法可以查询一个账号某段时间内的未达账务情况，账号不分类型
请求方式post
webService/app/allnotTransit.action?SIGNATURE=SIGNATURE&account=account&startdate=startdate&enddate=enddate
http://jhauto.gicp.net/accountclient/app/allnotTransit.action?SIGNATURE=123&ACCOUNT=401103010300000019467&STARTDATE=2014-09-01&ENDDATE=2014-09-30
参数	是否必须	说明
SIGNATURE	是	系统签名
ACCOUNT	是	账号
STARTDATE	是	开始日期
ENDDATE	是	结束日期
正常返回
参数说明
参数	说明
ERRCODE	状态码
MESSAGEID	通知编号
ACCOUNT	账号
CLINAME	单位名
DATES	生成对账单日
RESTORECONTENT	附言
SIGN	是否下通知（1已下，0未下）
STATES	状态（0只查看，1已查复，2未查复）
PROJECTBALANCE	单位账户余额
BALANCE	余额
ACCOUNTSIGN	默认为0

	 */
	public static final String ALL_NOT_TRANSIT_LIST = BASIC 
			+ "/allnotTransit.action?"
			+ "SIGNATURE=%s"
			+ "&ACCOUNT=%s"
			+ "&STARTDATE=%s"
			+ "&ENDDATE=%s"
			;
	/**
	13，请求一个客户的所有账号
在登录成功时，此方法可以返回当前客户的所有账号
请求方式
http://localhost:8080/accountclient/app/getAccountAll.action?SIGNATURE=123
正常返回
参数	说明
ERRCODE	状态码
DATA	数组
	 */
	public static final String ACCOUNT_ALL = BASIC 
			+ "/getAccountAll.action?"
			+ "SIGNATURE=%s"
			;
	/**
	 14，修改用户别名
修改在线用户的别名
请求方式post
http://localhost:8080/accountclient/app/updUserNa.action?SIGNATURE=123&USERNAME=USERNAME
参数说明
参数	是否必须	说明
SIGNATURE	是	系统签名
USERNAME	是	修改的用户名
USERNAME 需要验证为数字和字母组合
	 */
	public static final String UPD_USER_NA_URL = BASIC 
			+ "/updUserNa.action?"
			+ "SIGNATURE=%s"
			+ "&USERNAME=%s"
			;
	
	/**
	 * 15，修改密码
修改在线用户的密码
请求方式post
http://localhost:8080/accountclient/app/updUserPasswd.action?SIGNATURE=123&PASSWORD=123&UPASSWORD=123
参数说明
参数	是否必须	说明
SIGNATURE	是	系统签名
PASSWORD	是	原密码
UPASSWORD	是	修改后的密码
密码需要数字和字母结合并且大于6位
正常返回
{"ERRMSG":"ReLogin","ERRCODE":"1"}
	 */
	public static final String UPD_USER_PASSWD_URL = BASIC 
			+ "/updUserPasswd.action?"
			+ "SIGNATURE=%s"
			+ "&PASSWORD=%s"
			+ "&UPASSWORD=%s"
			;
	/**
	 * 16，对账单回执授权列表
取得授权列表，用于双人对账时
请求方式
http://jhauto.gicp.net/accountclient/app/accredit.action?SIGNATURE=123
	 */
	public static final String Receipt_Authorization_List = BASIC 
			+ "/accredit.action?"
			+ "SIGNATURE=%s"
			;
	/**
	 * 1. 客户端版本检测:
	 * http://192.168.0.152:8080/school/web/admin/SchoolCommon/Map.do
	 * ?SqlId=getversion&type=1
	 */
	public static final String UP_VERSION_NEW = BASIC
			// public static final String UP_VERSION_NEW = BASIC_TEST_URL
			+ "/web/admin/SchoolCommon/Map.do?" + "ver=android"
			+ "&SqlId=getversion&type=1" + "&verNum=%s";

	public static final String DEBUG_URL = BASIC
			+ "/web/admin/SchoolCommon/Add.do?" + "ver=android";
	/**
	 * 下载帮助文档
	 */
	public static final String HELP_DOC = HOST + "/accountclient/images/help.doc";
	
}
