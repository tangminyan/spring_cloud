# spring_cloud

入门: [https://www.cnblogs.com/chry/tag/Spring%20Cloud%E6%95%99%E7%A8%8B/](https://www.cnblogs.com/chry/tag/Spring%20Cloud%E6%95%99%E7%A8%8B/)
## 1. 注册中心
* 1. 新建spring Initializr项目，选择springboot版本号为1.5.X，Spring Cloud Discovery -> Eureka Server ,点击确定，
* 2. 配置文件:
* 
    	spring.application.name=eureka-server
    	#服务注册中心端口号
    	server.port=1110    
    	#服务注册中心实例的主机名
    	eureka.instance.hostname=localhost    
    	#是否向服务注册中心注册自己
    	eureka.client.register-with-eureka=false    
    	#是否检索服务,单节点不需要同步其他的EurekaServer节点的数据
    	eureka.client.fetch-registry=false       
    	#服务注册中心的配置内容，指定服务注册中心的位置
    	eureka.client.serviceUrl.defaultZone=http://localhost:1110/eureka/  

* 3.主函数

    > 加标签  @EnableEurekaServer

* 4.启动，访问  http://localhost:1110

## 2. eureka client
* 1.新建spring Initializr项目，选择springboot版本号为1.5.X，Spring Cloud Discovery -> Eureka Discovery Client
* 2.配置文件

    	spring.application.name=eureka-client-service-provider
		server.port=2001
		eureka.client.serviceUrl.defaultZone=http://localhost:1110/eureka/ 

* 3.主函数

    > 加标签  @EnableDiscoveryClient
    
* 4.启动，刷新 http://localhost:1110，可以看到Instances currently registered with Eureka下多了eureka-client-service-provider服务


## 3.配置管理
### 1) Config Server
* 1.新建spring Initializr项目，选择springboot版本号为1.5.X，选择 Spring Cloud Config -> Config Server 和 Spring Cloud Discovery -> Eureka Server 
* 2.创建自己的git路径，创建配置文件，https://github.com/tangminyan/application.git
* 3.配置文件

    
		server:
		  port: 8666
		
		eureka:
		  client:
		#    registerWithEureka: false
		#    fetchRegistry: false
		    service-url:
		      defaultZone: http://localhost:1110/eureka/
		
		spring:
		  application:
		    name: config-server
		  cloud:
		    config:
		      server:
		        git:
		          uri: https://github.com/tangminyan/application.git
		          username: tangminyan
		          password: tang510434


* 4.主函数

    > 加标签  @EnableEurekaServer，@EnableConfigServer

* 5.启动,访问http://localhost:8666/application-dev2.yml （仓库中有application-dev2.yml的配置文件）

    返回：

        user:
          age: 18
       	  sex: 女
          userName: 贝贝贝-dev2 

### 2) Config Client
* 1.新建spring Initializr项目，选择springboot版本号为1.5.X，选择 Spring Cloud Config -> Config Client 和 Spring Cloud Discovery -> Eureka Discovery Client
* 2.pom文件

    pom 文件中添加：

        <dependency>
		  <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

* 3.配置文件
* 
		eureka:
		  client:
		    service-url:
		      defaultZone: http://localhost:1110/eureka/
		spring:
		  application:
		    name: config-client
		  cloud:
		    config:
		      label: master
		      profile: dev
		      uri: http://localhost:8666/
		#      discovery:
		#        enable: true
		#        service-id: config-server
		server:
		  port: 8881

* 4.主函数

	添加：@EnableDiscoveryClient
* 5. 添加controller

			@RestController
			@RequestMapping(value = "/user")
			public class UserController {
    		  @Value("${user.userName}")
    		  private String userName;
			
    		  @Value("${user.sex}")
    		  private String sex;
    		  @Value("${user.age}")
			  private Integer age;
			  	
    		  @RequestMapping(value = "/info")
    		  public String getUserInfo(){
        	  	return "userName:"+userName+" sex:"+sex+" age:"+age;
    		  }
			}

* 6.启动，请求http://localhost:8881/user/info

	返回：

		userName:贝贝贝-dev2 sex:女 age:18

## 4. Ribbon实现客户端的负载均衡

### 1）代码实现
理解ribbon： [https://www.cnblogs.com/xuwujing/p/10273989.html](https://www.cnblogs.com/xuwujing/p/10273989.html)


假如Hello world服务的访问量剧增，用一个服务已经无法承载， 我们可以把Hello World服务做成一个集群。 
很简单，我们只需要复制Hello world服务，同时将原来的端口8762修改为8763。然后启动这两个Spring Boot应用， 就可以得到两个Hello World服务。这两个Hello world都注册到了eureka服务中心。这时候再访问http://localhost:1110, 可以看到两个hello world服务已经注册。

* 1.新建spring Initializr项目，选择springboot版本号为1.5.X，选择 Spring Cloud Discovery -> Eureka Discovery Client
* 2.pom文件

	添加：

		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-ribbon</artifactId>
        </dependency>
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

* 3.配置文件

		spring.application.name=service-ribbon
		server.port=8901
		eureka.client.serviceUrl.defaultZone=http://localhost:1110/eureka/

* 4.创建Service和Controller

	service:

		@Service
		public class HelloService {
    		@Autowired
    		RestTemplate restTemplate;

    		public String getHelloContent() {
        		return restTemplate.getForObject("http://EUREKA-CLIENT-SERVICE-PROVIDER/", String.class);
    		}
		}

	controller:

		@RestController
		@RequestMapping("/")
		public class HelloController {
    		@Autowired
    		private HelloService helloService;

    		@RequestMapping("port")
    		public String helloPort() {
        			return helloService.getHelloContent();
    		}
		}
		
* 5.主函数

		@SpringBootApplication
		@EnableDiscoveryClient
		@ComponentScan(basePackages = "eureka.service_ribbon")
		public class ServiceRibbonApplication {

		    public static void main(String[] args) {
		        SpringApplication.run(ServiceRibbonApplication.class, args);
		    }

		    @Bean
		    @LoadBalanced
		    RestTemplate restTemplate() {
		        return new RestTemplate();
		    }
		}

* 6.被请求的服务需要存在路径，如此时restTemplate.getForObject("http://EUREKA-CLIENT-SERVICE-PROVIDER/", String.class); 需要存在路径@RequestMapping("/"),添加如下：

		@RestController
		public class DemoController {
		    @Value("${server.port}")
		    private String port;
		
		    @RequestMapping("/")
		    public String getMsg() {
		        return "Say hello port is: " + port;
		
		    }
		}

* 7.启动，连续请求http://localhost:8901/port

会交替出现（ribbon默认循环负载均衡）：

		Say hello port is: 2002

		Say hello port is: 2001
### 2)ribbon原理理解
* 1. @LoadBalanced

@LoadBalanced注解的源码如下：

	@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Inherited
	@Qualifier
	public @interface LoadBalanced {
	}
* 2.LoadBalancerAutoConfiguration
相关包内使用该注解的类只有LoadBalancerAutoConfiguration，使用处为：

	@LoadBalanced
	@Autowired(required = false)
	private List<RestTemplate> restTemplates = Collections.emptyList();
该类就是利用了**RestTempllate**的拦截器，使用**RestTemplateCustomizer**对所有标注了@LoadBalanced的RestTemplate Bean添加了一个**LoadBalancerInterceptor**拦截器，而这个拦截器的作用就是对请求的URI进行转换获取到具体应该请求哪个服务实例ServiceInstance。

这个restTemplates能够将所有标注了@LoadBalanced的RestTemplate自动注入进来，是因为**@Autowired**和**@Qualifier**两个注解本身的作用，详见以下参考：

参考：[https://blog.csdn.net/xiao_jun_0820/article/details/78917215](https://blog.csdn.net/xiao_jun_0820/article/details/78917215)

**LoadBalancerAutoConfiguration**类上有以下注解：

	@Configuration
	@ConditionalOnClass({RestTemplate.class})
	@ConditionalOnBean({LoadBalancerClient.class})
	@EnableConfigurationProperties({LoadBalancerRetryProperties.class})

可以看出会使用LoadBalancerClient来对RestTemplate进行配置。

* 3.LoadBalancerClient

> - ServiceInstance choose(String serviceId) : 根据传入的服务名称，从负载均衡器中挑选一个对应服务的实例。
- <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException : 使用从负载均衡器中挑选出的服务实例来执行请求内容。
- URI reconstructURI(ServiceInstance instance, URI original) : 构建一个符合host:port格式的URI。在分布式系统中，我们都使用逻辑上的服务名作为host来构建URI(代替服务实例的host:port形式)进行请求。在该操作的定义中，前者ServiceInstance对象是带有host和port的具体服务实例，后者URI对象则是使用逻辑服务名的URI，返回的URI是根据这两者转换后的host:port形式的URI。

* 4.LoadBalancerClient中execute函数

函数源码如下：

    public <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException {
        ILoadBalancer loadBalancer = this.getLoadBalancer(serviceId);
        Server server = this.getServer(loadBalancer);
        if (server == null) {
            throw new IllegalStateException("No instances available for " + serviceId);
        } else {
            RibbonLoadBalancerClient.RibbonServer ribbonServer = new RibbonLoadBalancerClient.RibbonServer(serviceId, server, this.isSecure(server, serviceId), this.serverIntrospector(serviceId).getMetadata(server));
            return this.execute(serviceId, ribbonServer, request);
        }
    }

>**完整过程是：
LoadBalancerClient（RibbonLoadBalancerClient是实现类）在初始化的时候（execute方法），会通过ILoadBalance（BaseLoadBalancer是实现类）向Eureka注册中心获取服务注册列表，并且每10s一次向EurekaClient发送“ping”，来判断服务的可用性，如果服务的可用性发生了改变或者服务数量和之前的不一致，则从注册中心更新或者重新拉取。LoadBalancerClient有了这些服务注册列表，就可以根据具体的IRule来进行负载均衡。**

## 5.Feign调用远端HTTP服务

### 1)代码实现
用feign调用刚刚 ribbon的两个服务

* 1.新建spring Initializr项目，选择springboot版本号为1.5.X，选择 Spring Cloud Discovery -> Eureka Discovery Client
* 2.pom文件添加

		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-feign</artifactId>
        </dependency>
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

* 3.配置文件

		eureka:
		  client:
		    service-url:
		      defaultZone: http://localhost:1110/eureka/
		server:
		  port: 8902
		spring:
		  application:
		    name: service-feign

* 4.主函数

	添加注解  @EnableFeignClients

* 5.定义一个用@FeignClient注解的接口类

		@FeignClient(value = "EUREKA-CLIENT-SERVICE-PROVIDER")
		public interface HelloWorldService {
		    @RequestMapping(value = "/", method = RequestMethod.GET)
		    String sayHello();
		}

* 6.创建controller

		@RestController
		public class WebController {
		    @Autowired
		    HelloWorldService helloWorldService;
		
		    @RequestMapping(value = "/hello", method = RequestMethod.GET)
		    public String hello() {
		        return helloWorldService.sayHello();
		    }
		}
* 7.启动，多次访问http://localhost:8902/hello，出现结果与4节中的一致

### 2）feign原理理解
* 1.注解@EnableFeignClients

注解源码：

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE})
	@Documented
	@Import({FeignClientsRegistrar.class})
	public @interface EnableFeignClients {
	    String[] value() default {};
	
	    String[] basePackages() default {};
	
	    Class<?>[] basePackageClasses() default {};
	
	    Class<?>[] defaultConfiguration() default {};
	
	    Class<?>[] clients() default {};
	}
通过注解@Import导入了类FeignClientsRegistrar

* 2.FeignClientsRegistrar——feign客户端注册器

。。。。。。

## 6.断路器
### 1）ribbon上使用断路器

* 1.在之前的两HELLO WORLD服务集群（service-ribbon）中加入断路器， 防止其中一个Hello world挂掉后， 导致系统发生连锁超时失败
* 2.pom文件（注意hystrix-javanica版本号）

		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-hystrix</artifactId>
        </dependency>
        <dependency>
            <groupId>com.netflix.hystrix</groupId>
            <artifactId>hystrix-javanica</artifactId>
            <version>1.4.7</version>
        </dependency>
* 3.主函数

	添加注解  @EnableCircuitBreaker
* 4.修改service函数

		@Service
		public class HelloService {
		    @Autowired
		    RestTemplate restTemplate;
		
		    @HystrixCommand(fallbackMethod = "serviceFailure")
		    public String getHelloContent() {
		        return restTemplate.getForObject("http://EUREKA-CLIENT-SERVICE-PROVIDER/", String.class);
		    }
		
		    public String serviceFailure() {
		        return "hello world service is not available !";
		    }
		}

> @HystrixCommand注解定义了一个断路器，它封装了getHelloContant()方法， 当它访问的EUREKA-CLIENT-SERVICE-PROVIDER失败达到阀值后，将不会再调用EUREKA-CLIENT-SERVICE-PROVIDER， 取而代之的是返回由fallbackMethod定义的方法serviceFailure（）。@HystrixCommand注解定义的fallbackMethod方法，需要特别注意的有两点：

> 1>  fallbackMethod的返回值和参数类型需要和被@HystrixCommand注解的方法完全一致。否则会在运行时抛出异常。比如本例中，serviceFailure（）的返回值和getHelloContant()方法的返回值都是String。

> 2>  当底层服务失败后，fallbackMethod替换的不是整个被@HystrixCommand注解的方法（本例中的getHelloContant), 替换的只是通过restTemplate去访问的具体服务。可以从中的system输出看到， 即使失败，控制台输出里面依然会有“call EUREKA-CLIENT-SERVICE-PROVIDER”。

