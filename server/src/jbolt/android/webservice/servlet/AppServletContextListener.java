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
 * Person: feng.xie
 * Date: 18/06/11
 */
public class AppServletContextListener implements ServletContextListener {

    private static Locale cnLocale = new Locale("zh", "CN");

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        MKernelIOCFactory.initialize(
                new String[]{
                        "conf/services/Log4jTrace_Service.xml",
                        "conf/services/DataDictionary_Service.xml",
                        "conf/services/NumberSystem_Service.xml",
                        "conf/services/DAOLightweight_Service.xml",
                        "conf/services/jBoltCache_Service.xml",
                        "conf/services/Crud_Service.xml",
                        "conf/services/i18nDefault_Service.xml"});
        MKernelIOCFactory.getIocContainer().registerServiceBundle("Wardrobe", "conf/services/Wardrobe_Service.xml");
        DataDictionaryManager dataDictionaryManager =
                MKernelIOCFactory.getIocContainer().getService("dataDictionaryManager");
        dataDictionaryManager.registerConfigurationContext("Wardrobe", "conf/Wardrobe/domain/Wardrobe_Lightweight.xml");
        NumberSystemManager defaultNumberingManager =
                MKernelIOCFactory.getIocContainer().getService("defaultNumberingManager");
        defaultNumberingManager.registerNumberDefConfig(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("conf/Wardrobe/domain/Wardrobe_NumberingSystem.xml"));
        I18nManager i18nManager = MKernelIOCFactory.getIocContainer().getService("i18nManager");
        i18nManager.loadResourceInClassPath(cnLocale, "conf/Wardrobe/resource");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
