package com.mocktpo.modules.system.widgets;

import com.mocktpo.MyApplication;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.PDFUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.vo.TestSchemaVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.util.ResourceBundle;

public class TestPaperCard extends Composite {

    /* Constants */

    private static final int TITLE_WIDTH = 200;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    private Display d;

    /* Properties */

    private String fileAlias;
    private TestSchemaVo testSchema;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestPaperCard(Composite parent, int style, String fileAlias) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.fileAlias = fileAlias;
        this.testSchema = ConfigUtils.load(fileAlias, TestSchemaVo.class);
        init();
    }

    private void init() {
        golbal();
        initWidgets();
    }

    private void golbal() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(this).marginWidth(10).marginHeight(10).spacing(0);
        // this.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
    }

    private void initWidgets() {
        final Composite header = new Composite(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        FormLayoutSet.layout(header).marginWidth(0).marginHeight(0).spacing(0);

        final CLabel titleLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(titleLabel).atLeft().atTop().withWidth(TITLE_WIDTH);
        CLabelSet.decorate(titleLabel).setFont(MT.FONT_MEDIUM_BOLD).setText(testSchema.getTitle());

        final StarsComposite starsComposite = new StarsComposite(header, SWT.NONE, testSchema.getStars());
        FormDataSet.attach(starsComposite).atLeft().atTopTo(titleLabel, 10).atRight();

        final Label divider1 = new Label(header, SWT.NONE);
        FormDataSet.attach(divider1).atLeft().atTopTo(starsComposite, 10).atRight().withHeight(1);
        LabelSet.decorate(divider1).setBackground(MT.COLOR_WINDOW_BACKGROUND);

        final Composite footer = new Composite(this, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atTopTo(header).atRight();
        FormLayoutSet.layout(footer).marginWidth(0).marginHeight(10).spacing(0);

        final ImageButton editButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_EDIT, MT.IMAGE_SYSTEM_EDIT_HOVER);
        FormDataSet.attach(editButton).atLeft().atTop(10);
        editButton.addMouseListener(new EditButtonMouseAdapter());

        final ImageButton exportButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_EXPORT, MT.IMAGE_SYSTEM_EXPORT_HOVER);
        FormDataSet.attach(exportButton).atLeftTo(editButton, 10).atTop(10);
        exportButton.addMouseListener(new ExportButtonMouseAdapter());
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class EditButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            MyApplication.get().getWindow().toTestEditorPage();
        }
    }

    private class ExportButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            FileDialog dialog = new FileDialog(MyApplication.get().getWindow().getShell(), SWT.SAVE);
            dialog.setFilterNames(new String[]{"Zip Archive (*.zip)"});
            dialog.setFilterExtensions(new String[]{"*.zip"});
            dialog.setFileName("xx.zip");
            boolean done = false;
            while (!done) {
                String absoluteFileName = dialog.open();
                if (!StringUtils.isEmpty(absoluteFileName)) {
                    File file = new File(absoluteFileName);
                    if (file.exists()) {
                        MessageBox box = new MessageBox(dialog.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
                        box.setText(msgs.getString("file_exists"));
                        box.setMessage("\"xx.zip\" " + msgs.getString("exists_and_replace"));
                        int response = box.open();
                        if (response == SWT.YES) {
                            PDFUtils.save(absoluteFileName);
                            done = true;
                        }
                    } else {
                        PDFUtils.save(absoluteFileName);
                        done = true;
                    }
                } else {
                    done = true;
                }
            }
        }
    }
}
