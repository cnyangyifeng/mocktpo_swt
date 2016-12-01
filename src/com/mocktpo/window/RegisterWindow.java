package com.mocktpo.window;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.ActivationCode;
import com.mocktpo.orm.mapper.ActivationCodeMapper;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.vo.RequireActivationVo;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.*;
import org.h2.util.StringUtils;

import java.util.ResourceBundle;

public class RegisterWindow {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Application */

    protected MyApplication app;

    /* Display and Shell */

    protected Display d;
    private Shell s;

    /* Widgets */

    private Composite header;
    private Composite footer;

    private StyledText et;
    private Button eb;
    private CLabel em;
    private StyledText at;
    private CLabel am;
    private Button r;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public RegisterWindow() {
        this.app = MyApplication.get();
        this.d = app.getDisplay();
        init();
    }

    private void init() {
        s = new Shell(d, SWT.TOP);
        global();
        initHeader();
        initFooter();
        initBody();
    }

    private void global() {
        s.setText(msgs.getString("app_name"));
        s.setImage(ResourceManager.getImage(MT.IMAGE_APP_ICON));
        WindowUtils.setDefaultWindowSize(s);
        s.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));
        s.setBackgroundMode(SWT.INHERIT_FORCE);
        WindowUtils.disableFullscreen(s);
        FormLayoutSet.layout(s);
    }

    private void initHeader() {

        header = new Composite(s, SWT.NONE);
        CompositeSet.decorate(header).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        FormLayoutSet.layout(header);

        final StyledText bt = new StyledText(header, SWT.SINGLE);
        FormDataSet.attach(bt).atLeft(20).atTop(20);
        StyledTextSet.decorate(bt).setEditable(false).setEnabled(false).setFont(MT.FONT_LARGE).setText(msgs.getString("register"));

        final StyledText dt = new StyledText(header, SWT.WRAP);
        FormDataSet.attach(dt).atLeft(20).atTopTo(bt, 20).fromRight(20).atBottom(20);
        StyledTextSet.decorate(dt).setEditable(false).setEnabled(false).setLineSpacing(10).setText(msgs.getString("register_desc"));

        final Label ll = new Label(header, SWT.NONE);
        FormDataSet.attach(ll).atTop(20).atRight(20);
        LabelSet.decorate(ll).setImage(MT.IMAGE_LOGO);
    }

    private void initFooter() {

        footer = new Composite(s, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atRight().atBottom().withHeight(LC.BUTTON_HEIGHT_HINT * 2);
        CompositeSet.decorate(footer).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        FormLayoutSet.layout(footer);

        r = new Button(footer, SWT.PUSH);
        FormDataSet.attach(r).fromLeft(50, -LC.BUTTON_WIDTH_HINT - 10).fromTop(50, -LC.BUTTON_HEIGHT_HINT / 2).withWidth(LC.BUTTON_WIDTH_HINT).withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(r).setCursor(MT.CURSOR_HAND).setEnabled(false).setText(msgs.getString("register"));
        r.addSelectionListener(new RegisterSelectionListener());

        final Button c = new Button(footer, SWT.PUSH);
        FormDataSet.attach(c).fromLeft(50, 10).fromTop(50, -LC.BUTTON_HEIGHT_HINT / 2).withWidth(LC.BUTTON_WIDTH_HINT).withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(c).setCursor(MT.CURSOR_HAND).setText(msgs.getString("close"));
        c.addSelectionListener(new CancelSelectionListener());
    }

    private void initBody() {

        final Composite body = new Composite(s, SWT.NONE);
        FormDataSet.attach(body).atLeft(100).atTopTo(header, 20).atRight(100).atBottomTo(footer, 20);
        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(10);

        final CLabel el = new CLabel(body, SWT.NONE);
        FormDataSet.attach(el).atLeft().atTop().atRight();
        CLabelSet.decorate(el).setLeftMargin(0).setText(msgs.getString("email"));

        et = new StyledText(body, SWT.BORDER | SWT.SINGLE);
        FormDataSet.attach(et).atLeft().atTopTo(el).fromRight(40);
        KeyBindingSet.bind(et).traverse().selectAll();
        StyledTextSet.decorate(et).setFocus().setMargins(10);
        et.addKeyListener(new EmailTextKeyListener());

        eb = new Button(body, SWT.PUSH);
        FormDataSet.attach(eb).atLeftTo(et).atTopTo(el).atRight().atBottomTo(et, 0, SWT.BOTTOM);
        ButtonSet.decorate(eb).setCursor(MT.CURSOR_HAND).setEnabled(false).setText(msgs.getString("send_email"));
        eb.addSelectionListener(new SendSelectionListener());

        em = new CLabel(body, SWT.NONE);
        FormDataSet.attach(em).atLeft().atTopTo(et).atRight();
        CLabelSet.decorate(em).setLeftMargin(0);

        final CLabel al = new CLabel(body, SWT.NONE);
        FormDataSet.attach(al).atLeft().atTopTo(em).atRight();
        CLabelSet.decorate(al).setLeftMargin(0).setText(msgs.getString("activation_code"));

        am = new CLabel(body, SWT.NONE);
        FormDataSet.attach(am).atLeft().atRight().atBottom();
        CLabelSet.decorate(am).setLeftMargin(0);

        at = new StyledText(body, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(at).atLeft().atTopTo(al).atRight().atBottomTo(am, 0, SWT.TOP);
        KeyBindingSet.bind(at).traverse().selectAll();
        StyledTextSet.decorate(at).setFont(MT.FONT_ACTIVATION_CODE).setMargins(10);
        at.addKeyListener(new ActivationCodeTextKeyListener());
    }

    public void openAndWaitForDisposal() {
        WindowUtils.center(s);
        s.open();
        while (!s.isDisposed()) {
            if (!d.readAndDispatch()) {
                d.sleep();
            }
        }
    }

    public void close() {
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    s.dispose();
                }
            });
        }
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class EmailTextKeyListener implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            if (RegexUtils.isValidEmail(et.getText())) {
                eb.setEnabled(true);
                if (!StringUtils.isNullOrEmpty(at.getText())) {
                    r.setEnabled(true);
                } else {
                    r.setEnabled(false);
                }
            } else {
                eb.setEnabled(false);
                r.setEnabled(false);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    private class ActivationCodeTextKeyListener implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            if (!StringUtils.isNullOrEmpty(at.getText()) && RegexUtils.isValidEmail(et.getText())) {
                r.setEnabled(true);
            } else {
                r.setEnabled(false);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    private class SendSelectionListener implements SelectionListener {

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    em.setText("");
                    eb.setEnabled(false);
                }
            });
            String email = et.getText();
            String hardware = HardwareBinderUtils.uuid();
            final RequireActivationVo vo = new RequireActivationVo();
            vo.setEmail(email);
            vo.setHardware(hardware);
            new Thread() {
                @Override
                public void run() {
                    switch (ActivationCodeUtils.post(vo)) {
                        case ActivationCodeUtils.EMAIL_HARDWARE_OK:
                            d.asyncExec(new Runnable() {
                                @Override
                                public void run() {
                                    em.setText(msgs.getString("email_hardware_ok"));
                                    em.setForeground(ResourceManager.getColor(MT.COLOR_GREEN));
                                    eb.setEnabled(true);
                                }
                            });
                            break;
                        case ActivationCodeUtils.REGISTERED_EMAIL_NOT_FOUND:
                            d.asyncExec(new Runnable() {
                                @Override
                                public void run() {
                                    em.setText(msgs.getString("registered_email_not_found"));
                                    em.setForeground(ResourceManager.getColor(MT.COLOR_ORANGE_RED));
                                    eb.setEnabled(true);
                                }
                            });
                            break;
                        case ActivationCodeUtils.REGISTERED_HARDWARE_UNMATCHED:
                            d.asyncExec(new Runnable() {
                                @Override
                                public void run() {
                                    em.setText(msgs.getString("registered_hardware_unmatched"));
                                    em.setForeground(ResourceManager.getColor(MT.COLOR_ORANGE_RED));
                                    eb.setEnabled(true);
                                }
                            });
                            break;
                        case ActivationCodeUtils.NETWORK_FAILURE:
                        default:
                            d.asyncExec(new Runnable() {
                                @Override
                                public void run() {
                                    em.setText(msgs.getString("network_failure"));
                                    em.setForeground(ResourceManager.getColor(MT.COLOR_ORANGE_RED));
                                    eb.setEnabled(true);
                                }
                            });
                    }
                }
            }.start();
        }
    }

    private class RegisterSelectionListener implements SelectionListener {

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    am.setText("");
                    r.setEnabled(false);
                }
            });
            String email = et.getText();
            String acc = at.getText();
            if (ActivationCodeUtils.isLicensed(email, acc)) {
                SqlSession sqlSession = app.getSqlSession();
                ActivationCodeMapper mapper = sqlSession.getMapper(ActivationCodeMapper.class);
                ActivationCode ac = new ActivationCode();
                ac.setEmail(email);
                ac.setContent(acc);
                mapper.insert(ac);
                sqlSession.commit();
                d.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        am.setText("");
                        r.setEnabled(true);
                        close();
                    }
                });
            } else {
                d.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        am.setText(msgs.getString("register_failure"));
                        am.setForeground(ResourceManager.getColor(MT.COLOR_ORANGE_RED));
                        r.setEnabled(true);
                    }
                });
            }
        }
    }

    private class CancelSelectionListener implements SelectionListener {

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            close();
            System.exit(0);
        }
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public Display getDisplay() {
        return d;
    }
}
