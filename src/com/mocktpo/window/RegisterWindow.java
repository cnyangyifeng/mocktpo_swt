package com.mocktpo.window;

import java.util.ResourceBundle;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.h2.util.StringUtils;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.ActivationCode;
import com.mocktpo.orm.mapper.ActivationCodeMapper;
import com.mocktpo.util.ActivationCodeUtils;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.HardwareBinderUtils;
import com.mocktpo.util.KeyBindingSet;
import com.mocktpo.util.RegexUtils;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.SWTFontUtils;
import com.mocktpo.util.WindowUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.vo.RequireActivationVo;

public class RegisterWindow {

    /* Constants */

    private static final int SHELL_WIDTH = 800;
    private static final int SHELL_HEIGHT = 600;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 40;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Application and Display */

    protected MyApplication app;
    protected Display d;

    /* Shell */

    private Shell s;

    /* Widgets */

    private StyledText et;
    private Button eb;
    private Label em;
    private StyledText at;
    private Label am;
    private Button r;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public RegisterWindow(MyApplication app) {
        this.app = app;
        this.d = app.getDisplay();
        init();
    }

    private void init() {
        s = new Shell(d, SWT.TOP | SWT.ON_TOP);
        global();
        initHeader();
        initBody();
        initFooter();
    }

    private void global() {
        s.setText(msgs.getString("app_name"));
        s.setImage(ResourceManager.getImage(MT.IMAGE_APP_ICON));
        s.setSize(SHELL_WIDTH, SHELL_HEIGHT);
        s.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));
        WindowUtils.disableFullscreen(s);
        FormLayoutSet.layout(s);
    }

    private void initHeader() {
        final Composite header = new Composite(s, SWT.NONE);
        header.setBackground(ResourceManager.getColor(MT.COLOR_LIGHT_GRAY));
        FormDataSet.attach(header).atLeft().atTop().atRight().withHeight(120);
        FormLayoutSet.layout(header);

        final Label title = new Label(header, SWT.NONE);
        FormDataSet.attach(title).atLeft(20).atTop(20);
        title.setText(msgs.getString("app_name"));
        title.setBackground(ResourceManager.getColor(MT.COLOR_LIGHT_GRAY));

        final Label desc = new Label(header, SWT.WRAP);
        FormDataSet.attach(desc).atLeft(20).atTopTo(title, 10).atRight(20);
        desc.setText(msgs.getString("register_desc"));
        desc.setBackground(ResourceManager.getColor(MT.COLOR_LIGHT_GRAY));
    }

    private void initBody() {
        final Composite body = new Composite(s, SWT.NONE);
        FormDataSet.attach(body).atLeft(100).atTop(140).atRight(100).atBottom(120);
        body.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));
        FormLayoutSet.layout(body).marginWidth(20).marginHeight(20).spacing(10);

        final Label el = new Label(body, SWT.NONE);
        FormDataSet.attach(el).atLeft().atTop().atRight();
        el.setText(msgs.getString("email"));
        el.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));

        et = new StyledText(body, SWT.BORDER | SWT.SINGLE);
        FormDataSet.attach(et).atLeft().atTopTo(el).fromRight(40);
        KeyBindingSet.bind(et).traverse().selectAll();
        et.setMargins(10, 10, 10, 10);
        et.setFocus();
        et.addKeyListener(new EmailTextKeyListener());

        eb = new Button(body, SWT.PUSH);
        FormDataSet.attach(eb).atLeftTo(et).atTopTo(el).atRight().atBottomTo(et, 0, SWT.BOTTOM);
        eb.setText(msgs.getString("send_email"));
        eb.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
        eb.setEnabled(false);
        eb.addSelectionListener(new SendSelectionListener());

        em = new Label(body, SWT.NONE);
        FormDataSet.attach(em).atLeft().atTopTo(et).atRight();
        em.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));

        final Label al = new Label(body, SWT.NONE);
        FormDataSet.attach(al).atLeft().atTopTo(em).atRight();
        al.setText(msgs.getString("activation_code"));
        al.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));

        am = new Label(body, SWT.NONE);
        FormDataSet.attach(am).atLeft().atRight().atBottom();
        am.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));

        at = new StyledText(body, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(at).atLeft().atTopTo(al).atRight().atBottomTo(am, 0, SWT.TOP);
        KeyBindingSet.bind(at).traverse().selectAll();
        at.setMargins(10, 10, 10, 10);
        at.setFont(SWTFontUtils.getMonospacedFont(d));
        at.addKeyListener(new ActivationCodeTextKeyListener());
    }

    private void initFooter() {
        final Composite footer = new Composite(s, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atTop(500).atRight().atBottom();
        footer.setBackground(ResourceManager.getColor(MT.COLOR_LIGHT_GRAY));
        FormLayoutSet.layout(footer);

        r = new Button(footer, SWT.PUSH);
        FormDataSet.attach(r).fromLeft(50, -BUTTON_WIDTH - 10).fromTop(50, -BUTTON_HEIGHT / 2).withWidth(BUTTON_WIDTH).withHeight(BUTTON_HEIGHT);
        r.setText(msgs.getString("register"));
        r.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
        r.setEnabled(false);
        r.addSelectionListener(new RegisterSelectionListener());

        final Button c = new Button(footer, SWT.PUSH);
        FormDataSet.attach(c).fromLeft(50, 10).fromTop(50, -BUTTON_HEIGHT / 2).withWidth(BUTTON_WIDTH).withHeight(BUTTON_HEIGHT);
        c.setText(msgs.getString("close"));
        c.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
        c.addSelectionListener(new CancelSelectionListener());
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

    public void dispose() {
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    s.dispose();
                    d.dispose();
                }
            });
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

    /**************************************************
     * 
     * Listeners
     * 
     **************************************************/

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
            RequireActivationVo vo = new RequireActivationVo();
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
                                em.setForeground(ResourceManager.getColor(MT.COLOR_RED));
                                eb.setEnabled(true);
                            }
                        });
                        break;
                    case ActivationCodeUtils.REGISTERED_HARDWARE_UNMATCHED:
                        d.asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                em.setText(msgs.getString("registered_hardware_unmatched"));
                                em.setForeground(ResourceManager.getColor(MT.COLOR_RED));
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
                                em.setForeground(ResourceManager.getColor(MT.COLOR_RED));
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
            String content = at.getText();
            if (ActivationCodeUtils.isLicensed(content)) {
                SqlSession sqlSession = app.getSqlSession();
                ActivationCodeMapper mapper = sqlSession.getMapper(ActivationCodeMapper.class);
                ActivationCode code = new ActivationCode();
                code.setContent(content);
                mapper.insert(code);
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
                        am.setForeground(ResourceManager.getColor(MT.COLOR_RED));
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
            dispose();
            System.exit(0);
        }

    }

    /**************************************************
     * 
     * Getters and Setters
     * 
     **************************************************/

    public MyApplication getApp() {
        return app;
    }

    public void setApp(MyApplication app) {
        this.app = app;
    }

    public Display getDisplay() {
        return d;
    }

    public void setDisplay(Display d) {
        this.d = d;
    }

    public Shell getShell() {
        return s;
    }

    public void setShell(Shell s) {
        this.s = s;
    }
}
