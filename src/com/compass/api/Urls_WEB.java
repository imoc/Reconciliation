package com.compass.api;

public class Urls_WEB {

	public static final String BASIC_URL = "http://schoollink.wodenet.net/school";
	// testHost
	// public static final String BASIC_URL_HOST =
	// "http://192.168.0.99:8080/school";//郭红涛
	// public static final String BASIC_URL_HOST
	// ="http://192.168.0.152:8080/school";//ylj
	// public static final String BASIC_URL_HOST =
	// "http://192.168.0.123:8080/school";//xl
	// public static final String BASIC_URL_HOST =
	// "http://192.168.0.26:8080/school";//lll
	//
	public static final String BASIC_URL_HEAD = BASIC_URL + "/upload/";
	// 类型
	public static final String TYPE_HONOR = "1";
	public static final String TYPE_NEWS = "2";
	public static final String PRAISE_TYPE_TEACHER = "1";
	public static final String PRAISE_TYPE_SHARE = "2";

	/*
	 * (3) 输入（2）得到几个数量的学校的学校编码
	 * http://192.168.0.152:8080/school/web/admin/Login/forgetPassword
	 * .do?contactMobile=13601236547&schoolNum=Test01,XY0001 &studentName=李天三
	 * 说明:schoolNum指学校编码:Test001指一个学校编码 ,XY01指另一个学校编码;多个学校编码中间用逗号分隔。
	 */

	public static final String FORGET_CODE_LIST_URL = BASIC_URL + "/web/admin"
			+ "/Login/forgetPassword.do?" + "ver=android" + "&studentName=%s"
			+ "&contactMobile=%s" + "&schoolNum=%s";
	/*
	 * (2) 忘记密码(根据学生名称和手机号查学校数量)URL:
	 * http://192.168.0.152:8080/school/web/admin/SchoolCommon
	 * /Map.do?SqlId=getSchoolNum&contactMobile=13601236547&studentName=李天三
	 */
	public static final String FORGET_PSW_URL = BASIC_URL + "/web/admin"
			+ "/SchoolCommon/Map.do?" + "ver=android" + "&SqlId=getSchoolNum"
			+ "&studentName=%s" + "&contactMobile=%s";
	/*
	 * ．用户进入学校获取学校及学生和联系人Id接口 (1).URL:
	 * http://192.168.0.152:8080/school/web/Common
	 * /Map.do?SqlId=getStuIdAndConIdByParentId
	 * &databaseName=edu2_test&parentStudentId
	 * =12&parentStudentContactId=18&parentSchoolId=3
	 * 说明:databaseName=edu2_test;进入某一数据库的数据库名称;
	 * parentStudentId=12为总数据库的学生Id;parentStudentContactId
	 * =18为总数据库的联系人Id;parentSchoolId为总数据库的学校Id;
	 */
	public static final String GET_SUB_ID_URL = BASIC_URL + "/web/Common"
			+ "/Map.do?" + "ver=android" + "&SqlId=getStuIdAndConIdByParentId"
			+ "&parentStudentId=%s" + "&parentStudentContactId=%s"
			+ "&parentSchoolId=%s" + "&databaseName=%s";

