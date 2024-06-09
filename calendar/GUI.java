package calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JTextField idField;
    private JTextField typeField;
    private JTextField expenseField;
    private JTextField keywordsField;
    private JButton saveButton;
    private JTextArea resultArea;
    int year = 2024;
    int month = 6;
    int day = 1;
    JFrame frame;
    JTable table;
    DefaultTableModel tableModel;
    final String[] columnNames = {"日", "一", "二", "三", "四", "五", "六"};

    public GUI() {
        //2.构造函数，设置frame的基本属性。
        frame = new JFrame("calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new java.awt.Dimension(500, 700));
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
                saveAccountingEntry();
            }
        });
        accountingPanel.add(saveButton);

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

        // 添加inputPanel到searchPanel的顶部
        searchPanel.add(inputPanel, BorderLayout.NORTH);

        // 创建结果显示区域
        JTextArea resultArea = new JTextArea(5, 20); // 假设高度为5行，宽度与searchField相同
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
        String result = "查询结果..."; // 这里应该是查询逻辑的输出
        resultArea.setText(result); // 将查询结果设置到结果区域
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
    private void saveAccountingEntry() {
        int id = Integer.parseInt(idField.getText());
        String type = typeField.getText();
        double expense = Double.parseDouble(expenseField.getText());
        String keywords = keywordsField.getText();

        AccountingEntry entry = new AccountingEntry(id, type, expense, keywords);
        // 这里可以添加代码来存储记账条目，例如使用数据库或文件
        System.out.println("保存记账条目: " + entry);
    }
    public void updateLabel(JLabel labelYear, JLabel labelMonth) {
        if (month > 12) {
            month = 1;
            year++;
        }
        else if (month < 1) {
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
        frame.add(mainPanel, BorderLayout.CENTER);
    }



}
class MyTableModel extends DefaultTableModel {
    public MyTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
class CustomTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (hasFocus) {
            System.out.println(row + " " + column);
            //3.如果点击到了该位置，则在控制台输出该位置的行与列。
            cellComponent.setBackground(UIManager.getColor("Table.selectionBackground"));
        } else {
            cellComponent.setBackground(table.getBackground());
        }
        return cellComponent;
    }
}

