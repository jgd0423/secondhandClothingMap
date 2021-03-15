package common;

import java.io.File;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.StyleSheet.ListPainter;

public class Util {
	public int[] getDateTime() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		
		int[] result = new int[6];
		result[0] = year;
		result[1] = month;
		result[2] = day;
		result[3] = hour;
		result[4] = min;
		result[5] = sec;
		
		return result;
	}

	public int numberCheck(String pageNumStr, int defaultNumber) {
		String defaultPageNumber = String.valueOf(defaultNumber);
		
		if (pageNumStr == null || pageNumStr.trim().equals("")) {
			pageNumStr = defaultPageNumber;
		}
		
		try {
			Double.parseDouble(pageNumStr);
			return Integer.parseInt(pageNumStr);
		} catch (Exception e) {
			return Integer.parseInt(defaultPageNumber);
		}
	}

	public String list_gubunCheck(String list_gubun) {
		if (list_gubun == null || list_gubun.trim().equals("")) {
			list_gubun = "all";
		}
		
		list_gubun = list_gubun.trim();
		if (list_gubun.equals("all")) {} 
		else if (list_gubun.equals("ing")) {} 
		else if (list_gubun.equals("end")) {} 
		else { list_gubun = "all"; }
		
		return list_gubun;
	}

	public String[] searchCheck(String search_option, String search_data, String search_date_start, String search_date_end, String search_date_check) {
		String[] result = new String[5];
		
		if (search_option == null || search_option.trim().equals("")) {
			search_option = "";
		}
		search_option = search_option.trim();
		
		if (search_option.equals("")) {}
		else if (search_option.equals("question")) {}
		else { search_option = ""; }
		
		if (search_data == null || search_data.trim().equals("")) {
			search_data = "";
		}
		search_data = search_data.trim();
		
		if (search_option.equals("") || search_data.equals("")) {
			search_option = "";
			search_data = "";
		}
		
		if (search_date_check == null || search_date_check.trim().equals("")) {
			search_date_check = "";
		}
		
		if (search_date_check.equals("O")) {
			if (search_date_start == null || search_date_start.trim().equals("")) {
				search_date_start = "";
			}
			search_date_start = search_date_start.trim();
			
			if (search_date_end == null || search_date_end.trim().equals("")) {
				search_date_end = "";
			}
			search_date_end = search_date_end.trim();
			
			if (search_date_start.equals("") || search_date_end.equals("")) {
				search_date_check = "";
			}
		} else {
			search_date_start = "";
			search_date_end = "";
			search_date_check = "";
		}
		
		result[0] = search_option;
		result[1] = search_data;
		result[2] = search_date_start;
		result[3] = search_date_end;
		result[4] = search_date_check;
		
		return result;
	}

	public String[] getServerInfo(HttpServletRequest request) throws UnknownHostException {
		String[] result = new String[6];
		String referer = request.getHeader("REFERER");
		if (referer == null || referer.trim().equals("")) {
			referer = "";
		}
		
		String path = request.getContextPath();
		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI().toString();
		String ip = Inet4Address.getLocalHost().getHostAddress();
		String ip6 = "";
		
		result[0] = referer;
		result[1] = path;
		result[2] = url;
		result[3] = uri;
		result[4] = ip;
		result[5] = ip6;
		
		return result;
	}
	
	public String[] sessionCheck(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		int cookNo = 0;
		if (session.getAttribute("cookNo") != null) {
			cookNo = (Integer)session.getAttribute("cookNo");
		}
		
		String cookId = "";
		if (session.getAttribute("cookId") != null) {
			cookId = (String)session.getAttribute("cookId");
		}
		
		String cookName = "";
		if (session.getAttribute("cookId") != null) {
			cookName = (String)session.getAttribute("cookName");
		}
		
		String[] result = new String[3];
		result[0] = cookNo + "";
		result[1] = cookId;
		result[2] = cookName;
		
		return result;
	}
	
	public int[] pager(final int ONE_PAGE_ROWS, final int MAX_PAGING_WIDTH, int allRowsCount, int pageNum) {
		int maxPagesCount = (int)Math.ceil((double)allRowsCount / ONE_PAGE_ROWS);
		int tableRowNum = allRowsCount - (pageNum - 1) * ONE_PAGE_ROWS;
		int pagingLoopNum = (int)Math.ceil((double)pageNum / MAX_PAGING_WIDTH) - 1;
		int pagingStartNum = pagingLoopNum * MAX_PAGING_WIDTH + 1;
		int pagingEndNum = pagingStartNum + MAX_PAGING_WIDTH - 1;
		if (pagingEndNum > maxPagesCount) {
			pagingEndNum = maxPagesCount;
		}
		int endNum = pageNum * ONE_PAGE_ROWS;
		int startNum = endNum - ONE_PAGE_ROWS + 1;
		
		int[] result = new int[6];
		result[0] = tableRowNum;
		result[1] = pagingStartNum;
		result[2] = pagingEndNum;
		result[3] = maxPagesCount;
		result[4] = startNum;
		result[5] = endNum;
		
		return result;
	}
	
	public String nullCheck(String str) {
		String result = str;
		if (result == null || result.trim().equals("")) {
			result = "";
		}
		result = result.trim();
		return result;
	}
	
	public String createUuid() {
		return UUID.randomUUID().toString();
	}
	
	public String getDateTimeType() {
		String result = "";
		int[] dateTime = getDateTime();
		String y = dateTime[0] + "";
		String m = dateTime[1] + "";
		String d = dateTime[2] + "";
		String s = dateTime[3] + "";
		String b = dateTime[4] + "";
		String c = dateTime[5] + "";
		if (m.length() < 2) { m = "0" + m; }
		if (d.length() < 2) { d = "0" + d; }
		if (s.length() < 2) { s = "0" + s; }
		if (b.length() < 2) { b = "0" + b; }
		if (c.length() < 2) { c = "0" + c; }
		result = y + m + d + s + b + c;
		return result;
	}
	
	public String todayTime() {
		Date now = new Date();
		SimpleDateFormat sf;
		sf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sf.format(now);
	}

	public void fileDelete(HttpServletRequest request, String dir) {
		if (dir.trim().equals("")) {
			return;
		}
		
		// Calender 객체 생성
		Calendar cal = Calendar.getInstance();
		long todayMil = cal.getTimeInMillis();
		long oneDayMil = 24 * 60 * 60 * 1000;
		
		Calendar fileCal = Calendar.getInstance();
		Date fileDate = null;
		
		File path = new File(dir);
		File[] list = path.listFiles();
		
		for (int j = 0; j < list.length; j++) {
			// 파일의 마지막 수정시간 가져오기
			fileDate = new Date(list[j].lastModified());
			
			// 현재시간과 파일 수정시간 시간 차 계산(단위: 밀리 세컨드)
			fileCal.setTime(fileDate);
			long diffMil = todayMil - fileCal.getTimeInMillis();
			
			// 날짜로 계산
			int diffDay = (int)(diffMil / oneDayMil);
			
			// 3일이 지난 파일 삭제
//			if (diffDay > 3 && list[j].exists()) {
//				list[j].delete();
//				System.out.println(list[j].getName() + " 파일을 삭제했습니다.");
//			}
			
			if (diffMil > 0 && list[j].exists()) {
				list[j].delete();
				System.out.println(list[j].getName() + " 파일을 삭제했습니다.");
			}
		}
		
	}
}
