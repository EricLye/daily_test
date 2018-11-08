package cn.chuangze.util;

/**
 * @describe 百度Unit2.0 开放接口  访问路径
 * @author Y
 * @date 2018年4月28日
 */
public class UnitPathUitl {
	//	请求路径
	public static String Unit_Path = "https://aip.baidubce.com/rpc/2.0/unit/";
	//	对话请求路径
	public static String DH_Path = "bot/chat";

	/******场景 bot 管理**************/
	//	获取bot列表
	public static String BOT_LIST = "bot/list";
	//	新增bot
	public static String BOT_ADD = "bot/add";
	//	修改bot
	public static String BOT_UPDATE = "bot/update";
	//	删除bot
	public static String BOT_DEL = "bot/delete";
	//	查询bot高级设置
	public static String GET_BOT_INFO = "setting/info";
	//	修改bot高级设置
	public static String UPDATE_BOT_INFO = "setting/update";
	
	/******技能 skill 管理**************/
	//	获取技能skill列表
	public static String SKILL_LIST = "skill/list";
	
	/******意图 intent 管理**************/
	//	获取意图 intent 列表
	public static String INTENT_LIST = "intent/list";
	//	查询意图详情
	public static String INTENT_INFO = "intent/info";
	//	意图添加
	public static String INTENT_ADD = "intent/add";
	//	意图修改
	public static String INTENT_UPDATE = "intent/update";
	//	意图删除
	public static String INTENT_DELETE = "intent/delete";
	
	/******词槽 slot 管理**************/
	//	获取词槽列表
	public static String SLOT_LIST = "slot/list";
	//	查询系统词槽列表
	public static String SYSSLOT_LIST = "sysSlot/list";
	//	查询自定义词槽词典详细信息
	public static String SLOT_VALUE = "slot/value";
	//	新建词槽
	public static String SLOT_ADD = "slot/add";
	//	修改词槽
	public static String SLOT_UPDATE = "slot/update";
	//	删除词槽
	public static String SLOT_DELETE = "slot/delete";
	
	/******模型 model 管理**************/
	//	获取模型列表
	public static String MODEL_LIST = "model/list";
	//	训练模型
	public static String MODEL_TRAIN = "model/train";
	//	删除模型
	public static String MODEL_DELETE = "model/delete";
	
	/******训练数据 管理**************/
	//	获取样本包列表
	public static String QUERYSET_LIST = "querySet/list";
	//	新增样本包
	public static String QUERYSET_ADD = "querySet/add";
	//	删除样本包
	public static String QUERYSET_DELETE = "querySet/delete";
	
	/******样本 管理**************/
	//	查询样本列表
	public static String QUERY_LIST = "query/list";
	//	查询样本详情
	public static String QUERY_INFO = "query/info";
	//	新增样本
	public static String QUERY_ADD = "query/add";
	//	修改样本
	public static String QUERY_UPDATE = "query/update";
	//	删除样本
	public static String QUERY_DELETE = "query/delete";
	
	/******样本 管理**************/
	//	获取样本包列表
	public static String PATTERN_SET_LIST = "patternSet/list";
	//	查询模板列表
	public static String PATTERN_LIST = "pattern/list";
	//	查询模板详情
	public static String PATTERN_INFO = "pattern/info";
	//	导入模板
	public static String PATTERN_IMPORT = "pattern/import";
	//	清空模板
	public static String PATTERN_CLEAR = "pattern/clear";

	/******特征词 管理**************/
	//	查询特征词列表
	public static String KEYWORD_LIST = "keyword/list";
	//	查询特征词详情
	public static String KEYWORD_VALUE = "keyword/value";
	//	新建特征词
	public static String KEYWORD_ADD = "keyword/add";
	//	更新特征词
	public static String KEYWORD_UPDATE = "keyword/update";
	//	删除特征词
	public static String KEYWORD_DELETE = "keyword/delete";
	
	/******问答  管理**************/
	//	问答对
	public static String FEQ_LIST = "feq/list";
	//	查询问答对明细
	public static String FEQ_INFO = "feq/info";
	//	新增问答对
	public static String FEQ_ADD = "feq/add";
	//	更新问答对
	public static String FEQ_UPDATE = "feq/update";
	//	删除问答对
	public static String FEQ_DELETE = "feq/delete";
	//	清空问答对
	public static String FEQ_CLEAR = "feq/clear";
	//	导入问答对
	public static String FEQ_IMPORT = "feq/import";
	
	/******通用部分**************/
	//	上传文件
	public static String FILE_UPLOAD = "file/upload";
	//	查询任务信息
	public static String JOB_INOF = "job/info";







}
