package com.bzn.fundamental.serialization.compression;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bzn.fundamental.common.constant.ThunderConstants;
import com.bzn.fundamental.common.property.ThunderProperties;

public class CompressorFactory {
    private static final Logger LOG = LoggerFactory.getLogger(CompressorFactory.class);

    private static CompressorType compressorType = CompressorType.QUICK_LZ_COMPRESSOR;
    private static boolean compress;

    public static void initialize(ThunderProperties properties) {
        String compressor = properties.getString(ThunderConstants.COMPRESSOR_ATTRIBUTE_NAME);
        try {
            compressorType = CompressorType.fromString(compressor);
        } catch (Exception e) {
            LOG.warn("Invalid compressor={}, use default={}", compressor, compressorType);
        }
        LOG.info("Compressor is {}", compressorType);
        
        compress = properties.getBoolean(ThunderConstants.COMPRESS_ATTRIBUTE_NAME);
        LOG.info("Compress is {}", compress);
    }

    public static CompressorType getCompressorType() {
        return compressorType;
    }

    public static void setCompressorType(CompressorType compressorType) {
        CompressorFactory.compressorType = compressorType;
    }
    
    public static boolean isCompress() {
        return compress;
    }
    
    public static void setCompress(boolean compress) {
        CompressorFactory.compress = compress;
    }
}