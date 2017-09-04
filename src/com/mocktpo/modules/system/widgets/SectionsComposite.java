package com.mocktpo.modules.system.widgets;

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

    public SectionsComposite(Composite parent, int style, int numColumns, boolean enabled, boolean readingSelected, boolean listeningSelected, boolean speakingSelected, boolean writingSelected) {
        super(parent, style);
        GridLayoutSet.layout(this).numColumns(numColumns).makeColumnsEqualWidth(true).marginWidth(0).marginHeight(0).horizontalSpacing(10).verticalSpacing(10);
        readingCheckWidget = new CheckWidget(this, SWT.NONE, msgs.getString("reading"), enabled, readingSelected);
        listeningCheckWidget = new CheckWidget(this, SWT.NONE, msgs.getString("listening"), enabled, listeningSelected);
        speakingCheckWidget = new CheckWidget(this, SWT.NONE, msgs.getString("speaking"), enabled, speakingSelected);
        writingcheckWidget = new CheckWidget(this, SWT.NONE, msgs.getString("writing"), enabled, writingSelected);
    }

    public boolean isReadingSelected() {
        return readingCheckWidget.isSelected();
    }

    public boolean isListeningSelected() {
        return listeningCheckWidget.isSelected();
    }

    public boolean isSpeakingSelected() {
        return speakingCheckWidget.isSelected();
    }

    public boolean isWritingSelected() {
        return writingcheckWidget.isSelected();
    }

    private class CheckWidget extends Composite {

        private Label checkLabel, choiceLabel;
        private boolean enabled, selected;
        private String text;

        private ChooseAnswerAdapter chooseAnswerAdapter;
        private CheckWidgetMouseTrackAdapter checkWidgetMouseTrackAdapter;

        public CheckWidget(Composite parent, int style, String text, boolean enabled, boolean selected) {
            super(parent, style);
            this.text = text;
            this.enabled = enabled;
            this.selected = selected;
            this.chooseAnswerAdapter = new ChooseAnswerAdapter();
            this.checkWidgetMouseTrackAdapter = new CheckWidgetMouseTrackAdapter();
            init();
        }

        private void init() {
            golbal();
            initBody();
        }

        private void golbal() {
            RowLayoutSet.layout(this).marginWidth(0).marginHeight(0).spacing(10);
        }

        private void initBody() {
            checkLabel = new Label(this, SWT.NONE);
            if (enabled) {
                checkLabel.addMouseListener(chooseAnswerAdapter);
                checkLabel.addMouseTrackListener(checkWidgetMouseTrackAdapter);
                if (selected) {
                    LabelSet.decorate(checkLabel).setImage(MT.IMAGE_SYSTEM_BOXED);
                } else {
                    LabelSet.decorate(checkLabel).setImage(MT.IMAGE_SYSTEM_UNBOXED);
                }
            } else {
                if (selected) {
                    LabelSet.decorate(checkLabel).setImage(MT.IMAGE_SYSTEM_BOXED_DISABLED);
                } else {
                    LabelSet.decorate(checkLabel).setImage(MT.IMAGE_SYSTEM_UNBOXED_DISABLED);
                }
            }
            choiceLabel = new Label(this, SWT.WRAP);
            LabelSet.decorate(choiceLabel).setText(text);
            if (enabled) {
                LabelSet.decorate(choiceLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40);
                choiceLabel.addMouseListener(chooseAnswerAdapter);
                choiceLabel.addMouseTrackListener(checkWidgetMouseTrackAdapter);
            } else {
                if (selected) {
                    LabelSet.decorate(choiceLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40);
                } else {
                    LabelSet.decorate(choiceLabel).setFont(MT.FONT_SMALL_ITALIC).setForeground(MT.COLOR_GRAY60);
                }
            }
        }

        public boolean isSelected() {
            return selected;
        }

        private class ChooseAnswerAdapter extends MouseAdapter {

            @Override
            public void mouseDown(MouseEvent e) {
                if (selected) {
                    LabelSet.decorate(checkLabel).setImage(MT.IMAGE_SYSTEM_UNBOXED);
                } else {
                    LabelSet.decorate(checkLabel).setImage(MT.IMAGE_SYSTEM_BOXED);
                }
                selected = !selected;
            }
        }

        private class CheckWidgetMouseTrackAdapter extends MouseTrackAdapter {

            public void mouseEnter(MouseEvent e) {
                LabelSet.decorate(choiceLabel).setForeground(MT.COLOR_DARK_BLUE);
            }

            public void mouseExit(MouseEvent e) {
                LabelSet.decorate(choiceLabel).setForeground(MT.COLOR_GRAY40);
            }
        }
    }
}
