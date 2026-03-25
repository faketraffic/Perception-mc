package xyz.tharmsy.perception.event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {
    private final Map<Class<?>, CopyOnWriteArrayList<MethodData>> REGISTRY = new HashMap<>();

    public void register(Object o) {
        for (Method method : o.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(EventTarget.class))
                continue;
            Class<?>[] parameters = method.getParameterTypes();
            if (parameters.length != 1)
                continue;
            if (!Event.class.isAssignableFrom(parameters[0]))
                continue;

            method.setAccessible(true);
            MethodData data = new MethodData(o, method);

            REGISTRY.computeIfAbsent(parameters[0], k -> new CopyOnWriteArrayList<>()).add(data);
        }
    }

    public void unregister(Object o) {
        for (CopyOnWriteArrayList<MethodData> list : REGISTRY.values()) {
            list.removeIf(data -> data.source == o);
        }
    }

    public void call(Event event) {
        CopyOnWriteArrayList<MethodData> list = REGISTRY.get(event.getClass());
        if (list == null)
            return;

        for (MethodData data : list) {
            try {
                data.target.invoke(data.source, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class MethodData {
        private final Object source;
        private final Method target;

        public MethodData(Object source, Method target) {
            this.source = source;
            this.target = target;
        }
    }
}