	/*
	 * (2) 学校二维码信息(同学校简介)URL:
	 * http://192.168.0.152:8080/school/web/Common/Map.do?
	 * SqlId=select_shoolInfor&databaseName=edu2_test 说明：schoolCode:学校编码;
	 * twoCodePath
	 * :二维码图片路径:需要加前缀如:http://192.168.0.152:8080/school/upload/201406071312.png;
	 */
	public static final String QRCODE_URL = BASIC_URL + "/web/Common"
			+ "/Map.do?" + "ver=android" + "&SqlId=select_shoolInfor"
			+ "&databaseName=%s";
	/*
	 * (2) 我的收藏详情:
	 * http://192.168.0.152:8080/school/web/admin/SchoolCommon/Map.do?
	 * SqlId=getMyArticleInforById &parentStudentContactId=18 &articleId=1
	 * 说明：parentStudentContactId=18表示联系人Id;
	 */
	public static final String MY_COLLECT_DETAILS_URL = BASIC_URL
			+ "/web/admin" + "/SchoolCommon/Map.do?" + "ver=android"
			+ "&SqlId=getMyArticleInforById" + "&articleId=%s"
			+ "&parentStudentContactId=%s";
	/*
	 * (2) 我的收藏URL:
	 * http://192.168.0.152:8080/school/web/admin/SchoolCommon/SearchDo.do?
	 * queryId=getMyArticleInfor &parentStudentContactId=18 &perPage=5&curPage=1
	 * &databaseName=edu2_test 说明：parentStudentContactId=18表示联系人Id;
	 */
	public static final String MY_COLLECT_URL = BASIC_URL + "/web/admin"
			+ "/SchoolCommon/SearchDo.do?" + "ver=android"
			+ "&queryId=getMyArticleInfor" + "&perPage=5" + "&curPage=%s"
			+ "&parentStudentContactId=%s";
	/*
	 * 分享回复
	 * 
	 * http://192.168.0.152:8080/school/web/Common/Add.do?SqlId=
	 * insert_edu_share_reply
	 * &eduShareId=1&replyType=1&replyContent=谢谢你啊！＆studentContactId
	 * ＝12＆personId＝12&databaseName=edu2_test 参数名：replyType
	 * (1表示老师评论;2家长评论;3表示老师回复老师
	 * ;4老师回复家长;5表示家长回复老师;6表示家长回复家长;7表示赞)；replyContent：评论或回复内容
	 * ；eduShareId：回复的分享Id; studentContactId:联系人;personId:老师Ｉd
	 */
	// --post请求 replyContent用namePair 同chartActivity
	// public static final String SHARE_REPLY_URL = BASIC_URL+ "/web/Common" +
	// "/Add.do?"
	// + "SqlId=insert_edu_share_reply" + "&replyType=%s"
	// + "&eduShareId=%s" + "&personId=%s" + "&studentContactId=%s"
	// + "&databaseName=%s";
	// http://schoollink.wodenet.net/school/web/SchoolShare/eduShareReplyAdd.do
	public static final String SHARE_REPLY_URL = BASIC_URL
			+ "/web/SchoolShare/eduShareReplyAdd.do?" + "ver=android"
			+ "&SqlId=insert_edu_share_reply" + "&replyType=%s"
			+ "&eduShareId=%s" + "&personId=%s" + "&studentContactId=%s"
			+ "&databaseName=%s";
	/*
	 * 分享赞
	 */
	public static final String SHARE_PRAISE_URL = BASIC_URL + "/web/Setting"
			+ "/setPraise.do?" + "ver=android" + "&type=2" + "&replyType=7"
			+ "&eduShareId=%s" + "&studentContactId=%s" + "&databaseName=%s";
	/*
	 * 分享收藏
	 * 
	 * http://192.168.0.152:8080/school/web/Setting/MyArticle.do?
	 * parentStudentContactId=18 &personName=plao &schoolId=2 &parentSchoolId=2
	 * &eduShareId=1 &databaseName=edu2_test
	 */
	public static final String SHARE_COLLECT_URL = BASIC_URL + "/web/Setting"
			+ "/MyArticle.do?" + "ver=android" + "&eduShareId=%s"
			+ "&personName=%s" + "&parentStudentContactId=%s" + "&schoolId=%s"
			+ "&parentSchoolId=%s" + "&databaseName=%s";

