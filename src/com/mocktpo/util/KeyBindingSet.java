package com.mocktpo.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyledText;

public class KeyBindingSet {

    private StyledText c;

    public static KeyBindingSet bind(StyledText c) {
        return new KeyBindingSet(c);
    }

    private KeyBindingSet(StyledText c) {
        this.c = c;
    }

    public KeyBindingSet selectAll() {
        c.setKeyBinding(SWT.MOD1 | 'a', ST.SELECT_ALL);
        return this;
    }

    public KeyBindingSet traverse() {
        c.addTraverseListener(e -> {
            if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
                e.doit = true;
            }
        });
        return this;
    }
}
