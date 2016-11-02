package browser.android.com.calculator.actions;

import java.util.HashMap;

public class Action {

    private String mType;
    private HashMap<String, Object> mData;

    Action(String type, HashMap<String, Object> data) {
        mType = type;
        mData = data;
    }

    public static class Builder {

        private String mType;
        private HashMap<String, Object> mData;

        public Builder with(String type) {
            if (type == null) {
                throw new IllegalArgumentException("Type must not be null");
            }
            mType = type;
            return this;
        }

        public Builder bundle(String key, Object value) {
            if (key == null) {
                throw new IllegalArgumentException("Key must not be null");
            }
            if (value == null) {
                throw new IllegalArgumentException("Value must not be null");
            }
            if (mData == null) {
                mData = new HashMap<>();
            }
            mData.put(key, value);
            return this;
        }

        public Action build() {
            return new Action(mType, mData);
        }
    }
}
