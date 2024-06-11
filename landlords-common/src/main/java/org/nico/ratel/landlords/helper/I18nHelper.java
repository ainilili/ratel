package org.nico.ratel.landlords.helper;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class I18nHelper {

    private I18nHelper() {
    }

    private static final String I18N_BUNDLE_NAME = "messages";

    private static ResourceBundle messageBundle;

    private static volatile boolean enabled = false;

    /**
     * 启用并加载国际化信息，语言地区使用系统默认
     */
    public static void enable() {
        enable(Locale.getDefault());
    }

    /**
     * 启用并加载国际化，指定语言地区
     *
     * @param locale 语言地区
     */
    public static void enable(Locale locale) {
        if (enabled) {
            System.err.println("[warning] i18n resource has already loaded.");
            return;
        }
        try {
            messageBundle = ResourceBundle.getBundle(I18N_BUNDLE_NAME, locale);
            if (locale != messageBundle.getLocale()) {
                System.err.printf("[warning] missing i18n resource for %s, set locale to %s\n",
                        locale, messageBundle.getLocale());
            }
        } catch (Exception e) {
            System.err.println("[error] load i18n resource error, exception: " + e.getMessage());
            throw e;
        }
        I18nHelper.enabled = true;
    }

    /**
     * 更新并重新加载国际化资源
     *
     * @param locale 语言地区
     */
    public static void refresh(Locale locale) {
        if (!enabled) {
            throw new IllegalStateException("i18n not enabled!");
        }
        ResourceBundle bundle;
        try {
            bundle = ResourceBundle.getBundle(I18N_BUNDLE_NAME, locale);
            if (locale != bundle.getLocale()) {
                throw new IllegalStateException("i18n resource of " + locale + " not found");
            }
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw e;
            }
            throw new RuntimeException("load i18n resource of " + locale + "failed, exception: " + e.getMessage());
        }
        messageBundle = bundle;
    }

    /**
     * 翻译文本
     * <p>支持格式化，格式化方式采用String.format(format, Object...args)<p/>
     * <p>不在字典内的key将原样显示</p>
     *
     * @param key  待翻译语言key
     * @param args 参数
     */
    public static String translate(String key, Object... args) {
        if (!enabled) {
            return format(key, args);
        }
        try {
            String t = messageBundle.getString(key);
            return format(new String(t.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8), args);
        } catch (MissingResourceException e) {
            return format(key, args);
        }
    }

    /**
     * 获取当前地区语言
     */
    public static Locale getCurrentLocale() {
        return messageBundle == null ? null : messageBundle.getLocale();
    }

    private static String format(String template, Object... args) {
        return String.format(template, args);
    }
}
