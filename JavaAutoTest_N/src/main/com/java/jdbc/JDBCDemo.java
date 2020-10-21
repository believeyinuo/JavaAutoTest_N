package main.com.java.jdbc;


import java.util.Map;
import java.util.Set;

public class JDBCDemo {
    public static void main (String[] args) {
        String sql1 = "select id, regname, leaveamount, mobilephone from member where mobiLephone = ?";
        String sql2 = "select memberid, amount from invest where id = ?";
        Object[] values = {"8"};
        Map<String, Object> columLabelAndValue = JDBCUtil.query(sql1, values);
        Set<String> columnLabels = columLabelAndValue.keySet();
        for (String columnLabel : columnLabels) {
            System.out.println("columnLabel:" + columnLabel + ", colunmValue=" + columLabelAndValue);
        }
    }
}
