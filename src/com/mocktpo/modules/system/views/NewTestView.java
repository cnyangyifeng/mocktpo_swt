package com.mocktpo.modules.system.views;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.modules.system.widgets.TestCard;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.ZipUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ResourceBundle;

public class NewTestView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Widgets */

    private Composite toolBar;
    private ScrolledComposite sc;
    private Composite body;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public NewTestView(Composite parent, int style) {
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
        CompositeSet.decorate(toolBar).setBackground(MT.COLOR_WHITE_SMOKE);
        FormLayoutSet.layout(toolBar).marginWidth(10).marginHeight(10).spacing(0);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(toolBar).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        final ImageButton importButton = new ImageButton(toolBar, SWT.NONE, MT.IMAGE_SYSTEM_IMPORT, MT.IMAGE_SYSTEM_IMPORT_HOVER);
        FormDataSet.attach(importButton).atLeft().atTop();
        importButton.addMouseListener(new ImportButtonMouseAdapter());
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
     * Test Card Operations
     *
     * ==================================================
     */

    public void refreshCards() {
        for (Control c : body.getChildren()) {
            c.dispose();
        }
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    initCards();
                }
            });
        }
    }

    private void initCards() {
        try {
            File[] testDirs = new File(this.getClass().getResource(URLDecoder.decode(RC.TESTS_DATA_DIR, "utf-8")).toURI()).listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory();
                }
            });
            for (File testDir : testDirs) {
                String testDirName = testDir.getName();
                URL url = ConfigUtils.class.getResource(URLDecoder.decode(RC.TESTS_DATA_DIR + testDirName + MT.STRING_SLASH + testDirName + RC.JSON_FILE_TYPE_SUFFIX, "utf-8"));
                if (url != null) {
                    TestCard card = new TestCard(body, SWT.NONE, testDir.getName());
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

    private class ImportButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            FileDialog dialog = new FileDialog(MyApplication.get().getWindow().getShell(), SWT.OPEN);
            dialog.setFilterNames(new String[]{"Zip Archive (*.zip)"});
            dialog.setFilterExtensions(new String[]{"*.zip"});
            String fullDestFileName = dialog.open();
            ZipUtils.unzip(fullDestFileName);
            String fileAlias = FilenameUtils.removeExtension(FilenameUtils.getName(fullDestFileName));
            if (fileAlias != null) {
                refreshCards();
            }
        }
    }
}
