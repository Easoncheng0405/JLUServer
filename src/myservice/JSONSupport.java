/*
 * 安卓服务器端不需要实际的网页页面，返回客户端的数据大部分为JSON类型
 * JSONSupport类继承了ActionSupport类，并在其中定义了需要的返回的HashMap和GET方法
 * */


package myservice;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public class JSONSupport extends ActionSupport{
	public Map<String, Object> dataMap = new HashMap<String, Object>();
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
}
