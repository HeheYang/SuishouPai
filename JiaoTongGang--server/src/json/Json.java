package json;

import net.sf.json.JSONObject;

public class Json {
			
	
	/**
	 *@param key��ʾjson�ַ�����ͷ��Ϣ
	 *@param object �ǶԽ����ļ��ϵ�����
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
