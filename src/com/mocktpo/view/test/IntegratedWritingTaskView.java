package com.mocktpo.view.test;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.ImageButton;
import com.mocktpo.widget.VolumeControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scale;

public class IntegratedWritingTaskView extends SashTestView2 {

    /* Constants */

    private static final int WORD_COUNT_LABEL_WIDTH = 160;

    /* Widgets */

    private VolumeControl volumeControl;

    private CLabel wordCountLabel;
    private StyledText writingText;

    /* Properties */

    private int wordCount;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public IntegratedWritingTaskView(TestPage page, int style) {
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

        final ImageButton nob = new ImageButton(header, SWT.NONE, MT.IMAGE_NEXT_OVAL, MT.IMAGE_NEXT_OVAL_HOVER, MT.IMAGE_NEXT_OVAL_DISABLED);
        FormDataSet.attach(nob).atRight(10).atTop(10);
        nob.addMouseListener(new NextOvalButtonMouseListener());

        final ImageButton hob = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(hob).atRightTo(nob).atTopTo(nob, 0, SWT.TOP);
        hob.addMouseListener(new HelpOvalButtonMouseListener());

        final ImageButton vob = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(vob).atRightTo(hob).atTopTo(nob, 0, SWT.TOP);
        vob.addMouseListener(new VolumeOvalButtonMouseListener());

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(vob, 0, SWT.BOTTOM).atRightTo(vob, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTest().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionListener());

        // TODO Removes the continue button

        final ImageButton cb = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE_DEBUG, MT.IMAGE_CONTINUE_DEBUG_HOVER);
        FormDataSet.attach(cb).atRightTo(vob, 16).atTopTo(vob, 8, SWT.TOP);
        cb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent mouseEvent) {

                release();

                UserTest ut = page.getUserTest();
                ut.setCompletionRate(100 * vo.getViewId() / page.getTestSchema().getViews().size());
                ut.setLastViewId(vo.getViewId() + 1);

                sqlSession.getMapper(UserTestMapper.class).update(ut);
                sqlSession.commit();

                page.resume(ut);
            }
        });
    }

    @Override
    public void updateTop() {

        final StyledText dt = new StyledText(top, SWT.BORDER | SWT.WRAP);
        FormDataSet.attach(dt).atLeft().atTop().atRight();
        StyledTextSet.decorate(dt).setBackground(MT.COLOR_HIGHLIGHTED).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setMargins(5).setText(vo.getStyledText("directions").getText());
        StyleRangeUtils.decorate(dt, vo.getStyledText("directions").getStyles());

        final StyledText qt = new StyledText(top, SWT.WRAP);
        FormDataSet.attach(qt).atLeft(5).atTopTo(dt).atRight(5);
        StyledTextSet.decorate(qt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("question").getText());
        StyleRangeUtils.decorate(qt, vo.getStyledText("question").getStyles());
    }

    @Override
    public void updateLeft() {

        final ScrolledComposite sc = new ScrolledComposite(left, SWT.H_SCROLL | SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTop().atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        final Composite c = new Composite(sc, SWT.NONE);
        FormLayoutSet.layout(c).marginWidth(10).marginTop(10).marginBottom(100);

        final StyledText pt = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(pt).atLeft().atTop().atBottom().withWidth(ScreenUtils.getHalfClientWidth(d));
        StyledTextSet.decorate(pt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("passage").getText());
        StyleRangeUtils.decorate(pt, vo.getStyledText("passage").getStyles());

        sc.setContent(c);
        sc.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    @Override
    public void updateRight() {

        Button copyButton = new Button(right, SWT.PUSH);
        FormDataSet.attach(copyButton).atLeft().atTop().withHeight(LC.BUTTON_HEIGHT_HINT_2);
        ButtonSet.decorate(copyButton).setCursor(MT.CURSOR_HAND).setText(msgs.getString("copy"));
        copyButton.addMouseListener(new CopyButtonMouseListener());

        Button cutButton = new Button(right, SWT.PUSH);
        FormDataSet.attach(cutButton).atLeftTo(copyButton).atTop().withHeight(LC.BUTTON_HEIGHT_HINT_2);
        ButtonSet.decorate(cutButton).setCursor(MT.CURSOR_HAND).setText(msgs.getString("cut"));
        cutButton.addMouseListener(new CutButtonMouseListener());

        Button pasteButton = new Button(right, SWT.PUSH);
        FormDataSet.attach(pasteButton).atLeftTo(cutButton).atTop().withHeight(LC.BUTTON_HEIGHT_HINT_2);
        ButtonSet.decorate(pasteButton).setCursor(MT.CURSOR_HAND).setText(msgs.getString("paste"));
        pasteButton.addMouseListener(new PasteButtonMouseListener());

        wordCountLabel = new CLabel(right, SWT.NONE);
        FormDataSet.attach(wordCountLabel).atTopTo(pasteButton, 0, SWT.TOP).atRight().atBottomTo(pasteButton, 0, SWT.BOTTOM).withWidth(WORD_COUNT_LABEL_WIDTH);
        CLabelSet.decorate(wordCountLabel).setAlignment(SWT.RIGHT).setFont(MT.FONT_SMALL).setText(msgs.getString("word_count") + MT.STRING_SPACE + wordCount);

        int reserved = 15;
        writingText = new StyledText(right, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(writingText).atLeftTo(copyButton, 0, SWT.LEFT).atTopTo(pasteButton).atBottom().withWidth(ScreenUtils.getHalfClientWidth(d) - reserved);
        StyledTextSet.decorate(writingText).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setMargins(5);
        writingText.addModifyListener(new WritingTextModifyListener());
        KeyBindingSet.bind(writingText).selectAll();
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class NextOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            release();

            UserTest ut = page.getUserTest();
            ut.setCompletionRate(100 * vo.getViewId() / page.getTestSchema().getViews().size());
            ut.setLastViewId(vo.getViewId() + 1);

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();

            page.resume(ut);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class HelpOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class VolumeOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            volumeControlVisible = !volumeControlVisible;
            CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);

            UserTest ut = page.getUserTest();
            ut.setVolumeControlHidden(!volumeControlVisible);

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class VolumeControlSelectionListener implements SelectionListener {

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }

        @Override
        public void widgetSelected(SelectionEvent e) {

            Scale s = (Scale) e.widget;

            double selection = s.getSelection(), maximum = s.getMaximum();
            double volume = selection / maximum;

            UserTest ut = page.getUserTest();
            ut.setVolume(volume);

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();

            setAudioVolume(volume);
        }
    }

    private class CopyButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            writingText.copy();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class CutButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            writingText.cut();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class PasteButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            writingText.paste();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class WritingTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            wordCount = WordCountUtils.count(writingText.getText());
            CLabelSet.decorate(wordCountLabel).setText(msgs.getString("word_count") + MT.STRING_SPACE + wordCount);
        }
    }
}
