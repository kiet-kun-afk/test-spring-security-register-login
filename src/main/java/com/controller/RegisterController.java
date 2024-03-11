package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Staff;
import com.util.CaptchaUtil;

import cn.apiclub.captcha.Captcha;

@Controller
@RequestMapping("register")
public class RegisterController {
    
    @RequestMapping("/staff")
    public String registerStaff(Model model) {
        Staff staff = new Staff();
        getCaptcha(staff);
        model.addAttribute("realCaptcha", staff.getRealCaptcha());
        model.addAttribute("hiddenCaptcha", staff.getHiddenCaptcha());
        model.addAttribute("captcha", staff.getCaptcha());
        return "staff/register-staff";
    }

    @RequestMapping("/customer")
	public String registerCustomer() {
		return "customer/register-customer";
	}

    private void getCaptcha(Staff staff) {
        Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
        staff.setHiddenCaptcha(captcha.getAnswer());
        staff.setCaptcha(""); // value entered
        staff.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
    }
}
