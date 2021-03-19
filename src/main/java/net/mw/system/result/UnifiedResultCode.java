package net.mw.system.result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import net.mw.system.utils.ExtensibleMarkupLanguageFileHelper;




/**
 * 统一结果编码.
 * 
 * @author 侯骏雄
 * @since 6.0.0
 */
public final class UnifiedResultCode {
	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(UnifiedResultCode.class.getName());

	/**
	 * 统一结果编码列表.
	 */
	private static Map<String, Long> unifiedResultCodeList;

	static {
		Document xml = null;
		String tagName = "unifiedresultcode";
		List<Element> list = null;
		try {
			xml = ExtensibleMarkupLanguageFileHelper.loadExtensibleMarkupLanguageFile("unifiedresultcode.xml");
			list = ExtensibleMarkupLanguageFileHelper.getElementsByTagName(xml, tagName);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		unifiedResultCodeList = new HashMap<String, Long>();
		String info = null;
		Long code = null;
		for (Element element : list) {
			info = ExtensibleMarkupLanguageFileHelper.getChildTextToString(element, "info");
			code = ExtensibleMarkupLanguageFileHelper.getChildTextToLong(element, "code");
			try {
				addNewCode(code, info);
			} catch (Exception e) {
				logger.error("统一结果编码初始化失败");
				e.printStackTrace();
			}
		}

	}

	/**
	 * 私有构造方法.
	 * 
	 */
	private UnifiedResultCode() {
	}

	/**
	 * 添加新编码.
	 * 
	 * @param code 待添加的统一结果编码.
	 * @param info 与该统一结果编码对应的信息，不可为null，且不可与现有编码的信息相同.
	 * @return true 添加成功. false 该info或该value已存在于系统中，添加失败.
	 * @throws Exception IllegalArgumentException info为null时抛出此异常.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	public static boolean addNewCode(final long code, final String info) throws Exception {
		logger.trace("进入addNewCode方法");
		boolean result = true;
		if (info == null) {
			logger.error("参数info不得为null.");
			throw new IllegalArgumentException("The info must not be null");
		}

		boolean isInfoExist = unifiedResultCodeList.containsKey(info);
		if (isInfoExist) {
			logger.error("map中已存在参数info为key的编码");
			result = false;
		}

		boolean isCodeExist = unifiedResultCodeList.containsValue(code);
		if (isCodeExist) {
			logger.error("map中已存在参数code为value的编码");
			result = false;
		}
		if (result) {
			unifiedResultCodeList.put(info, code);
		}
		logger.trace("退出addNewCode方法");
		return result;
	}

	/**
	 * 根据统一结果编码对应的信息获取编码.
	 * 
	 * @param info 统一结果编码对应的信息，不可为null.
	 * @return 与该信息对应的统一结果编码，不存在该编码时返回null.
	 * @throws Exception IllegalArgumentException info为null时抛出此异常.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	public static Long getCode(final String info) {
		logger.trace("进入getCode方法");
		Long result = null;
		if (info == null) {
			logger.error("参数info不得为null.");
			throw new IllegalArgumentException("The info must not be null");
		}

		result = unifiedResultCodeList.get(info);
		logger.trace("退出getCode方法");
		return result;
	}
}
