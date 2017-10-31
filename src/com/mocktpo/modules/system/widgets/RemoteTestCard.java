package com.mocktpo.modules.system.widgets;

import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.vo.TestVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import java.util.ResourceBundle;

public class RemoteTestCard extends Composite {

    /* Constants */

    private static final int SUPERSCRIPT_WIDTH = 120;
    private static final int SUPERSCRIPT_HEIGHT = 24;
    private static final int TITLE_WIDTH = 200;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Widgets */

    private ImageButton downloadButton;

    /* Properties */

    private String fileAlias;
    private TestVo testVo;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public RemoteTestCard(Composite parent, int style, String fileAlias) {
        super(parent, style);
        this.fileAlias = fileAlias;
        this.testVo = ConfigUtils.pullFromTest(fileAlias, TestVo.class);
        init();
    }

    private void init() {
        golbal();
        initWidgets();
    }

    private void golbal() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(this).marginWidth(10).marginHeight(10).spacing(0);
        this.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
    }

    private void initWidgets() {
        final Composite header = new Composite(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        FormLayoutSet.layout(header).marginWidth(0).marginHeight(0).spacing(0);

        final CLabel superscriptLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(superscriptLabel).atLeft().atTop().withWidth(SUPERSCRIPT_WIDTH).withHeight(SUPERSCRIPT_HEIGHT);
        CLabelSet.decorate(superscriptLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY20).setText(msgs.getString("test_paper"));

        final CLabel titleLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(titleLabel).atLeft().atTopTo(superscriptLabel, 10).withWidth(TITLE_WIDTH);
        CLabelSet.decorate(titleLabel).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_BLACK).setText(getTitle());

        final StarsComposite starsComposite = new StarsComposite(header, SWT.NONE, testVo.getStars());
        FormDataSet.attach(starsComposite).atLeft().atTopTo(titleLabel, 10).atRight();

        final Label divider1 = new Label(header, SWT.NONE);
        FormDataSet.attach(divider1).atLeft().atTopTo(starsComposite, 20).atRight().withHeight(1);
        LabelSet.decorate(divider1).setBackground(MT.COLOR_WINDOW_BACKGROUND);

        final Composite body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(header).atRight();
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(0);

        final CLabel creatorPreLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(creatorPreLabel).atLeft().atTop(10);
        CLabelSet.decorate(creatorPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("creator"));

        final CLabel creatorLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(creatorLabel).atLeftTo(creatorPreLabel, 10).atTopTo(creatorPreLabel, 0, SWT.TOP).atRight();
        CLabelSet.decorate(creatorLabel).setAlignment(SWT.RIGHT).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(getCreator());

        final CLabel versionPreLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(versionPreLabel).atLeft().atTopTo(creatorPreLabel, 10);
        CLabelSet.decorate(versionPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("version"));

        final CLabel versionLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(versionLabel).atLeftTo(versionPreLabel, 10).atTopTo(versionPreLabel, 0, SWT.TOP).atRight();
        CLabelSet.decorate(versionLabel).setAlignment(SWT.RIGHT).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(Double.toString(getVersion()));

        final Label divider2 = new Label(body, SWT.NONE);
        FormDataSet.attach(divider2).atLeft().atTopTo(versionPreLabel, 10).atRight().withHeight(1);
        LabelSet.decorate(divider2).setBackground(MT.COLOR_WHITE_SMOKE);

        final Composite footer = new Composite(this, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atTopTo(body).atRight();
        FormLayoutSet.layout(footer).marginWidth(0).marginHeight(10).spacing(0);

        downloadButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_DOWNLOAD, MT.IMAGE_SYSTEM_DOWNLOAD_HOVER, MT.IMAGE_SYSTEM_PLEASE_WAIT);
        FormDataSet.attach(downloadButton).atLeft().atTop(10);
        downloadButton.addMouseListener(new DownloadButtonMouseAdapter());
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class DownloadButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            downloadButton.setEnabled(false);
        }
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public String getFileAlias() {
        return fileAlias;
    }

    public void setFileAlias(String fileAlias) {
        this.fileAlias = fileAlias;
    }

    private String getTitle() {
        String title = testVo.getTitle();
        if (StringUtils.isEmpty(title)) {
            title = msgs.getString("untitled");
        }
        return title;
    }

    private String getCreator() {
        String creator = testVo.getCreator();
        if (StringUtils.isEmpty(creator)) {
            creator = msgs.getString("unknown");
        }
        return creator;
    }

    private double getVersion() {
        double version = testVo.getVersion();
        if (version == 0) {
            return 1.0;
        } else {
            return version;
        }
    }
}