	/*
	 * 分享详情查询URL:
	 * http://localhost:8080/school/web/SchoolShare/Map.do?SqlId=getEduShareById
	 * &eduShareId=10&databaseName=edu2_test
	 */
	public static final String SHARE_DETAILS_URL = BASIC_URL
			+ "/web/SchoolShare" + "/Map.do?" + "ver=android"
			+ "&SqlId=getEduShareById" + "&eduShareId=%s" + "&databaseName=%s";
	/*
	 * 家校分享 http://192.168.0.152:8080/school/web/SchoolShare/List.do?SqlId=
	 * getEduShare_Infor&schoolId=2&perPage=5&curPage=1&databaseName=edu2_test
	 */
	public static final String SHARE_URL = BASIC_URL + "/web/SchoolShare"
			+ "/List.do?" + "ver=android" + "&SqlId=getEduShare_Infor"
			+ "&perPage=5" + "&curPage=%s" + "&studentId=%s" + "&schoolId=%s"
			+ "&databaseName=%s";
	/*
	 * 
	 * (4) 删除班级URL: http://192.168.0.152:8080/school/web/Common/Update.do?SqlId=
	 * update_studentClassStatus
	 * &classId=48&studentId=18&type=cur&databaseName=edu2_test
	 * 说明：删除的是班级type=cla; 班级参数为classId;删除课程时type=cur;课程参数为classId;
	 */
	public static final String DEL_CLASS_URL = BASIC_URL + "/web/Common"
			+ "/Update.do?" + "ver=android"
			+ "&SqlId=update_studentClassStatus" + "&type=%s" + "&classId=%s"
			+ "&studentId=%s" + "&databaseName=%s";
	/*
	 * (3) 添加班级或一对一课程URL:
	 * http://192.168.0.152:8080/school/web/Setting/setStudentClassForSchool
	 * ?schoolId
	 * =2&studentId=2&classId1=104&classId2=105&curriculumId1=4&curriculumId1
	 * =5&databaseName=edu2_tyshjy&databaseName1=edu2_tyxyjy
	 * 提交参数说明:schoolId:要加入的学校Id
	 * ;studentId:当前学生的Id;classId1表示添加一个班级;databaseName:原学校的数据库名称
	 * ;databaseName1:要加入的学校的数据库名称;此接口需要服务集成后有两个学校数据库才能做测试;
	 */
	public static final String ADD_CLASS_URL = BASIC_URL + "/web/Setting"
			+ "/setStudentClassForSchool.do?" + "ver=android"
			+ "&parentStudentId=%s" + "&schoolId=%s" + "&databaseName=%s"
			+ "%s";
	/*
	 * 请假/销假 http://192.168.0.99:8080/school/web/Course/studentAskForleave.do?
	 * studentClassTimeTableIds
	 * =1,2&studentLeaveStatueTableIds=0,1&studentId=21&studentConcatId
	 * =1&askForLeaveReason=%E7%97%85%E4%BA%86&databaseName=edu2_test
	 */
	public static final String ASK_URL = BASIC_URL + "/web/Course"
			+ "/studentAskForleave.do?" + "ver=android"
			+ "&studentClassTimeTableIds=%s" + "&studentLeaveStatueTableIds=%s"
			+ "&askForLeaveReason=%s" + "&studentId=%s" + "&studentConcatId=%s"
			+ "&datasourceFileName=%s" + "&databaseName=%s";
	/*
	 * 某月的课表情况
	 * http://192.168.0.99:8080/school/web/Common/List.do?SqlId=onemonthCourse
	 * &month
	 * =2014-06&studentId=21&schoolId=2&datasourceFileName=datasource1.xml&
	 * databaseName=edu2_test
	 */
	public static final String CURRICULUM_DAYSTATES_URL = BASIC_URL
			+ "/web/Common" + "/List.do?" + "ver=android"
			+ "&SqlId=onemonthCourse" + "&month=%s" + "&studentId=%s"
			+ "&schoolId=%s" + "&datasourceFileName=%s" + "&databaseName=%s";
	/*
	 * 本周课表
	 * http://192.168.0.99:8080/school/web/Common/List.do?SqlId=currentWeekCourse
	 * &current=week&studentId=21&schoolId=2&datasourceFileName=datasource1.xml&
	 * databaseName=edu2_test
	 */
	public static final String CURRICULUM_WEEK_URL = BASIC_URL + "/web/Common"
			+ "/List.do?" + "ver=android" + "&SqlId=currentWeekCourse"
			+ "&current=week" + "&studentId=%s" + "&schoolId=%s"
			+ "&datasourceFileName=%s" + "&databaseName=%s";
	/*
	 * 14某天的课表
	 * http://localhost:8080/school/web/Common/List.do?SqlId=currentWeekCourse
	 * &current
	 * =day&classDate=2014-06-25&studentId=21&schoolId=2&datasourceFileName
	 * =datasource1.xml&databaseName=edu2_test
	 */
	public static final String CURRICULUM_DAY_URL = BASIC_URL + "/web/Common"
			+ "/List.do?" + "ver=android" + "&SqlId=currentWeekCourse"
			+ "&current=day" + "&classDate=%s" + "&studentId=%s"
			+ "&schoolId=%s" + "&datasourceFileName=%s" + "&databaseName=%s";
	/*
	 * (3) 学校荣誉详情URL： http://192.168.0.152:8080/school/web/Common/Map.do?
	 * SqlId=getEduNews_ById&schoolHonorId=10&type=2&databaseName=edu2_test
	 * 说明:type=1为荣誉;type=2 为快讯; schoolHonorId:荣誉或快讯Id;
	 */
	public static final String DETAIL_HONOR_NEWS_URL = BASIC_URL
			+ "/web/Common" + "/Map.do?" + "ver=android"
			+ "&SqlId=getEduNews_ById" + "&type=%s" + "&schoolHonorId=%s"
			+ "&databaseName=%s";

