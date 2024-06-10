package org.hxzzz.calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class GUI {
    private JTextField idField;
    private JTextField typeField;
    private JTextField expenseField;
    private JTextField keywordsField;
    private JButton saveButton;
    private JTextArea resultArea;
    int year = 2024;
    int month = 6;
    int day = -1;
    static JFrame frame;
    JTable table;
    DefaultTableModel tableModel;
    final String[] columnNames = {"日", "一", "二", "三", "四", "五", "六"};
    Accountings accountings;

    public GUI(Accountings accountings) {
        //2.构造函数，设置frame的基本属性。
        this.accountings = accountings;
        frame = new JFrame("org/hxzzz/calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 700));
        frame.setResizable(false);
        // 创建窗口
        frame.setLayout(new BorderLayout());
        frameHead();
        calendarGUI();
        createSearchGUI();
        createAccountingGUI();
        frame.pack();
        frame.setVisible(true);
    }

    public void createAccountingGUI() {
        JPanel accountingPanel = new JPanel();
        accountingPanel.setLayout(new GridLayout(5, 2, 5, 5)); // 5行2列的网格布局

        // 添加标签和文本框
        accountingPanel.add(new JLabel("记账ID:"));
        idField = new JTextField();
        idField.setText(accountings.getId() + "");
        idField.setEditable(false);
        accountingPanel.add(idField);

        accountingPanel.add(new JLabel("记账类型:"));
        typeField = new JTextField();
        accountingPanel.add(typeField);

        accountingPanel.add(new JLabel("支出费用:"));
        expenseField = new JTextField();
        accountingPanel.add(expenseField);

        accountingPanel.add(new JLabel("关键字:"));
        keywordsField = new JTextField();
        accountingPanel.add(keywordsField);

        // 添加保存按钮
        saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (day != -1) {
                    LocalDate date = LocalDate.of(year, month, day);
                    try {
                        saveAccountingEntry(date);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    showResultInDialog("请选择日期");
                }
            }
        });
        JButton aboutButton = new JButton("关于");
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showResultInDialog("作者：刘昊昕，张志鹏\n版本：1.0\n日期：2024-06-24\n本软件（JAVA程序设计大作业）由作者使用IntelliJ IDEA开发，实现了一个简单的记账功能。");
            }
        });
        accountingPanel.add(saveButton);
        accountingPanel.add(aboutButton);

        // 添加记账面板到frame的左下角
        frame.add(accountingPanel, BorderLayout.SOUTH);
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        southPanel.add(accountingPanel); // 添加记账面板到这个新面板中

        // 最后，将新面板添加到frame的SOUTH区域
        frame.add(southPanel, BorderLayout.SOUTH);
    }

    public void createSearchGUI() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout()); // 使用BorderLayout来布局

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // 添加查询标签和查询文本框
        inputPanel.add(new JLabel("查询:"));
        JTextField searchField = new JTextField(20); // 假设查询框长度为20
        inputPanel.add(searchField);

        // 添加查询按钮
        JButton searchButton = new JButton("搜索");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 执行查询逻辑
                searchEntries(searchField.getText());
            }
        });
        inputPanel.add(searchButton);

        JButton thisMonth = new JButton("查询当月数据");
        thisMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 执行查询逻辑
                searchEntries();
            }
        });
        inputPanel.add(thisMonth);
        // 添加inputPanel到searchPanel的顶部
        searchPanel.add(inputPanel, BorderLayout.NORTH);

        // 创建结果显示区域
        resultArea = new JTextArea(5, 20); // 假设高度为5行，宽度与searchField相同
        resultArea.setEditable(false); // 设置为不可编辑
        JScrollPane scrollPane = new JScrollPane(resultArea); // 添加滚动条

        // 添加结果显示区域到searchPanel的中心
        searchPanel.add(scrollPane, BorderLayout.CENTER);

        // 将查询面板添加到frame的东部区域
        frame.add(searchPanel, BorderLayout.EAST);
    }

    private void searchEntries(String query) {
        // 实现查询逻辑
        // 假设这里是查询逻辑的实现，并且查询结果被存储在result变量中
        double income = 0;
        double outcome = 0;
        StringBuilder result = new StringBuilder();
        for (AccountingEntry entry : accountings.getAccountingsByKeyword(query)) {
            result.append("消费类型: ").append(entry.getType()).append(", ")
                    .append("消费金额: ").append(entry.getExpense()).append(", ")
                    .append("关键字: ").append(entry.getKeywords()).append(", ")
                    .append("日期: ").append(entry.getDate()).append("\n");
            if (entry.getType().equals("收入")) {
                income += entry.getExpense();
            } else {
                outcome += entry.getExpense();
            }
        }
        result.append("总收入: ").append(income).append("\n");
        resultArea.setText(result.toString()); // 将查询结果设置到结果区域
    }

    private void searchEntries() {
        // 实现查询逻辑
        // 假设这里是查询逻辑的实现，并且查询结果被存储在result变量中
        // 这里应该是查询逻辑的输出
        StringBuilder result = new StringBuilder();
        result.append("当月消费记录如下:\n");
        for (AccountingEntry entry : accountings.getAccountingsByMonth(year, month)) {
            result.append("消费类型: ").append(entry.getType()).append(", ")
                    .append("消费金额: ").append(entry.getExpense()).append(", ")
                    .append("关键字: ").append(entry.getKeywords()).append(", ")
                    .append("日期: ").append(entry.getDate()).append("\n");
        }
        result.append("\n关键字汇总如下:\n");
        Map<String, Integer> keywordMap = new HashMap<>();
        Map<String, Double> MoneyMap = new HashMap<>();
        double income = 0;
        double outcome = 0;
        for (AccountingEntry entry : accountings.getAccountingsByMonth(year, month)) {
            String keywords = entry.getKeywords();
            String[] keywordArray = keywords.split(";");
            for (String keyword : keywordArray) {
                keywordMap.put(keyword, keywordMap.getOrDefault(keyword, 0) + 1);
                MoneyMap.put(keyword, MoneyMap.getOrDefault(keyword, 0.0) + entry.getExpense());
            }
            if (entry.getType().equals("收入")) {
                income += entry.getExpense();
            } else {
                outcome += entry.getExpense();
            }
        }
        for (Map.Entry<String, Integer> entry : keywordMap.entrySet()) {
            result.append("关键字: ").append(entry.getKey()).append(", ")
                    .append("出现次数: ").append(entry.getValue()).append(", ")
                    .append("总金额: ").append(MoneyMap.get(entry.getKey())).append("\n");
        }
        result.append("总收入: ").append(income).append("\n");
        result.append("总支出: ").append(outcome).append("\n");
        resultArea.setText(result.toString()); // 将查询结果设置到结果区域
    }

    public void frameHead() {
        JPanel panel1 = new JPanel();
        //1.创建panel
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton LeftYear = new JButton("<<");

        JButton LeftMonth = new JButton("<");
        JLabel labelYear = new JLabel(year + "");
        JLabel labelMonth = new JLabel(month + "");
        JButton rightMonth = new JButton(">");
        JButton rightYear = new JButton(">>");
        setBeautifulBtn(LeftYear);
        setBeautifulBtn(LeftMonth);
        setBeautifulBtn(rightMonth);
        setBeautifulBtn(rightYear);
        panel1.add(LeftYear);
        panel1.add(LeftMonth);
        panel1.add(labelYear);
        panel1.add(labelMonth);
        panel1.add(rightMonth);
        panel1.add(rightYear);


        LeftYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 更新表格数据
                year--;
                updateLabel(labelYear, labelMonth);
            }
        });
        LeftMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 更新表格数据
                month--;
                updateLabel(labelYear, labelMonth);
            }
        });
        rightYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 更新表格数据
                year++;
                updateLabel(labelYear, labelMonth);
            }
        });
        rightMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 更新表格数据
                month++;
                updateLabel(labelYear, labelMonth);
            }
        });


        frame.add(panel1, BorderLayout.NORTH);
    }

    private void saveAccountingEntry(LocalDate date) throws IOException {
        try {
            int id = accountings.getId();
            String type = typeField.getText();
            if (!(type.equals("收入") || type.equals("支出"))) {
                showResultInDialog("请输入正确的类型，\"收入\"或\"支出\"");
                return;
            }
            double expense = Double.parseDouble(expenseField.getText());
            String keywords = keywordsField.getText();
            keywords = keywords.replace("；", ";");
            AccountingEntry entry = new AccountingEntry(id, type, expense, keywords, date);
            // 这里可以添加代码来存储记账条目，例如使用数据库或文件
            accountings.add(entry);
            idField.setText(accountings.getId() + "");
            System.out.println("保存记账条目: " + entry);
            showResultInDialog("保存成功");
        } catch (Exception NumberFormatException) {
            showResultInDialog("请输入正确的数字");
        }
    }

    public void updateLabel(JLabel labelYear, JLabel labelMonth) {
        if (month > 12) {
            month = 1;
            year++;
        } else if (month < 1) {
            month = 12;
            year--;
        }

        if (year < 0) {
            year = 0;
        }
        labelYear.setText(year + "");
        labelMonth.setText(month + "");
        tableModel.setDataVector(WeeklySchedule.getSchedule(year, month), columnNames);
    }

    public void setBeautifulBtn(JButton button) {
        button.setBackground(new Color(169, 169, 169));
        button.setBorderPainted(false);
        button.setMargin(new Insets(3, 3, 3, 3));
    }

    public void calendarGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        // 创建表格模型和表格
        tableModel = new MyTableModel(WeeklySchedule.getSchedule(year, month), columnNames);
        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(true);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 获取点击的行和列的索引
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                // 输出点击的单元格的行和列
                System.out.println("Clicked cell at row " + row + ", column " + col);

                // 获取点击的单元格的值
                Object value = table.getValueAt(row, col);
                System.out.println("Value: " + value);
                if (value == null) {
                    day = -1;
                } else {
                    day = Integer.parseInt(value.toString());
                }
            }
        });
        frame.add(mainPanel, BorderLayout.CENTER);
    }

    public static void showResultInDialog(String result) {

        JOptionPane.showMessageDialog(frame, result, "提示", JOptionPane.INFORMATION_MESSAGE);


    }
}


