package map.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import map.common.MapUtil;
import map.model.dao.ShopInfoDAO;
import map.model.dto.ShopInfoDTO;

@WebServlet("/map_servlet/*")
public class MapController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}
	
	protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		MapUtil util = new MapUtil();
		
		int[] yearMonthDayHourMinSec = util.getDateTime();
		HashMap<String, Integer> yearMonthDayMap = new HashMap<>();
		yearMonthDayMap.put("nowYear", yearMonthDayHourMinSec[0]);
		yearMonthDayMap.put("nowMonth", yearMonthDayHourMinSec[1]);
		yearMonthDayMap.put("nowDay", yearMonthDayHourMinSec[2]);
		
		String serverInfo[] = util.getServerInfo(request);   // request.getContextPath();
		String referer = serverInfo[0];
		String path = serverInfo[1];
		String url = serverInfo[2];
		String uri = serverInfo[3];
		String ip = serverInfo[4];
		// String ip6 = serverInfo[5];
		
		String pageNum_ = request.getParameter("pageNumber");
		int pageNum = util.numberCheck(pageNum_, 1);

		String no_ = request.getParameter("no");
		int no = util.numberCheck(no_, 0);
		
		String search_option = request.getParameter("search_option");
		String search_data = request.getParameter("search_data");
		String[] searchArray = util.searchCheck(search_option, search_data);
		search_option = searchArray[0];
		search_data = searchArray[1];
		
		request.setAttribute("yearMonthDayMap", yearMonthDayMap);
		request.setAttribute("ip", ip);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("no", no);
		
		ShopInfoDAO dao = new ShopInfoDAO();
		ShopInfoDTO dto = new ShopInfoDTO();
		
		if (url.indexOf("map.do") != -1) {
			List<ShopInfoDTO> allShopInfos = dao.getAllShopInfos();
			
			
			String page = "/map/map.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
			
		}
	}

}