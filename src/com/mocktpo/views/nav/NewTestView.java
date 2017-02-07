package com.mocktpo.views.nav;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.orm.mapper.ActivationCodeMapper;
import com.mocktpo.orm.mapper.UserTestSessionMapper;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.*;
import com.mocktpo.vo.TestSchemaVo;
import com.mocktpo.widgets.TestCard;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.net.URLDecoder;
import java.util.List;
import java.util.ResourceBundle;

public class NewTestView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

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
        FormLayoutSet.layout(this);
    }

    private void initToolBar() {
        toolBar = new Composite(this, SWT.NONE);
        FormDataSet.attach(toolBar).atLeft().atTop().atRight();
        CompositeSet.decorate(toolBar).setBackground(MT.COLOR_WHITE_SMOKE);
        FormLayoutSet.layout(toolBar).marginWidth(10).marginHeight(5).spacing(5);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(toolBar).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        final Button sortByNameButton = new Button(toolBar, SWT.PUSH);
        FormDataSet.attach(sortByNameButton).atLeft().atTop().withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(sortByNameButton).setText(msgs.getString("sort_by_name"));
        sortByNameButton.addSelectionListener(new SortButtonSelectionAdapter());

        final Button importButton = new Button(toolBar, SWT.PUSH);
        FormDataSet.attach(importButton).atTop().atRight().withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(importButton).setText(msgs.getString("import"));
        importButton.addSelectionListener(new ImportButtonSelectionAdapter());
    }

    private void initBody() {
        sc = new ScrolledComposite(this, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(toolBar).atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        sc.setContent(body);

        CompositeSet.decorate(body).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        GridLayoutSet.layout(body).numColumns(4).makeColumnsEqualWidth(true).marginWidth(20).marginHeight(20).horizontalSpacing(20).verticalSpacing(20);

        initCards();
    }

    private void initCards() {
        UserTestSessionMapper userTestSessionMapper = MyApplication.get().getSqlSession().getMapper(UserTestSessionMapper.class);
        List<UserTestSession> list = userTestSessionMapper.find();
        for (UserTestSession userTestSession : list) {
            String fileAlias = userTestSession.getFileAlias();
            try {
                File testPath = new File(this.getClass().getResource(URLDecoder.decode(RC.TESTS_DATA_DIR + fileAlias, "utf-8")).toURI());
                if (testPath.exists() && testPath.isDirectory()) {
                    TestCard card = new TestCard(body, SWT.NONE, userTestSession);
                    GridDataSet.attach(card).fillHorizontal();
                }
            } catch (Exception e) {
                logger.error("Failed to find \"{}\" test data files according to database records.", fileAlias);
            }
        }
        body.layout();
        sc.setMinSize(body.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    private void removeCards() {
        for (Control c : body.getChildren()) {
            c.dispose();
        }
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class SortButtonSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            logger.info("Test cards sorted by name successfully.");
        }
    }

    private class ImportButtonSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            FileDialog dialog = new FileDialog(MyApplication.get().getWindow().getShell(), SWT.OPEN);
            dialog.setFilterNames(new String[]{"Zip Archive (*.zip)"});
            dialog.setFilterExtensions(new String[]{"*.zip"});
            String absoluteFileName = dialog.open();
            UnzipUtils.unzip(absoluteFileName);
            String fileAlias = FilenameUtils.removeExtension(FilenameUtils.getName(absoluteFileName));
            if (null == fileAlias) {
                return;
            }
            TestSchemaVo testSchema = ConfigUtils.load(fileAlias, TestSchemaVo.class);
            UserTestPersistenceUtils.reset(fileAlias, testSchema);
            removeCards();
            initCards();
        }
    }
}
