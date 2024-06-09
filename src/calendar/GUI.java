package calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    int year = 2024;
    int month = 6;
    int day = 1;
    JFrame frame;
    JTable table;
    DefaultTableModel tableModel;
    final String[] columnNames = {"日", "一", "二", "三", "四", "五", "六"};
    public GUI() {
        frame = new JFrame("calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new java.awt.Dimension(500, 700));
        frame.setResizable(false);
        // 创建窗口
        frame.setLayout(new BorderLayout());
        frameHead();
        calendarGUI();


        frame.pack();
        frame.setVisible(true);
    }
    public void frameHead() {
        JPanel panel1 = new JPanel();
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
    public void updateLabel(JLabel labelYear, JLabel labelMonth) {
        if (month > 12) {
            month = 1;
            year++;
        }else if (month < 1) {
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
            cellComponent.setBackground(UIManager.getColor("Table.selectionBackground"));
        } else {
            cellComponent.setBackground(table.getBackground());
        }
        return cellComponent;
    }
}