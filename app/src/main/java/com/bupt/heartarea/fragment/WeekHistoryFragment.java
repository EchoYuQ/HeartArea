package com.bupt.heartarea.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bupt.heartarea.utils.GlobalData;
import com.bupt.heartarea.R;
import com.bupt.heartarea.bean.HistoryDataItemBean;

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
public class WeekHistoryFragment extends Fragment {

    private ComboLineColumnChartView mDayChart;
    private ComboLineColumnChartData data;

    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 7;

    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasPoints = true;
    private boolean hasLines = true;
    private boolean isCubic = false;
    private boolean hasLabels = false;
    private LineChartData mLineChartData;
    private ColumnChartData mColumnChartData;


    public static String[] days = new String[7];
    List<HistoryDataItemBean> historyList = new ArrayList<>(GlobalData.historyDataItemBeanList);
    private Axis mAxisY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_day, container, false);
        mDayChart = (ComboLineColumnChartView) view.findViewById(R.id.chart_history_day);

        mDayChart.setOnValueTouchListener(new ValueTouchListener());

        generateXText(GlobalData.select_date);
        generateXY();
        generateValues();
        generateData();
        return view;
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

    private void generateData() {
        // Chart looks the best when line data and column data have similar maximum viewports.
        data = new ComboLineColumnChartData(mColumnChartData, mLineChartData);
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        for (int i = 0; i < numberOfPoints; i++) {
            axisValues.add(new AxisValue(i).setLabel(days[i]));

        }
        if (hasAxes) {
            Axis axisX = new Axis(axisValues);
            axisX.setHasLines(true);
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

    /**
     * 生成折线图的数据
     *
     * @return
     */
    private LineChartData generateLineData() {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                if (j % 2 == 0) values.add(new PointValue(j, randomNumbersTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
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
     * 生成柱状图的数据
     *
     * @return
     */
    private ColumnChartData generateColumnData() {

        int numSubcolumns = 1;
        int numColumns = numberOfPoints;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {
            float top = 0;
            float bottom = 0;
            if (i % 2 == 0) {

                top = (float) Math.random() * 50 + 5;
                bottom = top - 15;
            }
            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue(top, bottom, ChartUtils.COLOR_GREEN));

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
//            Toast.makeText(getActivity(), days[columnIndex] + "您的心率最大值为" + value.getValueTop() + ",最小值为" + value.getValueBottom(), Toast.LENGTH_SHORT).show();
            String date = days[columnIndex];
            String[] date_array = date.split("-");
            int month = Integer.valueOf(date_array[0]);
            int day = Integer.valueOf(date_array[1]);
            switch (GlobalData.currenttype) {
                case HEART_RATE:
                    Toast.makeText(getActivity(), "在" + month + "月" + day + "日，您的心率最大值为"
                            + value.getValueTop() + ",最小值为" + value.getValueBottom(), Toast.LENGTH_SHORT).show();
                    break;
                case PRESSURE:
                    Toast.makeText(getActivity(), "在" + month + "月" + day + "日，您的疲劳度最大值为"
                            + value.getValueTop() + ",最小值为" + value.getValueBottom(), Toast.LENGTH_SHORT).show();
                    break;
                case BLOOD_OXYGEN:
                    Toast.makeText(getActivity(), "在" + month + "月" + day + "日，您的血氧最大值为"
                            + value.getValueTop() + ",最小值为" + value.getValueBottom(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {
//            Toast.makeText(getActivity(), days[(int) value.getX()] + "您的心率平均值为" + value.getY(), Toast.LENGTH_SHORT).show();
            String date = days[(int) value.getX()];
            String[] date_array = date.split("-");
            int month = Integer.valueOf(date_array[0]);
            int day = Integer.valueOf(date_array[1]);
            switch (GlobalData.currenttype) {


                case HEART_RATE:
                    Toast.makeText(getActivity(), "在" + month + "月" + day + "日，您的心率平均值为"
                            + value.getY(), Toast.LENGTH_SHORT).show();
                    break;
                case PRESSURE:
                    Toast.makeText(getActivity(), "在" + month + "月" + day + "日，您的疲劳度平均值为"
                            + value.getY(), Toast.LENGTH_SHORT).show();
                    break;
                case BLOOD_OXYGEN:
                    Toast.makeText(getActivity(), "在" + month + "月" + day + "日，您的血氧平均值为"
                            + value.getY(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    /**
     * 生成周历史的横坐标
     * 1,3,5,7,8,10,12 大月31天
     * 4,6,9,11 小月30天
     * 2为平月 闰年为29天 平年为28天
     *
     * @return
     */
    private void generateXText(String date) {
        String[] date_array = date.split("-");
        int year = Integer.valueOf(date_array[0]);
        int month = Integer.valueOf(date_array[1]);
        int day = Integer.valueOf(date_array[2]);
        int lastMonth = (month - 1 == 0) ? 12 : (month - 1);
        if (day - 6 >= 1) {
            for (int i = 0; i < 7; i++) {
                days[i] = month + "-" + (day - 6 + i);
            }
        } else {
            // 上个月为大月 31天
            if (lastMonth == 1 || lastMonth == 3 || lastMonth == 5 || lastMonth == 7 || lastMonth == 8 || lastMonth == 10 || lastMonth == 12) {
                for (int i = 0; i < 7; i++) {
                    days[i] = ((day - 6 + i + 31) / 32 > 0 ? month : lastMonth) + "-" + ((day - 6 + i + 31) % 31 == 0 ? 31 : (day - 6 + i + 31) % 31);
                }
            }
            // 上个月为小月 30天
            else if (lastMonth == 4 || lastMonth == 6 || lastMonth == 9 || lastMonth == 11) {
                for (int i = 0; i < 7; i++) {
                    days[i] = ((day - 6 + i + 30) / 31 > 0 ? month : lastMonth) + "-" + ((day - 6 + i + 30) % 30 == 0 ? 30 : (day - 6 + i + 30) % 30);
                }
            }
            // 上个月为2月
            else {
                // 闰年2月 29天
                if (year % 400 == 0 || year % 100 != 0 && year % 4 == 0) {
                    for (int i = 0; i < 7; i++) {

                        days[i] = ((day - 6 + i + 29) / 30 > 0 ? month : lastMonth) + "-" + ((day - 6 + i + 29) % 29 == 0 ? 29 : (day - 6 + i + 29) % 29);
                    }
                }
                // 非闰年2月 28天
                else {
                    for (int i = 0; i < 7; i++) {

                        days[i] = ((day - 6 + i + 28) / 29 > 0 ? month : lastMonth) + "-" + ((day - 6 + i + 28) % 28 == 0 ? 28 : (day - 6 + i + 28) % 28);
                    }
                }
            }
        }
    }


    /**
     * 将 2017-3-21等时间转化成x坐标
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private int calculateX(int year, int month, int day, int year_now, int month_now, int day_now) {
        if (day_now - day >= 0) return day + 6 - day_now;
        else {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                return day + 6 - (day_now + 31);
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                return day + 6 - (day_now + 30);
            } else {
                // 闰年2月 29天
                if (year % 400 == 0 || year % 100 != 0 && year % 4 == 0) {
                    return day + 6 - (day_now + 29);
                }
                // 非闰年2月 28天
                else {
                    return day + 6 - (day_now + 28);
                }
            }
        }
    }


    /**
     * 将 2017-3-20等时间转化成x坐标
     *
     * @param date
     * @return
     */
    private int calculateX(String date, String date_now) {

        int res = -1;
        String[] date_array = date.split("-");
        String[] date_now_array = date_now.split("-");
        if (date_array.length > 0) {
            res = calculateX(Integer.parseInt(date_array[0]),
                    Integer.parseInt(date_array[1]),
                    Integer.parseInt(date_array[2]),
                    Integer.parseInt(date_now_array[0]),
                    Integer.parseInt(date_now_array[1]),
                    Integer.parseInt(date_now_array[2]));
            Log.i("calculateX res", "date " + date + "" + res);
            return res;
        } else {
            Log.e("calculateX()错误", "日期格式不对，应为2017-02-12");
            return -1;

        }
    }

    /**
     * 将一个时间段区间的Y值list的平均值、最大值、最小值求出
     *
     * @param y_list
     * @return result[0]平均值，result[1]为最大值
     */
    float[] calculateY(List<Float> y_list) {
        float[] result = {0, 0, 0};
        if (y_list.size() == 0 || y_list == null) return result;
        float sum = 0;
        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;
        for (int i = 0; i < y_list.size(); i++) {
            float num = y_list.get(i);
            sum += num;
            if (max < num) max = num;
            if (min > num) min = num;
        }
        result[0] = sum / y_list.size();
        result[1] = max;
        result[2] = min;
        return result;

    }

    /**
     * 将一个时间段区间的Y值数组的平均值、最大值、最小值求出
     *
     * @param y_array
     * @return result[0]平均值，result[1]为最大值
     */
    float[] calculateY(float[] y_array) {
        float[] result = {0, 0, 0};
        float sum = 0;
        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;
        for (int i = 0; i < y_array.length; i++) {
            float num = y_array[i];
            sum += num;
            if (max < num) max = num;
            if (min > num) min = num;
        }
        result[0] = sum / y_array.length;
        result[1] = max;
        result[2] = min;
        return result;

    }

    /**
     * 计算出折线图和柱形图的数据集
     */
    private void generateXY() {
        List<List<Float>> listList = new ArrayList<>();
        for (int i = 0; i < days.length; i++) {
            List<Float> list = new ArrayList<>();
            listList.add(list);
        }

//        mHistoryDataItemList.add(new HistoryDataItemBean("2017-02-01", "03:20:30", 50, 90, 95));
//        mHistoryDataItemList.add(new HistoryDataItemBean("2017-02-01", "03:46:30", 50, 99, 95));
//        mHistoryDataItemList.add(new HistoryDataItemBean("2017-02-03", "05:20:30", 50, 86, 95));
//        mHistoryDataItemList.add(new HistoryDataItemBean("2017-02-03", "08:10:30", 50, 75, 95));
//        mHistoryDataItemList.add(new HistoryDataItemBean("2017-02-03", "08:20:30", 50, 75, 95));
//        mHistoryDataItemList.add(new HistoryDataItemBean("2017-02-04", "08:50:30", 50, 63, 95));
//        mHistoryDataItemList.add(new HistoryDataItemBean("2017-02-05", "09:20:30", 50, 65, 95));
//        mHistoryDataItemList.add(new HistoryDataItemBean("2017-02-05", "12:20:30", 50, 75, 95));
//        mHistoryDataItemList.add(new HistoryDataItemBean("2017-02-06", "12:40:30", 50, 90, 95));
//        mHistoryDataItemList.add(new HistoryDataItemBean("2017-02-07", "12:45:30", 50, 75, 95));
//        mHistoryDataItemList.add(new HistoryDataItemBean("2017-02-07", "13:20:30", 50, 79, 95));
//        mHistoryDataItemList.add(new HistoryDataItemBean("2017-02-07", "14:20:30", 50, 75, 95));
//        mHistoryDataItemList.add(new HistoryDataItemBean("2017-02-07", "15:20:30", 50, 70, 95));


        for (HistoryDataItemBean item : historyList) {
            switch (GlobalData.currenttype) {
                case HEART_RATE:
                    listList.get(calculateX(item.getDate(), GlobalData.select_date)).add((float) item.getHeart_rate());
                    break;
                case BLOOD_OXYGEN:
                    listList.get(calculateX(item.getDate(), GlobalData.select_date)).add((float) item.getBlood_oxygen());
                    break;
                case PRESSURE:
                    listList.get(calculateX(item.getDate(), GlobalData.select_date)).add((float) item.getPressure());
                    break;
            }


        }


        List<Line> lines = new ArrayList<Line>();
        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values1 = new ArrayList<PointValue>();
            List<SubcolumnValue> values2;

            for (int j = 0; j < numberOfPoints; ++j) {
                // 平均值放入折线图数据集中
                float[] resY = calculateY(listList.get(j));
                if (listList.get(j).size() > 0) {

                    values1.add(new PointValue(j, resY[0]));
                }

                values2 = new ArrayList<SubcolumnValue>();
//                Log.e("resY", resY[0] + " " + resY[1] + " " + resY[2]);
                values2.add(new SubcolumnValue(resY[1], resY[2], getResources().getColor(R.color.column_chart_green)));
                columns.add(new Column(values2));
            }

            Line line = new Line(values1);
            line.setColor(getResources().getColor(R.color.linear_chart_green));
            line.setCubic(isCubic);
            line.setHasLabels(hasLabels);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            lines.add(line);
        }

        mLineChartData = new LineChartData(lines);
        mColumnChartData = new ColumnChartData(columns);


    }
}
