package com.example.memov2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class Add_newUser extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText userIdEdit;
    private TextInputEditText passwordEdit;
    private TextInputEditText passwordEdit2;
    private TextInputEditText emailEdit;
    private Button registerBtn;
    private TextInputLayout Layout_new_userId;
    private TextInputLayout Layout_new_password;
    private TextInputLayout Layout_new_password2;
    private TextInputLayout Layout_new_email;
    private CharSequence hintid;
    private CharSequence hintpass;
    private String userIdStr;
    private String passwordStr;
    private String passwordStr2;
    private String emailStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);  //新規登録画面のXMLを参照

        userIdEdit = findViewById(R.id.new_userId);
        passwordEdit = findViewById(R.id.new_password);
        passwordEdit2 = findViewById(R.id.new_password2);
        emailEdit = findViewById(R.id.new_email);
        registerBtn = findViewById(R.id.register);
        registerBtn.setOnClickListener(this);

        Layout_new_userId = findViewById(R.id.Layout_new_userId);
        Layout_new_password = findViewById(R.id.Layout_new_password);
        Layout_new_password2 = findViewById(R.id.Layout_new_password2);
        Layout_new_email = findViewById(R.id.Layout_new_email);

//        hintid = Layout_new_userId.getHint();
//        Layout_new_userId.setHint(null);
//        Layout_new_userId.getEditText().setHint(hintid);
//
//        hintpass = Layout_new_password.getHint();
//        Layout_new_password.setHint(null);
//        Layout_new_password.getEditText().setHint(hintpass);
//
//        hintpass = Layout_new_password2.getHint();
//        Layout_new_password2.setHint(null);
//        Layout_new_password2.getEditText().setHint(hintpass);
//
//        hintpass = Layout_new_email.getHint();
//        Layout_new_email.setHint(null);
//        Layout_new_email.getEditText().setHint(hintpass);

    }

    @Override
    public void onClick(View view) {    //登録ボタンを押したときの処理

        if (view.getId() == R.id.register) {
            //入力内容の受け取り
            userIdStr = String.valueOf(userIdEdit.getText());
            passwordStr = String.valueOf(passwordEdit.getText());
            passwordStr2 = String.valueOf(passwordEdit2.getText());
            emailStr = String.valueOf(emailEdit.getText());

            //入力値のチェック
            Integer idResult = checkIdPattern(userIdStr);
            Integer passResult = checkPassPattern(passwordStr);
            Integer pass2Result = checkPassPattern2(passwordStr,passwordStr2);
            Integer emailResult = checkEmailPattern(emailStr);
            Integer isNew = isNull(userIdStr);

            if (isNew == 0) {   //データベース内に同じidがない場合
                if (idResult == 0 && passResult == 0 &&
                    pass2Result == 0 && emailResult == 0) {   //入力内容のチェック
                    addData();
                    Intent intent = new Intent(getApplication(), UserLogin.class);  //ログイン画面へ遷移
                    startActivity(intent);

                } else {    //エラーメッセージ出力
                    Layout_new_userId.setError(checkMessage(idResult));
                    Layout_new_password.setError(checkMessage(passResult));
                    Layout_new_password2.setError(checkMessage(pass2Result));
                    Layout_new_email.setError(checkMessage(emailResult));
                }
            } else {    //エラーメッセージ出力
                Layout_new_userId.setError(checkMessage(isNew));
                Layout_new_password.setError(checkMessage(passResult));
                Layout_new_password2.setError(checkMessage(pass2Result));
                Layout_new_email.setError(checkMessage(emailResult));
            }

        }
    }

    public Integer isNull(String userIdStr) {    //データベース内に同じidがあるかどうかをチェック
        Integer result = 0;

        try {
            DataListHelper dataListHelper = new DataListHelper(this);
            List<RowData> dataset = dataListHelper.selectDetail(userIdStr);
            String checkId = dataset.get(0).getUserid();
            if (checkId != null) {
                for (int i = 0; i < dataset.size(); i++) {
                    if (userIdStr.equals(checkId)) {
                        result = 1;
                        return result;
                    }
                }
            }
        } catch(Exception e) {
            result = 0;
        }
            return result;
    }

    public Integer checkIdPattern(String id) {  //idのバリデーション
        Integer result = 0; //id,pass,emailの総合結果

        if (id.isEmpty()) { //未入力
            result = -1;
        }
        if (!isSignCheck(id)) { //特殊文字使用チェック
            result = 20;
        }

        return result;
    }

    public Integer checkPassPattern(String password) {  //パスワード1回目のバリデーション
        Integer resultPassword = 0; //パスワードチェック結果の変数
        String pattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])[a-zA-Z0-9]{8,}$";  //パスワードの正規化パターン

        if (password.isEmpty()) { //未入力
            resultPassword = -2;
        } else if (!isSignCheck(password)) {    //特殊文字使用チェック
            resultPassword = 20;
        } else if (!password.matches(pattern)) {    //Eメールパターンチェック
            resultPassword = 10;
        }

        return resultPassword;
    }

    public Integer checkPassPattern2(String password, String password2) {   //パスワード2回目のバリデーション
        Integer resultPassword2 = 0; //パスワードチェック結果の変数
        String pattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])[a-zA-Z0-9]{8,}$";  //パスワードの正規化パターン

        if (password2.isEmpty()) { //未入力
            resultPassword2 = -2;
        } else if (!isSignCheck(password2)) {    //特殊文字使用チェック
            resultPassword2 = 20;
        } else if (!password2.matches(pattern)) {    //Eメールパターンチェック
            resultPassword2 = 10;
        } else if (!isSame(password, password2)) {  //1回目と2回目の内容チェック
            resultPassword2 = 5;
        }

        return resultPassword2;
    }

    public Integer checkEmailPattern(String email) {    //Emailのバリデーション
        Integer resultEmail = 0; //emailチェック結果の変数

        if (email.isEmpty()) {
            resultEmail = -3;
        } else if (!isSignCheck(email)) {
            resultEmail = 20;
        } else  if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            resultEmail = 100;
        }

        return resultEmail;
    }

    public String checkMessage(Integer num) {   //表示するエラーメッセージ
        String result = "";
        String ms1 = "ユーザIdが入力されていません。";
        String ms2 = "入力したIdが既に使用されているため、別のIdを入力してください。";
        String ms3 = "パスワードが入力されていません。";
        String ms4 = "emailが入力されていません。";
        String ms5 = "英字大小、数字を含む8文字以上で入力してください。";
        String ms6 = "パスワードの1回目と2回目の入力が異なっています。";
        String ms7 = "Eメールが正しく入力されていません。";
        String ms8 = "不正な入力がありました。";

        switch (num) {
            case -1:
                result = ms1;
                break;
            case -2:
                result = ms3;
                break;
            case -3:
                result = ms4;
                break;
            case 0:
                result = null;
                break;
            case 1:
                result = ms2;
                break;
            case 5:
                result = ms6;
                break;
            case 10:
                result = ms5;
                break;
            case 20:
                result = ms8;
                break;
            case 100:
                result = ms7;
                break;
        }

        return result;
    }

    public boolean isSignCheck(String password) {       //入力内容の特殊文字チェック
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

    public boolean isSame(String pass1, String pass2) {    //パスワード2回分の入力が同じかどうかをチェック
        boolean result = false;

        if (pass1.equals(pass2)) {
            result = true;
        }
        return result;
    }

    public void addData() {
        DataListHelper helper = new DataListHelper(this);
        RowData data = new RowData();   //データを1行のデータとして登録するためにインスタンス化
        data.setUserid(userIdStr);
        data.setPassWord(passwordStr);
        data.setEmail(emailStr);
        helper.dataInsert(data);    //データの登録メソッドを呼び出し、タイトルと詳細を追加する
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//    }

}
