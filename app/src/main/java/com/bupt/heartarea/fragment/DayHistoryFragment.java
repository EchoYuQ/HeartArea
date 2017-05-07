package com.bupt.heartarea.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bupt.heartarea.bean.HistoryDataItemBean;
import com.bupt.heartarea.utils.GlobalData;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;

/**
 * Created by yuqing on 2017/3/16.
 */
public class DayHistoryFragment extends Fragment {

    private ComboLineColumnChartView mDayChart;
    private ComboLineColumnChartData data;

    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 24 * 60;


    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasPoints = true;
    private boolean hasLines = true;
    private boolean isCubic = false;
    private boolean hasLabels = false;

    private String[] minutes = new String[24 * 60];
    private LineChartData mLineChartData;
    private ColumnChartData mColumnChartData;
    List<HistoryDataItemBean> mHistoryDataItemList = new ArrayList<>(GlobalData.historyDataItemBeanList);
    Axis mAxisY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(com.bupt.heartarea.R.layout.fragment_history_year, container, false);
        mDayChart = (ComboLineColumnChartView) view.findViewById(com.bupt.heartarea.R.id.chart_history_year);

        mDayChart.setOnValueTouchListener(new ValueTouchListener());

        generateXText();
        generateXY();
        generateValues();
        generateData();
        return view;
    }

    @Override
    public void onDestroyView() {
        mHistoryDataItemList.clear();
        super.onDestroyView();
    }

    /**
     * 将 2:35  20:40等时间转化成x坐标
     *
     * @param hour
     * @param min
     * @return
     */
    private int calculateX(int hour, int min) {

        return hour * 60 + min;
    }

    /**
     * 将 2:35  20:40等时间转化成x坐标
     *
     * @param time
     * @return
     */
    private int calculateX(String time) {
        String[] time_array = time.split(":");
        if (time_array.length > 0) {
            return calculateX(Integer.parseInt(time_array[0]), Integer.parseInt(time_array[1]));
        } else {
            Log.e("calculateX()错误", "时间格式不对，应为10:10:10");
            return 0;

        }


    }

    private void generateXText() {

        for (int i = 0; i < 24 * 60; i++) {
            String hour = String.valueOf(i / 60);
            String minute = i % 60 < 10 ? "0" + String.valueOf(i % 60) : String.valueOf(i % 60);
            minutes[i] = hour + ":" + minute;
        }
    }

    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 50f + 5;
            }
        }
    }

    private void reset() {
        numberOfLines = 1;

        hasAxes = true;
        hasAxesNames = true;
        hasLines = true;
        hasPoints = true;
        hasLabels = false;
        isCubic = false;

    }

    /**
     * 计算出折线图和柱形图的数据集
     */
    private void generateXY() {
//        mHistoryDataItemList.add(new HistoryDataItemBean("2015-10-08", "14:20:30", 50, 75, 95));
//        mHistoryDataItemList.add(new HistoryDataItemBean("2015-10-08", "15:20:30", 50, 75, 95));


//        listList.get(calculateX("16:20")).add(75f);
//        listList.get(calculateX("16:38")).add(76f);

//        for (int i=0;i<hours.length;i++)
//        Log.e("listList",listList.get(i).size()+"");


        List<Line> lines = new ArrayList<Line>();
        List<Column> columns = new ArrayList<Column>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values1 = new ArrayList<>();
//            List<SubcolumnValue> values2;

            for (HistoryDataItemBean item : mHistoryDataItemList) {
                System.out.println(item.toString() + "-----");
            }

            for (int j = 0; j < mHistoryDataItemList.size(); ++j) {
                // 平均值放入折线图数据集中
                String time = mHistoryDataItemList.get(j).getTime();
                float resY = 0.0f;
                switch (GlobalData.currenttype) {
                    case HEART_RATE:
                        resY = (float) mHistoryDataItemList.get(j).getHeart_rate();
                        break;
                    case BLOOD_OXYGEN:
                        resY = (float) mHistoryDataItemList.get(j).getBlood_oxygen();
                        break;
                    case PRESSURE:
                        resY = (float) mHistoryDataItemList.get(j).getPressure();
                        break;
                }
//                Log.i("resY", resY + "");
                values1.add(new PointValue(calculateX(time), resY));


            }


            Line line = new Line(values1);
            line.setColor(getResources().getColor(com.bupt.heartarea.R.color.linear_chart_green));
            line.setCubic(isCubic);
            line.setHasLabels(hasLabels);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            lines.add(line);
        }

        mLineChartData = new LineChartData(lines);
        mColumnChartData = new ColumnChartData(columns);


    }

    private void generateData() {

        data = new ComboLineColumnChartData(mColumnChartData, mLineChartData);
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        for (int i = 0; i < numberOfPoints; i++) {
            axisValues.add(new AxisValue(i).setLabel(minutes[i]));

        }
        if (hasAxes) {
            Axis axisX = new Axis(axisValues);
            mAxisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                // 坐标轴标签
                axisX.setName("时间");
                switch (GlobalData.currenttype) {
                    case HEART_RATE:
                        mAxisY.setName("心率/bps");
                        break;
                    case BLOOD_OXYGEN:
                        mAxisY.setName("血氧/%");
                        break;
                    case PRESSURE:
                        mAxisY.setName("压力值");
                        break;
                }

            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(mAxisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        mDayChart.setComboLineColumnChartData(data);
    }

    private LineChartData generateLineData() {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(getResources().getColor(com.bupt.heartarea.R.color.linear_chart_green));
            line.setCubic(isCubic);
            line.setHasLabels(hasLabels);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            lines.add(line);
        }

        LineChartData lineChartData = new LineChartData(lines);

        return lineChartData;

    }

    /**
     * @return
     */
    private ColumnChartData generateColumnData() {
        int numSubcolumns = 1;
        int numColumns = numberOfPoints;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                float top = (float) Math.random() * 50 + 5;
                float bottom = top - 15;
                values.add(new SubcolumnValue(top, bottom, ChartUtils.COLOR_GREEN));
            }

            columns.add(new Column(values));
        }

        ColumnChartData columnChartData = new ColumnChartData(columns);
        return columnChartData;
    }

    private void addLineToData() {
        if (data.getLineChartData().getLines().size() >= maxNumberOfLines) {
            Toast.makeText(getActivity(), "Samples app uses max 4 lines!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ++numberOfLines;
        }

        generateData();
    }

    private void toggleLines() {
        hasLines = !hasLines;

        generateData();
    }

    private void togglePoints() {
        hasPoints = !hasPoints;

        generateData();
    }

    private void toggleCubic() {
        isCubic = !isCubic;

        generateData();
    }

    private void toggleLabels() {
        hasLabels = !hasLabels;

        generateData();
    }

    private void toggleAxes() {
        hasAxes = !hasAxes;

        generateData();
    }

    private void toggleAxesNames() {
        hasAxesNames = !hasAxesNames;

        generateData();
    }

    private void prepareDataAnimation() {

        // Line animations
        for (Line line : data.getLineChartData().getLines()) {
            for (PointValue value : line.getValues()) {
                // Here I modify target only for Y values but it is OK to modify X targets as well.
                value.setTarget(value.getX(), (float) Math.random() * 50 + 5);
            }
        }

        // Columns animations
        for (Column column : data.getColumnChartData().getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setTarget((float) Math.random() * 50 + 5);
            }
        }
    }

    private class ValueTouchListener implements ComboLineColumnChartOnValueSelectListener {

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            Toast.makeText(getActivity(), "Selected column: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {
            switch (GlobalData.currenttype) {
                case HEART_RATE:
                    Toast.makeText(getActivity(), "测量时间：" + mHistoryDataItemList.get(pointIndex).getTime() +
                            " 心率为：" + mHistoryDataItemList.get(pointIndex).getHeart_rate(), Toast.LENGTH_SHORT).show();
                    break;
                case PRESSURE:
                    Toast.makeText(getActivity(), "测量时间：" + mHistoryDataItemList.get(pointIndex).getTime() +
                            " 疲劳度为：" + mHistoryDataItemList.get(pointIndex).getPressure() , Toast.LENGTH_SHORT).show();
                    break;
                case BLOOD_OXYGEN:
                    Toast.makeText(getActivity(), "测量时间：" + mHistoryDataItemList.get(pointIndex).getTime() +
                            " 血氧为：" + mHistoryDataItemList.get(pointIndex).getBlood_oxygen() , Toast.LENGTH_SHORT).show();
                    break;


            }
        }

    }
}