	/*
	 * ④查看班级介绍
	 * http://192.168.0.121:8080/school/web/Common/List.do?SqlId=An_class_introduce
	 * &
	 * schoolId=1&classId=120&databaseName=edu2_test&datasourceFileName=datasource1
	 * .xml
	 */
	public static final String DETAIL_CLASS_URL = BASIC_URL + "/web/Common"
			+ "/Map.do?" + "ver=android" + "&SqlId=An_class_introduce"
			+ "&classId=%s" + "&schoolId=%s" + "&datasourceFileName=%s"
			+ "&databaseName=%s";
	/*
	 * 作业完成操作
	 * http://192.168.0.121:8080/web/Common/Update.do?SqlId=up_studentoper
	 * &isFinish
	 * =1&studentContactId=18&studentLearning=80&databaseName=edu2_test&
	 * datasourceFileName=datasource1.xml
	 */
	public static final String LEARNING_FININSH_URL = BASIC_URL + "/web/Common"
			+ "/Update.do?" + "ver=android" + "&SqlId=up_studentoper"
			+ "&isFinish=%s" + "&studentLearningId=%s" + "&studentId=%s"
			+ "&datasourceFileName=%s" + "&databaseName=%s";
	/**
	 * 学习情况 一、默认 按日期(按日期混排：)
	 * http://192.168.0.121:8080/school/web/Common/SearchDo
	 * .do?queryId=learning&schoolId
	 * =1&studentId=18&perPage=5&databaseName=edu2_test
	 * &datasourceFileName=datasource1.xml
	 */
	public static final String LEARNING_URL = BASIC_URL + "/web/Common"
			+ "/SearchDo.do?" + "ver=android" + "&queryId=learning"
			+ "&perPage=5" + "&curPage=%s" + "&studentId=%s" + "&classId=%s"
			+ "&classMode=%s" + "&schoolId=%s" + "&datasourceFileName=%s"
			+ "&databaseName=%s";
	/**
	 * 在读课程接口URL
	 */
	// http://192.168.0.152:8080/school/web/Setting/getCutofCurrOfClas.do?schoolId=2&studentId=21&datasourceFileName=datasource1.xml&databaseName=edu2_test
	public static final String GET_ADD_COURSE_URL = BASIC_URL + "/web/Setting"
			+ "/getCutofCurrOfClas.do?" + "ver=android" + "&schoolId=%s"
			+ "&studentId=%s" + "&datasourceFileName=%s" + "&databaseName=%s";
	/**
	 * 教师请求地址
	 */
	// http://192.168.0.152:8080/school/web/Common/SearchDo.do?queryId=select_teacherInfor&perPage=5&curPage=1&datasourceFileName=datasource1.xml&databaseName=edu2_test
	public static final String TOP_TEACHER_URL = BASIC_URL + "/web/Common"
			+ "/SearchDo.do?" + "ver=android" + "&queryId=select_teacherInfor"
			+ "&perPage=5" + "&curPage=%s" + "&datasourceFileName=%s"
			+ "&schoolId=%s" + "&databaseName=%s";
	/**
	 * 给教师回复请求地址 http://schoollink.wodenet.net/school/web/Common/List.do?SqlId=
	 * select_teacherComment&personId=18&databaseName=edu2_tyshjy
	 * 
	 */

