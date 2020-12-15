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
import com.chetong.doc.constants.JsonFilelds;
import com.chetong.doc.constants.ParamRequiredField;
import com.chetong.doc.exception.ProcessCodeEnum;
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
	private static final String PARAM_NAME = "param";
	private static final String PARAM_NAME_JSON_NAME = "data";
	private static final String BLANK_SPACE_4 = "&nbsp;&nbsp;&nbsp;&nbsp;";
	private static final String BLANK_SPACE_6 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	private static final String WARN_DESC = "{\"waring\":\"You may have used non-display generics.\"}";
	private static final String LINE_BREAK_KEY = "\n";
	private static final String TRUE_LINE_BREAK_KEY = "|true\n";
	private static final String MAP_KEY= "\"mapKey\":";
	private static final String ANNOTATION_VALUE_KEY = "value";
	private static final String MODIFIER_PRIVATE = "private";
	private static final String MODIFIER_PUBLIC = "public";
	private static final String CONNECTOR = ".";
	private static final String COMMA = ",";
	private static final String LEFT_ANGLE_BRACKETS = "<";
	private static final String RIGHT_ANGLE_BRACKETS = ">";
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
	private String packageMatch;
	private List<ApiReqHeader> headers;
	private String basePathUrl;
	private String routeReqUrl;
	private ExecutorService threadPool;
	private Map<String, Integer> counterMap;
	
	public SourceBuilder(ApiConfig config, ExecutorService threadPool, Map<String, Integer> counterMap) {
		this.threadPool = threadPool;
		this.counterMap = counterMap;
		this.basePathUrl = config.getBasePathUrl();
		this.packageMatch = config.getPackageFilters();
		loadJavaFiles(config);
		this.headers = config.getRequestHeaders();
		this.routeReqUrl = this.basePathUrl + "/**/*.do";
	}

	/**
	 * 加载项目的源代码
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
						List<JavaAnnotation> classAnnotations = cls.getAnnotations();
						String serviceNameValue = null;
						for (JavaAnnotation annotation : classAnnotations) {
							String annotationName = annotation.getType().getName();
							if (GlobalConstants.SERVICE.equals(annotationName) || GlobalConstants.SERVICE_FULLY.equals(annotationName)) {
								serviceNameValue = annotation.getNamedParameter(ANNOTATION_VALUE_KEY).toString();
								serviceNameValue = serviceNameValue.replaceAll(DOUBLE_QUOTATION, EMPTY_STR);
							}
						}
						
						List<ApiDocContent> apiMethodDocs = null;
						if (StringUtil.isNotEmpty(packageMatch)) {
							if (DocUtil.isMatch(packageMatch, cls.getCanonicalName())) {
								apiMethodDocs = buildServiceMethod(index, cls, serviceNameValue);
							}
						} else {
							apiMethodDocs = buildServiceMethod(index, cls, serviceNameValue);
						}
						
						if (apiMethodDocs != null) {
							ApiDoc apiDoc = new ApiDoc();
							apiDoc.setIndex(index);
							apiDoc.setName(serviceNameValue);
							apiDoc.setDesc(index+SPACE_STR+clsComment);
							apiDoc.setServiceApiDocs(apiMethodDocs);
							return apiDoc;
						}
						return null;
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
							String annotationName = annotation.getType().getName();
							if (GlobalConstants.REQUEST_MAPPING.equals(annotationName) 
									|| GlobalConstants.REQUEST_MAPPING_FULLY.equals(annotationName)
									|| GlobalConstants.POST_MAPPING.equals(annotationName)
									|| GlobalConstants.POST_MAPPING_FULLY.equals(annotationName)
									|| GlobalConstants.GET_MAPPING.equals(annotationName)
									|| GlobalConstants.GET_MAPPING_FULLY.equals(annotationName)) {
								String controllerReqPath = annotation.getNamedParameter(ANNOTATION_VALUE_KEY).toString().replaceAll(DOUBLE_QUOTATION, EMPTY_STR);
								if (!controllerReqPath.startsWith(SLASH)) {
									baseReqPath.append(SLASH);
								}
								baseReqPath.append(controllerReqPath);
								break;
							}
						}
						
						List<ApiDocContent> apiMethodDocs = buildControllerMethod(index, cls, baseReqPath);
						if (apiMethodDocs != null) {
							ApiDoc apiDoc = new ApiDoc();
							apiDoc.setIndex(index);
							apiDoc.setName(cls.getName());
							apiDoc.setDesc(index+SPACE_STR+clsComment);
							apiDoc.setControllerApiDocs(apiMethodDocs);
							return apiDoc;
						}
						return null;
					}
				});
				futures.add(future);
			}else if (checkEnum(cls)) {
				List<JavaField> fields = cls.getFields();
				if (!fields.isEmpty()) {
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
									if (javaField.getModifiers().toString().indexOf(MODIFIER_PRIVATE) == -1
											&& javaField.getEnumConstantArguments() != null) {
										List<Expression> arguments = javaField.getEnumConstantArguments();
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
			isEnum = imports.contains(GlobalConstants.BASE_ENUM_FULLY_CT) || imports.contains(GlobalConstants.BASE_ENUM_FULLY_BB) || cls.getSource().toString().indexOf(GlobalConstants.BASE_ENUM) != -1;
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
			String annotationName = annotation.getType().getCanonicalName();
			if (GlobalConstants.SERVICE.equals(annotationName) || GlobalConstants.SERVICE_FULLY.equals(annotationName)) {
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
			String annotationName = annotation.getType().getCanonicalName();
			if (GlobalConstants.CONTROLLER.equals(annotationName) 
					|| GlobalConstants.CONTROLLER_FULLY.equals(annotationName)
					|| GlobalConstants.RESTCONTROLLER.equals(annotationName)
					|| GlobalConstants.RESTCONTROLLER_FULLY.equals(annotationName)) {
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
				String annotationName = annotation.getType().getName();
				if (GlobalConstants.PARAM_REQUIRED.equals(annotationName) 
						|| GlobalConstants.PARAM_REQUIRED_FULLY.equals(annotationName)) {
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
				} else if (GlobalConstants.LOG_TAIL.equals(annotationName) 
						|| GlobalConstants.LOG_TAIL_FULLY.equals(annotationName)) {
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
			
			String requestParams = buildMethodRequest(method, PARAM_NAME, cls.getCanonicalName(), isRequireds);
			String requestJson = buildReqJson(method);
			String responseParams = buildMethodReturn(method, cls.getGenericFullyQualifiedName());
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
				String annotationName = annotation.getType().getName();
				if (GlobalConstants.REQUEST_MAPPING.equals(annotationName) 
						|| GlobalConstants.REQUEST_MAPPING_FULLY.equals(annotationName)) {
					String methodReqPath = annotation.getNamedParameter(ANNOTATION_VALUE_KEY).toString().replaceAll(DOUBLE_QUOTATION, EMPTY_STR);
					if (!methodReqPath.startsWith(SLASH)) {
						apiReqUrl.append(SLASH);
					}
					apiReqUrl.append(methodReqPath);
					break;
				}
			}
			
			String requestParams = buildMethodRequest(method, PARAM_NAME, cls.getCanonicalName(), isRequireds);
			String requestJson = buildReqJson(method);
			String responseParams = buildMethodReturn(method, cls.getGenericFullyQualifiedName());
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

	private String buildMethodReturn(JavaMethod method, String serviceName) {
		StringBuilder buildReturn = new StringBuilder();
		String typeName = method.getReturnType().getFullyQualifiedName();
		String returnType = method.getReturnType().getGenericCanonicalName();
		if (DocClassUtil.isMvcIgnoreParams(typeName)) {
			if (!GlobalConstants.MODEL_AND_VIEW_FULLY.equals(typeName)) {
				throw ProcessCodeEnum.FAIL.buildProcessException("smart-doc can't support " + typeName + " as method return in " + serviceName);
			}
		} else if (DocClassUtil.isPrimitive(typeName)) {
			buildReturn.append(primitiveReturnRespComment(DocClassUtil.processTypeNameForParams(typeName)));
		} else if (DocClassUtil.isCollection(typeName)) {
			if (returnType.contains(LEFT_ANGLE_BRACKETS)) {
				String gicName = returnType.substring(returnType.indexOf(LEFT_ANGLE_BRACKETS) + 1, returnType.lastIndexOf(RIGHT_ANGLE_BRACKETS));
				if (DocClassUtil.isPrimitive(gicName)) {
					buildReturn.append(primitiveReturnRespComment("array of " + DocClassUtil.processTypeNameForParams(gicName)));
				}else {
					buildParams(gicName, EMPTY_STR, 0, new ArrayList<String>(), EMPTY_STR, buildReturn);
				}
			}
		} else if (DocClassUtil.isMap(typeName)) {
			String[] keyValue = DocClassUtil.getMapKeyValueType(returnType);
			if (keyValue.length != 0 && DocClassUtil.isPrimitive(keyValue[1])) {
				buildReturn.append(primitiveReturnRespComment("key value"));
			} else {
				buildParams(keyValue[1], EMPTY_STR, 0, new ArrayList<String>(), EMPTY_STR, buildReturn);
			}
		} else if (StringUtil.isNotEmpty(returnType)) {
			buildParams(returnType, EMPTY_STR, 0, new ArrayList<String>(), EMPTY_STR, buildReturn);
		}
		return buildReturn.toString();
	}

	private void buildParams(String className, String pre, int i, List<String> requiredFields, String parentFieldName, StringBuilder docContent) {
		String simpleName = DocClassUtil.getSimpleName(className);
		String[] globGicName = DocClassUtil.getSimpleGicName(className);
		JavaClass cls = builder.getClassByName(simpleName);

		List<JavaField> fields = getFields(cls, 0);
		int n = 0;
		if (DocClassUtil.isPrimitive(className)) {
			docContent.append(primitiveReturnRespComment(DocClassUtil.processTypeNameForParams(className)));
		} else if (DocClassUtil.isCollection(simpleName) || DocClassUtil.isArray(simpleName)) {
			if (!DocClassUtil.isCollection(globGicName[0])) {
				String gicName = globGicName[0];
				if (DocClassUtil.isArray(gicName)) {
					gicName = gicName.substring(0, gicName.indexOf(LEFT_BRACKETS));
				}
				buildParams(gicName, pre, i + 1, requiredFields, parentFieldName, docContent);
			}
		} else if (DocClassUtil.isMap(simpleName)) {
			if (globGicName.length == 2) {
				buildParams(globGicName[1], pre, i + 1, requiredFields, parentFieldName, docContent);
			}
		} else {
			out: 
			for (JavaField field : fields) {
				JavaClass declaringClass = field.getDeclaringClass();
				String fieldName = field.getName();
				// 只认ParameterVO参数的Service
				if (!(IgnoreFields.DECLARING_CLASS_NAME.equals(declaringClass.getName()) && !IgnoreFields.PARAM_NAME.equals(fieldName))
						&& !IgnoreFields.BASE_MODEL.equals(declaringClass.getName()) 
						&& !IgnoreFields.SERIAL_VERSION_UID.equals(fieldName)
						&& !IgnoreFields.REQUEST.equals(fieldName) 
						&& !IgnoreFields.RESPONSE.equals(fieldName)) {

					if (PARAM_NAME.equals(fieldName)) {
						fieldName = PARAM_NAME_JSON_NAME;
					}

					String subTypeName = field.getType().getFullyQualifiedName();
					String fieldGicName = field.getType().getGenericCanonicalName();
					List<JavaAnnotation> javaAnnotations = field.getAnnotations();

					List<DocletTag> paramTags = field.getTags();
					for (DocletTag docletTag : paramTags) {
						if (DocClassUtil.isIgnoreTag(docletTag.getName())) {
							continue out;
						}
					}
					
					String strRequired = this.isRequired(fieldName, requiredFields, parentFieldName);
					for (JavaAnnotation annotation : javaAnnotations) {
						String annotationName = annotation.getType().getSimpleName();
						if (JsonFilelds.JSON_IGNORE.equals(annotationName)) {
							continue out;
						} else if (JsonFilelds.JSON_FIELD.equals(annotationName) && null != annotation.getProperty(JsonFilelds.JSON_FIELD_NAME)) {
							fieldName = annotation.getProperty(JsonFilelds.JSON_FIELD_NAME).toString().replace(DOUBLE_QUOTATION, EMPTY_STR);
						}
					}

					// cover comment
					String comment = field.getComment();
					if (DocClassUtil.isPrimitive(subTypeName)) {
						docContent.append(pre).append(fieldName).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(subTypeName)).append(VERTICAL_SEPARATORS);
						if (StringUtil.isNotEmpty(comment)) {
							comment = comment.replaceAll("\r|\n", SPACE_STR);
							docContent.append(comment).append(VERTICAL_SEPARATORS).append(strRequired).append(LINE_BREAK_KEY);
						} else {
							docContent.append(NO_COMENT_DESC).append(VERTICAL_SEPARATORS).append(strRequired).append(LINE_BREAK_KEY);
						}
					} else {
						docContent.append(pre);
						StringBuilder fieldTypeDesc = new StringBuilder();
						if (DocClassUtil.isCollection(subTypeName)) {
							fieldTypeDesc.append(DocClassUtil.processTypeNameForParams(subTypeName));
							String[] simpleGicName = DocClassUtil.getSimpleGicName(fieldGicName);
							fieldTypeDesc.append(LEFT_PARENTHESES).append(DocClassUtil.processTypeNameForParams(simpleGicName[0])).append(RIGHT_PARENTHESES); 
						} else if (DocClassUtil.isArray(subTypeName)) {
							fieldTypeDesc.append(DocClassUtil.processTypeNameForParams(subTypeName));
						} else {
							String simpleN = DocClassUtil.getSimpleName(globGicName[n]);
							if (subTypeName.length() == 1 && DocClassUtil.isCollection(simpleN)) {
								fieldTypeDesc.append(DocClassUtil.processTypeNameForParams(simpleN));
								String gicSimpleName = DocClassUtil.getGlobGicSimpleName(globGicName[n]);
								fieldTypeDesc.append(LEFT_PARENTHESES).append(DocClassUtil.processTypeNameForParams(gicSimpleName)).append(RIGHT_PARENTHESES); 
							} else {
								fieldTypeDesc.append("obj");
							}
						}

						docContent.append(fieldName).append(VERTICAL_SEPARATORS).append(fieldTypeDesc).append(VERTICAL_SEPARATORS);
						if (StringUtil.isNotEmpty(comment)) {
							comment = comment.replaceAll("\r|\n", SPACE_STR);
							docContent.append(comment).append(VERTICAL_SEPARATORS).append(strRequired).append(LINE_BREAK_KEY);
						} else {
							docContent.append("No comments found|").append(strRequired).append(LINE_BREAK_KEY);
						}
						
						StringBuilder preBuilder = new StringBuilder();
						for (int j = 0; j < i; j++) {
							preBuilder.append(BLANK_SPACE_6);
						}
						preBuilder.append(BLANK_SPACE_4);
						
						String nextParentFieldName = StringUtil.isNotEmpty(parentFieldName) || PARAM_NAME_JSON_NAME.equals(fieldName) ? EMPTY_STR : parentFieldName+fieldName+CONNECTOR;
						if (DocClassUtil.isMap(subTypeName)) {
							String gNameTemp = field.getType().getGenericCanonicalName();
							if (DocClassUtil.isMap(gNameTemp)) {
								docContent.append(preBuilder + "any object|object|any object\n");
							} else {
								String valType = DocClassUtil.getMapKeyValueType(gNameTemp)[1];
								if (!DocClassUtil.isPrimitive(valType)) {
									if (valType.length() == 1) {
										String gicName = (n < globGicName.length) ? globGicName[n] : globGicName[globGicName.length - 1];
										if (!DocClassUtil.isPrimitive(gicName) && !simpleName.equals(gicName)) {
											buildParams(gicName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, docContent);
										}
									} else {
										buildParams(valType, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, docContent);
									}
								}
							}
						} else if (DocClassUtil.isCollection(subTypeName)) {
							String gNameTemp = field.getType().getGenericCanonicalName();
							String[] gNameArr = DocClassUtil.getSimpleGicName(gNameTemp);
							if (gNameArr.length > 0) {
								String gName = DocClassUtil.getSimpleGicName(gNameTemp)[0];
								if (!DocClassUtil.isPrimitive(gName) && !simpleName.equals(gName) && !gName.contains(simpleName)) {
									if (gName.length() == 1) {
										int len = globGicName.length;
										if (len > 0) {
											String gicName = (n < len) ? globGicName[n] : globGicName[len - 1];
											if (!DocClassUtil.isPrimitive(gicName) && !simpleName.equals(gicName)) {
												buildParams(gicName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, docContent);
											}
										}
									} else {
										buildParams(gName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, docContent);
									}
								}
							}
						} else if (subTypeName.length() == 1) {
							if (!simpleName.equals(className)) {
								if (n < globGicName.length) {
									String gicName = globGicName[n];
									String simple = DocClassUtil.getSimpleName(gicName);
									if (DocClassUtil.isPrimitive(simple)) {
										// do nothing
									} else if (gicName.contains(LEFT_ANGLE_BRACKETS)) {
										if (DocClassUtil.isCollection(simple)) {
											String gName = DocClassUtil.getSimpleGicName(gicName)[0];
											if (!DocClassUtil.isPrimitive(gName)) {
												buildParams(gName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, docContent);
											}
										} else if (DocClassUtil.isMap(simple)) {
											String valType = DocClassUtil.getMapKeyValueType(gicName)[1];
											if (!DocClassUtil.isPrimitive(valType)) {
												buildParams(valType, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, docContent);
											}
										} else {
											buildParams(gicName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, docContent);
										}
									} else {
										buildParams(gicName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, docContent);
									}
								} else {
									buildParams(subTypeName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, docContent);
								}
							   n++;
							}
						} else if (DocClassUtil.isArray(subTypeName)) {
							fieldGicName = fieldGicName.substring(0, fieldGicName.indexOf(LEFT_BRACKETS));
							if (className.equals(fieldGicName)) {
								// do nothing
							} else if (!DocClassUtil.isPrimitive(fieldGicName)) {
								buildParams(fieldGicName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, docContent);
							}
						} else if (simpleName.equals(subTypeName)) {
							// do nothing
						} else {
							buildParams(fieldGicName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, docContent);
						}
					}
				}
			}
		}
	}

	private String primitiveReturnRespComment(String typeName) {
		StringBuilder comments = new StringBuilder();
		comments.append("no param name|").append(typeName).append(VERTICAL_SEPARATORS).append("The interface directly returns the ");
		comments.append(typeName).append(" type value.\n");
		return comments.toString();
	}

	private String buildReturnJson(JavaMethod method) {
		if ("void".equals(method.getReturnType().getFullyQualifiedName())) {
			return "this api return nothing.";
		}
		String returnType = method.getReturnType().getGenericCanonicalName();
		String typeName = method.getReturnType().getFullyQualifiedName();
		return JsonFormatUtil.formatJson(buildDataJson(typeName, returnType, true));
	}
	
	private StringBuilder buildDataJson(String fullyQualifiedName, String genericCanonicalName, boolean isResp) {
		StringBuilder jsonBuilder = new StringBuilder();
		if (DocClassUtil.isMvcIgnoreParams(fullyQualifiedName)) {
			if (GlobalConstants.MODEL_AND_VIEW_FULLY.equals(fullyQualifiedName)) {
				jsonBuilder.append("forward or redirect to a page view.");
			} else {
				jsonBuilder.append("error restful return.");
			}
		} else if (DocClassUtil.isPrimitive(fullyQualifiedName)) {
			jsonBuilder.append(DocUtil.jsonValueByType(fullyQualifiedName));
		} else {
			buildJson(fullyQualifiedName, genericCanonicalName, isResp, jsonBuilder);
		}
		return jsonBuilder;
	}
	
	private void buildJson(String fullyQualifiedName, String genericCanonicalName, boolean isResp, StringBuilder jsonContent) {
		JavaClass cls;
		try {
			cls = builder.getClassByName(fullyQualifiedName);
		} catch (Exception e) {
			jsonContent.append(DocUtil.jsonValueByType(fullyQualifiedName));
			return;
		}
		String[] globGicName = DocClassUtil.getSimpleGicName(genericCanonicalName);
		if (DocClassUtil.isCollection(fullyQualifiedName)) {
			jsonContent.append(LEFT_BRACKETS);
			if (globGicName.length == 0) {
				jsonContent.append("{\"object\":\"any object\"}");
			} else {
				String gName = globGicName[0];
				if (DocClassUtil.isPrimitive(gName)) {
					jsonContent.append(DocUtil.jsonValueByType(gName));
				} else if (DocClassUtil.isCollection(gName)) {
					String simple = DocClassUtil.getSimpleName(gName);
					buildJson(simple, gName, isResp,jsonContent);
				} else {
					buildJson(gName, gName, isResp, jsonContent);
				}
			}
			jsonContent.append(RIGHT_BRACKETS);
		} else if(DocClassUtil.isArray(fullyQualifiedName)) {
			jsonContent.append(LEFT_BRACKETS);
			String gName = fullyQualifiedName.substring(0, fullyQualifiedName.indexOf(LEFT_BRACKETS)).toLowerCase();
			if (DocClassUtil.isPrimitive(gName)) {
				jsonContent.append(DocUtil.jsonValueByType(gName));
			} else {
				buildJson(gName, gName, isResp, jsonContent);
			}
			jsonContent.append(RIGHT_BRACKETS);
		} else if (DocClassUtil.isMap(fullyQualifiedName)) {
			String gNameTemp = genericCanonicalName;
			String[] getKeyValType = DocClassUtil.getMapKeyValueType(gNameTemp);
			jsonContent.append(LEFT_CURLY_BRACKETS);
			jsonContent.append(MAP_KEY);
			if (getKeyValType.length != 0) {
				String gicName = gNameTemp.substring(gNameTemp.indexOf(CONNECTOR) + 1, gNameTemp.lastIndexOf(RIGHT_ANGLE_BRACKETS));
				if (gicName.length() == 1)  {
					if (DocClassUtil.isPrimitive(gicName)) {
						jsonContent.append(DocUtil.jsonValueByType(gicName));
					} else if (gicName.contains(LEFT_ANGLE_BRACKETS)) {
						String simple = DocClassUtil.getSimpleName(gicName);
						buildJson(simple, gicName, isResp,jsonContent);
					}
				} else {
					buildJson(gicName, gNameTemp, isResp,jsonContent);
				}
			}
			jsonContent.append(RIGHT_CURLY_BRACKETS);
		} else {
			List<JavaField> fields = getFields(cls, 0);
			jsonContent.append(LEFT_CURLY_BRACKETS);
			int i = 0;
			out: 
			for (JavaField field : fields) {
				JavaClass declaringClass = field.getDeclaringClass();
				String fieldName = field.getName();
				if (!(IgnoreFields.DECLARING_CLASS_NAME.equals(declaringClass.getName()) && !PARAM_NAME.equals(fieldName))
						&& !IgnoreFields.BASE_MODEL.equals(declaringClass.getName()) 
						&& !IgnoreFields.USER.equals(fieldName)
						&& !IgnoreFields.SERIAL_VERSION_UID.equals(fieldName) 
						&& !IgnoreFields.ORIGIN_TYPE.equals(fieldName)
						&& !IgnoreFields.REQUEST.equals(fieldName)
						&& !IgnoreFields.RESPONSE.equals(fieldName)) {

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
					
					List<JavaAnnotation> annotations = field.getAnnotations();
					for (JavaAnnotation annotation : annotations) {
						String annotationName = annotation.getType().getSimpleName();
						if (JsonFilelds.JSON_IGNORE.equals(annotationName)) {
							continue out;
						} else if (JsonFilelds.JSON_FIELD.equals(annotationName) && null != annotation.getProperty(JsonFilelds.JSON_FIELD_NAME)) {
							fieldName = annotation.getProperty(JsonFilelds.JSON_FIELD_NAME).toString().replace(DOUBLE_QUOTATION, EMPTY_STR);
						}
					}
					
					String typeSimpleName = field.getType().getSimpleName();
					String fieldFQName = field.getType().getFullyQualifiedName();
					String fieldGCName = field.getType().getGenericCanonicalName();
					jsonContent.append(DOUBLE_QUOTATION).append(fieldName).append(DOUBLE_QUOTATION).append(COLON);
					if (DocClassUtil.isPrimitive(typeSimpleName)) {
						jsonContent.append(DocUtil.getValByTypeAndFieldName(typeSimpleName, field.getName()));
					} else if (DocClassUtil.isCollection(fieldFQName) || DocClassUtil.isArray(fieldFQName)) {
						fieldGCName = DocClassUtil.isArray(fieldFQName) ? fieldGCName.substring(0, fieldGCName.indexOf(LEFT_BRACKETS)) : fieldGCName;
						jsonContent.append(LEFT_BRACKETS);
						if (DocClassUtil.getSimpleGicName(fieldGCName).length == 0) {
							jsonContent.append("{\"object\":\"any object\"}");
						} else {
							String gicName = DocClassUtil.getSimpleGicName(fieldGCName)[0];
							if (DocClassUtil.isPrimitive(fullyQualifiedName)) {
								jsonContent.append(DocUtil.jsonValueByType(fullyQualifiedName));
							} else if (DocClassUtil.isCollection(fullyQualifiedName)) {
								buildJson(gicName, fieldGCName, isResp,jsonContent);
							} else if (gicName.length() == 1) {
								if (globGicName.length == 0) {
									jsonContent.append("{\"object\":\"any object\"}");
								} else {
									String gicName1 = (i < globGicName.length) ? globGicName[i] : globGicName[globGicName.length - 1];
									if (DocClassUtil.isPrimitive(gicName1)) {
										jsonContent.append(DocUtil.jsonValueByType(gicName1));
									} else {
										if (!fullyQualifiedName.equals(gicName1)) {
											buildJson(DocClassUtil.getSimpleName(gicName1), gicName1, isResp,jsonContent);
										} else {
											jsonContent.append("{\"$ref\":\"..\"}");
										}
									}
								}
							} else {
								if (!fullyQualifiedName.equals(gicName) && !gicName.contains(fullyQualifiedName)) {
									if(DocClassUtil.isPrimitive(gicName)){
										jsonContent.append(DocUtil.jsonValueByType(gicName));
									}else {
										buildJson(gicName, fieldGCName, isResp,jsonContent);
									}
								} else {
									jsonContent.append("{\"$ref\":\"..\"}");
								}
							}
						}
						jsonContent.append(RIGHT_BRACKETS);
					} else if (DocClassUtil.isMap(fieldFQName)) {
						String[] gicNameArr = DocClassUtil.getSimpleGicName(fieldGCName);
						jsonContent.append(LEFT_CURLY_BRACKETS).append(MAP_KEY);
						String gicName1 = gicNameArr[gicNameArr.length - 1];
						if (DocClassUtil.isPrimitive(gicName1)) {
							jsonContent.append(DocUtil.jsonValueByType(gicName1));
						} else {
							if (!fullyQualifiedName.equals(gicName1)) {
								buildJson(DocClassUtil.getSimpleName(gicName1), gicName1, isResp,jsonContent);
							} else {
								jsonContent.append(LEFT_CURLY_BRACKETS).append(RIGHT_CURLY_BRACKETS);
							}
						}
						jsonContent.append(RIGHT_CURLY_BRACKETS);
					} else if (fieldFQName.length() == 1) {
						if (!fullyQualifiedName.equals(genericCanonicalName)) {
							String gicName = globGicName[i];
							if (gicName.contains(LEFT_ANGLE_BRACKETS)) {
								String simple = DocClassUtil.getSimpleName(gicName);
								buildJson(simple, gicName, isResp,jsonContent);
							} else {
								if (DocClassUtil.isPrimitive(gicName)) {
									jsonContent.append(DocUtil.jsonValueByType(gicName));
								} else {
									buildJson(gicName, gicName, isResp,jsonContent);
								}
							}
						} else {
							jsonContent.append(WARN_DESC);
						}
						i++;
					} else if (fullyQualifiedName.equals(fieldFQName)) {
						jsonContent.append("{\"$ref\":\"...\"}");
					} else {
						buildJson(fieldFQName, fieldGCName, isResp,jsonContent);
					}
					jsonContent.append(COMMA);
				}
			}
			if (jsonContent.indexOf(COMMA) != -1) {
				jsonContent.deleteCharAt(jsonContent.lastIndexOf(COMMA));
			}
			jsonContent.append(RIGHT_CURLY_BRACKETS);
		}
	}

	private String buildReqJson(JavaMethod method) {
		List<JavaParameter> parameterList = method.getParameters();
		StringBuilder sb = new StringBuilder();
		for (JavaParameter parameter : parameterList) {
			JavaType javaType = parameter.getType();
			String simpleTypeName = javaType.getValue();
			String gicTypeName = javaType.getGenericCanonicalName();
			String typeName = javaType.getFullyQualifiedName();
			String paraName = parameter.getName();
			if (!DocClassUtil.isMvcIgnoreParams(typeName)) {
				if (DocClassUtil.isPrimitive(simpleTypeName)) {
					sb.append(LEFT_CURLY_BRACKETS);
					sb.append(DOUBLE_QUOTATION).append(paraName).append(DOUBLE_QUOTATION);
					sb.append(COLON);
					sb.append(DocUtil.jsonValueByType(simpleTypeName));
					sb.append(RIGHT_CURLY_BRACKETS);
				} else {
					sb.append(buildDataJson(typeName, gicTypeName, false));
				}
				break;
			}
		}
		if (sb.length() == 0) {
			sb.append("No request parameters are required.");
		}
		return JsonFormatUtil.formatJson(sb);
	}

	private String isRequired(String fieldStr, List<String> requiredFields, String parentFieldName) {
		boolean isRequired = false;
		for (int i = 0; i < requiredFields.size(); i++) {
			isRequired = requiredFields.get(i).indexOf(parentFieldName+fieldStr)!=-1;
			if (isRequired) {
				break;
			}
		}
		return isRequired ? "`"+isRequired + "`" : String.valueOf(isRequired);
	}

	private String buildMethodRequest(final JavaMethod javaMethod, final String tagName, final String className, List<String> requiredFields) {
		List<DocletTag> paramTags = javaMethod.getTagsByName(tagName);
		Map<String, String> paramTagMap = new HashMap<>();
		for (DocletTag docletTag : paramTags) {
			String value = docletTag.getValue();
			if (StringUtil.isEmpty(value)) {
				throw ProcessCodeEnum.FAIL.buildProcessException("ERROR: #" + javaMethod.getName() + "() - bad @param javadoc from " + className);
			}
			String pName;
			String pValue;
			int idx = value.indexOf(LINE_BREAK_KEY);
			// 如果存在换行
			if (idx > -1) {
				pName = value.substring(0, idx);
				pValue = value.substring(idx + 1);
			} else {
				pName = (value.indexOf(SPACE_STR) > -1) ? value.substring(0, value.indexOf(SPACE_STR)) : value;
				pValue = value.indexOf(SPACE_STR) > -1 ? value.substring(value.indexOf(' ') + 1) : NO_COMENT_DESC;
			}
			paramTagMap.put(pName, pValue);
		}

		List<JavaParameter> parameterList = javaMethod.getParameters();
		if (!parameterList.isEmpty()) {
			StringBuilder reqParam = new StringBuilder();
			for (JavaParameter parameter : parameterList) {
				String paramName = parameter.getName();
				String typeName = parameter.getType().getGenericCanonicalName();
				String fullTypeName = parameter.getType().getFullyQualifiedName();
				if (!DocClassUtil.isMvcIgnoreParams(typeName)) {
					String comment = paramTagMap.get(paramName);
					if (StringUtil.isEmpty(comment)) {
						comment = NO_COMENT_DESC;
					}

					if (DocClassUtil.isCollection(fullTypeName) || DocClassUtil.isArray(fullTypeName)) {
						String[] gicNameArr = DocClassUtil.getSimpleGicName(typeName);
						String gicName = gicNameArr[0];
						if (DocClassUtil.isArray(gicName)) {
							gicName = gicName.substring(0, gicName.indexOf(LEFT_BRACKETS));
						}
						if (DocClassUtil.isPrimitive(gicName)) {
							String typeTemp = " of " + DocClassUtil.processTypeNameForParams(gicName);
							reqParam.append(paramName).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(gicName)).append(typeTemp).append(VERTICAL_SEPARATORS).append(comment).append(TRUE_LINE_BREAK_KEY);
						} else {
							reqParam.append(paramName).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(gicName)).append(VERTICAL_SEPARATORS).append(comment).append(TRUE_LINE_BREAK_KEY);
							buildParams(gicNameArr[0], BLANK_SPACE_4, 1, requiredFields, EMPTY_STR, reqParam);
						}

					} else if (DocClassUtil.isPrimitive(fullTypeName)) {
						reqParam.append(paramName).append(VERTICAL_SEPARATORS).append(DocClassUtil.processTypeNameForParams(fullTypeName)).append(VERTICAL_SEPARATORS).append(comment).append(TRUE_LINE_BREAK_KEY);
					} else if (DocClassUtil.isMap(typeName)) {
						reqParam.append(paramName).append(VERTICAL_SEPARATORS).append("map").append(VERTICAL_SEPARATORS).append(comment).append(TRUE_LINE_BREAK_KEY);
					} else {
						buildParams(typeName, EMPTY_STR, 0, requiredFields, EMPTY_STR, reqParam);
					}
				}
			}
			return reqParam.toString();
		}
		return null;
	}

	private List<JavaField> getFields(JavaClass cls1, int i) {
		List<JavaField> fieldList = new ArrayList<>();
		if (null != cls1
				&& !IgnoreFields.Object.equals(cls1.getSimpleName()) 
				&& !IgnoreFields.TIMESTAMP.equals(cls1.getSimpleName())
				&& !IgnoreFields.DATE.equals(cls1.getSimpleName()) 
				&& !IgnoreFields.LOCALE.equals(cls1.getSimpleName())) {
			JavaClass pcls = cls1.getSuperJavaClass();
			fieldList.addAll(getFields(pcls, i));
			fieldList.addAll(cls1.getFields());
		}
		return fieldList;
	}
}
