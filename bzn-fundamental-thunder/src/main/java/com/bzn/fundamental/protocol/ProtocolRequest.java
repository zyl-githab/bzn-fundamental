package com.bzn.fundamental.protocol;

import com.bzn.fundamental.common.config.ReferenceConfig;
import com.bzn.fundamental.common.util.RandomUtil;

public class ProtocolRequest extends ProtocolMessage {
    private static final long serialVersionUID = 3399899702039468806L;
    
    private ReferenceConfig referenceConfig;
    
    public ProtocolRequest() {
        String messageId = RandomUtil.uuidRandom();
        
        super.setMessageId(messageId);
    }
    
    // Request的MessageId自动产生，不需要设置
    @Override
    public void setMessageId(String messageId) {
        
    }

    public ReferenceConfig getReferenceConfig() {
        return referenceConfig;
    }

    public void setReferenceConfig(ReferenceConfig referenceConfig) {
        this.referenceConfig = referenceConfig;
    }
}