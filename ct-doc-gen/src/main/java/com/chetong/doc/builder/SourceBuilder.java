package com.chetong.doc.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.chetong.doc.constants.GlobalConstants;
import com.chetong.doc.constants.IgnoreFields;
import com.chetong.doc.constants.ParamRequiredField;
import com.chetong.doc.model.ApiConfig;
import com.chetong.doc.model.ApiDoc;
import com.chetong.doc.model.ApiDocContent;
import com.chetong.doc.model.ApiReqHeader;
import com.chetong.doc.model.ApiResultCode;
import com.chetong.doc.model.SourcePath;
import com.chetong.doc.utils.CollectionUtil;
import com.chetong.doc.utils.DocClassUtil;
import com.chetong.doc.utils.DocUtil;
import com.chetong.doc.utils.JsonFormatUtil;
import com.chetong.doc.utils.StringUtil;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.JavaType;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import com.thoughtworks.qdox.model.expression.Expression;

/**
 * 源码加载解析
 * 
 * @author 罗乔
 * @time 2019-4-2 09:02:29
 */
public class SourceBuilder {
	
	private static final String CONTENT_TYPE = "application/json; charset=utf-8";
	private static final String METHOD_TYPE = "post";
	private static final String NO_COMENT_DESC = "No comments found.";
	private static final String NO_PARAM_NAME = "no param name";
	private static final String PARAM_NAME = "param";
	private static final String RETURN_NAME = "return";
	private static final String PARAM_NAME_JSON_NAME = "data";
	private static final String IS_REQUIRED_FALSE = "false";
	private static final String BLANK_SPACE_4 = "&nbsp;&nbsp;&nbsp;&nbsp;";
	private static final String DEFAULT_JSON_TYPE = "java.lang.Object";
	private static final String MAP_KEY= "mapKey";
	private static final String ANNOTATION_VALUE_KEY = "value";
	private static final String MODIFIER_PRIVATE = "private";
	private static final String MODIFIER_PUBLIC = "public";
	private static final String LINE_BREAK_KEY = "\n";
	private static final String NEW_LINE_BREAK = "\r|\n";
	private static final String CONNECTOR = ".";
	private static final String COMMA = ",";
	private static final String LEFT_CURLY_BRACKETS = "{";
	private static final String RIGHT_CURLY_BRACKETS = "}";
	private static final String LEFT_PARENTHESES = "(";
	private static final String RIGHT_PARENTHESES = ")";
	private static final String LEFT_BRACKETS = "[";
	private static final String RIGHT_BRACKETS = "]";
	private static final String DOUBLE_QUOTATION  = "\"";
	private static final String SLASH  = "/";
	private static final String BACK_SLASH  = "\\";
	private static final String VERTICAL_SEPARATORS = "|";
	private static final String COLON = ":";
	private static final String EMPTY_STR = "";
	private static final String SPACE_STR = " ";
	
	/**扫描class文件总数Key*/
	public static final String SCAN_CLASS_COUNT_KEY = "scanClassCounter";
	/**Controller总数Key*/
	public static final String CONTROLLER_COUNT_KEY = "controllerCounter";
	/**Service总数Key*/
	public static final String SERVICE_COUNT_KEY = "serviceCounter";
	/**Enum总数Key*/
	public static final String ENUM_COUNT_KEY = "enumCounter";

	private JavaProjectBuilder builder;
	private List<JavaClass> javaClasses = new ArrayList<>();
	private List<ApiReqHeader> headers;
	private String basePathUrl;
	private String routeReqUrl;
	private ExecutorService threadPool;
	private Map<String, Integer> counterMap;
	
	public SourceBuilder(ApiConfig config, ExecutorService threadPool, Map<String, Integer> counterMap) {
		this.threadPool = threadPool;
		this.counterMap = counterMap;
		this.basePathUrl = config.getBasePathUrl();
		loadJavaFiles(config);
		this.headers = config.getRequestHeaders();
		this.routeReqUrl = this.basePathUrl + "/**/*.do";
	}

	/**
	 * 加载项目的源代码文件
	 */
	private void loadJavaFiles(ApiConfig config) {
		List<SourcePath> paths = config.getSourcePaths();
		this.builder = new JavaProjectBuilder();
		for (SourcePath path : paths) {
			String strPath = path.getPath();
			strPath = strPath.replace(BACK_SLASH, SLASH);
			builder.addSourceTree(new File(strPath));
		}
		this.javaClasses.addAll(builder.getClasses());
		counterMap.put(SCAN_CLASS_COUNT_KEY, javaClasses.size());
	}

