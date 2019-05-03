package com.tj.graduation.travel.activity.me.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.login.activity.CollectionActivity;
import com.tj.graduation.travel.activity.login.activity.LoginActivity;
import com.tj.graduation.travel.activity.purchase.activity.PurchaseRecordsActivity;
import com.tj.graduation.travel.base.BaseFragment;
import com.tj.graduation.travel.dialog.SelectImageDialog;
import com.tj.graduation.travel.model.SpotMeModel;
import com.tj.graduation.travel.util.PhotoUtils;
import com.tj.graduation.travel.util.ShareUtil;
import com.tj.graduation.travel.util.StringUtils;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.Utils;
import com.tj.graduation.travel.util.glide.GlideUtil;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * 我的界面
 * Created by wangsong on 2019/3/3.
 */

public class MeFragment extends BaseFragment {

    private static final int IMAGE_SELECT_CODE = 1;
    private static final int IMAGE_CAMERA = 2;
    private static final int IMAGE_CROP = 3;
    private static final int PERMISION_READ = 4;
    private static final int PERMISION_CAMERA = 5;
    //    private Uri cameraUri;
    private Uri imageUri;


    private ImageView head_img_tx;
    private TextView user_name;
    private TextView account_tv;
    private LinearLayout ll_collection;
    private LinearLayout ll_purchase;
    private TextView tv_login;
    private LinearLayout ll_login_btn;

    private String base64 = "";
    private onLoginStatusListener listener;

    public void setOnLoginStatusListener(onLoginStatusListener listener) {
        this.listener = listener;
    }

    //监听登陆状态，去重制订单列表
    public interface onLoginStatusListener {

        void onLogout();
    }

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
        init();

        head_img_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isEmpty((String) ShareUtil.get(getActivity(), Constant.user_id, ""))) {
                    final SelectImageDialog selectImageDialog = new SelectImageDialog(getActivity());
                    selectImageDialog.setCanceledOnTouchOutside(false);
                    selectImageDialog.show();
                    Window window = selectImageDialog.getWindow();
                    window.setGravity(Gravity.BOTTOM);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.width = Utils.getScreenWidth(getActivity());
                    window.setAttributes(params);
                    selectImageDialog.setOnImageSelectListener(new SelectImageDialog.onImageSelectListener() {
                        @Override
                        public void onImageSelect() {

                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent, IMAGE_SELECT_CODE);
//                            PhotoUtils.openPic(getActivity(), IMAGE_SELECT_CODE);
                        }

                        @Override
                        public void onImageCamera() {
                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) !=
                                    PackageManager.PERMISSION_GRANTED) {

                                requestPermissions(new String[]{Manifest.permission
                                        .CAMERA}, PERMISION_CAMERA);


                            } else {
                                takePhoto();
                            }

                        }
                    });

                } else {
                    ToastUtil.showToastText(getActivity(), getResources().getString(R.string.no_login));
                }
            }
        });

        ll_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("false".equals(ShareUtil.get(getActivity(), Constant.login, ""))) {
                    ToastUtil.showToastText(getActivity(), "您还未登陆...");
                } else {
                    Intent i = new Intent(getActivity(), CollectionActivity.class);
                    startActivity(i);
                }
            }
        });
        ll_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("false".equals(ShareUtil.get(getActivity(), Constant.login, ""))) {
                    ToastUtil.showToastText(getActivity(), "您还未登陆...");
                } else {
                    Intent i = new Intent(getActivity(), PurchaseRecordsActivity.class);
                    startActivity(i);
                }
            }
        });
        return view;
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        File fileUri = new File(Environment.getExternalStorageDirectory() + "/" +
                SystemClock.currentThreadTimeMillis() + ".jpg");
        imageUri = Uri.fromFile(fileUri);
        //调用系统相机
        Intent intentCamera = new Intent();
        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //将拍照结果保存至photo_file的Uri中，不保留在相册中
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", fileUri)); //Uri.fromFile(tempFile)
        startActivityForResult(intentCamera, IMAGE_CAMERA);

//        PhotoUtils.takePicture(getActivity(), imageUri, IMAGE_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_SELECT_CODE && resultCode == RESULT_OK && data != null) {

            Uri oriUri = data.getData();
            File desFile = new File(Environment.getExternalStorageDirectory() + "/" +
                    SystemClock.currentThreadTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(desFile);
            cropImage(oriUri, imageUri);

        } else if (requestCode == IMAGE_CROP && resultCode == RESULT_OK && data != null) {

//            imageUri = data.getData();

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission
                    .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISION_READ);
            } else {

                try {
                    Bitmap bitmap = PhotoUtils.getBitmapFormUri(getActivity(), imageUri);
                    base64 = PhotoUtils.bitmapToBase64(bitmap);
                    doQryHeadImg();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } else if (requestCode == IMAGE_CAMERA) {
            cropImage(imageUri, imageUri);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void cropImage(Uri orgUri, Uri desUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(orgUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 50);
        intent.putExtra("aspectY", 50);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, IMAGE_CROP);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISION_READ) {

            if (grantResults[0] == 0) {
                try {
                    Bitmap bitmap = PhotoUtils.getBitmapFormUri(getActivity(), imageUri);
                    base64 = PhotoUtils.bitmapToBase64(bitmap);
                    doQryHeadImg();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == PERMISION_CAMERA) {
            if (grantResults[0] == 0) {
                takePhoto();
            }
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
                                    ShareUtil.put(getActivity(), Constant.login, "false");
                                    ShareUtil.put(getActivity(), Constant.username, "");
                                    ShareUtil.put(getActivity(), Constant.loginName, "");
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
                                    listener.onLogout();
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

    public void doQryMeList() {
        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                SpotMeModel model = (SpotMeModel) responseObj;
                account_tv.setText(model.getData().getAccountFee() + "");
                GlideUtil.LoadPicWithoutCache(getActivity(), model.getData().getHeadPicUrl(), head_img_tx);
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
        params.put("loginName", (String) ShareUtil.get(getActivity(), Constant.loginName, ""));
        RequestUtil.getRequest(Constant.URL_user + "queryUserInfo.api", params, listener, SpotMeModel.class);
        showProgressDialog();
    }

    private void doQryHeadImg() {
        doHeadRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                ToastUtil.showToastText(getActivity(), "上传成功");
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
        params.put("userId", (String) ShareUtil.get(getActivity(), Constant.loginName, ""));
        params.put("userHeadPic", base64);
        RequestUtil.getRequest(Constant.URL_user + "uploadHeadPic.api", params, listener, null);
        showProgressDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
