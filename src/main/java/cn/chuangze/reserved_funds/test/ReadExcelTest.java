package cn.chuangze.reserved_funds.test;

import cn.chuangze.reserved_funds.dao.QuestionAnswerDao;
import cn.chuangze.reserved_funds.module.QuestionAnswer;
import cn.chuangze.util.ExportExcel;
import com.google.gson.GsonBuilder;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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
		//解析excel
		List<Map<String,Object>> list = readExcel("D://公积金中心问答.xls");
		for (Map map:list){
			QuestionAnswer questionAnswer = questionAnswerDao.getById((Integer) map.get("id"));
			map.put("rawQuestion",questionAnswer.getQuestion());
		}
		System.out.println(list.size() + " : " + new GsonBuilder().create().toJson(list));
//		excelOut(list);
		questionAnswerDao.add(list);
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
//			int cellCount = row.getLastCellNum();
			//得到Excel工作表指定行的单元格
//			HSSFCell cell = row.getCell(0);
//			int id = (int) cell.getNumericCellValue();
			int pId = (int) row.getCell(0).getNumericCellValue();
			String question = row.getCell(1).getStringCellValue();
			String answer = row.getCell(2).getStringCellValue();
			resultMap.put("id",pId);
			resultMap.put("question",question);
			resultMap.put("answer",answer);
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
		String fileName = "公积金中心问答测试";
		//	列数量
		int columnNumber = 3;
		//	列宽度
		int[] columnWidth = { 10,30,100 };
		//	表头
		String[] columnName = { "p_id","rowQuestion","answer" };
		//	内容
		String[][] dataList = new String[list.size()][3];
		for (int i = 0;i < list.size();i ++){
			Map map = list.get(i);
			try {
				dataList[i][0] = map.get("id").toString();
				dataList[i][1] = (String) map.get("rowQuestion");
				dataList[i][2] = (String) map.get("answer");
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
