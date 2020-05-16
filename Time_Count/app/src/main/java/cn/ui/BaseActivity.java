package cn.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.config.Config;
import cn.time_count.R;

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;
    public Config config;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getApplicationContext();
        config = new Config();
        beforeview();
        setContentView(ContentLayout());
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
//        setStatusBarTransparent(this);
        afterview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideInputMethod();
    }

    protected abstract @LayoutRes
    int ContentLayout();

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(menu()!=0)
            getMenuInflater().inflate(menu(),menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

    protected @MenuRes
    int menu(){
        return 0;
    }

    public void beforeview(){
    }

    public void afterview(){
    }

    //状态栏设置为toolbar的背景颜色
//    public void SetStatusBarColor(Activity activity, int color){
//        Window mWindow = activity.getWindow();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mWindow.setStatusBarColor(color);
//        }
//    }

//    //设置状态栏透明
//    public void setStatusBarTransparent(final Activity pActivity) {
//        pActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        WindowManager.LayoutParams layoutParams=pActivity.getWindow().getAttributes();
//        layoutParams.flags|=WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//    }

    //隐藏输入框
    protected void hideInputMethod() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null & imm!=null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //申请权限
    public void requestPermission(Activity activity,String[] permissions){
        if(!checkPermission(permissions))
            ActivityCompat.requestPermissions(activity,permissions,100);
    }

    //检查权限
//    public boolean checkPermission(String permission) {
//        return checkPermission(new String[]{permission});
//    }

    public boolean checkPermission(String[] permissions) {
        boolean granted = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                granted = checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
                if (!granted) {
                    Toast.makeText(this,"权限不足",Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        return granted;
    }

//    public int getStatusBarHeight(){
//        int statusBarId = getResources().getIdentifier("status_bar_height","dimen","android");
//        return getResources().getDimensionPixelSize(statusBarId);
//    }

}
