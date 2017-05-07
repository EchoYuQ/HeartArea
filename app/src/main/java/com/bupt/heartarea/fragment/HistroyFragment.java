package com.bupt.heartarea.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bupt.heartarea.activity.HistoryActivity;
import com.bupt.heartarea.utils.GlobalData;

import java.util.Calendar;
import java.util.TimeZone;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * Demo应用的主Activity
 * The CalBloodOxygen activity of demo
 *
 * @author AigeStudio 2015-03-26
 */
public class HistroyFragment extends Fragment {
    DatePicker picker;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(com.bupt.heartarea.R.layout.fragment_three,container,false);
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码

        picker = (DatePicker) view.findViewById(com.bupt.heartarea.R.id.main_dp);
        picker.setDate(mYear, mMonth);
        picker.setMode(DPMode.SINGLE);
        picker.setFestivalDisplay(false);
        picker.setHolidayDisplay(false);
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Context context=getActivity().getApplicationContext();
//                Toast.makeText(context, date, Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context, HistoryActivity.class);
                Bundle bundle=new Bundle();
                String[] dates=date.split("-");
                if(Integer.parseInt(dates[1])<10) dates[1]="0"+dates[1];
                if(Integer.parseInt(dates[2])<10) dates[2]="0"+dates[2];
                String new_date=dates[0]+"-"+dates[1]+"-"+dates[2];
                bundle.putString("date",new_date);
                GlobalData.select_date=new_date;
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
        // 默认多选模式
//        DatePicker picker = (DatePicker) findViewById(R.id.main_dp);
//        picker.setDate(2015, 7);
//        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(List<String> date) {
//                String result = "";
//                Iterator iterator = date.iterator();
//                while (iterator.hasNext()) {
//                    result += iterator.next();
//                    if (iterator.hasNext()) {
//                        result += "\n";
//                    }
//                }
//                Toast.makeText(DatePickActivity.this, result, Toast.LENGTH_LONG).show();
//            }
//        });

        // 自定义背景绘制示例 Example of custom date's background
//        List<String> tmp = new ArrayList<>();
//        tmp.add("2015-7-1");
//        tmp.add("2015-7-8");
//        tmp.add("2015-7-16");
//        DPCManager.getInstance().setDecorBG(tmp);
//
//        DatePicker picker = (DatePicker) findViewById(R.id.main_dp);
//        picker.setDate(2015, 7);
//        picker.setDPDecor(new DPDecor() {
//            @Override
//            public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
//                paint.setColor(Color.RED);
//                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2F, paint);
//            }
//        });
//        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(List<String> date) {
//                String result = "";
//                Iterator iterator = date.iterator();
//                while (iterator.hasNext()) {
//                    result += iterator.next();
//                    if (iterator.hasNext()) {
//                        result += "\n";
//                    }
//                }
//                Toast.makeText(DatePickActivity.this, result, Toast.LENGTH_LONG).show();
//            }
//        });

        // 自定义前景装饰物绘制示例 Example of custom date's foreground decor
//        List<String> tmpTL = new ArrayList<>();
//        tmpTL.add("2015-10-5");
//        tmpTL.add("2015-10-6");
//        tmpTL.add("2015-10-7");
//        tmpTL.add("2015-10-8");
//        tmpTL.add("2015-10-9");
//        tmpTL.add("2015-10-10");
//        tmpTL.add("2015-10-11");
//        DPCManager.getInstance().setDecorTL(tmpTL);
//
//        List<String> tmpTR = new ArrayList<>();
//        tmpTR.add("2015-10-10");
//        tmpTR.add("2015-10-11");
//        tmpTR.add("2015-10-12");
//        tmpTR.add("2015-10-13");
//        tmpTR.add("2015-10-14");
//        tmpTR.add("2015-10-15");
//        tmpTR.add("2015-10-16");
//        DPCManager.getInstance().setDecorTR(tmpTR);
//
//        DatePicker picker = (DatePicker) findViewById(R.id.main_dp);
//        picker.setDate(2015, 10);
//        picker.setFestivalDisplay(false);
//        picker.setTodayDisplay(false);
//        picker.setHolidayDisplay(false);
//        picker.setDeferredDisplay(false);
//        picker.setMode(DPMode.NONE);
//        picker.setDPDecor(new DPDecor() {
//            @Override
//            public void drawDecorTL(Canvas canvas, Rect rect, Paint paint, String data) {
//                super.drawDecorTL(canvas, rect, paint, data);
//                switch (data) {
//                    case "2015-10-5":
//                    case "2015-10-7":
//                    case "2015-10-9":
//                    case "2015-10-11":
//                        paint.setColor(Color.GREEN);
//                        canvas.drawRect(rect, paint);
//                        break;
//                    default:
//                        paint.setColor(Color.RED);
//                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
//                        break;
//                }
//            }
//
//            @Override
//            public void drawDecorTR(Canvas canvas, Rect rect, Paint paint, String data) {
//                super.drawDecorTR(canvas, rect, paint, data);
//                switch (data) {
//                    case "2015-10-10":
//                    case "2015-10-11":
//                    case "2015-10-12":
//                        paint.setColor(Color.BLUE);
//                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
//                        break;
//                    default:
//                        paint.setColor(Color.YELLOW);
//                        canvas.drawRect(rect, paint);
//                        break;
//                }
//            }
//        });
//        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(List<String> date) {
//                String result = "";
//                Iterator iterator = date.iterator();
//                while (iterator.hasNext()) {
//                    result += iterator.next();
//                    if (iterator.hasNext()) {
//                        result += "\n";
//                    }
//                }
//                Toast.makeText(DatePickActivity.this, result, Toast.LENGTH_LONG).show();
//            }
//        });

        // 对话框下的DatePicker示例 Example in dialog
//        Button btnPick = (Button) findViewById(R.id.main_btn);
//        btnPick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog dialog = new AlertDialog.Builder(DatePickActivity.this).create();
//                dialog.show();
//                DatePicker picker = new DatePicker(DatePickActivity.this);
//                picker.setDate(2015, 10);
//                picker.setMode(DPMode.SINGLE);
//                picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
//                    @Override
//                    public void onDatePicked(String date) {
//                        Toast.makeText(DatePickActivity.this, date, Toast.LENGTH_LONG).show();
//                        dialog.dismiss();
//                    }
//                });
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.getWindow().setContentView(picker, params);
//                dialog.getWindow().setGravity(Gravity.CENTER);
//            }
//        });
    }


}
