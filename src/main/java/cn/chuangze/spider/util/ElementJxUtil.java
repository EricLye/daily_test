package cn.chuangze.spider.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.chuangze.spider.entity.LawDetail;
import cn.chuangze.spider.entity.SkillsOption;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;

public class ElementJxUtil {
	
	Gson gson = new Gson();
	/**
	 * json 解析
	 * @param content_el
	 * @return
	 */
	public Map<String, Object> JsonAnalysis(Elements content_el){
		Map<String, Object> map = new HashMap<String, Object>();
		for (Element ele:content_el) {
			//	判断是否存在【
			
			if(ele.hasText()){
				
				//	存在
//				System.out.println(ele.text().toString());
				
//				
//				Map<Map<Object,Object>> secMap = JSONObject.parseObject(ele.text().toString());
				
				Elements ps = ele.select("p");
				
				List<LawDetail> chpList = new ArrayList<LawDetail>();
				List<LawDetail> articleList = new ArrayList<LawDetail>();
				
				//6-121
				String str = "";
				int chp = 0;
				int bar = 0;
				for(int i = 12;i <= 121;i ++){
					String orgContent = ps.get(i).text();
					String content = orgContent.replaceAll("[　*| *| *|//s*]*", "");
					if(content.contains("章")){
						chp ++;
//						System.out.println(chp + " : " + content);
						if(chp == 5){
							System.out.println(chp);
						}
						LawDetail chpDetail = new LawDetail();
						chpDetail.setTitleNum(getHead(content));
						chpDetail.setContent(getBody(content));
						chpDetail.setPid(chp);
						chpDetail.setLaw(39625);
//						chpDetail.setContent(content);
						chpDetail.setType(1);
						chpList.add(chpDetail);
					}else if(content.contains("条")){
						if(content.contains("项")){
							str += content;
						}else{
							if(str != null && str.length() != 0){
//								System.out.println(bar + " : " + str);
								
								LawDetail articleDetail = new LawDetail();
//								articleDetail.setContent(str);
								articleDetail.setTitleNum(getHead(str));
								articleDetail.setContent(getBody(str));
								articleDetail.setPid(chp);
								articleDetail.setType(3);
								articleDetail.setLaw(39625);
								articleList.add(articleDetail);
							}
							str = "";
							bar ++;
							str += content;
						}
					}else{
						str += content;
					}
				}
				
				map.put("chpList", chpList);
				map.put("articleList", articleList);
				
			}
		}
		return map;
	}
	
	private String getHead(String str){
		
		String head = "";
		
		int chpNo = str.indexOf("章");
		int articleNo = str.indexOf("条");
		if(chpNo > 0 && chpNo <= 5){
			head = str.substring(0, chpNo+1);
		}else{
			if(articleNo > 0 && articleNo <= 5){
				head = str.substring(0, articleNo+1);
			}
		}
		return head;
	}
	
	private String getBody(String str){
		
		String bodyStr = "";
		
		int chpNo = str.indexOf("章");
		int articleNo = str.indexOf("条");
		if(chpNo > 0 && chpNo <= 5){
			bodyStr = str.substring(chpNo + 1, str.length());
		}else{
			if(articleNo > 0 && articleNo <= 5){
				bodyStr = str.substring(articleNo + 1, str.length());
			}
		}
		return bodyStr;
	}
	
	
	public String addP(String content){
		
		return "<p>" + content + "</p>";
	}
	
	/**
	 * @describe 政务大厅 行政许可
	 * @author Y
	 * @param content_el
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> JsonAnalysisDetailXK(Elements content_el, int skillsId){
		Map<String, Object> map = new HashMap<String, Object>();
		List<SkillsOption> skillsOptions = new ArrayList<SkillsOption>();
		for (Element ele:content_el) {
			//	判断是否存在【
			if(ele.hasText()){
				//	存在
				 Map<String, Object> mapTypes = JSON.parseObject(ele.text());  
				 if(mapTypes!=null ){
					  // 遍历 jsonarray 数组，把每一个对象转成 json 对象  
					 /**公共窗口**/
					JSONArray itemInfoJsons = JSONArray.parseArray(mapTypes.get("ItemInfo").toString());  
					Map<String, Object> itemInfoMap = (Map<String, Object>) itemInfoJsons.get(0);
					
