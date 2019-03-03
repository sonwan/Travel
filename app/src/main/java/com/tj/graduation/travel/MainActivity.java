package com.tj.graduation.travel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tj.graduation.travel.model.SpotListModel;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btn = findViewById(R.id.btn);
        tv = findViewById(R.id.tv);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRequest(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {

                        SpotListModel model = (SpotListModel) responseObj;
                        tv.setText(model.getData().getList().get(0).getDescInfo());

                    }

                    @Override
                    public void onFailure(Object responseObj) {

                    }
                });
            }
        });

    }

    private void doRequest(DisposeDataListener listener) {
        RequestParams params = new RequestParams();

        params.put("curpagenum", "1");
        params.put("pagecount", "10");

        RequestUtil.getRequest(Constant.URL + "querySpotList.api", params, listener, SpotListModel.class);
    }


}
