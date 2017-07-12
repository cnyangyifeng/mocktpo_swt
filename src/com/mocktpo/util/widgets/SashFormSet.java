package com.mocktpo.util.widgets;

import com.mocktpo.util.ResourceManager;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;

public class SashFormSet {

    private SashForm c;

    public static SashFormSet decorate(SashForm c) {
        return new SashFormSet(c);
    }

    private SashFormSet(SashForm c) {
        this.c = c;
    }

    /*
     * ==================================================
     *
     * Background Settings
     *
     * ==================================================
     */

    public SashFormSet setBackground(int i) {
        c.setBackground(ResourceManager.getColor(i));
        return this;
    }

    public SashFormSet setBackground(Color color) {
        c.setBackground(color);
        return this;
    }


    /*
     * ==================================================
     *
     * Form Weights Settings
     *
     * ==================================================
     */

    public SashFormSet setWeights(int[] weights) {
        c.setWeights(weights);
        return this;
    }
}
