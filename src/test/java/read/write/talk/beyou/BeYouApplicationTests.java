package read.write.talk.beyou;

import groovy.lang.GroovyClassLoader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import read.write.talk.beyou.data.service.AyUserService;
import read.write.talk.beyou.data.model.AyUserDTO;
import read.write.talk.beyou.ext.groovy.DataProvider;
import read.write.talk.beyou.ext.groovy.DataRequest;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
class BeYouApplicationTests {

	@Resource
	private RedisTemplate redisTemplate;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private AyUserService ayUserService;

	@Resource
	ApplicationContext context;


	@Test
	void contextLoads() {

	}

	@Test
	public void testRedis(){
//		System.out.println(redisTemplate.);
		System.out.println((String) redisTemplate.opsForValue().get("name"));
		redisTemplate.opsForValue().set("name", "beYou");
		String name = (String) redisTemplate.opsForValue().get("name");
		System.out.println(name);

		redisTemplate.delete("name");

		System.out.println((String) redisTemplate.opsForValue().get("name"));
		redisTemplate.opsForValue().set("name", "beYou1");
		System.out.println((String) redisTemplate.opsForValue().get("name"));
	}

	/**
	 * Mysql集成Spring Boot简单测试
	 */
	@Test
	public void mySqlTest() {
		String sql = "select id,name,password from ay_user";
		List<AyUserDTO> userList =
				jdbcTemplate.query(sql, (rs, rowNum) -> {
					AyUserDTO user = new AyUserDTO();
					user.setId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					return user;
				});
		System.out.println("查询成功：");
		for (AyUserDTO user : userList) {
			System.out.println("【id】: " + user.getId() + "；【name】: " + user.getName());
		}
	}


	@Test
	public void testRepository() {
		//查询所有数据
		List<AyUserDTO> userList = ayUserService.findAll();
		System.out.println("findAll() :" + userList.size());
		//通过name查询数据
		List<AyUserDTO> userList2 = ayUserService.findByName("阿毅");
		System.out.println("findByName() :" + userList2.size());
		Assert.isTrue(userList2.get(0).getName().equals("阿毅"), "data error!");
		//通过name模糊查询数据
		List<AyUserDTO> userList3 = ayUserService.findByNameLike("%毅%");
		System.out.println("findByNameLike() :" + userList3.size());
		Assert.isTrue(userList3.get(0).getName().equals("阿毅"), "data error!");
		//通过id列表查询数据
		List<String> ids = new ArrayList<String>();
		ids.add("1");
		ids.add("2");
		List<AyUserDTO> userList4 = ayUserService.findByIdIn(ids);
		System.out.println("findByIdIn() :" + userList4.size());
		//分页查询数据
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<AyUserDTO> userList5 = ayUserService.findAll(pageRequest);
		System.out.println("page findAll():" + userList5.getTotalPages() + "/" + userList5.getSize());
		//新增数据
		AyUserDTO ayUser = new AyUserDTO();
		ayUser.setId("3");
		ayUser.setName("test");
		ayUser.setPassword("123");
		ayUserService.save(ayUser);
		//删除数据
		ayUserService.delete("3");

	}

	@Test
	public void testTransaction() {
		AyUserDTO ayUser = new AyUserDTO();
		ayUser.setId("3");
		ayUser.setName("阿华");
		ayUser.setPassword("123");
		ayUserService.save(ayUser);
	}


	/**
	 * 测试redis
	 */
	@Test
	public void testFindById() {
		Long redisUserSize = 0L;
		//查询id = 1 的数据，该数据存在于redis缓存中
		AyUserDTO ayUser = ayUserService.findById("1");
		redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
		System.out.println("目前缓存中的用户数量为：" + redisUserSize);
		System.out.println("--->>> id: " + ayUser.getId() + " name:" + ayUser.getName());
		//查询id = 2 的数据，该数据存在于redis缓存中
		AyUserDTO ayUser1 = ayUserService.findById("2");
		redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
		System.out.println("目前缓存中的用户数量为：" + redisUserSize);
		System.out.println("--->>> id: " + ayUser1.getId() + " name:" + ayUser1.getName());
		//查询id = 4 的数据，该数据不存在于redis缓存中，存在于数据库中
		AyUserDTO ayUser3 = ayUserService.findById("4");
		System.out.println("--->>> id: " + ayUser3.getId() + " name:" + ayUser3.getName());
		redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
		System.out.println("目前缓存中的用户数量为：" + redisUserSize);

	}


