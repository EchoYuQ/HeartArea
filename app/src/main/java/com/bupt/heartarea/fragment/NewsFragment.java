package com.bupt.heartarea.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.bupt.heartarea.R;
import com.bupt.heartarea.activity.WebActivity;
import com.bupt.heartarea.adapter.NewsRecyclerViewAdapter;
import com.bupt.heartarea.bean.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jiangzehui on 16/11/5.
 */
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View view;
    RecyclerView rv;
    NewsRecyclerViewAdapter adapter;
    SwipeRefreshLayout fresh;
    View footView;
    TextView tv;
    ProgressBar pb;
    private LinearLayoutManager mLinearLayoutManager;
    private Context mContext;
    private static final String URL_LIST = "http://www.tngou.net/api/lore/list";
    private static final String URL_SHOW = "http://www.tngou.net/api/lore/show";

    //    List<HttpService.Result.TngouBean> dataBeanList = new ArrayList<>();
    private List<Result.DataBean> dataBeanList1 = new ArrayList<>();
    private RequestQueue mQueue;
//    List<HttpService.Result1.TngouBean.DataBean> dataBeanList1=new ArrayList<>();

    public static NewsFragment newInstance(String type) {
        NewsFragment newsFragment = new NewsFragment();
        Bundle bd = new Bundle();
        bd.putString("type", type);
        newsFragment.setArguments(bd);
        return newsFragment;
    }

    String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_news, container, false);
        }
        rv = (RecyclerView) view.findViewById(R.id.rv);
        fresh = (SwipeRefreshLayout) view.findViewById(R.id.fresh);
//        ButterKnife.inject(this, view);
        fresh.setColorSchemeResources(R.color.main, android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light);
        fresh.setOnRefreshListener(this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        Bundle bundle = getArguments();
//        type = bundle.getString("type");
//        getData(type);
        getData();
        Log.i(type, "onCreateView");


        return view;
    }


    private void getData() {
        mQueue = Volley.newRequestQueue(mContext);

        StringRequest stringRequest = new StringRequest(URL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG 请求成功", response);
                updateUi(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("TAG 请求失败", volleyError.getMessage());
            }
        }


        );
        mQueue.add(stringRequest);
    }


    /**
     * 更新数据
     * @param response
     */
    private void updateUi(String response) {
        if (fresh != null) {
            fresh.setRefreshing(false);
        }
        Gson gson = new Gson();
        Result result = gson.fromJson(response, Result.class);
//        if (result.getError_code() == 0) {

//            Log.e("HttpService.Result",result.getResult().toString());
        dataBeanList1 = result.getTngou();
        for (int i = 0; i < dataBeanList1.size(); i++) {

            System.out.println(dataBeanList1.get(i).toString());
        }
        if (adapter == null) {
            footView = LayoutInflater.from(getActivity()).inflate(R.layout.item_footview, null);
            footView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv = (TextView) footView.findViewById(R.id.tv);
            pb = (ProgressBar) footView.findViewById(R.id.pb);


            adapter = new NewsRecyclerViewAdapter(getActivity(), dataBeanList1, footView);
            adapter.setOnItemClickLitener(new NewsRecyclerViewAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    int id = dataBeanList1.get(position).getId();
                    String url = URL_SHOW + "?id=" + id;
                    StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(response);
                                if (jsonObject.has("url")) {
                                    String web_url = jsonObject.getString("url");
                                    Intent intent = new Intent(getActivity(), WebActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("url", web_url);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });
                    mQueue.add(stringRequest);


                }
            });
            if (rv != null) {
                rv.setLayoutManager(mLinearLayoutManager);
                rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    private boolean isScroll = false;

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE && isScroll) {
                            int lastVisibleItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                            int totalItemCount = mLinearLayoutManager.getItemCount();
                            Log.i("NewsFragment", totalItemCount + "");
                            Log.i("NewsFragment", lastVisibleItem + "");
                            if (lastVisibleItem == (totalItemCount - 1)) {
                                Log.i("Gif_Fragment", "LoadMore");
                                LoadMore();
                                isScroll = false;
                            }
                        }

                    }


                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dy > 0) {
                            isScroll = true;
                        } else {
                            isScroll = false;
                        }
                    }
                });
                rv.setAdapter(adapter);
            }

        } else {
            adapter.setList(dataBeanList1);
        }


    }

    /**
     * 加载更多
     */
    private void LoadMore() {
        tv.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
        Log.i(type, "LoadMore");

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(type, "onDestroyView");
    }


    @Override
    public void onRefresh() {
        getData();
    }
}
