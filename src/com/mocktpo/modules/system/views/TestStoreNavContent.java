package com.mocktpo.modules.system.views;

import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.modules.system.widgets.RemoteTestCard;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.*;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.vo.RemoteTestInfoVo;
import com.mocktpo.vo.TestTagVo;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TestStoreNavContent extends Composite {

    /* Constants */

    private static final int TAG_LABEL_WIDTH = 90;
    private static final int TAG_LABEL_HEIGHT = 28;

    private static final int HTTP_STATUS_OK = 200;
    private static final String TEST_TAGS_URL = "http://localhost:8080/website/api/v1/test-tags";
    private static final String TESTS_URL = "http://localhost:8080/website/api/v1/tests";

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Widgets */

    private Composite toolBar;
    private ImageButton checkUpdatesButton;
    private Composite tagsComposite;
    private CLabel defaultTagLabel;
    private List<CLabel> tagLabels;

    private ScrolledComposite sc;
    private Composite body;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestStoreNavContent(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        init();
    }

    private void init() {
        golbal();
        initToolBar();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this).marginWidth(0).marginHeight(0).spacing(0);
    }

    /*
     * ==================================================
     *
     * Tool Bar Initialization
     *
     * ==================================================
     */

    private void initToolBar() {
        toolBar = new Composite(this, SWT.NONE);
        FormDataSet.attach(toolBar).atLeft().atTop().atRight();
        CompositeSet.decorate(toolBar).setBackground(MT.COLOR_GREY_LIGHTEN_4);
        FormLayoutSet.layout(toolBar).marginWidth(10).marginHeight(10).spacing(0);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(toolBar).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        checkUpdatesButton = new ImageButton(toolBar, SWT.NONE, MT.IMAGE_SYSTEM_CHECK_UPDATES, MT.IMAGE_SYSTEM_CHECK_UPDATES_HOVER, MT.IMAGE_SYSTEM_CHECK_UPDATES_DISABLED);
        FormDataSet.attach(checkUpdatesButton).atLeft().atTop();
        checkUpdatesButton.addMouseListener(new CheckUpdatesButtonMouseAdapter());

        tagsComposite = new Composite(toolBar, SWT.WRAP);
        FormDataSet.attach(tagsComposite).atLeftTo(checkUpdatesButton, 20).atTop().atRight().atBottom();
        RowLayoutSet.layout(tagsComposite).marginWidth(0).marginHeight(0).spacing(0).wrap(false);
    }

    /*
     * ==================================================
     *
     * Body Initialization
     *
     * ==================================================
     */

    private void initBody() {
        sc = new ScrolledComposite(this, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(toolBar).atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        CompositeSet.decorate(body).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        GridLayoutSet.layout(body).numColumns(4).makeColumnsEqualWidth(true).marginWidth(10).marginHeight(10).horizontalSpacing(10).verticalSpacing(10);

        sc.setContent(body);
    }

    /*
     * ==================================================
     *
     * Refresh
     *
     * ==================================================
     */

    public void refresh() {
        if (!d.isDisposed()) {
            d.asyncExec(this::initTestTags);
        }
        if (!d.isDisposed()) {
            d.asyncExec(this::initCards);
        }
    }

    public void initTestTags() {
        for (Control c : tagsComposite.getChildren()) {
            c.dispose();
        }

        final CLabel tagsLabel = new CLabel(tagsComposite, SWT.NONE);
        RowDataSet.attach(tagsLabel).withWidth(40).withHeight(TAG_LABEL_HEIGHT);
        CLabelSet.decorate(tagsLabel).setImage(MT.IMAGE_SYSTEM_TAGS);

        defaultTagLabel = new CLabel(tagsComposite, SWT.NONE);
        RowDataSet.attach(defaultTagLabel).withWidth(TAG_LABEL_WIDTH).withHeight(TAG_LABEL_HEIGHT);
        CLabelSet.decorate(defaultTagLabel).setAlignment(SWT.CENTER).setBackground(MT.COLOR_INDIGO).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_WHITE).setText(msgs.getString("all"));
        defaultTagLabel.addMouseListener(new DefaultTagLabelMouseAdapter());

        List<TestTagVo> testTagVos = ConfigUtils.findAllTestTagVosInStoreBaseDir();
        tagLabels = new ArrayList<>();
        if (testTagVos != null) {
            for (TestTagVo testTagVo : testTagVos) {
                final CLabel tagLabel = new CLabel(tagsComposite, SWT.NONE);
                RowDataSet.attach(tagLabel).withWidth(TAG_LABEL_WIDTH).withHeight(TAG_LABEL_HEIGHT);
                CLabelSet.decorate(tagLabel).setAlignment(SWT.CENTER).setBackground(MT.COLOR_WHITE).setData("tagId", testTagVo.getTagId()).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_4).setText(testTagVo.getTitle());
                tagLabel.addMouseListener(new TagLabelMouseAdapter());
                tagLabels.add(tagLabel);
            }
        }

        tagsComposite.layout();
    }

    private void initCards() {
        for (Control c : body.getChildren()) {
            c.dispose();
        }
        List<RemoteTestInfoVo> remoteTestInfoVos = ConfigUtils.findAllRemoteTestInfoVosInStoreBaseDir();
        for (RemoteTestInfoVo remoteTestInfoVo : remoteTestInfoVos) {
            RemoteTestCard card = new RemoteTestCard(body, SWT.NONE, remoteTestInfoVo);
            GridDataSet.attach(card).fillHorizontal();
        }
        body.layout();
        sc.setMinSize(body.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        sc.setOrigin(0, 0);
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class DefaultTagLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            for (CLabel tagLabel : tagLabels) {
                CLabelSet.decorate(tagLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GREY_DARKEN_4);
            }
            CLabelSet.decorate(defaultTagLabel).setBackground(MT.COLOR_INDIGO).setForeground(MT.COLOR_WHITE);
        }
    }

    private class TagLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            for (CLabel tagLabel : tagLabels) {
                if (e.widget.equals(tagLabel)) {
                    CLabelSet.decorate(tagLabel).setBackground(MT.COLOR_INDIGO).setForeground(MT.COLOR_WHITE);
                } else {
                    CLabelSet.decorate(tagLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GREY_DARKEN_4);
                }
            }
            CLabelSet.decorate(defaultTagLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GREY_DARKEN_4);
        }
    }

    private class CheckUpdatesButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            checkUpdates();
        }
    }

    private void checkUpdates() {
        new Thread(() -> {
            d.asyncExec(() -> checkUpdatesButton.setEnabled(false));

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            try {
                URL url = new URL(TEST_TAGS_URL);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(6000);
                c.setReadTimeout(3000);
                c.setDoOutput(true);
                c.connect();
                int httpStatus = c.getResponseCode();
                switch (httpStatus) {
                    case HTTP_STATUS_OK:
                        logger.info("Http status: OK");
                        ConfigUtils.updateAllTestTagVosInStoreBaseDir(IOUtils.toString(c.getInputStream(), "utf-8"));
                        refresh();
                        break;
                    default:
                        logger.info("Http status: {}", httpStatus);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                URL url = new URL(TESTS_URL);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(6000);
                c.setReadTimeout(3000);
                c.setDoOutput(true);
                c.connect();
                int httpStatus = c.getResponseCode();
                switch (httpStatus) {
                    case HTTP_STATUS_OK:
                        logger.info("Http status: OK");
                        ConfigUtils.updateAllRemoteTestInfoVosInStoreBaseDir(IOUtils.toString(c.getInputStream(), "utf-8"));
                        refresh();
                        break;
                    default:
                        logger.info("Http status: {}", httpStatus);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            d.asyncExec(() -> checkUpdatesButton.setEnabled(true));

        }).start();
    }
}
