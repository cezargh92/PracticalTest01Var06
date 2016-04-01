package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PracticalTest01Var06MainActivity extends ActionBarActivity {

    private Button mDetailsButton;
    private Button mPassFailButton;
    private RelativeLayout mCenterRl;
    private EditText mLinkEditText;
    private EditText mTopEditText;
    private Button mNav;
    private PracticalTest01Var06Service service;
    private StartedServiceBroadcastReceiver recv;


    private int req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_main);

        mDetailsButton = (Button) findViewById(R.id.details_button);
        mCenterRl = (RelativeLayout) findViewById(R.id.center_rel_layout);
        mLinkEditText = (EditText) findViewById(R.id.link_edit_text);
        mPassFailButton = (Button) findViewById(R.id.pass_fail_button);
        mTopEditText = (EditText) findViewById(R.id.top_edit_text);
        mNav = (Button) findViewById(R.id.second_app_button);

        mDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCenterRl.getVisibility() == View.VISIBLE) {
                    mCenterRl.setVisibility(View.INVISIBLE);
                    mDetailsButton.setText("More details");
                } else {
                    mCenterRl.setVisibility(View.VISIBLE);
                    mDetailsButton.setText("Less details");
                }

            }
        });

        mLinkEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String editTextString = mLinkEditText.getText().toString();
                if (editTextString.length() >= 4) {
                    if ("http".equals(editTextString.substring(0, 4))) {
                        mPassFailButton.setText("Pass");
                        mPassFailButton.setBackground(getResources().getDrawable(R.color.green));

                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
                        intent.putExtra("link", mLinkEditText.getText().toString());
                        startService(intent);

                    } else {
                        mPassFailButton.setText("Fail");
                        mPassFailButton.setBackground(getResources().getDrawable(R.color.red));
                    }
                } else {
                    mPassFailButton.setText("Fail");
                    mPassFailButton.setBackground(getResources().getDrawable(R.color.red));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        recv = new StartedServiceBroadcastReceiver();

        req = 10;

        mNav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06SecondaryActivity.class);
                intent.putExtra("link", mLinkEditText.getText().toString());
                intent.putExtra("pass_fail", mPassFailButton.getText().toString());
                startActivityForResult(intent, req);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
            Toast.makeText(getApplicationContext(), "OK!!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "CANCEL!!", Toast.LENGTH_LONG).show();
    }

        @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("top_edit_text", mTopEditText.getText().toString());
        savedInstanceState.putString("details_button", mDetailsButton.getText().toString());
        savedInstanceState.putString("pass_fail_button", mPassFailButton.getText().toString());
        savedInstanceState.putString("link_edit_text", mLinkEditText.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String top = savedInstanceState.getString("top_edit_text");
        String detailsButton = savedInstanceState.getString("details_button");
        String passFail = savedInstanceState.getString("pass_fail_button");
        String link = savedInstanceState.getString("link_edit_text");

        Toast.makeText(getApplicationContext(), top + "\n" + detailsButton + "\n" + passFail + "\n" + link, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Constants.ACTION_1);
        registerReceiver(recv, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        stopService(new Intent(this, PracticalTest01Var06Service.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_var06_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
