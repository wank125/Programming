package flashnetty.server.handler;

import flashnetty.protocol.request.LoginRequestPacket;
import flashnetty.protocol.response.LoginResponsePacket;
import flashnetty.server.session.Session;
import flashnetty.server.session.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

  // 2. 构造单例
  public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
    //登陆操作

    LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
    loginResponsePacket.setVersion(loginRequestPacket.getVersion());
    loginResponsePacket.setUserName(loginRequestPacket.getUserName());

    if (valid(loginRequestPacket)) {
      loginResponsePacket.setSuccess(true);
      String userId = randomUserId();
      loginResponsePacket.setUserId(userId);
      System.out.println("[" + loginRequestPacket.getUserName() + "]登录成功");
      SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUserName()), ctx.channel());
    } else {
      loginResponsePacket.setReason("账号密码校验失败");
      loginResponsePacket.setSuccess(false);
      System.out.println(new Date() + ": 登录失败!");
    }

    // 登录响应
    ctx.channel().writeAndFlush(loginResponsePacket);

  }

  private boolean valid(LoginRequestPacket loginRequestPacket) {
    return true;
  }

  private static String randomUserId() {
    return UUID.randomUUID().toString().split("-")[0];
  }
}
