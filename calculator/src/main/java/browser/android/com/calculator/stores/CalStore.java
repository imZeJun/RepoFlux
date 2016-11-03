package browser.android.com.calculator.stores;

import com.squareup.otto.Subscribe;
import browser.android.com.calculator.actions.Action;
import browser.android.com.calculator.actions.CalActions;
import browser.android.com.calculator.dispatcher.Dispatcher;

public class CalStore extends Store {

    private int mResult;
    private static CalStore sInstance;

    public static CalStore getInstance(Dispatcher dispatcher) {
        if (sInstance == null) {
            sInstance = new CalStore(dispatcher);
        }
        return sInstance;
    }

    CalStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public int getResult() {
        return mResult;
    }

    @Override
    @Subscribe
    public void onAction(Action action) {
        int leftNum;
        int rightNum;
        switch (action.getType()) {
            case CalActions.ADD:
                leftNum = (int) action.getData().get(CalActions.KEY_LEFT_NUM);
                rightNum = (int) action.getData().get(CalActions.KEY_RIGHT_NUM);
                mResult = leftNum + rightNum;
                emitStoreChange();
                break;
            case CalActions.MINUS:
                leftNum = (int) action.getData().get(CalActions.KEY_LEFT_NUM);
                rightNum = (int) action.getData().get(CalActions.KEY_RIGHT_NUM);
                mResult = leftNum - rightNum;
                emitStoreChange();
                break;
            default:
                break;
        }
    }

    @Override
    public StoreChangeEvent changeEvent() {
        return new CalStoreChangeEvent();
    }

    public class CalStoreChangeEvent implements StoreChangeEvent {};
}
