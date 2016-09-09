package com.bzn.fundamental.sms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 短信模版
 * 
 * @author：fengli
 * @since：2016年8月24日 下午1:20:56
 * @version:
 */
public class SmsTemplate {

	/**
	 * 个人账号开通-模版id
	 */
	public static String TEMPLATE_ID_ACCOUNT_PERSON_OPEN = "108138";

	/**
	 * 个人账号开通-模版内容
	 */
	public static String TEMPLATE_TEXT_ACCOUNT_PERSON_OPEN = "【保准牛】系统已经为您开通个人账号，用户名是您的手机号，密码：{1}，请登录系统进行修改，如有疑问请致电400-085-2751。";

	/**
	 * 重置密码-模版id
	 */
	public static String TEMPLATE_ID_RESET_PASSWORD = "105046";

	/**
	 * 重置密码-模版内容
	 */
	public static String TEMPLATE_TEXT_RESET_PASSWORD = "【保准牛】尊敬的用户您好，您的{1}密码已被重置，新密码为：{2}，请您尽快登录系统进行修改，如有疑问请联系4000852751。";

	/**
	 * 保准牛-微信-OFO报案通知-模版id
	 */
	public static String TEMPLATE_ID_OFO_REPORT_NOTICE = "102917";

	/**
	 * 保准牛-微信-OFO报案通知-模版内容
	 */
	public static String TEMPLATE_TEXT_OFO_REPORT_NOTICE = "【保准牛】您好，保准牛于{1}受理OFO报案，详细信息请查看报案通知邮件。";

	/**
	 * 报案通知-模版id
	 */
	public static String TEMPLATE_ID_REPORT_NOTICE = "100674";

	/**
	 * 报案通知-模版内容
	 */
	public static String TEMPLATE_TEXT_REPORT_NOTICE = "【保准牛】{1}您好，您的报案已受理，请您准备相关索赔资料提交给贵公司的报案负责人{2}，以便快速完成理赔，如有疑问请联系4000852751。";

	/**
	 * 权限审核未通过-模版id
	 */
	public static String TEMPLATE_ID_AUDIT_NOT_PASSED = "81401";

	/**
	 * 权限审核未通过-模版内容
	 */
	public static String TEMPLATE_TEXT_AUDIT_NOT_PASSED = "【保准牛】您在百度外卖保障购买平台上的授权申请未审核通过，请重新提交相关资料进行审核。";

	/**
	 * 权限审核通过-模版id
	 */
	public static String TEMPLATE_ID_AUDIT_PASSED = "81398";

	/**
	 * 权限审核通过-模版内容
	 */
	public static String TEMPLATE_TEXT_AUDIT_PASSED = "【保准牛】您在百度外卖保障购买平台上的授权申请已经审核通过，请用该手机号登录平台完成产品购买。";

	/**
	 * 企业注册通知-模版id
	 */
	public static String TEMPLATE_ID_REGISTER_NOTICE_COMPANY = "75791";

	/**
	 * 企业注册通知-模版内容
	 */
	public static String TEMPLATE_TEXT_REGISTER_NOTICE_COMPANY = "【保准牛】已为您生成企业账号，用户名：{1}密码：{2}请您牢记！关注微信“baozhunniu”享受贴心服务，如有疑问欢迎致电4000852751。";

	/**
	 * 注册验证码-模版id
	 */
	public static String TEMPLATE_ID_REGISTER_VALID_CODE = "74843";

	/**
	 * 注册验证码-模版内容
	 */
	public static String TEMPLATE_TEXT_REGISTER_VALID_CODE = "【保准牛】验证码：{1}，有效期为{2}分钟。关注微信“baozhunniu”可快速查看最新动态，如有疑问请致电4000852751。";

	/**
	 * 宏替换正则
	 */
	public static String MACRO_SUBSTITUTION_PATTERN = "\\{\\d\\}";

	/**
	 * 短信模版宏替换函数
	 * 
	 * @param context
	 * @param params
	 * @return
	 */
	public static String macroSubstitution(String context, String[] params) {
		Pattern pattern = Pattern.compile(MACRO_SUBSTITUTION_PATTERN);
		Matcher matcher = pattern.matcher(TEMPLATE_TEXT_REGISTER_VALID_CODE);
		int intdex = 0;
		while (matcher.find()) {
			String group = matcher.group();
			context = context.replace(group, params[intdex++]);
		}
		return context;
	}
}
