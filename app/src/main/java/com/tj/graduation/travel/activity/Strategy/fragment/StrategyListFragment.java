package com.tj.graduation.travel.activity.Strategy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.Strategy.activity.SpotGuideDetailActivity;
import com.tj.graduation.travel.activity.Strategy.adapter.SpotGuideAdapter;
import com.tj.graduation.travel.base.BaseFragment;
import com.tj.graduation.travel.model.GuideListModel;
import com.tj.graduation.travel.model.GuideModel;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;
import com.tj.graduation.travel.util.pulltorefresh.ILoadingLayout;
import com.tj.graduation.travel.util.pulltorefresh.PullToRefreshBase;
import com.tj.graduation.travel.util.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 攻略列表界面
 * Created by wangsong on 2019/3/3.
 */

public class StrategyListFragment extends BaseFragment {


    private PullToRefreshListView glLv;
    private TextView nodataTv;
    private int requestcount = 10;
    private int index = 1;
    private String type;
    private SpotGuideAdapter spotGuideAdapter;

    private List<GuideModel> list = new ArrayList<>();

    public static StrategyListFragment newInstance() {
        StrategyListFragment strategyListFragment = new StrategyListFragment();
        return strategyListFragment;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.strategy_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        doGuidelistQuery();
    }

    public void doGuidelistQuery() {
        RequestParams params = new RequestParams();
        params.put("curpagenum", index + "");
        params.put("pagecount", requestcount + "");
        params.put("type", "all");
        RequestUtil.getRequest(Constant.URL + "guidelistQuery.api", params, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                glLv.onRefreshComplete();
                GuideListModel model = (GuideListModel) responseObj;
                setCommentData(model);
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                glLv.onRefreshComplete();
            }
        }, GuideListModel.class);
//        showProgressDialog();
    }


    private void setCommentData(GuideListModel model) {
        List<GuideModel> guideList = model.getData().getGuidelist();
        if ("refresh".equals(type)) {
            list.clear();
            spotGuideAdapter.clear();
        }
        list.addAll(guideList);
        if (list.size() > 0) {
            glLv.setVisibility(View.VISIBLE);
            nodataTv.setVisibility(View.GONE);
            spotGuideAdapter.setList(list);
        } else {
            glLv.setVisibility(View.GONE);
            nodataTv.setVisibility(View.VISIBLE);
        }

    }

    private void initView(View view) {

        nodataTv = view.findViewById(R.id.tv_gl_nodata);
        glLv = view.findViewById(R.id.lv_gl);
        glLv.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabelse = glLv.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("加载中");// 刷新时
        startLabelse.setReleaseLabel("释放开始加载");// 下来达到一定距离时，显示的提示


        ILoadingLayout endLabelsr = glLv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("加载中");// 刷新时
        endLabelsr.setReleaseLabel("释放开始加载");// 下来达到一定距离时，显示的提示


        glLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                type = "refresh";

                index = 1;
                doGuidelistQuery();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                type = "load";
                ++index;
                doGuidelistQuery();
            }
        });

        spotGuideAdapter = new SpotGuideAdapter(getActivity(), new ArrayList<GuideModel>());
        glLv.setAdapter(spotGuideAdapter);
        glLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SpotGuideDetailActivity.startSpotGuideDetailActivity(getActivity(), list.get(i - 1).getId());
            }
        });
    }
}
