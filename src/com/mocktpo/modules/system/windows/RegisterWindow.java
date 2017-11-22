package com.mocktpo.modules.system.windows;

import com.alibaba.fastjson.JSON;
import com.mocktpo.MyApplication;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.orm.domain.LicenseCode;
import com.mocktpo.orm.mapper.LicenseCodeMapper;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.util.widgets.StyledTextSet;
import com.mocktpo.vo.ActivationVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterWindow {

    /* Constants */

    private static final int HTTP_STATUS_OK = 200;
    private static final int HTTP_STATUS_ACCEPTED = 202;
    private static final int HTTP_STATUS_NOT_FOUND = 404;
    private static final int HTTP_STATUS_CONFLICT = 409;
    private static final String ACTIVATION_URL = "http://localhost:8080/website/api/v1/licenses/activate";

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Application */

    private MyApplication app;

    /* Display and Shell */

    protected Display d;
    private Shell s;

    /* Widgets */

    private Composite header;
    private Composite footer;

    private StyledText activationCodeTextWidget;
    private ImageButton activateButton;
    private StyledText messageTextWidget;

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
        s = new Shell(d, SWT.TOOL);
        global();
        initHeader();
        initFooter();
        initBody();
    }

    private void global() {
        s.setText(msgs.getString("app_name"));
        s.setImage(ResourceManager.getImage(MT.IMAGE_APP_ICON));
        WindowUtils.setModalWindowBoundsToCenter(s);
        s.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));
        s.setBackgroundMode(SWT.INHERIT_FORCE);
        FormLayoutSet.layout(s);
    }

    private void initHeader() {
        header = new Composite(s, SWT.NONE);
        CompositeSet.decorate(header).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        FormLayoutSet.layout(header).marginWidth(20).marginHeight(20).spacing(0);

        final CLabel titleLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(titleLabel).atLeft().atTop().withHeight(48);
        CLabelSet.decorate(titleLabel).setFont(MT.FONT_LARGE).setForeground(MT.COLOR_GREY_DARKEN_4).setText(msgs.getString("app_name"));

        final Label logoLabel = new Label(header, SWT.NONE);
        FormDataSet.attach(logoLabel).atTop().atRight();
        LabelSet.decorate(logoLabel).setImage(MT.IMAGE_LOGO);
    }

    private void initFooter() {
        footer = new Composite(s, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atRight().atBottom();
        CompositeSet.decorate(footer).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        FormLayoutSet.layout(footer).marginWidth(0).marginHeight(20).spacing(0);

        activateButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_ACTIVATE, MT.IMAGE_SYSTEM_ACTIVATE_HOVER, MT.IMAGE_SYSTEM_ACTIVATE_DISABLED);
        FormDataSet.attach(activateButton).fromLeft(50, -LC.SYSTEM_BUTTON_WIDTH_HINT - 10);
        activateButton.addMouseListener(new ActivateButtonMouseAdapter());

        final ImageButton cancelButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_CANCEL, MT.IMAGE_SYSTEM_CANCEL_HOVER, MT.IMAGE_SYSTEM_CANCEL_DISABLED);
        FormDataSet.attach(cancelButton).fromLeft(50, 10);
        cancelButton.addMouseListener(new CancelButtonMouseAdapter());
    }

    private void initBody() {
        final Composite body = new Composite(s, SWT.NONE);
        FormDataSet.attach(body).atLeft(60).atTopTo(header, 20).atRight(60).atBottomTo(footer, 20);
        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(20).spacing(10);

        final StyledText descriptionTextWidget = new StyledText(body, SWT.WRAP);
        FormDataSet.attach(descriptionTextWidget).atLeft().atTop().atRight();
        StyledTextSet.decorate(descriptionTextWidget).setNoCaret().setEditable(false).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GREY_DARKEN_4).setLineSpacing(10).setText(msgs.getString("enter_activation_code"));

        activationCodeTextWidget = new StyledText(body, SWT.SINGLE);
        FormDataSet.attach(activationCodeTextWidget).atLeft().atTopTo(descriptionTextWidget).atRight().withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        KeyBindingSet.bind(activationCodeTextWidget).selectAll();
        StyledTextSet.decorate(activationCodeTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GREY_DARKEN_4).setMargins(10, 10, 10, 10);
        activationCodeTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        messageTextWidget = new StyledText(body, SWT.WRAP);
        FormDataSet.attach(messageTextWidget).atLeft().atTopTo(activationCodeTextWidget, 10).atRight();
        StyledTextSet.decorate(messageTextWidget).setNoCaret().setEditable(false).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_PINK_ACCENT_2).setText("");
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
            d.asyncExec(s::dispose);
        }
    }

    /*
     * ==================================================
     *
     * Activation
     *
     * ==================================================
     */

    public void activate(ActivationVo activationVo) {
        new Thread(() -> {
            d.asyncExec(() -> {
                activateButton.setEnabled(false);
                StyledTextSet.decorate(messageTextWidget).setText(msgs.getString("activating"));
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            try {
                URL url = new URL(ACTIVATION_URL);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("POST");
                c.setRequestProperty("Content-Type", "application/json");
                c.setRequestProperty("charset", "utf-8");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(6000);
                c.setReadTimeout(3000);
                c.setDoOutput(true);
                c.connect();
                OutputStreamWriter w = new OutputStreamWriter(c.getOutputStream());
                w.write(JSON.toJSONString(activationVo));
                w.flush();
                w.close();
                int httpStatus = c.getResponseCode();
                switch (httpStatus) {
                    case HTTP_STATUS_OK:
                    case HTTP_STATUS_ACCEPTED:
                        logger.info("Http status: OK/Accepted");
                        String data = JSON.parseObject(c.getInputStream(), String.class);
                        logger.info("License code:\n{}", data);
                        if (ActivationUtils.isLicensed(data)) {
                            SqlSession sqlSession = app.getSqlSession();
                            LicenseCodeMapper mapper = sqlSession.getMapper(LicenseCodeMapper.class);
                            mapper.deleteAll();
                            mapper.insert(new LicenseCode(data));
                            sqlSession.commit();
                            d.asyncExec(() -> {
                                activateButton.setEnabled(true);
                                StyledTextSet.decorate(activationCodeTextWidget).setText("");
                                StyledTextSet.decorate(messageTextWidget).setText(msgs.getString("activated"));
                            });
                            close();
                        } else {
                            d.asyncExec(() -> {
                                activateButton.setEnabled(true);
                                StyledTextSet.decorate(activationCodeTextWidget).setText("");
                                StyledTextSet.decorate(messageTextWidget).setText(msgs.getString("deactivated"));
                            });
                        }
                        break;
                    case HTTP_STATUS_NOT_FOUND:
                        logger.info("Http status: Not found");
                        d.asyncExec(() -> {
                            activateButton.setEnabled(true);
                            StyledTextSet.decorate(activationCodeTextWidget).setText("");
                            StyledTextSet.decorate(messageTextWidget).setText(msgs.getString("activation_code_not_found"));
                        });
                        break;
                    case HTTP_STATUS_CONFLICT:
                        logger.info("Http status: Conflict");
                        d.asyncExec(() -> {
                            activateButton.setEnabled(true);
                            StyledTextSet.decorate(activationCodeTextWidget).setText("");
                            StyledTextSet.decorate(messageTextWidget).setText(msgs.getString("activation_code_has_been_used"));
                        });
                        break;
                    default:
                        logger.info("Http status: {}", httpStatus);
                }
            } catch (SocketTimeoutException stex) {
                d.asyncExec(() -> {
                    activateButton.setEnabled(true);
                    StyledTextSet.decorate(activationCodeTextWidget).setText("");
                    StyledTextSet.decorate(messageTextWidget).setText(msgs.getString("network_failure"));
                });
                stex.printStackTrace();
            } catch (Exception ex) {
                d.asyncExec(() -> {
                    activateButton.setEnabled(true);
                    StyledTextSet.decorate(activationCodeTextWidget).setText("");
                    StyledTextSet.decorate(messageTextWidget).setText(msgs.getString("client_error"));
                });
                ex.printStackTrace();
            }
        }).start();
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class ActivateButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            String activationCode = StringUtils.upperCase(StringUtils.trim(activationCodeTextWidget.getText()));
            String hardware = HardwareBinderUtils.uuid();
            activate(new ActivationVo(activationCode, hardware));
        }
    }

    private class CancelButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
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
