package com.chetong.doc.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.chetong.doc.constants.GlobalConstants;
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
	
	private static final String JSON_CONTENT_TYPE = "application/json; charset=utf-8";
	private static final String NO_COMENT_DESC = "No comments found.";
	private static final String PARAM_NAME = "param";
	private static final String WARN_DESC = "{\"waring\":\"You may have used non-display generics.\"}";
	private static final String SPACE_KEY = " ";
	private static final String LINE_BREAK_KEY = "\n";
	private static final String TRUE_LINE_BREAK_KEY = "|true\n";
	private static final String MAP_KEY= "\"mapKey\":";
	private static final String ANNOTATION_VALUE_KEY = "value";
	private static final String CONNECTOR = ".";
	private static final String COMMA = ",";
	private static final String LEFT_ANGLE_BRACKETS = "<";
	private static final String RIGHT_ANGLE_BRACKETS = ">";
	
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
	private String appUrl;

	protected static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();
	
	protected static final Map<String, Integer> COUNTER_MAP = new ConcurrentHashMap<>();
	
	public SourceBuilder(ApiConfig config) {
		this.appUrl = config.getBasePathUrl();
		this.packageMatch = config.getPackageFilters();
		loadJavaFiles(config);
		this.headers = config.getRequestHeaders();
	}

	/**
	 * 加载项目的源代码
	 */
	private void loadJavaFiles(ApiConfig config) {
		List<SourcePath> paths = config.getSourcePaths();
		this.builder = new JavaProjectBuilder();
		for (SourcePath path : paths) {
			String strPath = path.getPath();
			if (StringUtil.isNotEmpty(strPath)) {
				strPath = strPath.replace("\\", "/");
				builder.addSourceTree(new File(strPath));
			}
		}
		this.javaClasses.addAll(builder.getClasses());
		COUNTER_MAP.put(SCAN_CLASS_COUNT_KEY, javaClasses.size());
	}

	public List<Future<?>> getApiDocData() {
		List<Future<?>> futures = new ArrayList<>();
		COUNTER_MAP.put(CONTROLLER_COUNT_KEY, 0);
		COUNTER_MAP.put(SERVICE_COUNT_KEY, 0);
		COUNTER_MAP.put(ENUM_COUNT_KEY, 0);
		
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
				Future<ApiDoc> future = THREAD_POOL.submit(new Callable<ApiDoc>() {
					@Override
					public ApiDoc call() throws Exception {
						List<JavaAnnotation> classAnnotations = cls.getAnnotations();
						String serviceNameValue = null;
						for (JavaAnnotation annotation : classAnnotations) {
							String annotationName = annotation.getType().getName();
							if (GlobalConstants.SERVICE.equals(annotationName) || GlobalConstants.SERVICE_FULLY.equals(annotationName)) {
								serviceNameValue = annotation.getNamedParameter(ANNOTATION_VALUE_KEY).toString();
								serviceNameValue = serviceNameValue.replaceAll("\"", "");
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
							apiDoc.setDesc(index+SPACE_KEY+clsComment);
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
				Future<ApiDoc> future = THREAD_POOL.submit(new Callable<ApiDoc>() {
					@Override
					public ApiDoc call() throws Exception {
						List<JavaAnnotation> classAnnotations = cls.getAnnotations();
						String baseReqPath = null;
						for (JavaAnnotation annotation : classAnnotations) {
							String annotationName = annotation.getType().getName();
							if (GlobalConstants.REQUEST_MAPPING.equals(annotationName) 
									|| GlobalConstants.REQUEST_MAPPING_FULLY.equals(annotationName)
									|| GlobalConstants.POST_MAPPING.equals(annotationName)
									|| GlobalConstants.POST_MAPPING_FULLY.equals(annotationName)
									|| GlobalConstants.GET_MAPPING.equals(annotationName)
									|| GlobalConstants.GET_MAPPING_FULLY.equals(annotationName)) {
								baseReqPath = annotation.getNamedParameter(ANNOTATION_VALUE_KEY).toString();
								baseReqPath = baseReqPath.replaceAll("\"", "");
							}
						}
						
						if (baseReqPath==null) {
							baseReqPath = "";
						} else {
							baseReqPath = baseReqPath.replaceAll("\"", "");
						}
						
						List<ApiDocContent> apiMethodDocs = buildControllerMethod(index, cls, baseReqPath);
						if (apiMethodDocs != null) {
							ApiDoc apiDoc = new ApiDoc();
							apiDoc.setIndex(index);
							apiDoc.setName(cls.getName());
							apiDoc.setDesc(index+SPACE_KEY+clsComment);
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
						Future<ApiResultCode> future = THREAD_POOL.submit(new Callable<ApiResultCode>() {
							@Override
							public ApiResultCode call() throws Exception {
								ApiResultCode resultCode = new ApiResultCode();
								resultCode.setName(cls.getName());
								resultCode.setDesc(3+CONNECTOR+index+SPACE_KEY+clsComment);
								StringBuilder codeDesc = new StringBuilder();
								for (JavaField javaField : fields) {
									if (javaField.getModifiers().toString().indexOf("private") == -1
											&& javaField.getEnumConstantArguments() != null) {
										List<Expression> arguments = javaField.getEnumConstantArguments();
										codeDesc.append(arguments.get(0));
										codeDesc.append('|');
										codeDesc.append(arguments.get(1));
										codeDesc.append(LINE_BREAK_KEY);
									}
								}
								resultCode.setCodeDesc(codeDesc.toString().replaceAll("\"", ""));
								//添加计数
								COUNTER_MAP.put(ENUM_COUNT_KEY, COUNTER_MAP.get(ENUM_COUNT_KEY)+1);
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
			if (null != method.getTagByName(GlobalConstants.IGNORE_TAG)
					|| method.getModifiers().contains("private")
					|| interfaceCls == null) {
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
			
			List<String> isRequireds = new LinkedList<>();
			List<JavaAnnotation> annotations = method.getAnnotations();
			for (JavaAnnotation annotation : annotations) {
				String annotationName = annotation.getType().getName();
				if (GlobalConstants.PARAM_REQUIRED.equals(annotationName) 
						|| GlobalConstants.PARAM_REQUIRED_FULLY.equals(annotationName)) {
					AnnotationValue baseVule = annotation.getProperty("base");
					if (baseVule != null) {
						isRequireds.addAll((Collection<? extends String>) baseVule.getParameterValue());
					}
					AnnotationValue objValue = annotation.getProperty("objFields");
					if (objValue != null) {
						isRequireds.addAll((Collection<? extends String>) objValue.getParameterValue());
					}
					AnnotationValue numberValue = annotation.getProperty("numberFields");
					if (numberValue != null) {
						isRequireds.addAll((Collection<? extends String>) numberValue.getParameterValue());
					}
					AnnotationValue arrayValue = annotation.getProperty("arrayFields");
					if (arrayValue != null) {
						isRequireds.addAll((Collection<? extends String>) arrayValue.getParameterValue());
					}
					AnnotationValue phoneFieldsValue = annotation.getProperty("phoneFields");
					if (phoneFieldsValue != null) {
						isRequireds.addAll((Collection<? extends String>) phoneFieldsValue.getParameterValue());
					}
					AnnotationValue emailFieldsValue = annotation.getProperty("emailFields");
					if (emailFieldsValue != null) {
						isRequireds.addAll((Collection<? extends String>) emailFieldsValue.getParameterValue());
					}
					AnnotationValue idCardFieldsValue = annotation.getProperty("idCardFields");
					if (idCardFieldsValue != null) {
						isRequireds.addAll((Collection<? extends String>) idCardFieldsValue.getParameterValue());
					}
				} else if (GlobalConstants.LOG_TAIL.equals(annotationName) 
						|| GlobalConstants.LOG_TAIL_FULLY.equals(annotationName)) {
					String tailDesc = annotation.getNamedParameter(ANNOTATION_VALUE_KEY).toString();
					if (StringUtil.isNotEmpty(tailDesc)) {
						methodDocument = tailDesc.replace("\"", "");
					}
				}
			}
			
			// 接口，本身，以及logTail都没有注释的 不往下走
			if (StringUtil.isEmpty(methodDocument)) {
				continue;
			}
			
			//添加计数
			COUNTER_MAP.put(SERVICE_COUNT_KEY, COUNTER_MAP.get(SERVICE_COUNT_KEY)+1);

			
			String requestParams = getCommentTag(method, PARAM_NAME, cls.getCanonicalName(), isRequireds);
			String requestJson = buildReqJson(method);
			String responseParams = buildMethodReturn(method, cls.getGenericFullyQualifiedName());
			String responseJson = buildReturnJson(method);
			
			ApiDocContent apiMethodDoc = new ApiDocContent();
			apiMethodDoc.setDesc(parentIndex+CONNECTOR+index+SPACE_KEY+methodDocument);
			apiMethodDoc.setUrl(this.appUrl+"/**/*.do");
			apiMethodDoc.setHeaders(createHeaders(this.headers));
			apiMethodDoc.setContentType(JSON_CONTENT_TYPE);
			apiMethodDoc.setType("POST");
			apiMethodDoc.setName(method.getName());
			apiMethodDoc.setServiceName(serviceName + CONNECTOR + method.getName());
			apiMethodDoc.setRequestParams(requestParams);
			apiMethodDoc.setRequestUsage(JsonFormatUtil.formatJson(requestJson));
			apiMethodDoc.setResponseParams(responseParams);
			apiMethodDoc.setResponseUsage(responseJson);
			methodDocList.add(apiMethodDoc);
			index++;
		}
		return methodDocList;
	}
	
	public List<ApiDocContent> buildControllerMethod(int parentIndex, final JavaClass cls, String baseReqPath) {
		List<JavaMethod> methods = cls.getMethods();
		List<ApiDocContent> methodDocList = new ArrayList<>(methods.size());
		
		int index = 1;
		for (JavaMethod method : methods) {
			String methodDocument = method.getComment();
			List<String> modifiers = method.getModifiers();
			// 没有修饰符、没有注释，忽略，不生成
			if (modifiers.isEmpty() 
					|| !"public".equals(modifiers.get(0))
					|| StringUtil.isEmpty(method.getComment())
					|| null != method.getTagByName(GlobalConstants.IGNORE_TAG)) {
				continue;
			}
			
			//添加计数
			COUNTER_MAP.put(CONTROLLER_COUNT_KEY, COUNTER_MAP.get(CONTROLLER_COUNT_KEY)+1);
			
			String reqPath = null;
			List<String> isRequireds = new LinkedList<>();
			List<JavaAnnotation> annotations = method.getAnnotations();
			for (JavaAnnotation annotation : annotations) {
				String annotationName = annotation.getType().getName();
				if (GlobalConstants.LOG_TAIL.equals(annotationName) || GlobalConstants.LOG_TAIL_FULLY.equals(annotationName)) {
					String tailDesc = annotation.getNamedParameter(ANNOTATION_VALUE_KEY).toString();
					if (StringUtil.isNotEmpty(tailDesc)) {
						methodDocument = tailDesc.replace("\"", "");
					}
				}
				
				if (GlobalConstants.REQUEST_MAPPING.equals(annotationName) || GlobalConstants.REQUEST_MAPPING_FULLY.equals(annotationName)) {
					reqPath = annotation.getNamedParameter(ANNOTATION_VALUE_KEY).toString();
				}
			}
			
			if (reqPath==null) {
				reqPath = "";
			} else {
				reqPath = reqPath.replaceAll("\"", "");
				reqPath = reqPath.replaceAll(".do", "");
			}

			String apiReqUrl = baseReqPath+reqPath;
			if (!apiReqUrl.startsWith("/")) {
				apiReqUrl = "/"+apiReqUrl;
			}
			
			String requestParams = getCommentTag(method, PARAM_NAME, cls.getCanonicalName(), isRequireds);
			String requestJson = buildReqJson(method);
			String responseParams = buildMethodReturn(method, cls.getGenericFullyQualifiedName());
			String responseJson = buildReturnJson(method);
			
			ApiDocContent apiDocContent = new ApiDocContent();
			apiDocContent.setDesc(parentIndex+CONNECTOR+index+SPACE_KEY+methodDocument);
			apiDocContent.setUrl(this.appUrl+apiReqUrl+".do");
			apiDocContent.setHeaders(createHeaders(this.headers));
			apiDocContent.setType("POST");
			apiDocContent.setName(method.getName());
			apiDocContent.setRequestParams(requestParams);
			apiDocContent.setRequestUsage(JsonFormatUtil.formatJson(requestJson));
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
			sb.append(header.getName()).append('|');
			sb.append(header.getType()).append('|');
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
					buildParams(gicName, "", 0, new LinkedList<String>(), "", buildReturn);
				}
			}
		} else if (DocClassUtil.isMap(typeName)) {
			String[] keyValue = DocClassUtil.getMapKeyValueType(returnType);
			if (keyValue.length != 0 && DocClassUtil.isPrimitive(keyValue[1])) {
				buildReturn.append(primitiveReturnRespComment("key value"));
			} else {
				buildParams(keyValue[1], "", 0, new LinkedList<String>(), "", buildReturn);
			}
		} else if (StringUtil.isNotEmpty(returnType)) {
			buildParams(returnType, "", 0, new LinkedList<String>(), "", buildReturn);
		}
		return buildReturn.toString();
	}

	private void buildParams(String className, String pre, int i, List<String> requiredFields, String parentFieldName, StringBuilder params) {
		String simpleName = DocClassUtil.getSimpleName(className);
		String[] globGicName = DocClassUtil.getSimpleGicName(className);
		JavaClass cls = builder.getClassByName(simpleName);

		List<JavaField> fields = getFields(cls, 0);
		int n = 0;
		if (DocClassUtil.isPrimitive(className)) {
			params.append(primitiveReturnRespComment(DocClassUtil.processTypeNameForParams(className)));
		} else if (DocClassUtil.isCollection(simpleName) || DocClassUtil.isArray(simpleName)) {
			if (!DocClassUtil.isCollection(globGicName[0])) {
				String gicName = globGicName[0];
				if (DocClassUtil.isArray(gicName)) {
					gicName = gicName.substring(0, gicName.indexOf('['));
				}
				buildParams(gicName, pre, i + 1, requiredFields, parentFieldName, params);
			}
		} else if (DocClassUtil.isMap(simpleName)) {
			if (globGicName.length == 2) {
				buildParams(globGicName[1], pre, i + 1, requiredFields, parentFieldName, params);
			}
		} else {
			out: 
			for (JavaField field : fields) {
				JavaClass declaringClass = field.getDeclaringClass();
				String fieldName = field.getName();
				// 只认ParameterVO参数的Service
				if (!("ParameterVO".equals(declaringClass.getName()) && !PARAM_NAME.equals(fieldName))
						&& !"BaseModel".equals(declaringClass.getName()) 
						&& !"serialVersionUID".equals(fieldName)
						&& !"request".equals(fieldName) 
						&& !"response".equals(fieldName)) {

					if (PARAM_NAME.equals(fieldName)) {
						fieldName = "data";
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
					
					String strRequired = this.isRequired(fieldName, requiredFields,parentFieldName);
					for (JavaAnnotation annotation : javaAnnotations) {
						String annotationName = annotation.getType().getSimpleName();
						if ("JsonIgnore".equals(annotationName)) {
							continue out;
						} else if ("JSONField".equals(annotationName) && null != annotation.getProperty("name")) {
							fieldName = annotation.getProperty("name").toString().replace("\"", "");
						}
					}

					// cover comment
					String comment = field.getComment();
					if (DocClassUtil.isPrimitive(subTypeName)) {
						params.append(pre).append(fieldName).append('|').append(DocClassUtil.processTypeNameForParams(subTypeName)).append('|');
						if (StringUtil.isNotEmpty(comment)) {
							comment = comment.replaceAll("\r|\n", SPACE_KEY);
							params.append(comment).append('|').append(strRequired).append(LINE_BREAK_KEY);
						} else {
							params.append(NO_COMENT_DESC).append('|').append(strRequired).append(LINE_BREAK_KEY);
						}
					} else {
						params.append(pre);
						StringBuilder fieldTypeDesc = new StringBuilder();
						if (DocClassUtil.isCollection(subTypeName)) {
							fieldTypeDesc.append(DocClassUtil.lowerFirstCapse(subTypeName.substring(subTypeName.lastIndexOf(CONNECTOR) + 1)));
							String[] simpleGicName = DocClassUtil.getSimpleGicName(fieldGicName);
							fieldTypeDesc.append('(').append(simpleGicName.length == 1 && DocClassUtil.isPrimitive(simpleGicName[0]) ? DocClassUtil.processTypeNameForParams(simpleGicName[0]) : "obj").append(')'); 
						} else if (DocClassUtil.isArray(subTypeName)) {
							fieldTypeDesc.append("array("+DocClassUtil.processTypeNameForParams(subTypeName.substring(0, subTypeName.indexOf('[')).toLowerCase())+")");
						} else {
							String simpleN = DocClassUtil.getSimpleName(globGicName[n]);
							if (subTypeName.length() == 1 && DocClassUtil.isCollection(simpleN)) {
								fieldTypeDesc.append(DocClassUtil.lowerFirstCapse(simpleN.substring(simpleN.lastIndexOf(CONNECTOR) + 1)));
								String gicSimpleName = DocClassUtil.getGlobGicSimpleName(globGicName[n]);
								fieldTypeDesc.append('(').append(DocClassUtil.isPrimitive(gicSimpleName)?DocClassUtil.processTypeNameForParams(gicSimpleName):"obj").append(')'); 
							} else {
								fieldTypeDesc.append("obj");
							}
						}

						params.append(fieldName).append('|').append(fieldTypeDesc).append('|');
						if (StringUtil.isNotEmpty(comment)) {
							comment = comment.replaceAll("\r|\n", SPACE_KEY);
							params.append(comment).append('|').append(strRequired).append(LINE_BREAK_KEY);
						} else {
							params.append("No comments found|").append(strRequired).append(LINE_BREAK_KEY);
						}
						StringBuilder preBuilder = new StringBuilder();
						for (int j = 0; j < i; j++) {
							preBuilder.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						preBuilder.append("&nbsp;&nbsp;&nbsp;&nbsp;");
						String nextParentFieldName = StringUtil.isNotEmpty(parentFieldName) || "data".equals(fieldName) ? "" : parentFieldName+fieldName+CONNECTOR;
						if (DocClassUtil.isMap(subTypeName)) {
							String gNameTemp = field.getType().getGenericCanonicalName();
							if (DocClassUtil.isMap(gNameTemp)) {
								params.append(preBuilder + "any object|object|any object\n");
							} else {
								String valType = DocClassUtil.getMapKeyValueType(gNameTemp)[1];
								if (!DocClassUtil.isPrimitive(valType)) {
									if (valType.length() == 1) {
										String gicName = (n < globGicName.length) ? globGicName[n]
												: globGicName[globGicName.length - 1];
										if (!DocClassUtil.isPrimitive(gicName) && !simpleName.equals(gicName)) {
											buildParams(gicName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, params);
										}
									} else {
										buildParams(valType, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, params);
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
												buildParams(gicName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, params);
											}
										}
									} else {
										buildParams(gName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, params);
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
												buildParams(gName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, params);
											}
										} else if (DocClassUtil.isMap(simple)) {
											String valType = DocClassUtil.getMapKeyValueType(gicName)[1];
											if (!DocClassUtil.isPrimitive(valType)) {
												buildParams(valType, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, params);
											}
										} else {
											buildParams(gicName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, params);
										}
									} else {
										buildParams(gicName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, params);
									}
								} else {
									buildParams(subTypeName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, params);
								}
							   n++;
							}
						} else if (DocClassUtil.isArray(subTypeName)) {
							fieldGicName = fieldGicName.substring(0, fieldGicName.indexOf('['));
							if (className.equals(fieldGicName)) {
								// do nothing
							} else if (!DocClassUtil.isPrimitive(fieldGicName)) {
								buildParams(fieldGicName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, params);
							}
						} else if (simpleName.equals(subTypeName)) {
							// do nothing
						} else {
							buildParams(fieldGicName, preBuilder.toString(), i + 1, requiredFields, nextParentFieldName, params);
						}
					}
				}
			}
		}
	}

	private String primitiveReturnRespComment(String typeName) {
		StringBuilder comments = new StringBuilder();
		comments.append("no param name|").append(typeName).append('|').append("The interface directly returns the ");
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
	
	private String buildDataJson(String typeName, String genericCanonicalName, boolean isResp) {
		if (DocClassUtil.isMvcIgnoreParams(typeName)) {
			if (GlobalConstants.MODEL_AND_VIEW_FULLY.equals(typeName)) {
				return "forward or redirect to a page view.";
			} else {
				return "error restful return.";
			}
		} else if (DocClassUtil.isPrimitive(typeName)) {
			return DocUtil.jsonValueByType(typeName);
		} else {
			StringBuilder jsonBuilder = new StringBuilder();
			buildJson(typeName, genericCanonicalName, isResp, jsonBuilder);
			return jsonBuilder.toString();
		}
	}
	
	private void buildJson(String typeName, String genericCanonicalName, boolean isResp, StringBuilder jsonBuilder) {
		JavaClass cls;
		try {
			cls = builder.getClassByName(typeName);
		} catch (Exception e) {
			jsonBuilder.append(DocUtil.jsonValueByType(typeName));
			return;
		}
		String[] globGicName = DocClassUtil.getSimpleGicName(genericCanonicalName);
		if (DocClassUtil.isCollection(typeName)) {
			jsonBuilder.append('[');
			if (globGicName.length == 0) {
				jsonBuilder.append("{\"object\":\"any object\"}");
			} else {
				String gName = globGicName[0];
				if (DocClassUtil.isPrimitive(gName)) {
					jsonBuilder.append(DocUtil.jsonValueByType(gName));
				} else if (DocClassUtil.isCollection(gName)) {
					String simple = DocClassUtil.getSimpleName(gName);
					buildJson(simple, gName, isResp,jsonBuilder);
				} else {
					buildJson(gName, gName, isResp, jsonBuilder);
				}
			}
			jsonBuilder.append("]");
		} else if(DocClassUtil.isArray(typeName)) {
			jsonBuilder.append('[');
			String gName = typeName.substring(0, typeName.indexOf('[')).toLowerCase();
			if (DocClassUtil.isPrimitive(gName)) {
				jsonBuilder.append(DocUtil.jsonValueByType(gName));
			} else {
				buildJson(gName, gName, isResp, jsonBuilder);
			}
			jsonBuilder.append("]");
		} else if (DocClassUtil.isMap(typeName)) {
			String gNameTemp = genericCanonicalName;
			String[] getKeyValType = DocClassUtil.getMapKeyValueType(gNameTemp);
			jsonBuilder.append("{");
			jsonBuilder.append(MAP_KEY);
			if (getKeyValType.length == 0) {
				jsonBuilder.append("{}");
			} else {
				String gicName = gNameTemp.substring(gNameTemp.indexOf(CONNECTOR) + 1, gNameTemp.lastIndexOf(RIGHT_ANGLE_BRACKETS));
				if (gicName.length() == 1)  {
					if (DocClassUtil.isPrimitive(gicName)) {
						jsonBuilder.append(DocUtil.jsonValueByType(gicName));
					} else if (gicName.contains(LEFT_ANGLE_BRACKETS)) {
						String simple = DocClassUtil.getSimpleName(gicName);
						buildJson(simple, gicName, isResp,jsonBuilder);
					}
				} else {
					buildJson(gicName, gNameTemp, isResp,jsonBuilder);
				}
			}
			jsonBuilder.append("}");
		} else {
			List<JavaField> fields = getFields(cls, 0);
			jsonBuilder.append("{");
			int i = 0;
			out: 
			for (JavaField field : fields) {
				JavaClass declaringClass = field.getDeclaringClass();
				String fieldName = field.getName();
				if (!("ParameterVO".equals(declaringClass.getName()) && !PARAM_NAME.equals(fieldName))
						&& !"BaseModel".equals(declaringClass.getName()) 
						&& !"user".equals(fieldName)
						&& !"serialVersionUID".equals(fieldName) 
						&& !"request".equals(fieldName)
						&& !"response".equals(fieldName)) {

					if (PARAM_NAME.equals(fieldName)) {
						fieldName = "data";
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
						if ("JsonIgnore".equals(annotationName)) {
							continue out;
						} else if ("JSONField".equals(annotationName) && null != annotation.getProperty("name")) {
							fieldName = annotation.getProperty("name").toString().replace("\"", "");
						}
					}
					
					String typeSimpleName = field.getType().getSimpleName();
					String subTypeName = field.getType().getFullyQualifiedName();
					String fieldGicName = field.getType().getGenericCanonicalName();
					jsonBuilder.append("\"").append(fieldName).append("\":");
					if (DocClassUtil.isPrimitive(typeSimpleName)) {
						jsonBuilder.append(DocUtil.getValByTypeAndFieldName(typeSimpleName, field.getName())).append(COMMA);
					} else if (DocClassUtil.isCollection(subTypeName) || DocClassUtil.isArray(subTypeName)) {
						fieldGicName = DocClassUtil.isArray(subTypeName) ? fieldGicName.substring(0, fieldGicName.indexOf('[')) : fieldGicName;
						if (DocClassUtil.getSimpleGicName(fieldGicName).length == 0) {
							jsonBuilder.append("{\"object\":\"any object\"},");
						} else {
							String gicName = DocClassUtil.getSimpleGicName(fieldGicName)[0];
							jsonBuilder.append('[');
							if (DocClassUtil.isPrimitive(typeName)) {
								jsonBuilder.append(DocUtil.jsonValueByType(typeName));
							} else if (DocClassUtil.isCollection(typeName)) {
								buildJson(gicName, fieldGicName, isResp,jsonBuilder);
							} else if (gicName.length() == 1) {
								if (globGicName.length == 0) {
									jsonBuilder.append("{\"object\":\"any object\"}");
								} else {
									String gicName1 = (i < globGicName.length) ? globGicName[i] : globGicName[globGicName.length - 1];
									if (DocClassUtil.isPrimitive(gicName1)) {
										jsonBuilder.append(DocUtil.jsonValueByType(gicName1));
									} else {
										if (!typeName.equals(gicName1)) {
											buildJson(DocClassUtil.getSimpleName(gicName1), gicName1, isResp,jsonBuilder);
										} else {
											jsonBuilder.append("{\"$ref\":\"..\"}");
										}
									}
								}
							} else {
								if (!typeName.equals(gicName) && !gicName.contains(typeName)) {
									if(DocClassUtil.isPrimitive(gicName)){
										jsonBuilder.append(DocUtil.jsonValueByType(gicName));
									}else {
										buildJson(gicName, fieldGicName, isResp,jsonBuilder);
									}
								} else {
									jsonBuilder.append("{\"$ref\":\"..\"}");
								}
							}
							jsonBuilder.append("],");
						}
					} else if (DocClassUtil.isMap(subTypeName)) {
						String gicName = fieldGicName.substring(fieldGicName.indexOf(CONNECTOR) + 1, fieldGicName.indexOf(RIGHT_ANGLE_BRACKETS));
						jsonBuilder.append("{").append(MAP_KEY);
						if (gicName.length() == 1) {
							String gicName1 = (i < globGicName.length) ? globGicName[i] : globGicName[globGicName.length - 1];
							if (DocClassUtil.isPrimitive(gicName1)) {
								jsonBuilder.append(DocUtil.jsonValueByType(gicName1));
							} else {
								if (!typeName.equals(gicName1)) {
									buildJson(DocClassUtil.getSimpleName(gicName1), gicName1, isResp,jsonBuilder);
								} else {
									jsonBuilder.append("{}");
								}
							}
						} else {
							buildJson(gicName, fieldGicName, isResp,jsonBuilder);
						}
						jsonBuilder.append("},");
					} else if (subTypeName.length() == 1) {
						if (!typeName.equals(genericCanonicalName)) {
							String gicName = globGicName[i];
							if (gicName.contains(LEFT_ANGLE_BRACKETS)) {
								String simple = DocClassUtil.getSimpleName(gicName);
								buildJson(simple, gicName, isResp,jsonBuilder);
							} else {
								if (DocClassUtil.isPrimitive(gicName)) {
									jsonBuilder.append(DocUtil.jsonValueByType(gicName));
								} else {
									buildJson(gicName, gicName, isResp,jsonBuilder);
								}
							}
						} else {
							jsonBuilder.append(WARN_DESC);
						}
						jsonBuilder.append(COMMA);
						i++;
					} else if (typeName.equals(subTypeName)) {
						jsonBuilder.append("{\"$ref\":\"...\"},");
					} else {
						buildJson(subTypeName, fieldGicName, isResp,jsonBuilder);
						jsonBuilder.append(COMMA);
					}
				}
			}
			if (jsonBuilder.toString().contains(COMMA)) {
				jsonBuilder.deleteCharAt(jsonBuilder.lastIndexOf(COMMA));
			}
			jsonBuilder.append("}");
		}
	}

	private String buildReqJson(JavaMethod method) {
		List<JavaParameter> parameterList = method.getParameters();
		for (JavaParameter parameter : parameterList) {
			JavaType javaType = parameter.getType();
			String simpleTypeName = javaType.getValue();
			String gicTypeName = javaType.getGenericCanonicalName();
			String typeName = javaType.getFullyQualifiedName();
			String paraName = parameter.getName();
			if (!DocClassUtil.isMvcIgnoreParams(typeName)) {
				if (DocClassUtil.isPrimitive(simpleTypeName)) {
					StringBuilder sb = new StringBuilder();
					sb.append("{\"").append(paraName).append("\":").append(DocUtil.jsonValueByType(simpleTypeName)).append("}");
					return sb.toString();
				} else {
					return buildDataJson(typeName, gicTypeName, false);
				}
			}
		}
		return "No request parameters are required.";
	}

	private String isRequired(String fieldStr, List<String> requiredFields, String parentFieldName) {
		boolean isRequired = false;
		for (int i = 0; i < requiredFields.size(); i++) {
			isRequired = requiredFields.get(i).indexOf(parentFieldName+fieldStr)!=-1;
			if (isRequired) {
				break;
			}
		}
		return isRequired ? "`"+isRequired + "`" : ""+isRequired;
	}

	private String getCommentTag(final JavaMethod javaMethod, final String tagName, final String className,
			List<String> requiredFields) {
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
				pName = (value.indexOf(SPACE_KEY) > -1) ? value.substring(0, value.indexOf(SPACE_KEY)) : value;
				pValue = value.indexOf(SPACE_KEY) > -1 ? value.substring(value.indexOf(' ') + 1) : NO_COMENT_DESC;
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
							gicName = gicName.substring(0, gicName.indexOf('['));
						}
						String typeTemp = "";
						if (DocClassUtil.isPrimitive(gicName)) {
							typeTemp = " of " + DocClassUtil.processTypeNameForParams(gicName);
							reqParam.append(paramName).append('|').append(DocClassUtil.processTypeNameForParams(gicName)).append(typeTemp).append('|').append(comment).append(TRUE_LINE_BREAK_KEY);
						} else {
							reqParam.append(paramName).append('|').append(DocClassUtil.processTypeNameForParams(gicName)).append(typeTemp).append('|').append(comment).append(TRUE_LINE_BREAK_KEY);
							buildParams(gicNameArr[0], "&nbsp;&nbsp;&nbsp;&nbsp;", 1, requiredFields, "", reqParam);
						}

					} else if (DocClassUtil.isPrimitive(fullTypeName)) {
						reqParam.append(paramName).append('|').append(DocClassUtil.processTypeNameForParams(fullTypeName)).append('|').append(comment).append(TRUE_LINE_BREAK_KEY);
					} else if (DocClassUtil.isMap(typeName)) {
						reqParam.append(paramName).append('|').append("map").append('|').append(comment).append(TRUE_LINE_BREAK_KEY);
					} else {
						buildParams(typeName, "", 0, requiredFields, "", reqParam);
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
				&& !"Object".equals(cls1.getSimpleName()) 
				&& !"Timestamp".equals(cls1.getSimpleName())
				&& !"Date".equals(cls1.getSimpleName()) 
				&& !"Locale".equals(cls1.getSimpleName())) {
			JavaClass pcls = cls1.getSuperJavaClass();
			fieldList.addAll(getFields(pcls, i));
			fieldList.addAll(cls1.getFields());
		}
		return fieldList;
	}
}
