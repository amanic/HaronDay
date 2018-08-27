package com.haron.pro.haron;

import com.alibaba.fastjson.JSONObject;
import com.haron.pro.common.annotation.*;
import com.haron.pro.common.module.credential.WxJsTicketManager;
import com.haron.pro.common.module.event.WxEvent;
import com.haron.pro.common.module.extend.WxCard;
import com.haron.pro.common.module.extend.WxQrCode;
import com.haron.pro.common.module.extend.WxShortUrl;
import com.haron.pro.common.module.js.WxJsApi;
import com.haron.pro.common.module.js.WxJsConfig;
import com.haron.pro.common.module.media.WxMedia;
import com.haron.pro.common.module.media.WxMediaManager;
import com.haron.pro.common.module.message.*;
import com.haron.pro.common.module.user.WxTagUser;
import com.haron.pro.common.module.user.WxUser;
import com.haron.pro.common.module.web.WxRequest;
import com.haron.pro.common.module.web.WxRequestBody;
import com.haron.pro.common.module.web.session.WxSession;
import com.haron.pro.common.service.WxApiService;
import com.haron.pro.common.service.WxExtendService;
import com.haron.pro.common.util.HttpClientUtil;
import com.haron.pro.common.util.WxMessageUtils;
import com.haron.pro.common.util.WxWebUtils;
import com.haron.pro.common.web.WxWebUser;
import com.haron.pro.dao.entity.DateRemind;
import com.haron.pro.service.api.DateRemindService;
import com.haron.pro.service.model.UnionParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WxApplication(menuAutoCreate = true)
@WxController
@ComponentScan(value = {"com.haron.pro"})
@EnableSwagger2
@EnableScheduling
public class HaronApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaronApplication.class, args);
	}

	@Autowired
	WxApiService wxApiService;

	@Autowired
	WxMediaManager wxMediaManager;

	@Autowired
	WxMessageTemplate wxMessageTemplate;

	@Autowired
	WxExtendService wxExtendService;

	@Autowired
	WxJsTicketManager wxJsTicketManager;

	@Autowired
	DateRemindService dateRemindService;



	/**
	 * 定义微信菜单
	 */
	@WxButton(group = WxButton.Group.LEFT, main = true, name = "左")
	public void left() {
	}

	/**
	 * 定义微信菜单
	 */
	@WxButton(group = WxButton.Group.MIDDLE, main = true, name = "中")
	@WxAsyncMessage
	public WxMessage middle(WxUser wxUser) {
		WxQrCode wxQrCode = WxQrCode.builder().permanent(wxUser.getOpenId()).build();
		WxQrCode.Result qrCode = wxExtendService.createQrCode(wxQrCode);
//        List<WxMessage> messages = new ArrayList<>();
//        messages.add(WxMessage.textBuilder().content("消息规则").build());
		return WxMessage.imageBuilder()
				.mediaUrl(qrCode.getShowUrl())
//                .mediaPath("E:/showqrcode2.jpg")
				.build();
	}

	/**
	 * 定义微信菜单
	 */
	@WxButton(group = WxButton.Group.RIGHT, main = true, name = "右")
	@WxAsyncMessage
	public String right(WxUser wxUser) {
		return wxUser.getNickName() + "haha";
	}

	/** ***********************************************************************************************************************************/



	@WxButton(group = WxButton.Group.RIGHT, order = WxButton.Order.FIRST, name = "每日签到")
	public WxMessage right1(WxUser wxUser) {
		return dateRemindService.sign(wxUser);
	}

	@WxButton(group = WxButton.Group.RIGHT, order = WxButton.Order.SECOND, name = "查看最近纪念日")
	public WxMessage right2(WxUser wxUser) {
		return dateRemindService.next(wxUser);
	}

	@WxButton(type = WxButton.Type.VIEW,
			group = WxButton.Group.RIGHT,
			order = WxButton.Order.THIRD,
			url = "http://haron.natapp1.cc/temp/error.html",
			name = "反对作者帅！")
	@WxAsyncMessage
	public WxMessage right3(WxRequest wxRequest) {
		return WxMessage.Text.builder().content("网页都不对劲了，他是最帅的😍！").build();
	}

	@WxButton(group = WxButton.Group.RIGHT, order = WxButton.Order.THIRD, name = "右4", type = WxButton.Type.PIC_WEIXIN)
	public WxMessage right4(WxUser wxUser) {
		return WxMessage.newsBuilder().addItem("title","description","https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-674407.jpg","http://www.btkitty.com/").build();
	}

	@WxButton(group = WxButton.Group.RIGHT, order = WxButton.Order.THIRD, name = "右5", type = WxButton.Type.VIEW, url = "http://www.btkitty.com/")
	public WxMessage right5(WxUser wxUser) {
		return WxMessage.newsBuilder().addItem("title","description","https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-674407.jpg","http://www.btkitty.com/").build();
	}



	/**
	 * 定义微信菜单，并接受事件
	 */
	@WxButton(type = WxButton.Type.CLICK,
			group = WxButton.Group.LEFT,
			order = WxButton.Order.FIRST,
			name = "文本消息")
	public WxMessage leftFirst(WxRequest wxRequest, WxUser wxUser) {
		return WxMessage.Text.builder().content("测试文本消息").build();
	}

	/**
	 * 定义微信菜单，并接受事件
	 */
	@WxButton(type = WxButton.Type.VIEW,
			group = WxButton.Group.LEFT,
			order = WxButton.Order.SECOND,
			url = "http://haron.natapp1.cc/test/t1",
			name = "点击链接")
	@WxAsyncMessage
	public WxMessage link(WxRequest wxRequest) {
		return WxMessage.Text.builder().content("点击了菜单链接").build();
	}

	/**
	 * 定义微信菜单，并接受事件
	 */
	@WxButton(type = WxButton.Type.CLICK,
			group = WxButton.Group.LEFT,
			order = WxButton.Order.THIRD,
			name = "图文消息")
	public WxMessage news() {
		return WxMessage.News.builder()
				.addItem("测试图文消息", "测试", "https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-661501.jpg", "http://tczmh.club/bz/index.html")
				.addItem("测试图文消息", "测试", "https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-679271.png", "https://github.com/amanic")
				.addItem("测试图文消息", "测试", "https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-9394.jpg", "https://github.com/LauItachi/WeChatTest")
				.build();
	}

	/**
	 * 定义微信菜单，并接受事件
	 */
	@WxButton(type = WxButton.Type.CLICK,
			group = WxButton.Group.LEFT,
			order = WxButton.Order.FORTH,
			name = "图片消息")
	public WxMessage image() {
		return WxMessage.imageBuilder()
				.mediaUrl("https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-669722.jpg")
				.build();
	}

	@WxButton(type = WxButton.Type.CLICK,
			group = WxButton.Group.LEFT,
			order = WxButton.Order.FIFTH,
			name = "资料")
	@WxAsyncMessage
	public WxUserMessage showQrCode(WxUser wxUser) {
		WxQrCode wxQrCode = WxQrCode.builder().permanent(wxUser.getOpenId()).build();
		WxQrCode.Result qrCode = wxExtendService.createQrCode(wxQrCode);
		String showUrl = qrCode.getShowUrl();
		WxUserMessage message = WxMessage.News.builder()
				.addItem(WxMessageBody.News.Item.builder().title("二维码").description("您的专属二维码")
						.picUrl(showUrl)
						.url(showUrl).build()).build();
		return message;
	}

	/**
	 * 接受微信事件
	 *
	 * @param wxRequest
	 * @param wxUser
	 */
	@WxEventMapping(type = WxEvent.Type.UNSUBSCRIBE)
	public void unsubscribe(WxRequest wxRequest, WxUser wxUser) {
		System.out.println("取消关注" + wxUser.getOpenId());
//        System.out.println(wxUser.getNickName() + "退订了公众号");
	}

	/**
	 * 接受微信事件
	 *
	 * @param wxRequest
	 * @param wxUser
	 */
	@WxEventMapping(type = WxEvent.Type.SUBSCRIBE)
	public String subscribe(WxRequest wxRequest, WxUser wxUser) {
		return "欢迎您关注本公众号，本公众号使用FastBootWeixin框架开发，简单极速开发微信公众号，你值得拥有";
	}

	@WxEventMapping(type = WxEvent.Type.SCAN)
	public String scan(WxRequest wxRequest, WxUser wxUser) {
		System.out.println("扫描二维码" + wxUser.getOpenId());
		return "触发扫描二维码";
	}

	/**
	 * 接受微信事件
	 *
	 */
	@WxEventMapping(type = WxEvent.Type.LOCATION)
	public WxMessage location(WxRequestBody.LocationReport location) {
		return WxMessage.News.builder()
				.addItem("接受到您的地理位置", "测试", "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white.png", "http://mxixm.com")
				.addItem("纬度" + location.getLatitude(), "测试", "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white.png", "http://smc24f.natappfree.cc/vendor/82")
				.addItem("经度" + location.getLongitude(), "测试", "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white.png", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2a0e54054e2fb7c0&redirect_uri=http://smc24f.natappfree.cc/vendor/82&response_type=code&scope=snsapi_base&state#wechat_redirect")
				.build();
	}


	/**
	 * 接受微信事件
	 *
	 * @param wxUser
	 */
	@WxEventMapping(type = WxEvent.Type.TEMPLATESENDJOBFINISH)
	public void template(WxRequestBody.Template template, WxUser wxUser) {
		// 模板消息发送完成的回调
		System.out.println(template.toString());
	}

	/**
	 * 接受用户文本消息，异步返回文本消息
	 *
	 * @param content
	 * @return the result
	 */
	/*@WxMessageMapping(type = WxMessage.Type.TEXT)
	public String text(WxRequest wxRequest, String content) {
		WxSession wxSession = wxRequest.getWxSession();
		if (wxSession != null && wxSession.getAttribute("last") != null) {
			return "上次收到消息内容为" + wxSession.getAttribute("last");
		}
		return "收到消息内容为?:" + content;
	}*/


	@WxMessageMapping(type = WxMessage.Type.LOCATION)
	public String location(WxRequest wxRequest, WxRequestBody.Location location) {
		return location.toString();
	}

	/**
	 * 接受用户文本消息，同步返回图文消息
	 *
	 * @param content
	 * @return the result
	 */
	@WxMessageMapping(type = WxMessage.Type.TEXT, wildcard = "*")
	public String message(WxSession wxSession, String content,WxUser wxUser) {
		wxSession.setAttribute("last", content);
		return dateRemindService.chat(content,wxUser.getOpenId());
	}

	/*
	@WxMessageMapping(type = WxMessage.Type.TEXT, wildcard = "2*")
	@WxAsyncMessage
	public String text2(WxRequestBody.Text text, String content) {
		boolean match = text.getContent().equals(content);
		return "收到消息内容为" + content + "!结果匹配！" + match;
	}

	@WxMessageMapping(type = WxMessage.Type.TEXT, wildcard = "3*")
	@WxAsyncMessage
	public String text3(WxRequestBody.Text text, String content) {
		return WxMessageUtils.linkBuilder().href("http://baidu.com").text("123123").build();
	}


	@WxMessageMapping(type = WxMessage.Type.TEXT, wildcard = "群发*")
	@WxAsyncMessage
	public WxMessage groupMessage(String content) {
		String tagId = content.substring("群发".length());
		return WxMessage.Text.builder().content("pKS9_xJ6hvk4uLPOsHNPmnVRw0vE").toGroup(Integer.parseInt(tagId)).build();
	}


	@WxMessageMapping(type = WxMessage.Type.TEXT, wildcard = "模板*")
	public String templateMessage(WxRequestBody.Text text) {
		WxTemplateMessage templateMessage = WxMessage.templateBuilder()
				.data("keynote1", "1324.76", "#FF0000")
				.data("keynote2", "2017-10-25", "#0000FF")
				.templateId("IIXwm9TJ5F-tAXPdqP7D4xL6rRK-lVwpNWlVRIsZ9Wo")
				.toUser(text.getFromUserName())
//                .url("http://www.baidu.com")
				.build();
		wxMessageTemplate.sendTemplateMessage(templateMessage);
		return "模板消息已发送";
	}

	@WxMessageMapping(type = WxMessage.Type.TEXT, wildcard = "卡券*")
	public List<WxMessage> cardMessage(String content) {
		Integer tagId = Integer.parseInt(content.substring("卡券".length()));
		WxTagUser.UserList userList = wxApiService.listUserByTag(WxTagUser.listUser(tagId));
		return userList.getOpenIdList().stream().flatMap(u -> {
			List<WxMessage> l = new ArrayList();
			l.add(WxMessage.WxCard.builder().cardId("pKS9_xMBmNqlcWD-uAkD1pOy09Qw").toUser(u).build());
			l.add(WxMessage.WxCard.builder().cardId("pKS9_xPsM7ZCw7BW1U2lRRN-J2Qg").toUser(u).build());
			return l.stream();
		}).collect(Collectors.toList());
	}*/

	@RequestMapping("cards")
	public List<WxCard> cards() {
		return wxApiService.getCards(WxCard.ListSelector.of(WxCard.Status.CARD_STATUS_NOT_VERIFY))
				.getCardIdList().stream().map(id -> {
					return wxApiService.cardInfo(WxCard.CardSelector.info(id));
				}).collect(Collectors.toList());
	}

	@RequestMapping("card")
	public WxCard card() {
		return wxApiService.cardInfo(WxCard.CardSelector.info("pKS9_xMBmNqlcWD-uAkD1pOy09Qw"));
	}


	@RequestMapping("mediaUpload")
	public String mediaUpload() {
		return wxMediaManager.addTempMedia(WxMedia.Type.IMAGE, new FileSystemResource("E:/test.png"));
	}

	@RequestMapping("send")
	@ResponseBody
	public String testWeb(String openId) {
		WxUserMessage wxUserMessage = WxMessage.imageBuilder().mediaUrl("http://g.hiphotos.baidu.com/image/pic/item/7dd98d1001e939018ffc7c2d71ec54e736d19623.jpg").build();
		wxMessageTemplate.sendMessage(openId, wxUserMessage);
		return "";
	}

	@RequestMapping("sendGroup")
	@ResponseBody
	public WxMessage sendGroup(String text) {
		return WxMessage.textBuilder().content(text).toGroup().build();
	}

	@RequestMapping("qrcode")
	@ResponseBody
	public WxQrCode.Result qrcode() {
		return wxExtendService.createQrCode(WxQrCode.builder().temporary(1).build());
	}

	@RequestMapping("shortUrl")
	@ResponseBody
	public String shortUrl() {
		return wxExtendService.createShortUrl(WxShortUrl.builder().longUrl("http://wap.koudaitong.com/v2/showcase/goods?alias=128wi9shh&spm=h56083&redirect_count=1").build());
	}

	@RequestMapping("wx/bind")
	@ResponseBody
	public String login() {
		WxWebUser wxWebUser = WxWebUtils.getWxWebUserFromSession();
		return wxWebUser.getOpenId();
	}

	@PostMapping("doError")
	@ResponseBody
	public WxMessage err(String text) {
		return WxMessage.textBuilder().content(text).toGroup("oKS9_xGOW1xJQnIaKhFUaoei_UxU", "oKS9_xBZfDTmA3v6ahWs-hrkAqT4").build();
	}

	@RequestMapping("getWxJsConfig")
	@ResponseBody
	public WxJsConfig wxJsConfig() {
		return wxJsTicketManager.getWxJsConfigFromRequest(WxJsApi.getLocation);
	}
}
