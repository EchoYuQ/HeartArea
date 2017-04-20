/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.bupt.heartarea.utils;

/**
 * 常量类
 *
 * @author rendayun
 */
public final class Constants {

    // 服务器URL
    // 线上
    // public static String URL_TRACK_AR_PREFIX = "https://dusee.baidu.com";
    // 线下
    public static String URL_TRACK_AR_PREFIX = "http://cp01-rdqa-dev123.cp01.baidu.com:8383";

    /**
     * 服务器service名称
     */
    public static String URL_TRACK_AR_SERVICE = "/artrack-bos";
    /**
     * 服务器端的action
     */
    public static final String URL_TRACK_AR_ACTION_QUERY = "/queryARResource";

    /**
     * 场景描述文件名称
     */
    public static final String TARGET_FILE = "targets.json";

    /**
     * lua命令描述文件名称
     */
    public static final String COMMAND_FILE = "command.json";

    /**
     * 统计接口
     */
    public static final String URL_TRACK_AR_STATISTIC = "/count_ar";
    // zip类型文件后缀
    /**
     * zip包后缀
     */
    public static final String ZIP_SUFFIX = "zip";
    /**
     * 英文的点
     */
    public static final String DOT = ".";
    /**
     * app 名称
     */
    public static final String AR_APP_NAME = "AR";
    /**
     * 缓存目录相对路径
     */
    public static final String AR_RESOURCE_CACHE_DIR = "/ARResource";
    /**
     * track ar 资源存放相对路径
     */
    public static final String AR_RESOURCE_CACHE_DIR_TRACK = "/track";
    /**
     * udt ar 资源存放相对路径
     */
    public static final String AR_RESOURCE_CACHE_DIR_UDT = "/udt";
    /**
     * lbs ar 资源存放相对路径
     */
    public static final String AR_RESOURCE_CACHE_DIR_LBS = "/lbs";
    /**
     * 解压缩后，资源根目录
     */
    public static final String AR_UNZIP_ROOT_DIR = "ar";

    /**
     * 领奖 用户信息
     **/
    public static final String PRIZE_BDUSS = "bduss";
    /**
     * 领奖 模型类型
     **/
    public static final String PRIZE_MODEL_TYPE = "model_type";
    /**
     * 渠道号标识 第三方营销库统计使用 营销库分配 可为空
     **/
    public static final String PRIZE_CHANNEL = "channel";
    /**
     * 抽奖时间戳
     **/
    public static final String PRIZE_TIMESTAMP = "timestamp";

    /** 领奖参数 **/
    /**
     * 中奖标识 0,中奖 非0 失败
     **/
    public static String PRIZE_ERRODCODE = "error_code";
    /**
     * 非0 失败原因
     **/
    public static String PRIZE_ERRODMSG = "error_msg";
    /**
     * 奖品详情
     **/
    public static String PRIZE = "prize";
    /**
     * 奖品id
     **/
    public static String PRIZE_ID = "id";
    /**
     * 奖品类型
     **/
    public static String PRIZE_TYPE = "type";
    /**
     * 奖品名称
     **/
    public static String PRIZE_NAME = "name";
    /**
     * 领取奖品URL
     **/
    public static String PRIZE_URL = "url";
    /**
     * 结果展示类型 0, 默认Dialog[模型配置] 1,H5展示类型[依赖业务方FE开发]
     **/
    public static String PRIZE_SHOW_TYPE = "show_type";
    /**
     * BDUSS 错误或失效
     */
    public static final int BDUSS_EXCEPRION = -10000;
    /**
     * ar value
     */
    public static final String AR_VALUE = "arValue";
    /**
     * ar id
     */
    public static final String AR_ID = "ar_id";
    /**
     * ar key
     */
    public static final String AR_KEY = "ar_key";
    /**
     * 手百AR旧版本解析的h5传入的关键字，ar key
     */
    public static final String OLD_AR_KEY = "arKey";
    /**
     * ar类型，比如跟踪、udt等
     */
    public static final String AR_TYPE = "ar_type";
    /**
     * 手百AR旧版本解析的h5传入的关键字，ar类型，比如跟踪、udt等
     */
    public static final String OLD_AR_TYPE = "arType";
    /**
     * ar调起方式，比如多模的拍照调起，feed流的H5调起
     */
    public static final String AR_LAUNCH_MODE = "ar_launch_mode";
    /**
     * ar info 扩展参数
     */
    public static final String EXTRA_INFO = "extra_info";
    /**
     * ar settings
     */
    public static final String AR_SETTINGS = "ar_settings";
    /**
     * ac id
     */
    public static final String AC_ID = "ac_id";

