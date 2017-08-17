package com.mocktpo.modules.system.widgets;

import com.mocktpo.util.ImageUtils;
import com.mocktpo.util.constants.RC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.net.URLDecoder;
import java.util.ResourceBundle;

public class AnimatedGif extends Canvas {


    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Animation */

    private ImageLoader loader;
    private GC gc;
    private int currentImageId;
    private volatile boolean animating;
    private Thread animateThread;


    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public AnimatedGif(Composite parent, int style, String fileName) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.loader = new ImageLoader();
        try {
            loader.load(ImageUtils.class.getResourceAsStream(URLDecoder.decode(RC.IMAGES_DIR + fileName + RC.GIF_FILE_TYPE_SUFFIX, "utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Image initialFrame = new Image(d, loader.data[0]);
        gc = new GC(initialFrame);

        this.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                e.gc.drawImage(initialFrame, 0, 0);
            }
        });
        this.currentImageId = 0;
        this.animating = false;
        init();
    }

    private void init() {
        golbal();
    }

    private void golbal() {
    }

    public void animate() {
        if (animateThread == null) {
            animateThread = newAnimateThread();
            animateThread.setDaemon(true);
        }

        if (animateThread.isAlive()) {
            return;
        }

        animateThread.start();
    }

    public void stop() {
        animating = false;
        if (animateThread != null) {
            try {
                animateThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            animateThread = null;
        }
    }

    private Thread newAnimateThread() {
        return new Thread("animated gif") {
            public void run() {
                animating = true;
                while (animating) {
                    long currentTime = System.currentTimeMillis();
                    int delayTime = loader.data[currentImageId].delayTime;
                    while (currentTime + delayTime * 10 > System.currentTimeMillis()) {
                        // Wait till the delay time has passed
                    }
                    logger.info(currentImageId);
                    if (!d.isDisposed()) {
                        d.asyncExec(new Runnable() {
                            public void run() {
                                currentImageId = (currentImageId == loader.data.length - 1) ? 0 : currentImageId + 1;
                                ImageData nextFrameData = loader.data[currentImageId];
                                Image frame = new Image(d, nextFrameData);
                                gc.drawImage(frame, nextFrameData.x, nextFrameData.y);
                                frame.dispose();
                                if (!AnimatedGif.this.isDisposed()) {
                                    redraw();
                                }
                                logger.info(currentImageId);
                            }
                        });
                    }
                }
                logger.info("stopped");
            }
        };
    }
}
