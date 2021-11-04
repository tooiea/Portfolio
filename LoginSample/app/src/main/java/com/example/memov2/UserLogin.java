package com.example.memov2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class UserLogin<pubic> extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText userId;
    private TextInputEditText password;
    private Button loginBtn;
    private TextInputLayout textInputLayout_userId;
    private TextInputLayout textInputLayout_password;
    private CharSequence hintid;
    private CharSequence hintpass;
    private String userIdStr;
    private String passwordStr;
    private String checkId;
    private String checkPassword;
    private static int COUNT_LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        userId = findViewById(R.id.userID);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        textInputLayout_userId = findViewById(R.id.textInputLayout_userId);
        textInputLayout_password = findViewById(R.id.textInputLayout_password);

//        hintid = textInputLayout_userId.getHint();
//        textInputLayout_userId.setHint(null);
//        textInputLayout_userId.getEditText().setHint(hintid);
//        hintpass = textInputLayout_password.getHint();
//        textInputLayout_password.setHint(null);
//        textInputLayout_password.getEditText().setHint(hintpass);
    }

    @Override
    public void onRestart() {   //バックボタンを押されたときに、入力をリセットする
        super.onRestart();
        COUNT_LOGIN = 0;
    }

        @Override
    public void onClick(View view) {
        if (COUNT_LOGIN <= 4) {
            if (view.getId() == R.id.loginBtn) {
                userIdStr = String.valueOf(userId.getText());
                passwordStr = String.valueOf(password.getText());

                Integer idNum = 0;
                Integer passNum = 0;

                idNum = checkId(userIdStr);
                passNum = checkPass(passwordStr);

                if (idNum == 0 && passNum == 0) {     //idとパスワードが一致したとき
                    userId.setText(null);   //入力内容をリセット
                    password.setText(null);
                    Intent intent = new Intent(getApplication(), SelectMenu.class);
                    startActivity(intent);
                }    //エラーメッセージ出力
                textInputLayout_userId.setError(checkMessage(idNum));
                textInputLayout_password.setError(checkMessage(passNum));
            }
        } else {    //エラーメッセージ出力
            textInputLayout_userId.setError("ログインの制限回数を超えています。15分後に再度、入力してください。");
            textInputLayout_password.setError("ログインの制限回数を超えています。15分後に再度、入力してください。");
        }
    }

    public boolean isNull(String userIdStr) {   //DBにアクセスし、データの有無をチェック
        boolean result = false;

        try {
            DataListHelper dataListHelper = new DataListHelper(this);
            List<RowData> dataset = dataListHelper.selectDetail(userIdStr);
            checkId = dataset.get(0).getUserid();
            checkPassword = dataset.get(0).getPassword();
            if (checkId != null) {
                for (int i = 0; i < dataset.size(); i++) {
                    if (userIdStr.equals(checkId)) {
                        result = true;
                        return result;
                    }
                }
            }
        } catch(Exception e) {}
        return result;
    }

    public Integer checkId(String id) { //入力されたidをチェック
        Integer idNum = 0;
        boolean USERID = isNull(userIdStr);
        boolean idJudge = false;

        if (USERID) {   //ユーザidがDBに登録されているか
            if (!isSignCheck(id)) { //入力内容に特殊文字が使われているか
                idNum = 3;
            } else if (id.equals(checkId)) {
                idNum = 0;
            }

        } else if (id.isEmpty()) {  //未入力
                idNum = -1;
            } else  if (!isSignCheck(id)) { //入力内容に特殊文字が使われているか
                idNum = 3;
            }else {    //登録されていない
                idNum = 1;
            }
        return idNum;
    }

    public Integer checkPass(String pass) { //入力されたパスワードをチェック
        Integer passNum = 0;
        boolean passJudge = false;

        if (pass.equals(checkPassword)) {
            passJudge = true;
        }

        if (pass.isEmpty()) {
            passNum = -2;
            COUNT_LOGIN ++;
        } else {    //passwordが未入力でない場合
            if (isSignCheck(pass)) { //パスワードに不正な入力がないかをチェック
                if (!passJudge) {   //パスワードが間違っているとき
                    passNum = 2;
                    COUNT_LOGIN ++;
                }
            } else {
                passNum = 3;
                COUNT_LOGIN ++;
            }
        }
        return passNum;
    }

    public boolean isSignCheck(String password) {   //不正入力(SQLインジェクション対策// )
        boolean result = true;
        String[] strArray = password.split("");

        for (int i = 0; i < strArray.length; i++) {
            String tmp = strArray[i];
            switch (tmp) {
                case "\"" :
                case "\'" :
                case "=" :
                case "*" :
                case "?" :
                case "(" :
                case ")" :
                    result = false;
                    break;
            }
            if (!result) {
                break;
            }
        }
        return result;
    }

    public String checkMessage(Integer num) {   //表示するメッセージをチェック
        String result = "";
        String strNullid = "Idが入力されていません";
        String strnonid = "入力されたidは登録されていません";
        String strNullpass = "パスワードが入力されていません";
        String strid = "Idが間違っています。";
        String strpass = "パスワードが間違っています。";
        String strincorrect = "不正な入力がありました。";

        switch (num) {
            case -1:
                result = strNullid;
                break;
            case -2:
                result = strNullpass;
                break;
            case 0:
                result = null;
                break;
            case 1:
                result = strid;
                break;
            case 2:
                result = strpass;
                break;
            case 3:
                result = strincorrect;
                break;

        }
        return result;
    }
}