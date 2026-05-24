package quanlysanbong.view;

import javax.swing.*;
import java.awt.*;

public final class FormUiHelper {

    private FormUiHelper() {
    }

    public static JPanel createColumnForm(String title) {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder(title));
        return form;
    }

    public static int addField(JPanel form, int row, String labelText, JComponent field) {
        GridBagConstraints labelGbc = new GridBagConstraints();
        labelGbc.gridx = 0;
        labelGbc.gridy = row * 2;
        labelGbc.anchor = GridBagConstraints.WEST;
        labelGbc.insets = new Insets(6, 10, 2, 10);
        labelGbc.weightx = 1;
        labelGbc.weighty = 0;
        labelGbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(new JLabel(labelText), labelGbc);

        GridBagConstraints fieldGbc = new GridBagConstraints();
        fieldGbc.gridx = 0;
        fieldGbc.gridy = row * 2 + 1;
        fieldGbc.insets = new Insets(0, 10, 8, 10);
        fieldGbc.weightx = 1;
        fieldGbc.weighty = 0;
        fieldGbc.fill = GridBagConstraints.HORIZONTAL;
        int fieldHeight = Math.max(field.getPreferredSize().height, 28);
        field.setPreferredSize(new Dimension(280, fieldHeight));
        field.setMinimumSize(new Dimension(200, fieldHeight));
        form.add(field, fieldGbc);
        return row + 1;
    }

    /** Đặt chiều cao ưu tiên đủ cho scroll dọc, không nén các ô nhập. */
    public static void finalizeColumnForm(JPanel form, int fieldCount) {
        int height = fieldCount * 62 + 48;
        form.setPreferredSize(new Dimension(300, height));
        form.setMinimumSize(new Dimension(260, 200));
    }

    public static void layoutCrudWithSplit(JFrame frame, JPanel formPanel, JTable table,
                                           JPanel buttons, int dividerLocation) {
        JScrollPane formScroll = new JScrollPane(formPanel);
        formScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        formScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        formScroll.getVerticalScrollBar().setUnitIncrement(14);
        formScroll.setBorder(BorderFactory.createEmptyBorder());

        JScrollPane tableScroll = new JScrollPane(table);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formScroll, tableScroll);
        split.setDividerLocation(dividerLocation);
        split.setResizeWeight(0.28);
        split.setContinuousLayout(true);
        split.setOneTouchExpandable(true);

        frame.setLayout(new BorderLayout(0, 6));
        frame.add(split, BorderLayout.CENTER);
        frame.add(buttons, BorderLayout.SOUTH);
    }
}
