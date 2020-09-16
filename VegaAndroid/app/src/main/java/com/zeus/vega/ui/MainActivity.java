package com.zeus.vega.ui;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zeus.vega.R;
import com.zeus.vega.Vega;
import com.zeus.vega.model.User;
import com.zeus.vega.net.VegaHttpClient;
import com.zeus.vega.net.exception.ResponseThrowable;
import com.zeus.vega.net.service.RequestService;
import com.zeus.vega.net.subcriber.RequestSubscriber;
import com.zeus.vega.rx.RxRequestUtil;
import com.zeus.vega.util.EncryptUtils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testApi();
            }
        });
    }

    private void testApi(){
       // String URL = Vega.URL_DOMAIN+"ApiBus/getURLCommon.action";
        String URL = "charmword/loginUser.action";
        String pass = new EncryptUtils().getMD5Str("minggo" + "imei" + "charmword");
        ArrayMap<String,Object> params = new ArrayMap<>();
        params.put("password",123456);
        params.put("email","minggo8en@gmail.com");
        params.put("imei","imei");
        params.put("pass",pass);

        RxRequestUtil.call(VegaHttpClient.getInstance().create(RequestService.class).test(URL,params), new RequestSubscriber<User>(MainActivity.this) {

            @Override
            protected void onSuccess(User user) {
                if (user!=null){
                    Toast.makeText(MainActivity.this,user.username,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"返回数据为空",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected void onFailed(ResponseThrowable e) {
                Toast.makeText(MainActivity.this, e.message,Toast.LENGTH_LONG).show();
            }
        });
    }
}