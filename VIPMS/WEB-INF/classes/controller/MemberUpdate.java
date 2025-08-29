package controller;

import model.DB;
import model.Member;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberUpdate extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String message = "���³ɹ�"; // Ĭ�ϸ��³ɹ�

        Member member = new Member();
        try {
            // ��ȡǰ�˴��ݵĲ���������� Member ����
            member.setId(parseInteger(request.getParameter("memberId")));
            member.setName(request.getParameter("memberName"));
            member.setSimpleName(request.getParameter("memberSimpleName"));
            member.setPhone(request.getParameter("memberPhone"));

            // ��������
            String birthdayStr = request.getParameter("memberBirthday");
            Date birthday = (birthdayStr != null && !birthdayStr.isEmpty()) 
                            ? new SimpleDateFormat("yyyy-MM-dd").parse(birthdayStr) 
                            : null;
            member.setBirthday(birthday);

            member.setBalance(parseBigDecimal(request.getParameter("memberBalance")));
            member.setRewardTimes(parseInteger(request.getParameter("memberRewardTimes")));
            member.setTotalReward(parseBigDecimal(request.getParameter("memberTotalReward")));
            member.setAvailableReward(parseBigDecimal(request.getParameter("memberAvailableReward")));
            member.setStatus("1".equals(request.getParameter("memberStatus")));

            // �������ݿ�
            DB db = new DB();
            String sql = "UPDATE members SET member_name = ?, member_simple_name = ?, member_phone = ?, "
                    + "member_birthday = ?, member_balance = ?, member_reward_times = ?, "
                    + "member_total_reward = ?, member_available_reward = ?, member_status = ? "
                    + "WHERE member_id = ?";

            Object[] params = {
                    member.getName(),
                    member.getSimpleName(),
                    member.getPhone(),
                    new java.sql.Date(member.getBirthday().getTime()), // java.util.Date -> java.sql.Date
                    member.getBalance(),
                    member.getRewardTimes(),
                    member.getTotalReward(),
                    member.getAvailableReward(),
                    member.isStatus() ? "1" : "0", // ת������ֵΪ���ݿ�洢��ʽ
                    member.getId()
            };

            int rowsAffected = db.excuteU(sql, params);
            if (rowsAffected == 0) {
                message = "����ʧ�ܣ���Ա�����ڣ�";
            }
        } catch (ParseException e) {
            message = "���ڸ�ʽ����" + e.getMessage();
        } catch (NumberFormatException e) {
            message = "���ָ�ʽ����" + e.getMessage();
        } catch (Exception e) {
            message = "���ݿ����" + e.getMessage();
        }

        // ͳһҳ��ת��
        request.setAttribute("message", message);
        request.setAttribute("memberStatus", String.valueOf(member.isStatus()));
        request.getRequestDispatcher("MemberUpdate.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // �������������� NumberFormatException
    private static int parseInteger(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // ���� BigDecimal������ NumberFormatException
    private static BigDecimal parseBigDecimal(String value) {
        try {
            return (value != null && !value.isEmpty()) ? new BigDecimal(value) : BigDecimal.ZERO;
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
}