					Map<String, Object> window_Map = new HashMap<String, Object>();
					 //	实施主体
					window_Map.put("ORG_NAME", itemInfoMap.get("ORG_NAME")==null?"":itemInfoMap.get("ORG_NAME").toString());
					 //	承办机构
					window_Map.put("AGENT_NAME", itemInfoMap.get("AGENT_NAME")==null?"":itemInfoMap.get("AGENT_NAME").toString());
					 //	共同实施部门
					window_Map.put("ORG_NAME_ITEM", itemInfoMap.get("ORG_NAME_ITEM")==null?"无":itemInfoMap.get("ORG_NAME_ITEM").toString());
					 //	办理情况公开范围
					String ORG_DECIDE_CODE = "";
					if(itemInfoMap.get("ORG_DECIDE_CODE")==null) {
						ORG_DECIDE_CODE = "不公开";
					} else {
						ORG_DECIDE_CODE = "公开";
					}
					window_Map.put("ORG_DECIDE_CODE", ORG_DECIDE_CODE);
					 
					 //	法定时限
					window_Map.put("LAW_TIME", itemInfoMap.get("LAW_TIME")==null?"":itemInfoMap.get("LAW_TIME")+"个"+itemInfoMap.get("LAW_TIME_UNIT_VALUE"));
					 //	承诺期限
					window_Map.put("AGREE_TIME", itemInfoMap.get("AGREE_TIME")==null?"":itemInfoMap.get("AGREE_TIME")+"个"+itemInfoMap.get("AGREE_TIME"));

					 //	是否收费 IS_CHARGE
					window_Map.put("IS_CHARGE", itemInfoMap.get("IS_CHARGE")=="0"?"否":"是");
					 //	可在线申请 IS_ONLINE
					window_Map.put("IS_ONLINE", itemInfoMap.get("IS_CHARGE")=="0"?"否":"是");
					
					 //	*窗口信息
					Map<String, Object> windowMap = (Map<String, Object>) JSONArray.parseArray(mapTypes.get("window").toString()).get(0);
					 
					StringBuffer window_str = new StringBuffer();
				 	//	受理地点
				 	window_str.append("受理地点："+windowMap.get("ADDRESS")+"("+windowMap.get("NAME")+")<br>");
				 	//	咨询电话
				 	window_str.append("咨询电话："+windowMap.get("PHONE")+"<br>");
				 	//	投诉电话
				 	window_str.append("投诉电话："+windowMap.get("COMPLAINT_PHONE")+"<br>");
				 	//	受理时间
				 	window_str.append("受理时间："+windowMap.get("OFFICE_HOUR"));
				 	
					window_Map.put("WINDOW", window_str.toString());
					
					SkillsOption skillsOption_window = new SkillsOption();
					skillsOption_window.setSort(1);
					skillsOption_window.setContents(gson.toJson(window_Map));
					skillsOption_window.setSkill(skillsId);
					skillsOption_window.setName("业务基本信息");
					skillsOptions.add(skillsOption_window);
					
					 /**实施依据**/
				 	StringBuffer legalbasis_str = new StringBuffer();
				 	JSONArray legalbasijson = JSONArray.parseArray(mapTypes.get("legalbasis").toString());  
			
				 	if(legalbasijson!=null){
				 		for(int le=0; le<legalbasijson.size(); le++){
				 			Map<String, Object> legalbasiMap = (Map<String, Object>) legalbasijson.get(le);
				 			if(legalbasiMap.get("NAME")!=null && !legalbasiMap.get("NAME").toString().equals("null")) {
								legalbasis_str.append("<b>"+Integer.parseInt(legalbasiMap.get("SORT_ORDER").toString())+":"+legalbasiMap.get("NAME")+":</b>"+legalbasiMap.get("CONTENT") + "<br/>");
							} else {
								legalbasis_str.append("<b>"+Integer.parseInt(legalbasiMap.get("SORT_ORDER").toString())+":</b>"+legalbasiMap.get("CONTENT") + "<br/>");
							}
				 		}
				 	}
					 	
						
					SkillsOption skillsOption_legalbasis = new SkillsOption();
					skillsOption_legalbasis.setSort(2);
					skillsOption_legalbasis.setContents(legalbasis_str.toString());
					skillsOption_legalbasis.setSkill(skillsId);
					skillsOption_legalbasis.setName("实施依据");
					skillsOptions.add(skillsOption_legalbasis);
						
