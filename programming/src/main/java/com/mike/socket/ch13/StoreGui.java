package com.mike.socket.ch13;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Iterator;
import java.util.Set;

public class StoreGui {

    //界面的主要窗体组件
    protected JFrame frame;
    protected Container contentPane;
    protected CardLayout card = new CardLayout();
    protected JPanel cardPan = new JPanel();

    protected JPanel selPan = new JPanel();
    protected JButton cusBt = new JButton("客户详细信息");
    protected JButton allCustBt = new JButton("所有客户清单");

    //各种按钮的选择面板上的组件
    protected JPanel custPan = new JPanel();
    protected JLabel nameLb = new JLabel("客户姓名");
    protected JLabel idLb = new JLabel("ID");
    protected JLabel addrLb = new JLabel("地址");
    protected JLabel ageLb = new JLabel("年龄");

    protected JTextField nameTf = new JTextField(25);
    protected JTextField idTf = new JTextField(25);
    protected JTextField addrTf = new JTextField(25);
    protected JTextField ageTf = new JTextField(25);

    protected JButton getBt = new JButton("查询客户");
    protected JButton upBt = new JButton("更新客户");
    protected JButton addBt = new JButton("添加客户");
    protected JButton delBt = new JButton("删除客户");

    //列举所有客户的面板上的组件
    protected JPanel allCustPan = new JPanel();
    protected JLabel allCustLb = new JLabel("所有客户清单", SwingConstants.CENTER);
    protected JTextArea allCustTa = new JTextArea();
    protected JScrollPane allCustSp = new JScrollPane(allCustTa);


    String[] tableHeaders = {"ID", "姓名", "地址", "年龄"};

    JTable table;
    JScrollPane tablePane;
    DefaultTableModel tableModel;

    //日志面板上的组件
    protected JPanel logPan = new JPanel();
    protected JLabel logLb = new JLabel("操作日志", SwingConstants.CENTER);

    protected JTextArea logTa = new JTextArea(9, 50);
    protected JScrollPane logSp = new JScrollPane(logTa);

    //显示单个客户面板
    public void refreshCustPane(Customer cust) {
        showCard("customer");
        if (cust == null || cust.getId() == -1) {
            idTf.setText(null);
            nameTf.setText(null);
            addrTf.setText(null);
            ageTf.setText(null);
            return;
        }
        idTf.setText(new Long(cust.getId()).toString());
        nameTf.setText(cust.getName().trim());
        addrTf.setText(cust.getAddr().trim());
        ageTf.setText(new Integer(cust.getAge()).toString());
    }

    //显示所有客户面板
    public void refreshAllCustPan(Set<Customer> custs) {
        showCard("allcustomer");
        String newData[][];
        newData = new String[custs.size()][4];
        Iterator<Customer> it = custs.iterator();
        int i = 0;
        while (it.hasNext()) {
            Customer cust = it.next();
            newData[i][0] = new Long(cust.getId()).toString();
            newData[i][1] = cust.getName();
            newData[i][2] = cust.getAddr();
            newData[i][3] = new Integer(cust.getAge()).toString();
            i++;
        }
        tableModel.setDataVector(newData, tableHeaders);
    }

    public void updateLog(String msg) {
        logTa.append(msg + "\n");
    }

    //或者客户面板custPan上用户输入的客户信息

    public long getCustIdOnCustPan() {
        try {
            return Long.parseLong(idTf.getText().trim());
        } catch (Exception e) {
            updateLog(e.getMessage());
            return -1;
        }
    }

    public Customer getCustomerOnCustPan() {
        try {
            return new Customer(Long.parseLong(idTf.getText().trim()),
                    nameTf.getText().trim(), addrTf.getText().trim(),
                    Integer.parseInt(ageTf.getText().trim()));
        } catch (Exception e) {
            updateLog(e.getMessage());
            return null;
        }
    }

    //显示单个客户面板custPan或者所有客户面板allCustPan
    private void showCard(String cardStr) {
        card.show(cardPan, cardStr);
    }

    public StoreGui() {
        buildDisplay();
    }

    private void buildDisplay() {
        frame = new JFrame("商店的客户管理系统");
        buildSelectionPanel();
       // buildCustPanel();
       // buildAllCustPanel();
       // buildLogPanel();

        cardPan.setLayout(card);
        cardPan.add(custPan, "customer");
        cardPan.add(allCustPan, "allcustomers");

        contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(cardPan, BorderLayout.CENTER);
        contentPane.add(selPan, BorderLayout.NORTH);
        contentPane.add(logPan, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private  void buildSelectionPanel(){

    }


}
