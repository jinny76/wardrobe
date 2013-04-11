package jbolt.android.webservice.servlet;

import java.util.Locale;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import jbolt.core.datadict.DataDictionaryManager;
import jbolt.core.i18n.I18nManager;
import jbolt.core.ioc.MKernelIOCFactory;
import jbolt.core.numbering.NumberSystemManager;

/**
 * Component:
 * Description:
 * User: feng.xie
 * Date: 18/06/11
 */
public class ABoltServletContextListener implements ServletContextListener {

    private static Locale cnLocale = new Locale("zh", "CN");
    private static Locale enLocale = new Locale("en", "US");

    private static Locale itLocale = new Locale("it", "IT");
    private static Locale koLocale = new Locale("ko", "KR");
    private static Locale jaLocale = new Locale("ja", "JP");
    private static Locale frLocale = new Locale("fr", "FR");
    private static Locale ruLocale = new Locale("ru", "RU");

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        MKernelIOCFactory.initialize(
                new String[]{
                        "conf/services/Log4jTrace_Service.xml",
                        "conf/services/DataDictionary_Service.xml",
                        "conf/services/NumberSystem_Service.xml",
                        "conf/services/DAOLightweight_Service.xml",
                        "conf/services/jBoltCache_Service.xml",
                        "conf/services/Crud_Service.xml",
                        "conf/services/i18nDefault_Service.xml",
                        "conf/services/Distributor_Service.xml"});
        MKernelIOCFactory.getIocContainer().registerServiceBundle("Wardrobe", "conf/services/Wardrobe_Service.xml");
        DataDictionaryManager dataDictionaryManager =
                MKernelIOCFactory.getIocContainer().getService("dataDictionaryManager");
        dataDictionaryManager.registerConfigurationContext("Wardrobe", "conf/wardrobe/domain/Wardrobe_Lightweight.xml");
        NumberSystemManager defaultNumberingManager =
                MKernelIOCFactory.getIocContainer().getService("defaultNumberingManager");
        defaultNumberingManager.registerNumberDefConfig(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("conf/wardrobe/domain/Wardrobe_NumberingSystem.xml"));
        I18nManager i18nManager = MKernelIOCFactory.getIocContainer().getService("i18nManager");
        i18nManager.loadResourceInClassPath(cnLocale, "conf/wardrobe/resource");
        i18nManager.loadResourceInClassPath(enLocale, "conf/wardrobe/resource");
        i18nManager.loadResourceInClassPath(itLocale, "conf/wardrobe/resource");
        i18nManager.loadResourceInClassPath(koLocale, "conf/wardrobe/resource");
        i18nManager.loadResourceInClassPath(jaLocale, "conf/wardrobe/resource");
        i18nManager.loadResourceInClassPath(frLocale, "conf/wardrobe/resource");
        i18nManager.loadResourceInClassPath(ruLocale, "conf/wardrobe/resource");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
