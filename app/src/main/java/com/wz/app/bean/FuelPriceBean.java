package com.wz.app.bean;

public class FuelPriceBean {
    public String province;
    public float price_gas_89;
    public float price_gas_92;
    public float price_gas_95;
    public float price_diesel_0;
    //更新时间
    public String updateTime;

    @Override
    public String toString() {
        return province + "[89号汽油：" + price_gas_89
                + "，92号汽油：" + price_gas_92 + "，95号汽油："
                + price_gas_95 + "，0号柴油：" + price_diesel_0 + "]";
    }
}
