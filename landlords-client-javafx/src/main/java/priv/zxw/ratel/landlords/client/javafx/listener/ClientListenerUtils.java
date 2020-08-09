package priv.zxw.ratel.landlords.client.javafx.listener;

import org.nico.ratel.landlords.enums.ClientEventCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.zxw.ratel.landlords.client.javafx.ui.UIService;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ClientListenerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientListenerUtils.class);

    /** code - listener 映射 */
    private static final Map<ClientEventCode, ClientListener> LISTENER_MAP = new HashMap<>(16);

    /**
     * 获取code对应的事件监听器
     *
     * @param code 事件编码
     * @return  code对应的事件监听器，如果不存在对应的事件监听器则返回null
     */
    public static ClientListener getListener(ClientEventCode code) {
        ClientListener clientListener = LISTENER_MAP.get(code);

        return clientListener;
    }

    public static ClientEventCode[] supportCodes() {
        return LISTENER_MAP.keySet().toArray(new ClientEventCode[] {});
    }

    public static void setUIService(UIService uiService) {
        for (ClientListener listener : LISTENER_MAP.values()) {
            listener.setUIService(uiService);
        }
    }

    static {
        List<Class<ClientListener>> listenerClassList = findListener();

        for (Class<ClientListener> clazz : listenerClassList) {
            try {
                ClientListener listener = clazz.newInstance();
                LISTENER_MAP.put(listener.getCode(), listener);

                LOGGER.info("添加 {} -> {} 事件监听器映射", listener.getCode(), listener.getClass().getName());
            } catch (InstantiationException e) {
                LOGGER.warn(clazz.getName() + " 不能被实例化");
            } catch (IllegalAccessException e) {
                LOGGER.warn(clazz.getName() + " 没有默认构造函数或默认构造函数不可访问", e);
            }
        }
    }

    private static List<Class<ClientListener>> findListener() {
        ClassLoader defaultClassLoader = ClientListenerUtils.class.getClassLoader();
        URL classWorkPath = ClientListenerUtils.class.getResource("");
        File classWorkDir = new File(classWorkPath.getPath());

        return loadClasses(defaultClassLoader, classWorkDir.listFiles(ClientListenerUtils::isNormalClass))
                .stream()
                .filter(clazz -> clazz.getSuperclass() == AbstractClientListener.class)
                .map(clazz -> (Class<ClientListener>) clazz)
                .collect(Collectors.toList());
    }

    private static boolean isNormalClass(File file) {
        String fileName = file.getName();
        boolean isClassFile = fileName.endsWith(".class");
        boolean isNotInnerClassFile = !fileName.matches("[A-Z]\\w+\\$\\w+.class");

        return isClassFile && isNotInnerClassFile;
    }

    private static List<Class<?>> loadClasses(ClassLoader classLoader, File[] classFiles) {
        String classpath = classLoader.getResource("").getPath();

        List<Class<?>> classList = new ArrayList<>(classFiles.length);
        for (File classFile : classFiles) {
            String absolutePath = classFile.getAbsolutePath();
            String classFullName = absolutePath.substring(classpath.length() - 1, absolutePath.lastIndexOf("."))
                                               .replace(File.separator, ".");

            try {
                classList.add(classLoader.loadClass(classFullName));
            } catch (ClassNotFoundException e) {
                LOGGER.warn("默认类加载器在 {} 路径下没有找到 {} 类", classpath, classFullName);
            }
        }

        return classList;
    }
}
