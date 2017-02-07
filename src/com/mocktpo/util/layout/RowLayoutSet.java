package com.mocktpo.util.layout;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

public class RowLayoutSet {

    private RowLayout l;

    public static RowLayoutSet layout(Composite c) {
        return new RowLayoutSet(c);
    }

    private RowLayoutSet(Composite c) {
        l = new RowLayout();
        c.setLayout(l);
    }

    public RowLayoutSet wrap(boolean b) {
        l.wrap = b;
        return this;
    }

    public RowLayoutSet justify(boolean b) {
        l.justify = b;
        return this;
    }

    public RowLayoutSet marginLeft(int n) {
        l.marginLeft = n;
        return this;
    }

    public RowLayoutSet marginTop(int n) {
        l.marginTop = n;
        return this;
    }

    public RowLayoutSet marginRight(int n) {
        l.marginRight = n;
        return this;
    }

    public RowLayoutSet marginBottom(int n) {
        l.marginBottom = n;
        return this;
    }

    public RowLayoutSet marginWidth(int n) {
        l.marginWidth = n;
        return this;
    }

    public RowLayoutSet marginHeight(int n) {
        l.marginHeight = n;
        return this;
    }

    public RowLayoutSet spacing(int n) {
        l.spacing = n;
        return this;
    }

    public RowLayout getRowLayout() {
        return l;
    }
}