	// http://192.168.0.152:8080/school/web/Common/SearchDo.do?queryId=select_teacherComment&personId=18&perPage=5&curPage=1&databaseName=edu2_test&datasourceFileName=datasource1.xml
	public static final String TEACHER_DETAILS_REPLY_URL = BASIC_URL
			+ "/web/Common" + "/List.do?" + "ver=android"
			+ "&SqlId=select_teacherComment" + "&personId=%s"
			+ "&datasourceFileName=%s" + "&databaseName=%s";
	// public static final String TEACHER_DETAILS_REPLY_URL = BASIC_URL+
	// "/web/Common"
	// + "/SearchDo.do?" + "queryId=select_teacherComment" + "&perPage=5"
	// + "&curPage=%s" + "&personId=%s" + "&datasourceFileName=%s"
	// + "&databaseName=%s";
	/**
	 * 给老师评论URL
	 */
	// http://localhost:8080/school/web/Common/Add.do?SqlId=insert_teacherComment&personId=2&content=这真的是一个很好的好老师哦，同学commentKind=1&showType=1&studentContactId=16&databaseName=edu2_test&datasourceFileName=datasource1.xml
	// post 原因同chatActivity回复
	public static final String TEACHER_COMMENT_URL = BASIC_URL + "/web/Common"
			+ "/Add.do?" + "ver=android" + "&SqlId=insert_teacherComment"
			+ "&personId=%s" + "&commentKind=%s" + "&showType=%s"
			+ "&studentContactId=%s" + "&datasourceFileName=%s"
			+ "&databaseName=%s";
	/**
	 * 点赞URL：
	 */
	// http://localhost:8080/school/web/Setting/setPraise.do?type=1&personId=2&studentContactId=18&commentKind=3&showType=1&databaseName=edu2_test&datasourceFileName=datasource1.xml
	public static final String PRAISE_URL = BASIC_URL + "/web/Setting"
			+ "/setPraise.do?" + "ver=android" + "&type=%s" + "&personId=%s"
			+ "&studentContactId=%s" + "&commentKind=3" + "&showType=1"
			+ "&datasourceFileName=%s" + "&databaseName=%s";
	/**
	 * 教师详情请求地址
	 */
	// http://192.168.0.152:8080/school/web/Common/Map.do?SqlId=select_teacherDetail&personId=18&databaseName=edu2_test&datasourceFileName=datasource1.xml
	public static final String TEACHER_DETAILS_URL = BASIC_URL + "/web/Common"
			+ "/Map.do?" + "ver=android" + "&SqlId=select_teacherDetail"
			+ "&personId=%s" + "&datasourceFileName=%s" + "&databaseName=%s";
	/**
	 * 查看班级介绍
	 */
	// http://192.168.0.121:8080/school/web/Common/List.do?SqlId=An_class_introduce&schoolId=1&classId=120&databaseName=edu2_test&datasourceFileName=datasource1.xml
	public static final String CLASS_INTRODUCE_URL = BASIC_URL + "/web/Common"
			+ "/List.do?" + "ver=android" + "&SqlId=An_class_introduce"
			+ "&classId=%s" + "&schoolId=%s" + "&datasourceFileName=%s"
			+ "&databaseName=%s";
	/**
	 * 课程请求地址
	 */
	// http://192.168.0.152:8080/school/web/Common/SearchDo.do?queryId=select_curriculumInfor&schoolId=2&perPage=5&curPage=1&databaseName=edu2_test&datasourceFileName=datasource1.xml
	public static final String TOP_COURSE_URL = BASIC_URL + "/web/Common"
			+ "/SearchDo.do?" + "ver=android"
			+ "&queryId=select_curriculumInfor" + "&perPage=5" + "&curPage=%s"
			+ "&schoolId=%s" + "&datasourceFileName=%s" + "&databaseName=%s";
	/**
	 * 课程详情教师请求地址
	 */
	// http://192.168.0.152:8080/school/web/Setting/getCurForTea.do?curriculumId=48&schoolId=2&databaseName=edu2_test&datasourceFileName=datasource1.xml
	public static final String COURSE_DETAILS_TEACHER_URL = BASIC_URL
			+ "/web/Setting" + "/getCurForTea.do?" + "ver=android"
			+ "&curriculumId=%s" + "&schoolId=%s" + "&datasourceFileName=%s"
			+ "&databaseName=%s";
	/**
	 * 课程详情时间请求地址
	 */
	// http://192.168.0.152:8080/school/web/Setting/getCurForClas.do?curriculumId=48&schoolId=2&databaseName=edu2_test&datasourceFileName=datasource1.xml
	public static final String COURSE_DETAILS_TIME_URL = BASIC_URL
			+ "/web/Setting" + "/getCurForClas.do?" + "ver=android"
			+ "&curriculumId=%s" + "&schoolId=%s" + "&datasourceFileName=%s"
			+ "&databaseName=%s";
	/**
	 * 其他校区地址/联系方式
	 */
	// http://192.168.0.152:8080/school/web/Common/List.do?SqlId=getSchool&databaseName=edu2_test
	public static final String ADDRESS_URL = BASIC_URL + "/web/Common"
			+ "/List.do?" + "ver=android" + "&SqlId=getSchool"
			+ "&databaseName=%s";
	/**
	 * 学校简介请求地址
	 */
	// http://192.168.0.152:8080/school/web/Common/Map.do?SqlId=select_shoolInfor&databaseName=edu2_test&datasourceFileName=datasource1.xml
	public static final String SHOOL_INFOR_URL = BASIC_URL + "/web/Common"
			+ "/Map.do?" + "ver=android" + "&SqlId=select_shoolInfor"
			+ "&datasourceFileName=%s" + "&databaseName=%s";

