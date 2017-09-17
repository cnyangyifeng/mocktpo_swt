package com.mocktpo.modules.system.windows;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.*;
import com.mocktpo.vo.ActivationVo;
import com.mocktpo.vo.StyleRangeVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterWindow {

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
        CLabelSet.decorate(titleLabel).setFont(MT.FONT_LARGE).setForeground(MT.COLOR_GRAY20).setText(msgs.getString("register"));

        final Label logoLabel = new Label(header, SWT.NONE);
        FormDataSet.attach(logoLabel).atTop().atRight();
        LabelSet.decorate(logoLabel).setImage(MT.IMAGE_LOGO);
    }

    private void initFooter() {
        footer = new Composite(s, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atRight().atBottom();
        CompositeSet.decorate(footer).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        FormLayoutSet.layout(footer).marginWidth(0).marginHeight(20).spacing(0);

        activateButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_ACTIVATE, MT.IMAGE_SYSTEM_ACTIVATE_HOVER);
        FormDataSet.attach(activateButton).fromLeft(50, -LC.SYSTEM_BUTTON_WIDTH_HINT - 10);
        activateButton.addMouseListener(new ActivateButtonMouseAdapter());

        final ImageButton cancelButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_CANCEL, MT.IMAGE_SYSTEM_CANCEL_HOVER);
        FormDataSet.attach(cancelButton).fromLeft(50, 10);
        cancelButton.addMouseListener(new CancelButtonMouseAdapter());
    }

    private void initBody() {
        final Composite body = new Composite(s, SWT.NONE);
        FormDataSet.attach(body).atLeft(60).atTopTo(header, 20).atRight(60).atBottomTo(footer, 20);
        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(60).spacing(40);

        final StyledText descriptionTextWidget = new StyledText(body, SWT.WRAP);
        FormDataSet.attach(descriptionTextWidget).atLeft().atTop().atRight();
        StyledTextSet.decorate(descriptionTextWidget).setNoCaret().setEditable(false).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY40).setText("Please copy and paste your activation code.");
        StyleRangeUtils.decorate(descriptionTextWidget, new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(27, 15, MT.FONT_MEDIUM_ITALIC, MT.COLOR_DARK_BLUE, 0, false, null));
        }});

        activationCodeTextWidget = new StyledText(body, SWT.SINGLE);
        FormDataSet.attach(activationCodeTextWidget).atLeft().atTopTo(descriptionTextWidget).atRight().withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        KeyBindingSet.bind(activationCodeTextWidget).selectAll();
        StyledTextSet.decorate(activationCodeTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10);
        activationCodeTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final StyledText footnoteTextWidget = new StyledText(body, SWT.NONE);
        FormDataSet.attach(footnoteTextWidget).atLeft().atTopTo(activationCodeTextWidget).atRight();
        StyledTextSet.decorate(footnoteTextWidget).setNoCaret().setEditable(false).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY40).setText("Have questions? Contact us.");
        StyleRangeUtils.decorate(footnoteTextWidget, new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(16, 10, MT.FONT_MEDIUM_ITALIC, MT.COLOR_DARK_BLUE, 0, false, null));
        }});
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
     * Listeners
     *
     * ==================================================
     */

    private class EmailTextKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
//            if (RegexUtils.isValidEmail(et.getText())) {
//                 eb.setEnabled(true);
//                if (!StringUtils.isNullOrEmpty(at.getText())) {
//                    activateButton.setEnabled(true);
//                } else {
//                    activateButton.setEnabled(false);
//                }
//            } else {
//                 eb.setEnabled(false);
//                activateButton.setEnabled(false);
//            }
        }
    }

    private class ActivationCodeTextKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
//            if (!StringUtils.isNullOrEmpty(at.getText()) && RegexUtils.isValidEmail(et.getText())) {
//                activateButton.setEnabled(true);
//            } else {
//                activateButton.setEnabled(false);
//            }
        }
    }

    private class ActivateButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            String activationCode = activationCodeTextWidget.getText();
            String hardware = HardwareBinderUtils.uuid();
            final ActivationVo vo = new ActivationVo();
            vo.setActivationCode(activationCode);
            vo.setHardware(hardware);
            new Thread(() -> {
                switch (ActivationUtils.post(vo)) {
                    case ActivationUtils.ACTIVATION_CODE_HARDWARE_OK:
                        d.asyncExec(() -> {
//                            em.setText(msgs.getString("email_hardware_ok"));
//                            em.setForeground(ResourceManager.getColor(MT.COLOR_GREEN));
//                            eb.setEnabled(true);
                            logger.info("ok");
                            close();
                        });
                        break;
                    case ActivationUtils.ACTIVATION_CODE_NOT_FOUND:
                        d.asyncExec(() -> {
//                            em.setText(msgs.getString("registered_email_not_found"));
//                            em.setForeground(ResourceManager.getColor(MT.COLOR_ORANGE_RED));
                            // eb.setEnabled(true);
                            logger.info("activation code not found");
                        });
                        break;
                    case ActivationUtils.REGISTERED_HARDWARE_UNMATCHED:
                        d.asyncExec(() -> {
//                            em.setText(msgs.getString("registered_hardware_unmatched"));
//                            em.setForeground(ResourceManager.getColor(MT.COLOR_ORANGE_RED));
//                            eb.setEnabled(true);
                            logger.info("hardware unmatched");
                        });
                        break;
                    case ActivationUtils.NETWORK_FAILURE:
                    default:
                        d.asyncExec(() -> {
//                            em.setText(msgs.getString("network_failure"));
//                            em.setForeground(ResourceManager.getColor(MT.COLOR_ORANGE_RED));
//                            eb.setEnabled(true);
                            logger.info("network_failure");
                        });
                }
            }).start();
//            d.asyncExec(() -> {
//                am.setText("");
//                activateButton.setEnabled(false);
//            });
//            String email = et.getText();
//            String acc = at.getText();
//            if (ActivationUtils.isLicensed(email, acc)) {
//                SqlSession sqlSession = app.getSqlSession();
//                ActivationCodeMapper mapper = sqlSession.getMapper(ActivationCodeMapper.class);
//                ActivationCode ac = new ActivationCode();
//                ac.setEmail(email);
//                ac.setContent(acc);
//                mapper.insert(ac);
//                sqlSession.commit();
//                d.asyncExec(() -> {
//                    am.setText("");
//                    activateButton.setEnabled(true);
//                    close();
//                });
//            } else {
//                d.asyncExec(() -> {
//                    am.setText(msgs.getString("register_failure"));
//                    am.setForeground(ResourceManager.getColor(MT.COLOR_ORANGE_RED));
//                    activateButton.setEnabled(true);
//                });
//            }
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
