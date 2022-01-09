package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhonebookController extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		PhoneDao phoneDao = new PhoneDao();
		List<PersonVo> personList = phoneDao.getPersonList();
		
		//주소값에서 action 을 주고 이걸 받아오는데 action변수에 넣었다
		String act = request.getParameter("action");
	
		//html 과 list 섞어서 표현해야한다
		//servlet 으로는 어렵다 -->jsp를 이용한다
		request.setAttribute("pList", personList); // pList에 정보를 넣어둘테니 pList 를 통해서 꺼내먹어요
		//포워드 시키겠다 = 일시키겠다 
		
		if("list".equals(act)) {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp");
		rd.forward(request, response); //위의 주소 rd에 리퀘스트 리스폰스 데이터를 주겠다.
		
		
		
		
		}else if("writeForm".equals(act)){
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/writeForm.jsp");
			rd.forward(request, response);
			
			

			
		}else if("write".equals(act)) { // 저장페이지
			
			//파라미터 3개를 꺼내온다
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			//vo 로 만든다
			PersonVo personVo = new PersonVo(name, hp, company);
			//dao 메모리로 올린다 ,난 위에 함
			//dao.insert(vo);
			phoneDao.personInsert(personVo);
			
			//포워드보다 리다이렉트가 좋다
			response.sendRedirect("/phonebook2/pbc?action=list");
		
		
		
		
		}else if("delete".equals(act)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp");
			rd.forward(request, response);
			
			String id = request.getParameter("id");
			int i = Integer.parseInt(id);
			
			
			phoneDao.personDelete(i);
			response.sendRedirect("/phonebook2/pbc?action=list");
		
		}else if("updateForm".equals(act)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/updateForm.jsp");
			rd.forward(request, response);
			
			
			String id = request.getParameter("id");
			int i = Integer.parseInt(id);
		
			
		
		}else if("update".equals(act)) {
			
			
			String name = request.getParameter("name");
			String hp = request.getParameter("HP");
			String company = request.getParameter("company");
			String id = request.getParameter("id");
			int i = Integer.parseInt(id);
			//vo 로 만든다
			PersonVo personVo = new PersonVo(i, name, hp, company);
			//dao 메모리로 올린다 ,난 위에 함
			//dao.insert(vo);
			phoneDao.personUpdate(personVo);
			
			//포워드보다 리다이렉트가 좋다
			response.sendRedirect("/phonebook2/pbc?action=list");
				
				
				
		
		}else {
			System.out.println("틀린값입니다.");
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
