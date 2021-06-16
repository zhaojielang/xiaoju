package com.chetong.doc.builder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.beetl.core.Template;

import com.chetong.doc.model.ApiConfig;
import com.chetong.doc.model.ApiDoc;
import com.chetong.doc.model.ApiResultCode;
import com.chetong.doc.utils.BeetlTemplateUtil;

/**
 * 文档构建工具
 * 
 * @author 罗乔
 * @time 2021年6月16日 上午9:12:39
 */
public class ApiDocBuilder {
	
    /**
     * 构建文档
     * 
     * @param config
     * @return
     * @author 罗乔
     * @time 2021年6月16日 上午9:17:02
     */
    public static List<ApiDoc> builderApiDcs(ApiConfig config) {
    	ExecutorService threadPool = Executors.newCachedThreadPool();
    	Map<String, Integer> counterMap = new ConcurrentHashMap<>();
    	try {
    		if (null == config) {
    			throw new NullPointerException("ApiConfig can't be null");
    		}
    		
    		SourceBuilder sourceBuilder = new SourceBuilder(config, threadPool, counterMap);
    		List<ApiDoc> apiDocList = new ArrayList<>();
    		List<ApiResultCode> enumDocList = new ArrayList<>();
    		List<Future<Object>> futures = sourceBuilder.getApiDocData();
    		for (Future<Object> future : futures) {
    			Object docObj = future.get();
    			if (docObj instanceof ApiDoc) {
    				apiDocList.add((ApiDoc) docObj);
    			} else if (docObj instanceof ApiResultCode) {
    				enumDocList.add((ApiResultCode) docObj);
    			}
    		}
    		
    		//重新排序
    		apiDocList.sort(Comparator.comparing(ApiDoc::getIndex));
    		
    		ApiDoc enumCodeDoc = new ApiDoc();
    		enumCodeDoc.setName("enumCodeDoc");
    		enumCodeDoc.setDesc("3 枚举列表");
    		enumCodeDoc.setApiResultCodes(enumDocList);
    		apiDocList.add(0, enumCodeDoc);
    		//生成格式文档
    		buildApiDoc(apiDocList);
    		//公共字段
    		ApiDoc commonReqFieldDoc = buildCommonReqFieldDoc();
    		apiDocList.add(0, commonReqFieldDoc);
    		//使用说明
    		ApiDoc explainDoc = buildExplainDoc(String.format("Controller：%s Service：%s Enum：%s ", counterMap.get(SourceBuilder.CONTROLLER_COUNT_KEY), counterMap.get(SourceBuilder.SERVICE_COUNT_KEY), counterMap.get(SourceBuilder.ENUM_COUNT_KEY)));
    		apiDocList.add(0, explainDoc);
    		counterMap.clear();
    		return apiDocList;
		} catch (InterruptedException|ExecutionException e) {
			throw new RuntimeException(e);
		} finally {
			threadPool.shutdown();
		}
    }

    /**
     * 公共生成service api 文档
     *
     * @param apiDocList
     * @param outPath
     */
    private static void buildApiDoc(List<ApiDoc> apiDocList) {
    	String serviceTemplate = "ServiceApiDoc.btl";
    	String controllerTemplate = "ControllerApiDoc.btl";
    	String enumTemplate = "EnumCodeList.btl";
    	String bindList = "list";
    	String descKey = "desc";
    	String nameKey = "name";
        for (ApiDoc doc : apiDocList) {
        	Template mapper;
        	if (doc.getServiceApiDocs() != null) {
        		mapper = BeetlTemplateUtil.getByName(serviceTemplate);
        		mapper.binding(bindList, doc.getServiceApiDocs());
			} else if(doc.getControllerApiDocs() != null) {
				mapper = BeetlTemplateUtil.getByName(controllerTemplate);
				mapper.binding(bindList, doc.getControllerApiDocs());
			} else {
				mapper = BeetlTemplateUtil.getByName(enumTemplate);
				mapper.binding(bindList, doc.getApiResultCodes());
			}
            mapper.binding(descKey, doc.getDesc());
            mapper.binding(nameKey, doc.getName());
            doc.setApiDocContent(mapper.render());
        }
    }
    
    /**
     * 文档使用说明
     *
     * @param outPath
     */
    private static ApiDoc buildExplainDoc(String apiDocContent) {
    	ApiDoc apiDoc = new ApiDoc();
    	apiDoc.setName("explain");
    	apiDoc.setDesc("1.文档使用说明#"+apiDocContent);
    	Template mapper = BeetlTemplateUtil.getByName("Explain.btl");
    	apiDoc.setApiDocContent(mapper.render());
    	return apiDoc;
    }

    /**
     * 通用字段
     *
     * @param outPath
     */
    private static ApiDoc buildCommonReqFieldDoc() {
    	ApiDoc apiDoc = new ApiDoc();
    	apiDoc.setName("commonReqField");
    	apiDoc.setDesc("2.公共请求参数字段");
    	Template mapper = BeetlTemplateUtil.getByName("CommonReqField.btl");
    	apiDoc.setApiDocContent(mapper.render());
    	return apiDoc;
    }
}
