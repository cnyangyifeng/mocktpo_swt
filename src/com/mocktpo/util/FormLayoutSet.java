package com.mocktpo.util;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

public class FormLayoutSet {

    private FormLayout l;

    public static FormLayoutSet layout(Composite c) {
        return new FormLayoutSet(c);
    }

    private FormLayoutSet(Composite c) {
        l = new FormLayout();
        c.setLayout(l);
    }

    public FormLayoutSet marginLeft(int n) {
        l.marginLeft = n;
        return this;
    }

    public FormLayoutSet marginTop(int n) {
        l.marginTop = n;
        return this;
    }

    public FormLayoutSet marginRight(int n) {
        l.marginRight = n;
        return this;
    }

    public FormLayoutSet marginBottom(int n) {
        l.marginBottom = n;
        return this;
    }

    public FormLayoutSet marginWidth(int n) {
        l.marginWidth = n;
        return this;
    }

    public FormLayoutSet marginHeight(int n) {
        l.marginHeight = n;
        return this;
    }

    public FormLayoutSet spacing(int n) {
        l.spacing = n;
        return this;
    }

    public FormLayout getFormLayout() {
        return l;
    }
}