					 /**收费（征收）的标准及依据**/
					 JSONArray chargejson = JSONArray.parseArray(mapTypes.get("charge").toString());  
					 String charge_str = "";
					 if(chargejson.size()>0){
						StringBuffer charge = new StringBuffer();
				 		for(int c=0; c<chargejson.size(); c++){
				 			Map<String, Object> chargeMap = (Map<String, Object>) chargejson.get(c);
						 	charge.append("<b>"+chargeMap.get("SORT_ORDER")+"、费用名称：</b>"+chargeMap.get("NAME"));
						 	charge.append("<b>收费依据：</b>"+chargeMap.get("BASIS"));
						 	charge.append("<b>收费标准：</b>"+chargeMap.get("STANDARD")+"<br>");
				 		}
				 		charge_str = charge.toString();
					 }else{
						charge_str = "无";
					 }
					SkillsOption skillsOption_charge = new SkillsOption();
					skillsOption_charge.setSort(3);
					skillsOption_charge.setContents(charge_str);
					skillsOption_charge.setSkill(skillsId);
					skillsOption_charge.setName("收费的标准及依据");
					skillsOptions.add(skillsOption_charge);
					
					 /**受理条件**/
				 	StringBuffer condition_str = new StringBuffer();
				 	condition_str.append("<b>");
				 	JSONArray conditionjson = JSONArray.parseArray(mapTypes.get("condition").toString());  
				 	
					if(conditionjson!=null){
				 		for(int ci=0; ci<conditionjson.size(); ci++){
				 			Map<String, Object> conditionMap = (Map<String, Object>) conditionjson.get(ci);
				 			condition_str.append((ci+1)+":"+conditionMap.get("NAME").toString()+"<br>");
				 		}
				 	}
					condition_str.append("</b>");
					
					SkillsOption skillsOption_condition = new SkillsOption();
					skillsOption_condition.setSort(4);
					skillsOption_condition.setContents(condition_str.toString());
					skillsOption_condition.setSkill(skillsId);
					skillsOption_condition.setName("受理条件");
					skillsOptions.add(skillsOption_condition);
						
					 /**申报材料**/
				 	JSONArray materiasjson = JSONArray.parseArray(mapTypes.get("material").toString());  
				 	List<Map<String, Object>> materiasList = new ArrayList<Map<String,Object>>();
				 	
					if(materiasjson!=null){
				 		for(int mi=0; mi<materiasjson.size(); mi++){
				 			Map<String, Object> materiaMap = (Map<String, Object>) materiasjson.get(mi);
				 			
				 			Map<String, Object> materia_map = (Map<String, Object>) materiasjson.get(mi);
				 			//	材料名称
				 			materia_map.put("NAME", materiaMap.get("NAME").toString());
							//	材料形式
				 			StringBuffer publishersb = new StringBuffer();
				 			publishersb.append(materiaMap.get("PUBLISHER")+"&nbsp;&nbsp;");
				 			if(materiaMap.get("PUBLISHER")!=null && !materiaMap.get("PUBLISHER").equals("0")) {
								publishersb.append("原件"+materiaMap.get("ORIGIN")+"份");
							}
				 			if(materiaMap.get("COPY")!=null && !materiaMap.get("COPY").equals("0")) {
								publishersb.append("复印件"+materiaMap.get("COPY")+"份");
							}
				 			materia_map.put("PUBLISHER", publishersb.toString());
				 			
							//	材料必要性
				 			String must = "";
				 			if(materiaMap.get("MUST")!=null && materiaMap.get("MUST").equals("0")) {
								must = "否";
							} else if(materiaMap.get("MUST")!=null && materiaMap.get("MUST").equals("1")) {
								must = "是";
							} else if(materiaMap.get("MUST")!=null && materiaMap.get("MUST").equals("2")) {
								must = "容缺";
							} else {
								must = "";
							}
				 			materia_map.put("MUST", must);
							//	备注	REMARK
				 			materia_map.put("REMARK", materiaMap.get("REMARK")==null?"":materiaMap.get("REMARK").toString());
				 			//	图像地址
				 			materia_map.put("SAMPLE", materiaMap.get("SAMPLE")==null?"":materiaMap.get("SAMPLE").toString());
				 			materiasList.add(materia_map);
				 		}
				 	}

