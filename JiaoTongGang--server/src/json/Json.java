package json;

import net.sf.json.JSONObject;

public class Json {
			
	
	/**
	 *@param key表示json字符串的头消息
	 *@param object 是对解析的集合的类型
	 * @return 
	 * 
	 */
	public static String creatJsonString(String key,Object value)
	{	
		JSONObject jsonObject=new JSONObject();
	
		jsonObject.put(key,value);
		return jsonObject.toString();
	}
}
