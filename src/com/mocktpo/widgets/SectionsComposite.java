package com.mocktpo.widgets;

import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.layout.RowLayoutSet;
import com.mocktpo.util.widgets.LabelSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import java.util.ResourceBundle;

public class SectionsComposite extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Widgets */

    private CheckWidget readingCheckWidget, listeningCheckWidget, speakingCheckWidget, writingcheckWidget;

    /* Properties */

    private int numColumns;
    private boolean enabled;

    public SectionsComposite(Composite parent, int style, int numColumns, boolean enabled) {
        super(parent, style);
        this.numColumns = numColumns;
        this.enabled = enabled;
        init();
    }

    private void init() {
        golbal();
        initBody();
    }

    private void golbal() {
        GridLayoutSet.layout(this).numColumns(numColumns).makeColumnsEqualWidth(true);
    }

    private void initBody() {
        readingCheckWidget = new CheckWidget(this, SWT.NONE, msgs.getString("reading"), enabled);
        listeningCheckWidget = new CheckWidget(this, SWT.NONE, msgs.getString("listening"), enabled);
        speakingCheckWidget = new CheckWidget(this, SWT.NONE, msgs.getString("speaking"), enabled);
        writingcheckWidget = new CheckWidget(this, SWT.NONE, msgs.getString("writing"), enabled);
    }

    public boolean isReadingSectionEnabled() {
        return readingCheckWidget.isChecked();
    }

    public boolean isListeningSectionEnabled() {
        return listeningCheckWidget.isChecked();
    }

    public boolean isSpeakingSectionEnabled() {
        return speakingCheckWidget.isChecked();
    }

    public boolean isWritingSectionEnabled() {
        return writingcheckWidget.isChecked();
    }

    private class CheckWidget extends Composite {

        private Label checkLabel, choiceLabel;
        private boolean checked, enabled;
        private String text;

        private ChooseAnswerAdapter chooseAnswerAdapter;
        private CheckWidgetMouseTrackAdapter checkWidgetMouseTrackAdapter;

        public CheckWidget(Composite parent, int style, String text, boolean enabled) {
            super(parent, style);
            this.checked = true;
            this.enabled = enabled;
            this.text = text;
            this.chooseAnswerAdapter = new ChooseAnswerAdapter();
            this.checkWidgetMouseTrackAdapter = new CheckWidgetMouseTrackAdapter();
            init();
        }

        private void init() {
            golbal();
            initBody();
        }

        private void golbal() {
            RowLayoutSet.layout(this);
        }

        private void initBody() {
            checkLabel = new Label(this, SWT.NONE);
            LabelSet.decorate(checkLabel).setImage(MT.IMAGE_BOXED);
            if (enabled) {
                checkLabel.addMouseListener(chooseAnswerAdapter);
                checkLabel.addMouseTrackListener(checkWidgetMouseTrackAdapter);
            }
            choiceLabel = new Label(this, SWT.WRAP);
            LabelSet.decorate(choiceLabel).setFont(MT.FONT_SMALL).setText(text);
            if (enabled) {
                LabelSet.decorate(choiceLabel).setForeground(MT.COLOR_BLACK);
                choiceLabel.addMouseListener(chooseAnswerAdapter);
                choiceLabel.addMouseTrackListener(checkWidgetMouseTrackAdapter);
            } else {
                LabelSet.decorate(choiceLabel).setForeground(MT.COLOR_GRAY40);
            }
        }

        public boolean isChecked() {
            return checked;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
            if (enabled) {
                checkLabel.addMouseListener(chooseAnswerAdapter);
                checkLabel.addMouseTrackListener(checkWidgetMouseTrackAdapter);
                choiceLabel.addMouseListener(chooseAnswerAdapter);
                choiceLabel.addMouseTrackListener(checkWidgetMouseTrackAdapter);
            } else {
                checkLabel.removeMouseListener(chooseAnswerAdapter);
                checkLabel.addMouseTrackListener(checkWidgetMouseTrackAdapter);
                choiceLabel.removeMouseListener(chooseAnswerAdapter);
                choiceLabel.addMouseTrackListener(checkWidgetMouseTrackAdapter);
            }
        }

        private class ChooseAnswerAdapter extends MouseAdapter {

            @Override
            public void mouseDown(MouseEvent e) {
                if (checked) {
                    LabelSet.decorate(checkLabel).setImage(MT.IMAGE_UNBOXED);
                } else {
                    LabelSet.decorate(checkLabel).setImage(MT.IMAGE_BOXED);
                }
                checked = !checked;
            }
        }

        private class CheckWidgetMouseTrackAdapter extends MouseTrackAdapter {

            public void mouseEnter(MouseEvent e) {
                LabelSet.decorate(choiceLabel).setForeground(MT.COLOR_DARK_BLUE);
            }

            public void mouseExit(MouseEvent e) {
                LabelSet.decorate(choiceLabel).setForeground(MT.COLOR_BLACK);
            }
        }
    }
}
