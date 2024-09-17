# sl-express-ms-sms-api使用手册

## 1、说明

为了将短信相关的代码进行统一，所以将发送短信的代码抽取到sl-express-ms-sms-service中，而sl-express-ms-sms-api提供了短信服务的功能。

主要功能：

- 短信（短信验证码，通知短信）发送功能，支持同时发送多个短信验证码，暂时支持文字短信验证码

## 2、SQL脚本

错误消息记录的SQL脚本：

~~~sql
无
~~~

## 3、使用

### 3.1、导入依赖

~~~xml
<dependency>
    <groupId>com.sl-express.ms.sms</groupId>
    <artifactId>sl-express-ms-sms-api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
~~~

### 3.2、使用

~~~java
	//注入Service
    @Resource
    private SmsFeign smsFegin;

	....................
        
     //短信内容DTO组装
        SmsInfoDTO smsInfoDTO = new SmsInfoDTO();
        //业务类型 例如快递员端
        smsInfoDTO.setBussinessType(BussinessType.COURIER.getType());
        //短信类型 例如验证类型短信
        smsInfoDTO.setSmsType(SmsType.VERIFY.getType());
        //内容类型短信 例如文字类型
        smsInfoDTO.setContentType(SmsContentType.WORD_MESSAGE.getType());
        //接收短信的手机号，支持多个手机号中间用逗号隔开
        smsInfoDTO.setMobiles("188****1234,188****1231");
        //短信验证码,如果是验证类型，直接填短信验证码，如果是通知类型的则给一个空字符串
        smsInfoDTO.setVerifyCode("1234");
        //触发本次短信的使用者的id
        smsInfoDTO.setUserId(12345L);
        
    //发送消息
    this.smsFegin.send(smsInfoDTO);
~~~