	/**
	 * 学校荣誉请求地址 type=1 学校快讯请求地址 type=2
	 */
	public static final String TOP_HONOR_URL = BASIC_URL + "/web/Common"
			+ "/SearchDo.do?" + "ver=android" + "&queryId=getEduNews_Infor"
			+ "&perPage=5" + "&type=%s" + "&curPage=%s"
			+ "&datasourceFileName=%s" + "&databaseName=%s";

	/**
	 * 
	 * 互联发送消息
	 */
	// public static final String SEND_MSG_URL =
	// "http://192.168.0.121:8080/school/web/SchoolContact/getMessageTouch.do?"
	// + "commonTouchId=%s"
	// + "&content=%s"
	// + "&replyObject=1"
	// + "&studentContactId=%s"
	// + "&personId=%s"
	// + "&replyType=%s"
	// + "&schoolId=%s"
	// + "&datasourceFileName=%s.xml"
	// + "&databaseName=%s";
	// + "&content=%s"用new BasicNameValuePair("content", msg);
	// post请求，因为content可能为"a   a",在url非法
	public static final String SEND_MSG_URL = BASIC_URL + "/web/SchoolContact"
			+ "/getMessageTouch.do?" + "ver=android" + "&commonTouchId=%s"
			+ "&replyObject=1" + "&studentContactId=%s" + "&personId=%s"
			+ "&replyType=%s" + "&schoolId=%s" + "&datasourceFileName=%s.xml"
			+ "&databaseName=%s";
	/**
	 * 课程预约详情
	 */
	public static final String COURSERE_COMMEND_DETAIlS = BASIC_URL
			+ "/web/Common" + "/List.do?" + "ver=android"
			+ "&SqlId=An_select_kctj" + "&studentContactId=%s" + "&schoolId=%s"
			+ "&courseRecommendId=%s" + "&databaseName=%s"
			+ "&datasourceFileName=%s";
	/**
	 * 课程预约操作
	 */
	public static final String COURSERE_COMMEND_ORDER = BASIC_URL
			+ "/web/Common" + "/Update.do?" + "ver=android" + "&SqlId=up_crd"
			+ "&studentContactId=%s" + "&schoolId=%s" + "&isReservation=%s"
			+ "&courseRecommendId=%s" + "&databaseName=%s"
			+ "&datasourceFileName=%s";
	/**
	 * 登录
	 */
	public static final String USER_LOGIN = BASIC_URL + "/web/admin"
			+ "/Login/clientLogin.do?" + "ver=android" + "&loginName=%s"
			+ "&password=%s" + "&pushUserId=%s"// 百度推送号
			+ "&pushChannelId=%s"// 频道号
			+ "&deviceType=3";// 设备类型-android
	/**
	 * 各学校报班数量
	 */
	public static final String CLASS_NUM = BASIC_URL + "/web/Setting"
			+ "/getStudentCenterSchoolClas.do?" + "ver=android"
			+ "&parentStudentId=%s" + "&parentStudentContactId=%s";// 设备号

