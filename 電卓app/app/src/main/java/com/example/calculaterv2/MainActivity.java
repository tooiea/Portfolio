package com.example.calculaterv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //ボタン、テキストのフィールド宣言
    private Button btn_0;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;
    private Button btn_8;
    private Button btn_9;
    private Button btn_plus;
    private Button btn_minus;
    private Button btn_times;
    private Button btn_dev;
    private Button btn_equal;
    private Button btn_decimal;
    private Button btn_allClear;
    private Button btn_rate;
    private Button btn_change;
    private Button btn_return;
    private TextView txtResult;
    private TextView txtNum1;
    private TextView txtNum2;
    private TextView txtSign;
    private String resultStr = "";
    private String num1 = "";   //表示変数num1(上部左側)
    private String num2 = "";   //表示変数num2(上部右側)
    private String sign = "";   //表示変数sign(上部中央)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //xmlファイルの紐づけ

        //ボタンの変数とidの紐づけ
        btn_0 = findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);
        btn_1 = findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2 = findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        btn_3 = findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
        btn_4 = findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);
        btn_5 = findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);
        btn_6 = findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);
        btn_7 = findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);
        btn_8 = findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);
        btn_9 = findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);
        btn_allClear = findViewById(R.id.btn_delete);
        btn_allClear.setOnClickListener(this);
        btn_change = findViewById(R.id.btn_change);
        btn_change.setOnClickListener(this);
        btn_decimal = findViewById(R.id.btn_decimal);
        btn_decimal.setOnClickListener(this);
        btn_dev = findViewById(R.id.btn_div);
        btn_dev.setOnClickListener(this);
        btn_equal = findViewById(R.id.btn_equal);
        btn_equal.setOnClickListener(this);
        btn_minus = findViewById(R.id.btn_minus);
        btn_minus.setOnClickListener(this);
        btn_plus = findViewById(R.id.btn_plus);
        btn_plus.setOnClickListener(this);
        btn_times = findViewById(R.id.btn_times);
        btn_times.setOnClickListener(this);
        btn_rate = findViewById(R.id.btn_rate);
        btn_rate.setOnClickListener(this);
        btn_return = findViewById(R.id.btn_return);
        btn_return.setOnClickListener(this);
        txtResult = findViewById(R.id.txt_result);  //結果表示
        txtResult.setText("0"); //初期設定を0表示とする
        txtNum1 = findViewById(R.id.txt_num1);  //上部表示左
        txtNum2 = findViewById(R.id.txt_num2);  //上部表示中央
        txtSign = findViewById(R.id.txt_sign);  //上部表示右
    }

    public Boolean isSign(String str) { //符号のチェックメソッド
        boolean result;
        switch (str) {  //strで文字列を受け付けて、switchで判定
            case "+":
            case "-":
            case "×":
            case "÷":
            case "%":
            case "+ / -":
            case "=":
            case "←":
                result = true;  //符号であれば、trueを代入
                break;
            default:
                result = false; //符号でなければ、falseを代入
        }
        return result;
    }

    public BigDecimal calculator(String num1, String num2, String sign) {  //計算するメソッドBigDecimalクラスを使用
        BigDecimal result = BigDecimal.valueOf(0);  //returnで返す変数を用意
        BigDecimal bdnum1 = new BigDecimal(num1);
        BigDecimal bdnum2 = new BigDecimal(num2);

        switch (sign) {
            case "+":      //signの文字列が"+"
                BigDecimal addResult = bdnum1.add(bdnum2);  //addメソッドで加算
                result = addResult;
                break;
            case "-":   //signの文字列が"-"
                BigDecimal subtractResult = bdnum1.subtract(bdnum2);   //subtractメソッドで減算
                result = subtractResult;
                break;
            case "×":   //signの文字列が"×"
                BigDecimal multiplyResult = bdnum1.multiply(bdnum2);   //multiplyメソッドで乗算
                result = multiplyResult;
                break;
            case "÷":   //signの文字列が"÷"
                BigDecimal divideResult = bdnum1.divide(bdnum2, 9, BigDecimal.ROUND_HALF_DOWN);   //divideメソッドで除算(9桁で表示)
                result = divideResult;
                break;
        }
        return result;
    }

    public String changeFormat(BigDecimal str) { //数値桁の切り捨て
        String data = String.valueOf(str);  //Stringに型変換
        try {   //try catchでParseExceptionのキャッチを指定
            double num = DecimalFormat.getNumberInstance().parse(String.valueOf(str)).doubleValue();    //strをdouble型に変換
            int n = (int)(data).length();   //数値の桁チェック
            int ch = data.indexOf("E");     //指数表示になっているかをチェック

            if (ch != -1) { //指数表示になっていないか
                if (num == (int) num) {
                    if ((num > 0 && n > 9) || (num < 0 && n > 10)) {
                        data = String.valueOf((int)num);
                    }
                    data = String.valueOf((int)num);
                } else {
                    data =String.valueOf(str);
                }
            } else {
                if (num == (long) num) {
                    if ((num > 0 && n > 9) || (num < 0 && n > 10)) {
                        data = String.valueOf((long)num);
                    }
                    data =  String.valueOf((long)num);
                }
                else {
                    data =String.valueOf(num);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    public String changeCode(String num) {  //符号の反転(+,-)
        double dataf = 0.0000000E0;
        long datai = 0;
        String temp = String.valueOf(num.charAt(0));
        dataf = Double.parseDouble(num);

        if (dataf == (long)dataf) {  //整数の場合(正の数)  *doubleへキャストした数字がlongへキャストした時と差異がない＝整数とみなす
            datai = Long.parseLong(num);  //整数変換のdataiを準備
            if (temp.equals("-")) { //負の数の場合
                datai = datai * -1; //-1を掛けて符号
                return String.valueOf(datai);   //String型へ変換し、return
            } else {    //整数の負の数
                datai = datai * -1;
                return String.valueOf(datai);  //String型へ変換し、return
            }

        } else {    //小数点数の場合
            if (temp.equals("-")) { //負の数の場合
                dataf = dataf * -1;     //-1を掛けて符号を反転
                return String.valueOf(dataf);
            } else {
                dataf = dataf * -1;
                return String.valueOf(dataf);
            }
        }
    }

    public String isNumber(String num) {    //%ボタンで100分の1の数値へ変換メソッド
        double data = Double.parseDouble(num);
        BigDecimal result = BigDecimal.valueOf(0);
        BigDecimal bdnum1 = new BigDecimal(num);
        BigDecimal bdnum2 = BigDecimal.valueOf(100);

        if (data < 0) {
            if (data <= -100) {
                result = bdnum1.divide(bdnum2);
            }
        } else {
            result = bdnum1.divide(bdnum2);
        }
        return changeFormat(result);
    }

    public boolean isError_div(String sign, String num2) {  //ゼロ除算のエラーチェックメソッド
        if (sign.equals("÷")) {
            if (num2.equals("0"))
                return true;
        }
        return false;
    }

    public void makeToast() {   //トースト表示メソッド
        Toast toast = Toast.makeText(this,"エラー",Toast.LENGTH_SHORT);
        toast.show();
    }

    public String deleteCha(String str) {   //１文字戻しメソッド(削除)
        int n = str.length() -1;
        if (str.length() == 1) {
            str = "0";
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append(str);
            str = String.valueOf(sb.deleteCharAt(n));
        }
        return str;
    }

    public String checkDigit(String str) {  //桁チェック(正の数：９桁まで、負の数：１０桁までの入力制限)
        int n = str.length();
        String fc = String.valueOf(str.charAt(0));  //
        String temp = str;
        int num = 0;
        double dataf = 0.0000000E0;

        if (n > 9) {
            if (fc.equals("-")) {   //-が含まれている
                if (n > 10) {   //10桁を超えたとき
                    num = n - 10;   //桁あふれ分をチェック
                    int tnum = n - 1;   //最大要素数(桁数)
                    for (int i = 0; i < num; i++) { //桁があふれた分だけを要素数が大きい後ろからカット
                        StringBuffer sb = new StringBuffer();
                        sb.append(temp);
                        temp = String.valueOf(sb.deleteCharAt(tnum));   //deleteCharAtで一番後ろを削除する
                        tnum--; //次の要素数が大きいもの(一番後ろ)に要素数を変更(上でカットしたため)
                    }
                    return temp;
                } else {
                    return str;
                }
            } else {
                num = n - 9;
                int tnum = n - 1;
                for (int i = 0; i < num; i++) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(temp);
                    temp = String.valueOf(sb.deleteCharAt(tnum));
                    tnum--;
                }
                return temp;
            }
        }
        if (str.equals("-")) {
            return temp;
        } else {
            dataf = Double.parseDouble(temp);
            if (dataf >= 1000 || dataf <= -1000) {
                NumberFormat nfNum = NumberFormat.getNumberInstance();
                temp = nfNum.format(dataf);
            }
        }
        return temp;
    }

    public String deleteComma(String str) { // ","を計算前に削除する
        char[] ch = str.toCharArray();  //文字列を文字の配列に置き換える
        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < ch.length; i++) {   //カンマがある個所の要素番号をリストへ追加
            if (ch[i] == (',')) {
                list.add(i);    //カンマのある要素数(番号)をリストに入れていく
            }
        }

        int size = list.size(); //listの要素サイズの代入
        for (int i = size; i > 0; i--) {    //要素配列番号の大きいほうから、カンマを削除する
            StringBuffer sb = new StringBuffer();   //StringBufferクラスで、カンマのある個所を削除する
            sb.append(str);
            str = String.valueOf(sb.deleteCharAt(list.get(i - 1)));
        }
        return str; //処理された文字列をreturnする
    }

    public boolean checkDot(String str) {   //num1,num2の文字列内にドット"."があるかをチェック
        char[] ch = str.toCharArray();  //文字列を文字の配列に置き換える

        for (int i = 0; i < ch.length; i++) {   //カンマがある個所の要素番号をリストへ追加
            if (ch[i] == ('.')) {
                return true;
            }
        }
        return false;
    }

    public boolean checkLastString(String str) {    //文字列内の最後に","が入っているかどうかをチェック
        int n = str.length();
        char[] ch = str.toCharArray();
        String tmp = String.valueOf(ch[n-1]);

        if (tmp.equals(".")) {
            return true;
        }
        return false;
    }

        @Override
        public void onClick (View view){    //ボタンをクリックしたとき(共通：すべてのボタンが押されたとき)
//            num1 = "10";
//            sign = "÷";
//            num2 = "100";

            if (view.getId() != R.id.btn_delete) {  // "AC"ボタン以外を押したとき
                Button btn = (Button) view;         //viewにクラスを挿入
                resultStr = String.valueOf(btn.getText());  //画面表示のテキストから文字列を抜き出し、変数に代入
                resultStr = deleteComma(resultStr);         //計算前にカンマを削除
                num1 = deleteComma(num1);   //計算前にカンマを削除
                num2 = deleteComma(num2);   //計算前にカンマを削除
                if (isSign(resultStr)) {    //記号かどうかをチェック
                    if (num1 == "") {
                        if (view.getId() == R.id.btn_minus) {   // "-"を押したとき
                            num1 = resultStr;   //num1に代入
                        } else {
                            resultStr = "0"; //表示させない
                        }
                    } else if (view.getId() == R.id.btn_return) {   // "←"ボタンを押したとき
                        if (sign == "") {
                            num1 = deleteCha(num1);
                            if (num1.equals("0")) {
                                num1 = "";
                                resultStr = "0";
                            } else {
                                resultStr = num1;
                            }
                        } else if (num2 == "") {
                            sign = deleteCha(sign);
                            resultStr = sign;
                        } else if (num2 != "") {
                            num2 = deleteCha(num2);
                            resultStr = num2;
                        }
                    } else if (view.getId() == R.id.btn_change) {   //符号切り替えの処理
                        if (sign == "") {
                            if (num1.equals("-")) {
                                resultStr = "";
                                num1 = "";
                            } else {
                                resultStr = changeCode(num1);
                                num1 = resultStr;
                            }
                        } else if (sign != "" && !num2.equals("-") && num2 != "") {
                            resultStr = changeCode(num2);
                            num2 = resultStr;
                        }
                    } else if(view.getId() == R.id.btn_rate) {  // "%"ボタンを押したとき
                        if (!num2.equals("-") && num2 != "") {
                            resultStr = isNumber(num2);
                            num1 = resultStr;
                            sign = "";
                            num2 = "";
                        } else if (!num1.equals("-")) {
                            resultStr = isNumber(num1);
                            num1 = resultStr;
                            sign = "";
                        }
                    } else if (!num1.equals("-") && num1 != "" && sign == "" && !resultStr.equals("=")) {
                        sign = resultStr;
                    } else if (sign != "") {
                        if  (num2 == "") {
                            if (view.getId() == R.id.btn_plus) {        // "+"の場合
                                sign = resultStr;                        //signを上書き
                            }
                            if (view.getId() == R.id.btn_minus) {       // "-"の場合
                                sign = resultStr;                        //signを上書き
                            }
                            if (view.getId() == R.id.btn_times) {       //  "×"の場合
                                sign = resultStr;                        //signを上書き
                            }
                            if (view.getId() == R.id.btn_div) {         //  "÷"の場合
                                sign = resultStr;                        //signを上書き
                            }
                        }
                    }

                } else {    //数字が入力された場合
                    if (num1 == "") {
                        if (resultStr.equals(".")) {
                            num1 = "0.";
                        } else
                            num1 += resultStr;
                    } else if (view.getId() == R.id.btn_decimal) {  // "."ボタンが押されたとき
                         if (num1.equals("-")) {
                            num1 = "-0.";
                            resultStr = num1;
                        } else if (sign == "" && !checkDot(num1)) {
                            num1 += resultStr;
                        }
                        if (sign != "") {
                            if (num2 == "") {
                                num2 = "0.";
                            } else if (num2.equals("-")) {
                                num2 = "-0.";
                                resultStr = num2;
                            } else if (num2 != "" && !checkDot(num2)) {
                                num2 += resultStr;
                            }
                        }
                    } else if (num1.equals("-")) {
                        num1 += resultStr;
                    }  else if (sign == "") {
                        num1 += resultStr;
                    } else if (sign != "") {
                            num2 += resultStr;
                    }
                }

                boolean judge;  //isError_divメソッドに代入する変数を宣言

                if (num1 != "" && sign != "" && num2 != "" && !num2.equals("-")) {  //num1,num2,signの条件が揃った時に、計算するフローへ移行
                    if (isSign(resultStr)) {    //入力が記号化チェック
                        if (view.getId() != R.id.btn_rate) {    // "%"ボタンでないこと
                           if (view.getId() != R.id.btn_change) {   // "+/-"ボタンでないこと
                               if (view.getId() != R.id.btn_delete) {   // "AC"ボタンでないこと
                                   judge = isError_div(sign,num2);  //ゼロ除算でないかをチェック
                                   if (judge) {     //ゼロ除算となったとき
                                       makeToast();     //トースト表示
                                       num1 = "";
                                       sign = "";
                                       num2 = "";
                                       resultStr = "0";   //表示をすべてリセット
                                   } else if (view.getId() == R.id.btn_equal) {   // "="を押したとき
                                       resultStr = changeFormat(calculator(num1, num2, sign));
                                       sign = "";
                                       num2 = "";
                                       num1 = resultStr;
                                   } else if (view.getId() == R.id.btn_plus) {   // "+"を押したとき
                                           resultStr = changeFormat(calculator(num1, num2, sign));
                                           sign = "+";
                                           num2 = "";
                                           num1 = resultStr;
                                   } else if (view.getId() == R.id.btn_minus) {   // "-"を押したとき
                                           resultStr = changeFormat(calculator(num1, num2, sign));
                                           sign = "-";
                                           num2 = "";
                                           num1 = resultStr;
                                   } else if (view.getId() == R.id.btn_times) {   // "×"を押したとき
                                           resultStr = changeFormat(calculator(num1, num2, sign));
                                           sign = "×";
                                           num2 = "";
                                           num1 = resultStr;
                                   } else if (view.getId() == R.id.btn_div) {   // "÷"を押したとき
                                           resultStr = changeFormat(calculator(num1, num2, sign));
                                           sign = "÷";
                                           num2 = "";
                                           num1 = resultStr;
                                   }
                               }
                           }
                        }
                    }
                }

                //電卓の表示の整理(resultStr,num1,sign,num2それぞれでの表示を更新)
                if (num1 == "" && resultStr.equals("0")){   //num1が空で、resultStr="0"(初期値)の時
                    resultStr = resultStr;
                } else if (sign == "") {            //signに入力がないとき
                    if (checkLastString(num1)) {    //文字列の最後が"，"であるかどうかをチェック
                        resultStr = num1;
                    } else {                        //文字列の最後に","がない場合
                        num1 = checkDigit(num1);
                        resultStr = num1;
                    }
                } else if (num2 == "") {            //num2に入力がないとき
                    resultStr = sign;
                } else if (num2 != "") {            //num2に入力がある場合
                    if (checkLastString(num2)) {        //文字列の最後が"，"であるかどうかをチェック
                        resultStr = num2;
                    } else {                        //文字列の最後に","がない場合
                        num2 = checkDigit(num2);        //桁チェックメソッドを通す
                        resultStr = num2;
                    }
                }

            } else if (view.getId() == R.id.btn_delete) {   // "AC"ボタンを押したとき
                resultStr = "0";    //初期値に戻す
                num1 = "";
                num2 = "";
                sign = "";
            }
            txtResult.setText(resultStr);   //画面表示：計算結果（過程も）
            txtNum1.setText(num1);          //画面表示：左上の数値
            txtSign.setText(sign);          //画面表示：中央の記号
            txtNum2.setText(num2);          //画面表示：右上の数値
        }
    }