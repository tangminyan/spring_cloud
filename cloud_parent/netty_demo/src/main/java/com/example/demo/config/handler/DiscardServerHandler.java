package com.example.demo.config.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;

/**
 * Created by tangminyan on 2019/10/22.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                System.out.println("--- Reader Idle ---");
                ctx.writeAndFlush("读取等待：客户端你在吗... ...\r\n");
                // ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) {
                System.out.println("--- Write Idle ---");
                ctx.writeAndFlush("写入等待：客户端你在吗... ...\r\n");
                // ctx.close();
            } else if (e.state() == IdleState.ALL_IDLE) {
                System.out.println("--- All_IDLE ---");
                ctx.writeAndFlush("全部时间：客户端你在吗... ...\r\n");
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + " channelActive");
        String str = "您已经开启与服务端链接 " + ctx.channel().id() + new Date() + " " + ctx.channel().localAddress();
        ctx.writeAndFlush(str);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + " channelInactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                System.out.println((char)in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}




