	/**
	 * 1. 客户端版本检测:
	 * http://192.168.0.152:8080/school/web/admin/SchoolCommon/Map.do
	 * ?SqlId=getversion&type=1
	 */
	public static final String UP_VERSION_NEW = BASIC_URL
			+ "/web/admin/SchoolCommon/Map.do?" + "ver=android"
			+ "&SqlId=getversion&type=1" + "&verNum=%s";
	/**
	 * 注册
	 */
	public static final String USER_REGIST = BASIC_URL + "/regist?"
			+ "ver=android" + "&account=%s" + "&pwd=%s";

	/**
	 * 更新个人信息 http://192.168.0.152:8080/school/web/Setting/updateStudent.do?
	 * studentId
	 * =11&studentName=李天&sex=0&studentGradeId=2&schoolName=太原一中&className
	 * =104班&databaseName=edu2_test
	 */

	public static final String USER_UPDATA = BASIC_URL + "/web/Setting"
			+ "/updateStudent.do?" + "ver=android"
			+ "&parentStudentContactId=%s" + "&parentStudentId=%s";
	/**
	 * 更新密码] (1) 修改密码URL
	 * http://192.168.0.152:8080/school/web/admin/Login/updatePassword
	 * .do?type=1&
	 * old_password=123456789&new_password=123456&parentStudentContactId=1
	 * 说明：参数: parentStudentContactId = 1
	 * 登录时的学生联系的Id;type=1表示修改密码;type=2表示忘记密码得置密码;
	 */
	public static final String UPDATE_PSW = BASIC_URL + "/web/admin"
			+ "/Login/updatePassword.do?" + "ver=android" + "&type=%s"
			+ "&old_password=%s" + "&new_password=%s"
			+ "&parentStudentContactId=%s";

	/**
	 * 添加学校（二维码）
	 * http://192.168.0.152:8080/school/web/admin/SchoolCommon/List.do?
	 * SqlId=search_student_school&schoolNum=Test01
	 */
	public static final String ADD_SCHOOL_CODE_URL = BASIC_URL + "/web/admin"
			+ "/SchoolCommon/List.do?" + "ver=android"
			+ "&SqlId=search_student_school" + "&type=schoolId"
			+ "&schoolNum=%s";

	/**
	 * 添加学校（学校名）
	 */
	public static final String USER_ADDSCHOOL = BASIC_URL + "/web/admin"
			+ "/SchoolCommon/List.do?" + "ver=android"
			+ "&SqlId=search_student_school" + "&type=schoolId"
			+ "&schoolName=%s";
	/**
	 * 添加学校（添加咨询）
	 */
	public static final String ADD_CONSULTING = BASIC_URL + "/web/admin"
			+ "/SchoolCommon/Add.do?" + "ver=android" + "&SqlId=insertConsult"
			+ "&parentStudentContactId=%s" + "&parentSchoolId=%s";

	/**
	 * 头像上传
	 */
	// public static final String USER_UPLOAD = BASIC_URL + "/upload?";
	// public static final String USER_UPLOAD = BASIC_URL_HOST
	// +"/UploadHead?"
	// + "parentStudentContactId=%s&parentStudentId=%s";
	public static final String USER_UPLOAD = BASIC_URL + "/UploadHead?"
			+ "ver=android" + "&parentStudentContactId=%s&parentStudentId=%s";
	// /**
	// * 报错日志
	// */
	// public static final String DEBUG_URL = BASIC_URL_HOST
	// +"/Dedug?";
	/**
	 * 20.异常接口 (1)URL:
	 * http://schoollink.wodenet.net/school/web/admin/SchoolCommon
	 * /Add.do?SqlId=insertException
	 * &type=1&parentStudentContactId=1&exceptionContent=nullPotion
	 * 说明:type=1表示android; exceptionContent表示异常信息;
	 */
	public static final String DEBUG_URL = BASIC_URL
			+ "/web/admin/SchoolCommon/Add.do?" + "ver=android";
	/**
	 * 21.请假记录
	 * http://192.168.0.99:8080/school/web/Common/SearchDo.do?ver=android
	 * &queryId=askforleaverecord&schoolId=2&studentId=11&perPage=5&curPage=1&
	 * databaseName=edu2_tyfbsjy
	 */

	public static final String LEAVE_RECORDS_URL = BASIC_URL
			+ "/web/Common/SearchDo.do?" + "ver=android"
			+ "&queryId=askforleaverecord" + "&perPage=5" + "&curPage=%s"
			+ "&studentId=%s" + "&schoolId=%s" + "&databaseName=%s";
}
