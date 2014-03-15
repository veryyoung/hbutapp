package com.young.activity;

import java.io.IOException;

import org.json.JSONException;

import com.young.R;
import com.young.business.HBUT;
import com.young.util.NetworkUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePwdActivity extends BaseActivity {
    private String stuId;
    private String password;
    private SharedPreferences sp;

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd);
        Button buttonCancle = (Button) findViewById(R.id.cancleButton);
        sp = ChangePwdActivity.this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        getUserIdAndPassWord();
        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePwdActivity.this.finish();
            }
        });
        Button modifyButton = (Button) findViewById(R.id.changeButton);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        EditText oldPass = (EditText) findViewById(R.id.change_oldpw);
                        EditText newPass = (EditText) findViewById(R.id.change_newpw);
                        EditText conPass = (EditText) findViewById(R.id.change_conpw);

                        String oldPassword = oldPass.getText().toString();
                        String newPassword = newPass.getText().toString();
                        String conPassword = conPass.getText().toString();
                        if (!NetworkUtil.isOpenNetwork()) {
                            finish();
                            Looper.prepare();
                            Toast.makeText(getBaseContext(), "无网络连接",
                                    Toast.LENGTH_LONG).show();
                            Looper.loop();
                        } else {
                            HBUT hbut = HBUT.getInstance();
                            try {
                                hbut.login(stuId, password);
                                String message = hbut.changePass(oldPassword,
                                        newPassword, conPassword);
                                Looper.prepare();

                                Toast.makeText(getBaseContext(), message,
                                        Toast.LENGTH_LONG).show();
                                if ("保存成功".equals(message)) {
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("PASSWORD", newPassword);
                                    editor.commit();
                                    ChangePwdActivity.this.finish();
                                }
                                Looper.loop();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

    }

    private void getUserIdAndPassWord() {
        stuId = sp.getString("USER_NAME", "");
        password = sp.getString("PASSWORD", "");
    }


}