* 5.启动，停止EUREKA-CLIENT-SERVICE-PROVIDER的其中一个服务,http://localhost:8901/port

	交替出现：

		Say hello port is: 2002

		hello world service is not available !

### 2）feign上使用断路器
Feign内部已经支持了断路器，所以不需要想Ribbon方式一样，在Spring Boot启动类上加额外注解

* 1.在之前service-feign项目上进行修改
* 2.pom文件添加

		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-hystrix</artifactId>
        </dependency>
* 3. service中@FeignClient注解添加fallback参数
* 

		@FeignClient(value = "EUREKA-CLIENT-SERVICE-PROVIDER", fallback = HelloWorldServiceFailure.class)
		public interface HelloWorldService {
		    @RequestMapping(value = "/", method = RequestMethod.GET)
		    String sayHello();
		}

* 4.创建HelloWorldServiceFailure类， 必须实现被@FeignClient修饰的HelloWorldService接口

		@Component
		public class HelloWorldServiceFailure implements HelloWorldService {
		    @Override
		    public String sayHello() {
		        System.out.println("hello world service is not available !");
		        return "hello world service is not available !";
		    }
		}
* 5.Spring Cloud之前的Brixton版本中，Feign是缺省是自动激活了断路器的，但最近的Dalston版本已经将缺省配置修改为禁止。本项目用的Spring Cloud版本未禁止，否则需在配置文件中加入：

		feign:
		   hystrix:
		     enabled: true

