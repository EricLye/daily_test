package cn.chuangze.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitUtil {
	
	/**
	 * @describe 2.0返回结果解析
	 * @author Y
	 * @param result
	 * @结构 result +
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static Map<String, Object> loadResults2(String result) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!CommonUtility.isEmpty(result)) {
			// 解析结果
			Map<String,Object> jsonObject=JSON.parseObject(result);
			int error_code= Integer.parseInt(jsonObject.get("error_code").toString());	//错误码，为 0 时表示成功
			if(error_code==0){
				//请求成功
				Map<String,Object> objMap=(Map<String, Object>) jsonObject.get("result");	//总结果
				String bot_id = objMap.get("bot_id").toString();
				Map<String,Object> response=(Map<String, Object>) objMap.get("response");	//本轮应答体
				 
				List<Map<String,Object>> action_list=(List<Map<String, Object>>) response.get("action_list");	//动作列表
				Map<String,Object> actionObj=action_list.get(0);
				//String type=(String) actionObj.get("type");		//当前回话状态 clarify： 澄清 satisfy： 满足 guide： 引导 faqguide： faq引导
				String act_type = actionObj.get("type").toString();	//当前回话状态 clarify： 澄清 satisfy： 满足 guide： 引导 faqguide： faq引导
				String say = actionObj.get("say").toString();
				
				String action_id = actionObj.get("action_id").toString();	//当前动作id
				Map<String,Object> schema=(Map<String, Object>) response.get("schema");	//解析的 schema，解析意图、词槽结果都从这里面获取
				String intent=schema.get("intent").toString();	//意图名称
				List<Map<String,Object>> slotsList=(List<Map<String, Object>>) schema.get("slots");	//词槽列表
				//组织数据返回
				String bot_session = objMap.get("bot_session").toString();	//session 信息（多轮会话标识）
				//解析bot_session
				Map<String,Object> bigSession=JSON.parseObject(bot_session);
				String session_id = bigSession.get("session_id").toString();
				
				String intent_confidence = schema.get("intent_confidence").toString();	//意图置信度
				Map<String,Object> parameter=new HashMap<String,Object>();
				if(slotsList!=null && slotsList.size()>0){
					for(Map<String,Object> obj:slotsList){
						if(obj==null || obj.size()<=0){
							continue;
						}
						Map<String,Object> objMaps=new HashMap<String,Object>();
						objMaps.put("name", obj.get("name"));
						objMaps.put("original_word", obj.get("original_word"));
						// String name=obj.get("name")+"";
						objMaps.put("normalized_word", CommonUtility.isEmpty(obj.get("normalized_word"))?"":obj.get("normalized_word"));
						parameter.put(obj.get("name").toString(), objMaps);	//解析后的词槽内容列表						   
					}
				}
				map.put("parameter", parameter);
				map.put("act_type", act_type);
				
				map.put("say", say);
				map.put("action_id", action_id);
				map.put("intent", intent);
				map.put("bot_session", bot_session);
				map.put("intent_confidence", intent_confidence);
				map.put("bot_id", bot_id);
				map.put("sessionId", session_id);
				map.put("slots", slotsList);
				
				if (!CommonUtility.isEmpty(say) && say.equals("我不知道应该怎么答复您。")) {
					// 未查询到任何数据
					map.put("isResult", "0"); // 是否匹配到数据 0否 1是
				} else {
					map.put("isResult", "1"); // 是否匹配到数据 0否 1是
				}
				
			}
		}
		return map;
	}

	
	
	/**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.f147784b986c023c7e417d964b164ea8.2592000.1519353534.282335-10625975"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
    
   /**
    * 请求语义处理api
    * @param accessToken 请求签名
    * @param params 参数json集
    * @return
    */
    public static String utterance(String accessToken, String params) {
        // 请求URL
        String talkUrl = "https://aip.baidubce.com/rpc/2.0/unit/v1/";
        try {
            String result = HttpUtil.post(talkUrl, accessToken, "application/json", params);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析返回结果
     */
    public static List<Map<String, Object>> loadResult(String result){
 	   List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
 	   if(!CommonUtility.isEmpty(result)){
 		   Map<String, Object> map = new HashMap<String, Object>();
 		   // 解析结果
 		   Gson gson = new Gson();
 		   // 总结果
 		   List<Map<String,Object>> list = gson.fromJson("["+result+"]", new TypeToken<List<Map<String,Object>>>(){}.getType());
 		   // 查询出错
 		   if(!CommonUtility.isEmpty(list.get(0).get("error_code"))){
 			   map.put("isResult", "0");
		   }else{
			   // 返回信息
	 		   String resultDeatil = gson.toJson(list.get(0).get("result"));
	 		   List<Map<String,Object>> list1 = gson.fromJson("["+resultDeatil+"]", new TypeToken<List<Map<String,Object>>>(){}.getType());
	 		   // 请求sessionId
	 		   String sessionId = String.valueOf(list1.get(0).get("session_id"));
	 		   // 返回语句或函数名
	 		   String actionList = gson.toJson(list1.get(0).get("action_list"));
	 		   List<Map<String,Object>> list2 = gson.fromJson(actionList, new TypeToken<List<Map<String,Object>>>(){}.getType());
	 		   // 获取所说的话 
	 		   String say = String.valueOf(list2.get(0).get("say"));
	 		   if(!CommonUtility.isEmpty(say) && say.equals("我不知道应该怎么答复您。")){
	 			   // 未查询到任何数据
	 			   map.put("isResult", "0");   // 是否匹配到数据 0否  1是
	 		   }else{
	 			   // 获取所调用的函数名
	 	 		   String main_exe = String.valueOf(list2.get(0).get("main_exe"));
	 	 		   List<Map<String, Object>> valueList = new ArrayList<Map<String,Object>>();
	 	 		   // 获取词槽信息
	 			   String schema = gson.toJson(list1.get(0).get("schema"));
	 			   List<Map<String,Object>> list3 = gson.fromJson("["+schema+"]", new TypeToken<List<Map<String,Object>>>(){}.getType());
	 	 		   // 获取当前意图名称
	 			   String currentQuIntent = String.valueOf(list3.get(0).get("current_qu_intent"));
	 			   if(!CommonUtility.isEmpty(main_exe)){
	 	 			   // 获取词槽填充信息
	 	 			   String bot_merged_slots = gson.toJson(list3.get(0).get("bot_merged_slots"));
	 	 			   List<Map<String,Object>> list4 = gson.fromJson(bot_merged_slots, new TypeToken<List<Map<String,Object>>>(){}.getType());
	 	 			   if(!CommonUtility.isEmpty(list4)){
	 	 				   for(Map<String,Object> value : list4){
	 	 					   Map<String,Object> vMap = new HashMap<String, Object>();
	 	 					   vMap.put("name", value.get("type"));
	 	 					   vMap.put("originalWord", value.get("original_word"));
	 	 					   vMap.put("normalizedWord", value.get("normalized_word"));
	 	 					   vMap.put(value.get("type")+"", value.get("normalized_word"));	//当前词槽内容
	 	 					   valueList.add(vMap);
	 	 				   }
	 	 			   }
	 	 		   }
	 			   
	 			   // 组装返回信息
	 			   map.put("isResult", "1");   // 是否匹配到数据 0否  1是
	 			   map.put("currentQuIntent", currentQuIntent);
	 	 		   map.put("sessionId", sessionId);
	 	 		   map.put("say", say);
	 	 		   map.put("mainExe", main_exe);
	 	 		   map.put("valueList", valueList);
	 		   }
		   }
 		   returnList.add(map);
 	   }
 	   return returnList;
    }
    
    /**
     * 解析json字符串返回结果
     * @param result 百度unit返回的结果集
     * @return
     */
   public static Map<String,Object> getResult(String result){
	   if(CommonUtility.isEmpty(result)){
		   return null;
	   }
	   Map<String,Object> map=new HashMap<String,Object>();
	   Map<String,Object> objMap=JSON.parseObject(result);
	   if(objMap!=null && objMap.size()>0){
		   int error_code=CommonUtility.isEmpty(objMap.get("error_code"))?0: Integer.parseInt(objMap.get("error_code")+"");
		   if(error_code>0){
			   //百度unit返回错误
			   return null;
		   }
		   Map<String,Object> resultMap=(Map<String, Object>) objMap.get("result");
		   if(resultMap==null && resultMap.size()>0){
			   return null;
		   }
		   map.put("session_id",resultMap.get("session_id"));	//多轮会话ID
		   Map<String,Object> schema=(Map<String, Object>) resultMap.get("schema");	//词槽解析结果集
		   if(schema!=null && schema.size()>0){
			   map.put("current_qu_intent", schema.get("current_qu_intent"));	//当前意图
			   map.put("intent_confidence", schema.get("intent_confidence"));	//意图置信度
			   List<Map<String,Object>> bot_merged_slots=(List<Map<String, Object>>) schema.get("bot_merged_slots");
			   if(bot_merged_slots!=null && bot_merged_slots.size()>0){
				   List<Map<String,Object>> parameterList=new ArrayList<Map<String,Object>>();
				   for(Map<String,Object> obj:bot_merged_slots){
					   if(obj==null || obj.size()<=0){
						   continue;
					   }
					   Map<String,Object> parameter=new HashMap<String,Object>();
					   parameter.put("type", obj.get("type"));
					   parameter.put("original_word", obj.get("original_word"));
					   parameter.put("normalized_word", CommonUtility.isEmpty(obj.get("normalized_word"))?"":obj.get("normalized_word"));
					   parameterList.add(parameter);
					   
				   }
				   map.put("parameterList", parameterList);	//解析后的词槽内容列表
			   }
		   }
		   
		   List<Map<String,Object>> action_list=(List<Map<String, Object>>) resultMap.get("action_list");
		   if(action_list!=null && action_list.size()>0){
			   Map<String,Object> actionObj=action_list.get(0);
			   map.put("action_id", actionObj.get("action_id"));
			   Map<String,Object> action_type=(Map<String, Object>) actionObj.get("action_type");
			   map.put("act_type", action_type.get("act_type"));
			   map.put("act_target_detail", action_type.get("act_target_detail"));
			   map.put("say", actionObj.get("say"));
		   }
		   
		   //获得当前匹配到的当前词槽信息
		   Map<String,Object> qu_res=(Map<String, Object>) resultMap.get("qu_res");		//bot解析的结果
		   List<Map<String,Object>> intent_candidates=(qu_res==null || qu_res.size()<=0)?null:(List<Map<String, Object>>) qu_res.get("intent_candidates");	//意图候选项
		   Map<String,Object> intentCandidatesObj=(intent_candidates==null || intent_candidates.size()<=0)?null:intent_candidates.get(0);
		   List<Map<String,Object>> slots=(intentCandidatesObj==null || intentCandidatesObj.size()<=0)?null:(List<Map<String, Object>>) intentCandidatesObj.get("slots");
		   Map<String,Object> nowObj=(slots==null || slots.size()<=0)?null:slots.get(0);
		   if(nowObj!=null && nowObj.size()>0){
			   map.put("nowType", nowObj.get("type"));	//当前回话改变的词槽名称
			   map.put("nowNormalizedWord", CommonUtility.isEmpty(nowObj.get("normalized_word"))?"":nowObj.get("normalized_word"));	//当前词槽归一后的值
			   map.put("nowOriginalWord", CommonUtility.isEmpty(nowObj.get("original_word"))?"":nowObj.get("original_word"));	//当前词槽归一后的值
		   }
		   
		   
		   
		   
	   }
	   
	return map;
   }
}
