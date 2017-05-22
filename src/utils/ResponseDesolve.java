package utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import entitys.BaseDataResponse;
import entitys.BaseResponse;
/**
 * ������ش�
 * @author Administrator
 *
 */
public class ResponseDesolve {
	private static ResponseDesolve desolve;
	public static ResponseDesolve getInstance(){
		if(desolve==null){
			desolve=new ResponseDesolve();
		}
		return desolve;
	}
	
	public String desolve(BaseDataResponse bean, String errMsg){
		Log.i("ResponseDesolve.desolve", "BaseBean="+bean);
		BaseResponse res=new BaseResponse();
		try{
			Gson gson=new Gson();
			String dataStr = gson.toJson(bean);
			res.setData(dataStr);
		}catch(Exception e){
			res.setCode(-1);
			if(StringUtil.isNull(errMsg))
				errMsg="";
			res.setMsg(errMsg);
		}
		Gson gson=new Gson();
		String str = gson.toJson(res);
		return str;
	}
	
	public String desolve(int errCode, String errMsg){
		BaseResponse res=new BaseResponse();
		res.setCode(errCode);
		if(StringUtil.isNull(errMsg))
			errMsg="";
		res.setMsg(errMsg);
		Gson gson=new Gson();
		String str = gson.toJson(res);
		return str;
	}
	
	public void sendResponse(String responseStr, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setAttribute("response", responseStr);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
}