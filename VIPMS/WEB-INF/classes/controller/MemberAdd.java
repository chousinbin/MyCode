package controller;
import model.Member;
import model.DB;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;

public class MemberAdd extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws 
    ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        
        // ��ȡrequest��Ϣ
        Member member = new Member();
        member.setName(request.getParameter("memberName"));
        member.setSimpleName(request.getParameter("memberSimpleName"));
        member.setPhone(request.getParameter("memberPhone"));
        member.setBirthday(new Date());
        
        //�������ݿ�
        DB db = new DB();
        String sql = 
        "INSERT INTO " + 
        "members (member_name, member_simple_name, member_phone, member_birthday) " +  
        "VALUES (?, ?, ?, ?)";
        Object[] params = new Object[] { 
            member.getName(),
            member.getSimpleName(),
            member.getPhone(),
            member.getBirthday()
        };

        int res = db.excuteU(sql, params);
        String message = (res > 0) ? "��Ա��ӳɹ���" : "���ʧ�ܣ������������ݣ�";
        request.setAttribute("message", message);
        request.setAttribute("member", member);
        request.getRequestDispatcher("MemberAdd.jsp").forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws 
    ServletException, IOException {
        doPost(request, response);
    }
}