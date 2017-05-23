package utils;

import java.lang.reflect.Type;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  

import com.google.gson.Gson;  
import com.google.gson.reflect.TypeToken;  

public final class GsonUtil {  
  private GsonUtil(){}    
    
  /**   
   * 对象转换成json字符串   
   * @param obj    
   * @return    
   */    
  public static String toJson(Object obj) {    
      try{  
            
            Gson gson = new Gson();    
            return gson.toJson(obj);    
      }catch(Exception e){  
          e.printStackTrace();  
            
      }  
      return null;  
  }    
    
  /**   
   * 对象转换成json字符串   
   * @param obj    
   * @return    
   */    
  public static String toJson(String key ,Object obj) {    
      try{  
            Map map = new HashMap();  
            map.put(key, obj);  
            Gson gson = new Gson();    
            return gson.toJson(map);    
      }catch(Exception e){  
          e.printStackTrace();  
            
      }  
      return null;  
  }    
  
  /**   
   * json字符串转成对象   
   * @param str     
   * @param type   
   * @return    
   */    
  public static Object fromJsonToObject(String str, Type type) {    
      try{  
           Gson gson = new Gson();    
           return gson.fromJson(str, type);    
      }catch(Exception e){  
          e.printStackTrace();  
      }  
     return null;  
  }    
  
  /**   
   * json字符串转成对象   
   * @param str     
   * @param type    
   * @return    
   */    
  public static Object fromJsonToObject(String str, Class type) {    
      try{  
            Gson gson = new Gson();    
            return gson.fromJson(str, type);    
      }catch(Exception e){  
          e.printStackTrace();  
      }  
      return null;  
  }    
}  