package com.mocktpo.widget;

import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.LabelSet;
import com.mocktpo.util.constants.MT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ResourceBundle;

public class DroppableAnswerComposite extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Label label;
    private PropertyChangeSupport support;

    /* Properties */

    private int answer;

    public DroppableAnswerComposite(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.support = new PropertyChangeSupport(this);
        init();
    }

    private void init() {
        golbal();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this);
    }

    private void initBody() {
        label = new Label(this, SWT.WRAP);
        FormDataSet.attach(label).atLeft().atTop().atRight().atBottom();
        LabelSet.decorate(label).setFont(MT.FONT_MEDIUM);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void addMouseListener(MouseListener listener) {
        label.addMouseListener(listener);
    }

    /**************************************************
     * 
     * Getters and Setters
     * 
     **************************************************/

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        support.firePropertyChange("answer", this.answer, answer);
        this.answer = answer;
        label.setData(MT.KEY_CHOICE, this.answer);
    }

    public String getText() {
        return label.getText();
    }

    public void setText(String value) {
        label.setText(value);
    }
}
