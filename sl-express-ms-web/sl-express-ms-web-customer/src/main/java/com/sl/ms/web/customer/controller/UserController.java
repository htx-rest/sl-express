package com.sl.ms.web.customer.controller;

import com.sl.ms.web.customer.service.MemberService;
import com.sl.ms.web.customer.vo.user.MemberVO;
import com.sl.ms.web.customer.vo.user.RealNameVerifyVO;
import com.sl.ms.web.customer.vo.user.UserLoginRequestVO;
import com.sl.ms.web.customer.vo.user.UserLoginVO;
import com.sl.transport.common.util.UserThreadLocal;
import com.sl.transport.common.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 * 用户管理
 */
@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private MemberService memberService;

    /**
     * C端用户登录--微信登录
     *
     * @param userLoginRequestVO 用户登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public R<UserLoginVO> login(@RequestBody UserLoginRequestVO userLoginRequestVO) throws IOException {
        UserLoginVO login = memberService.login(userLoginRequestVO);
        return R.success(login);
    }

    @ApiOperation(value = "更新个人信息")
    @PutMapping("profile")
    public R update(@RequestBody MemberVO vo) {
        memberService.update(vo);
        return R.success();
    }

    @ApiOperation(value = "我的信息")
    @GetMapping("profile")
    public R<MemberVO> profile() {
        //获取userid
        Long userId = UserThreadLocal.getUserId();
        MemberVO member = memberService.detail(userId);
        return R.success(member);
    }

    @ApiOperation(value = "解除我的信息")
    @DeleteMapping("profile")
    public R del() {
        memberService.del();
        return R.success();
    }

    /**
     * 实名认证
     * @param verifyVo   姓名 & 身份证号
     * @return 是否通过认证
     */
    @ApiOperation(value = "实名认证")
    @PostMapping("realNameVerify")
    public R<RealNameVerifyVO> realNameVerify(@RequestBody RealNameVerifyVO verifyVo) {
        RealNameVerifyVO realNameVerifyVo = memberService.realNameVerify(verifyVo);
        if (realNameVerifyVo.getFlag()) {
            return R.success(realNameVerifyVo);
        }
        return R.error(realNameVerifyVo, "身份校验未通过");
    }

}