    /**
     * 动态代码资源下载地址
     */
    public static final String AR_CODE_URL = "ar_code_url";

    // http key
    /**
     * error code
     */
    public static final String HTTP_ERR_CODE = "err_code";
    /**
     * error msg
     */
    public static final String HTTP_ERR_MSG = "err_msg";
    /**
     * return
     */
    public static final String HTTP_RET = "ret";
    /**
     * ar resource url
     */
    public static final String HTTP_AR_RESOURCE = "ar_resource";
    /**
     * ar resource url
     */
    public static final String HTTP_AR_MULTI_RESOURCE = "ar_resource_urls";
    /**
     * ar redirect url
     */
    public static final String HTTP_AR_REDIRECT_URL = "redirect_url";
    /**
     * version code
     */
    public static final String HTTP_VERSION_CODE = "version_code";
    /**
     * 操作系统类型参数
     */
    // TODO:等服务端做了兼容后再修改
    public static final String HTTP_OS_TYPE = "osType";
    /**
     * 渲染引擎版本号
     */
    public static final String HTTP_ENGINE_VERSION = "engine_version";
    /**
     * app ID
     */
    public static final String HTTP_APP_ID = "app_id";
    /**
     * user id
     */
    public static final String HTTP_USER_ID = "user_id";
    /**
     * user id
     */
    public static final String HTTP_GLES_VERSION = "gles_version";
    /**
     * ar资源md5校验值
     */
    public static final String HTTP_AR_MD5 = "md5";
    /**
     * ar渲染中的一些数据，用于表单提交
     */
    public static final String HTTP_AR_RENDER_DATA = "ar_render_data";
    /**
     * ar value ,用于入口统计
     */
    public static final String HTTP_AR_VALUE = "ar_value";
    /**
     * 设备型号 参数
     */
    public static final String HTTP_DEVICE_ID = "device_id";
    /**
     * 系统版本
     */
    public static final String HTTP_SYSTEM_VERSION = "system_version";
    /**
     * 是否在黑名单中 如果在，说明设备不支持
     */
    public static final String HTTP_REFUSED = "refused";

    /**
     * command.json中字段commandList
     */
    public static final String LUA_COMMAND_LIST = "commandList";
    /**
     * command.json中字段name
     */
    public static final String LUA_COMMAND_NAME = "name";
    /**
     * command.json中字段luacommand
     */
    public static final String LUA_COMMAND_STRING = "luacommand";








    /**
     * 操作系统类型
     */
    public static final String OS_TYPE_VALUE = "android";
    /**
     * 宿主是否需要获取AR的camera关闭时的最后一帧图
     */
    public static final String AR_NEED_LAST_PREVIEW = "ar_last_preview";
    /**
     * 错误码，SDK版本太低
     */
    public static final int HTTP_ERRCODE_VERSION_LOW = 1051;
    /**
     * 错误码，SDK版本太高，和case 版本不兼容。SDK升级要保证线上case向后兼容，或者重新适配case
     */
    public static final int HTTP_ERRCODE_VERSION_HIGH = 1052;
    /**
     * http请求连接的超时时间
     */
    public static final int HTTP_CONNECT_TIMEOUT = 20 * 1000;
    /**
     * http请求读取的超时时间
     */
    public static final int HTTP_READ_TIMEOUT = 30 * 1000;
    // 网络类型
    /**
     * 2G网络
     */
    public static final int NETWORKTYPE_2G = 2;
    /**
     * 3G网络
     */
    public static final int NETWORKTYPE_3G = 3;
    /**
     * 4G网络
     */
    public static final int NETWORKTYPE_4G = 4;
    /**
     * wifi网络
     */
    public static final int NETWORKTYPE_WIFI = 5;

    /**
     * ar 资源默认存放相对路径
     */
    public static final String AR_RESOURCE_CACHE_DIR_NORMAL = "/normal";
    /**
     * ar log存放相对路径
     */
    public static final String AR_RESOURCE_CACHE_DIR_LOG = "/log";

    // 白名单
    /**
     * 硬件
     */
    public static final String AR_HARDWARE_SATISFIED = "hardware_satisfied";

    /**
     * 是否重新解压缩，默认为TRUE
     */
    public static boolean RE_EXTRACT = false;

    /**
     * 引擎 log 打印开关
     */
    public static boolean AR_IS_NEED_PRINT_FRAME_LOG = false;

    /**
     * 在debug模式下设置flag
     *
     * @param flag 是否重新解压缩的flag
     */
    public static void setReExtract(boolean flag) {
        if (DEBUG) {
            RE_EXTRACT = flag;
        }
    }

