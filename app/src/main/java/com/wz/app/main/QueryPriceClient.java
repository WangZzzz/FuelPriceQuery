package com.wz.app.main;

import com.wz.app.bean.FuelPriceBean;
import com.wz.common.network.NetClient;
import com.wz.common.util.StringUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wz on 2017/10/20.
 */
public class QueryPriceClient {

    private static final String PRICE_URL = "http://www.bitauto.com/youjia/";

    public static void main(String[] args) {
        CloseableHttpResponse httpResponse = NetClient.doGet("http://www.bitauto.com/youjia/");
        String html = StringUtil.responseToString(httpResponse);
        List<FuelPriceBean> priceList = parseHtml(html);
    }

    private static List<FuelPriceBean> parseHtml(String html) {
        List<FuelPriceBean> fuelBeanList = new ArrayList<>();
        Document document = Jsoup.parse(html);
        if (document != null) {
            Elements elements = document.select("div[class=oilTableOut] tbody tr");
            for (Element element : elements) {
                Elements provinceElements = element.select("th a");
                Elements priceElements = element.select("td");

                try {
                    FuelPriceBean fuelBean1 = new FuelPriceBean();
                    fuelBean1.province = provinceElements.get(0).text();
                    fuelBean1.price_gas_92 = getPriceFromStr(priceElements.get(1).text());
                    fuelBean1.price_gas_95 = getPriceFromStr(priceElements.get(2).text());
                    fuelBean1.price_diesel_0 = getPriceFromStr(priceElements.get(3).text());
                    fuelBeanList.add(fuelBean1);
                    System.out.println(fuelBean1.toString());
                    if (provinceElements.size() == 2) {
                        FuelPriceBean fuelBean2 = new FuelPriceBean();
                        fuelBean2.province = provinceElements.get(1).text();
                        fuelBean2.price_gas_89 = getPriceFromStr(priceElements.get(4).text());
                        fuelBean2.price_gas_92 = getPriceFromStr(priceElements.get(5).text());
                        fuelBean2.price_gas_95 = getPriceFromStr(priceElements.get(6).text());
                        fuelBean2.price_diesel_0 = getPriceFromStr(priceElements.get(7).text());
                        System.out.println(fuelBean2.toString());
                        fuelBeanList.add(fuelBean2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return fuelBeanList;
    }

    private static float getPriceFromStr(String str) {
        if (!StringUtil.isEmpty(str)) {
            if (str.contains("(")) {
                String tmpStr = str.substring(0, str.indexOf("("));
                return Float.parseFloat(tmpStr);
            } else {
                return Float.parseFloat(str);
            }
        }
        return 0.00f;
    }
}
