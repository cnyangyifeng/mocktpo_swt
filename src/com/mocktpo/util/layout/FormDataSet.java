package com.mocktpo.util.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Control;

public class FormDataSet {

    private static final int DENOMINATOR = 100;

    private FormData data;

    public static FormDataSet attach(Control c) {
        return new FormDataSet(c);
    }

    private FormDataSet(Control c) {
        data = new FormData();
        c.setLayoutData(data);
    }

    public FormDataSet atLeft() {
        return atLeft(0);
    }

    public FormDataSet atLeft(int margin) {
        data.left = new FormAttachment(0, margin);
        return this;
    }

    public FormDataSet atLeftTo(Control control) {
        return atLeftTo(control, 0);
    }

    public FormDataSet atLeftTo(Control control, int margin) {
        return atLeftTo(control, margin, SWT.DEFAULT);
    }

    public FormDataSet atLeftTo(Control control, int margin, int alignment) {
        data.left = new FormAttachment(control, margin, alignment);
        return this;
    }

    public FormDataSet fromLeft(int numerator) {
        return fromLeft(numerator, 0);
    }

    public FormDataSet fromLeft(int numerator, int margin) {
        data.left = new FormAttachment(numerator, margin);
        return this;
    }

    public FormDataSet atTop() {
        return atTop(0);
    }

    public FormDataSet atTop(int margin) {
        data.top = new FormAttachment(0, margin);
        return this;
    }

    public FormDataSet atTopTo(Control control) {
        return atTopTo(control, 0);
    }

    public FormDataSet atTopTo(Control control, int margin) {
        return atTopTo(control, margin, SWT.DEFAULT);
    }

    public FormDataSet atTopTo(Control control, int margin, int alignment) {
        data.top = new FormAttachment(control, margin, alignment);
        return this;
    }

    public FormDataSet fromTop(int numerator) {
        return fromTop(numerator, 0);
    }

    public FormDataSet fromTop(int numerator, int margin) {
        data.top = new FormAttachment(numerator, margin);
        return this;
    }

    public FormDataSet atRight() {
        return atRight(0);
    }

    public FormDataSet atRight(int margin) {
        data.right = new FormAttachment(DENOMINATOR, -margin);
        return this;
    }

    public FormDataSet atRightTo(Control control) {
        atRightTo(control, 0);
        return this;
    }

    public FormDataSet atRightTo(Control control, int margin) {
        return atRightTo(control, margin, SWT.DEFAULT);
    }

    public FormDataSet atRightTo(Control control, int margin, int alignment) {
        data.right = new FormAttachment(control, -margin, alignment);
        return this;
    }

    public FormDataSet fromRight(int numerator) {
        return fromRight(numerator, 0);
    }

    public FormDataSet fromRight(int numerator, int margin) {
        data.right = new FormAttachment(DENOMINATOR - numerator, -margin);
        return this;
    }

    public FormDataSet atBottom() {
        return atBottom(0);
    }

    public FormDataSet atBottom(int margin) {
        data.bottom = new FormAttachment(DENOMINATOR, -margin);
        return this;
    }

    public FormDataSet atBottomTo(Control control) {
        return atBottomTo(control, 0);
    }

    public FormDataSet atBottomTo(Control control, int margin) {
        return atBottomTo(control, margin, SWT.DEFAULT);
    }

    public FormDataSet atBottomTo(Control control, int margin, int alignment) {
        data.bottom = new FormAttachment(control, -margin, alignment);
        return this;
    }

    public FormDataSet fromBottom(int numerator) {
        return fromBottom(numerator, 0);
    }

    public FormDataSet fromBottom(int numerator, int margin) {
        data.bottom = new FormAttachment(DENOMINATOR - numerator, -margin);
        return this;
    }

    public FormDataSet withWidth(int width) {
        data.width = width;
        return this;
    }

    public FormDataSet withHeight(int height) {
        data.height = height;
        return this;
    }

    public FormData getFormData() {
        return data;
    }
}
