package com.demo.zejun.repoflux.dispatcher;

import com.demo.zejun.repoflux.actions.Action;
import com.demo.zejun.repoflux.stores.Store;
import com.squareup.otto.Bus;

public class Dispatcher {

    private final Bus bus;
    private static Dispatcher instance;

    public static Dispatcher get(Bus bus) { //Dispatcher是一个单例模式，只有一个对象，并且它和Bus相关联。
        if (instance == null) {
            instance = new Dispatcher(bus);
        }
        return instance;
    }

    Dispatcher(Bus bus) {
        this.bus = bus;
    }

    public void register(final Object cls) { //接收者将会收到相应的Action。
        bus.register(cls);
    }

    public void unRegister(final Object cls) { //反注册接收者。
        bus.unregister(cls);
    }

    public void emitChange(Store.StoreChangeEvent event) { //由Store模块调用来发送事件。
        post(event);
    }

    public void dispatch(String type, Object... data) { //分发原始的事件，并把键值对转换成为Action。
        if (isEmpty(type)) {
            throw new IllegalArgumentException("Type must be not empty");
        }
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("Data must be a valid list of key, value pairs");
        }
        Action.Builder actionBuilder = Action.type(type);
        int i = 0;
        while (i < data.length) {
            String key = (String) data[i++];
            Object value = data[i++];
            actionBuilder.bundle(key, value);
        }
        post(actionBuilder.build());
    }

    private boolean isEmpty(String type) {
        return type == null || type.isEmpty();
    }

    private void post(final Object event) { //通过总线发送事件给各个接收者。
        bus.post(event);
    }
}
