package com.mocktpo.util.layout;

import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Control;

public class RowDataSet {

    private static final int DENOMINATOR = 100;

    private RowData data;

    public static RowDataSet attach(Control c) {
        return new RowDataSet(c);
    }

    private RowDataSet(Control c) {
        data = new RowData();
        c.setLayoutData(data);
    }

    public RowDataSet withWidth(int width) {
        data.width = width;
        return this;
    }

    public RowDataSet withHeight(int height) {
        data.height = height;
        return this;
    }

    public RowData getFormData() {
        return data;
    }
}
