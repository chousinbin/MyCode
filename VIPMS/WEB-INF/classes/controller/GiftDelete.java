package controller;

import model.DB;
import model.Gift;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class GiftDelete extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String giftId = request.getParameter("giftId");
        // ִ�����ݿ���²���
        DB db = new DB();
        String sql = "UPDATE gifts SET gift_status = '0' where gift_id = ?";
        
        try {
            Object[] params = new Object[] {
                giftId
            };
            db.excuteU(sql, params);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ���³ɹ��󣬿����ض���ز�ѯҳ�����ʾ�ɹ���Ϣ
        response.sendRedirect("GiftQuery?keyword=");
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws 
    ServletException, IOException {
        doPost(request, response);
    }
}
