package com.zhou.git;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {
    public static void main(String[] args) {

        //模拟验证码发送
        //verifyCode("15070555959");
        getRedisCode("15070555959","092147");
    }

    //1、生成6位数字验证码
    public static String getCode(){
        Random random = new Random();
        String code = "";
        for (int i=0; i<6; i++){
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
    }

    //2、让每个手机每天只能发送三次，验证码放到redis中，设置过期事件
    public static void verifyCode(String phone){

        //连接Redis
        Jedis jedis = new Jedis("192.168.137.128",6379);
        jedis.auth("123456");

        //拼接key，手机发送次数key
        String countKey = "VerifyCode"+phone+":count";
        //验证码key
        String codeKey = "VerifyCode"+phone+":code";
        //每部手机发送的次数
        String count = jedis.get(countKey);
        if (count == null){
            //没有发送，第一次发送
            jedis.setex(countKey,24*60*60,"1");
        }else if(Integer.parseInt(count)<=2){
            //发送次数+1
            jedis.incr(countKey);
        }else if(Integer.parseInt(count)>2){
            System.out.println("你今天的次数已经超出了3次");
            jedis.close();
            return;
        }
        //发送的验证码，放进redis里面
        String vcode = getCode();
        jedis.setex(codeKey,120,vcode);
        jedis.close();
    }

    //3、验证码校验
    public static void getRedisCode(String phone, String code){
        //从redis获取到验证码
        //连接Redis
        Jedis jedis = new Jedis("192.168.137.128",6379);
        jedis.auth("123456");
        String codeKey = "VerifyCode"+phone+":code";
        String redisCode = jedis.get(codeKey);
        //判断
        if(redisCode.equals(code)){
            System.out.println("成功");
        }else {
            System.out.println("失败");
        }
        jedis.close();
    }
}
