package com.bupt.heartarea.bloodpressure;


import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class BloodPressure {

//    private double[] mData;

    public BloodPressure(double[] data) {
//        mData=data;
        calculate(data);
    }

    public static void calculate(final double[] data) {
        //  System.out.println ("here");
        List<Point> points = new ArrayList<Point>();
        //ArrayList<Integer> samples = new ArrayList<Integer>();
        //data是未处理的原始数据
//        double[] data = {4.365267164650911, 4.353899892285188, 4.355855076729777, 4.360882050301021, 4.37099447181071, 4.371171987813795, 4.3697659557154305, 4.371301115892825, 4.377576842134224, 4.381928480393524, 4.390071543032459, 4.397343837917595, 4.397846427966451, 4.362091334040999, 4.3489834160336045, 4.34733239609276, 4.35223207823983, 4.360579248780065, 4.36098078105679, 4.359543185887361, 4.360462839771086, 4.3650233821077435, 4.368110912779764, 4.374720177862649, 4.3778834557526976, 4.3758850628242065, 4.353082833266345, 4.324951251310605, 4.326709460039738, 4.350924823042583, 4.356741201197907, 4.3593338263024295, 4.358371217300469, 4.361544266463001, 4.3645632436616815, 4.3732614145097735, 4.376526510642991, 4.383943888843464, 4.386510235437215, 4.349426828913298, 4.332181300490328, 4.32907528868044, 4.332044428036058, 4.357673036482688, 4.360648281488243, 4.359921939670915, 4.360890614068979, 4.367040072603398, 4.3714661163131785, 4.379236583693624, 4.383530790112164, 4.392331332705831, 4.382701615945236, 4.3402963041039, 4.334769533635521, 4.341862452603839, 4.349284652404745, 4.357343479405425, 4.358026229382989, 4.360037403301639, 4.362135106645846, 4.370897223562037, 4.376038889053469, 4.385591630630064, 4.389476913619544, 4.396808921231108, 4.390334241920141, 4.345767637682432, 4.33779762281023, 4.343125196620114, 4.347929139037546, 4.357738227392269, 4.357865059542216, 4.357670509617692, 4.360134200962841, 4.367375959464244, 4.3721582232082845, 4.380430803141867, 4.384622988973347, 4.391176663028434, 4.379878357344589, 4.342737461159566, 4.339109784187739, 4.349482369083505, 4.355981135477877, 4.362266412965314, 4.361040719974517, 4.362600889473722, 4.3664841848363425, 4.375058462020093, 4.379665302520724, 4.387969915297943, 4.392174133775632, 4.3765929635363445, 4.352619705960522, 4.338358348428591, 4.342186885260215, 4.352322521212295, 4.357005722361774, 4.359466005406317, 4.361521612318538, 4.364681183725682, 4.369107165044434, 4.377703659813175, 4.386396806396165, 4.390764586290298, 4.386463588687883, 4.360846283183241, 4.334238868030836, 4.335684347953264, 4.345945597739051, 4.351673996278787, 4.35617044687595, 4.35567887161036, 4.356578306322917, 4.362153722000546, 4.37051861338607, 4.375501883022822, 4.383589885014655, 4.387697276466208, 4.391427345504232, 4.371532407908998, 4.345524681306228, 4.33193709448398, 4.339163326252767, 4.345197747900263, 4.353960257117062, 4.353639622338632, 4.355549229629674, 4.358849861353442, 4.366930922631228, 4.37140630057554, 4.378724550532372, 4.382679928964275, 4.389338360675084, 4.376499729870393, 4.333200514602362, 4.328397975724529, 4.337449588055835, 4.342949298236618, 4.352352497696, 4.352364183200462, 4.352807115541662, 4.356626874030865, 4.366230677566941, 4.370441277473503, 4.380187230939415, 4.3843351658807155, 4.392431888005473, 4.3904403781340875, 4.3449909930549895, 4.3357649355401335, 4.341614950601249, 4.347540709535806, 4.360707232656287, 4.361638904893926, 4.360707232656287, 4.362594854798478, 4.371092209239187, 4.37565130090147, 4.384362721762569, 4.38814150234619, 4.3954934908507735, 4.388468416740417, 4.342892881123343, 4.335906464685301, 4.344470846316834, 4.350716704537425, 4.362816605194427, 4.362965919592759, 4.363913068982787, 4.366724606657584, 4.375693987623623, 4.38048020203149, 4.389749067750526, 4.394064739698323, 4.397802734510025, 4.37480413864556, 4.335059056927108, 4.3352751107615335, 4.346727121914457, 4.353725879143459, 4.360203768494794, 4.3598921892392175, 4.362133597277574, 4.3645532055671135, 4.372714451447498, 4.377540180902333, 4.386595176034084, 4.39119034712424, 4.392924267719715, 4.36829644186198, 4.330180269208833, 4.330845475147308, 4.356723496518534, 4.363028755687341, 4.369369434707607, 4.368648902301945, 4.370359943577608, 4.374072598447555, 4.381908256455335, 4.387035965673142, 4.396045647400936, 4.400305716652628, 4.404225533249986, 4.369914735373032, 4.326541598532385, 4.326743341708793, 4.349880741163196, 4.356723496518534, 4.363927633507272, 4.364080297136363, 4.365173872178944, 4.368379943719945};
//        data=mData;
        double[] x = new double[data.length];//为了数据倒过来
        //把波形倒过来，易于计算波峰
        for (int i = 0; i < data.length; i++) {
            double tem = data[i];
            x[data.length - i - 1] = tem;
        }
        //把数据变成坐标形式，这是为了后面的插值计算
        for (int i = 0; i < data.length; i++) {
            points.add(new Point(i, (int) (x[i] * 100000)));//points是point（int，int）格式数据，所以把纵坐标加大
        }

        BasicSpline spline = new BasicSpline();//spline插值方法，结果用spline.getPoint得到，在第二个循环
        if (points.size() > 2) {//检验必须有两个点（不然不能进行插值计算）
            for (Point p : points) {
                spline.addPoint(p);
            }
            spline.calcSpline();
        }

        double[] originaldata = new double[1001];//定义新的插值后数据
        if (points.size() > 2) {
            int i = 0;
            for (float f = 0; f <= 1; f += 0.001) {//把原来124个值经过插值计算变成1001个值，其实就是使得曲线更平滑，相当于一个重采样（但是这个重采样更接近曲线本身）
                Point px = spline.getPoint(f);
                double y = px.y;
                originaldata[i] = y;
                i = i + 1;
            }
        }
        MovingAverageFilter filter = new MovingAverageFilter(3);//声明一个均值滤波器，目的是去掉基线漂移
        double[] denoisedata = filter.movingAverageFilter(originaldata);//denoisedata是去噪后数据

        List<Integer> peak = new ArrayList<Integer>();//去噪后曲线的极小值
        for (int i = 1; i < denoisedata.length - 1; i++) {//自己写的极小值的循环，可能有些low，于神帮着改改
            if (denoisedata[i] <= denoisedata[i - 1] && denoisedata[i] < denoisedata[i + 1]) {
                peak.add(i);
            }
        }
        double sum = sum(denoisedata, 0, peak.size() - 1);//因为极小值和最小值不一样，具体看我给你的论文，所以要去掉不符合条件的极小值
        double aver = sum / peak.size();//用极小值的平均值滤掉不符合要求的极小值
        List<Integer> minpeak = new ArrayList<Integer>();//声明符合要求的极小值

        for (int i = 0; i < peak.size() - 1; i++) {
            if (denoisedata[peak.get(i)] < aver && (peak.get(i + 1) - peak.get(i)) < 100) {
                minpeak.add(peak.get(i));

            }
        }
        List<Double> PI = new ArrayList<Double>();//PI就是最后要的PressureIndex,具体看论文，解释起来太麻烦了
        for (int i = 0; i < minpeak.size() - 1; i++) {//PI是每一个单独波形就会有一个的，所以先用极小值差分单独PPG，具体看论文里的图
            int start = minpeak.get(i);//单独波形起点
            int end = minpeak.get(i + 1);//单独波形终点
            double[] onewave = new double[end - start + 1];//声明的单独波形onewave
            for (int j = start; j <= end; j++) {
                onewave[j - start] = denoisedata[j];

            }
            double[] diff1 = diff(onewave);//单独波形的一阶微分，因为是离散量，所以有差分法，因此长度-1
            List<Integer> zerodiff1 = new ArrayList<Integer>();//一阶微分的过零点
            List<Integer> zerodiff2 = new ArrayList<Integer>();//二阶微分的过零点
            zerodiff1 = finddiffzero(onewave);
            zerodiff2 = finddiffzero(diff1);

            if (zerodiff1.size() >= 3) {
                int l1 = zerodiff2.get(0);
                int l2 = zerodiff1.get(0);
                int l3 = zerodiff2.get(2);
                int l4 = zerodiff1.get(1);
                int mid = (int) (zerodiff2.size() - 3) / 2 + 3;
                int l5 = zerodiff2.get(mid);
                int l6 = zerodiff1.get(2);
                int l7 = zerodiff2.get(zerodiff2.size() - 1);//l1到l7就是论文中横坐标了，具体看论文
                if (l1 < l2 && l2 < l3 && l3 < l4 && l4 < l5 && l5 < l6 && l6 < l7) {//基线漂移去的不理想，因此加一个判断，PI长度一般小于数据长度**
                    double PPi = sum(onewave, 0, l1);
                    double PNi = sum(onewave, l1, l2);
                    double NNi = sum(onewave, l2, l3);
                    double NPi = sum(onewave, l3, l4);
                    double PPr = sum(onewave, l4, l5);
                    double PNr = sum(onewave, l5, l6);
                    double NNr = sum(onewave, l6, l7);
                    double Npr = sum(onewave, l7, onewave.length - 1);
                    double PIone = (NNi + NPi) / (PNi + NNi + NPi);//论文中的指标，虽然PI只用到了几个，但是还是都算出来把
                    PI.add(PIone);//把单独波的PI加到总的PI里
                    System.out.println(PIone);//这是我打印出来看看的
                    System.out.println("ok");//同上
                }
            }

        }


    }

    public static double sum(double[] data, int s, int e) {//求和
        double sum = 0.0;
        for (int i = s; i <= e; i++) {
            sum = sum + data[i];
        }
        return sum;
    }

    public static List<Integer> finddiffzero(double[] data) {//找到微分过零点
        List<Integer> zero = new ArrayList<Integer>();
        for (int i = 0; i < data.length - 2; i++) {
            double c = data[i + 1] - data[i];
            double d = data[i + 2] - data[i + 1];
            if (c * d <= 0) {
                zero.add(i + 2);
            }
        }
        return zero;
    }

    public static double[] diff(double[] data) {//求微分（差值）
        double[] diff = new double[data.length - 1];
        for (int k = 0; k < data.length - 1; k++) {
            diff[k] = data[k + 1] - data[k];
        }
        return diff;
    }

}

