package controller;

import model.DB;
import model.Member;
import model.Gift;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;

public class GiftConvert extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        // ��ȡǰ����Ϣ
        int giftId = Integer.parseInt(request.getParameter("giftId"));
        String giftName = request.getParameter("giftName");
        int convertNumber = Integer.parseInt(request.getParameter("convertNumber"));
        String memberId = request.getParameter("memberId");
        int giftStock = Integer.parseInt(request.getParameter("giftStock"));
        int giftValue = Integer.parseInt(request.getParameter("giftValue"));
        
        String message = "�һ��ɹ�"; // Ĭ�ϳɹ���Ϣ
        boolean st = true;
        
        // �жϿ���Ƿ�����
        if (giftStock < convertNumber) {
            message = "��治�㣬�޷��һ�";
            st = false;
        }
        // �жϻ�Ա�Ƿ����
        DB db = new DB();
        String sql = "select member_available_reward from members where member_id = ?";
        Object[] params = new Object[] {memberId};
        ResultSet rs = db.excuteQ(sql, params);
        int oldReward = 0;

        try {
            
            if (rs.next()) {
                oldReward = rs.getInt(1);
            } else {
                message = "��Ա������";
                st = false;
            }

            // �жϻ�Ա�����Ƿ��㹻
            int awards = rs.getInt(1);
            if (awards < convertNumber * giftValue) {
                message = "���ֲ���";
                st = false;
            }

            // �ɹ��һ�
            if (st) {
                // ���»���
                sql = "UPDATE members SET member_available_reward = member_available_reward - ? WHERE member_id = ?";
                db.excuteU(sql, new Object[]{convertNumber * giftValue, memberId});

                // ������Ʒ���
                sql = "UPDATE gifts SET gift_stock = gift_stock - ? WHERE gift_id = ?";
                db.excuteU(sql, new Object[]{convertNumber, giftId});

                // ��Ӷһ���¼
                sql = "INSERT INTO convert_records (member_id, gift_id, gift_quantity, reward_used)" + 
                "values(?, ?, ?, ?)";
                db.excuteU(sql, new Object[]{memberId, giftId, convertNumber, convertNumber * giftValue});
            }

            db.close();
        } catch(Exception e) {
             e.printStackTrace();
        }
        

        // ͳһҳ��ת��
        Gift gift = new Gift(giftId, giftName, st ? giftStock - convertNumber : giftStock, giftValue);
        request.setAttribute("gift", gift);
        request.setAttribute("convertNumber", convertNumber);
        request.setAttribute("memberId", memberId);
        request.setAttribute("avaliableReward", st ? oldReward - convertNumber * giftValue : oldReward);
        request.setAttribute("message", message);
        request.getRequestDispatcher("GiftConvertResult.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        doGet(request, response);
    }
}
