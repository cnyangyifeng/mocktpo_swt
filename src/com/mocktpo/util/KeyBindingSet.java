package com.mocktpo.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;

public class KeyBindingSet {

    private StyledText c;

    public static KeyBindingSet bind(StyledText c) {
        return new KeyBindingSet(c);
    }

    private KeyBindingSet(StyledText c) {
        this.c = c;
    }

    public KeyBindingSet selectAll() {

        if (PlatformUtils.isMac()) {
            c.setKeyBinding(SWT.COMMAND | 'a', ST.SELECT_ALL);
        } else {
            c.setKeyBinding(SWT.CTRL | 'a', ST.SELECT_ALL);
        }

        return this;
    }

    public KeyBindingSet traverse() {

        c.addTraverseListener(new TraverseListener() {
            @Override
            public void keyTraversed(TraverseEvent e) {
                if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
                    e.doit = true;
                }
            }
        });

        return this;
    }

}