* 6.启动，连续请求http://localhost:8902/hello，发现第一次访问到关闭的服务时会显示 “hello world service is not available !”，之后一直访问正常的服务。

## 7.路由网关zuul
“Zuul的主要功能是路由和过滤器。路由功能是微服务的一部分，比如／api/user映射到user服务，/api/shop映射到shop服务。zuul实现了负载均衡。以下是微服务结构中，Zuul的基本流程。在接下来的步骤中，我们来创建一个zuul服务， 将/api-feign/**映射到我们之前创建feign-service, 将/api-ribbon/**映射到之前的ribbon-service服务。”

* 1.新建spring Initializr项目，选择springboot版本号为1.5.X，选择 Spring Cloud Discovery -> Eureka Discovery Client
* 2.pom文件添加：
	
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zuul</artifactId>
            <version>1.4.7.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
* 3.配置文件

		eureka:
		  client:
		    service-url:
		      defaultZone: http://localhost:1110/eureka/
		server:
		  port: 8904
		spring:
		  application:
		    name: service-zuul
		zuul:
		  routes:
		    ribbo:
		      path: /api-ribbon/**
		      serviceId: service-ribbon
		    feign:
		      path: /api-feign/**
		      serviceId: service-feign

* 4.主函数，添加注解 @EnableZuulProxy， @EnableDiscoveryClient

* 5.配置zuul过滤器
* 
		@Component
		public class DemoFilter extends ZuulFilter {
		
		    private static Logger logger = LoggerFactory.getLogger(DemoFilter.class);
		
		    @Override
		    public String filterType() {
		        return "pre";
		    }
		
		    @Override
		    public int filterOrder() {
		        return 0;
		    }
		
		    @Override
		    public boolean shouldFilter() {
		        return true;
		    }
		
		    @Override
		    public Object run() throws ZuulException {
		        RequestContext ctx = RequestContext.getCurrentContext();
		        HttpServletRequest request = ctx.getRequest();
		        String str = String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString());
		        logger.info(str);
		        return null;
		    }
		}

