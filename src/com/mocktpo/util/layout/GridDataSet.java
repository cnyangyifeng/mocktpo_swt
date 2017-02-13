package com.mocktpo.util.layout;

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

    public GridDataSet fillBoth() {
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        return this;
    }

    public GridDataSet fillHorizontal() {
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.BEGINNING;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = false;
        return this;
    }

    public GridDataSet centerBoth() {
        data.horizontalAlignment = GridData.CENTER;
        data.verticalAlignment = GridData.CENTER;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        return this;
    }

    public GridDataSet topCenter() {
        data.horizontalAlignment = GridData.CENTER;
        data.verticalAlignment = GridData.BEGINNING;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        return this;
    }

    public GridDataSet withWidth(int width) {
        data.widthHint = width;
        return this;
    }

    public GridDataSet withHeight(int height) {
        data.heightHint = height;
        return this;
    }
}