					SkillsOption skillsOption_material = new SkillsOption();
					skillsOption_material.setSort(5);
					skillsOption_material.setContents(gson.toJson(materiasList));
					skillsOption_material.setSkill(skillsId);
					skillsOption_material.setName("申报材料");
					skillsOptions.add(skillsOption_material);
					
					 /**办理流程**/
					JSONArray outmapjson = JSONArray.parseArray(mapTypes.get("outmap").toString());  
					List<Map<String,Object>> outmapList = new ArrayList<Map<String,Object>>();
					if(outmapjson!=null){
				 		for(int oi=0; oi<outmapjson.size(); oi++){
				 			Map<String, Object> outmapMap = (Map<String, Object>) outmapjson.get(oi);
				 			Map<String, Object> outmap_map = new HashMap<String, Object>();
				 			outmap_map.put("URL",  "http://58.59.41.173:8001/WebDiskServerDemo/doc?type=thumbnail&doc_id="+outmapMap.get("URL").toString());
				 			outmapList.add(outmap_map);
				 		}
					}else{
						Map<String, Object> outmap_map = new HashMap<String, Object>();
						outmap_map.put("URL", "无");
						outmapList.add(outmap_map);
					}
					SkillsOption skillsOption_outmap = new SkillsOption();
					skillsOption_outmap.setSort(6);
					skillsOption_outmap.setContents(gson.toJson(outmapList));
					skillsOption_outmap.setSkill(skillsId);
					skillsOption_outmap.setName("办理流程");
					skillsOptions.add(skillsOption_outmap);
					
