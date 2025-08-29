package controller;
import model.Gift;
import model.DB;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;

public class GiftAdd extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws 
    ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        
        // ��ȡrequest��Ϣ
        Gift gift = new Gift();
        gift.setName(request.getParameter("giftName"));
        gift.setStock(Integer.parseInt(request.getParameter("giftStock")));
        gift.setValue(Integer.parseInt(request.getParameter("giftValue")));
        
        //�������ݿ�
        DB db = new DB();
        String sql = 
        "INSERT INTO " + 
        "gifts (gift_name, gift_stock, gift_value) " +  
        "VALUES (?, ?, ?)";
        Object[] params = new Object[] { 
            gift.getName(),
            gift.getStock(),
            gift.getValue(),
        };

        int res = db.excuteU(sql, params);
        String message = (res > 0) ? "��Ա��ӳɹ���" : "���ʧ�ܣ������������ݣ�";
        db.close();

        request.setAttribute("message", message);
        request.setAttribute("gift", gift);
        request.getRequestDispatcher("GiftAdd.jsp").forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws 
    ServletException, IOException {
        doPost(request, response);
    }
}