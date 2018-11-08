package cn.chuangze.rzLib.test;

import cn.chuangze.reserved_funds.dao.QuestionAnswerDao;
import cn.chuangze.util.ExportExcel;
import cn.chuangze.util.HttpUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiZG
 * @version 1.0
 * @date 2018/10/13 8:51
 */
public class ReadExcelTest {
	private QuestionAnswerDao questionAnswerDao = new QuestionAnswerDao();
	@Test
	public void execute() throws Exception{
		Map<String,Object> resultMap = new HashMap<>(10);
		List<Map<String,Object>> resultList = new ArrayList<>();
		//解析excel
		List<Map<String,Object>> list = readExcel("D://rzLib-question.xls");
		for (Map map:list) {
			String code = "003001004210aa160ca9";
			String result = HttpUtil.getServerResponse(code,map.get("question").toString());
			map.put("answer",result);
			JSONObject jsonObject = new JSONObject(result);
            int flag = jsonObject.getInt("flag");
            if (flag != 0){
            	resultMap.put("flag",101);
				resultMap.put("msg","返回结果错误");
				resultMap.put("question",map.get("question"));
				resultMap.put("answer",result);
				resultList.add(resultMap);
				System.out.println("返回结果错误");
				System.out.println("question : " + map.get("question"));
				System.out.println("answer : " + result);
				System.out.println();
			}else {
//				JSONObject skillList = jsonObject.getJSONObject("skillList");
				JSONArray skillList = jsonObject.getJSONArray("skillList");
				if (skillList != null && skillList.length() != 0){
					resultMap.put("flag",102);
					resultMap.put("msg","返回多个结果");
					resultMap.put("question",map.get("question"));
					resultMap.put("answer",result);
					resultList.add(resultMap);
					System.out.println("返回多个结果");
					System.out.println("question : " + map.get("question"));
					System.out.println("answer : " + result);
					System.out.println();
				}
			}
		}
		System.out.println("检查结束");

		excelOut(resultList);
//		questionAnswerDao.add(list);
	}

	/**
		解析excel
	 */
	public List<Map<String,Object>> readExcel(String fileNameIncludePath) throws Exception{


		List<Map<String,Object>> list = new ArrayList<>(100);

		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileNameIncludePath));
		//得到Excel工作簿对象
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		//取得sheet的数目
		wb.getNumberOfSheets();
		//得到Excel工作表对象
		HSSFSheet sheet = wb.getSheetAt(0);
		//取得有效的行数
		int rowCount = sheet.getLastRowNum();
		for (int i = 1; i <= rowCount; i ++){
			Map<String,Object> resultMap = new HashMap<>(3);
			//得到Excel工作表的行
			HSSFRow row = sheet.getRow(i);
			//取得一行的有效单元格个数
			int cellCount = row.getLastCellNum();
			//得到Excel工作表指定行的单元格
//			HSSFCell cell = row.getCell(0);
//			int id = (int) cell.getNumericCellValue();

			String question = "";
			if (cellCount > 1) {
				String key = row.getCell(0).getStringCellValue();
				String key1 = row.getCell(1).getStringCellValue();
//			String answer = row.getCell(2).getStringCellValue();

				//组合question
				if (!"".equals(key)) {
					if (key.contains(",")) {
						String[] keys = key.split(",");
						key = keys[0];
					}
				}
				if (!"".equals(key1) && key1.contains(",")) {
					String[] key1s = key1.split(",");
					key1 = key1s[0];
				}
				question = key + key1;
			}else{
				String key = row.getCell(0).getStringCellValue();
				if (!"".equals(key) && key.contains(",")) {
					String[] keys = key.split(",");
					key = keys[0];
				}
				question = key;
			}
			resultMap.put("question",question);
//			resultMap.put("key1",key1);
//			resultMap.put("answer",answer);
			list.add(resultMap);
		}
		return list;
	}
	/**
	 * 导出excel
	 */
	private void excelOut(List<Map<String ,Object>> list) throws Exception{
		String sheetName = "基础问答";
		//
		String titleName = "基础问答";
		//	文件名称
		String fileName = "一楼机器人问答一";
		//	列数量
		int columnNumber = 4;
		//	列宽度
		int[] columnWidth = {30,30,30,100 };
		//	表头
		String[] columnName = { "flag","msg","question","answer"};
		//	内容
		String[][] dataList = new String[list.size()][4];
		for (int i = 0;i < list.size();i ++){
			Map map = list.get(i);
			try {
				dataList[i][0] = map.get("flag").toString();
				dataList[i][1] = (String) map.get("msg");
				dataList[i][2] = map.get("question").toString();
				dataList[i][3] = (String) map.get("answer");
			}catch (Exception e){
				System.out.println(map.get("question") + "=========" + map.get("answer"));
				e.printStackTrace();
			}
		}

		/*
		 * 注：columnNumber、columnWidth、columnName，长度一定要一致
		 */

		//	本地测试文件生成路径
		String filePath = "D:\\";

		File file = new File(filePath+fileName);
		if (file.exists()){
			file.delete();
		}

		new ExportExcel().ExportNoResponse(sheetName, titleName, fileName,

				columnNumber, columnWidth, columnName, dataList, filePath);
	}
}
