package com.mocktpo.modules.system.views;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.modules.system.widgets.RemoteTestCard;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import com.mocktpo.util.layout.*;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
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

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.ResourceBundle;

public class TestStoreNavContent extends Composite {

    /* Constants */

    private static final int TAG_LABEL_WIDTH = 90;
    private static final int TAG_LABEL_HEIGHT = 28;

    private static final int HTTP_STATUS_OK = 200;
    private static final String TEST_TAGS_URL = "http://localhost:8080/website/api/v1/test-tags";

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Widgets */

    private Composite toolBar;
    private ImageButton refreshButton;

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
        FormLayoutSet.layout(toolBar).marginWidth(20).marginHeight(10).spacing(0);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(toolBar).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        refreshButton = new ImageButton(toolBar, SWT.NONE, MT.IMAGE_SYSTEM_REFRESH, MT.IMAGE_SYSTEM_REFRESH_HOVER);
        FormDataSet.attach(refreshButton).atLeft().atTop();
        refreshButton.addMouseListener(new RefreshButtonMouseAdapter());

        final Composite tagsComposite = new Composite(toolBar, SWT.WRAP);
        FormDataSet.attach(tagsComposite).atLeftTo(refreshButton, 20).atRight();
        RowLayoutSet.layout(tagsComposite).marginWidth(0).marginHeight(0).spacing(0).wrap(false);

        final Label tagsLabel = new Label(tagsComposite, SWT.NONE);
        RowDataSet.attach(tagsLabel).withWidth(40).withHeight(TAG_LABEL_HEIGHT);
        LabelSet.decorate(tagsLabel).setImage(MT.IMAGE_SYSTEM_TAGS);

        for (int i = 0; i < 4; i++) {
            final CLabel tagLabel = new CLabel(tagsComposite, SWT.NONE);
            RowDataSet.attach(tagLabel).withWidth(TAG_LABEL_WIDTH).withHeight(TAG_LABEL_HEIGHT);
            CLabelSet.decorate(tagLabel).setAlignment(SWT.CENTER).setBackground(MT.COLOR_GREY_DARKEN_4).setFont(MT.FONT_SMALL).setText(msgs.getString("reading"));
            tagLabel.addMouseListener(new TagLabelMouseAdapter());
        }
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
        GridLayoutSet.layout(body).numColumns(4).makeColumnsEqualWidth(true).marginWidth(20).marginHeight(20).horizontalSpacing(20).verticalSpacing(20);

        sc.setContent(body);
    }

    /*
     * ==================================================
     *
     * Remote Test Card Operations
     *
     * ==================================================
     */

    public void refreshCards() {
        for (Control c : body.getChildren()) {
            c.dispose();
        }
        if (!d.isDisposed()) {
            d.asyncExec(this::initCards);
        }
    }

    private void initCards() {
        try {
            File[] testDirs = new File(this.getClass().getResource(URLDecoder.decode(RC.TESTS_DATA_DIR, "utf-8")).toURI()).listFiles(File::isDirectory);
            for (File testDir : testDirs) {
                String fileAlias = testDir.getName();
                URL url = ConfigUtils.class.getResource(URLDecoder.decode(RC.TESTS_DATA_DIR + fileAlias + MT.STRING_SLASH + fileAlias + RC.JSON_FILE_TYPE_SUFFIX, "utf-8"));
                if (url != null) {
                    RemoteTestCard card = new RemoteTestCard(body, SWT.NONE, testDir.getName());
                    GridDataSet.attach(card).fillHorizontal();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private class TagLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            String text = ((CLabel) e.widget).getText();
            if (msgs.getString("reading").equals(text)) {
                // toReadingReportView();
            } else if (msgs.getString("listening").equals(text)) {
                // toListeningReportView();
            } else if (msgs.getString("speaking").equals(text)) {
                // toSpeakingReportView();
            } else if (msgs.getString("writing").equals(text)) {
                // toWritingReportView();
            }
        }
    }

    private class RefreshButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            refresh();
        }
    }

    private void refresh() {
        new Thread(() -> {
            d.asyncExec(() -> {
                refreshButton.setEnabled(false);
            });
            try {
                Thread.sleep(1000);
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
                        List<TestTagVo> testTagVos = JSON.parseObject(IOUtils.toString(c.getInputStream(), "utf-8"), new TypeReference<List<TestTagVo>>() {
                        });
                        logger.info("TestTagVos:\n{}", testTagVos);
                        break;
                    default:
                        logger.info("Http status: {}", httpStatus);
                }
            } catch (Exception ex) {
                d.asyncExec(() -> refreshButton.setEnabled(true));
                ex.printStackTrace();
            }
        }).start();
    }
}
