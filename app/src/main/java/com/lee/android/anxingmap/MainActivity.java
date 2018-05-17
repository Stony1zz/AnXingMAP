package com.lee.android.anxingmap;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public MapView mMapView=null;
    private AMap aMap;
    public MyLocationStyle mMyLocationStyle;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private Button basicmap;
    private Button nightmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取地图控件引用
        mMapView=(MapView)findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.showMapText(true);
        }
        mUiSettings = aMap.getUiSettings();
        aMap.showMapText(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true); // 是否显示默认的定位按钮
        mMyLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        mMyLocationStyle.interval(10000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(mMyLocationStyle);//设置定位蓝点的Style
        aMap.showMapText(true);
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
       // mMyLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        init();

    }

    private void init() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            mUiSettings = aMap.getUiSettings();
            aMap.showMapText(true);
        }
        //setMapCustomStyleFile(this);

        basicmap = (Button)findViewById(R.id.basicmap);
        basicmap.setOnClickListener(this);
        nightmap = (Button)findViewById(R.id.nightmap);
        nightmap.setOnClickListener(this);

    }
    private void setMapCustomStyleFile(Context context) {
        String styleName = "style_json.json";
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String filePath = null;
        try {
            inputStream = context.getAssets().open(styleName);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            filePath = context.getFilesDir().getAbsolutePath();
            File file = new File(filePath + "/" + styleName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            outputStream.write(b);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();

                if (outputStream != null)
                    outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        aMap.setCustomMapStylePath(filePath + "/" + styleName);

        aMap.showMapText(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.basicmap:
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
                aMap.showMapText(true);
                break;
            case R.id.nightmap:
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);//夜景地图模式
                aMap.showMapText(true);
                break;
        }
    }

}

