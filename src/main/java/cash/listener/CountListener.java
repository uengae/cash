package cash.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cash.service.CounterService;

@WebListener
public class CountListener implements HttpSessionListener {
	private CounterService counterSevice = null;
	
    public void sessionCreated(HttpSessionEvent se)  {
    	System.out.println(se.getSession().getId() + "새로운 세션이 생성되었습니다.");
//		현재 접속자수 + 1 -> application.attribute
    	ServletContext application = se.getSession().getServletContext();
    	int currentCounter = (Integer)application.getAttribute("currentCounter");
    	application.setAttribute("currentCounter", currentCounter + 1);
    	
//		오늘 접속자수 + 1  -> DB
    	this.counterSevice = new CounterService();
    	int counter = counterSevice.getCounter();
    	if(counter == 0) {
    		counterSevice.addCounter();
    	} else {
    		counterSevice.modifyCounter();
    	}
    	
    }

    public void sessionDestroyed(HttpSessionEvent se)  { 
    	System.out.println(se.getSession().getId() + "세션이 소멸되었습니다.");
//    	현재 접속자수 - 1
    	ServletContext application = se.getSession().getServletContext();
    	int currentCounter = (Integer)application.getAttribute("currentCounter");
    	application.setAttribute("currentCounter", currentCounter - 1);
    }
	
}
