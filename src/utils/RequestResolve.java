package utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
/**
 * «Î«Û¥ÆΩ‚Œˆ
 * @author Administrator
 *
 */
public class RequestResolve {
	public static RequestResolve resolve;
	public static RequestResolve getInstance(){
		if(resolve==null){
			resolve=new RequestResolve();
		}
		return resolve;
	}
	
	public <T> T resolve(HttpServletRequest req, Class<T> tClass){
		try {
			T t=tClass.newInstance();
			Field[] fields=tClass.getDeclaredFields();
			for(int i=0; i<fields.length; i++){
				Field field=fields[i];
				Object reqValue=req.getParameter(field.getName());
				Method setMethod=tClass.getDeclaredMethod(StringUtil.getSetMethodName(field.getName()), field.getType());
				Object setValue=setMethod.invoke(t, reqValue);
			}
			return t;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
