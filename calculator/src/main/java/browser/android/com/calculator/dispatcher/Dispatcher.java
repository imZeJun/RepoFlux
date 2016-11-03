package browser.android.com.calculator.dispatcher;

import com.squareup.otto.Bus;

import browser.android.com.calculator.actions.Action;
import browser.android.com.calculator.stores.Store;

public class Dispatcher {

    private static Dispatcher sInstance;
    private Bus mBus;

    public static Dispatcher getInstance(Bus bus) {
        if (sInstance == null) {
            sInstance = new Dispatcher(bus);
        }
        return sInstance;
    }

    private Dispatcher(Bus bus) {
        mBus = bus;
    }

    public void register(Object observer) {
        if (mBus != null) {
            mBus.register(observer);
        }
    }

    public void unRegister(Object observer) {
        if (mBus != null) {
            mBus.unregister(observer);
        }
    }

    public void emitStoreChange(Store.StoreChangeEvent changeEvent) {
        post(changeEvent);
    }

    public void dispatch(String key, Object... data) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("Key must not be empty");
        }
        if (data != null && data.length % 2 != 0) {
            throw new IllegalArgumentException("Data must be a key-value pair");
        }
        Action.Builder actionBuilder = new Action.Builder();
        actionBuilder.with(key);
        int i = 0;
        if (data != null) {
            while (i < data.length) {
                actionBuilder.bundle((String) data[i++], data[i++]);
            }
        }
        post(actionBuilder.build());
    }

    private void post(Object event) {
        if (mBus != null) {
            mBus.post(event);
        }
    }

}
