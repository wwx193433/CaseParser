package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class CaseFactory {
	
	/**
	 * excel数据实例化
	 * @param excelDataList
	 * @return
	 */
	public static List<CaseBean> formatData(List<String[]> excelDataList){
		List<CaseBean> caseList = new ArrayList<CaseBean>();
		for(String[] fields:excelDataList) {
			CaseBean caseBean = new CaseBean();
			caseBean.setCaseId(fields[0]);
			caseBean.setName(fields[1]);
			caseBean.setNumber(fields[2]);
			caseBean.setStage(fields[3]);
			caseBean.setRound(fields[4]);
			caseBean.setTep(fields[5]);
			caseBean.setStartTime(fields[6]);
			caseBean.setEndTime(fields[7]);
			caseBean.setTimeCost(fields[8]);
			caseBean.setTestFea(fields[9]);
			caseBean.setResult(fields[10]);
			caseList.add(caseBean);
		}
		return caseList;
	}

}
