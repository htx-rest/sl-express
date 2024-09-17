# sl-express-ms-sms-service使用手册

## 1、说明

为了将短信相关的代码进行统一，所以将发送短信的代码抽取到sl-express-ms-sms-service

主要功能：

- 发送短信包含文字短信、语音短信，现在第三方短信通道还没有对接
- 可以配置多个第三方短信通过实现动态切换
- 一条短信同时可以多条发送

## 2、SQL脚本

错误消息记录的SQL脚本：

~~~sql
CREATE TABLE `sms_third_channel` (
                                     `id` bigint(20) NOT NULL COMMENT '主键id',
                                     `bussiness_type` int(11) NOT NULL COMMENT '业务类型type，1：用户端，2：司机端，3：快递员端，4：后台管理系统',
                                     `sms_type` int(11) NOT NULL COMMENT '短信类型，1：验证类型短信，2：通知类型短信',
                                     `content_type` int(11) NOT NULL COMMENT '内容类型，1:文字短信，2：语音短信',
                                     `sms_template` varchar(255) NOT NULL COMMENT '短信模板',
                                     `third_template_code` varchar(50) NOT NULL COMMENT '第三方平台模板code',
                                     `channel_code` varchar(20) NOT NULL COMMENT '第三方短信平台码',
                                     `sign_name` varchar(50) NOT NULL COMMENT '签名',
                                     `sms_priority` int(11) NOT NULL COMMENT '数字越大优先级越高',
                                     `status` int(11) NOT NULL COMMENT '通道状态1：使用 中，2：已经停用',
                                     `created` datetime NOT NULL COMMENT '创建时间',
                                     `updated` datetime NOT NULL COMMENT '更新时间',
                                     `creater` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建者',
                                     `updater` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新者',
                                     `is_delete` bit(1) NOT NULL COMMENT '是否删除',
                                     PRIMARY KEY (`id`),
                                     KEY `index_created` (`created`) USING BTREE,
                                     KEY `index_updated` (`updated`) USING BTREE,
                                     KEY `index_type` (`bussiness_type`,`sms_type`,`content_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信发送通道';

CREATE TABLE `sms_record` (
                              `id` bigint(20) NOT NULL COMMENT '短信发送记录id',
                              `mobile` varchar(20) NOT NULL COMMENT '手机号',
                              `user_id` bigint(20) NOT NULL COMMENT '发送者唯一id',
                              `third_channel_code` varchar(20) NOT NULL COMMENT '发送第三方的平台code',
                              `verify_code` varchar(10) NOT NULL COMMENT '短信验证码',
                              `bussiness_type` int(11) NOT NULL COMMENT '业务类型,1：用户端，2：司机端，3：快递员端，4：后台管理系统',
                              `sms_type` int(11) NOT NULL COMMENT '短信类型,1:文字短信，2：语音短信',
                              `content_type` int(11) NOT NULL COMMENT '内容类型，1：短信验证码，2：营销短信',
                              `batch_id` bigint(20) NOT NULL COMMENT '发送批次id，用于判断这些数据是同一批次发送的',
                              `created` datetime NOT NULL COMMENT '创建时间',
                              `updated` datetime NOT NULL COMMENT '更新时间',
                              `creater` bigint(20) NOT NULL COMMENT '创建者',
                              `updater` bigint(20) NOT NULL COMMENT '更新者',
                              PRIMARY KEY (`id`),
                              KEY `index_created` (`created`) USING BTREE,
                              KEY `index_updated` (`updated`) USING BTREE,
                              KEY `index_user_id` (`user_id`) USING BTREE,
                              KEY `index_batch_id` (`batch_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信发送记录';
~~~

## 3、使用

> 短信中心使用方法见 sl-express-ms-sms-api项目中的README.md


## 4.配置
### 4.1、通道配置（表字段介绍）
~~~
表sms_third_channel
通道信息：
bussiness_type：业务类型，指的是用户端，司机端，快递员端，后台管理系统，每一端为一个业务类型
sms_type：短信类型，分为两种类型：1：验证类型短信，2：通知类型短信',
content_type 短信内容类型，1:文字短信，2：语音短信
sms_template 短信模板，例如：您有一条新的短信验证码123456，请注意查收
third_template_code 第三方平台模板code，来自第三方平台，可以是一个code，也可以是一个id
channel_code 第三方短信平台码，这个码在程序中是以bean的name注入到容器中的，每一个通道实现有一个唯一的code
sign_name 签名，短信中的签名 
sms_priority 数字越大优先级越高

~~~~

### 4.2、路由规则
路由规则现在只有一个：
根据业务类型、短信类型、内容类型 状态使用中作为查询条件，通过权重排序，取优先级前五的通道，然后从这些通道中随机选择一条通道。
路由规则后续可以进行拓展。代码展示

~~~java
    @Override
public SmsThirdChannelEntity route(Integer bussinessType, Integer smsType, Integer contentType) {

        //取前五条记录
        QueryWrapper<SmsThirdChannelEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bussiness_type", bussinessType)
        .eq("sms_type", smsType)
        .eq("content_type", contentType);
        queryWrapper.orderBy(true, false, "sms_priority");
        queryWrapper.last("limit 5");
        List<SmsThirdChannelEntity> smsThirdChannelEntities = smsThirdChannelMapper.selectList(queryWrapper);

        if (CollectionUtil.isEmpty(smsThirdChannelEntities)) {
        return null;
        }

        //随机选择
        int randomNumber = (int) (smsThirdChannelEntities.size() * Math.random());
        return smsThirdChannelEntities.get(randomNumber);
        }

~~~
### 4.3、第三方通道对接

例如
~~~java
    @Service("aliyun")
    @Slf4j
    public class AliyunThirdSmsServiceImpl implements ThirdSmsService {
        @Override
        public void send(PlatformSmsInfoDTO platformSmsInfoDTO) {
            //第三方发送短信验证码 此处用来对接第三方短信通道
            log.info("短信发送成功 ...");
            log.info("platformSmsInfoDTO：{}", platformSmsInfoDTO);
    
        }
    }
~~~

> **对接短信通道时一定要注入service中要加上名称，这个名称是sms_third_channel表中的channel_code字段**
