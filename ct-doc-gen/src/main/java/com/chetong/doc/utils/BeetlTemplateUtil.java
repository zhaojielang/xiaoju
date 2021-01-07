package com.chetong.doc.utils;

import java.io.Closeable;
import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import com.chetong.doc.exception.ProcessCodeEnum;

/**
 * 获取模板工具
 */
public class BeetlTemplateUtil {

	public static Template getByName(Object templateName) {
		ClasspathResourceLoader resourceLoader = null;
		try {
			resourceLoader = new ClasspathResourceLoader("/template/");
			Configuration cfg = Configuration.defaultConfiguration();
			GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
			return gt.getTemplate(templateName);
		} catch (IOException e) {
			throw ProcessCodeEnum.FAIL.buildProcessException("获取模板异常",e);
		} finally {
			close(resourceLoader);
		}
	}
	
	public static void close(Closeable x) {
        if (x != null) {
            try {
                x.close();
            } catch (Exception e) {
                // skip
            }
        }
    }
}
