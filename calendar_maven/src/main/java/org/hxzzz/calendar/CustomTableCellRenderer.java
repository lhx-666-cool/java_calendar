package org.hxzzz.calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class CustomTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (isSelected) {
            ((Component) cellComponent).setBackground(table.getSelectionBackground());
            cellComponent.setForeground(table.getSelectionForeground());
        } else {
            cellComponent.setBackground(table.getBackground());
            cellComponent.setForeground(table.getForeground());
        }
        return cellComponent;
    }
}
