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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
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
import com.mocktpo.util.ImageUtils;
import com.mocktpo.util.KeyBindingSet;
import com.mocktpo.util.RegexUtils;
import com.mocktpo.util.SWTFontUtils;
import com.mocktpo.util.WindowUtils;
import com.mocktpo.util.constants.ResourceConstants;
import com.mocktpo.vo.RequireActivationVo;

public class RegisterWindow {

    /* Constants */

    private static final int SHELL_WIDTH = 800;
    private static final int SHELL_HEIGHT = 600;
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 40;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    private Display d;
    private MyApplication app;

    /* Shell */

    private Shell s;

    /* Widgets */

    private StyledText et;
    private Button eb;
    private Label em;
    private StyledText at;
    private Label am;
    private Button r;

    /* Resources */

    private Color gray;
    private Color green;
    private Color red;
    private Color white;
    private Cursor hand;
    private Font tf;
    private Image ico;

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
        s = new Shell(d, SWT.TOOL);
        alloc();
        global();
        initHeader();
        initBody();
        initFooter();
    }

    private void global() {
        s.setText(msgs.getString("app_name"));
        s.setImage(ico);
        s.setSize(SHELL_WIDTH, SHELL_HEIGHT);
        s.setBackground(white);
        FormLayoutSet.layout(s);
    }

    private void initHeader() {
        final Composite header = new Composite(s, SWT.NONE);
        header.setBackground(gray);
        FormDataSet.attach(header).atLeft().atTop().atRight().withHeight(120);
        FormLayoutSet.layout(header);

        final Label title = new Label(header, SWT.NONE);
        FormDataSet.attach(title).atLeft(20).atTop(20);
        title.setText(msgs.getString("app_name"));
        title.setFont(tf);

        final Label desc = new Label(header, SWT.WRAP);
        FormDataSet.attach(desc).atLeft(20).atTopTo(title, 10).atRight(20);
        desc.setText(msgs.getString("register_desc"));
    }

    private void initBody() {
        final Composite body = new Composite(s, SWT.NONE);
        FormDataSet.attach(body).atLeft(100).atTop(140).atRight(100).atBottom(120);
        body.setBackground(white);
        FormLayoutSet.layout(body).marginWidth(20).marginHeight(20).spacing(10);

        final Label el = new Label(body, SWT.NONE);
        FormDataSet.attach(el).atLeft().atTop().atRight();
        el.setText(msgs.getString("email"));

        et = new StyledText(body, SWT.BORDER | SWT.SINGLE);
        FormDataSet.attach(et).atLeft().atTopTo(el).fromRight(40);
        KeyBindingSet.bind(et).traverse().selectAll();
        et.setMargins(10, 10, 10, 10);
        et.setFocus();
        et.addKeyListener(new EmailTextKeyListener());

        eb = new Button(body, SWT.PUSH);
        FormDataSet.attach(eb).atLeftTo(et).atTopTo(el).atRight().atBottomTo(et, 0, SWT.BOTTOM);
        eb.setText(msgs.getString("send_email"));
        eb.setCursor(hand);
        eb.setEnabled(false);
        eb.addSelectionListener(new SendSelectionListener());

        em = new Label(body, SWT.NONE);
        FormDataSet.attach(em).atLeft().atTopTo(et).atRight();

        final Label al = new Label(body, SWT.NONE);
        FormDataSet.attach(al).atLeft().atTopTo(em).atRight();
        al.setText(msgs.getString("activation_code"));

        am = new Label(body, SWT.NONE);
        FormDataSet.attach(am).atLeft().atRight().atBottom();

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
        footer.setBackground(gray);
        FormLayoutSet.layout(footer);

        r = new Button(footer, SWT.PUSH);
        FormDataSet.attach(r).fromLeft(50, -BUTTON_WIDTH - 10).fromTop(50, -BUTTON_HEIGHT / 2).withWidth(BUTTON_WIDTH).withHeight(BUTTON_HEIGHT);
        r.setText(msgs.getString("register"));
        r.setCursor(hand);
        r.setEnabled(false);
        r.addSelectionListener(new RegisterSelectionListener());

        final Button c = new Button(footer, SWT.PUSH);
        FormDataSet.attach(c).fromLeft(50, 10).fromTop(50, -BUTTON_HEIGHT / 2).withWidth(BUTTON_WIDTH).withHeight(BUTTON_HEIGHT);
        c.setText(msgs.getString("close"));
        c.setCursor(hand);
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
        release();
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
     * Native Resource Operations
     * 
     **************************************************/

    private void alloc() {
        gray = new Color(d, 246, 246, 246);
        green = new Color(d, 92, 184, 92);
        red = new Color(d, 217, 83, 79);
        white = new Color(d, 255, 255, 255);
        hand = new Cursor(d, SWT.CURSOR_HAND);
        tf = new Font(d, "Arial", 20, SWT.BOLD);
        ico = ImageUtils.load(d, ResourceConstants.APP_ICON_UNIVERSAL_FILE);
    }

    private void release() {
        gray.dispose();
        green.dispose();
        red.dispose();
        white.dispose();
        hand.dispose();
        tf.dispose();
        ico.dispose();
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
                                em.setForeground(green);
                                eb.setEnabled(true);
                            }
                        });
                        break;
                    case ActivationCodeUtils.REGISTERED_EMAIL_NOT_FOUND:
                        d.asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                em.setText(msgs.getString("registered_email_not_found"));
                                em.setForeground(red);
                                eb.setEnabled(true);
                            }
                        });
                        break;
                    case ActivationCodeUtils.REGISTERED_HARDWARE_UNMATCHED:
                        d.asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                em.setText(msgs.getString("registered_hardware_unmatched"));
                                em.setForeground(red);
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
                                em.setForeground(red);
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
                        am.setForeground(red);
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

    public Display getDisplay() {
        return d;
    }

    public void setDisplay(Display d) {
        this.d = d;
    }

    public MyApplication getApp() {
        return app;
    }

    public void setApp(MyApplication app) {
        this.app = app;
    }

    public Shell getShell() {
        return s;
    }

    public void setShell(Shell s) {
        this.s = s;
    }
}