    /**
     * 手机系统信息
     *
     * @author xiegaoxi
     */
    public final class OSInfo {
        public static final String OS_BRAND = "os_brand"; // 手机厂商
        public static final String OS_MODEL = "os_model"; // 手机型号
        public static final String OS_VERSION_SDK = "os_version_sdk"; // SDK版本
        public static final String OS_VERSION_RELESE = "os_version_release"; // 系统版本
        public static final String OS_WIDTH_PIXELS = "os_width_pixels"; // 屏幕分辨率
        public static final String OS_HEIGHT_PIXELS = "os_height_pixels"; // 屏幕分辨率
        public static final String OS_SCALE_PDI = "os_scale_pdi"; // 屏幕密度
        public static final String OS_RAM_AVAIL_MEMORY = "os_ram_avail_memory"; // 可用内存大小
        public static final String OS_RAM_MEMEORY = "os_ram_memory"; // 内存大小
        public static final String OS_ROM_AVAIL_MEMORY = "os_rom_avail_memory"; // 可用ROM存储
        public static final String OS_ROM_MEMORY = "os_rom_memory"; // ROM存储
        public static final String OS_ROM_SDCARD_AVAIL_MEMORY = "os_sdcard_avail_memory"; // 可用SDCARD空间
        public static final String OS_SDCARD_MEMORY = "os_sdcard_memory"; // SDCARD存储空间

        public static final String OS_GPU_VENDOR = "os_gpu_vendor"; // GPU供应商
        public static final String OS_GPU_RENDERER = "os_gpu_renderer"; // GPU渲染器
        public static final String OS_GPU_VERSION = "os_gpu_verion"; // GPU版本

        public static final String OS_CPU_NAME = "os_cpu_name"; // CPU名称
        public static final String OS_CPU_NUM_CORES = "os_cpu_num_cores"; // CPU核心数
        public static final String OS_CPU_MIN_FREQ = "os_cpu_min_freq"; // 最低频率
        public static final String OS_CPU_MAX_FREQ = "os_cpu_max_freq"; // 最高频率
        public static final String OS_CPU_CUR_FREQ = "os_cpu_cur_freq"; // 当前频率
        public static final String OS_CPU_SUPPORTED_ABIS = "os_cpu_supported_abis"; // 这个设备支持的CPU

        public static final String OS_NATIVE_HEAPSIZE = "os_native_heapsize"; // 堆内存
        public static final String OS_NATIVE_SENSOR = "os_native_sensor"; // 陀螺仪支持
    }

    public static final String MARKETING = "marketing";
    public static final String CHANNELID = "channel_id";
    public static final String PUBLICKEY = "public_key";
    public static final String DRAWURL = "draw_url";
    public static final String AR_NAME = "ar_name";
    /** 营销库 end **/

    /**
     * 设置Debug开关
     *
     * @param enable 是否开启Debug开关
     */
    public static void setDebugEnable(boolean enable) {
        DEBUG = enable;
        DEBUG_LOG = DEBUG_LOG & DEBUG;
        DEBUG_TOAST = DEBUG_TOAST & DEBUG;
        DEBUG_TRACK_EDGE = DEBUG_TRACK_EDGE & DEBUG;
        DEBUG_TRACK_JIT = DEBUG_TRACK_JIT & DEBUG;
        DEBUG_LOG2FILE = DEBUG_LOG2FILE & DEBUG_TRACK_JIT;
        DEBUG_CAPTURE = DEBUG_CAPTURE & DEBUG;
    }



    // dubug
    /**
     * debug 开关
     */
    public static boolean DEBUG = false;
    /**
     * debug log开关
     */
    public static boolean DEBUG_LOG = true & DEBUG;
    /**
     * debug toast开关
     */
    public static boolean DEBUG_TOAST = true & DEBUG;
    /**
     * debug， 绘制track目标物边框开关
     */
    public static boolean DEBUG_TRACK_EDGE = true & DEBUG;
    /**
     * debug， track界面实时跟踪情况开关
     */
    public static boolean DEBUG_TRACK_JIT = true & DEBUG;
    /**
     * debug, 写log到文件中，用于性能数据日志，必须实时跟踪开的情况下才能记录性能数据
     */
    public static boolean DEBUG_LOG2FILE = true & DEBUG_TRACK_JIT;
    /**
     * debug, capture测试开关
     */
    public static boolean DEBUG_CAPTURE = false & DEBUG;
    /**
     * debug, 可以使用配置的server地址
     */
    public static boolean DEBUG_SERVER = false & DEBUG;

    /**
     * 引擎 log 打印间隔
     */
    public static int PRINT_FPS_INTERVAL = 100;
}
