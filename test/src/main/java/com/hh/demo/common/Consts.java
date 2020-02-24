package com.hh.demo.common;

public class Consts {

    public static final String USER="CURRENT_USER";

    public enum CartProductCheckEnum {

        CHECKED(1, "已选中"),
        UNCHECKED(0, "未选中"),

        ;

        private int check;
        private String desc;

        CartProductCheckEnum(int check, String desc) {
            this.check = check;
            this.desc = desc;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum OrderStatusEnum{

        CANCELED(0,"已取消"),
        UNPAY(10,"未付款"),
        PAYEN(20,"已付款"),
        SEND(40,"已发货"),
        SUCCESS(50,"交易成功"),
        CLOSE(60,"交易关闭"),


        ;

        private int status;
        private String desc;
        OrderStatusEnum(int status,String desc){
            this.status = status;
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

    }
}

