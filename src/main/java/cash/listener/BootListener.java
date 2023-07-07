package cash.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BootListener implements ServletContextListener {
	
	
//	driver 로딩을 미리해서 시간을 줄이기위함
    public void contextInitialized(ServletContextEvent sce)  {
    	System.out.println("실행확인 : ServletContextListener.contextInitialized");
    	
//    	application속성 영역에 "currentCounter" 속성변수 초기화
    	ServletContext application = sce.getServletContext();
    	application.setAttribute("currentCounter", 0);
    	
    	try {
    		String driver = "org.mariadb.jdbc.Driver";
			Class.forName(driver);
			System.out.println("MariaDB 드라이버 로딩 성공!");
		} catch (Exception e) {
			System.out.println("MariaDB 드라이버 로딩 실패...");
			e.printStackTrace();
		}
    }
	
}
