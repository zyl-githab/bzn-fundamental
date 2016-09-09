package com.bzn.fundamental.protocol.netty;

import java.io.Serializable;

import com.bzn.fundamental.serialization.SerializerExecutor;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;

public class NettyObjectEncoder extends MessageToByteEncoder<Object> {
    public NettyObjectEncoder() {
        
    }
    
    @Override
    protected void encode(ChannelHandlerContext context, Object object, ByteBuf buf) throws Exception {
        byte[] bytes = null;
        try {
            if (buf == null) {
                return;
            }
            
            if (!(object instanceof Serializable)) {
                return;
            }
            bytes = SerializerExecutor.serialize((Serializable) object);
            
			buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            ReferenceCountUtil.release(bytes);
        }
    }
}