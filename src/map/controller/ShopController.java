package map.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import map.common.MapUtil;
import map.model.dao.ShopInfoDAO;
import map.model.dto.ShopInfoDTO;

@WebServlet("/shop_servlet/*")
public class ShopController extends HttpServlet {
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
		
		if (url.indexOf("write.do") != -1) {
			String page = "/shop/write.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
			
		} else if (url.indexOf("writeProc.do") != -1) {
			String latitude_ = request.getParameter("latitude");
			String longitude_ = request.getParameter("longitude");
			Double latitude = Double.parseDouble(latitude_);
			Double longitude = Double.parseDouble(longitude_);
			String name = request.getParameter("name");
			String instagram = request.getParameter("instagram");
			String address = request.getParameter("address");
			String shopUrl = request.getParameter("shopUrl");
			String uuid = util.createUuid();
			
			dto.setId(uuid);
			dto.setLatitude(latitude);
			dto.setLongitude(longitude);
			dto.setName(name);
			dto.setInstagram(instagram);
			dto.setAddress(address);
			dto.setShopUrl(shopUrl);
			
			String temp;
			int result = dao.setInsert(dto);
			if (result > 0) {
				temp = path + "/shop_servlet/list.do";
			} else {
				temp = path + "/shop_servlet/write.do";
			}
			response.sendRedirect(temp);
			
			
		} else if (url.indexOf("map.do") != -1) {
			String page = "/map/map.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
			
		} else if (url.indexOf("list.do") != -1) {
			// paging
			int allRowsCount = dao.getAllRowsCount(search_option, search_data);
			final int ONE_PAGE_ROWS = 10;
			final int MAX_PAGING_WIDTH = 10;
			
			int[] pagerArr = util.pager(ONE_PAGE_ROWS, MAX_PAGING_WIDTH, allRowsCount, pageNum);
			int tableRowNum = pagerArr[0];
			int pagingStartNum = pagerArr[1];
			int pagingEndNum = pagerArr[2];
			int maxPagesCount = pagerArr[3];
			int startNum = pagerArr[4];
			int endNum = pagerArr[5];
			
			ArrayList<ShopInfoDTO> list = dao.getPagingList(startNum, endNum, search_option, search_data);
			
			request.setAttribute("menu_gubun", "shop_list");
			request.setAttribute("list", list);
			
			request.setAttribute("ONE_PAGE_ROWS", ONE_PAGE_ROWS);
			request.setAttribute("MAX_PAGING_WIDTH", MAX_PAGING_WIDTH);
			request.setAttribute("allRowsCount", allRowsCount);
			request.setAttribute("tableRowNum", tableRowNum);
			
			request.setAttribute("pagingStartNum", pagingStartNum);
			request.setAttribute("pagingEndNum", pagingEndNum);
			
			request.setAttribute("maxPagesCount", maxPagesCount);
			request.setAttribute("pagingStartNum", pagingStartNum);
			request.setAttribute("pagingEndNum", pagingEndNum);			
			
			String page = "/shop/list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
			
		}else if (url.indexOf("view.do") != -1) {
			dto = dao.getView(no);

			request.setAttribute("menu_gubun", "shop_view");
			request.setAttribute("dto", dto);
			
			String page = "/shop/view.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
			
		}
	}
}
