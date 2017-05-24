package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.TableAttendance;
import db.TableDepartment;
import db.TablePersonal;
import services.AttendanceDaoImp;
import services.PersonalManagerDaoImp;
import utils.Log;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * url: http://localhost:8080/Xing/AttendanceAddServlet?id=10002&tripDays=2.5
 */
public class AttendanceAddServlet extends HttpServlet {

	private AttendanceDaoImp attendanceDao = null;
	private PersonalManagerDaoImp personalManagerDao = null;

	public AttendanceAddServlet() {
		super();
		attendanceDao = new AttendanceDaoImp();
		personalManagerDao = new PersonalManagerDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("id");
		String tripString = request.getParameter("tripDays");
		String leaveString = request.getParameter("leaveDays");
		String absenteeismString = request.getParameter("absenteeismDays");
		String responseStr = null;
		double tripDays = -1, leaveDays = -1, absenteeismDays = -1;
		try {
			tripDays = StringUtil.isNull(tripString) ? tripDays : Double.parseDouble(tripString);
			leaveDays = StringUtil.isNull(leaveString) ? leaveDays : Double.parseDouble(leaveString);
			absenteeismDays = StringUtil.isNull(absenteeismString) ? absenteeismDays
					: Double.parseDouble(absenteeismString);
			Log.i("doGet", "uid=" + uid + ", trip=" + tripDays + ", leave=" + leaveDays + ", absen=" + absenteeismDays);
			if (StringUtil.isNull(uid) || tripDays < 0 && leaveDays < 0 && absenteeismDays < 0) {
				responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS,
						ResponseCommon.Msg.ERROR_PARAMS);
			} else {
				List<TablePersonal> queryById = personalManagerDao.queryById(uid);
				if (queryById == null || queryById.size() <= 0) {
					List<TableAttendance> queryAttendanceList = attendanceDao.queryById(uid);
					if (queryAttendanceList!=null&&queryAttendanceList.size()>0) {
						//之前有数据
						tripDays=tripDays<0?queryAttendanceList.get(0).getDays_trip():tripDays;
						leaveDays=leaveDays<0?queryAttendanceList.get(0).getDays_leave():leaveDays;
						absenteeismDays=absenteeismDays<0?queryAttendanceList.get(0).getDays_absenteeism():absenteeismDays;
					}else {
						//之前没有数据
						tripDays=tripDays<0?0:tripDays;
						leaveDays=leaveDays<0?0:leaveDays;
						absenteeismDays=absenteeismDays<0?0:absenteeismDays;
					}
					int position = attendanceDao.updateById(uid, tripDays, leaveDays, absenteeismDays);
					if (position < 0) {
						responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
								ResponseCommon.Msg.ERROR_NORMAL);
					} else {
						responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS,
								ResponseCommon.Msg.SUCCESS_ATTENDANCE_ADD);
					}
				} else {
					responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
							ResponseCommon.Msg.FAILE_ATTENDANCE_ADD);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS,
					ResponseCommon.Msg.ERROR_PARAMS);
		}
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
