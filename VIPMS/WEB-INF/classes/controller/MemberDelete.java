package controller;

import model.DB;
import model.Member;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class MemberDelete extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String memberId = request.getParameter("memberId");
        // ִ�����ݿ���²���
        DB db = new DB();
        String sql = "UPDATE members SET member_status = '0' WHERE member_id = " + memberId;
        
        try {
            db.excuteU(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ���³ɹ��󣬿����ض���ز�ѯҳ�����ʾ�ɹ���Ϣ
        response.sendRedirect("MemberQuery?keyword=" + memberId);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws 
    ServletException, IOException {
        doPost(request, response);
    }
}
