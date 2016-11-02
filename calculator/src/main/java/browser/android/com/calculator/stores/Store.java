package browser.android.com.calculator.stores;

import browser.android.com.calculator.actions.Action;
import browser.android.com.calculator.dispatcher.Dispatcher;

public abstract class Store {

    Dispatcher mDispatcher;

    Store(Dispatcher dispatcher) {
        mDispatcher = dispatcher;
    }

    public void emitStoreChange() {
        if (mDispatcher != null) {
            mDispatcher.emitStoreChange(changeEvent());
        }
    }

    public abstract void onCalFinished(Action action);

    public abstract StoreChangeEvent changeEvent();

    public interface StoreChangeEvent {}

}
