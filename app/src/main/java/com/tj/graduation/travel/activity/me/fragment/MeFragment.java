package com.tj.graduation.travel.activity.me.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.login.activity.CollectionActivity;
import com.tj.graduation.travel.activity.login.activity.LoginActivity;
import com.tj.graduation.travel.activity.purchase.activity.PurchaseRecordsActivity;
import com.tj.graduation.travel.base.BaseFragment;
import com.tj.graduation.travel.model.SpotMeModel;
import com.tj.graduation.travel.util.ShareUtil;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.glide.GlideUtil;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * 我的界面
 * Created by wangsong on 2019/3/3.
 */

public class MeFragment extends BaseFragment {

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "";

    private ImageView head_img_tx;
    private TextView user_name;
    private TextView account_tv;
    private LinearLayout ll_collection;
    private LinearLayout ll_purchase;
    private TextView tv_login;
    private LinearLayout ll_login_btn;

    private Drawable head_drawable;
    private String base64 = "";

    public static MeFragment newInstance() {
        MeFragment meFragment = new MeFragment();
        return meFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        head_img_tx = view.findViewById(R.id.h_head);
        user_name = view.findViewById(R.id.user_name);
        account_tv = view.findViewById(R.id.tv_right_text);
        ll_collection = view.findViewById(R.id.ll_root_collection);
        ll_purchase = view.findViewById(R.id.ll_root_purchase);
        tv_login = view.findViewById(R.id.login_btn);
        ll_login_btn = view.findViewById(R.id.ll_login_btn);
//        ShareUtil.put(getActivity(),"loginName","zhangsan");
//        ShareUtil.put(getActivity(),"login","true");
//        ShareUtil.put(getActivity(),"username","张三");
        init();

        head_img_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("false".equals(ShareUtil.get(getActivity(),Constant.login,""))){
                    ToastUtil.showToastText(getActivity(),"您还未登陆...");
                }else {
                    AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity())
                            .setTitle("请选择头像")
                            .setPositiveButton("本地相册", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    ToastUtil.showToastText(getActivity(), "本地相册");
                                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                    galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                                    galleryIntent.setType("image/*");
                                    startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
                                }
                            })
                            .setNegativeButton("照相机", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    ToastUtil.showToastText(getActivity(), "照相机");
                                    if (isSdcardExisting()) {
                                        Intent cameraIntent = new Intent(
                                                "android.media.action.IMAGE_CAPTURE");
                                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
                                        cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                                        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                                    } else {
                                        ToastUtil.showToastText(getActivity(), "请插入sd卡");
                                    }
                                }
                            })
                            .create();
                    alertDialog2.show();
                }
            }
        });
        ll_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("false".equals(ShareUtil.get(getActivity(),Constant.login,""))){
                    ToastUtil.showToastText(getActivity(),"您还未登陆...");
                }else {
                    Intent i = new Intent(getActivity(), CollectionActivity.class);
                    startActivity(i);
                }
            }
        });
        ll_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("false".equals(ShareUtil.get(getActivity(),Constant.login,""))){
                    ToastUtil.showToastText(getActivity(),"您还未登陆...");
                }else{
                    Intent i = new Intent(getActivity(), PurchaseRecordsActivity.class);
                    startActivity(i);
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    resizeImage(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (isSdcardExisting()) {
                        resizeImage(getImageUri());
                    } else {
//                        Toast.makeText(MainActivity.this, "未找到存储卡，无法存储照片！",
//                                Toast.LENGTH_LONG).show();
                    }
                    break;

                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    private void showResizeImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            head_drawable = new BitmapDrawable(photo);
            base64 = bitmapToBase64(photo);
            doQryHeadImg();
        }
    }
    public String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Uri getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }

    private boolean isSdcardExisting() {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private void init() {
        if ("false".equals(ShareUtil.get(getActivity(), Constant.login, "false"))) {
            user_name.setText("您还未登录...");
            account_tv.setText("0");
            ll_login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                }
            });
        } else if ("true".equals(ShareUtil.get(getActivity(), Constant.login, "false"))) {
            doQryMeList();
            user_name.setText(ShareUtil.get(getActivity(), "username", "") + "");
            tv_login.setText("退出登录");
            ll_login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity())
                            .setTitle("是否退出登录？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ShareUtil.put(getActivity(),Constant.login,"false");
                                    ShareUtil.put(getActivity(),Constant.username,"");
                                    ShareUtil.put(getActivity(),Constant.loginName,"");
                                    user_name.setText("您还未登录...");
                                    account_tv.setText("0");
                                    tv_login.setText("点击登录");
                                    ShareUtil.put(getActivity(), Constant.user_id, "");
//                                    ll_login_btn.setBackgroundColor(Color.parseColor("#FF8C00"));
                                    head_img_tx.setImageDrawable(getActivity().getDrawable(R.mipmap.tx_not));
                                    ll_login_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(getActivity(), LoginActivity.class);
                                            startActivity(i);
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Toast.makeText(getActivity(), "这是取消按钮", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create();
                    alertDialog2.show();
                }
            });
        }
    }

    private void doQryMeList() {
        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                SpotMeModel model = (SpotMeModel) responseObj;
                account_tv.setText(model.getData().getAccountFee() + "");
                GlideUtil.LoadPic(getActivity(), model.getData().getHeadPicUrl(), head_img_tx);
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                Log.e("msg", "failure");
            }
        });
    }

    private void doRequest(DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("loginName", (String) ShareUtil.get(getActivity(),Constant.loginName,""));
        RequestUtil.getRequest(Constant.URL_user + "queryUserInfo.api", params, listener, SpotMeModel.class);
        showProgressDialog();
    }

    private void doQryHeadImg() {
        doHeadRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                SpotMeModel model = (SpotMeModel) responseObj;
                account_tv.setText(model.getData().getAccountFee() + "");
                ToastUtil.showToastText(getActivity(), model.getMsg());
                head_img_tx.setImageDrawable(head_drawable);
                doQryMeList();
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                Log.e("msg", "failure");
            }
        });
    }

    private void doHeadRequest(DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("userId", (String) ShareUtil.get(getActivity(),Constant.loginName,""));
        params.put("userHeadPic", base64);
        RequestUtil.getRequest(Constant.URL_user + "uploadHeadPic.api", params, listener, SpotMeModel.class);
        showProgressDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