	@Test
	public void testGroovy() throws IOException {
		ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap(128);
		final String path = "classpath*:*.groovyTemplate";
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Arrays.stream(resolver.getResources(path))
				.parallel()
				.forEach(resource -> {
					try {
						String fileName = resource.getFilename();
						InputStream input = resource.getInputStream();
						InputStreamReader reader = new InputStreamReader(input);
						BufferedReader br = new BufferedReader(reader);
						StringBuilder template = new StringBuilder();
						for (String line; (line = br.readLine()) != null; ) {
							template.append(line).append("\n");
						}
						concurrentHashMap.put(fileName, template.toString());
					} catch (Exception e) {
						System.out.println("resolve file failed");
					}
				});

		String scriptBuilder = concurrentHashMap.get("DataProvider.groovyTemplate");
		String importStr = "import read.write.talk.beyou.data.model.AyUserDTO;\n" +
				"import read.write.talk.beyou.data.service.AyUserService;\n"+
				"import javax.annotation.Resource;\n"
				;
		String scriptClassName = "TestGroovy";

		String auto = " @Resource\n" +
				"    private AyUserService ayUserService;";
		String validate = "";
		String dependsOn = "Collections.emptyList()";
		String dataName = "dataName";
		//这一部分String的获取逻辑进行可配置化
		String StrategyLogicUnit = "if( \"dataGroup\".equals(request.getDataGroup())){\n" +
				"            Map<String, Object> s = new HashMap<>();\n" +
				"            s.put(\"a\", request.getDataGroup());\n" +
				"            List<AyUserDTO> ayUser = ayUserService.findAll();\n" +
				"            s.put(\"users\",ayUser);\n" +
				"            return s;\n" +
				"        }\n" +
				"        return new HashMap<>();";

		String StrategyLogicUnit1 = "if( \"dataGroup\".equals(request.getDataGroup())){\n" +
				"            Map<String, Object> s = new HashMap<>();\n" +
				"            s.put(\"a\", request.getDataGroup());\n" +
				"            s.put(\"b\", request.getDataGroup());\n" +
				"            return s;\n" +
				"        }\n" +
				"        return new HashMap<>();";
		String fullScript = String.format(scriptBuilder, importStr, scriptClassName, auto,
				validate, dependsOn, dataName,
				StrategyLogicUnit);

		Runtime runtime = Runtime.getRuntime();
		GroovyClassLoader classLoader = new GroovyClassLoader();
//		Class<DataProvider> aClass = classLoader.parseClass(fullScript);
			Class<DataProvider> aClass = classLoader.parseClass(fullScript, "a");
			classLoader.clearCache();
		for (int i = 0; i < 500; i++) {
			tt(aClass);
			printMemory(runtime);
		}


//		fullScript = String.format(scriptBuilder, importStr, scriptClassName,
//				validate, dependsOn, dataName, StrategyLogicUnit1);
//		aClass = classLoader.parseClass(fullScript, "a");
//		for (int i = 0; i < 500; i++) {
//			tt(aClass);
//			printMemory(runtime);
//		}

	}

	private void tt(Class<DataProvider> aClass) {

		DataRequest dataRequest = new DataRequest();
		dataRequest.setDataGroup("dataGroup");
		dataRequest.setAdditional("additional");
		try {
			DataProvider dataProvider = aClass.newInstance();
			context.getAutowireCapableBeanFactory().autowireBean(dataProvider);
			System.out.println("Groovy Script returns:{} "+dataProvider.handle(dataRequest, new HashMap<>()));
		}
		catch (Exception e){
			System.out.println("error...");
		}
	}

	void printMemory(Runtime runtime) {
		System.out.println(runtime.totalMemory());
		System.out.println(runtime.freeMemory());
	}

}
