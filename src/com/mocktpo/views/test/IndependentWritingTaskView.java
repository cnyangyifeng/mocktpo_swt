package com.mocktpo.views.test;

import com.mocktpo.pages.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.UserTestPersistenceUtils;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.widgets.*;
import com.mocktpo.widgets.ImageButton;
import com.mocktpo.widgets.VolumeControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Scale;

public class IndependentWritingTaskView extends SashTestView {

    /* Constants */

    private static final int WORD_COUNT_LABEL_WIDTH = 160;

    /* Widgets */

    private VolumeControl volumeControl;

    private CLabel wordCountLabel;
    private StyledText writingTextWidget;

    /* Properties */

    private int wordCount;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public IndependentWritingTaskView(TestPage page, int style) {
        super(page, style);
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    @Override
    public void updateHeader() {
        final ImageButton nextOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_NEXT_OVAL, MT.IMAGE_NEXT_OVAL_HOVER, MT.IMAGE_NEXT_OVAL_DISABLED);
        FormDataSet.attach(nextOvalButton).atRight(10).atTop(10);
        nextOvalButton.addMouseListener(new NextOvalButtonMouseAdapter());

        final ImageButton helpOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(helpOvalButton).atRightTo(nextOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        helpOvalButton.addMouseListener(new HelpOvalButtonMouseAdapter());

        final ImageButton volumeOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(volumeOvalButton).atRightTo(helpOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        volumeOvalButton.addMouseListener(new VolumeOvalButtonMouseAdapter());

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(volumeOvalButton, 0, SWT.BOTTOM).atRightTo(volumeOvalButton, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTestSession().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionAdapter());

        // TODO Removes the continue debug button

        final ImageButton continueDebugButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE_DEBUG, MT.IMAGE_CONTINUE_DEBUG_HOVER);
        FormDataSet.attach(continueDebugButton).atRightTo(volumeOvalButton, 16).atTopTo(volumeOvalButton, 8, SWT.TOP);
        continueDebugButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent mouseEvent) {
                release();
                UserTestPersistenceUtils.saveToNextView(IndependentWritingTaskView.this);
                page.resume();
            }
        });
    }

    @Override
    public void updateLeft() {
        final StyledText directionsTextWidget = new StyledText(left, SWT.BORDER | SWT.WRAP);
        FormDataSet.attach(directionsTextWidget).atLeft(10).atTop(10).atRight(10).withWidth(ScreenUtils.getHalfClientWidth(d));
        StyledTextSet.decorate(directionsTextWidget).setBackground(MT.COLOR_HIGHLIGHTED).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setMargins(5).setText(vo.getStyledText("directions").getText());
        StyleRangeUtils.decorate(directionsTextWidget, vo.getStyledText("directions").getStyles());

        final StyledText questionTextWidget = new StyledText(left, SWT.WRAP);
        FormDataSet.attach(questionTextWidget).atLeftTo(directionsTextWidget, 0, SWT.LEFT).atTopTo(directionsTextWidget, 10).atRightTo(directionsTextWidget, 0, SWT.RIGHT).withWidth(ScreenUtils.getHalfClientWidth(d));
        StyledTextSet.decorate(questionTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("question").getText());
        StyleRangeUtils.decorate(questionTextWidget, vo.getStyledText("question").getStyles());
    }

    @Override
    public void updateRight() {
        Button copyButton = new Button(right, SWT.PUSH);
        FormDataSet.attach(copyButton).atLeft(10).atTop(10).withHeight(LC.BUTTON_HEIGHT_HINT_2);
        ButtonSet.decorate(copyButton).setText(msgs.getString("copy"));
        copyButton.addSelectionListener(new CopyButtonSelectionAdapter());

        Button cutButton = new Button(right, SWT.PUSH);
        FormDataSet.attach(cutButton).atLeftTo(copyButton, 10).atTopTo(copyButton, 0, SWT.TOP).withHeight(LC.BUTTON_HEIGHT_HINT_2);
        ButtonSet.decorate(cutButton).setText(msgs.getString("cut"));
        cutButton.addSelectionListener(new CutButtonSelectionAdapter());

        Button pasteButton = new Button(right, SWT.PUSH);
        FormDataSet.attach(pasteButton).atLeftTo(cutButton, 10).atTopTo(copyButton, 0, SWT.TOP).withHeight(LC.BUTTON_HEIGHT_HINT_2);
        ButtonSet.decorate(pasteButton).setText(msgs.getString("paste"));
        pasteButton.addSelectionListener(new PasteButtonSelectionAdapter());

        wordCountLabel = new CLabel(right, SWT.NONE);
        FormDataSet.attach(wordCountLabel).atTopTo(copyButton, 0, SWT.TOP).atRight(10).atBottomTo(pasteButton, 0, SWT.BOTTOM).withWidth(WORD_COUNT_LABEL_WIDTH);
        CLabelSet.decorate(wordCountLabel).setAlignment(SWT.RIGHT).setFont(MT.FONT_SMALL).setText(msgs.getString("word_count") + MT.STRING_SPACE + wordCount);

        int reserved = 10;
        writingTextWidget = new StyledText(right, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(writingTextWidget).atLeftTo(copyButton, 0, SWT.LEFT).atTopTo(pasteButton, 10).atBottom(10).withWidth(ScreenUtils.getHalfClientWidth(d) - reserved);
        StyledTextSet.decorate(writingTextWidget).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setMargins(5);
        writingTextWidget.addModifyListener(new WritingTextModifyListener());
        KeyBindingSet.bind(writingTextWidget).selectAll();

        updateWidgetsForAnswers();
    }

    private void updateWidgetsForAnswers() {
        StyledTextSet.decorate(writingTextWidget).setText(answerText);
        logger.info("[Independent Writing Task {}] Answer: {}", vo.getQuestionNumberInSection(), answerText);
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class NextOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            UserTestPersistenceUtils.saveToNextView(IndependentWritingTaskView.this);
            page.resume();
        }
    }

    private class HelpOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
        }
    }

    private class VolumeOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            volumeControlVisible = !volumeControlVisible;
            CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
            UserTestPersistenceUtils.saveVolumeControlVisibility(IndependentWritingTaskView.this);
        }
    }

    private class VolumeControlSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            Scale s = (Scale) e.widget;
            double selection = s.getSelection(), maximum = s.getMaximum();
            double volume = selection / maximum;
            UserTestPersistenceUtils.saveVolume(IndependentWritingTaskView.this, volume);
            setAudioVolume(volume);
        }
    }

    private class CopyButtonSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            writingTextWidget.copy();
        }
    }

    private class CutButtonSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            writingTextWidget.cut();
        }
    }

    private class PasteButtonSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            writingTextWidget.paste();
        }
    }

    private class WritingTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            wordCount = WordCountUtils.count(writingTextWidget.getText());
            CLabelSet.decorate(wordCountLabel).setText(msgs.getString("word_count") + MT.STRING_SPACE + wordCount);
            answerText = writingTextWidget.getText();
            UserTestPersistenceUtils.saveAnswer(IndependentWritingTaskView.this, answerText);
        }
    }
}
