package jbolt.android.base;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import jbolt.android.wardrobe.models.Person;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>Title: AppContext</p>
 * <p>Description: AppContext</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class AppContext {

    public static Activity context;
    public static Person user;

    public static void setContext(Activity context) {
        AppContext.context = context;
    }

    public static Resources getSystem() {
        return context.getResources().getSystem();
    }

    public static CharSequence getText(int id) throws Resources.NotFoundException {
        return context.getResources().getText(id);
    }

    public static CharSequence getQuantityText(int id, int quantity) throws Resources.NotFoundException {
        return context.getResources().getQuantityText(id, quantity);
    }

    public static String getString(int id) throws Resources.NotFoundException {
        return context.getResources().getString(id);
    }

    public static String getString(int id, Object... formatArgs) throws Resources.NotFoundException {
        return context.getResources().getString(id, formatArgs);
    }

    public static String getQuantityString(int id, int quantity, Object... formatArgs)
        throws Resources.NotFoundException {
        return context.getResources().getQuantityString(id, quantity, formatArgs);
    }

    public static String getQuantityString(int id, int quantity) throws Resources.NotFoundException {
        return context.getResources().getQuantityString(id, quantity);
    }

    public static CharSequence getText(int id, CharSequence def) {
        return context.getResources().getText(id, def);
    }

    public static CharSequence[] getTextArray(int id) throws Resources.NotFoundException {
        return context.getResources().getTextArray(id);
    }

    public static String[] getStringArray(int id) throws Resources.NotFoundException {
        return context.getResources().getStringArray(id);
    }

    public static int[] getIntArray(int id) throws Resources.NotFoundException {
        return context.getResources().getIntArray(id);
    }

    public static TypedArray obtainTypedArray(int id) throws Resources.NotFoundException {
        return context.getResources().obtainTypedArray(id);
    }

    public static float getDimension(int id) throws Resources.NotFoundException {
        return context.getResources().getDimension(id);
    }

    public static int getDimensionPixelOffset(int id) throws Resources.NotFoundException {
        return context.getResources().getDimensionPixelOffset(id);
    }

    public static int getDimensionPixelSize(int id) throws Resources.NotFoundException {
        return context.getResources().getDimensionPixelSize(id);
    }

    public static float getFraction(int id, int base, int pbase) {
        return context.getResources().getFraction(id, base, pbase);
    }

    public static Drawable getDrawable(int id) throws Resources.NotFoundException {
        return context.getResources().getDrawable(id);
    }

    public static Movie getMovie(int id) throws Resources.NotFoundException {
        return context.getResources().getMovie(id);
    }

    public static int getColor(int id) throws Resources.NotFoundException {
        return context.getResources().getColor(id);
    }

    public static ColorStateList getColorStateList(int id) throws Resources.NotFoundException {
        return context.getResources().getColorStateList(id);
    }

    public static boolean getBoolean(int id) throws Resources.NotFoundException {
        return context.getResources().getBoolean(id);
    }

    public static int getInteger(int id) throws Resources.NotFoundException {
        return context.getResources().getInteger(id);
    }

    public static XmlResourceParser getLayout(int id) throws Resources.NotFoundException {
        return context.getResources().getLayout(id);
    }

    public static XmlResourceParser getAnimation(int id) throws Resources.NotFoundException {
        return context.getResources().getAnimation(id);
    }

    public static XmlResourceParser getXml(int id) throws Resources.NotFoundException {
        return context.getResources().getXml(id);
    }

    public static InputStream openRawResource(int id) throws Resources.NotFoundException {
        return context.getResources().openRawResource(id);
    }

    public static InputStream openRawResource(int id, TypedValue value) throws Resources.NotFoundException {
        return context.getResources().openRawResource(id, value);
    }

    public static AssetFileDescriptor openRawResourceFd(int id) throws Resources.NotFoundException {
        return context.getResources().openRawResourceFd(id);
    }

    public static void getValue(int id, TypedValue outValue, boolean resolveRefs) throws Resources.NotFoundException {
        context.getResources().getValue(id, outValue, resolveRefs);
    }

    public static void getValue(String name, TypedValue outValue, boolean resolveRefs)
        throws Resources.NotFoundException {
        context.getResources().getValue(name, outValue, resolveRefs);
    }

    public static Resources.Theme newTheme() {
        return context.getResources().newTheme();
    }

    public static TypedArray obtainAttributes(AttributeSet set, int[] attrs) {
        return context.getResources().obtainAttributes(set, attrs);
    }

    public static void updateConfiguration(Configuration config, DisplayMetrics metrics) {
        context.getResources().updateConfiguration(config, metrics);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return context.getResources().getDisplayMetrics();
    }

    public static Configuration getConfiguration() {
        return context.getResources().getConfiguration();
    }

    public static int getIdentifier(String name, String defType, String defPackage) {
        return context.getResources().getIdentifier(name, defType, defPackage);
    }

    public static String getResourceName(int resid) throws Resources.NotFoundException {
        return context.getResources().getResourceName(resid);
    }

    public static String getResourcePackageName(int resid) throws Resources.NotFoundException {
        return context.getResources().getResourcePackageName(resid);
    }

    public static String getResourceTypeName(int resid) throws Resources.NotFoundException {
        return context.getResources().getResourceTypeName(resid);
    }

    public static String getResourceEntryName(int resid) throws Resources.NotFoundException {
        return context.getResources().getResourceEntryName(resid);
    }

    public static void parseBundleExtras(XmlResourceParser parser, Bundle outBundle)
        throws XmlPullParserException, IOException {
        context.getResources().parseBundleExtras(parser, outBundle);
    }

    public static void parseBundleExtra(String tagName, AttributeSet attrs, Bundle outBundle)
        throws XmlPullParserException {
        context.getResources().parseBundleExtra(tagName, attrs, outBundle);
    }

    public static AssetManager getAssets() {
        return context.getResources().getAssets();
    }

    public static void flushLayoutCache() {
        context.getResources().flushLayoutCache();
    }

    public static void finishPreloading() {
        context.getResources().finishPreloading();
    }

    public static Person getUser() {
        return user;
    }

    public static void setUser(Person user) {
        AppContext.user = user;
    }
}
