package com.bzn.fundamental.framework;

import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.bzn.fundamental.common.constant.ThunderConstants;
import com.bzn.fundamental.common.container.CacheContainer;
import com.bzn.fundamental.common.container.ExecutorContainer;
import com.bzn.fundamental.common.delegate.ThunderDelegate;
import com.bzn.fundamental.common.delegate.ThunderDelegateImpl;
import com.bzn.fundamental.common.property.ThunderProperties;
import com.bzn.fundamental.common.property.ThunderPropertiesManager;
import com.bzn.fundamental.framework.parser.ApplicationBeanDefinitionParser;
import com.bzn.fundamental.framework.parser.MethodBeanDefinitionParser;
import com.bzn.fundamental.framework.parser.MonitorBeanDefinitionParser;
import com.bzn.fundamental.framework.parser.ProtocolBeanDefinitionParser;
import com.bzn.fundamental.framework.parser.ReferenceBeanDefinitionParser;
import com.bzn.fundamental.framework.parser.RegistryBeanDefinitionParser;
import com.bzn.fundamental.framework.parser.ServiceBeanDefinitionParser;
import com.bzn.fundamental.framework.parser.StrategyBeanDefinitionParser;

public class ThunderNamespaceHandlerSupport extends NamespaceHandlerSupport {
	static {
		try {
			Font font = new Font("黑体", Font.PLAIN, 14);
			AffineTransform at = new AffineTransform();
			FontRenderContext frc = new FontRenderContext(at, true, true);
			GlyphVector gv = font.createGlyphVector(frc, "BaoZhun牛"); // 要显示的文字
			Shape shape = gv.getOutline(2, 15);
			int weith = 100;
			int height = 20;
			boolean[][] view = new boolean[weith][height];
			for (int i = 0; i < weith; i++) {
				for (int j = 0; j < height; j++) {
					if (shape.contains(i, j)) {
						view[i][j] = true;
					} else {
						view[i][j] = false;
					}
				}
			}
			int n = 0;

			for (int j = 0; j < height; j++) {
				for (int i = 0; i < weith; i++) {
					if (view[i][j]) {
						n++;

						switch (n % 4) {
						case 0:
							System.out.print("@");
							break;
						case 1:
							System.out.print("&");
							break;
						default:
							System.out.print("$");
						}
					} else {
						System.out.print(" ");
					}
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		ThunderProperties properties = ThunderPropertiesManager.getProperties();

		CacheContainer cacheContainer = new CacheContainer();
		ExecutorContainer executorContainer = new ExecutorContainer();

		ThunderDelegate delegate = new ThunderDelegateImpl();
		delegate.setProperties(properties);
		delegate.setCacheContainer(cacheContainer);
		delegate.setExecutorContainer(executorContainer);

		registerBeanDefinitionParser(ThunderConstants.APPLICATION_ELEMENT_NAME,
				new ApplicationBeanDefinitionParser(delegate));
		registerBeanDefinitionParser(ThunderConstants.PROTOCOL_ELEMENT_NAME,
				new ProtocolBeanDefinitionParser(delegate));
		registerBeanDefinitionParser(ThunderConstants.REGISTRY_ELEMENT_NAME,
				new RegistryBeanDefinitionParser(delegate));
		registerBeanDefinitionParser(ThunderConstants.STRATEGY_ELEMENT_NAME,
				new StrategyBeanDefinitionParser(delegate));
		registerBeanDefinitionParser(ThunderConstants.MONITOR_ELEMENT_NAME,
				new MonitorBeanDefinitionParser(delegate));
		registerBeanDefinitionParser(ThunderConstants.SERVICE_ELEMENT_NAME,
				new ServiceBeanDefinitionParser(delegate));
		registerBeanDefinitionParser(ThunderConstants.REFERENCE_ELEMENT_NAME,
				new ReferenceBeanDefinitionParser(delegate));
		registerBeanDefinitionParser(ThunderConstants.METHOD_ELEMENT_NAME,
				new MethodBeanDefinitionParser(delegate));
	}
}