	public List<Future<Object>> getApiDocData() {
		List<Future<Object>> futures = new ArrayList<>();
		counterMap.put(CONTROLLER_COUNT_KEY, 0);
		counterMap.put(SERVICE_COUNT_KEY, 0);
		counterMap.put(ENUM_COUNT_KEY, 0);
		
		// 服务序号
		int docIndex = 4;
		// 枚举序号
		int enumIndex = 1;
		for (int i=0; i<javaClasses.size();i++) {
			JavaClass cls = javaClasses.get(i);
			String clsComment = cls.getComment();
			if (StringUtil.isEmpty(clsComment)) {
				continue;
			}
			
			if (checkService(cls)) {
				final int index = docIndex;
				docIndex = docIndex +1;
				
				Future<Object> future = threadPool.submit(new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						String serviceNameValue = null;
						List<JavaAnnotation> classAnnotations = cls.getAnnotations();
						for (JavaAnnotation annotation : classAnnotations) {
							String annotationName = annotation.getType().getFullyQualifiedName();
							if (GlobalConstants.SERVICE_ANNOTATION.containsKey(annotationName)) {
								serviceNameValue = annotation.getNamedParameter(ANNOTATION_VALUE_KEY).toString();
								serviceNameValue = serviceNameValue.replaceAll(DOUBLE_QUOTATION, EMPTY_STR);
								break;
							}
						}
						
						if (serviceNameValue == null) {
							return null;
						}
						
						List<ApiDocContent> apiMethodDocs = buildServiceMethod(index, cls, serviceNameValue);
						ApiDoc apiDoc = new ApiDoc();
						apiDoc.setIndex(index);
						apiDoc.setName(serviceNameValue);
						apiDoc.setDesc(index+SPACE_STR+clsComment);
						apiDoc.setServiceApiDocs(apiMethodDocs);
						return apiDoc;
					}
				});
				futures.add(future);
			} else if (checkController(cls)) {
				final int index = docIndex;
				docIndex = docIndex +1;
				Future<Object> future = threadPool.submit(new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						List<JavaAnnotation> classAnnotations = cls.getAnnotations();
						StringBuilder baseReqPath = new StringBuilder(basePathUrl);
						for (JavaAnnotation annotation : classAnnotations) {
							String annotationName = annotation.getType().getFullyQualifiedName();
							if (GlobalConstants.REQUEST_MAPPING_ANNOTATION.containsKey(annotationName)) {
								String controllerReqPath = annotation.getNamedParameter(ANNOTATION_VALUE_KEY).toString().replaceAll(DOUBLE_QUOTATION, EMPTY_STR);
								if (!controllerReqPath.startsWith(SLASH)) {
									baseReqPath.append(SLASH);
								}
								baseReqPath.append(controllerReqPath);
								break;
							}
						}
						
						List<ApiDocContent> apiMethodDocs = buildControllerMethod(index, cls, baseReqPath);
						ApiDoc apiDoc = new ApiDoc();
						apiDoc.setIndex(index);
						apiDoc.setName(cls.getName());
						apiDoc.setDesc(index+SPACE_STR+clsComment);
						apiDoc.setControllerApiDocs(apiMethodDocs);
						return apiDoc;
					}
				});
				futures.add(future);
			}else if (checkEnum(cls)) {
				List<JavaField> fields = cls.getFields();
				if (CollectionUtil.isNotEmpty(fields)) {
					List<Expression> enumConstantArguments = fields.get(0).getEnumConstantArguments();
					if (enumConstantArguments !=null && enumConstantArguments.size()>=2) {
						final int index = enumIndex;
						enumIndex = enumIndex +1;
						Future<Object> future = threadPool.submit(new Callable<Object>() {
							@Override
							public Object call() throws Exception {
								ApiResultCode resultCode = new ApiResultCode();
								resultCode.setName(cls.getName());
								resultCode.setDesc(3+CONNECTOR+index+SPACE_STR+clsComment);
								StringBuilder codeDesc = new StringBuilder();
								for (JavaField javaField : fields) {
									List<Expression> arguments = javaField.getEnumConstantArguments();
									if (!javaField.getModifiers().contains(MODIFIER_PRIVATE)
											&& CollectionUtil.isNotEmpty(arguments)) {
										codeDesc.append(arguments.get(0));
										codeDesc.append(VERTICAL_SEPARATORS);
										codeDesc.append(arguments.get(1));
										codeDesc.append(LINE_BREAK_KEY);
									}
								}
								resultCode.setCodeDesc(codeDesc.toString().replaceAll(DOUBLE_QUOTATION, EMPTY_STR));
								//添加计数
								counterMap.put(ENUM_COUNT_KEY, counterMap.get(ENUM_COUNT_KEY)+1);
								return resultCode;
							}
						});
						futures.add(future);
					}
				}
			}
		}
		return futures;
	}
	
	/**
	 * 检测枚举
	 *
	 * @param cls
	 * @return
	 */
	private boolean checkEnum(JavaClass cls) {
		boolean isEnum = false;
		if (cls.isEnum()) {
			List<String> imports = cls.getSource().getImports();
			for (String importEnum : imports) {
				if(GlobalConstants.BASE_ENUM.containsKey(importEnum)) {
					return true;
				}
			}
		}
		return isEnum;
	}
	
	/**
	 * 检测Service上的注解
	 *
	 * @param cls
	 * @return
	 */
	private boolean checkService(JavaClass cls) {
		List<JavaAnnotation> classAnnotations = cls.getAnnotations();
		for (JavaAnnotation annotation : classAnnotations) {
			String annotationName = annotation.getType().getFullyQualifiedName();
			if (GlobalConstants.SERVICE_ANNOTATION.containsKey(annotationName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检测Controller上的注解
	 *
	 * @param cls
	 * @return
	 */
	private boolean checkController(JavaClass cls) {
		List<JavaAnnotation> classAnnotations = cls.getAnnotations();
		for (JavaAnnotation annotation : classAnnotations) {
			String annotationName = annotation.getType().getFullyQualifiedName();
			if (GlobalConstants.CONTROLLER_ANNOTATION.containsKey(annotationName)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<ApiDocContent> buildServiceMethod(int parentIndex, final JavaClass cls, String serviceName) {
		List<JavaClass> interfaces = cls.getInterfaces();
		JavaClass interfaceCls = interfaces.isEmpty() ? null : interfaces.get(0);
		List<JavaMethod> methods = cls.getMethods();
		List<ApiDocContent> methodDocList = new ArrayList<>(methods.size());
		
		int index = 1;
		for (JavaMethod method : methods) {
			// 忽略、私有、没有接口说明不是服务
			if (interfaceCls == null
					|| method.getTagByName(GlobalConstants.IGNORE_TAG) != null
					|| method.getModifiers().contains(MODIFIER_PRIVATE)) {
				continue;
			}

			String methodDocument = method.getComment();
			JavaMethod interfaceJavaMethod =  interfaceCls.getMethodBySignature(method.getName(), method.getParameterTypes());
			// 接口中没有对应方法也说明不是服务
			if (interfaceJavaMethod == null) {
				continue;
			}
			
			if (methodDocument == null) {
				methodDocument = interfaceJavaMethod.getComment();
			}
			
			List<String> isRequireds = new ArrayList<>();
			List<JavaAnnotation> annotations = method.getAnnotations();
			for (JavaAnnotation annotation : annotations) {
				String annotationName = annotation.getType().getFullyQualifiedName();
				if (GlobalConstants.REQUIRED_ANNOTATION.containsKey(annotationName)) {
					AnnotationValue baseVule = annotation.getProperty(ParamRequiredField.BASE);
					if (baseVule != null) {
						isRequireds.addAll((Collection<? extends String>) baseVule.getParameterValue());
					}
					AnnotationValue objValue = annotation.getProperty(ParamRequiredField.OBJ_FIELDS);
					if (objValue != null) {
						isRequireds.addAll((Collection<? extends String>) objValue.getParameterValue());
					}
					AnnotationValue numberValue = annotation.getProperty(ParamRequiredField.NUMBER_FIELDS);
					if (numberValue != null) {
						isRequireds.addAll((Collection<? extends String>) numberValue.getParameterValue());
					}
					AnnotationValue arrayValue = annotation.getProperty(ParamRequiredField.ARRAY_FIELDS);
					if (arrayValue != null) {
						isRequireds.addAll((Collection<? extends String>) arrayValue.getParameterValue());
					}
					AnnotationValue phoneFieldsValue = annotation.getProperty(ParamRequiredField.PHONE_FIELDS);
					if (phoneFieldsValue != null) {
						isRequireds.addAll((Collection<? extends String>) phoneFieldsValue.getParameterValue());
					}
					AnnotationValue emailFieldsValue = annotation.getProperty(ParamRequiredField.EMAIL_FIELDS);
					if (emailFieldsValue != null) {
						isRequireds.addAll((Collection<? extends String>) emailFieldsValue.getParameterValue());
					}
					AnnotationValue idCardFieldsValue = annotation.getProperty(ParamRequiredField.IDCARD_FIELDS);
					if (idCardFieldsValue != null) {
						isRequireds.addAll((Collection<? extends String>) idCardFieldsValue.getParameterValue());
					}
				} else if (GlobalConstants.LOG_TAIL_ANNOTATION.containsKey(annotationName)) {
					String tailDesc = annotation.getNamedParameter(ANNOTATION_VALUE_KEY).toString();
					if (StringUtil.isNotEmpty(tailDesc)) {
						methodDocument = tailDesc.replace(DOUBLE_QUOTATION, EMPTY_STR);
					}
				}
			}
			
			// 接口，本身，以及logTail都没有注释的 不往下走
			if (StringUtil.isEmpty(methodDocument)) {
				continue;
			}
			
			//添加计数
			counterMap.put(SERVICE_COUNT_KEY, counterMap.get(SERVICE_COUNT_KEY)+1);
			
			String requestParams = buildMethodRequest(method, isRequireds);
			String requestJson = buildReqJson(method);
			String responseParams = buildMethodReturn(method, new ArrayList<>());
			String responseJson = buildReturnJson(method);
			
			ApiDocContent apiMethodDoc = new ApiDocContent();
			apiMethodDoc.setDesc(parentIndex+CONNECTOR+index+SPACE_STR+methodDocument);
			apiMethodDoc.setUrl(this.routeReqUrl);
			apiMethodDoc.setHeaders(createHeaders(this.headers));
			apiMethodDoc.setContentType(CONTENT_TYPE);
			apiMethodDoc.setType(METHOD_TYPE);
			apiMethodDoc.setName(method.getName());
			apiMethodDoc.setServiceName(serviceName + CONNECTOR + method.getName());
			apiMethodDoc.setRequestParams(requestParams);
			apiMethodDoc.setRequestUsage(requestJson);
			apiMethodDoc.setResponseParams(responseParams);
			apiMethodDoc.setResponseUsage(responseJson);
			methodDocList.add(apiMethodDoc);
			index++;
		}
		return methodDocList;
	}
	
	public List<ApiDocContent> buildControllerMethod(int parentIndex, final JavaClass cls, StringBuilder controllerReqPath) {
		List<JavaMethod> methods = cls.getMethods();
		List<ApiDocContent> methodDocList = new ArrayList<>(methods.size());
		
		int index = 1;
		for (JavaMethod method : methods) {
			String methodDocument = method.getComment();
			List<String> modifiers = method.getModifiers();
			// 没有修饰符、没有注释，忽略，不生成
			if (modifiers.isEmpty() 
					|| !MODIFIER_PUBLIC.equals(modifiers.get(0))
					|| StringUtil.isEmpty(methodDocument)
					|| null != method.getTagByName(GlobalConstants.IGNORE_TAG)) {
				continue;
			}
			
			//添加计数
			counterMap.put(CONTROLLER_COUNT_KEY, counterMap.get(CONTROLLER_COUNT_KEY)+1);
			
			StringBuilder apiReqUrl = new StringBuilder(controllerReqPath);
			
			List<String> isRequireds = new ArrayList<>();
			List<JavaAnnotation> annotations = method.getAnnotations();
			for (JavaAnnotation annotation : annotations) {
				String annotationName = annotation.getType().getFullyQualifiedName();
				if (GlobalConstants.REQUEST_MAPPING_ANNOTATION.containsKey(annotationName)) {
					String methodReqPath = annotation.getNamedParameter(ANNOTATION_VALUE_KEY).toString().replaceAll(DOUBLE_QUOTATION, EMPTY_STR);
					if (!methodReqPath.startsWith(SLASH)) {
						apiReqUrl.append(SLASH);
					}
					apiReqUrl.append(methodReqPath);
					break;
				}
			}
			
			String requestParams = buildMethodRequest(method, isRequireds);
			String requestJson = buildReqJson(method);
			String responseParams = buildMethodReturn(method, new ArrayList<>());
			String responseJson = buildReturnJson(method);
			
			ApiDocContent apiDocContent = new ApiDocContent();
			apiDocContent.setDesc(parentIndex+CONNECTOR+index+SPACE_STR+methodDocument);
			apiDocContent.setUrl(apiReqUrl.toString());
			apiDocContent.setHeaders(createHeaders(this.headers));
			apiDocContent.setType(METHOD_TYPE);
			apiDocContent.setName(method.getName());
			apiDocContent.setRequestParams(requestParams);
			apiDocContent.setRequestUsage(requestJson);
			apiDocContent.setResponseParams(responseParams);
			apiDocContent.setResponseUsage(responseJson);
			methodDocList.add(apiDocContent);
			index++;
		}
		return methodDocList;
	}

	private String createHeaders(List<ApiReqHeader> headers) {
		StringBuilder sb = new StringBuilder();
		if (CollectionUtil.isEmpty(headers)) {
			headers = new ArrayList<>(0);
		}
		for (ApiReqHeader header : headers) {
			sb.append(header.getName()).append(VERTICAL_SEPARATORS);
			sb.append(header.getType()).append(VERTICAL_SEPARATORS);
			sb.append(header.getDesc()).append(LINE_BREAK_KEY);
		}
		return sb.toString();
	}
	
	private String buildMethodRequest(JavaMethod javaMethod, List<String> requiredFields) {
		//获取参数注释
		Map<String, String> paramTagMap = getDocletTagMap(javaMethod, PARAM_NAME);
		List<JavaParameter> parameterList = javaMethod.getParameters();
		if (parameterList.isEmpty()) {
			return "";
		}
		StringBuilder docContent = new StringBuilder();
		for (JavaParameter parameter : parameterList) {
			String paramName = parameter.getName();
			JavaType javaType = parameter.getType();
			String fullTypeName = javaType.getFullyQualifiedName();
			String globGicName = javaType.getGenericCanonicalName();
			if (!DocClassUtil.isMvcIgnoreParams(fullTypeName)) {
				String comment = paramTagMap.get(paramName);
				if (StringUtil.isEmpty(comment)) {
					comment = NO_COMENT_DESC;
				}
				
				if (DocClassUtil.isPrimitive(fullTypeName)) {
					docContent.append(paramName).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(fullTypeName)).append(VERTICAL_SEPARATORS).append(comment).append(VERTICAL_SEPARATORS).append(IS_REQUIRED_FALSE).append(LINE_BREAK_KEY);
				} else if (DocClassUtil.isCollection(fullTypeName)) {
					String[] gicNames = DocClassUtil.getGicName(globGicName);
					String gicName = gicNames[0];
					docContent.append(paramName).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(fullTypeName)+LEFT_PARENTHESES+DocClassUtil.getSimpleName(gicName)+RIGHT_PARENTHESES).append(VERTICAL_SEPARATORS).append(comment).append(VERTICAL_SEPARATORS).append(IS_REQUIRED_FALSE).append(LINE_BREAK_KEY);
					if (!DocClassUtil.isPrimitive(gicName)) {
						buildParams(gicName, 1, 0, requiredFields, EMPTY_STR, docContent);
					}
				} else if(DocClassUtil.isArray(fullTypeName)) {
					docContent.append(paramName).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(fullTypeName)).append(VERTICAL_SEPARATORS).append(comment).append(VERTICAL_SEPARATORS).append(IS_REQUIRED_FALSE).append(LINE_BREAK_KEY);
					String gicName = DocClassUtil.getArraySimpleName(fullTypeName);
					if (!DocClassUtil.isPrimitive(gicName)) {
						buildParams(gicName, 1, 0, requiredFields, EMPTY_STR, docContent);
					}
				} else if (DocClassUtil.isMap(fullTypeName)) {
					docContent.append(paramName).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(fullTypeName)).append(VERTICAL_SEPARATORS).append(comment).append(VERTICAL_SEPARATORS).append(IS_REQUIRED_FALSE).append(LINE_BREAK_KEY);
					String[] gicValTypes = DocClassUtil.getGicName(globGicName);
					docContent.append(MAP_KEY).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(gicValTypes[0])).append(VERTICAL_SEPARATORS).append(NO_COMENT_DESC).append(VERTICAL_SEPARATORS).append(IS_REQUIRED_FALSE).append(LINE_BREAK_KEY);
					if (gicValTypes.length == 2 && !DocClassUtil.isPrimitive(gicValTypes[1])) {
						buildParams(gicValTypes[1], 2, 0, requiredFields, EMPTY_STR, docContent);
					}
				} else {
					buildParams(globGicName, 0, 0, requiredFields, EMPTY_STR, docContent);
				}
			}
		}
		return docContent.toString();
	}

	private Map<String, String> getDocletTagMap(JavaMethod javaMethod, String tagName) {
		List<DocletTag> paramTags = javaMethod.getTagsByName(tagName);
		Map<String, String> paramTagMap = new HashMap<>();
		for (DocletTag docletTag : paramTags) {
			String value = docletTag.getValue();
			String pName;
			String pValue;
			value = value.replaceAll(LINE_BREAK_KEY,EMPTY_STR);
			String[] values = value.split(SPACE_STR);
			pName = values[0];
			if (values.length>1) {
				pValue = values[1];
			} else {
				pValue = NO_COMENT_DESC;
			}
			paramTagMap.put(pName, pValue);
		}
		return paramTagMap;
	}

	private String buildMethodReturn(JavaMethod javaMethod, List<String> requiredFields) {
		StringBuilder docContent = new StringBuilder();
		JavaType javaType = javaMethod.getReturnType();
		String fullTypeName = javaType.getFullyQualifiedName();
		String globGicName = javaType.getGenericCanonicalName();
		if (!DocClassUtil.isMvcIgnoreParams(fullTypeName)) {
			List<DocletTag> docletTags = javaMethod.getTagsByName(RETURN_NAME);
			String comment = null;
			if (!docletTags.isEmpty() && StringUtil.isEmpty(docletTags.get(0))) {
				comment = docletTags.get(0).getValue();
				comment = comment.replaceAll(LINE_BREAK_KEY,EMPTY_STR).substring(comment.lastIndexOf(SPACE_STR));
			} else {
				comment = NO_COMENT_DESC;
			}
			
			if ("void".equals(fullTypeName)) {
				docContent.append(NO_PARAM_NAME).append(VERTICAL_SEPARATORS).append("null").append(VERTICAL_SEPARATORS).append(comment).append(VERTICAL_SEPARATORS).append(IS_REQUIRED_FALSE).append(LINE_BREAK_KEY);
			} else if (DocClassUtil.isPrimitive(fullTypeName)) {
				docContent.append(NO_PARAM_NAME).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(fullTypeName)).append(VERTICAL_SEPARATORS).append(comment).append(VERTICAL_SEPARATORS).append(IS_REQUIRED_FALSE).append(LINE_BREAK_KEY);
			} else if (DocClassUtil.isCollection(fullTypeName)) {
				String[] gicNames = DocClassUtil.getGicName(globGicName);
				String gicName = gicNames[0];
				docContent.append(NO_PARAM_NAME).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(fullTypeName)+LEFT_PARENTHESES+DocClassUtil.getSimpleName(gicName)+RIGHT_PARENTHESES).append(VERTICAL_SEPARATORS).append(comment).append(VERTICAL_SEPARATORS).append(IS_REQUIRED_FALSE).append(LINE_BREAK_KEY);
				if (!DocClassUtil.isPrimitive(gicName)) {
					buildParams(gicName, 0, 0, requiredFields, EMPTY_STR, docContent);
				}
			} else if(DocClassUtil.isArray(fullTypeName)) {
				docContent.append(NO_PARAM_NAME).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(fullTypeName)).append(VERTICAL_SEPARATORS).append(comment).append(VERTICAL_SEPARATORS).append(IS_REQUIRED_FALSE).append(LINE_BREAK_KEY);
				String gicName = DocClassUtil.getArraySimpleName(fullTypeName);
				if (!DocClassUtil.isPrimitive(gicName)) {
					buildParams(gicName, 0, 0, requiredFields, EMPTY_STR, docContent);
				}
			} else if (DocClassUtil.isMap(fullTypeName)) {
				docContent.append(NO_PARAM_NAME).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(fullTypeName)).append(VERTICAL_SEPARATORS).append(comment).append(VERTICAL_SEPARATORS).append(IS_REQUIRED_FALSE).append(LINE_BREAK_KEY);
				String[] gicNames = DocClassUtil.getGicName(globGicName);
				docContent.append(MAP_KEY).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(gicNames[0])).append(VERTICAL_SEPARATORS).append(NO_COMENT_DESC).append(VERTICAL_SEPARATORS).append(IS_REQUIRED_FALSE).append(LINE_BREAK_KEY);
				if (gicNames.length == 2 && !DocClassUtil.isPrimitive(gicNames[1])) {
					buildParams(gicNames[1], 1, 0, requiredFields, EMPTY_STR, docContent);
				}
			} else {
				buildParams(globGicName, 0, 0, requiredFields, EMPTY_STR, docContent);
			}
		}
		return docContent.toString();
	}
	
	public void buildParams(String className, int tabCount, int selfCount, List<String> requiredFields, String parentFieldName, StringBuilder docContent) {
		String globalTypeName = DocClassUtil.getSimpleName(className);
		int i = 0;
		String[] globalGicNames = DocClassUtil.getGicName(className);
		if (DocClassUtil.isPrimitive(globalTypeName)) {
			docContent.append(DocClassUtil.processTypeNameForParams(className));
		} else if (DocClassUtil.isCollection(globalTypeName)) {
			if (globalGicNames.length != 0) {
				String gicName = globalGicNames[0];
				if (gicName.length() == 1) {
					docContent.append(DocClassUtil.processTypeNameForParams(className));
				} else {
					buildParams(gicName, tabCount + 1, selfCount, requiredFields, parentFieldName, docContent);
				}
			} else {
				docContent.append(DocClassUtil.processTypeNameForParams(className));
			}
		} else if (DocClassUtil.isArray(globalTypeName)) {
			docContent.append(DocClassUtil.processTypeNameForParams(className));
		} else if (DocClassUtil.isMap(globalTypeName)) {
			if (globalGicNames.length == 2) {
				buildParams(globalGicNames[1], tabCount + 1, selfCount, requiredFields, parentFieldName, docContent);
			}
		} else {
			JavaClass cls = builder.getClassByName(globalTypeName);
			List<JavaField> fields = getFields(cls);
			out: 
			for (JavaField field : fields) {
				JavaClass declaringClass = field.getDeclaringClass();
				String fieldClassName = declaringClass.getName();
				String fieldClassFullName = declaringClass.getFullyQualifiedName();
				String fieldName = field.getName();
				// 只认ParameterVO参数的Service
				if (!(IgnoreFields.DECLARING_CLASS_NAME.equals(fieldClassName) && !IgnoreFields.PARAM_NAME.equals(fieldName))
						&& !IgnoreFields.SERIAL_VERSION_UID.equals(fieldName)
						&& !GlobalConstants.IGNORE_CLASS.containsKey(fieldClassFullName)) {

					if (PARAM_NAME.equals(fieldName)) {
						fieldName = PARAM_NAME_JSON_NAME;
					}
					
					JavaClass javaType = field.getType();
					String fieldTypeName = javaType.getFullyQualifiedName();
					String fieldGicName = javaType.getGenericCanonicalName();

					List<DocletTag> paramTags = field.getTags();
					for (DocletTag docletTag : paramTags) {
						if (DocClassUtil.isIgnoreTag(docletTag.getName())) {
							continue out;
						}
					}
					
					String strRequired = this.isRequired(fieldName, requiredFields, parentFieldName);
					
					StringBuilder pre = new StringBuilder();
					for (int j = 0; j < tabCount; j++) {
						pre.append(BLANK_SPACE_4);
					}
					
					// cover comment
					String comment = field.getComment();
					StringBuilder fieldTypeNameDesc = new StringBuilder();
					if (DocClassUtil.isCollection(fieldTypeName)) {
						fieldTypeNameDesc.append(DocClassUtil.processTypeNameForParams(fieldTypeName));
						String[] simpleGicName = DocClassUtil.getGicName(fieldGicName);
						fieldTypeNameDesc.append(LEFT_PARENTHESES).append(DocClassUtil.processTypeNameForParams(simpleGicName[0])).append(RIGHT_PARENTHESES); 
					} else if (fieldTypeName.length() == 1) {
						String gicName = globalGicNames[i];
						String simpleName = DocClassUtil.getSimpleName(gicName);
						if (DocClassUtil.isCollection(simpleName)) {
							fieldTypeNameDesc.append(DocClassUtil.processTypeNameForParams(simpleName));
							String[] ggicName = DocClassUtil.getGicName(gicName);
							fieldTypeNameDesc.append(LEFT_PARENTHESES).append(DocClassUtil.processTypeNameForParams(ggicName[0])).append(RIGHT_PARENTHESES); 
						} else {
							fieldTypeNameDesc.append(DocClassUtil.processTypeNameForParams(gicName)); 
						}
					} else {
						fieldTypeNameDesc.append(DocClassUtil.processTypeNameForParams(fieldTypeName));
					}
					
					docContent.append(pre);
					docContent.append(fieldName).append(VERTICAL_SEPARATORS).append(fieldTypeNameDesc).append(VERTICAL_SEPARATORS);
					if (StringUtil.isNotEmpty(comment)) {
						comment = comment.replaceAll(NEW_LINE_BREAK, SPACE_STR);
						docContent.append(comment).append(VERTICAL_SEPARATORS).append(strRequired).append(LINE_BREAK_KEY);
					} else {
						docContent.append(NO_COMENT_DESC).append(VERTICAL_SEPARATORS).append(strRequired).append(LINE_BREAK_KEY);
					}
					
					String nextParentFieldName = StringUtil.isNotEmpty(parentFieldName) || PARAM_NAME_JSON_NAME.equals(fieldName) ? EMPTY_STR : parentFieldName+fieldName+CONNECTOR;
					if (DocClassUtil.isPrimitive(fieldTypeName)) {
						// do nothing
					} else if (DocClassUtil.isMap(fieldTypeName)) {
						docContent.append(pre);
						String[] gicNames = DocClassUtil.getGicName(fieldGicName);
						docContent.append(MAP_KEY).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(gicNames[0])).append(VERTICAL_SEPARATORS).append(NO_COMENT_DESC).append(VERTICAL_SEPARATORS).append(IS_REQUIRED_FALSE).append(LINE_BREAK_KEY);
						if (gicNames.length == 2 && gicNames[1].length() > 1) {
							buildParams(gicNames[1], tabCount + 2, selfCount, requiredFields, nextParentFieldName, docContent);
						}
					} else if (DocClassUtil.isCollection(fieldTypeName)) {
						String[] gicNames = DocClassUtil.getGicName(fieldGicName);
						if (gicNames.length == 1) {
							String gicName = gicNames[0];
							String simpleName = DocClassUtil.getSimpleName(gicName);
							if (globalTypeName.equals(gicName)) {
								if (selfCount > 0 || DocClassUtil.isPrimitive(simpleName) || builder.getClassByName(simpleName).isEnum()) {
									// do nothing
								}else {
									buildParams(gicName, tabCount + 1, selfCount+1, requiredFields, nextParentFieldName, docContent);
								}
							} else {
								buildParams(gicName, tabCount + 1, selfCount, requiredFields, nextParentFieldName, docContent);
							}
						}
					} else if (DocClassUtil.isArray(fieldTypeName)) {
						fieldGicName = DocClassUtil.getArraySimpleName(fieldGicName);
						if (!globalTypeName.equals(fieldGicName) && !DocClassUtil.isPrimitive(fieldGicName)) {
							buildParams(fieldGicName, tabCount + 1, selfCount, requiredFields, nextParentFieldName, docContent);
						}
					} else if (fieldTypeName.length() == 1) {
						if (i < globalGicNames.length) {
							String gicName = globalGicNames[i];
							if (DocClassUtil.isPrimitive(gicName)) {
							} else if (DocClassUtil.isCollection(gicName)) {
								String ggicName = DocClassUtil.getGicName(gicName)[0];
								buildParams(ggicName, tabCount + 1, selfCount, requiredFields, nextParentFieldName, docContent);
							} else if (DocClassUtil.isMap(gicName)) {
								String ggicName = DocClassUtil.getGicName(gicName)[1];
								buildParams(ggicName, tabCount + 1, selfCount, requiredFields, nextParentFieldName, docContent);
							} else if(DocClassUtil.isArray(globalTypeName)) {
								String ggicName = DocClassUtil.getArraySimpleName(gicName);
								buildParams(ggicName, tabCount + 1, selfCount, requiredFields, nextParentFieldName, docContent);
							} else {
								buildParams(gicName, tabCount + 1, selfCount, requiredFields, nextParentFieldName, docContent);
							}
						}
						i++;
					} else if (globalTypeName.equals(fieldTypeName)) {
						if (selfCount == 0) {
							if (DocClassUtil.isPrimitive(fieldTypeName) || builder.getClassByName(fieldTypeName).isEnum()) {
								// do nothing
							} else {
								buildParams(fieldGicName, tabCount + 1, selfCount+1, requiredFields, nextParentFieldName, docContent);
							}
						}
					} else {
						buildParams(fieldGicName, tabCount + 1, selfCount, requiredFields, nextParentFieldName, docContent);
					}
				}
			}
		}
	}

	private String buildReqJson(JavaMethod method) {
		List<JavaParameter> parameterList = method.getParameters();
		StringBuilder reqJson = new StringBuilder();
		for (JavaParameter parameter : parameterList) {
			JavaType javaType = parameter.getType();
			String fqTypeName = javaType.getFullyQualifiedName();
			String gcTypeName = javaType.getGenericCanonicalName();
			String paraName = parameter.getName();
			if (!DocClassUtil.isMvcIgnoreParams(fqTypeName)) {
				if (DocClassUtil.isPrimitive(fqTypeName)) {
					reqJson.append(LEFT_CURLY_BRACKETS);
					reqJson.append(DOUBLE_QUOTATION).append(paraName).append(DOUBLE_QUOTATION);
					reqJson.append(COLON);
					reqJson.append(DocUtil.jsonValueByType(fqTypeName));
					reqJson.append(RIGHT_CURLY_BRACKETS);
				} else {
					reqJson.append(buildDataJson(fqTypeName, gcTypeName, false));
				}
				break;
			}
		}
		if (reqJson.length() == 0) {
			reqJson.append("No request parameters are required.");
		}
		return JsonFormatUtil.formatJson(reqJson);
	}

	private String buildReturnJson(JavaMethod method) {
		JavaType returnType = method.getReturnType();
		String fqTypeName = returnType.getFullyQualifiedName();
		String gcTypeName = returnType.getGenericCanonicalName();
		if ("void".equals(gcTypeName)) {
			return "this api return nothing.";
		}
		return JsonFormatUtil.formatJson(buildDataJson(fqTypeName, gcTypeName, true));
	}
	
	private StringBuilder buildDataJson(String fullyQualifiedName, String genericCanonicalName, boolean isResp) {
		StringBuilder jsonContent = new StringBuilder();
		if (DocClassUtil.isMvcIgnoreParams(fullyQualifiedName)) {
			if (GlobalConstants.MODEL_AND_VIEW_FULLY.equals(fullyQualifiedName)) {
				jsonContent.append("forward or redirect to a page view.");
			} else {
				jsonContent.append("error restful return.");
			}
		} else if (DocClassUtil.isPrimitive(fullyQualifiedName)) {
			jsonContent.append(DocUtil.jsonValueByType(fullyQualifiedName));
		} else {
			buildJson(genericCanonicalName, isResp, jsonContent);
		}
		return jsonContent;
	}
	
	public void buildJson(String className, boolean isResp, StringBuilder jsonContent) {
		String globTypeName = DocClassUtil.getSimpleName(className);
		String[] globGicName = DocClassUtil.getGicName(className);
		if (DocClassUtil.isPrimitive(globTypeName)) {
			jsonContent.append(DocClassUtil.processTypeNameForParams(className));
		} else if (DocClassUtil.isCollection(globTypeName)) {
			jsonContent.append(LEFT_BRACKETS);
			if (globGicName.length == 0) {
				jsonContent.append(DocUtil.jsonValueByType(DEFAULT_JSON_TYPE));
			} else {
				String gicName = globGicName[0];
				if (DocClassUtil.isPrimitive(gicName)) {
					jsonContent.append(DocUtil.jsonValueByType(gicName));
				} else {
					buildJson(gicName, isResp, jsonContent);
				}
			}
			jsonContent.append(RIGHT_BRACKETS);
		} else if(DocClassUtil.isArray(globTypeName)) {
			jsonContent.append(LEFT_BRACKETS);
			String gicName = DocClassUtil.getArraySimpleName(className);
			if (DocClassUtil.isPrimitive(gicName)) {
				jsonContent.append(DocUtil.jsonValueByType(gicName));
			} else {
				buildJson(gicName, isResp, jsonContent);
			}
			jsonContent.append(RIGHT_BRACKETS);
		} else if (DocClassUtil.isMap(globTypeName)) {
			jsonContent.append(LEFT_CURLY_BRACKETS);
			jsonContent.append(DOUBLE_QUOTATION).append(MAP_KEY).append(DOUBLE_QUOTATION).append(COLON);
			if (globGicName.length == 2) {
				String gicName = globGicName[1];
				if (gicName.length() == 1 || DocClassUtil.isPrimitive(gicName)) {
					jsonContent.append(DocUtil.jsonValueByType(gicName));
				} else {
					buildJson(globGicName[1], isResp, jsonContent);
				}
			} else {
				jsonContent.append(DocUtil.jsonValueByType(DEFAULT_JSON_TYPE));
			}
			jsonContent.append(RIGHT_CURLY_BRACKETS);
		} else {
			JavaClass cls = builder.getClassByName(globTypeName);
			List<JavaField> fields = getFields(cls);
			jsonContent.append(LEFT_CURLY_BRACKETS);
			int i = 0;
			out: 
			for (JavaField field : fields) {
				JavaClass declaringClass = field.getDeclaringClass();
				String fieldClassName = declaringClass.getName();
				String fieldClassFullName = declaringClass.getFullyQualifiedName();
				String fieldName = field.getName();
				if (!(IgnoreFields.DECLARING_CLASS_NAME.equals(fieldClassName) && !PARAM_NAME.equals(fieldName))
						&& !IgnoreFields.SERIAL_VERSION_UID.equals(fieldName)
						&& !GlobalConstants.IGNORE_CLASS.containsKey(fieldClassFullName)) {

					if (PARAM_NAME.equals(fieldName)) {
						fieldName = PARAM_NAME_JSON_NAME;
					}
					
					List<DocletTag> paramTags = field.getTags();
					if (!isResp) {
						for (DocletTag docletTag : paramTags) {
							if (DocClassUtil.isIgnoreTag(docletTag.getName())) {
								continue out;
							}
						}
					}
					
					String fieldFQName = field.getType().getFullyQualifiedName();
					String fieldGCName = field.getType().getGenericCanonicalName();
					jsonContent.append(DOUBLE_QUOTATION).append(fieldName).append(DOUBLE_QUOTATION).append(COLON);
					if (DocClassUtil.isPrimitive(fieldFQName)) {
						jsonContent.append(DocUtil.getValByTypeAndFieldName(fieldFQName, field.getName()));
					} else if (DocClassUtil.isCollection(fieldFQName)) {
						jsonContent.append(LEFT_BRACKETS);
						String[] gicNames = DocClassUtil.getGicName(fieldGCName);
						if (gicNames.length == 1) {
							String gicName = gicNames[0];
							if (gicName.length() == 1 || DocClassUtil.isPrimitive(gicName)) {
								jsonContent.append(DocUtil.jsonValueByType(gicName));
							} else if(globTypeName.equals(gicName)){
								// do nothing
							}else {
								buildJson(gicName,isResp,jsonContent);
							}
						} else {
							jsonContent.append(DocUtil.jsonValueByType(DEFAULT_JSON_TYPE));
						}
						jsonContent.append(RIGHT_BRACKETS);
					} else if(DocClassUtil.isArray(fieldFQName)) {
						jsonContent.append(LEFT_BRACKETS);
						String gicName = DocClassUtil.getArraySimpleName(fieldFQName);
						if (DocClassUtil.isPrimitive(gicName)) {
							jsonContent.append(DocUtil.jsonValueByType(gicName));
						} else {
							buildJson(gicName, isResp,jsonContent);
						}
						jsonContent.append(RIGHT_BRACKETS);
					} else if (DocClassUtil.isMap(fieldFQName)) {
						String[] gicNames = DocClassUtil.getGicName(fieldGCName);
						jsonContent.append(LEFT_CURLY_BRACKETS).append(DOUBLE_QUOTATION).append(MAP_KEY).append(DOUBLE_QUOTATION).append(COLON);
						String gicName = gicNames[1];
						if (DocClassUtil.isPrimitive(gicName)) {
							jsonContent.append(DocUtil.jsonValueByType(gicName));
						} else {
							buildJson(gicName,isResp,jsonContent);
						}
						jsonContent.append(RIGHT_CURLY_BRACKETS);
					} else if (fieldFQName.length() == 1) {
						String gicName = globGicName[i];
						if (DocClassUtil.isPrimitive(gicName)) {
							jsonContent.append(DocUtil.jsonValueByType(gicName));
						} else {
							buildJson(gicName, isResp,jsonContent);
						}
						i++;
					}else if(globTypeName.equals(fieldFQName)) {
						// 带有本身属性的不再往下解析
					} else {
						buildJson(fieldGCName, isResp,jsonContent);
					}
					jsonContent.append(COMMA);
				}
			}
			if (jsonContent.toString().contains(COMMA)) {
				jsonContent.deleteCharAt(jsonContent.lastIndexOf(COMMA));
			}
			jsonContent.append(RIGHT_CURLY_BRACKETS);
		}
	}

	private String isRequired(String fieldStr, List<String> requiredFields, String parentFieldName) {
		boolean isRequired = false;
		for (int i = 0; i < requiredFields.size(); i++) {
			isRequired = requiredFields.get(i).contains(parentFieldName+fieldStr);
			if (isRequired) {
				break;
			}
		}
		return isRequired ? "`"+isRequired + "`" : String.valueOf(isRequired);
	}

	private List<JavaField> getFields(JavaClass cls1) {
		List<JavaField> fieldList = new ArrayList<>();
		if (null != cls1 && !DocClassUtil.BASE_CLASS_NAME.containsKey(cls1.getFullyQualifiedName())) {
			JavaClass pcls = cls1.getSuperJavaClass();
			fieldList.addAll(getFields(pcls));
			fieldList.addAll(cls1.getFields());
		}
		return fieldList;
	}
}
