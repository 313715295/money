import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        printWeekData("161723");
    }


    private static void printWeekData(String code) {
        Map<Integer, String> map = Maps.newHashMap();
        map.put(1, "一");
        map.put(2, "二");
        map.put(3, "三");
        map.put(4, "四");
        map.put(5, "五");
        map.put(6, "六");
        map.put(7, "日");
        LocalDate now = LocalDate.now();
        LocalDate start = now.minusYears(1);
        List<Data> dataList = listData(code, 1, 365, DateUtil.toString(start), DateUtil.toString(now));
        Map<Integer, BigDecimal> group = dataList.stream()
                .collect(Collectors.toMap(data -> {
                    LocalDate date = data.getDate();
                    return date.getDayOfWeek().getValue();
                }, Data::getRate, BigDecimal::add));
        group.forEach((k,v)-> System.out.println(map.get(k) + " = " + v));
    }

    private static List<Data> listData(String code, Integer pageIndex, Integer pageSize, String startTime, String endTime) {
        String referer = "http://fundf10.eastmoney.com/f10/jjjz_" + code + ".html";
        long time = System.currentTimeMillis();
        String url = "http://api.fund.eastmoney.com/f10/lsjz?callback=jQuery18306596328894644803_1571038362181&" +
                "fundCode=%s&pageIndex=%s&pageSize=%s&startDate=%s&endDate=%s&_=%s";
        url = String.format(url,code,pageIndex,pageSize,startTime,endTime,time);
        HttpRequest request = HttpUtil.createGet(url);
        request.header("Referer", referer);
        String str = request.execute().body();
        //indexOf返回某个指定的字符串值在字符串中首次出现的位置
        int indexStart = str.indexOf("(");
        int indexEnd = str.lastIndexOf(")");
        //截取字符串
        str = str.substring(indexStart + 1, indexEnd);
        //转换为Obj类型
        JSONObject jsonObject = (JSONObject) JSON.parseObject(str).get("Data");
        //获取数组
        JSONArray jsonArray = jsonObject.getJSONArray("LSJZList");
        return jsonArray.toJavaList(JsonData.class)
                .parallelStream().map(JsonData::convert).collect(Collectors.toList());
    }


}
