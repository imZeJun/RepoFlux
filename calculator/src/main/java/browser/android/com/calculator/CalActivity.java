package browser.android.com.calculator;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import browser.android.com.calculator.actions.ActionCreator;
import browser.android.com.calculator.dispatcher.Dispatcher;
import browser.android.com.calculator.stores.CalStore;

public class CalActivity extends AppCompatActivity {

    private Dispatcher mDispatcher;
    private CalStore mCalStore;
    private ActionCreator mActionCreator;

    private EditText mLeftEditText;
    private EditText mRightEditText;
    private Button mAddButton;
    private Button mMinusButton;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });
        initDependency();
        setupViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDependency() {
        mDispatcher = Dispatcher.getInstance(new Bus());
        mCalStore = CalStore.getInstance(mDispatcher);
        mActionCreator = ActionCreator.getInstance(mDispatcher);
    }

    private void setupViews() {
        mLeftEditText = (EditText) findViewById(R.id.left_num);
        mRightEditText = (EditText) findViewById(R.id.right_num);
        mAddButton = (Button) findViewById(R.id.add_button);
        mMinusButton = (Button) findViewById(R.id.minus_button);
        mResult = (TextView) findViewById(R.id.cal_result);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAddOp();
            }
        });
        mMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doMinusOp();
            }
        });
    }

    @Subscribe
    public void onCalStoreChangeEvent(CalStore.CalStoreChangeEvent event) {
        updateUi();
    }

    private void doAddOp() {
        int leftNum = Integer.valueOf(mLeftEditText.getText().toString());
        int rightNum = Integer.valueOf(mRightEditText.getText().toString());
        mActionCreator.add(leftNum, rightNum);
    }

    private void doMinusOp() {
        int leftNum = Integer.valueOf(mLeftEditText.getText().toString());
        int rightNum = Integer.valueOf(mRightEditText.getText().toString());
        mActionCreator.minus(leftNum, rightNum);
    }

    private void updateUi() {
        mResult.setText(String.valueOf(mCalStore.getResult()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDispatcher.register(this);
        mDispatcher.register(mCalStore);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDispatcher.unRegister(mCalStore);
        mDispatcher.unRegister(this);
    }
}
