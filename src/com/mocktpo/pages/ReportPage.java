package com.mocktpo.pages;

import com.alibaba.fastjson.JSON;
import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTestAnswer;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.TimeUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import com.mocktpo.util.constants.VT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.AnswerUtils;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.vo.QuestionAndAnswerDetailsVo;
import com.mocktpo.vo.TestSchemaVo;
import com.mocktpo.vo.TestViewVo;
import com.mocktpo.widgets.ImageButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReportPage extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Widgets */

    private Composite toolBar;
    private Composite body;

    /* Views */

    private Browser browser;

    /* Properties */

    private TestSchemaVo testSchema;
    private UserTestSession userTestSession;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReportPage(Composite parent, int style, UserTestSession userTestSession) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.userTestSession = userTestSession;
        this.testSchema = ConfigUtils.load(this.userTestSession.getFileAlias(), TestSchemaVo.class);
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

        final ImageButton backButton = new ImageButton(toolBar, SWT.NONE, MT.IMAGE_SYSTEM_BACK, MT.IMAGE_SYSTEM_BACK_HOVER);
        FormDataSet.attach(backButton).atLeft().atTop();
        backButton.addMouseListener(new BackButtonMouseAdapter());

        final ImageButton exportButton = new ImageButton(toolBar, SWT.NONE, MT.IMAGE_SYSTEM_EXPORT, MT.IMAGE_SYSTEM_EXPORT_HOVER);
        FormDataSet.attach(exportButton).atTop().atRight();
    }

    private void initBody() {
        final ScrolledComposite sc = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(toolBar).atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        sc.setContent(body);

        CompositeSet.decorate(body).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        FormLayoutSet.layout(body);

        browser = new Browser(body, SWT.NONE);
        try {
            URL url = this.getClass().getResource(URLDecoder.decode(RC.TEMPLATES_DIR + "index.html", "utf-8"));
            browser.setUrl(url.toString());
            new TitleFunction(browser, "getTitle");
            new StartTimeFunction(browser, "getStartTime");
            new EndTimeFunction(browser, "getEndTime");
            new TotalQuestionCountInSectionFunction(browser, "getTotalQuestionCountInSection");
            new QuestionAndAnswerDetailsFunction(browser, "getQuestionAndAnswerDetails");
        } catch (Exception e) {
            e.printStackTrace();
        }
        FormDataSet.attach(browser).atLeft().atTop().atRight().atBottom();

        sc.setMinSize(body.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class BackButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            MyApplication.get().getWindow().toMainPage();
        }
    }

    /*
     * ==================================================
     *
     * Browser Functions
     *
     * ==================================================
     */

    public class TitleFunction extends BrowserFunction {

        public TitleFunction(Browser browser, String name) {
            super(browser, name);
        }

        @Override
        public Object function(Object[] args) {
            return ReportPage.this.testSchema.getTitle();
        }
    }

    public class StartTimeFunction extends BrowserFunction {

        public StartTimeFunction(Browser browser, String name) {
            super(browser, name);
        }

        @Override
        public Object function(Object[] args) {
            return TimeUtils.displayClockTime(ReportPage.this.userTestSession.getStartTime());
        }
    }

    public class EndTimeFunction extends BrowserFunction {

        public EndTimeFunction(Browser browser, String name) {
            super(browser, name);
        }

        @Override
        public Object function(Object[] args) {
            return TimeUtils.displayClockTime(ReportPage.this.userTestSession.getLastVisitTime());
        }
    }

    public class TotalQuestionCountInSectionFunction extends BrowserFunction {

        public TotalQuestionCountInSectionFunction(Browser browser, String name) {
            super(browser, name);
        }

        @Override
        public Object function(Object[] args) {
            int sectionType = ((Double) args[0]).intValue();
            return ReportPage.this.testSchema.findTotalQuestionCountInSection(sectionType);
        }
    }

    public class QuestionAndAnswerDetailsFunction extends BrowserFunction {

        public QuestionAndAnswerDetailsFunction(Browser browser, String name) {
            super(browser, name);
        }

        @Override
        public Object function(Object[] args) {
            int sectionType = ((Double) args[0]).intValue();
            logger.info("sectionType: {}", sectionType);
            List<TestViewVo> viewVos = ReportPage.this.testSchema.getViewVos();
            List<QuestionAndAnswerDetailsVo> result = new ArrayList<QuestionAndAnswerDetailsVo>();
            for (TestViewVo viewVo : viewVos) {
                if (sectionType == viewVo.getSectionType() && viewVo.isAnswerable()) {
                    logger.info("sectionType: {}, viewVo: {}", sectionType, getQuestionText(viewVo));
                    QuestionAndAnswerDetailsVo detailsVo = new QuestionAndAnswerDetailsVo();
                    detailsVo.setQuestionNumberInSection(viewVo.getQuestionNumberInSection());
                    detailsVo.setQuestion(getQuestionText(viewVo));
                    UserTestAnswer userTestAnswer = PersistenceUtils.findAnswer(ReportPage.this.userTestSession, viewVo.getViewId());
                    if (null != userTestAnswer) {
                        detailsVo.setAnswer(AnswerUtils.toAlphabeticAnswer(userTestAnswer.getAnswer()));
                    } else {
                        detailsVo.setAnswer("NOT SEEN");
                    }
                    result.add(detailsVo);
                }
            }
            return JSON.toJSONString(result);
        }

        private String getQuestionText(TestViewVo vo) {
            String text;
            if (vo.getViewType() == VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION || vo.getViewType() == VT.VIEW_TYPE_READING_CATEGORY_CHART_QUESTION) {
                text = vo.getStyledText("directions").getText() + MT.STRING_SPACE + "<strong>" + vo.getStyledText("question").getText() + "</strong>";
            } else {
                text = vo.getStyledText("question").getText();
            }
            return text;
        }
    }
}
