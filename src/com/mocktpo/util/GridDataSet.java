package com.mocktpo.util;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;

public class GridDataSet {

    private GridData data;

    public static GridDataSet attach(Control c) {
        return new GridDataSet(c);
    }

    private GridDataSet(Control c) {
        data = new GridData();
        c.setLayoutData(data);
    }

    public GridDataSet horizontalAlignment(int style) {
        data.horizontalAlignment = style;
        return this;
    }

    public GridDataSet fillBoth() {
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        return this;
    }
}
