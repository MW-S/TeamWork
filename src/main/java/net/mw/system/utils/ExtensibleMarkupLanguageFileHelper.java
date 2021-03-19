package net.mw.system.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;


/**
 * xml配置文件工具类.
 * 
 * @author 侯骏雄
 * @since 6.0.0
 */
public final class ExtensibleMarkupLanguageFileHelper {
	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(ExtensibleMarkupLanguageFileHelper.class.getName());
	/**
	 * tomcat所使用的classpath与java的classpath集合.
	 */
	private static Set<String> classPaths = new HashSet<String>();

	/**
	 * 私有构造方法，其作用是为了防止用户显式生成工具类的实例对象.
	 * 
	 */
	private ExtensibleMarkupLanguageFileHelper() {
	}

	/**
	 * 读取xml配置文件.
	 * 
	 * @param fileName 用于获取xml配置文件的文件名fileName,如果为null则返回null.
	 * @return xml配置文件，如果文件不存在则返回null.
	 * @throws DocumentException 读取xml异常.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	public static Document loadExtensibleMarkupLanguageFile(final String fileName) throws DocumentException {
		logger.trace("进入loadXML方法");
		Document result = null;
		ClassPathResource classPathResource = new ClassPathResource(fileName);
		try {
			InputStream is = classPathResource.getInputStream();
			SAXReader reader = new SAXReader();
			result = reader.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.trace("退出loadXML方法");
		return result;
	}

	/**
	 * 根据标签名获取元素列表.
	 * 
	 * @param xml     xml配置文件,不得为null.
	 * @param tagName 用于获取元素的标签名tagName,如果为null则返回null.
	 * @return 与tagName对应的元素列表，如果元素不存在则返回null.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	public static List<Element> getElementsByTagName(final Document xml, final String tagName) {
		logger.trace("进入getElementsByTagName方法");
		if (xml == null) {
			logger.error("参数xml不得为null.");
			throw new IllegalArgumentException("The xml must not be null");
		}

		List<Element> result = null;
		if (tagName != null) {
			result = xml.getRootElement().elements(tagName);
			if (result.size() == 0) {
				result = null;
			}
		}
		logger.trace("退出getElementsByTagName方法");
		return result;
	}

	/**
	 * 以字符串类型获取子元素内容.
	 * 
	 * @param element      父元素，不得为null.
	 * @param childTagName 用于获取子元素的标签名childTagName,如果为null则返回null.
	 * @return 子元素的内容，如果元素不存在则返回null.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	public static String getChildTextToString(final Element element, final String childTagName) {
		logger.trace("进入getChildTextToString方法");
		String result = getChildTextToString(element, childTagName, null);
		logger.trace("退出getChildTextToString方法");
		return result;
	}

	/**
	 * 以字符串类型获取子元素内容.
	 * 
	 * @param element       父元素，不得为null.
	 * @param childTagName  用于获取子元素的标签名childTagName,如果为null则返回默认值defaultText.
	 * @param defaultString 默认值，允许为null.
	 * @return 子元素的内容，如果元素不存在则返回默认值defaultText.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	public static String getChildTextToString(final Element element, final String childTagName,
			final String defaultString) {
		logger.trace("进入getChildTextToString方法");
		if (element == null) {
			logger.error("参数element不得为null.");
			throw new IllegalArgumentException("The element must not be null");
		}

		String result = null;
		if (childTagName != null) {
			Element child = element.element(childTagName);
			if (child != null) {
				result = child.getText();
			}
		}
		if (result == null || "".equals(result)) {
			result = defaultString;
		}
		logger.trace("退出getChildTextToString方法");
		return result;
	}

	/**
	 * 以整型类型获取子元素内容.
	 * 
	 * @param element      父元素，不得为null.
	 * @param childTagName 用于获取子元素的标签名childTagName,如果为null则返回null.
	 * @return 子元素的内容，如果元素不存在则返回null.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	public static Integer getChildTextToInteger(final Element element, final String childTagName) {
		logger.trace("进入getChildTextToInteger方法");
		Integer result = getChildTextToInteger(element, childTagName, null);
		logger.trace("退出getChildTextToInteger方法");
		return result;
	}

	/**
	 * 以整型类型获取子元素内容.
	 * 
	 * @param element        父元素，不得为null.
	 * @param childTagName   用于获取子元素的标签名childTagName,如果为null则返回默认值defaultText.
	 * @param defaultInteger 默认值，允许为null.
	 * @return 子元素的内容，如果元素不存在则返回默认值defaultText.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	public static Integer getChildTextToInteger(final Element element, final String childTagName,
			final Integer defaultInteger) {
		logger.trace("进入getChildTextToInteger方法");
		if (element == null) {
			logger.error("参数element不得为null.");
			throw new IllegalArgumentException("The element must not be null");
		}

		String content = null;
		Integer result = null;
		if (childTagName != null) {
			Element child = element.element(childTagName);
			if (child != null) {
				content = child.getText();
			}
		}
		if (content == null || "".equals(content)) {
			result = defaultInteger;
		} else {
			result = Integer.valueOf(content);
		}
		logger.trace("退出getChildTextToInteger方法");
		return result;
	}

	/**
	 * 以布尔类型获取子元素内容.
	 * 
	 * @param element      父元素，不得为null.
	 * @param childTagName 用于获取子元素的标签名childTagName,如果为null则返回null.
	 * @return 子元素的内容，如果元素不存在则返回null.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	public static Boolean getChildTextToBoolean(final Element element, final String childTagName) {
		logger.trace("进入getChildTextToBoolean方法");
		Boolean result = getChildTextToBoolean(element, childTagName, null);
		logger.trace("退出getChildTextToBoolean方法");
		return result;
	}

	/**
	 * 以布尔类型获取子元素内容.
	 * 
	 * @param element        父元素，不得为null.
	 * @param childTagName   用于获取子元素的标签名childTagName,如果为null则返回默认值defaultText.
	 * @param defaultBoolean 默认值，允许为null.
	 * @return 子元素的内容，如果元素不存在则返回默认值defaultText.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	public static Boolean getChildTextToBoolean(final Element element, final String childTagName,
			final Boolean defaultBoolean) {
		logger.trace("进入getChildTextToBoolean方法");
		if (element == null) {
			logger.error("参数element不得为null.");
			throw new IllegalArgumentException("The element must not be null");
		}

		String content = null;
		Boolean result = null;
		if (childTagName != null) {
			Element child = element.element(childTagName);
			if (child != null) {
				content = child.getText();
			}
		}
		if (content == null || "".equals(content)) {
			result = defaultBoolean;
		} else {
			result = Boolean.valueOf(content);
		}
		logger.trace("退出getChildTextToBoolean方法");
		return result;
	}

	/**
	 * 以长整型类型获取子元素内容.
	 * 
	 * @param element      父元素，不得为null.
	 * @param childTagName 用于获取子元素的标签名childTagName,如果为null则返回null.
	 * @return 子元素的内容，如果元素不存在则返回null.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	public static Long getChildTextToLong(final Element element, final String childTagName) {
		logger.trace("进入getChildTextToLong方法");
		Long result = getChildTextToLong(element, childTagName, null);
		logger.trace("退出getChildTextToLong方法");
		return result;
	}

	/**
	 * 以长整型类型获取子元素内容.
	 * 
	 * @param element      父元素，不得为null.
	 * @param childTagName 用于获取子元素的标签名childTagName,如果为null则返回默认值defaultText.
	 * @param defaultLong  默认值，允许为null.
	 * @return 子元素的内容，如果元素不存在则返回默认值defaultText.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	public static Long getChildTextToLong(final Element element, final String childTagName, final Long defaultLong) {
		logger.trace("进入getChildTextToLong方法");
		if (element == null) {
			logger.error("参数element不得为null.");
			throw new IllegalArgumentException("The element must not be null");
		}

		String content = null;
		Long result = null;
		if (childTagName != null) {
			Element child = element.element(childTagName);
			if (child != null) {
				content = child.getText();
			}
		}
		if (content == null || "".equals(content)) {
			result = defaultLong;
		} else {
			result = Long.valueOf(content);
		}
		logger.trace("退出getChildTextToLong方法");
		return result;
	}
}
