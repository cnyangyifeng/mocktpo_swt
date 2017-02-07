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

    public SectionsComposite(Composite parent, int style) {
        super(parent, style);
        init();
    }

    private void init() {
        golbal();
        initBody();
    }

    private void golbal() {
        GridLayoutSet.layout(this).numColumns(2).makeColumnsEqualWidth(true);
    }

    private void initBody() {
        readingCheckWidget = new CheckWidget(this, SWT.NONE, msgs.getString("reading"));
        listeningCheckWidget = new CheckWidget(this, SWT.NONE, msgs.getString("listening"));
        speakingCheckWidget = new CheckWidget(this, SWT.NONE, msgs.getString("speaking"));
        writingcheckWidget = new CheckWidget(this, SWT.NONE, msgs.getString("writing"));
    }

    public boolean isReadingChecked() {
        return readingCheckWidget.isChecked();
    }

    public boolean isListeningChecked() {
        return listeningCheckWidget.isChecked();
    }

    public boolean isSpeakingChecked() {
        return speakingCheckWidget.isChecked();
    }

    public boolean isWritingChecked() {
        return writingcheckWidget.isChecked();
    }

    private class CheckWidget extends Composite {

        private Label checkLabel, choiceLabel;
        private boolean checked;
        private String text;

        public CheckWidget(Composite parent, int style, String text) {
            super(parent, style);
            this.checked = true;
            this.text = text;
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
            checkLabel.addMouseListener(new ChooseAnswerAdapter());
            checkLabel.addMouseTrackListener(new CheckWidgetMouseTrackAdapter());

            choiceLabel = new Label(this, SWT.WRAP);
            LabelSet.decorate(choiceLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_BLACK).setText(text);
            choiceLabel.addMouseListener(new ChooseAnswerAdapter());
            choiceLabel.addMouseTrackListener(new CheckWidgetMouseTrackAdapter());
        }

        public boolean isChecked() {
            return checked;
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
