package com.sl.ms.web.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.json.JSONObject;
import com.sl.ms.base.domain.enums.StatusEnum;
import com.sl.ms.user.api.MemberFeign;
import com.sl.ms.user.domain.dto.MemberDTO;
import com.sl.ms.web.customer.properties.JwtProperties;
import com.sl.ms.web.customer.service.MemberService;
import com.sl.ms.web.customer.service.WechatService;
import com.sl.ms.web.customer.util.JwtUtil;
import com.sl.ms.web.customer.vo.user.MemberVO;
import com.sl.ms.web.customer.vo.user.RealNameVerifyVO;
import com.sl.ms.web.customer.vo.user.UserLoginRequestVO;
import com.sl.ms.web.customer.vo.user.UserLoginVO;
import com.sl.transport.common.constant.Constants;
import com.sl.transport.common.exception.SLWebException;
import com.sl.transport.common.util.ObjectUtil;
import com.sl.transport.common.util.RealNameVerifyUtil;
import com.sl.transport.common.util.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 */
@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberFeign memberFeign;

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private WechatService wechatService;

    @Resource
    private RealNameVerifyUtil realNameVerifyUtil;

    //实名认证默认关闭
    @Value("${real-name-registration.enable}")
    private String realNameVerify;

    @Override
    public MemberVO detail(Long userId) {
        log.info("查找用户信息(本地):{}", userId);
        MemberDTO member = memberFeign.detail(userId);
        log.info("查找用户信息(本地):{} Result:{}", userId, member);
        MemberVO memberVO = BeanUtil.toBean(member, MemberVO.class);
        memberVO.setName(DesensitizedUtil.chineseName(memberVO.getName()));
        memberVO.setIdCardNo(DesensitizedUtil.idCardNum(memberVO.getIdCardNo(), 6, 4));
        return memberVO;
    }

    /**
     * 新增
     *
     * @param user 用户信息
     */
    public void save(MemberDTO user) {
        memberFeign.save(user);
    }

    /**
     * 根据openid查询用户
     * @param openid 微信ID
     * @return 用户信息
     */
    @Override
    public MemberDTO getByOpenid(String openid) {
        return memberFeign.detailByOpenId(openid);
    }

    /**
     * 登录
     * @param userLoginRequestVO 登录code
     * @return 用户信息
     */
    @Override
    public UserLoginVO login(UserLoginRequestVO userLoginRequestVO) throws IOException {
        //调用小程序的api，根据code获取openid
        JSONObject jsonObject = wechatService.getOpenid(userLoginRequestVO.getCode());
        //若code不正确，则获取不到openid，响应失败
        if (ObjectUtil.isNotEmpty(jsonObject.getInt("errcode"))) {
            throw new SLWebException(jsonObject.getStr("errmsg"));
        }
        String openid = jsonObject.getStr("openid");
        //根据openid查询用户是否存在
        MemberDTO user = getByOpenid(openid);

        //如果用户不存在
        if (ObjectUtil.isEmpty(user)) {

            // 如果没有手机号临时凭证 则需要通知小程序端申请用户授权手机号，并获得手机号临时凭证
            if (ObjectUtil.isEmpty(userLoginRequestVO.getPhoneCode())) {
                return UserLoginVO.builder()
                        .binding(StatusEnum.DISABLED.getCode())
                        .build();
            }

            // 获取手机号
            String phone = wechatService.getPhone(userLoginRequestVO.getPhoneCode());

            // 存到数据库
            user = MemberDTO.builder()
                 // openId
                .openId(openid)
                // 平台唯一ID
                .authId(jsonObject.getStr("unionid"))
                 // 手机号
                .phone(phone)
                 .build();

            save(user);
            // 再次获取ID
            user = getByOpenid(openid);
        }

        //生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl() , claims);

        //封装用户信息和token，响应结
        return UserLoginVO
                .builder()
                .openid(openid)
                .token(token)
                .binding(StatusEnum.NORMAL.getCode())
                .build();
    }

    /**
     * 实名认证
     * @param vo 身份证号 姓名
     * @return 是否通过认证
     */
    @Override
    public RealNameVerifyVO realNameVerify(RealNameVerifyVO vo) {
        if (!ObjectUtil.isEmpty(vo.getFlag()) && !vo.getFlag()) {
            // 删除实名认证
            // 保存用户表
            MemberDTO memberDTO = MemberDTO
                    .builder()
                    .id(UserThreadLocal.getUserId())
                    .idCardNoVerify(StatusEnum.DISABLED.getCode())
                    .build();
            save(memberDTO);
            vo.setFlag(true);
            return vo;
        }

        RealNameVerifyVO realNameVerifyVO = new RealNameVerifyVO();
        realNameVerifyVO.setName(DesensitizedUtil.chineseName(vo.getName()));
        realNameVerifyVO.setIdCard(DesensitizedUtil.idCardNum(vo.getIdCard(), 6, 4));
        realNameVerifyVO.setSex(IdcardUtil.getGenderByIdCard(vo.getIdCard()));
        realNameVerifyVO.setFlag(false);


        //1.校验身份证号规则
        if (!IdcardUtil.isValidCard(vo.getIdCard())) {
            return realNameVerifyVO;
        }


        //2.实名认证（校验身份证号和姓名的一致性）
        //实名认证收费，免费次数有限，请慎重使用
        if (Boolean.parseBoolean(realNameVerify)) {
            try {
                if (!realNameVerifyUtil.realNameVerify(vo.getName(), vo.getIdCard())) {
                    // 不通过
                    return realNameVerifyVO;
                }
            } catch (IOException e) {
                throw new SLWebException("实名认证方法执行失败");
            }
        }
        realNameVerifyVO.setFlag(true);
        // 保存用户表
        MemberDTO memberDTO = MemberDTO
                .builder()
                .id(UserThreadLocal.getUserId())
                .name(vo.getName())
                .idCardNo(vo.getIdCard())
                .idCardNoVerify(StatusEnum.NORMAL.getCode())
                .build();
        save(memberDTO);
        return realNameVerifyVO;
    }

    /**
     * 删除用户
     */
    @Override
    public void del() {
        Long userId = UserThreadLocal.getUserId();
        memberFeign.del(userId);
    }

    /**
     * 更新用户
     * @param vo 用户
     */
    @Override
    public void update(MemberVO vo) {
        Long userId = UserThreadLocal.getUserId();
        MemberDTO memberDTO = BeanUtil.toBean(vo, MemberDTO.class);
        memberDTO.setId(userId);
        memberFeign.update(userId, memberDTO);
    }
}
