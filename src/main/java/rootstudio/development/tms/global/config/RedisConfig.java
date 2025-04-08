package rootstudio.development.tms.global.config;

import io.lettuce.core.ClientOptions;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

	private final String host;
	private final int port;
	private final String username;
	private final String password;

	public RedisConfig(
			@Value("${spring.redis.host}") String host,
			@Value("${spring.redis.port}") int port,
			@Value("${spring.redis.username}") String username,
			@Value("${spring.redis.password}") String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	@Bean
	public RedisStandaloneConfiguration redisStandaloneConfiguration() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
		if(!StringUtils.isBlank(username)){
			redisStandaloneConfiguration.setUsername(username);
		}
		if(!StringUtils.isBlank(password)){
			redisStandaloneConfiguration.setPassword(password);
		}
		return redisStandaloneConfiguration;
	}

	@Bean
	public ClientOptions clientOptions() {
		return ClientOptions.builder()
		.disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
		.autoReconnect(true)
		.build();
	}

	@Bean
	public RedisConnectionFactory connectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {
		LettuceClientConfiguration configuration = LettuceClientConfiguration.builder()
		.clientOptions(clientOptions()).build();
		return new LettuceConnectionFactory(redisStandaloneConfiguration, configuration);
	}

	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	@Primary
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}
}