					map.put("skillsOptions", skillsOptions);
				 }
			}
		}
		return map;
	}
	/**
	 * @describe 政务大厅  公共服务
	 * @author Y
	 * @param content_el
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> JsonAnalysisDetailGG(Elements content_el, int skillsId){
		Map<String, Object> map = new HashMap<String, Object>();
		List<SkillsOption> skillsOptions = new ArrayList<SkillsOption>();
		for (Element ele:content_el) {
			//	判断是否存在【
			if(ele.hasText()){
				//	存在
				 Map<String, Object> mapTypes = JSON.parseObject(ele.text());  
				 if(mapTypes!=null ){
					  // 遍历 jsonarray 数组，把每一个对象转成 json 对象  
					 /**公共窗口**/
					 JSONArray itemInfoJsons = JSONArray.parseArray(mapTypes.get("ItemInfo").toString());  
					 Map<String, Object> itemInfoMap = (Map<String, Object>) itemInfoJsons.get(0);
					 Map<String, Object> window_Map = new HashMap<String, Object>();
					 	//	实施主体
					 window_Map.put("ORG_NAME", itemInfoMap.get("ORG_NAME")==null?"":itemInfoMap.get("ORG_NAME").toString());
					 	//	实施主体性质	ORG_PROPERTY
					 window_Map.put("ORG_PROPERTY", itemInfoMap.get("ORG_PROPERTY")==null?"":itemInfoMap.get("ORG_PROPERTY").toString());
					 	//	联办机构
					 window_Map.put("ORG_NAME_ITEM", itemInfoMap.get("ORG_NAME_ITEM")==null?"":itemInfoMap.get("ORG_NAME_ITEM").toString());
					 	//	事项类型	ORG_DECIDE_EMAIL
					 window_Map.put("ORG_DECIDE_EMAIL", itemInfoMap.get("ORG_DECIDE_EMAIL")==null?"":itemInfoMap.get("ORG_DECIDE_EMAIL").toString());
					 	//	*服务对象
					 window_Map.put("TITLE_NAME", itemInfoMap.get("TITLE_NAME")==null?"":itemInfoMap.get("TITLE_NAME").toString());
					 	//	行使层级	AUTH_LEVEL	
					 String AUTH_LEVEL = "";
					 if (itemInfoMap.get("AUTH_LEVEL")!=null && itemInfoMap.get("AUTH_LEVEL").equals("1")) {
						 AUTH_LEVEL = "国家级";
					 } else if (itemInfoMap.get("AUTH_LEVEL")!=null && itemInfoMap.get("AUTH_LEVEL").equals("2")) {
						 AUTH_LEVEL = "省级";
					 } else if (itemInfoMap.get("AUTH_LEVEL")!=null && itemInfoMap.get("AUTH_LEVEL").equals("3")) {
						 AUTH_LEVEL = "市(州)级";
					 } else if (itemInfoMap.get("AUTH_LEVEL")!=null && itemInfoMap.get("AUTH_LEVEL").equals("4")) {
						 AUTH_LEVEL = "县(市、区)级";
					 } else if (itemInfoMap.get("AUTH_LEVEL")!=null && itemInfoMap.get("AUTH_LEVEL").equals("5")) {
						 AUTH_LEVEL = "乡(镇、街道)级";
					 }
					 window_Map.put("AUTH_LEVEL", AUTH_LEVEL);
					 
					 	//	权限划分	AUTHORITY_DIVISION
					 window_Map.put("AUTHORITY_DIVISION", itemInfoMap.get("AUTHORITY_DIVISION")==null?"":itemInfoMap.get("AUTHORITY_DIVISION").toString());
					 	//	运行系统	RUN_SYSTEM 1、国家	2、省级	3、市级
					 String RUN_SYSTEM = "";
					 if (itemInfoMap.get("RUN_SYSTEM")!=null && itemInfoMap.get("RUN_SYSTEM").equals("1")) {
						 RUN_SYSTEM = "国家级";
					 } else if (itemInfoMap.get("RUN_SYSTEM")!=null && itemInfoMap.get("RUN_SYSTEM").equals("2")) {
						 RUN_SYSTEM = "省级";
					 } else if (itemInfoMap.get("RUN_SYSTEM")!=null && itemInfoMap.get("RUN_SYSTEM").equals("3")) {
						 RUN_SYSTEM = "市级";
					 }
					 window_Map.put("RUN_SYSTEM", RUN_SYSTEM);
					 	//	数量限制	LIMIT_NUMBER
					 window_Map.put("LIMIT_NUMBER", itemInfoMap.get("LIMIT_NUMBER")==null?"":itemInfoMap.get("LIMIT_NUMBER").toString());
					 	//	法定办结时限	LAW_TIME+LAW_TIME_UNIT_VALUE
					 window_Map.put("LAW_TIME", itemInfoMap.get("LAW_TIME")==null?"0":itemInfoMap.get("LAW_TIME").toString()+"个"+itemInfoMap.get("LAW_TIME_UNIT_VALUE"));

					 	//	承诺办结期限	AGREE_TIME+AGREE_TIME_UNIT_VALUE
					 window_Map.put("AGREE_TIME", itemInfoMap.get("AGREE_TIME")==null?"0":itemInfoMap.get("AGREE_TIME").toString()+"个"+itemInfoMap.get("AGREE_TIME_UNIT_VALUE"));
					 	//	是否收费	IS_CHARGE
					 window_Map.put("IS_CHARGE", itemInfoMap.get("IS_CHARGE")=="0"?"否":"是");
					 	//	办件类型	ASSORT
					 String ASSORT = "";
					 if (itemInfoMap.get("ASSORT")!=null && itemInfoMap.get("ASSORT").equals("1")) {
						 ASSORT = "承诺件";
					 } else if (itemInfoMap.get("ASSORT")!=null && itemInfoMap.get("ASSORT").equals("2")) {
						 ASSORT = "即办件";
					 } else if (itemInfoMap.get("ASSORT")!=null && itemInfoMap.get("ASSORT").equals("3")) {
						 ASSORT = "上报件";
					 } else if (itemInfoMap.get("ASSORT")!=null && itemInfoMap.get("ASSORT").equals("4")) {
						 ASSORT = "联办件";
					 }
					 window_Map.put("ASSORT", ASSORT);
					 	//	通办范围
					 String cross = "";
					 if (itemInfoMap.get("CROSS_SCOPE")!=null && itemInfoMap.get("CROSS_SCOPE").equals("0")) {
						 cross = "未实现";
					 } else if (itemInfoMap.get("CROSS_SCOPE")!=null && itemInfoMap.get("CROSS_SCOPE").equals("1")) {
						 cross = "全国";
					 } else if (itemInfoMap.get("CROSS_SCOPE")!=null && itemInfoMap.get("CROSS_SCOPE").equals("2")) {
						 cross = "跨省";
					 } else if (itemInfoMap.get("CROSS_SCOPE")!=null && itemInfoMap.get("CROSS_SCOPE").equals("3")) {
						 cross = "跨市";
					 } else if (itemInfoMap.get("CROSS_SCOPE")!=null && itemInfoMap.get("CROSS_SCOPE").equals("4")) {
						 cross = "跨县";
					 } else {
						 cross = itemInfoMap.get("CROSS_SCOPE").toString();
					 }
					 window_Map.put("CROSS_SCOPE", cross);
					 	//	办理形式
					 String ONLINE = "";
					 if (itemInfoMap.get("IS_ONLINE")!=null && itemInfoMap.get("IS_ONLINE").equals("0")) {
						 ONLINE = "仅窗口办理";
					 } else if (itemInfoMap.get("IS_ONLINE")!=null && itemInfoMap.get("IS_ONLINE").equals("2")) {
						 ONLINE = "仅网上办理";
					 } else if (itemInfoMap.get("IS_ONLINE")!=null && itemInfoMap.get("IS_ONLINE").equals("3")) {
						 ONLINE = "窗口、网上均可办理";
					 }
					 window_Map.put("ONLINE", ONLINE);

					 	//	咨询电话	CONSULT_PHONE
					 window_Map.put("CONSULT_PHONE",  itemInfoMap.get("CONSULT_PHONE")==null?"":itemInfoMap.get("CONSULT_PHONE").toString());
					 	//	监督电话	CONSULT_EMAIL
					 window_Map.put("CONSULT_EMAIL",  itemInfoMap.get("CONSULT_EMAIL")==null?"":itemInfoMap.get("CONSULT_EMAIL").toString());
					 	//	网上支付	PAY_ONLINE 
					 window_Map.put("PAY_ONLINE",  itemInfoMap.get("PAY_ONLINE")=="0"?"否":"是");
					 	//	物流快递
					 window_Map.put("IS_DELIVERY",  itemInfoMap.get("IS_DELIVERY")=="0"?"否":"是");

					 	//	*受理地点、时间 
					Map<String, Object> windowMap = (Map<String, Object>) JSONArray.parseArray(mapTypes.get("window").toString()).get(0);
					
					StringBuffer window_str = new StringBuffer();
				 	//	受理地点
				 	window_str.append("受理地点："+windowMap.get("ADDRESS")+"("+windowMap.get("NAME")+")<br>");
				 	//	受理时间
				 	window_str.append("受理时间："+windowMap.get("OFFICE_HOUR"));
				 	
					window_Map.put("WINDOW", windowMap.toString());
					
					SkillsOption skillsOption_window = new SkillsOption();
					skillsOption_window.setSort(1);
					skillsOption_window.setContents(gson.toJson(window_Map));
					skillsOption_window.setSkill(skillsId);
					skillsOption_window.setName("业务基本信息");
					skillsOptions.add(skillsOption_window);
					
					 /**设立依据**/
				 	StringBuffer legalbasis_str = new StringBuffer();
				 	JSONArray legalbasijson = JSONArray.parseArray(mapTypes.get("legalbasis").toString());  
			
				 	if(legalbasijson!=null){
				 		for(int le=0; le<legalbasijson.size(); le++){
				 			Map<String, Object> legalbasiMap = (Map<String, Object>) legalbasijson.get(le);
				 			if(legalbasiMap.get("NAME")!=null && !legalbasiMap.get("NAME").toString().equals("null")) {
								legalbasis_str.append("<b>"+Integer.parseInt(legalbasiMap.get("SORT_ORDER").toString())+":"+legalbasiMap.get("NAME")+":</b>"+legalbasiMap.get("CONTENT") + "<br>");
							} else {
								legalbasis_str.append("<b>"+Integer.parseInt(legalbasiMap.get("SORT_ORDER").toString())+":</b>"+legalbasiMap.get("CONTENT") + "<br>");
							}
				 		}
				 	}
				 	SkillsOption skillsOption_legalbasis = new SkillsOption();
					skillsOption_legalbasis.setSort(2);
					skillsOption_legalbasis.setContents(legalbasis_str.toString());
					skillsOption_legalbasis.setSkill(skillsId);
					skillsOption_legalbasis.setName("实施依据");
					skillsOptions.add(skillsOption_legalbasis);
					
					 /**收费标准**/
					JSONArray chargejson = JSONArray.parseArray(mapTypes.get("charge").toString());
					String charge_str = "";
					if (chargejson.size() > 0) {
						StringBuffer charge = new StringBuffer();
						for (int c = 0; c < chargejson.size(); c++) {
							Map<String, Object> chargeMap = (Map<String, Object>) chargejson.get(c);
							charge.append("<b>" + chargeMap.get("SORT_ORDER") + "、费用名称：</b>" + chargeMap.get("NAME"));
							charge.append("<b>收费依据：</b>" + chargeMap.get("BASIS"));
							charge.append("<b>收费标准：</b>" + chargeMap.get("STANDARD") + "<br>");
						}
						charge_str = charge.toString();
					} else {
						charge_str = "无";
					}
					
					SkillsOption skillsOption_charge = new SkillsOption();
					skillsOption_charge.setSort(3);
					skillsOption_charge.setContents(charge_str);
					skillsOption_charge.setSkill(skillsId);
					skillsOption_charge.setName("收费的标准及依据");
					skillsOptions.add(skillsOption_charge);
					
					 /**受理条件**/
					StringBuffer condition_str = new StringBuffer();
				 	condition_str.append("<b>");
				 	JSONArray conditionjson = JSONArray.parseArray(mapTypes.get("condition").toString());  
				 	
					if(conditionjson!=null){
				 		for(int ci=0; ci<conditionjson.size(); ci++){
				 			Map<String, Object> conditionMap = (Map<String, Object>) conditionjson.get(ci);
				 			condition_str.append((ci+1)+":"+conditionMap.get("NAME").toString()+"<br>");
				 		}
				 	}
					condition_str.append("</b>");

					SkillsOption skillsOption_condition = new SkillsOption();
					skillsOption_condition.setSort(4);
					skillsOption_condition.setContents(condition_str.toString());
					skillsOption_condition.setSkill(skillsId);
					skillsOption_condition.setName("受理条件");
					skillsOptions.add(skillsOption_condition);
					
					 /**申请材料**/
					JSONArray materiasjson = JSONArray.parseArray(mapTypes.get("material").toString());  
					List<Map<String, Object>> materiasList = new ArrayList<Map<String,Object>>();
				 	
					if(materiasjson!=null){
				 		for(int mi=0; mi<materiasjson.size(); mi++){
				 			Map<String, Object> materiaMap = (Map<String, Object>) materiasjson.get(mi);
				 			
				 			Map<String, Object> materia_map = (Map<String, Object>) materiasjson.get(mi);
				 			//	材料名称
				 			materia_map.put("NAME", materiaMap.get("NAME").toString());
							//	材料形式
				 			StringBuffer publishersb = new StringBuffer();
				 			publishersb.append(materiaMap.get("PUBLISHER")+"&nbsp;&nbsp;");
				 			if(materiaMap.get("PUBLISHER")!=null && !materiaMap.get("PUBLISHER").equals("0")) {
								publishersb.append("原件"+materiaMap.get("ORIGIN")+"份");
							}
				 			if(materiaMap.get("COPY")!=null && !materiaMap.get("COPY").equals("0")) {
								publishersb.append("复印件"+materiaMap.get("COPY")+"份");
							}
				 			materia_map.put("PUBLISHER", publishersb.toString());
				 			
							//	材料必要性
				 			String must = "";
				 			if(materiaMap.get("MUST")!=null && materiaMap.get("MUST").equals("0")) {
								must = "否";
							} else if(materiaMap.get("MUST")!=null && materiaMap.get("MUST").equals("1")) {
								must = "是";
							} else if(materiaMap.get("MUST")!=null && materiaMap.get("MUST").equals("2")) {
								must = "容缺";
							} else {
								must = "";
							}
				 			materia_map.put("MUST", must);
							//	备注	REMARK
				 			materia_map.put("REMARK", materiaMap.get("REMARK")==null?"":materiaMap.get("REMARK").toString());
				 			//	图像地址
				 			materia_map.put("SAMPLE", materiaMap.get("SAMPLE")==null?"":materiaMap.get("SAMPLE").toString());
				 			materiasList.add(materia_map);
				 		}
				 	}

					SkillsOption skillsOption_material = new SkillsOption();
					skillsOption_material.setSort(5);
					skillsOption_material.setContents(gson.toJson(materiasList));
					skillsOption_material.setSkill(skillsId);
					skillsOption_material.setName("申报材料");
					skillsOptions.add(skillsOption_material);
					
					/**办理流程**/

					JSONArray outmapjson = JSONArray.parseArray(mapTypes.get("outmap").toString());  
					List<Map<String,Object>> outmapList = new ArrayList<Map<String,Object>>();
					if(outmapjson!=null){
				 		for(int oi=0; oi<outmapjson.size(); oi++){
				 			Map<String, Object> outmapMap = (Map<String, Object>) outmapjson.get(oi);
				 			Map<String, Object> outmap_map = new HashMap<String, Object>();
				 			if(outmapMap.get("URL")!=null) {
								outmap_map.put("URL",  "http://58.59.41.173:8001/WebDiskServerDemo/doc?type=thumbnail&doc_id="+outmapMap.get("URL").toString());
							} else {
								outmap_map.put("URL", "无");
							}
				 			outmapList.add(outmap_map);
				 		}
					}else{
						Map<String, Object> outmap_map = new HashMap<String, Object>();
						outmap_map.put("URL", "无");
						outmapList.add(outmap_map);
					}
		
					SkillsOption skillsOption_outmap = new SkillsOption();
					skillsOption_outmap.setSort(6);
					skillsOption_outmap.setContents(gson.toJson(outmapList));
					skillsOption_outmap.setSkill(skillsId);
					skillsOption_outmap.setName("办理流程");
					skillsOptions.add(skillsOption_outmap);
					
					/**结果名称/样本**/
					SkillsOption skillsOption_paper = new SkillsOption();
					skillsOption_paper.setSort(7);
					skillsOption_paper.setContents(itemInfoMap.get("PAPER")==null?"本事项无结果名称":itemInfoMap.get("PAPER").toString());
					skillsOption_paper.setSkill(skillsId);
					skillsOption_paper.setName("结果名称/样本");
					skillsOptions.add(skillsOption_paper);
					 
					/**行使内容	EXERCISE_CONTENT**/
					SkillsOption skillsOption_EXERCISE_CONTENT = new SkillsOption();
					skillsOption_EXERCISE_CONTENT.setSort(8);
					skillsOption_EXERCISE_CONTENT.setContents(itemInfoMap.get("EXERCISE_CONTENT")==null?"本事项无行使内容":itemInfoMap.get("EXERCISE_CONTENT").toString());
					skillsOption_EXERCISE_CONTENT.setSkill(skillsId);
					skillsOption_EXERCISE_CONTENT.setName("行使内容");
					skillsOptions.add(skillsOption_EXERCISE_CONTENT);
					 
					/**中介服务	AGENCYORGAN_INFO**/
					SkillsOption skillsOption_AGENCYORGAN_INFO = new SkillsOption();
					skillsOption_AGENCYORGAN_INFO.setSort(9);
					skillsOption_AGENCYORGAN_INFO.setContents(itemInfoMap.get("AGENCYORGAN_INFO")==null?"本事项无中介服务":itemInfoMap.get("AGENCYORGAN_INFO").toString());
					skillsOption_AGENCYORGAN_INFO.setSkill(skillsId);
					skillsOption_AGENCYORGAN_INFO.setName("中介服务");
					skillsOptions.add(skillsOption_AGENCYORGAN_INFO);
					 
					/**常见问题	COMMON_QUESTION**/
					SkillsOption skillsOption_COMMON_QUESTION = new SkillsOption();
					skillsOption_COMMON_QUESTION.setSort(10);
					skillsOption_COMMON_QUESTION.setContents(itemInfoMap.get("COMMON_QUESTION")==null?"暂无常见问题，如果您有问题，请点击左侧“我要咨询”按钮，或进入办事咨询栏目进行咨询。 ":itemInfoMap.get("COMMON_QUESTION").toString());
					skillsOption_COMMON_QUESTION.setSkill(skillsId);
					skillsOption_COMMON_QUESTION.setName("常见问题");
					skillsOptions.add(skillsOption_COMMON_QUESTION);
				 }
			}
		}
		map.put("skillsOptions", skillsOptions);
		return map;
	}
}
