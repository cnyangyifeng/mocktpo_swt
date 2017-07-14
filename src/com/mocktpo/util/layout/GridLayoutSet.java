package com.mocktpo.util.layout;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class GridLayoutSet {

    private GridLayout l;

    public static GridLayoutSet layout(Composite c) {
        return new GridLayoutSet(c);
    }

    private GridLayoutSet(Composite c) {
        l = new GridLayout();
        c.setLayout(l);
    }

    public GridLayoutSet spacing(int n) {
        l.horizontalSpacing = n;
        l.verticalSpacing = n;
        return this;
    }

    public GridLayoutSet horizontalSpacing(int n) {
        l.horizontalSpacing = n;
        return this;
    }

    public GridLayoutSet verticalSpacing(int n) {
        l.verticalSpacing = n;
        return this;
    }

    public GridLayoutSet makeColumnsEqualWidth(boolean b) {
        l.makeColumnsEqualWidth = b;
        return this;
    }

    public GridLayoutSet numColumns(int n) {
        l.numColumns = n;
        return this;
    }

    public GridLayoutSet marginLeft(int n) {
        l.marginLeft = n;
        return this;
    }

    public GridLayoutSet marginTop(int n) {
        l.marginTop = n;
        return this;
    }

    public GridLayoutSet marginRight(int n) {
        l.marginRight = n;
        return this;
    }

    public GridLayoutSet marginBottom(int n) {
        l.marginBottom = n;
        return this;
    }

    public GridLayoutSet marginWidth(int n) {
        l.marginWidth = n;
        return this;
    }

    public GridLayoutSet marginHeight(int n) {
        l.marginHeight = n;
        return this;
    }

    public GridLayout getGridLayout() {
        return l;
    }
}
