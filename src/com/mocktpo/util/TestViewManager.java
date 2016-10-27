package com.mocktpo.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.swt.SWT;

import com.mocktpo.page.TestPage;
import com.mocktpo.util.constants.TV;
import com.mocktpo.view.GeneralTestInfoView;
import com.mocktpo.view.TestIntroView;
import com.mocktpo.view.TestView;

public class TestViewManager {

    private static final int INITIAL_CACHE_SIZE = 64;

    private static final ConcurrentMap<Integer, TestView> caches = new ConcurrentHashMap<Integer, TestView>(INITIAL_CACHE_SIZE);

    public static TestView getTestView(TestPage page) {
        int key = page.getUserTest().getLastViewId();

        TestView tv = null;

        if (caches.containsKey(key)) {
            tv = caches.get(key);
            tv.reset();
        }

        switch (key) {
        case TV.VIEW_TEST_INTRO:
            tv = new TestIntroView(page, SWT.NONE);
            caches.put(TV.VIEW_TEST_INTRO, tv);
            break;
        case TV.VIEW_GENERAL_TEST_INFO:
            tv = new GeneralTestInfoView(page, SWT.NONE);
            caches.put(TV.VIEW_GENERAL_TEST_INFO, tv);
            break;
        }
        return tv;
    }

    public static void dispose() {
        for (int key : caches.keySet()) {
            TestView r = caches.get(key);
            caches.remove(key);
            r.dispose();
        }
    }
}
