package com.huawei;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLFactory {

	public static final String SOURCE_FILE = "resources/data.xls";
	public static final String XML_TEMPLATE = "resources/case.xml";

	/**
	 * 解析为xml
	 * 
	 * @param caseBean
	 */
	private void parse(CaseBean caseBean) {
		int blockState = 0;// 是否阻塞
		String status = "";// 是否通过
		try {
			// 读取已存在的Xml文件person.xml
			Document doc = new SAXReader().read(new File(XML_TEMPLATE));

			// 获取根节点
			Element root = doc.getRootElement();

			// 获取case节点
			Element caseNode = (Element) doc.selectSingleNode("/root/suite/case");
			// case节点 id赋值
			Attribute caseIdAttr = caseNode.attribute("id");
			caseIdAttr.setValue(caseBean.getCaseId());
			// case节点 name赋值
			Attribute caseNameAttr = caseNode.attribute("name");
			caseNameAttr.setValue(caseBean.getName());
			// case节点 times赋值
			Attribute caseTimesAttr = caseNode.attribute("times");
			caseTimesAttr.setValue(caseBean.getRound());
			// case节点 IP赋值
			Attribute caseIpAttr = caseNode.attribute("ip");
			caseIpAttr.setValue(caseBean.getIp());

			// result节点 info赋值
			String result = caseBean.getResult();
			switch (result.toLowerCase()) {
			case "pass":
			case "passed":
				status = "Passed";
				break;
			case "fail":
			case "failed":
				status = "Failed";
				break;
			default:
				blockState = 1;
				break;
			}
			Element resultNode = caseNode.element("result");
			setNodeValue(resultNode, "info", status);

			// result节点 times赋值
			setNodeValue(resultNode, "times", caseBean.getRound());

			// detail节点 文本赋值
			String formatCostTime = new Util().formatDuration(caseBean.getStartTime(), caseBean.getEndTime());
			Element detailNode = resultNode.element("detail");
			detailNode.setText("{" + caseBean.getTimeCost() + "sec} StartTime: " + caseBean.getStartTime() + " "
					+ caseBean.getEndTime() + ",Last: " + formatCostTime);

			// checkpoint节点
			Element checkPointNode = (Element) resultNode.selectSingleNode("checkpoints/checkpoint");
			setNodeValue(checkPointNode, "info", status);

			// summary节点
			Element summaryNode = root.element("summary");
			// summary block属性
			setNodeValue(summaryNode, "block", String.valueOf(blockState));
			// summary startTime属性
			setNodeValue(summaryNode, "start", caseBean.getStartTime());
			// summary endTime属性
			setNodeValue(summaryNode, "end", caseBean.getEndTime());
			// summary pass\fail属性
			if (blockState == 0) {
				int failState = status.equals("Passed") ? 0 : 1;
				setNodeValue(summaryNode, "fail", String.valueOf(failState));
				setNodeValue(summaryNode, "pass", String.valueOf(Math.abs(failState - 1)));
			}
			setNodeValue(summaryNode, "time", formatCostTime);

			// 格式化为缩进格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			// 设置编码格式
			format.setEncoding("UTF-8");
			// 指定文件输出的位置
			FileOutputStream out = new FileOutputStream("resources/result/" + caseBean.getCaseId() + ".xml");
			// 1.创建写出对象
			XMLWriter writer = new XMLWriter(out);
			// 2.写出Document对象
			writer.write(doc);
			// 3.关闭流
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 修改节点的属性值
	private void setNodeValue(Element node, String attr, String value) {
		Attribute attribute = node.attribute(attr);
		attribute.setValue(value);
	}

	public static void main(String[] args) {
		List<String[]> excelData = new ExcelFactory().readExcel(SOURCE_FILE);
		List<CaseBean> caseList = CaseFactory.formatData(excelData);
		for (CaseBean caseBean : caseList) {
			new XMLFactory().parse(caseBean);
		}

	}
}
