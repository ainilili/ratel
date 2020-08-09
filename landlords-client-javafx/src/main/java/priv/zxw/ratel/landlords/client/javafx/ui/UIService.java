package priv.zxw.ratel.landlords.client.javafx.ui;


import priv.zxw.ratel.landlords.client.javafx.ui.view.Method;

import java.util.HashMap;
import java.util.Map;

public class UIService {
    private Map<String, Method> methodMap = new HashMap<>(16);

    public void addMethods(Method... methods) {
        for (Method method : methods) {
            methodMap.put(method.getName(), method);
        }
    }

    public Method getMethod(String name) {
        return methodMap.get(name);
    }
}
